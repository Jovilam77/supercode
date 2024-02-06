package ${config.basePackage}<#if config.module?? && config.module!=''>.${config.module!}</#if>.service;

import java.util.List;
import ${config.basePackage}.model.${className};

/**
 * ${tableInfo.remarks!} ${config.appProjectClassNamePrefix}业务接口
 *
 * @author ${config.author!}<#if config.version?? && config.version!=''>
 * @version ${config.version!}</#if><#if config.email?? && config.email!=''>
 * @email ${config.email!}</#if>
 * @date ${date?string('yyyy-MM-dd HH:mm:ss')}
 */
public interface App${className}Service {

    /**
      * 根据id查询
      *
      * @param id
      * @return
      */
      Result<${className}> getById(Long id);

    /**
      * 查询全部
      *
      * @return
      */
      Result<List<${className}>> getAll();

    /**
      * 分页列表查询
      *
      * @param pageNum
      * @param pageSize
      * @return
      */
      Result<ResultData<${className}>> list(int pageNum, int pageSize);

    /**
      * 新增
      *
      * @param ${className?uncap_first}CreateDto
      * @return
      */
      Result<Void> add(${className}CreateDto ${className?uncap_first}CreateDto);

     /**
      * 根据id修改
      *
      * @param ${className?uncap_first}UpdateDto
      * @return
      */
      Result<Void> updateById(${className}UpdateDto ${className?uncap_first}UpdateDto);

     /**
      * 新增或编辑
      *
      * @param ${className?uncap_first}UpdateDto
      * @return
      */
      Result<Void> addOrEdit(${className}UpdateDto ${className?uncap_first}UpdateDto);

     /**
      * 根据id删除
      *
      * @param id
      * @return
      */
      Result<Void> deleteById(Long id);

}