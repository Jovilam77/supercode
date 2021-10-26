package cn.vonce.supercode.core.model;

import cn.vonce.sql.bean.ColumnInfo;

/**
 * Java字段信息
 *
 * @author Jovi
 * @version 1.0
 * @email imjovi@qq.com
 * @date 2021-10-13 17:06:25
 */
public class FiledInfo {

    private String name;
    private String typeName;
    private String typeFullName;
    private ColumnInfo columnInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeFullName() {
        return typeFullName;
    }

    public void setTypeFullName(String typeFullName) {
        this.typeFullName = typeFullName;
    }

    public ColumnInfo getColumnInfo() {
        return columnInfo;
    }

    public void setColumnInfo(ColumnInfo columnInfo) {
        this.columnInfo = columnInfo;
    }
}
