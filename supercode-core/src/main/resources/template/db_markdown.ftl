#### 数据库表文档说明
###### 作者：${config.author!}
###### 日期：${date?string('yyyy-MM-dd HH:mm:ss')}
```
${tableInfo.name!} <#if tableInfo.remarks?? && tableInfo.remarks!=''>(${tableInfo.remarks!})</#if>
```
列名  | 类型  | 可为空 | 默认值 | 主键 | 外键 | 长度 | 精度 | 注释
 :----: | :-----: | :-----: | :------: | :------: | :------: | :------:  | :------: | :------: 
 <#list fieldInfoList as filedInfo>
 ${filedInfo.columnInfo.name!} | ${filedInfo.columnInfo.type!} | <#if filedInfo.columnInfo.notnull?? && filedInfo.columnInfo.notnull><#else>是</#if> | ${filedInfo.columnInfo.dfltValue!} | <#if filedInfo.columnInfo.pk?? && filedInfo.columnInfo.pk>是<#else></#if>  | <#if (filedInfo.columnInfo.fk)??>${filedInfo.columnInfo.fk?c}</#if> | ${filedInfo.columnInfo.length!} | ${filedInfo.columnInfo.scale!} | ${filedInfo.columnInfo.remarks!}
 </#list>