package ${config.basePackage}<#if config.module?? && config.module!=''>.${config.module!}</#if>.controller;

import cn.vonce.sql.page.ResultData;
import ${config.basePackage}.model.entity.${className};
import ${config.basePackage}.model.dto.${className}CreateDto;
import ${config.basePackage}.model.dto.${className}QueryDto;
import ${config.basePackage}.model.dto.${className}UpdateDto;
import ${config.basePackage}.service.${className}Service;
import cn.vonce.sql.bean.Select;
import ${config.basePackage}.model.Result;
<#if config.getJavaDocType().name() == 'Swagger'>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.beans.BeanUtils;

/**
 * ${tableInfo.remarks!} 控制器
 *
 * @author ${config.author!}<#if config.email?? && config.email!=''>《${config.email!}》</#if>
 * @version ${config.version!}《${date?string('yyyy-MM-dd HH:mm:ss')}》
 */
<#if config.getJavaDocType().name() == 'Swagger'>
@Api(tags = "${tableInfo.remarks!} 控制器")
</#if>
@RequestMapping("${config.urlPrefix!}/${className?uncap_first}")
@RestController
public class ${className}Controller {

    @Autowired
    private ${className}Service ${className?uncap_first}Service;

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
    <#if config.useRestfulApi>@GetMapping("/{id}")<#else>@GetMapping("/info")</#if>
    public Result<${className}> info(<#if config.useRestfulApi>@PathVariable("id") <#else>@RequestParam("id") </#if>${id.typeName} ${id.name}) {
        ${className} ${className?uncap_first} = ${className?uncap_first}Service.selectById(${id.name});
        return <#if config.useSqlBean>Result.success("根据id查询成功", ${className?uncap_first})<#else>${className?uncap_first}</#if>;
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
    @GetMapping("/all")
    public Result<List<${className}>> all() {
        List<${className}> ${className?uncap_first}List = ${className?uncap_first}Service.select();
        return <#if config.useSqlBean>Result.success("查询全部成功", ${className?uncap_first}List)<#else>${className?uncap_first}List</#if>;
    }

<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiOperation(value = "分页列表查询")
<#else>
    /**
    * 分页列表查询
    *
    * @param dto
    * @return
    */
</#if>
    @GetMapping(value = "/list")
    public Result<ResultData<${className}>> list(${className}QueryDto dto) {
        Select select = new Select();
        ResultData<${className}> resultData = ${className?uncap_first}Service.paging(select, dto.getPageNum(), dto.getPageSize());
        return Result.success("获取列表成功", resultData);
    }

<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiOperation(value = "新增")
<#else>
    /**
     * 新增
     *
     * @param dto
     * @return
     */
</#if>
    <#if config.useRestfulApi>@PostMapping<#else>@PostMapping("/add")</#if>
    public Result<?> add(@RequestBody ${className}CreateDto dto) {
        ${className} ${className?uncap_first} = new ${className}();
        BeanUtils.copyProperties(dto, ${className?uncap_first});
        int i = ${className?uncap_first}Service.insert(${className?uncap_first});
        if (i > 0) {
            return <#if config.useSqlBean>Result.success()<#else>"新增成功"</#if>;
        }
        return <#if config.useSqlBean>Result.fail("新增失败")<#else>"新增失败"</#if>;
    }

<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiOperation(value = "根据id修改")
<#else>
    /**
     * 根据id修改
     *
     * @param dto
     * @return
     */
</#if>
    <#if config.useRestfulApi>@PutMapping<#else>@PostMapping("/updateById")</#if>
    public Result<?> updateById(@RequestBody ${className}UpdateDto dto) {
        ${className} ${className?uncap_first} = new ${className}();
        BeanUtils.copyProperties(dto, ${className?uncap_first});
    <#if config.useSqlBean>
        int i = ${className?uncap_first}Service.updateByBeanId(${className?uncap_first});
    <#else>
        int i = ${className?uncap_first}Service.updateById(${className?uncap_first});
    </#if>
        if (i > 0) {
            return <#if config.useSqlBean>Result.success()<#else>"根据id修改成功"</#if>;
        }
        return <#if config.useSqlBean>Result.fail("根据id修改失败")<#else>"根据id修改失败"</#if>;
    }

<#if config.getJavaDocType().name() == 'Swagger'>
    @ApiOperation(value = "新增或根据id修改")
<#else>
    /**
    * 新增或编辑
    *
    * @param dto
    * @return
    */
</#if>
    @PostMapping("/addOrEdit")
    public Result<?> addOrEdit(@RequestBody ${className}UpdateDto dto) {
        if (dto.getId() == null) {
            ${className}CreateDto createDto = new ${className}CreateDto();
            return add(createDto);
        }
        return updateById(dto);
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
    <#if config.useRestfulApi>@DeleteMapping("/{id}")<#else>@PostMapping("/deleteById")</#if>
    public Result<?> deleteById(<#if config.useRestfulApi>@PathVariable("id")<#else>@RequestParam("id")</#if> ${id.typeName} ${id.name}) {
        int i = ${className?uncap_first}Service.deleteById(${id.name});
        if (i > 0) {
            return <#if config.useSqlBean>Result.success()<#else>"根据id删除成功"</#if>;
        }
        return <#if config.useSqlBean>Result.fail("根据id删除失败")<#else>"根据id删除失败"</#if>;
    }

}
