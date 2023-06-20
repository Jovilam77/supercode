package cn.vonce.supercode.core.enumeration;

/**
 * @author Jovi
 * @email imjovi@qq.com
 * @date 2023/6/16 17:10
 */
public enum TemplateType {

    MODEL("model.ftl"),
    MAPPER("mapper.ftl"),
    SERVICE("service.ftl"),
    SERVICE_IMPL("service_impl.ftl"),
    CONTROLLER("controller.ftl"),
    DB_HTML("db_html.ftl"),
    DB_MARKDOWN("db_markdown.ftl"),
    DB_WORD("db_word.ftl"),
    SQL("sql.ftl");

    TemplateType(String templateName) {
        this.templateName = templateName;
    }

    String templateName;

    public String getTemplateName() {
        return templateName;
    }

}
