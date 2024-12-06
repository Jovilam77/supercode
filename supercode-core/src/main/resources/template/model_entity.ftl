package ${config.basePackage}<#if config.module?? && config.module!=''>.${config.module!}</#if>.model.entity;

<#if config.useSqlBean>
import cn.vonce.sql.annotation.SqlId;
import cn.vonce.sql.annotation.SqlTable;
import cn.vonce.sql.annotation.SqlColumn;
</#if>
<#if config.getJavaDocType().name() == 'Swagger'>
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if config.useLombok>
import lombok.Data;
</#if>
<#list otherTypeMap?keys as key>
import ${key};
</#list>

/**
 * ${tableInfo.remarks!} 实体类
 *
 * @author ${config.author!}<#if config.email?? && config.email!=''>《${config.email!}》</#if>
 * @version ${config.version!}《${date?string('yyyy-MM-dd HH:mm:ss')}》
 */<#if config.useLombok>
@Data</#if><#if config.useSqlBean>
@SqlTable(value = "${tableInfo.name}", autoAlter = true, remarks = "${tableInfo.remarks}")</#if>
public class ${className}<#if baseClassName?? && baseClassName!=''> extends ${baseClassName!}</#if> {

<#list fieldInfoList as filedInfo>
<#if !filedInfo.ignore>
<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiModelProperty(value = "${filedInfo.columnInfo.remarks!}")
<#else>
    /**
     * ${filedInfo.columnInfo.remarks!}
     */
</#if><#if config.useSqlBean><#if filedInfo.columnInfo.pk>
    @SqlId<#if filedInfo.typeName == 'Long'>(type = IdType.SNOWFLAKE_ID_18)<#elseif filedInfo.typeName == 'String'>(type = IdType.UUID)</#if>
</#if><#if filedInfo.createTime>
    @SqlDefaultValue(with = FillWith.INSERT)
</#if><#if filedInfo.updateTime>
    @SqlDefaultValue(with = FillWith.UPDATE)
</#if>
    @SqlColumn(<#if filedInfo.columnInfo.notnull?? && filedInfo.columnInfo.notnull>notNull = ${filedInfo.columnInfo.notnull?c}, </#if>remarks = "${filedInfo.columnInfo.remarks}")
</#if>
    private ${filedInfo.typeName} ${filedInfo.name};
</#if>
</#list>

<#if !config.useLombok>
<#list fieldInfoList as filedInfo>
    public ${filedInfo.typeName} get${filedInfo.name?cap_first}() {
        return ${filedInfo.name};
    }

    public void set${filedInfo.name?cap_first}(${filedInfo.typeName} ${filedInfo.name}) {
        this.${filedInfo.name} = ${filedInfo.name};
    }

</#list>
</#if>
}