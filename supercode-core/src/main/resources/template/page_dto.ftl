package ${config.basePackage}<#if config.module?? && config.module!=''>.${config.module!}</#if>.model;

<#if config.useLombok>
import lombok.Data;
</#if>

/**
* 分页Dto
* @author ${config.author!}<#if config.email?? && config.email!=''>《${config.email!}》</#if>
* @version ${config.version!}《${date?string('yyyy-MM-dd HH:mm:ss')}》
*/
@Data
public class PageDto {

    /**
    * 页码
    */
    private Integer pageNum;
    /**
    * 每页数量
    */
    private Integer pageSize;

}