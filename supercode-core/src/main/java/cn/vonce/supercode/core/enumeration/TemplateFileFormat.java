package cn.vonce.supercode.core.enumeration;

/**
 * 模板文件格式
 *
 * @author Jovi
 * @email imjovi@qq.com
 * @date 2024/2/5 15:11
 */
public enum TemplateFileFormat {

    JAVA(".java"), HTML(".html"), MD(".md"), DOC(".doc"), SQL(".sql");

    private String format;

    TemplateFileFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

}
