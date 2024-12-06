package ${config.basePackage}<#if config.module?? && config.module!=''>.${config.module!}</#if>.model.dto;

<#if config.getJavaDocType().name() == 'Swagger'>
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if config.useLombok>
import lombok.Data;
</#if>
<#list otherTypeMap?keys as key>
<#if otherTypeMap[key]>
import ${key};
</#if>
</#list>

/**
 * ${tableInfo.remarks!} 新增Dto
 *
 * @author ${config.author!}<#if config.email?? && config.email!=''>《${config.email!}》</#if>
 * @version ${config.version!}《${date?string('yyyy-MM-dd HH:mm:ss')}》
 */<#if config.useLombok>
@Data</#if>
public class ${className}CreateDto {

<#list fieldInfoList as filedInfo>
<#if !filedInfo.ignore>
<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiModelProperty(value = "${filedInfo.columnInfo.remarks!}")
<#else>
    /**
     * ${filedInfo.columnInfo.remarks!}
     */
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