package cn.vonce.supercode.core.enumeration;

import java.io.File;

/**
 * 模板
 *
 * @author Jovi
 * @email imjovi@qq.com
 * @date 2023/6/16 17:10
 */
public enum Template {

    MODEL("model.ftl", TemplateType.JAVA, "common", File.separator + "model" + File.separator, "", "", ".java"),
    MAPPER("mapper.ftl", TemplateType.JAVA, "common", File.separator + "mapper" + File.separator, "", "Mapper", ".java"),
    SERVICE("service.ftl", TemplateType.JAVA, "common", File.separator + "service" + File.separator, "", "Service", ".java"),
    SERVICE_IMPL("service_impl.ftl", TemplateType.JAVA, "common", File.separator + "service" + File.separator + "impl" + File.separator, "", "ServiceImpl", ".java"),
    CONTROLLER("controller.ftl", TemplateType.JAVA, "common", File.separator + "controller" + File.separator, "", "Controller", ".java"),

    APP_SERVICE("app_service.ftl", TemplateType.JAVA, "application", File.separator + "service" + File.separator, "App", "Service", ".java"),
    APP_SERVICE_IMPL("app_service_impl.ftl", TemplateType.JAVA, "application", File.separator + "service" + File.separator + "impl" + File.separator, "App", "ServiceImpl", ".java"),
    APP_CONTROLLER("app_controller.ftl", TemplateType.JAVA, "application", File.separator + "controller" + File.separator, "App", "Controller", ".java"),


    DB_HTML("db_html.ftl", TemplateType.DOC, "", File.separator + "doc" + File.separator, "", "", ".html"),
    DB_MARKDOWN("db_markdown.ftl", TemplateType.DOC, "", File.separator + "doc" + File.separator, "", "", ".md"),
    DB_WORD("db_word.ftl", TemplateType.DOC, "", File.separator + "doc" + File.separator, "", "", ".doc"),
    SQL("sql.ftl", TemplateType.SQL, "", File.separator + "sql" + File.separator, "", "", ".sql");

    Template(String name, TemplateType type, String project, String relativePath, String namePrefix, String nameSuffix, String fileSuffix) {
        this.name = name;
        this.type = type;
        this.project = project;
        this.relativePath = relativePath;
        this.namePrefix = namePrefix;
        this.nameSuffix = nameSuffix;
        this.fileSuffix = fileSuffix;
    }

    /**
     * 模板文件名
     */
    String name;
    /**
     * 模板类型
     */
    TemplateType type;
    /**
     * 项目
     */
    String project;
    /**
     * 相对路径
     */
    String relativePath;
    /**
     * 名字前缀
     */
    String namePrefix;
    /**
     * 名字后缀
     */
    String nameSuffix;
    /**
     * 文件后缀
     */
    String fileSuffix;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TemplateType getType() {
        return type;
    }

    public void setType(TemplateType type) {
        this.type = type;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    public String getNameSuffix() {
        return nameSuffix;
    }

    public void setNameSuffix(String nameSuffix) {
        this.nameSuffix = nameSuffix;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

}
