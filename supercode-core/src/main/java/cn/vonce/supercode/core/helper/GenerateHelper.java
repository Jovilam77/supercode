package cn.vonce.supercode.core.helper;

import cn.vonce.sql.bean.ColumnInfo;
import cn.vonce.sql.bean.TableInfo;
import cn.vonce.sql.service.TableService;
import cn.vonce.sql.uitls.StringUtil;
import cn.vonce.supercode.core.config.GenerateConfig;
import cn.vonce.supercode.core.map.JdbcMapJava;
import cn.vonce.supercode.core.model.FiledInfo;
import cn.vonce.supercode.core.model.GenerateObject;
import cn.vonce.supercode.core.util.FreemarkerUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * 生成助手
 *
 * @author Jovi
 * @version 1.0
 * @email imjovi@qq.com
 * @date 2021-10-26 20:01:11
 */
@Component
public class GenerateHelper {

    public void start(GenerateConfig generateConfig, TableService tableService) {
        List<GenerateObject> generateObjectList = getGenerateObjectList(generateConfig, tableService);
        FreemarkerUtil freemarkerUtil = getFreemarkerUtil(generateConfig);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(5, 20,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());
        for (GenerateObject generateObject : generateObjectList) {
            pool.execute(() -> freemarkerUtil.fprint(generateObject, "model.ftl", generateConfig.getTargetPath() + File.separator + generateObject.getClassName() + "Model"));
            pool.execute(() -> freemarkerUtil.fprint(generateObject, "mapper.ftl", generateConfig.getTargetPath() + File.separator + generateObject.getClassName() + "Mapper"));
            pool.execute(() -> freemarkerUtil.fprint(generateObject, "service.ftl", generateConfig.getTargetPath() + File.separator + generateObject.getClassName() + "Service"));
            pool.execute(() -> freemarkerUtil.fprint(generateObject, "service_impl.ftl", generateConfig.getTargetPath() + File.separator + generateObject.getClassName() + "ServiceImpl"));
            pool.execute(() -> freemarkerUtil.fprint(generateObject, "controller.ftl", generateConfig.getTargetPath() + File.separator + generateObject.getClassName() + "Controller"));
        }
        pool.shutdown();
    }

    public List<GenerateObject> getGenerateObjectList(GenerateConfig generateConfig, TableService tableService) {
        List<TableInfo> tableInfoList = tableService.getTableList(null);
        if (tableInfoList == null || tableInfoList.isEmpty()) {
            return null;
        }
        Date date = new Date();
        List<GenerateObject> generateObjectList = new ArrayList<>();
        for (TableInfo tableInfo : tableInfoList) {
            GenerateObject generateObject = new GenerateObject();
            generateObject.setConfig(generateConfig);
            generateObject.setClassName(StringUtil.underlineToHump(tableInfo.getName()));
            generateObject.setTableInfo(tableInfo);
            generateObject.setDate(date);
            List<ColumnInfo> columnInfoList = tableService.getColumnInfoList(tableInfo.getName());
            if (columnInfoList != null && !columnInfoList.isEmpty()) {
                List<FiledInfo> filedInfoList = new ArrayList<>();
                FiledInfo filedInfo;
                for (ColumnInfo columnInfo : columnInfoList) {
                    Class<?> clazz = JdbcMapJava.getJavaType(columnInfo.getType());
                    filedInfo = new FiledInfo();
                    filedInfo.setColumnInfo(columnInfo);
                    filedInfo.setName(StringUtil.underlineToHump(columnInfo.getName()));
                    filedInfo.setTypeName(clazz.getTypeName());
                    filedInfo.setTypeFullName(clazz.getName());
                    if (columnInfo.getPk()) {
                        generateObject.setId(filedInfo);
                    }
                    filedInfoList.add(filedInfo);
                }
                generateObject.setFiledInfoList(filedInfoList);
            }
            generateObjectList.add(generateObject);
        }
        return generateObjectList;
    }

    /**
     * 获取
     *
     * @param generateConfig
     * @return
     */
    public FreemarkerUtil getFreemarkerUtil(GenerateConfig generateConfig) {
        FreemarkerUtil freemarkerUtil = FreemarkerUtil.getInstance("2.3.31", "D:\\wk\\project\\supercode\\supercode-core\\src\\main\\resources");
        return freemarkerUtil;
    }
}
