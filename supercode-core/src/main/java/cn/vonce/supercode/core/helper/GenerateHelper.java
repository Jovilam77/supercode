package cn.vonce.supercode.core.helper;

import cn.vonce.sql.annotation.SqlColumn;
import cn.vonce.sql.annotation.SqlDefaultValue;
import cn.vonce.sql.annotation.SqlTable;
import cn.vonce.sql.bean.ColumnInfo;
import cn.vonce.sql.bean.Table;
import cn.vonce.sql.bean.TableInfo;
import cn.vonce.sql.config.SqlBeanConfig;
import cn.vonce.sql.config.SqlBeanDB;
import cn.vonce.sql.enumerate.DbType;
import cn.vonce.sql.enumerate.FillWith;
import cn.vonce.sql.enumerate.IdType;
import cn.vonce.sql.service.TableService;
import cn.vonce.sql.uitls.DateUtil;
import cn.vonce.sql.uitls.SqlBeanUtil;
import cn.vonce.sql.uitls.StringUtil;
import cn.vonce.supercode.core.config.GenerateConfig;
import cn.vonce.supercode.core.enumeration.JdbcDocType;
import cn.vonce.supercode.core.enumeration.TemplateType;
import cn.vonce.supercode.core.map.JdbcMapJava;
import cn.vonce.supercode.core.model.FiledInfo;
import cn.vonce.supercode.core.model.ClassInfo;
import cn.vonce.supercode.core.enumeration.JdbcDaoType;
import cn.vonce.supercode.core.util.FreemarkerUtil;
import freemarker.template.Configuration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.*;

/**
 * 生成助手
 *
 * @author Jovi
 * @version 1.0
 * @email imjovi@qq.com
 * @date 2021-10-26 20:01:11
 */
public class GenerateHelper {

    /**
     * 通过数据库表 构建生成（全部）
     *
     * @param config       生成信息配置
     * @param tableService 数据库连接实现类
     * @throws IOException
     */
    public static void build(GenerateConfig config, TableService tableService) {
        List<TableInfo> tableInfoList = getTableInfoList(tableService);
        Map<String, String> filePaths = getFilePaths(config);
        ExecutorService pool = new ThreadPoolExecutor(3, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        int num = 10;
        int size = tableInfoList.size() % num == 0 ? tableInfoList.size() / num : tableInfoList.size() / num + 1;
        for (int i = 0; i < size; i++) {
            int finalI = i;
            pool.execute(() -> {
                List<ClassInfo> classInfoList = getClassInfoList(config, tableInfoList.subList(num * finalI, finalI == size - 1 ? tableInfoList.size() : num * finalI + num), tableService);
                try {
                    make(config, filePaths, classInfoList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        pool.shutdown();
    }

    /**
     * 通过数据库表 构建生成（单表）
     *
     * @param config         生成信息配置
     * @param tableInfo      表信息
     * @param columnInfoList 列信息列表
     */
    public static void build(GenerateConfig config, TableInfo tableInfo, List<ColumnInfo> columnInfoList) {
        Map<String, String> filePaths = getFilePaths(config);
        List<ClassInfo> classInfoList = new ArrayList<>();
        classInfoList.add(getClassInfo(config, tableInfo, columnInfoList));
        try {
            make(config, filePaths, classInfoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过实体类 构建生成（单表）
     *
     * @param config 生成信息配置
     * @param dbType 数据库类型
     * @param bean   实体类
     */
    public static void build(GenerateConfig config, DbType dbType, Class<?> bean) {
        build(config, dbType, false, bean);
    }

    /**
     * 通过实体类 构建生成（单表）
     *
     * @param config         生成信息配置
     * @param dbType         数据库类型
     * @param sqlToUpperCase SQL语句是否转大写
     * @param bean           实体类
     */
    public static void build(GenerateConfig config, DbType dbType, boolean sqlToUpperCase, Class<?> bean) {
        SqlBeanDB sqlBeanDB = new SqlBeanDB();
        sqlBeanDB.setDbType(dbType);
        SqlBeanConfig sqlBeanConfig = new SqlBeanConfig();
        sqlBeanConfig.setToUpperCase(sqlToUpperCase);
        sqlBeanDB.setSqlBeanConfig(sqlBeanConfig);
        Map<String, String> filePaths = getFilePaths(config);
        Table table = SqlBeanUtil.getTable(bean);
        SqlTable sqlTable = SqlBeanUtil.getSqlTable(bean);
        TableInfo tableInfo = new TableInfo();
        tableInfo.setName(table.getName());
        tableInfo.setRemarks(sqlTable.remarks());
        List<Field> fieldList = SqlBeanUtil.getBeanAllField(bean);
        List<ColumnInfo> columnInfoList = new ArrayList<>();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            if (SqlBeanUtil.isIgnore(field)) {
                continue;
            }
            columnInfoList.add(SqlBeanUtil.getColumnInfo(sqlBeanDB, field, sqlTable, field.getAnnotation(SqlColumn.class)));
        }
        List<ClassInfo> classInfoList = new ArrayList<>();
        classInfoList.add(getClassInfo(config, tableInfo, columnInfoList));
        try {
            make(config, filePaths, classInfoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取要生成的数据表信息
     *
     * @param tableService
     * @return
     */
    public static List<TableInfo> getTableInfoList(TableService tableService) {
        List<TableInfo> tableInfoList = tableService.getTableList(null);
        if (tableInfoList == null || tableInfoList.isEmpty()) {
            return null;
        }
        return tableInfoList;
    }

    /**
     * 获取生成所需的对象列表
     *
     * @param config
     * @param tableInfoList
     * @param tableService
     * @return
     */
    public static List<ClassInfo> getClassInfoList(GenerateConfig config, List<TableInfo> tableInfoList, TableService tableService) {
        List<ClassInfo> classInfoList = new ArrayList<>();
        for (TableInfo tableInfo : tableInfoList) {
            List<ColumnInfo> columnInfoList = tableService.getColumnInfoList(tableInfo.getName());
            classInfoList.add(getClassInfo(config, tableInfo, columnInfoList));
        }
        return classInfoList;
    }

    /**
     * 获取生成所需的对象列表
     *
     * @param config
     * @param tableInfo
     * @param columnInfoList
     * @return
     */
    public static ClassInfo getClassInfo(GenerateConfig config, TableInfo tableInfo, List<ColumnInfo> columnInfoList) {
        Date date = new Date();
        ClassInfo classInfo = new ClassInfo();
        classInfo.setConfig(config);
        String newTableName;
        if (config.isBePrefix() && StringUtil.isNotEmpty(config.getPrefix()) && tableInfo.getName().indexOf(config.getPrefix()) == 0) {
            newTableName = tableInfo.getName().substring(config.getPrefix().length());
        } else if (config.isBePrefix()) {
            newTableName = tableInfo.getName().substring(tableInfo.getName().indexOf("_") + 1);
        } else {
            newTableName = tableInfo.getName();
        }
        classInfo.setClassName(newTableName.substring(0, 1).toUpperCase() + StringUtil.underlineToHump(newTableName.substring(1)));
        classInfo.setTableInfo(tableInfo);
        classInfo.setDate(date);
        if (columnInfoList != null && !columnInfoList.isEmpty()) {
            List<FiledInfo> filedInfoList = new ArrayList<>();
            Set<String> otherTypeSet = new HashSet<>();
            FiledInfo filedInfo;
            for (ColumnInfo columnInfo : columnInfoList) {
                Class<?> clazz = JdbcMapJava.getJavaType(columnInfo.getType());
                filedInfo = new FiledInfo();
                filedInfo.setColumnInfo(columnInfo);
                filedInfo.setName(StringUtil.underlineToHump(columnInfo.getName()));
                filedInfo.setTypeName(clazz.getSimpleName());
                filedInfo.setTypeFullName(clazz.getName());
                if (columnInfo.getPk()) {
                    classInfo.setId(filedInfo);
                    if (filedInfo.getTypeName().equals("Long") || filedInfo.getTypeName().equals("String")) {
                        otherTypeSet.add(IdType.class.getName());
                    }
                }
                filedInfo.setCreateTime(clazz.getSimpleName().equals("Date") && (columnInfo.getName().toLowerCase().indexOf("create") > -1 || columnInfo.getRemarks().equals("创建时间")));
                filedInfo.setUpdateTime(clazz.getSimpleName().equals("Date") && (columnInfo.getName().toLowerCase().indexOf("update") > -1 || columnInfo.getRemarks().equals("更新时间")));
                //使用到的java.lang包下的其他类需要导入
                if (filedInfo.getTypeFullName().indexOf("java.lang") == -1) {
                    otherTypeSet.add(filedInfo.getTypeFullName());
                }
                if (filedInfo.isCreateTime() || filedInfo.isUpdateTime()) {
                    otherTypeSet.add(SqlDefaultValue.class.getName());
                    otherTypeSet.add(FillWith.class.getName());
                }
                filedInfoList.add(filedInfo);
            }
            if (classInfo.getId() == null) {
                classInfo.setId(filedInfoList.get(0));
            }
            classInfo.setFiledInfoList(filedInfoList);
            classInfo.setOtherTypeSet(otherTypeSet);
        }
        return classInfo;
    }

    /**
     * 创建要生成的文件
     *
     * @param config
     * @param filePaths
     * @param classInfoList
     * @throws IOException
     */
    public static void make(GenerateConfig config, Map<String, String> filePaths, List<ClassInfo> classInfoList) throws IOException {
        FreemarkerUtil freemarkerUtil = getFreemarkerUtil(config);
        for (ClassInfo classInfo : classInfoList) {
            freemarkerUtil.fprint(classInfo, TemplateType.MODEL.getTemplateName(), filePaths.get(TemplateType.MODEL.name()) + File.separator + classInfo.getClassName() + ".java");
            freemarkerUtil.fprint(classInfo, TemplateType.MAPPER.getTemplateName(), filePaths.get(TemplateType.MAPPER.name()) + File.separator + classInfo.getClassName() + (config.getJdbcDaoType() == JdbcDaoType.MyBatis ? "Mapper.java" : "Jdbc.java"));
            freemarkerUtil.fprint(classInfo, TemplateType.SERVICE.getTemplateName(), filePaths.get(TemplateType.SERVICE.name()) + File.separator + classInfo.getClassName() + "Service.java");
            freemarkerUtil.fprint(classInfo, TemplateType.SERVICE_IMPL.getTemplateName(), filePaths.get(TemplateType.SERVICE_IMPL.name()) + File.separator + classInfo.getClassName() + "ServiceImpl.java");
            freemarkerUtil.fprint(classInfo, TemplateType.CONTROLLER.getTemplateName(), filePaths.get(TemplateType.CONTROLLER.name()) + File.separator + classInfo.getClassName() + "Controller.java");
            freemarkerUtil.fprint(classInfo, config.getJdbcDocType().getTemplateName(), filePaths.get(JdbcDocType.class.getSimpleName()) + File.separator + classInfo.getTableInfo().getName() + config.getJdbcDocType().getSuffix());
        }
    }

    /**
     * 获取各个文件生成的目标地址
     *
     * @param config
     * @return
     */
    public static Map<String, String> getFilePaths(GenerateConfig config) {
        Map<String, String> filePaths = new HashMap<>();
        File targetDir;
        File modelDir;
        File mapperDir;
        File serviceDir;
        File serviceImplDir;
        File controllerDir;
        File sqlDocDir;
        String packPath = config.getBasePackage().replace(".", File.separator);
        String dateString = DateUtil.dateToString(new Date(), "yyyyMMddHHmmss");
        if (StringUtil.isNotEmpty(config.getTargetPath())) {
            targetDir = new File(config.getTargetPath() + File.separator + dateString);
        } else {
            //默认生成地址
            targetDir = new File(System.getProperty("user.dir") + File.separator + "generateTarget" + File.separator + dateString);
        }
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        modelDir = new File(targetDir.getAbsolutePath() + File.separator + packPath + File.separator + "model");
        mapperDir = new File(targetDir.getAbsolutePath() + File.separator + packPath + File.separator + (config.getJdbcDaoType() == JdbcDaoType.MyBatis ? "mapper" : "jdbc"));
        serviceDir = new File(targetDir.getAbsolutePath() + File.separator + packPath + File.separator + "service");
        serviceImplDir = new File(targetDir.getAbsolutePath() + File.separator + packPath + File.separator + "service" + File.separator + "impl");
        controllerDir = new File(targetDir.getAbsolutePath() + File.separator + packPath + File.separator + "controller");
        sqlDocDir = new File(targetDir.getAbsolutePath() + File.separator + "dbDoc");
        modelDir.mkdirs();
        mapperDir.mkdirs();
        serviceDir.mkdirs();
        serviceImplDir.mkdirs();
        controllerDir.mkdirs();
        sqlDocDir.mkdirs();
        filePaths.put(TemplateType.MODEL.name(), modelDir.getAbsolutePath());
        filePaths.put(TemplateType.MAPPER.name(), mapperDir.getAbsolutePath());
        filePaths.put(TemplateType.SERVICE.name(), serviceDir.getAbsolutePath());
        filePaths.put(TemplateType.SERVICE_IMPL.name(), serviceImplDir.getAbsolutePath());
        filePaths.put(TemplateType.CONTROLLER.name(), controllerDir.getAbsolutePath());
        filePaths.put(JdbcDocType.class.getSimpleName(), sqlDocDir.getAbsolutePath());
        return filePaths;
    }

    /**
     * 获取FreemarkerUtil
     *
     * @param config
     * @return
     */
    public static FreemarkerUtil getFreemarkerUtil(GenerateConfig config) throws IOException {
        FreemarkerUtil freemarkerUtil = null;
        if (StringUtil.isNotEmpty(config.getTemplatePath())) {
            File file = new File(config.getTemplatePath());
            freemarkerUtil = FreemarkerUtil.getInstance(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS.toString(), file);
        } else {
            freemarkerUtil = FreemarkerUtil.getInstance(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS.toString(), "/template/");
        }
        return freemarkerUtil;
    }
}
