package ${config.basePackage}.mapper;

import ${config.basePackage}.model.${className};
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

/**
 * ${tableInfo.comment!} dao
 *
 * @author ${config.author!}
 * @version ${config.version!}
 * @email ${config.email!}
 * @date ${date?string('yyyy-MM-dd HH:mm:ss')}
 */
public interface ${className}Mapper {

    /**
     * 根据id查询
     *
     * @param ${id.name}
     * @return
     */
    @Select("SELECT * FROM ${tableInfo.name} WHERE ${id.columnInfo.name!} = ${r"#"}{${id.name},jdbcType=${id.columnInfo.type}}")
    ${className} selectById(${id.typeName} ${id.name});

    /**
     * 查询全部
     *
     * @return
     */
    @Select("SELECT * FROM ${tableInfo.name} ")
    List<${className}> selectAll();

    /**
     * 新增
     *
     * @param ${className?uncap_first}
     * @return
     */
    @Insert({ "insert into ${tableInfo.name} ",
              "(<#list filedInfoList as filedInfo>${filedInfo.columnInfo.name}<#if (filedInfo_index < (filedInfoList?size - 1))>, </#if></#list> )",
     		  "values (<#list filedInfoList as filedInfo>${r"#"}{${filedInfo.name},jdbcType=${filedInfo.columnInfo.type}}<#if (filedInfo_index < (filedInfoList?size - 1))>, </#if></#list>)" })
    int insert(${className} ${className?uncap_first});

    /**
     * 根据id修改
     *
     * @param ${className?uncap_first}
     * @return
     */
    @Update({ "update ${tableInfo.name}",
              "set "<#list filedInfoList as filedInfo>,
              "${filedInfo.columnInfo.name} = ${r"#"}{${filedInfo.name},jdbcType=${filedInfo.columnInfo.type}}<#if (filedInfo_index < (filedInfoList?size - 1))>, </#if>"</#list>,
     		  "where ${id.columnInfo.name} = ${r"#"}{${id.name},jdbcType=${id.columnInfo.type}}"})
    int updateById(${className} ${className?uncap_first});

    /**
     * 根据id删除
     *
     * @param ${id.name}
     * @return
     */
    @Delete({ "delete from ${tableInfo.name} where ${id.columnInfo.name} = ${r"#"}{${id.name},jdbcType=${id.columnInfo.type}}"})
    int deleteById(${id.typeName} ${id.name});

}