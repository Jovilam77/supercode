package ${config.basePackage}.service.impl;

<#if config.useSqlBean == true>
import cn.vonce.sql.spring.service.MybatisSqlBeanServiceImpl;
<#else>
import java.util.List;
</#if>
import ${config.basePackage}.mapper.${className}Mapper;
import ${config.basePackage}.model.${className};
import ${config.basePackage}.service.${className}Service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ${tableInfo.comment!} 业务实现
 *
 * @author ${config.author!}
 * @version ${config.version!}
 * @email ${config.email!}
 * @date ${date?string('yyyy-MM-dd HH:mm:ss')}
 */
 @Service
public class ${className}ServiceImpl <#if config.useSqlBean == true>extends MybatisSqlBeanServiceImpl<${className}, ${id.typeName}></#if> implements ${className}Service {

    @Autowired
    private ${className}Mapper ${className?uncap_first}Mapper;

<#if config.useSqlBean == false>
    /**
     * 根据id查询
     *
     * @param ${id.name}
     * @return
     */
	@Override
    public ${className} selectById(${id.typeName} ${id.name}) {
		return ${className?uncap_first}Mapper.selectById(${id.name});
    }

    /**
     * 查询全部
     *
     * @return
     */
	@Override
    public List<${className}> selectAll() {
		return ${className?uncap_first}Mapper.selectAll();
    }

    /**
     * 新增
     *
     * @param ${className?uncap_first}
     * @return
     */
	@Override
    public int insert(${className} ${className?uncap_first}) {
		return ${className?uncap_first}Mapper.insert(${className?uncap_first});
    }

    /**
     * 根据id修改
     *
     * @param ${className?uncap_first}
     * @return
     */
	@Override
    public int updateById(${className} ${className?uncap_first}) {
		return ${className?uncap_first}Mapper.insert(${className?uncap_first});
    }

    /**
     * 根据id删除
     *
     * @param ${id.name}
     * @return
     */
	@Override
    public int deleteById(${id.typeName} ${id.name}) {
		return ${className?uncap_first}Mapper.selectById(${id.name});
    }
</#if>

}