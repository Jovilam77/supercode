package cn.vonce.sql.annotation;

import java.lang.annotation.*;

/**
 * 标识Bean 实体类的字段与数据库中对应的字段名
 *
 * @author Jovi
 * @version 1.0
 * @email 766255988@qq.com
 * @date 2018年2月8日下午2:55:04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface SqlBeanField {

    /**
     * 字段名称
     *
     * @return
     */
    String value();

    /**
     * 是否为id
     *
     * @return
     */
    boolean id() default false;

    /**
     * 是否忽略该字段
     *
     * @return
     */
    boolean ignore() default false;

    /**
     * 是否为一个实体类
     *
     * @return
     */
    boolean isBean() default false;

    /**
     * 是否为逻辑删除字段
     *
     * @return
     */
    boolean logically() default false;

    /**
     * 连表查询
     * @return
     */
    SqlBeanJoin join() default @SqlBeanJoin();

}