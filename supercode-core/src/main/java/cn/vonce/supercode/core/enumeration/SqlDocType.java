package cn.vonce.supercode.core.enumeration;

/**
 * 数据库文档生成类型
 *
 * @author Jovi
 * @version 1.0
 * @email imjovi@qq.com
 * @date 2021-10-12 11:53:22
 */
public enum SqlDocType {

    HTML(Template.DB_HTML), MARKDOWN(Template.DB_MARKDOWN), WORD(Template.DB_WORD);

    SqlDocType(Template template) {
        this.template = template;
    }

    Template template;

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }
}
