package cn.vonce.supercode.core.enumeration;

/**
 * 数据库文档生成类型
 *
 * @author Jovi
 * @version 1.0
 * @email imjovi@qq.com
 * @date 2021-10-12 11:53:22
 */
public enum JdbcDocType {

    HTML(TemplateType.DB_HTML, ".html"), MARKDOWN(TemplateType.DB_MARKDOWN, ".md"), WORD(TemplateType.DB_WORD, ".doc");

    JdbcDocType(TemplateType templateType, String suffix) {
        this.templateType = templateType;
        this.suffix = suffix;
    }

    TemplateType templateType;
    String suffix;

    public String getTemplateName() {
        return templateType.templateName;
    }

    public String getSuffix() {
        return suffix;
    }


}
