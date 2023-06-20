package cn.vonce.supercode.core.model;

import cn.vonce.sql.bean.TableInfo;
import cn.vonce.supercode.core.config.GenerateConfig;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 生成对象
 *
 * @author Jovi
 * @version 1.0
 * @email imjovi@qq.com
 * @date 2021-10-12 11:53:22
 */
public class ClassInfo {

    private TableInfo tableInfo;
    private List<FieldInfo> fieldInfoList;
    private Set<String> otherTypeSet;
    private FieldInfo id;
    private GenerateConfig config;
    private String className;
    private String baseClassName;
    private Date date;
    private String sql;

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public List<FieldInfo> getFieldInfoList() {
        return fieldInfoList;
    }

    public void setFieldInfoList(List<FieldInfo> fieldInfoList) {
        this.fieldInfoList = fieldInfoList;
    }

    public Set<String> getOtherTypeSet() {
        return otherTypeSet;
    }

    public void setOtherTypeSet(Set<String> otherTypeSet) {
        this.otherTypeSet = otherTypeSet;
    }

    public FieldInfo getId() {
        return id;
    }

    public void setId(FieldInfo id) {
        this.id = id;
    }

    public GenerateConfig getConfig() {
        return config;
    }

    public void setConfig(GenerateConfig config) {
        this.config = config;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getBaseClassName() {
        return baseClassName;
    }

    public void setBaseClassName(String baseClassName) {
        this.baseClassName = baseClassName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

}
