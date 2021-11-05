package ${config.basePackage}.controller;

import ${config.basePackage}.model.${className};
import ${config.basePackage}.service.${className}Service;
import cn.vonce.common.base.BaseController;
import cn.vonce.common.bean.RS;
<#if config.getJavaDocType().name() == 'Swagger'>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * ${tableInfo.comm!} 控制器
 *
 * @author ${config.author!}
 * @version ${config.version!}
 * @email ${config.email!}
 * @date ${date?string('yyyy-MM-dd HH:mm:ss')}
 */
<#if config.getJavaDocType().name() == 'Swagger'>
@Api(value = "${tableInfo.comm!} 控制器")
</#if>
@RequestMapping("/${className?uncap_first}/")
@RestController
public class ${className}Controller<#if config.useSqlBean> extends BaseController</#if> {

    @Autowired
    private ${className}Service ${className?uncap_first}Service;

<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiOperation(value = "根据id查询")
<#else>
    /**
     * 根据id查询
     *
     * @param ${id.name}
     * @return
     */
</#if>
    @GetMapping("getById")
    public <#if config.useSqlBean>RS<#else>${className}</#if> getById(${id.typeName} ${id.name}) {
        ${className} ${className?uncap_first} = ${className?uncap_first}Service.selectById(${id.name});
        return <#if config.useSqlBean>super.successHint("根据id查询成功", ${className?uncap_first})<#else>${className?uncap_first}</#if>;
    }

<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiOperation(value = "查询全部")
<#else>
    /**
     * 查询全部
     *
     * @return
     */
</#if>
    @GetMapping("getAll")
    public <#if config.useSqlBean>RS<#else>List<${className}></#if> getAll() {
        List<${className}> ${className?uncap_first}List = ${className?uncap_first}Service.selectAll();
        return <#if config.useSqlBean>super.successHint("查询全部成功", ${className?uncap_first}List)<#else>${className?uncap_first}List</#if>;
    }

<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiOperation(value = "新增")
<#else>
    /**
     * 新增
     *
     * @param ${className?uncap_first}
     * @return
     */
</#if>
    @PostMapping("add")
    public <#if config.useSqlBean>RS<#else>String</#if> add(@RequestBody ${className} ${className?uncap_first}) {
        int i = ${className?uncap_first}Service.insert(${className?uncap_first});
        if (i > 0) {
            return <#if config.useSqlBean>super.successHint("新增成功")<#else>"新增成功"</#if>;
        }
        return <#if config.useSqlBean>super.othersHint("新增失败")<#else>"新增失败"</#if>;
    }

<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiOperation(value = "根据id修改")
<#else>
    /**
     * 根据id修改
     *
     * @param ${className?uncap_first}
     * @return
     */
</#if>
    @PostMapping("updateById")
    public <#if config.useSqlBean>RS<#else>String</#if> updateById(@RequestBody ${className} ${className?uncap_first}) {
    <#if config.useSqlBean>
        int i = ${className?uncap_first}Service.updateByBeanId(${className?uncap_first}, true, false);
    <#else>
        int i = ${className?uncap_first}Service.updateById(${className?uncap_first});
    </#if>
        if (i > 0) {
            return <#if config.useSqlBean>super.successHint("根据id修改成功")<#else>"根据id修改成功"</#if>;
        }
        return <#if config.useSqlBean>super.othersHint("根据id修改失败")<#else>"根据id修改失败"</#if>;
    }

<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiOperation(value = "根据id删除")
<#else>
    /**
     * 根据id删除
     *
     * @param ${id.name}
     * @return
     */
</#if>
    @PostMapping("deleteById")
    public <#if config.useSqlBean>RS<#else>String</#if> deleteById(${id.typeName} ${id.name}) {
        int i = ${className?uncap_first}Service.deleteById(${id.name});
        if (i > 0) {
            return <#if config.useSqlBean>super.successHint("根据id删除成功")<#else>"根据id删除成功"</#if>;
        }
        return <#if config.useSqlBean>super.othersHint("根据id删除失败")<#else>"根据id删除失败"</#if>;
    }

}
