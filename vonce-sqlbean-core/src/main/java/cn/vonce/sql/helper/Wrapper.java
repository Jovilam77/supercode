package cn.vonce.sql.helper;

import cn.vonce.sql.enumerate.SqlLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * 条件包装器
 *
 * @author Jovi
 * @version 1.0
 * @email 766255988@qq.com
 * @date 2021年4月28日下午5:49:00
 */
public class Wrapper {

    private List<Model> modelList = new ArrayList<>();

    /**
     * 条件
     *
     * @param cond
     * @return
     */
    public static Wrapper cond(Cond cond) {
        Wrapper wrapper = new Wrapper();
        wrapper.modelList.add(new Model(SqlLogic.AND, cond));
        return wrapper;
    }

    /**
     * 并且
     *
     * @param wrapper
     * @return
     */
    public Wrapper and(Wrapper wrapper) {
        modelList.add(new Model(SqlLogic.AND, wrapper));
        return this;
    }

    /**
     * 并且
     *
     * @param cond
     * @return
     */
    public Wrapper and(Cond cond) {
        modelList.add(new Model(SqlLogic.AND, cond));
        return this;
    }

    /**
     * 或者
     *
     * @param wrapper
     * @return
     */
    public Wrapper or(Wrapper wrapper) {
        modelList.add(new Model(SqlLogic.OR, wrapper));
        return this;
    }

    /**
     * 或者
     *
     * @param cond
     * @return
     */
    public Wrapper or(Cond cond) {
        modelList.add(new Model(SqlLogic.OR, cond));
        return this;
    }

    /**
     * 获得条件模型列表
     *
     * @return
     */
    public List<Model> getModelList() {
        return this.modelList;
    }

    /**
     * 条件模型
     */
    public static class Model {
        private SqlLogic sqlLogic;
        private Object item;

        public Model() {
        }

        public Model(SqlLogic sqlLogic, Object item) {
            this.sqlLogic = sqlLogic;
            this.item = item;
        }

        public SqlLogic getSqlLogic() {
            return sqlLogic;
        }

        public void setSqlLogic(SqlLogic sqlLogic) {
            this.sqlLogic = sqlLogic;
        }

        public Object getItem() {
            return item;
        }

        public void setItem(Object item) {
            this.item = item;
        }
    }

}