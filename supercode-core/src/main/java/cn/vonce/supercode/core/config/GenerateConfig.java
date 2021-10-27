package cn.vonce.supercode.core.config;

import cn.vonce.supercode.core.type.DaoType;
import cn.vonce.supercode.core.type.DocType;

/**
 * 生成配置
 *
 * @author Jovi
 * @version 1.0
 * @email imjovi@qq.com
 * @date 2021-10-12 11:53:22
 */
public class GenerateConfig {

    /**
     * 作者
     */
    private String author;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 版本
     */
    private String version;
    /**
     * 基础包名
     */
    private String basePackage;
    /**
     * 是否使用Lombok 默认使用Lombok
     */
    private boolean useLombok = true;
    /**
     * 是否使用SqlBean 默认使用SqlBean
     */
    private boolean useSqlBean = true;
    /**
     * 是否存在表名前缀
     */
    private boolean bePrefix;
    /**
     * 表前缀,如果bePrefix=true,prefix为空则默认处理表前缀, 例如t_user,自动去除t_
     */
    private String prefix;
    /**
     * dao持久化框架类型 默认使用Mybatis
     */
    private DaoType daoType = DaoType.MyBatis;
    /**
     * 文档类型 默认使用SmartDoc
     */
    private DocType docType = DocType.SmartDoc;
    /**
     * 模板路径
     */
    private String templatePath;
    /**
     * 生成的目标路径
     */
    private String targetPath;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public boolean isUseLombok() {
        return useLombok;
    }

    public void setUseLombok(boolean useLombok) {
        this.useLombok = useLombok;
    }

    public boolean isUseSqlBean() {
        return useSqlBean;
    }

    public void setUseSqlBean(boolean useSqlBean) {
        this.useSqlBean = useSqlBean;
    }

    public boolean isBePrefix() {
        return bePrefix;
    }

    public void setBePrefix(boolean bePrefix) {
        this.bePrefix = bePrefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public DaoType getDaoType() {
        return daoType;
    }

    public void setDaoType(DaoType daoType) {
        this.daoType = daoType;
    }

    public DocType getDocType() {
        return docType;
    }

    public void setDocType(DocType docType) {
        this.docType = docType;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }
}
