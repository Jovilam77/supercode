package cn.vonce.supercode.core;

import cn.vonce.sql.bean.Select;
import cn.vonce.sql.config.SqlBeanConfig;
import cn.vonce.sql.config.SqlBeanMeta;
import cn.vonce.sql.enumerate.DbType;
import cn.vonce.sql.helper.SqlHelper;

/**
 * @author Jovi
 * @email imjovi@qq.com
 * @date 2024/9/21 1:32
 */
public class Test {

    public static void main(String[] args) {
        SqlBeanMeta sqlBeanDB = new SqlBeanMeta();
        sqlBeanDB.setDbType(DbType.MySQL);
        sqlBeanDB.setSqlBeanConfig(new SqlBeanConfig());
        Select select = new Select();
        select.setSqlBeanMeta(sqlBeanDB);
        select.column("*");
        select.table("user");
        select.where().eq("id",1);
        System.out.println(SqlHelper.buildSelectSql(select));
    }

}
