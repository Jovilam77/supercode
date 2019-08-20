package cn.vonce.sql.config;

import cn.vonce.sql.enumerate.DbType;

import java.io.Serializable;

/**
 * SqlBean配置
 *
 * @author Jovi
 * @version 1.0
 * @email 766255988@qq.com
 * @date 2018年6月12日下午2:48:12
 */
public class SqlBeanConfig implements Serializable {

    public SqlBeanConfig() {

    }

    public SqlBeanConfig(DbType dbType) {
        this.dbType = dbType;
    }

    private DbType dbType;
    private Boolean oracleToUpperCase;

    public DbType getDbType() {
        return dbType;
    }

    public void setDbType(DbType dbType) {
        if (this.dbType == null) {
            this.dbType = dbType;
        }
    }

    public Boolean getOracleToUpperCase() {
        return oracleToUpperCase;
    }

    public void setOracleToUpperCase(Boolean oracleToUpperCase) {
        if (this.oracleToUpperCase == null) {
            this.oracleToUpperCase = oracleToUpperCase;
        }
    }

}