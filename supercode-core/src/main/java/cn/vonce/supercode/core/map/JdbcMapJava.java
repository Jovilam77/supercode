package cn.vonce.supercode.core.map;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Jdbc映射Java类型
 *
 * @author Jovi
 * @version 1.0
 * @email imjovi@qq.com
 * @date 2021-10-12 11:53:22
 */
public class JdbcMapJava {

    private static final Map<String, Class<?>> map = new HashMap<>();

    static {
        //数值类型
        map.put("BIT", Boolean.class);
        map.put("TINYINT", Integer.class);
        map.put("SMALLINT", Integer.class);
        map.put("MEDIUMINT", Integer.class);
        map.put("INT", Integer.class);
        map.put("INTEGER", Integer.class);
        map.put("BIGINT", Long.class);
        map.put("FLOAT", Float.class);
        map.put("REAL", Float.class);
        map.put("DOUBLE", Double.class);
        map.put("DECIMAL", BigDecimal.class);
        map.put("NUMERIC", BigDecimal.class);
        map.put("SMALLMONEY", BigDecimal.class);
        map.put("MONEY", BigDecimal.class);
        //字符串类型
        map.put("CHAR", String.class);
        map.put("NCHAR", String.class);
        map.put("VARCHAR", String.class);
        map.put("NVARCHAR", String.class);
        map.put("TINYBLOB", String.class);
        map.put("TINYTEXT", String.class);
        map.put("BLOB", String.class);
        map.put("TEXT", String.class);
        map.put("NTEXT", String.class);
        map.put("MEDIUMBLOB", String.class);
        map.put("MEDIUMTEXT", String.class);
        map.put("LONGBLOB", String.class);
        map.put("LONGTEXT", String.class);
        //日期类型类型
        map.put("DATE", java.sql.Date.class);
        map.put("TIME", java.sql.Time.class);
        map.put("YEAR", Integer.class);
        map.put("DATETIME", java.util.Date.class);
        map.put("DATETIME2", java.util.Date.class);
        map.put("SMALLDATETIME", java.util.Date.class);
        map.put("TIMESTAMP", java.sql.Timestamp.class);
    }

    /**
     * 通过jdbc类型获取java类型
     *
     * @param jdbcType
     * @return
     */
    public static Class<?> getJavaType(String jdbcType) {
        Class<?> javaType = map.get(jdbcType.toUpperCase());
        if (javaType == null) {
            return Object.class;
        }
        return javaType;
    }

}
