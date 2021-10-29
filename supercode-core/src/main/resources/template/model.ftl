package ${config.basePackage}.model;

<#if config.useSqlBean>
import cn.vonce.sql.annotation.SqlColumn;
import cn.vonce.sql.annotation.SqlId;
import cn.vonce.sql.annotation.SqlTable;
</#if>
<#if config.useLombok>
import lombok.Data;
</#if>
<#list otherTypeSet as otherType>
import ${otherType};
</#list>

/**
 * ${tableInfo.comment!} 实体类
 *
 * @author ${config.author!}
 * @version ${config.version!}
 * @email ${config.email!}
 * @date ${date?string('yyyy-MM-dd HH:mm:ss')}
 */<#if config.useLombok>
@Data</#if><#if config.useSqlBean>
@SqlTable("${tableInfo.name}")</#if>
public class ${className} {

<#list filedInfoList as filedInfo>
    /**
     * ${filedInfo.columnInfo.comment!}
     */<#if config.useSqlBean && filedInfo.columnInfo.pk>
    @SqlId</#if><#if config.useSqlBean>
    @SqlColumn(value = "${filedInfo.columnInfo.name}")</#if>
    private ${filedInfo.typeName} ${filedInfo.name};
</#list>

<#if !config.useLombok>
<#list filedInfoList as filedInfo>
    public ${filedInfo.typeName} get${filedInfo.name?cap_first}() {
        return ${filedInfo.name};
    }

    public void set${filedInfo.name?cap_first}(${filedInfo.typeName} ${filedInfo.name}) {
        this.${filedInfo.name} = ${filedInfo.name};
    }
</#list>
</#if>

}