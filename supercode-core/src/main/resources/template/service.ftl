package ${config.basePackage}.service;

<#if config.useSqlBean == true>
import cn.vonce.sql.service.SqlBeanService;
</#if>
import ${config.basePackage}.model.${className};
import java.util.List;

/**
 * ${tableInfo.comment} 业务接口
 *
 * @author ${config.author}
 * @version ${config.version}
 * @email ${config.email}
 * @date ${date}
 */
public interface ${className}Service <#if config.useSqlBean == true>extends SqlBeanService<${className}, ${id.type}></#if> {

<#if config.useSqlBean == false>
    /**
     * 根据id查询
     *
     * @param ${id.name}
     * @return
     */
    ${className} selectById(${id.type} ${id.name});

    /**
     * 查询全部
     *
     * @return
     */
    List<${className}> selectAll();

    /**
     * 新增
     *
     * @param ${className?cap_first}
     * @return
     */
    int insert(${className} ${className?cap_first});

    /**
     * 根据id修改
     *
     * @param ${className?cap_first}
     * @return
     */
    int updateById(${className} ${className?cap_first});

    /**
     * 根据id删除
     *
     * @param ${id.name}
     * @return
     */
    int deleteById(${id.type} ${id.name});
</#if>

}