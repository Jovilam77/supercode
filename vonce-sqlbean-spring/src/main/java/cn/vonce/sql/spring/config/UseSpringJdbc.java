package cn.vonce.sql.spring.config;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用自动配置
 *
 * @author Jovi
 * @version 1.0
 * @email 766255988@qq.com
 * @date 2019年6月25日下午12:7:50
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({AutoCreateTableListener.class})
public @interface UseSpringJdbc {


}
