package cn.vonce.supercode.core.model;

import cn.vonce.sql.bean.TableInfo;
import cn.vonce.supercode.core.config.GenerateConfig;

import java.util.Date;
import java.util.HashSet;
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
    private List<FiledInfo> filedInfoList;
    private Set<String> otherTypeSet;
    private FiledInfo id;
    private GenerateConfig config;
    private String className;
    private Date date;

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public List<FiledInfo> getFiledInfoList() {
        return filedInfoList;
    }

    public void setFiledInfoList(List<FiledInfo> filedInfoList) {
        this.filedInfoList = filedInfoList;
    }

    public Set<String> getOtherTypeSet() {
        return otherTypeSet;
    }

    public void setOtherTypeSet(Set<String> otherTypeSet) {
        this.otherTypeSet = otherTypeSet;
    }

    public FiledInfo getId() {
        return id;
    }

    public void setId(FiledInfo id) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
