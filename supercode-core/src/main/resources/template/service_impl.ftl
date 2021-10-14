package ${config.basePackage}.service.impl;

<#if config.useSqlBean == true>
import cn.vonce.sql.service.SqlBeanService;
</#if>
import ${config.basePackage}.model.${className};
import ${config.basePackage}.service.${className}Service;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * ${comm} 业务实现
 *
 * @author ${config.author}
 * @version ${config.version}
 * @email ${config.email}
 * @date ${date}
 */
 @Service
public class ${className}ServiceImpl <#if config.useSqlBean == true>extends MybatisSqlBeanServiceImpl<${className}, ${id.type}></#if> implements ${className}Service {

    @Autowired
    private ${className}Mapper ${className?cap_first}Mapper;

<#if config.useSqlBean == false>
    /**
     * 根据id查询
     *
     * @param ${id.name}
     * @return
     */
	@Override
    public ${className} selectById(${id.type} ${id.name}) {
		return ${className?cap_first}Mapper.selectById(${id.name});
    }

    /**
     * 查询全部
     *
     * @return
     */
	@Override
    public List<${className}> selectAll() {
		return ${className?cap_first}Mapper.selectAll();
    }

    /**
     * 新增
     *
     * @param ${className?cap_first}
     * @return
     */
	@Override
    public int insert(${className} ${className?cap_first}) {
		return ${className?cap_first}Mapper.insert(${className?cap_first});
    }

    /**
     * 根据id修改
     *
     * @param ${className?cap_first}
     * @return
     */
	@Override
    public int updateById(${className} ${className?cap_first}) {
		return ${className?cap_first}Mapper.insert(${className?cap_first});
    }

    /**
     * 根据id删除
     *
     * @param ${id.name}
     * @return
     */
	@Override
    public int deleteById(${id.type} ${id.name}) {
		return ${className?cap_first}Mapper.selectById(${id.name});
    }
</#if>

}