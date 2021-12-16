package ${config.basePackage}.service.impl;

<#if config.useSqlBean && config.getJdbcDaoType().name() == 'MyBatis'>
import cn.vonce.sql.spring.service.MybatisSqlBeanServiceImpl;
<#elseif config.useSqlBean && config.getJdbcDaoType().name() == 'SpringJdbc'>
import cn.vonce.sql.spring.service.SpringJdbcSqlBeanServiceImpl;
</#if>
<#if !config.useSqlBean>
import java.util.List;
</#if>
<#if !config.useSqlBean && config.getJdbcDaoType().name() == 'MyBatis'>
import org.springframework.beans.factory.annotation.Autowired;
import ${config.basePackage}.mapper.${className}Mapper;
<#elseif !config.useSqlBean && config.getJdbcDaoType().name() == 'SpringJdbc'>
import org.springframework.beans.factory.annotation.Autowired;
import ${config.basePackage}.jdbc.${className}Jdbc;
import org.springframework.jdbc.core.JdbcTemplate;
</#if>
import ${config.basePackage}.model.${className};
import ${config.basePackage}.service.${className}Service;
import org.springframework.stereotype.Service;

/**
 * ${tableInfo.comm!} 业务实现
 *
 * @author ${config.author!}
 * @version ${config.version!}
 * @email ${config.email!}
 * @date ${date?string('yyyy-MM-dd HH:mm:ss')}
 */
@Service
public class ${className}ServiceImpl <#if config.useSqlBean && config.getJdbcDaoType().name() == 'MyBatis' >extends MybatisSqlBeanServiceImpl <${className}, ${id.typeName}><#elseif config.useSqlBean && config.getJdbcDaoType().name() == 'SpringJdbc'>extends SpringJdbcSqlBeanServiceImpl <${className}, ${id.typeName}></#if>implements ${className}Service {

<#if !config.useSqlBean && config.getJdbcDaoType().name() == 'MyBatis'>
    @Autowired
    private ${className}Mapper ${className?uncap_first}Mapper;
</#if>
<#if !config.useSqlBean && config.getJdbcDaoType().name() == 'SpringJdbc'>
    @Autowired
    private JdbcTemplate jdbcTemplate;
</#if>

<#if !config.useSqlBean>
    /**
     * 根据id查询
     *
     * @param ${id.name}
     * @return
     */
	@Override
    public ${className} selectById(${id.typeName} ${id.name}) {
<#if config.getJdbcDaoType().name() == 'MyBatis'>
		return ${className?uncap_first}Mapper.selectById(${id.name});
<#else>
		return ${className}Jdbc.selectById(jdbcTemplate, ${id.name});
</#if>
    }

    /**
     * 查询全部
     *
     * @return
     */
	@Override
    public List<${className}> selectAll() {
<#if config.getJdbcDaoType().name() == 'MyBatis'>
		return ${className?uncap_first}Mapper.selectAll();
<#else>
		return ${className}Jdbc.selectAll(jdbcTemplate);
</#if>
    }

    /**
     * 新增
     *
     * @param ${className?uncap_first}
     * @return
     */
	@Override
    public int insert(${className} ${className?uncap_first}) {
<#if config.getJdbcDaoType().name() == 'MyBatis'>
		return ${className?uncap_first}Mapper.insert(${className?uncap_first});
<#else>
		return ${className}Jdbc.insert(jdbcTemplate, ${className?uncap_first});
</#if>
    }

    /**
     * 根据id修改
     *
     * @param ${className?uncap_first}
     * @return
     */
	@Override
    public int updateById(${className} ${className?uncap_first}) {
<#if config.getJdbcDaoType().name() == 'MyBatis'>
		return ${className?uncap_first}Mapper.updateById(${className?uncap_first});
<#else>
		return ${className}Jdbc.updateById(jdbcTemplate, ${className?uncap_first});
</#if>
    }

    /**
     * 根据id删除
     *
     * @param ${id.name}
     * @return
     */
	@Override
    public int deleteById(${id.typeName} ${id.name}) {
<#if config.getJdbcDaoType().name() == 'MyBatis'>
		return ${className?uncap_first}Mapper.deleteById(${id.name});
<#else>
		return ${className}Jdbc.deleteById(jdbcTemplate, ${id.name});
</#if>
    }
</#if>

}