package cn.vonce.supercode.core.util;

import freemarker.core.ParseException;
import freemarker.template.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Freemarker工具
 *
 * @author Jovi
 * @version 1.0
 * @email imjovi@qq.com
 * @date 2021-10-12 11:53:22
 */
public class FreemarkerUtil {

    private static FreemarkerUtil freemarkerUtil;

    private static Configuration configuration;

    private FreemarkerUtil() {
    }

    /**
     * @param freemarkerVersionNo freemarker的版本号
     * @param templatePath        模版加载路径
     * @return
     */
    public static FreemarkerUtil getInstance(String freemarkerVersionNo, String templatePath) {
        if (null == freemarkerUtil) {
            configuration = new Configuration(new Version(freemarkerVersionNo));
            configuration.setClassForTemplateLoading(FreemarkerUtil.class, templatePath);
            freemarkerUtil = new FreemarkerUtil();
        }
        return freemarkerUtil;
    }

    /**
     * @param templateName 根据模版名称加载对应模版
     * @return
     */
    private Template getTemplate(String templateName) {
        try {
            return configuration.getTemplate(templateName);
        } catch (TemplateNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedTemplateNameException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param dataModel    数据模型
     * @param templateName 输出模版
     */
    public void sprint(Map<String, Object> dataModel, String templateName) {
        try {
            this.getTemplate(templateName).process(dataModel, new PrintWriter(System.out));
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param dataModel    数据模型
     * @param templateName 输出模版
     * @param filePath     输出文件路径
     */
    public void fprint(Map<String, Object> dataModel, String templateName, String filePath) {
        try {
            this.getTemplate(templateName).process(dataModel, new FileWriter(filePath));
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
