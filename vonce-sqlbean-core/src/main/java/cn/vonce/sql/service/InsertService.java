package cn.vonce.sql.service;

import cn.vonce.sql.bean.Insert;
import java.util.List;

/**
 * Insert 通用业务接口
 *
 * @param <T>
 * @author Jovi
 * @version 1.0
 * @email 766255988@qq.com
 * @date 2019年6月27日下午3:57:33
 */
public interface InsertService<T> {

    /**
     * 插入数据
     *
     * @param bean
     * @return
     */
    @SuppressWarnings("unchecked")
    int insert(T... bean);

    /**
     * 插入数据
     *
     * @param beanList
     * @return
     */
    @SuppressWarnings("unchecked")
    int insert(List<T> beanList);

    /**
     * 插入数据
     *
     * @param insert
     * @return
     */
    int inset(Insert<T> insert);

}
