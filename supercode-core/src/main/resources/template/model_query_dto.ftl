package ${config.basePackage}<#if config.module?? && config.module!=''>.${config.module!}</#if>.model.dto;

<#if config.getJavaDocType().name() == 'Swagger'>
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if config.useLombok>
import lombok.Data;
</#if>

/**
 * ${tableInfo.remarks!} 查询Dto
 *
 * @author ${config.author!}<#if config.version?? && config.version!=''>
 * @version ${config.version!}</#if><#if config.email?? && config.email!=''>
 * @email ${config.email!}</#if>
 * @date ${date?string('yyyy-MM-dd HH:mm:ss')}
 */<#if config.useLombok>
@Data</#if>
public class ${className}QueryDto {


}