#### 数据库表文档说明
```
${tableInfo.name!} <#if tableInfo.comm??>(${tableInfo.comm!})</#if>
```
列名  | 类型  | 可为空 | 默认值 | 主键 | 外键 | 长度 | 精度 | 注释
 :----: | :-----: | :-----: | :------: | :------: | :------: | :------:  | :------: | :------: 
 <#list filedInfoList as filedInfo>
 ${filedInfo.columnInfo.name!} | ${filedInfo.columnInfo.type!} | ${filedInfo.columnInfo.notnull?c} | ${filedInfo.columnInfo.dfltValue!} | ${filedInfo.columnInfo.pk?c}  | <#if (filedInfo.columnInfo.fk)??>${filedInfo.columnInfo.fk!c}</#if> | ${filedInfo.columnInfo.length!} | ${filedInfo.columnInfo.scale!} | ${filedInfo.columnInfo.comm!}
 </#list>