package cn.vonce.supercode.core.helper;

import cn.vonce.sql.bean.ColumnInfo;
import cn.vonce.sql.bean.TableInfo;
import cn.vonce.sql.service.TableService;
import cn.vonce.sql.uitls.DateUtil;
import cn.vonce.sql.uitls.StringUtil;
import cn.vonce.supercode.core.config.GenerateConfig;
import cn.vonce.supercode.core.map.JdbcMapJava;
import cn.vonce.supercode.core.model.FiledInfo;
import cn.vonce.supercode.core.model.ClassInfo;
import cn.vonce.supercode.core.type.JdbcDaoType;
import cn.vonce.supercode.core.util.FreemarkerUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import freemarker.template.Configuration;

import java.io.File;
import java.io.IOException;
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
     * 构建生成
     *
     * @param config
     * @param tableService
     * @throws IOException
     */
    public static void build(GenerateConfig config, TableService tableService) throws IOException {
        List<TableInfo> tableInfoList = getTableInfoList(config, tableService);
        Map<String, String> filePaths = getFilePaths(config);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(5, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());
        int num = 10;
        int size = tableInfoList.size() % num == 0 ? tableInfoList.size() / num : tableInfoList.size() / num + 1;
        for (int i = 0; i < size; i++) {
            int finalI = i;
            pool.execute(() -> {
                List<ClassInfo> classInfoList = getGenerateObjectList(config, tableInfoList.subList(num * finalI, finalI == size - 1 ? tableInfoList.size() : num * finalI + num), tableService);
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
     * 获取要生成的数据表信息
     *
     * @param config
     * @param tableService
     * @return
     */
    public static List<TableInfo> getTableInfoList(GenerateConfig config, TableService tableService) {
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
    public static List<ClassInfo> getGenerateObjectList(GenerateConfig config, List<TableInfo> tableInfoList, TableService tableService) {
        Date date = new Date();
        List<ClassInfo> classInfoList = new ArrayList<>();
        for (TableInfo tableInfo : tableInfoList) {
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
            List<ColumnInfo> columnInfoList = tableService.getColumnInfoList(tableInfo.getName());
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
                    }
                    if (filedInfo.getTypeFullName().indexOf("java.lang") == -1) {
                        otherTypeSet.add(filedInfo.getTypeFullName());
                    }
                    filedInfoList.add(filedInfo);
                }
                if (classInfo.getId() == null) {
                    classInfo.setId(filedInfoList.get(0));
                }
                classInfo.setFiledInfoList(filedInfoList);
                classInfo.setOtherTypeSet(otherTypeSet);
            }
            classInfoList.add(classInfo);
        }
        return classInfoList;
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
            freemarkerUtil.fprint(classInfo, "model.ftl", filePaths.get("model") + File.separator + classInfo.getClassName() + ".java");
            freemarkerUtil.fprint(classInfo, "mapper.ftl", filePaths.get("mapper") + File.separator + classInfo.getClassName() + (config.getJdbcDaoType() == JdbcDaoType.MyBatis ? "Mapper.java" : "Jdbc.java"));
            freemarkerUtil.fprint(classInfo, "service.ftl", filePaths.get("service") + File.separator + classInfo.getClassName() + "Service.java");
            freemarkerUtil.fprint(classInfo, "service_impl.ftl", filePaths.get("serviceImpl") + File.separator + classInfo.getClassName() + "ServiceImpl.java");
            freemarkerUtil.fprint(classInfo, "controller.ftl", filePaths.get("controller") + File.separator + classInfo.getClassName() + "Controller.java");
            freemarkerUtil.fprint(classInfo, config.getJdbcDocType().getTemplateName(), filePaths.get("sqlDocDir") + File.separator + classInfo.getTableInfo().getName() + config.getJdbcDocType().getSuffix());
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
        File targerDir;
        File modelDir;
        File mapperDir;
        File serviceDir;
        File serviceImplDir;
        File controllerDir;
        File sqlDocDir;
        String packPath = config.getBasePackage().replace(".", File.separator);
        String dateString = DateUtil.dateToString(new Date(), "yyyyMMddHHmmss");
        if (StringUtil.isNotEmpty(config.getTargetPath())) {
            targerDir = new File(config.getTargetPath() + File.separator + dateString);
        } else {
            //默认生成地址
            targerDir = new File(System.getProperty("user.dir") + File.separator + "generateTarget" + File.separator + dateString);
        }
        if (!targerDir.exists()) {
            targerDir.mkdirs();
        }
        modelDir = new File(targerDir.getAbsolutePath() + File.separator + packPath + File.separator + "model");
        mapperDir = new File(targerDir.getAbsolutePath() + File.separator + packPath + File.separator + (config.getJdbcDaoType() == JdbcDaoType.MyBatis ? "mapper" : "jdbc"));
        serviceDir = new File(targerDir.getAbsolutePath() + File.separator + packPath + File.separator + "service");
        serviceImplDir = new File(targerDir.getAbsolutePath() + File.separator + packPath + File.separator + "service" + File.separator + "impl");
        controllerDir = new File(targerDir.getAbsolutePath() + File.separator + packPath + File.separator + "controller");
        sqlDocDir = new File(targerDir.getAbsolutePath() + File.separator + "dbDoc");
        modelDir.mkdirs();
        mapperDir.mkdirs();
        serviceDir.mkdirs();
        serviceImplDir.mkdirs();
        controllerDir.mkdirs();
        sqlDocDir.mkdirs();
        filePaths.put("model", modelDir.getAbsolutePath());
        filePaths.put("mapper", mapperDir.getAbsolutePath());
        filePaths.put("service", serviceDir.getAbsolutePath());
        filePaths.put("serviceImpl", serviceImplDir.getAbsolutePath());
        filePaths.put("controller", controllerDir.getAbsolutePath());
        filePaths.put("sqlDocDir", sqlDocDir.getAbsolutePath());
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
