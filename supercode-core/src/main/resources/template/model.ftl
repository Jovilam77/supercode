package ${config.basePackage}.model;

<#if config.useSqlBean == true>
import cn.vonce.sql.annotation.SqlColumn;
import cn.vonce.sql.annotation.SqlId;
import cn.vonce.sql.annotation.SqlTable;
</#if>
<#if config.useLombok == true>
import lombok.Data;
</#if>
import java.util.Date;

/**
 * ${tableInfo.comment} 实体类
 *
 * @author ${config.author}
 * @version ${config.version}
 * @email ${config.email}
 * @date ${date}
 */
<#if config.useLombok == true>@Data</#if>
<#if config.useSqlBean == true>@SqlTable("${tableInfo.name}")</#if>
public class ${className} {

<#list filedInfoList as filedInfo>
    /**
     * ${filedInfo.columnInfo.comm}
     */
    <#if config.useSqlBean == true && filedInfo.columnInfo.pk == true>@SqlId</#if>
    <#if config.useSqlBean == true>@SqlColumn(value = "${filedInfo.columnInfo.name}")</#if>
    private ${filedInfo.typeName} ${filedInfo.name};
</#list>

<#if config.useLombok == false>
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