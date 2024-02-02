package cn.vonce.supercode.core.helper;

import cn.vonce.sql.annotation.SqlColumn;
import cn.vonce.sql.annotation.SqlDefaultValue;
import cn.vonce.sql.annotation.SqlTable;
import cn.vonce.sql.bean.ColumnInfo;
import cn.vonce.sql.bean.Create;
import cn.vonce.sql.bean.Table;
import cn.vonce.sql.bean.TableInfo;
import cn.vonce.sql.config.SqlBeanConfig;
import cn.vonce.sql.config.SqlBeanDB;
import cn.vonce.sql.enumerate.DbType;
import cn.vonce.sql.enumerate.FillWith;
import cn.vonce.sql.enumerate.IdType;
import cn.vonce.sql.helper.SqlHelper;
import cn.vonce.sql.service.TableService;
import cn.vonce.sql.uitls.DateUtil;
import cn.vonce.sql.uitls.SqlBeanUtil;
import cn.vonce.sql.uitls.StringUtil;
import cn.vonce.supercode.core.config.GenerateConfig;
import cn.vonce.supercode.core.enumeration.Template;
import cn.vonce.supercode.core.enumeration.TemplateType;
import cn.vonce.supercode.core.map.JdbcMapJava;
import cn.vonce.supercode.core.model.FieldInfo;
import cn.vonce.supercode.core.model.ClassInfo;
import cn.vonce.supercode.core.util.ClassUtil;
import cn.vonce.supercode.core.util.FreemarkerUtil;
import freemarker.template.Configuration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

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
        File packDir = getFilePaths(config);
        ExecutorService pool = new ThreadPoolExecutor(3, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        int num = 10;
        int size = tableInfoList.size() % num == 0 ? tableInfoList.size() / num : tableInfoList.size() / num + 1;
        for (int i = 0; i < size; i++) {
            int finalI = i;
            pool.execute(() -> {
                List<ClassInfo> classInfoList = getClassInfoList(config, tableInfoList.subList(num * finalI, finalI == size - 1 ? tableInfoList.size() : num * finalI + num), tableService);
                try {
                    make(config, packDir, classInfoList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        pool.shutdown();
    }

    /**
     * 通过数据库表 构建生成（批量）
     *
     * @param config         生成信息配置
     * @param tableInfo      表信息
     * @param columnInfoList 列信息列表
     */
    public static void build(GenerateConfig config, TableInfo tableInfo, List<ColumnInfo> columnInfoList) {
        File packDir = getFilePaths(config);
        List<ClassInfo> classInfoList = new ArrayList<>();
        classInfoList.add(getClassInfo(config, tableInfo, columnInfoList));
        try {
            make(config, packDir, classInfoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过实体类 构建生成（批量）
     *
     * @param config       生成信息配置
     * @param dbType       数据库类型
     * @param packageNames 包名数组
     */
    public static void build(GenerateConfig config, DbType dbType, String... packageNames) {
        build(config, dbType, false, packageNames);
    }

    /**
     * 通过实体类 构建生成（批量）
     *
     * @param config         生成信息配置
     * @param dbType         数据库类型
     * @param sqlToUpperCase SQL是否转大写
     * @param packageNames   包名数组
     */
    public static void build(GenerateConfig config, DbType dbType, boolean sqlToUpperCase, String... packageNames) {
        if (packageNames == null || packageNames.length == 0) {
            return;
        }
        List<Class<?>> beanClassList = new ArrayList<>();
        for (String packageName : packageNames) {
            beanClassList.addAll(ClassUtil.getClasses(packageName));
        }
        for (Class<?> beanClass : beanClassList) {
            build(config, dbType, sqlToUpperCase, beanClass);
        }
    }

    /**
     * 通过实体类 构建生成（批量）
     *
     * @param config        生成信息配置
     * @param dbType        数据库类型
     * @param beanClassList 生成的实体类列表
     */
    public static void build(GenerateConfig config, DbType dbType, List<Class<?>> beanClassList) {
        build(config, dbType, false, beanClassList);
    }

    /**
     * 通过实体类 构建生成（批量）
     *
     * @param config         生成信息配置
     * @param dbType         数据库类型
     * @param sqlToUpperCase SQL是否转大写
     * @param beanClassList  生成的实体类列表
     */
    public static void build(GenerateConfig config, DbType dbType, boolean sqlToUpperCase, List<Class<?>> beanClassList) {
        if (beanClassList == null || beanClassList.size() == 0) {
            return;
        }
        for (Class<?> beanClass : beanClassList) {
            build(config, dbType, sqlToUpperCase, beanClass);
        }
    }

    /**
     * 通过实体类 构建生成（单表）
     *
     * @param config    生成信息配置
     * @param dbType    数据库类型
     * @param beanClass 生成的实体类
     */
    public static void build(GenerateConfig config, DbType dbType, Class<?> beanClass) {
        build(config, dbType, false, beanClass);
    }

    /**
     * 通过实体类 构建生成（单表）
     *
     * @param config         生成信息配置
     * @param dbType         数据库类型
     * @param sqlToUpperCase SQL是否转大写
     * @param beanClass      生成的实体类
     */
    public static void build(GenerateConfig config, DbType dbType, boolean sqlToUpperCase, Class<?> beanClass) {
        SqlBeanDB sqlBeanDB = new SqlBeanDB();
        sqlBeanDB.setDbType(dbType);
        SqlBeanConfig sqlBeanConfig = new SqlBeanConfig();
        sqlBeanConfig.setToUpperCase(sqlToUpperCase);
        sqlBeanDB.setSqlBeanConfig(sqlBeanConfig);
        File packDir = getFilePaths(config);
        Table table = SqlBeanUtil.getTable(beanClass);
        SqlTable sqlTable = SqlBeanUtil.getSqlTable(beanClass);
        TableInfo tableInfo = new TableInfo();
        tableInfo.setName(table.getName());
        tableInfo.setRemarks(sqlTable != null ? sqlTable.remarks() : "");
        List<Field> fieldList = SqlBeanUtil.getBeanAllField(beanClass);
        List<ColumnInfo> columnInfoList = new ArrayList<>();

        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            if (SqlBeanUtil.isIgnore(field)) {
                continue;
            }
            columnInfoList.add(SqlBeanUtil.buildColumnInfo(sqlBeanDB, field, sqlTable, field.getAnnotation(SqlColumn.class)));
        }

        List<ClassInfo> classInfoList = new ArrayList<>();
        ClassInfo classInfo = getClassInfo(config, tableInfo, columnInfoList);
        Create create = new Create();
        create.setSqlBeanDB(sqlBeanDB);
        create.setTable(beanClass);
        create.setBeanClass(beanClass);
        classInfo.setSql(SqlHelper.buildCreateSql(create));
        classInfoList.add(classInfo);

        try {
            make(config, packDir, classInfoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取要生成的数据表信息
     *
     * @param tableService 数据库连接实现类
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
     * @param config        生成信息配置
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
     * @param config         生成信息配置
     * @param tableInfo      表信息
     * @param columnInfoList 列信息列表
     * @return
     */
    public static ClassInfo getClassInfo(GenerateConfig config, TableInfo tableInfo, List<ColumnInfo> columnInfoList) {
        Date date = new Date();
        ClassInfo classInfo = new ClassInfo();
        classInfo.setConfig(config);
        String newTableName;
        //处理前缀
        if (config.isBePrefix() && StringUtil.isNotEmpty(config.getPrefix()) && tableInfo.getName().indexOf(config.getPrefix()) == 0) {
            newTableName = tableInfo.getName().substring(config.getPrefix().length());
        } else if (config.isBePrefix()) {
            newTableName = tableInfo.getName().substring(tableInfo.getName().indexOf("_") + 1);
        } else {
            newTableName = tableInfo.getName();
        }
        //类名
        classInfo.setClassName(newTableName.substring(0, 1).toUpperCase() + StringUtil.underlineToHump(newTableName.substring(1)));
        classInfo.setTableInfo(tableInfo);
        classInfo.setDate(date);
        if (columnInfoList != null && !columnInfoList.isEmpty()) {
            List<FieldInfo> fieldInfoList = new ArrayList<>();
            String baseClassPath = "";
            List<String> baseClassFiledList = null;
            Set<String> otherTypeSet = new HashSet<>();
            //处理基类
            if (config.getBaseClass() != null) {
                baseClassPath = config.getBaseClass().getName();
                List<Field> fieldList = SqlBeanUtil.getBeanAllField(config.getBaseClass());
                baseClassFiledList = fieldList.stream().map(item -> item.getName()).collect(Collectors.toList());
            } else if (StringUtil.isNotBlank(config.getBaseClassName())) {
                if (config.getBaseClassName().indexOf(".") > -1) {
                    baseClassPath = config.getBaseClassName();
                } else {
                    baseClassPath = config.getBasePackage() + ((StringUtil.isNotBlank(config.getModule())) ? "." + config.getModule() : "") + "." + config.getBaseClassName();
                }
                baseClassFiledList = Arrays.asList(config.getBaseClassFields());
            }
            if (StringUtil.isNotBlank(baseClassPath)) {
                classInfo.setBaseClassName(baseClassPath.substring(baseClassPath.lastIndexOf(".") + 1));
                otherTypeSet.add(baseClassPath);
            }
            FieldInfo filedInfo;
            for (ColumnInfo columnInfo : columnInfoList) {
                String columnName = StringUtil.underlineToHump(columnInfo.getName());
                Class<?> clazz = JdbcMapJava.getJavaType(columnInfo.getType());
                filedInfo = new FieldInfo();
                filedInfo.setColumnInfo(columnInfo);
                filedInfo.setName(columnName);
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
                fieldInfoList.add(filedInfo);
            }
            //基类中的字段设置为忽略
            if (baseClassFiledList != null && baseClassFiledList.size() > 0) {
                for (FieldInfo fieldInfo : fieldInfoList) {
                    for (String filedName : baseClassFiledList) {
                        if (fieldInfo.getName().equals(filedName)) {
                            fieldInfo.setIgnore(true);
                            break;
                        }
                    }
                }
            }
            if (classInfo.getId() == null) {
                classInfo.setId(fieldInfoList.get(0));
            }
            classInfo.setFieldInfoList(fieldInfoList);
            classInfo.setOtherTypeSet(otherTypeSet);
        }
        return classInfo;
    }

    /**
     * 创建要生成的文件
     *
     * @param config        生成信息配置
     * @param targetDir     文件生成路径
     * @param classInfoList 类信息列表
     * @throws IOException
     */
    private static void make(GenerateConfig config, File targetDir, List<ClassInfo> classInfoList) throws IOException {
        FreemarkerUtil freemarkerUtil = getFreemarkerUtil(config);
        String packPath = config.getBasePackage().replace(".", File.separator);
        if (StringUtil.isNotBlank(config.getModule())) {
            packPath = packPath + File.separator + config.getModule();
        }
        for (ClassInfo classInfo : classInfoList) {
            for (Template template : Template.values()) {
                if (template.getType() == TemplateType.JAVA) {
                    //如果不是多模块项目且属于多模块的模板则跳过
                    if (!config.isMultiProject() && StringUtil.isNotBlank(template.getNamePrefix())) {
                        continue;
                    }
                    String name = template.getNamePrefix() + classInfo.getClassName();
                    if (config.isMultiProject()) {
                        freemarkerUtil.fprint(classInfo, template.getName(), targetDir.getAbsolutePath() + File.separator + template.getProject() + File.separator + packPath + template.getRelativePath() + name + template.getNameSuffix() + template.getFileSuffix());
                    } else {
                        freemarkerUtil.fprint(classInfo, template.getName(), targetDir.getAbsolutePath() + File.separator + packPath + template.getRelativePath() + name + template.getNameSuffix() + template.getFileSuffix());
                    }
                }
            }
            freemarkerUtil.fprint(classInfo, config.getJdbcDocType().getTemplate().getName(), targetDir.getAbsolutePath() + config.getJdbcDocType().getTemplate().getRelativePath() + classInfo.getTableInfo().getName() + config.getJdbcDocType().getTemplate().getFileSuffix());
            if (StringUtil.isNotBlank(classInfo.getSql())) {
                freemarkerUtil.fprint(classInfo, Template.SQL.getName(), targetDir.getAbsolutePath() + Template.SQL.getRelativePath() + classInfo.getTableInfo().getName() + Template.SQL.getFileSuffix());
            }
        }
    }

    /**
     * 获取各个文件生成的目标地址
     *
     * @param config 生成信息配置
     * @return
     */
    public static File getFilePaths(GenerateConfig config) {
        File targetDir;
        String packPath = config.getBasePackage().replace(".", File.separator);
        if (StringUtil.isNotBlank(config.getModule())) {
            packPath = packPath + File.separator + config.getModule();
        }
        String dateString = DateUtil.dateToString(new Date(config.getTimestamp()), "yyyyMMddHHmmss");
        if (StringUtil.isNotEmpty(config.getTargetPath())) {
            targetDir = new File(config.getTargetPath() + File.separator + dateString);
        } else {
            //默认生成地址
            targetDir = new File(System.getProperty("user.dir") + File.separator + "generateTarget" + File.separator + dateString);
        }
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        for (Template template : Template.values()) {
            File dir;
            if (template.getType() == TemplateType.JAVA) {
                if (config.isMultiProject()) {
                    dir = new File(targetDir.getAbsolutePath() + File.separator + template.getProject() + File.separator + packPath + template.getRelativePath());
                } else {
                    dir = new File(targetDir.getAbsolutePath() + File.separator + packPath + template.getRelativePath());
                }
            } else {
                dir = new File(targetDir.getAbsolutePath() + template.getRelativePath());
            }
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        return targetDir;
    }

    /**
     * 获取FreemarkerUtil
     *
     * @param config 生成信息配置
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
