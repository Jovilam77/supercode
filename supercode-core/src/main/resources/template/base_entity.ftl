package ${config.basePackage}<#if config.module?? && config.module!=''>.${config.module!}</#if>.model;

<#if config.useSqlBean>
import cn.vonce.sql.annotation.SqlColumn;
import cn.vonce.sql.annotation.SqlDefaultValue;
import cn.vonce.sql.annotation.SqlId;
import cn.vonce.sql.enumerate.FillWith;
import cn.vonce.sql.enumerate.IdType;
</#if>
<#if config.useLombok>
import lombok.Data;
</#if>
import java.util.Date;

/**
* 实体类父类
* @author ${config.author!}<#if config.email?? && config.email!=''>《${config.email!}》</#if>
* @version ${config.version!}《${date?string('yyyy-MM-dd HH:mm:ss')}》
*/<#if config.useLombok>
@Data</#if>
public class BaseEntity {

<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiModelProperty(value = "唯一id")
<#else>
    /**
    * 唯一id
    */
</#if>
    @SqlId(type = IdType.SNOWFLAKE_ID_16)
    @SqlColumn(notNull = true)
    private Long id;
<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiModelProperty(value = "创建者")
<#else>
    /**
    * 创建者
    */
</#if>
    private Long creator;
<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiModelProperty(value = "创建时间")
<#else>
    /**
    * 创建时间
    */
</#if>
    @SqlDefaultValue(with = FillWith.INSERT)
    private Date createTime;
<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiModelProperty(value = "更新者")
<#else>
    /**
    * 更新者
    */
</#if>
    private Long updater;
<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiModelProperty(value = "更新时间")
<#else>
    /**
    * 更新时间
    */
</#if>
    @SqlDefaultValue(with = FillWith.UPDATE)
    private Date updateTime;
<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiModelProperty(value = "是否删除(0正常 1删除)")
<#else>
    /**
    * 是否删除(0正常 1删除)
    */
</#if>
    private Boolean deleted;

}