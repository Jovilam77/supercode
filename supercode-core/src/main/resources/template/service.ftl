package ${config.basePackage}.service;

<#if config.useSqlBean>
import cn.vonce.sql.service.SqlBeanService;
<#else>
import java.util.List;
</#if>
import ${config.basePackage}.model.${className};

/**
 * ${tableInfo.comm!} 业务接口
 *
 * @author ${config.author!}
 * @version ${config.version!}
 * @email ${config.email!}
 * @date ${date?string('yyyy-MM-dd HH:mm:ss')}
 */
public interface ${className}Service <#if config.useSqlBean>extends SqlBeanService<${className}, ${id.typeName}></#if> {

<#if !config.useSqlBean>
    /**
     * 根据id查询
     *
     * @param ${id.name}
     * @return
     */
    ${className} selectById(${id.typeName!} ${id.name!});

    /**
     * 查询全部
     *
     * @return
     */
    List<${className}> selectAll();

    /**
     * 新增
     *
     * @param ${className?uncap_first}
     * @return
     */
    int insert(${className} ${className?uncap_first});

    /**
     * 根据id修改
     *
     * @param ${className?uncap_first}
     * @return
     */
    int updateById(${className} ${className?uncap_first});

    /**
     * 根据id删除
     *
     * @param ${id.name}
     * @return
     */
    int deleteById(${id.typeName} ${id.name});
</#if>

}