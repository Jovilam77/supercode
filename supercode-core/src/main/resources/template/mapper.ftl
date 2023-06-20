package ${config.basePackage}<#if config.module?? && config.module!=''>.${config.module!}</#if>.<#if config.getJdbcDaoType().name() == 'MyBatis'>mapper<#else>jdbc</#if>;

import ${config.basePackage}.model.${className};
<#if config.getJdbcDaoType().name() == 'MyBatis'>
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
<#else>
import org.springframework.jdbc.core.JdbcTemplate;
</#if>
import java.util.List;

/**
 * ${tableInfo.remarks!} dao
 *
 * @author ${config.author!}<#if config.version?? && config.version!=''>
 * @version ${config.version!}</#if><#if config.email?? && config.email!=''>
 * @email ${config.email!}</#if>
 * @date ${date?string('yyyy-MM-dd HH:mm:ss')}
 */
public <#if config.getJdbcDaoType().name() == 'MyBatis'>interface<#else>class</#if> ${className}<#if config.getJdbcDaoType().name() == 'MyBatis'>Mapper<#else>Jdbc</#if> {

<#if config.getJdbcDaoType().name() == 'MyBatis'>
    /**
     * 根据id查询
     *
     * @param ${id.name}
     * @return
     */
    @Select("SELECT * FROM ${tableInfo.name} WHERE ${id.columnInfo.name!} = ${r"#"}{${id.name}, jdbcType=${id.columnInfo.type}}")
    ${className} selectById(${id.typeName} ${id.name});
<#else>
    /**
     * 根据id查询
     *
     * @param jdbcTemplate
     * @param ${id.name}
     * @return
     */
    public static ${className} selectById(JdbcTemplate jdbcTemplate, ${id.typeName} ${id.name}) {
    	String sql = "SELECT * FROM ${tableInfo.name} WHERE ${id.columnInfo.name!} = ?";
		return jdbcTemplate.queryForObject(sql, ${className}.class, ${id.name});
    }
</#if>

<#if config.getJdbcDaoType().name() == 'MyBatis'>
    /**
     * 查询全部
     *
     * @return
     */
    @Select("SELECT * FROM ${tableInfo.name} ")
    List<${className}> selectAll();
<#else>
    /**
     * 查询全部
     *
     * @param jdbcTemplate
     * @return
     */
    public static List<${className}> selectAll(JdbcTemplate jdbcTemplate) {
    	String sql = "SELECT * FROM ${tableInfo.name}";
		return jdbcTemplate.queryForList(sql, ${className}.class);
    }
</#if>

<#if config.getJdbcDaoType().name() == 'MyBatis'>
    /**
     * 新增
     *
     * @param ${className?uncap_first}
     * @return
     */
    @Insert({ "INSERT INTO ${tableInfo.name} ",
              "(<#list fieldInfoList as filedInfo>${filedInfo.columnInfo.name}<#if (filedInfo_index < (fieldInfoList?size - 1))>, </#if></#list> )",
     		  "VALUES (<#list fieldInfoList as filedInfo>",
     		  "${r"#"}{${filedInfo.name}, jdbcType=${filedInfo.columnInfo.type}}<#if (filedInfo_index < (fieldInfoList?size - 1))>, </#if></#list>)" })
    int insert(${className} ${className?uncap_first});
<#else>
    /**
     * 新增
     *
     * @param jdbcTemplate
     * @param ${className?uncap_first}
     * @return
     */
    public static int insert(JdbcTemplate jdbcTemplate, ${className} ${className?uncap_first}) {
    	String sql = "INSERT INTO ${tableInfo.name} " +
    	             "(<#list fieldInfoList as filedInfo>${filedInfo.columnInfo.name}<#if (filedInfo_index < (fieldInfoList?size - 1))>, </#if></#list> )" +
		             "VALUES (<#list fieldInfoList as filedInfo>?<#if (filedInfo_index < (fieldInfoList?size - 1))>, </#if></#list>)";
		Object[] args = {<#list fieldInfoList as filedInfo>${className?uncap_first}.get${filedInfo.name?cap_first}()<#if (filedInfo_index < (fieldInfoList?size - 1))>, </#if></#list>};
		return jdbcTemplate.update(sql, args);
    }
</#if>

<#if config.getJdbcDaoType().name() == 'MyBatis'>
    /**
     * 根据id修改
     *
     * @param ${className?uncap_first}
     * @return
     */
    @Update({ "UPDATE ${tableInfo.name}",
              "SET "<#list fieldInfoList as filedInfo>,
              "${filedInfo.columnInfo.name} = ${r"#"}{${filedInfo.name},jdbcType=${filedInfo.columnInfo.type}}<#if (filedInfo_index < (fieldInfoList?size - 1))>, </#if>"</#list>,
     		  "WHERE ${id.columnInfo.name} = ${r"#"}{${id.name}, jdbcType=${id.columnInfo.type}}" })
    int updateById(${className} ${className?uncap_first});
<#else>
    /**
     * 根据id修改
     *
     * @param jdbcTemplate
     * @param ${className?uncap_first}
     * @return
     */
    public static int updateById(JdbcTemplate jdbcTemplate, ${className} ${className?uncap_first}) {
    	String sql = "UPDATE ${tableInfo.name}" +
    	             "SET " +
		             "<#list fieldInfoList as filedInfo>${filedInfo.columnInfo.name} = ?<#if (filedInfo_index < (fieldInfoList?size - 1))>, </#if></#list>" +
		             "WHERE ${id.columnInfo.name} = ?";
		Object[] args = {<#list fieldInfoList as filedInfo>${className?uncap_first}.get${filedInfo.name?cap_first}()<#if (filedInfo_index < (fieldInfoList?size - 1))>, </#if></#list>, ${className?uncap_first}.get${id.name?cap_first}()};
		return jdbcTemplate.update(sql, args);
    }
</#if>

<#if config.getJdbcDaoType().name() == 'MyBatis'>
    /**
     * 根据id删除
     *
     * @param ${id.name}
     * @return
     */
    @Delete("DELETE FROM ${tableInfo.name} WHERE ${id.columnInfo.name} = ${r"#"}{${id.name}, jdbcType=${id.columnInfo.type}}")
    int deleteById(${id.typeName} ${id.name});
<#else>
    /**
     * 根据id删除
     *
     * @param jdbcTemplate
     * @param ${id.name}
     * @return
     */
    public static int deleteById(JdbcTemplate jdbcTemplate, ${id.typeName} ${id.name}) {
    	String sql = "DELETE FROM ${tableInfo.name} WHERE ${id.columnInfo.name} = ?";
		return jdbcTemplate.update(sql, ${id.name});
    }
</#if>

}