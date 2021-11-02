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
public class ${className}Controller extends BaseController {

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
    public RS getById(${id.typeName} ${id.name}) {
        ${className} ${className?uncap_first} = ${className?uncap_first}Service.selectById(${id.name});
        return super.successHint("根据id查询成功", ${className?uncap_first});
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
    public RS getAll() {
        List<${className}> ${className?uncap_first}List = ${className?uncap_first}Service.selectAll();
        return super.successHint("查询全部成功", ${className?uncap_first}List);
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
    public RS add(@RequestBody ${className} ${className?uncap_first}) {
        int i = ${className?uncap_first}Service.insert(${className?uncap_first});
        if (i > 0) {
            return super.successHint("新增成功");
        }
        return super.othersHint("新增失败");
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
    public RS updateById(@RequestBody ${className} ${className?uncap_first}) {
    <#if config.useSqlBean>
        int i = ${className?uncap_first}Service.updateByBeanId(${className?uncap_first}, true, false);
    <#else>
        int i = ${className?uncap_first}Service.updateById(${className?uncap_first});
    </#if>
        if (i > 0) {
            return super.successHint("根据id修改成功");
        }
        return super.othersHint("根据id修改失败");
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
    public RS deleteById(${id.typeName} ${id.name}) {
        int i = ${className?uncap_first}Service.deleteById(${id.name});
        if (i > 0) {
            return super.successHint("根据id删除成功");
        }
        return super.othersHint("根据id删除失败");
    }

}
