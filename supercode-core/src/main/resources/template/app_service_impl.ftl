package ${config.basePackage}<#if config.module?? && config.module!=''>.${config.module!}</#if>.service.impl;

import java.util.List;
import cn.vonce.sql.bean.Select;
import cn.vonce.sql.page.PageHelper;
import cn.vonce.sql.page.ResultData;
import ${config.basePackage}.app.service.App${className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import ${config.basePackage}.model.${className};
import ${config.basePackage}.service.${className}Service;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * ${tableInfo.remarks!} App业务实现
 *
 * @author ${config.author!}<#if config.version?? && config.version!=''>
 * @version ${config.version!}</#if><#if config.email?? && config.email!=''>
 * @email ${config.email!}</#if>
 * @date ${date?string('yyyy-MM-dd HH:mm:ss')}
 */
@Service
public class App${className}ServiceImpl implements App${className}Service {

    @Autowired
    private ${className}Service ${className?uncap_first}Service;

	@Override
    public Result<${className}> getById(${id.typeName} ${id.name}) {
		return Result.success(${className?uncap_first}Service.selectById(${id.name}));
    }

	@Override
    public Result<List<${className}>> getAll() {
		return Result.success(${className?uncap_first}Service.select());
    }

    @Override
    public Result<ResultData<${className}>> list(int pageNum, int pageSize) {
		Select select = new Select();
		PageHelper pageHelper = new PageHelper(pageNum, pageSize);
		ResultData<${className}> resultData = ${className?uncap_first}Service.paging(select, pageHelper);
		return Result.success(resultData);
    }

	@Override
    public Result<Void> add(${className}CreateDto ${className?uncap_first}CreateDto) {
        ${className} ${className?uncap_first} = new ${className}();
        BeanUtils.copyProperties(${className?uncap_first}CreateDto, ${className?uncap_first});
        if (${className?uncap_first}Service.insert(${className?uncap_first}) > 0) {
            return Result.success();
        }
		return Result.fail();
    }

	@Override
    public Result<Void> updateById(${className}UpdateDto ${className?uncap_first}UpdateDto) {
        ${className} ${className?uncap_first} = new ${className}();
        BeanUtils.copyProperties(${className?uncap_first}UpdateDto, ${className?uncap_first});
        if (${className?uncap_first}Service.updateByBeanId(${className?uncap_first}) > 0) {
            return Result.success();
        }
        return Result.fail();
    }

	@Override
    public Result<Void> addOrEdit(${className}UpdateDto ${className?uncap_first}UpdateDto) {
        if (${className?uncap_first}UpdateDto.getId() == null) {
            ${className}CreateDto ${className?uncap_first}CreateDto = new ${className}CreateDto();
            BeanUtils.copyProperties(${className?uncap_first}UpdateDto, ${className?uncap_first}CreateDto);
            return add(${className?uncap_first}CreateDto);
        }
        return updateById(${className?uncap_first}UpdateDto);
    }

	@Override
    public Result<Void> deleteById(${id.typeName} ${id.name}) {
        if (${className?uncap_first}Service.deleteById(${id.name}) > 0) {
            return Result.success();
        }
		return Result.fail();
    }

}