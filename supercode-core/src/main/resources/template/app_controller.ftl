package ${config.basePackage}<#if config.module?? && config.module!=''>.${config.module!}</#if>.controller;

import ${config.basePackage}.model.${className};
import cn.vonce.sql.page.ResultData;
import ${config.basePackage}.app.service.App${className}Service;
import com.fangte.anime.entity.Result;
import com.fangte.anime.entity.model.AnswerRecordDetail;
import cn.vonce.sql.bean.Select;
import cn.vonce.common.bean.RS;
<#if config.getJavaDocType().name() == 'Swagger'>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * ${tableInfo.remarks!} App控制器
 *
 * @author ${config.author!}
<#if config.version?? && config.version!=''> * @version ${config.version!}</#if>
<#if config.email?? && config.email!=''> * @email ${config.email!}</#if>
 * @date ${date?string('yyyy-MM-dd HH:mm:ss')}
 */
<#if config.getJavaDocType().name() == 'Swagger'>
@Api(tags = "${tableInfo.remarks!} 控制器")
</#if>
@RequestMapping("/app/${className?uncap_first}/")
@RestController
public class App${className}Controller {

    @Autowired
    private App${className}Service app${className}Service;

<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiOperation(value = "根据id查询", response = ${className}.class)
<#else>
    /**
     * 根据id查询
     *
     * @param ${id.name}
     * @return
     */
</#if>
    <#if config.useRestfulApi>@GetMapping("{id}")<#else>@GetMapping("getById")</#if>
    public Result<${className}> getById(<#if config.useRestfulApi>@PathVariable("id") <#else>@RequestParam("id") </#if>${id.typeName} ${id.name}) {
        return app${className}Service.getById(${id.name});
    }

<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiOperation(value = "查询全部", response = ${className}.class)
<#else>
    /**
     * 查询全部
     *
     * @return
     */
</#if>
    @GetMapping("getAll")
    public Result<List<${className}>> getAll() {
        return app${className}Service.getAll();
    }

<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiOperation(value = "分页列表查询")
<#else>
    /**
    * 分页列表查询
    *
    * @return
    */
</#if>
    @GetMapping(value = "list")
    public Result<ResultData<${className}>> list(int pageNum, int pageSize) {
        return app${className}Service.list(pageNum, pageSize);
    }

<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiOperation(value = "新增")
<#else>
    /**
     * 新增
     *
     * @param ${className?uncap_first}CreateDto
     * @return
     */
</#if>
    @PostMapping("add")
    public Result<Void> add(@RequestBody ${className}CreateDto ${className?uncap_first}CreateDto) {
        return app${className}Service.add(${className?uncap_first}CreateDto);
    }

<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiOperation(value = "根据id修改")
<#else>
    /**
     * 根据id修改
     *
     * @param ${className?uncap_first}UpdateDto
     * @return
     */
</#if>
    <#if config.useRestfulApi>@PutMapping("updateById")<#else>@PostMapping("updateById")</#if>
    public Result<Void> updateById(@RequestBody ${className}UpdateDto ${className?uncap_first}UpdateDto) {
        return app${className}Service.updateById(${className?uncap_first}UpdateDto);
    }

<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiOperation(value = "新增或根据id修改")
<#else>
    /**
    * 新增或根据id修改
    *
    * @param ${className?uncap_first}UpdateDto
    * @return
    */
</#if>
    @PostMapping("addOrEdit")
    public Result<Void> addOrEdit(@RequestBody ${className}UpdateDto ${className?uncap_first}UpdateDto) {
        return app${className}Service.addOrEdit(${className?uncap_first}UpdateDto);
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
    <#if config.useRestfulApi>@DeleteMapping("deleteById")<#else>@PostMapping("deleteById")</#if>
    public Result<Void> deleteById(@RequestParam("id") ${id.typeName} ${id.name}) {
        return app${className}Service.deleteById(${id.name});
    }

}