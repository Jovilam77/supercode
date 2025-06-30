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

    BASE_ENTITY("base_entity.ftl", TemplateType.JAVA, "common", File.separator + "model" + File.separator, "", "", TemplateFileFormat.JAVA),
    RESULT("result.ftl", TemplateType.JAVA, "common", File.separator + "model" + File.separator, "", "", TemplateFileFormat.JAVA),
    PAGE_DTO("page_dto.ftl", TemplateType.JAVA, "common", File.separator + "model" + File.separator, "", "", TemplateFileFormat.JAVA),

    MODEL_ENTITY("model_entity.ftl", TemplateType.JAVA, "common", File.separator + "model" + File.separator + "entity" + File.separator, "", "", TemplateFileFormat.JAVA),
    MODEL_CREATE_DTO("model_create_dto.ftl", TemplateType.JAVA, "common", File.separator + "model" + File.separator + "dto" + File.separator, "", "CreateDto", TemplateFileFormat.JAVA),
    MODEL_UPDATE_DTO("model_update_dto.ftl", TemplateType.JAVA, "common", File.separator + "model" + File.separator + "dto" + File.separator, "", "UpdateDto", TemplateFileFormat.JAVA),
    MODEL_QUERY_DTO("model_query_dto.ftl", TemplateType.JAVA, "common", File.separator + "model" + File.separator + "dto" + File.separator, "", "QueryDto", TemplateFileFormat.JAVA),
    MODEL_VO("model_vo.ftl", TemplateType.JAVA, "common", File.separator + "model" + File.separator + "vo" + File.separator, "", "Vo", TemplateFileFormat.JAVA),
    MAPPER("mapper.ftl", TemplateType.JAVA, "common", File.separator + "mapper" + File.separator, "", "Mapper", TemplateFileFormat.JAVA),
    SERVICE("service.ftl", TemplateType.JAVA, "common", File.separator + "service" + File.separator, "", "Service", TemplateFileFormat.JAVA),
    SERVICE_IMPL("service_impl.ftl", TemplateType.JAVA, "common", File.separator + "service" + File.separator + "impl" + File.separator, "", "ServiceImpl", TemplateFileFormat.JAVA),
    CONTROLLER("controller.ftl", TemplateType.JAVA, "common", File.separator + "controller" + File.separator, "", "Controller", TemplateFileFormat.JAVA),

    APP_SERVICE("app_service.ftl", TemplateType.JAVA, "application", File.separator + "service" + File.separator, "App", "Service", TemplateFileFormat.JAVA),
    APP_SERVICE_IMPL("app_service_impl.ftl", TemplateType.JAVA, "application", File.separator + "service" + File.separator + "impl" + File.separator, "App", "ServiceImpl", TemplateFileFormat.JAVA),
    APP_CONTROLLER("app_controller.ftl", TemplateType.JAVA, "application", File.separator + "controller" + File.separator, "App", "Controller", TemplateFileFormat.JAVA),


    DB_HTML("db_html.ftl", TemplateType.DOC, "", File.separator + "doc" + File.separator, "", "", TemplateFileFormat.HTML),
    DB_MARKDOWN("db_markdown.ftl", TemplateType.DOC, "", File.separator + "doc" + File.separator, "", "", TemplateFileFormat.MD),
    DB_WORD("db_word.ftl", TemplateType.DOC, "", File.separator + "doc" + File.separator, "", "", TemplateFileFormat.DOC),
    SQL("sql.ftl", TemplateType.SQL, "", File.separator + "sql" + File.separator, "", "", TemplateFileFormat.SQL);

    Template(String name, TemplateType type, String project, String relativePath, String namePrefix, String nameSuffix, TemplateFileFormat fileFormat) {
        this.name = name;
        this.type = type;
        this.project = project;
        this.relativePath = relativePath;
        this.namePrefix = namePrefix;
        this.nameSuffix = nameSuffix;
        this.fileFormat = fileFormat;
    }

    /**
     * 模板文件名
     */
    final String name;
    /**
     * 模板类型
     */
    final TemplateType type;
    /**
     * 项目
     */
    final String project;
    /**
     * 相对路径
     */
    final String relativePath;
    /**
     * 名字前缀
     */
    final String namePrefix;
    /**
     * 名字后缀
     */
    final String nameSuffix;
    /**
     * 文件后缀
     */
    final TemplateFileFormat fileFormat;

    public String getName() {
        return name;
    }

    public TemplateType getType() {
        return type;
    }

    public String getProject() {
        return project;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public String getNameSuffix() {
        return nameSuffix;
    }

    public String getFileFormat() {
        return fileFormat.getFormat();
    }

}
