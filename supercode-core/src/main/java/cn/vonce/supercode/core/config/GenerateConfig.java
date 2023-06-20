package cn.vonce.supercode.core.config;

import cn.vonce.sql.uitls.StringUtil;
import cn.vonce.supercode.core.enumeration.JdbcDaoType;
import cn.vonce.supercode.core.enumeration.JavaDocType;
import cn.vonce.supercode.core.enumeration.JdbcDocType;

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
     * 模块
     */
    private String module;
    /**
     * 基础类
     */
    private Class<?> baseClass;
    /**
     * 基础类名(如果使用baseClass则不需要设置此字段)
     */
    private String baseClassName;
    /**
     * 基础类字段(如果使用baseClass则不需要设置此字段)
     */
    private String[] baseClassFields;
    /**
     * 是否使用Lombok 默认使用Lombok
     */
    private boolean useLombok = true;
    /**
     * 是否使用SqlBean 默认使用SqlBean
     */
    private boolean useSqlBean = true;
    /**
     * 是否生成为RestFulApi
     */
    private boolean useRestfulApi = true;
    /**
     * 是否存在表名前缀
     */
    private boolean bePrefix = false;
    /**
     * 表前缀,如果bePrefix=true,prefix为空则默认处理表前缀, 例如t_user,自动去除t_
     */
    private String prefix;
    /**
     * Jdbc Dao持久化框架类型 默认使用Mybatis
     */
    private JdbcDaoType jdbcDaoType = JdbcDaoType.MyBatis;
    /**
     * 数据库 文档类型 默认使用Html
     */
    private JdbcDocType jdbcDocType = JdbcDocType.HTML;
    /**
     * Java 文档类型 默认使用SmartDoc
     */
    private JavaDocType javaDocType = JavaDocType.SmartDoc;
    /**
     * 模板路径
     */
    private String templatePath;
    /**
     * 生成的目标路径
     */
    private String targetPath;

    /**
     * 时间戳
     */
    private Long timestamp = System.currentTimeMillis();

    public String getAuthor() {
        if (StringUtil.isBlank(author)) {
            return System.getProperty("user.name");
        }
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
        if (StringUtil.isBlank(basePackage)) {
            return "cn.vonce";
        }
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Class<?> getBaseClass() {
        return baseClass;
    }

    public void setBaseClass(Class<?> baseClass) {
        this.baseClass = baseClass;
    }

    public String getBaseClassName() {
        return baseClassName;
    }

    public void setBaseClassName(String baseClassName) {
        this.baseClassName = baseClassName;
    }

    public String[] getBaseClassFields() {
        return baseClassFields;
    }

    public void setBaseClassFields(String... baseClassFields) {
        this.baseClassFields = baseClassFields;
    }

    public boolean isUseLombok() {
        return useLombok;
    }

    public void setUseLombok(boolean useLombok) {
        this.useLombok = useLombok;
    }

    public boolean isUseRestfulApi() {
        return useRestfulApi;
    }

    public void setUseRestfulApi(boolean useRestfulApi) {
        this.useRestfulApi = useRestfulApi;
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
        if (StringUtil.isNotEmpty(this.prefix)) {
            this.bePrefix = true;
        } else {
            this.bePrefix = false;
        }
    }

    public JdbcDaoType getJdbcDaoType() {
        return jdbcDaoType;
    }

    public void setJdbcDaoType(JdbcDaoType jdbcDaoType) {
        this.jdbcDaoType = jdbcDaoType;
    }

    public JdbcDocType getJdbcDocType() {
        return jdbcDocType;
    }

    public void setJdbcDocType(JdbcDocType jdbcDocType) {
        this.jdbcDocType = jdbcDocType;
    }

    public JavaDocType getJavaDocType() {
        return javaDocType;
    }

    public void setJavaDocType(JavaDocType javaDocType) {
        this.javaDocType = javaDocType;
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

    public long getTimestamp() {
        return timestamp;
    }

}
