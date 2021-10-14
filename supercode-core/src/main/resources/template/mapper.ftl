package ${config.basePackage}.mapper;

import ${config.basePackage}.model.${className};
import java.util.List;

/**
 * ${comm} dao
 *
 * @author ${config.author}
 * @version ${config.version}
 * @email ${config.email}
 * @date ${date}
 */
public interface ${className}Mapper {

    /**
     * 根据id查询
     *
     * @param ${id.name}
     * @return
     */
     @Select("SELECT * FROM ${tableName} WHERE ${id.columnInfo.name} = #{${id.name,jdbcType=${id.columnInfo.type}}} ")
    ${className} selectById(${id.type} ${id.name});

    /**
     * 查询全部
     *
     * @return
     */
     @Select("SELECT * FROM ${tableName} ")
    List<${className}> selectAll();

    /**
     * 新增
     *
     * @param ${className?cap_first}
     * @return
     */
     @Insert({ "insert into ${tableName} "(<#list filedInfoList as filedInfo>${filedInfo.columnInfo.name}<#if (filedInfo_index < (filedInfoList?size - 1))>, </#if></#list> )",
     			"values (<#list filedInfoList as filedInfo>#{${filedInfo.name},jdbcType=${filedInfo.columnInfo.type}}<#if (filedInfo_index < (filedInfoList?size - 1))>, </#if></#list>)" })
    int insert(${className} ${className?cap_first});

    /**
     * 根据id修改
     *
     * @param ${className?cap_first}
     * @return
     */
     @Update({ "update ${tableName}", "set <#list filedInfoList as filedInfo>${filedInfo.columnInfo.name} = #{${filedInfo.name},jdbcType=${filedInfo.columnInfo.type}}<#if (filedInfo_index < (filedInfoList?size - 1))>, </#if></#list>",
     			"where ${id.columnInfo.name} = #{${id.name},jdbcType=${id.columnInfo.type}}" })
    int updateById(${className} ${className?cap_first});

    /**
     * 根据id删除
     *
     * @param ${id.name}
     * @return
     */
     @Delete({ "delete from ${tableName} where ${id.columnInfo.name} = #{${id.name,jdbcType=${id.columnInfo.type}}}" })
    int deleteById(${id.type} ${id.name});

}