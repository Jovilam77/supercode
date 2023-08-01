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

    MODEL("model.ftl", TemplateType.JAVA, File.separator + "model" + File.separator, "", ".java"),
    MAPPER("mapper.ftl", TemplateType.JAVA, File.separator + "mapper" + File.separator, "Mapper", ".java"),
    SERVICE("service.ftl", TemplateType.JAVA, File.separator + "service" + File.separator, "Service", ".java"),
    SERVICE_IMPL("service_impl.ftl", TemplateType.JAVA, File.separator + "service" + File.separator + "impl" + File.separator, "ServiceImpl", ".java"),
    CONTROLLER("controller.ftl", TemplateType.JAVA, File.separator + "controller" + File.separator, "Controller", ".java"),
    DB_HTML("db_html.ftl", TemplateType.DOC, File.separator + "doc" + File.separator, "", ".html"),
    DB_MARKDOWN("db_markdown.ftl", TemplateType.DOC, File.separator + "doc" + File.separator, "", ".md"),
    DB_WORD("db_word.ftl", TemplateType.DOC, File.separator + "doc" + File.separator, "", ".doc"),
    SQL("sql.ftl", TemplateType.SQL, File.separator + "sql" + File.separator, "", ".sql");

    Template(String name, TemplateType type, String relativePath, String nameSuffix, String fileSuffix) {
        this.name = name;
        this.type = type;
        this.relativePath = relativePath;
        this.nameSuffix = nameSuffix;
        this.fileSuffix = fileSuffix;
    }

    /**
     * 模板文件名
     */
    String name;
    /**
     * 相对路径
     */
    String relativePath;
    /**
     * 模板类型
     */
    TemplateType type;
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

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public TemplateType getType() {
        return type;
    }

    public void setType(TemplateType type) {
        this.type = type;
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
