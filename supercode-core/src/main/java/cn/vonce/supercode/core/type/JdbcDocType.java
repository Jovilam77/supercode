package cn.vonce.supercode.core.type;

/**
 * 数据库文档生成类型
 *
 * @author Jovi
 * @version 1.0
 * @email imjovi@qq.com
 * @date 2021-10-12 11:53:22
 */
public enum JdbcDocType {

    Html("db_html.ftl", ".html"), Markdown("db_markdown.ftl", ".md"), Word("db_word.ftl", ".doc");

    JdbcDocType(String templateName, String suffix) {
        this.templateName = templateName;
        this.suffix = suffix;
    }

    String templateName;
    String suffix;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

}
