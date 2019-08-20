package cn.vonce.sql.provider;

import cn.vonce.sql.bean.*;
import cn.vonce.sql.constant.SqlHelperCons;
import cn.vonce.sql.enumerate.DbType;
import cn.vonce.sql.enumerate.SqlOperator;
import cn.vonce.sql.exception.SqlBeanException;
import cn.vonce.sql.helper.SqlHelper;
import cn.vonce.sql.uitls.SqlBeanUtil;

import java.lang.reflect.Field;

/**
 * 通用的数据库操作sql语句生成
 *
 * @author Jovi
 * @version 1.0
 * @email 766255988@qq.com
 * @date 2018年5月15日下午2:23:47
 */
public class SqlBeanProvider {

    /**
     * 根据id条件查询
     *
     * @param clazz
     * @param id
     * @return
     * @author Jovi
     * @date 2018年5月15日下午2:22:05
     */
    public String selectByIdSql(Class<?> clazz, Object id) {
        return selectByIdsSql(clazz, new Object[]{id});
    }

    /**
     * 根据ids条件查询
     *
     * @param clazz
     * @param ids
     * @return
     * @author Jovi
     * @date 2018年5月15日下午2:22:05
     */
    public String selectByIdsSql(Class<?> clazz, Object[] ids) {
        Select select;
        Field idField;
        try {
            select = newSelect(clazz, false);
            idField = SqlBeanUtil.getIdField(clazz);
        } catch (SqlBeanException e) {
            e.printStackTrace();
            return null;
        }
        if (ids.length > 1) {
            select.where(SqlBeanUtil.getFieldFullName(clazz, idField), ids, SqlOperator.IN);
        } else {
            select.where(SqlBeanUtil.getFieldFullName(clazz, idField), ids[0]);
        }
        return SqlHelper.buildSelectSql(select);
    }

    /**
     * 根据条件查询
     *
     * @param clazz
     * @param paging
     * @param where
     * @param args
     * @return
     * @author Jovi
     * @date 2018年5月15日下午2:21:33
     */
    public String selectByConditionSql(Class<?> clazz, Paging paging, String where, Object... args) {
        Select select = newSelect(clazz, false);
        select.setWhere(SqlBeanUtil.getWhere(where, args));
        setPaging(select, paging, clazz);
        return SqlHelper.buildSelectSql(select);
    }

    /**
     * 根据条件查询统计
     *
     * @param clazz
     * @param where
     * @param args
     * @return
     * @author Jovi
     * @date 2018年7月5日下午4:09:45
     */
    public String selectCountByConditionSql(Class<?> clazz, String where, Object[] args) {
        Select select = newSelect(clazz, true);
        select.setWhere(SqlBeanUtil.getWhere(where, args));
        return SqlHelper.buildSelectSql(select);
    }

    /**
     * 查询全部
     *
     * @param clazz
     * @return
     * @author Jovi
     * @date 2018年5月15日下午2:21:27
     */
    public String selectAllSql(Class<?> clazz, Paging paging) {
        Select select = newSelect(clazz, false);
        setPaging(select, paging, clazz);
        return SqlHelper.buildSelectSql(select);
    }

    /**
     * 根据自定义条件查询（可自动分页）
     *
     * @param clazz
     * @param select
     * @return
     * @author Jovi
     * @date 2018年5月15日下午2:21:05
     */
    public String selectSql(Class<?> clazz, Select select) {
        if (!select.isCustomMode() || select == null || select.getColumn().isEmpty()) {
            select.setColumn(SqlBeanUtil.getSelectFields(clazz, select.getFilterFields()));
            if (select.getPage() != null) {
                try {
                    select.getPage().setIdName(SqlBeanUtil.getFieldName(SqlBeanUtil.getIdField(clazz)));
                } catch (SqlBeanException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return setSelectAndBuild(clazz, select);
    }

    /**
     * 根据自定义条件统计
     *
     * @param clazz
     * @param select
     * @return
     * @author Jovi
     * @date 2018年5月15日下午2:20:22
     */
    public String countSql(Class<?> clazz, Select select) {
        if (select == null || select.getColumn().isEmpty()) {
            select.column(SqlHelperCons.COUNT + SqlHelperCons.BEGIN_BRACKET + SqlHelperCons.ALL + SqlHelperCons.END_BRACKET);
        }
        return setSelectAndBuild(clazz, select);
    }

    /**
     * 根据id条件删除
     *
     * @param clazz
     * @param id
     * @return
     * @author Jovi
     * @date 2018年5月15日下午2:20:10
     */
    public String deleteByIdSql(Class<?> clazz, Object id) {
        Delete delete = new Delete();
        delete.setDeleteBable(clazz);
        Field idField;
        try {
            idField = SqlBeanUtil.getIdField(clazz);
        } catch (SqlBeanException e) {
            e.printStackTrace();
            return null;
        }
        delete.where(SqlBeanUtil.getFieldName(idField), id, SqlOperator.IN);
        return SqlHelper.buildDeleteSql(delete);
    }

    /**
     * 根据条件删除
     *
     * @param clazz
     * @param where
     * @param args
     * @return
     * @author Jovi
     * @date 2018年5月15日下午2:19:59
     */
    public String deleteByConditionSql(Class<?> clazz, String where, Object[] args) {
        Delete delete = new Delete();
        delete.setDeleteBable(clazz);
        delete.setWhere(SqlBeanUtil.getWhere(where, args));
        return SqlHelper.buildDeleteSql(delete);
    }

    /**
     * 删除
     *
     * @param clazz
     * @param delete
     * @param ignore
     * @return
     * @author Jovi
     * @date 2019年1月12日下午2:19:59
     */
    public String deleteSql(Class<?> clazz, Delete delete, boolean ignore) {
        if (delete.getDeleteBable() == null || delete.getDeleteBable().equals("")) {
            delete.setDeleteBable(clazz);
        }
        if (ignore || !delete.getWhereMap().isEmpty()) {
            return SqlHelper.buildDeleteSql(delete);
        } else {
            try {
                throw new SqlBeanException("该delete sql未设置where条件，如果确实不需要where条件，请使用delete(Select select, boolean ignore)");
            } catch (SqlBeanException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 逻辑删除
     *
     * @param clazz
     * @param id
     * @return
     * @author Jovi
     * @date 2019年6月12日下午2:19:59
     */
    public String logicallyDeleteByIdSql(Class<?> clazz, Object id) {
        Update update = new Update();
        Object bean;
        try {
            bean = newLogicallyDeleteBean(clazz);
            update.setUpdateBean(bean);
            Field idField = SqlBeanUtil.getIdField(bean.getClass());
            idField.setAccessible(true);
            update.where(SqlBeanUtil.getFieldName(idField), id);
            //sqlBean.where(SqlBeanUtil.getFieldName(idField), ReflectAsmUtil.get(bean.getClass(), bean, idField.getName()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (SqlBeanException e) {
            e.printStackTrace();
            return null;
        }
        return SqlHelper.buildUpdateSql(update);
    }

    /**
     * 逻辑删除
     *
     * @param clazz
     * @param where
     * @param args
     * @return
     * @author Jovi
     * @date 2019年6月12日下午2:19:59
     */
    public String logicallyDeleteByConditionSql(Class<?> clazz, String where, Object[] args) {
        Update update = new Update();
        try {
            update.setUpdateBean(newLogicallyDeleteBean(clazz));
            update.setWhere(SqlBeanUtil.getWhere(where, args));
            //sqlBean.where(SqlBeanUtil.getFieldName(idField), ReflectAsmUtil.get(bean.getClass(), bean, idField.getName()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (SqlBeanException e) {
            e.printStackTrace();
            return null;
        }
        return SqlHelper.buildUpdateSql(update);
    }

    /**
     * 更新
     *
     * @param update
     * @param ignore
     * @return
     * @author Jovi
     * @date 2019年1月12日下午4:16:24
     */
    public String updateSql(Update update, boolean ignore) {
        if (ignore || !update.getWhereMap().isEmpty()) {
            return SqlHelper.buildUpdateSql(update);
        } else {
            try {
                throw new SqlBeanException("该update sql未设置where条件，如果确实不需要where条件，请使用update(Select select, boolean ignore)");
            } catch (SqlBeanException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 根据实体类id条件更新
     *
     * @param bean
     * @param updateNotNull
     * @return
     * @author Jovi
     * @date 2018年5月15日下午2:19:24
     */
    public String updateByIdSql(Object bean, Object id, boolean updateNotNull, String[] filterFields) {
        Update update = newUpdate(bean, updateNotNull);
        update.setFilterFields(filterFields);
        Field idField;
        try {
            idField = SqlBeanUtil.getIdField(bean.getClass());
        } catch (SqlBeanException e) {
            e.printStackTrace();
            return null;
        }
        update.where(SqlBeanUtil.getFieldName(idField), id);
        //sqlBean.where(SqlBeanUtil.getFieldName(idField), id);
        return SqlHelper.buildUpdateSql(update);
    }

    /**
     * 根据实体类id条件更新
     *
     * @param bean
     * @param updateNotNull
     * @return
     * @author Jovi
     * @date 2018年5月15日下午2:19:24
     */
    public String updateByBeanIdSql(Object bean, boolean updateNotNull, String[] filterFields) {
        Update update = newUpdate(bean, updateNotNull);
        update.setFilterFields(filterFields);
        Field idField;
        try {
            idField = SqlBeanUtil.getIdField(bean.getClass());
            idField.setAccessible(true);
            update.where(SqlBeanUtil.getFieldName(idField), idField.get(bean));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (SqlBeanException e) {
            e.printStackTrace();
            return null;
        }
        //sqlBean.where(SqlBeanUtil.getFieldName(idField), ReflectAsmUtil.get(bean.getClass(), bean, idField.getName()));
        return SqlHelper.buildUpdateSql(update);
    }

    /**
     * 根据条件更新
     *
     * @param bean
     * @param updateNotNull
     * @param where
     * @param args
     * @return
     * @author Jovi
     * @date 2018年5月15日下午2:18:03
     */
    public String updateByConditionSql(Object bean, boolean updateNotNull, String[] filterFields, String where, Object[] args) {
        Update update = newUpdate(bean, updateNotNull);
        update.setFilterFields(filterFields);
        update.setWhere(SqlBeanUtil.getWhere(where, args));
        return SqlHelper.buildUpdateSql(update);
    }

    /**
     * 根据实体类字段条件更新
     *
     * @param bean
     * @param updateNotNull
     * @param where
     * @return
     * @author Jovi
     * @date 2018年5月15日下午2:16:36
     */
    public String updateByBeanConditionSql(Object bean, boolean updateNotNull, String[] filterFields, String where) {
        Update update = newUpdate(bean, updateNotNull);
        update.setFilterFields(filterFields);
        try {
            update.setWhere(SqlBeanUtil.getWhere(where, bean));
        } catch (NoSuchFieldException e) {
            try {
                throw new SqlBeanException("bean找不到该字段：" + e.getMessage());
            } catch (SqlBeanException e1) {
                e1.printStackTrace();
            }
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        return SqlHelper.buildUpdateSql(update);
    }

    /**
     * 插入数据
     *
     * @param bean
     * @return
     * @author Jovi
     * @date 2018年5月15日下午2:16:30
     */
    public String insertBeanSql(Object bean) {
        Insert insert = new Insert();
        insert.setInsertBean(bean);
        return SqlHelper.buildInsertSql(insert);
    }

    /**
     * 插入数据
     *
     * @param insert
     * @return
     * @author Jovi
     * @date 2018年5月15日下午2:16:30
     */
    public String insertSql(Insert insert) {
        return SqlHelper.buildInsertSql(insert);
    }

    /**
     * 实例化Select
     *
     * @param clazz
     * @return
     * @throws SqlBeanException
     */
    private Select newSelect(Class<?> clazz, boolean isCount) {
        Select select = new Select();
        if (isCount) {
            select.column(SqlHelperCons.COUNT + SqlHelperCons.BEGIN_BRACKET + SqlHelperCons.ALL + SqlHelperCons.END_BRACKET);
        } else {
            select.setColumn(SqlBeanUtil.getSelectFields(clazz, select.getFilterFields()));
        }
        select.setFrom(clazz);
        try {
            SqlBeanUtil.setJoin(select, clazz);
        } catch (SqlBeanException e) {
            e.printStackTrace();
            return null;
        }
        return select;
    }

    /**
     * 设置SqlBean中的Select 并生成select sql
     *
     * @param clazz
     * @param select
     * @return
     * @throws SqlBeanException
     */
    private String setSelectAndBuild(Class<?> clazz, Select select) {
        if (select.getFrom() == null || select.getFrom().equals("")) {
            select.setFrom(clazz);
            try {
                SqlBeanUtil.setJoin(select, clazz);
            } catch (SqlBeanException e) {
                e.printStackTrace();
                return null;
            }
        }
        return SqlHelper.buildSelectSql(select);
    }

    /**
     * 逻辑删除需要的对象
     *
     * @param clazz
     * @return
     * @throws IllegalAccessException
     */
    private Object newLogicallyDeleteBean(Class<?> clazz) throws IllegalAccessException, InstantiationException, SqlBeanException {
        Object bean = clazz.newInstance();
        Field field = SqlBeanUtil.getLogicallyField(clazz);
        field.setAccessible(true);
        field.setBoolean(bean, true);
        return bean;
    }

    /**
     * 实例化Select
     *
     * @param bean
     * @param updateNotNull
     * @return
     * @throws SqlBeanException
     */
    private Update newUpdate(Object bean, boolean updateNotNull) {
        Update update = new Update();
        update.setUpdateBean(bean);
        update.setUpdateNotNull(updateNotNull);
        return update;
    }

    /**
     * 设置分页参数
     *
     * @param select
     * @param paging
     */
    private void setPaging(Select select, Paging paging, Class<?> clazz) {
        if (paging != null) {
            if (SqlHelper.getSqlBeanConfig().getDbType() == DbType.SQLServer2008) {
                try {
                    select.setPage(SqlBeanUtil.getFieldName(SqlBeanUtil.getIdField(clazz)), paging.getPagenum(), paging.getPagesize());
                } catch (SqlBeanException e) {
                    e.printStackTrace();
                }
            } else {
                select.setPage(null, paging.getPagenum(), paging.getPagesize());
            }
            if (paging.getSortdatafield() != null && paging.getSortorder() != null && paging.getSortdatafield().length > 0 && paging.getSortdatafield().length == paging.getSortorder().length) {
                for (int i = 0; i < paging.getSortdatafield().length; i++) {
                    select.orderBy(paging.getSortdatafield()[i], paging.getSortorder()[i]);
                }
            }
        }
    }

}