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
import cn.vonce.supercode.core.type.DaoType;
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

    public static void start(GenerateConfig config, TableService tableService) throws IOException {
        List<ClassInfo> classInfoList = getGenerateObjectList(config, tableService);
        FreemarkerUtil freemarkerUtil = getFreemarkerUtil(config);
        File targerDir = null;
        File modelDir;
        File mapperDir;
        File serviceDir;
        File serviceImplDir;
        File controllerDir;
        String packPath = config.getBasePackage().replace(".", File.separator);
        String dateString = DateUtil.dateToString(new Date(), "yyyyMMddHHmmss");
        if (StringUtil.isNotEmpty(config.getTargetPath())) {
            targerDir = new File(config.getTargetPath() + File.separator + dateString);
        } else {
            //默认生成地址
        }
        if (!targerDir.exists()) {
            targerDir.mkdirs();
        }
        modelDir = new File(targerDir.getAbsolutePath() + File.separator + packPath + File.separator + "model");
        mapperDir = new File(targerDir.getAbsolutePath() + File.separator + packPath + File.separator + (config.getDaoType() == DaoType.MyBatis ? "mapper" : "jdbc"));
        serviceDir = new File(targerDir.getAbsolutePath() + File.separator + packPath + File.separator + "service");
        serviceImplDir = new File(targerDir.getAbsolutePath() + File.separator + packPath + File.separator + "service" + File.separator + "impl");
        controllerDir = new File(targerDir.getAbsolutePath() + File.separator + packPath + File.separator + "controller");
        modelDir.mkdirs();
        mapperDir.mkdirs();
        serviceDir.mkdirs();
        serviceImplDir.mkdirs();
        controllerDir.mkdirs();
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(5, 20,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());
        for (ClassInfo classInfo : classInfoList) {
            pool.execute(() -> freemarkerUtil.fprint(classInfo, "model.ftl", modelDir.getAbsolutePath() + File.separator + classInfo.getClassName() + ".java"));
            pool.execute(() -> freemarkerUtil.fprint(classInfo, "mapper.ftl", mapperDir.getAbsolutePath() + File.separator + classInfo.getClassName() + (config.getDaoType() == DaoType.MyBatis ? "Mapper.java" : "Jdbc.java")));
            pool.execute(() -> freemarkerUtil.fprint(classInfo, "service.ftl", serviceDir.getAbsolutePath() + File.separator + classInfo.getClassName() + "Service.java"));
            pool.execute(() -> freemarkerUtil.fprint(classInfo, "service_impl.ftl", serviceImplDir.getAbsolutePath() + File.separator + classInfo.getClassName() + "ServiceImpl.java"));
            pool.execute(() -> freemarkerUtil.fprint(classInfo, "controller.ftl", controllerDir.getAbsolutePath() + File.separator + classInfo.getClassName() + "Controller.java"));
        }
        pool.shutdown();
    }

    public static List<ClassInfo> getGenerateObjectList(GenerateConfig config, TableService tableService) {
        List<TableInfo> tableInfoList = tableService.getTableList(null);
        if (tableInfoList == null || tableInfoList.isEmpty()) {
            return null;
        }
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
                classInfo.setFiledInfoList(filedInfoList);
                classInfo.setOtherTypeSet(otherTypeSet);
            }
            classInfoList.add(classInfo);
        }
        return classInfoList;
    }

    /**
     * 获取
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
            freemarkerUtil = FreemarkerUtil.getInstance(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS.toString(), "/resources");
        }
        return freemarkerUtil;
    }
}
