package ${config.basePackage}.controller;

import ${config.basePackage.model.${className};
import ${config.basePackage.service.${className}Service;
import cn.vonce.common.base.BaseController;
import cn.vonce.common.bean.RS;
<#if config.getDocType().name() == 'Swagger'>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ${tableInfo.comment} 控制器
 *
 * @author ${config.author}
 * @version ${config.version}
 * @email ${config.email}
 * @date ${date}
 */
<#if config.getDocType().name() == 'Swagger'>
@Api(description = "${tableInfo.comment} 控制器")
</#if>
@RequestMapping("/${className?cap_first}/")
@RestController
public class ${className}Controller extends BaseController {

    @Autowired
    private ${className}Service ${className?cap_first}Service;

<#if config.getDocType().name() == 'Swagger'>
    @ApiOperation(value = "根据id查询", notes = "")
</#if>
    @GetMapping("getById")
    public RS getById(${id.typeName} ${id.name}) {
        ${className} ${className?cap_first} = ${className?cap_first}Service.selectById(${id.name});
        return super.successHint("根据id查询成功", ${className?cap_first});
    }

<#if config.getDocType().name() == 'Swagger'>
    @ApiOperation(value = "查询全部", notes = "")
</#if>
    @GetMapping("getAll")
    public RS getAll() {
        List<${className}> ${className?cap_first}List = ${className?cap_first}Service.selectAll();
        return super.successHint("查询全部成功", ${className?cap_first}List);
    }

<#if config.getDocType().name() == 'Swagger'>
    @ApiOperation(value = "新增", notes = "")
</#if>
    @PostMapping("add")
    public RS add(@RequestBody ${className} ${className?cap_first}) {
        int i = ${className?cap_first}Service.insert(${className?cap_first});
        if (i > 0) {
            return super.successHint("新增成功");
        }
        return super.othersHint("新增失败");
    }

<#if config.getDocType().name() == 'Swagger'>
    @ApiOperation(value = "根据id修改", notes = "")
</#if>
    @PostMapping("updateById")
    public RS updateById(@RequestBody ${className} ${className?cap_first}) {
    <#if config.useSqlBean == true>
        int i = ${className?cap_first}Service.updateByBeanId(${className?cap_first}, true, false);
    <#else>
        int i = ${className?cap_first}Service.updateById(${className?cap_first});
    </#if>
        if (i > 0) {
            return super.successHint("根据id修改成功");
        }
        return super.othersHint("根据id修改失败");
    }

<#if config.getDocType().name() == 'Swagger'>
    @ApiOperation(value = "根据id删除", notes = "")
</#if>
    @PostMapping("deleteById")
    public RS deleteById(${id.typeName} ${id.name}) {
        int i = ${className?cap_first}Service.deleteById(${id.name});
        if (i > 0) {
            return super.successHint("根据id删除成功");
        }
        return super.othersHint("根据id删除失败");
    }

}
