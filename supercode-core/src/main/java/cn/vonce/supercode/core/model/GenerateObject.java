package cn.vonce.supercode.core.model;

import cn.vonce.supercode.core.config.GenerateConfig;

import java.util.Date;
import java.util.List;

/**
 * 生成对象
 *
 * @author Jovi
 * @version 1.0
 * @email imjovi@qq.com
 * @date 2021-10-12 11:53:22
 */
public class GenerateObject {

    private List<FiledInfo> filedInfoList;
    private FiledInfo id;
    private GenerateConfig config;
    private String className;
    private String tableName;
    private String comm;
    private Date date;

    public List<FiledInfo> getFiledInfoList() {
        return filedInfoList;
    }

    public void setFiledInfoList(List<FiledInfo> filedInfoList) {
        this.filedInfoList = filedInfoList;
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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
