package com.casic.web.controller.tool;

import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.enums.BusinessType;
import com.casic.common.support.Convert;
import com.casic.common.utils.StringUtils;
import com.casic.common.utils.UuidUtils;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.util.ShiroUtils;
import com.casic.framework.web.base.BaseController;
import com.casic.generator.domain.GenCodeComponentTable;
import com.casic.generator.domain.GenCodeTableColumns;
import com.casic.generator.domain.GenCodeTemplateCfgColumns;
import com.casic.generator.service.IGenCodeComponentTableService;
import com.casic.generator.service.IGenCodeFunctionService;
import com.casic.generator.service.IGenCodeTableColumnsService;
import com.casic.generator.service.IGenCodeTemplateCfgColumnsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 功能表字段设计
 */
@Controller
@RequestMapping("/tool/genCodeFunctionTableField")
public class GenCodeFunctionTableFieldController extends BaseController {

    private String prefix = "/tool/genCodeComponentTable/genCodeFunctionTableField";

    @Autowired
    private IGenCodeFunctionService genCodeFunctionService;
    @Autowired
    private IGenCodeComponentTableService genCodeComponentTableService;
    @Autowired
    private IGenCodeTableColumnsService genCodeTableColumnsService;
    @Autowired
    private IGenCodeTemplateCfgColumnsService genCodeTemplateCfgColumnsService;

    /**
     * 字段设计
     *
     * @param id  功能id
     * @return
     */
    @RequiresPermissions("tool:genCodeFunctionTableField:view")
    @GetMapping("/fieldDesign/{id}")
    public String view(@PathVariable("id") String id, ModelMap modelMap) {
        List<Map> maps = genCodeFunctionService.selectTableByFunctionId(id);
        maps.stream().forEach(p -> {
            GenCodeComponentTable genCodeComponentTable = genCodeComponentTableService.selectGenCodeComponentTableById(p.get("table_id").toString());
            p.put("tableName", genCodeComponentTable.getTableName());
            p.put("tableEnName", genCodeComponentTable.getTableEnName());
        });
        modelMap.put("id", id);
        modelMap.put("maps", maps);
        return prefix + "/genCodeFunctionTableField";
    }

    /**
     * 列表
     * @param genCodeTemplateCfgColumns
     * @return
     */
    @RequiresPermissions("tool:genCodeFunctionTableField:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GenCodeTemplateCfgColumns genCodeTemplateCfgColumns) {
       startPage();
        List<GenCodeTemplateCfgColumns> list = genCodeTemplateCfgColumnsService.selectGenCodeTemplateCfgColumnsList(genCodeTemplateCfgColumns);
        return getDataTable(list);
    }

    /**
     * 新增视图
     *
     * @param tableId  表主键
     * @param functionId 功能id
     * @param modelMap
     * @return
     */
    @GetMapping("/add/{tableId}/{functionId}")
    public String add(@PathVariable("tableId") String tableId, @PathVariable("functionId") String functionId, ModelMap modelMap) {
        List<GenCodeTableColumns> list = genCodeTableColumnsService.selectGenCodeTableColumnsByTableId(tableId);
        GenCodeComponentTable genCodeComponentTable = genCodeComponentTableService.selectGenCodeComponentTableById(tableId);
        modelMap.put("id", tableId);
        modelMap.put("functionId", functionId);
        modelMap.put("componentId", genCodeComponentTable.getComponentId());
        modelMap.put("list", list);
        return prefix + "/addTemplateField";
    }

    /**
     * 添加字段
     *
     * @param genCodeTemplateCfgColumns
     * @param colsId
     * @return
     */
    @RequiresPermissions("tool:genCodeFunctionTableField:add")
    @Log(title = "字段维护", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(GenCodeTemplateCfgColumns genCodeTemplateCfgColumns, String colsId) {
        if (genCodeTemplateCfgColumns != null && StringUtils.isEmpty(genCodeTemplateCfgColumns.getId())) {
            genCodeTemplateCfgColumns.setDelFlag("0");
            genCodeTemplateCfgColumns.setCreateBy(String.valueOf(ShiroUtils.getUserId()));
            genCodeTemplateCfgColumns.setCreateTime(new Date());
        }
        //根据表字段id查询表字段信息
        String[] ids = Convert.toStrArray(colsId);
        AtomicInteger flag = new AtomicInteger();
        Arrays.stream(ids).forEach(p -> {
            GenCodeTableColumns genCodeTableColumns = genCodeTableColumnsService.selectGenCodeTableColumnsById(p);
            genCodeTemplateCfgColumns.setTableColumnId(p);
            genCodeTemplateCfgColumns.setId(UuidUtils.getUUIDString());
            genCodeTemplateCfgColumns.setJavaField(genCodeTableColumns.getJavaField());
            genCodeTemplateCfgColumns.setJavaType(genCodeTableColumns.getJavaType());
            genCodeTemplateCfgColumns.setJdbcType(genCodeTableColumns.getJdbcType());
            genCodeTemplateCfgColumns.setColumnName(genCodeTableColumns.getColumnName());
            genCodeTemplateCfgColumns.setComments(genCodeTableColumns.getComments());
            genCodeTemplateCfgColumns.setIsPk(genCodeTableColumns.getIsPk());
            genCodeTemplateCfgColumns.setIsNull(genCodeTableColumns.getIsNull());
            genCodeTemplateCfgColumns.setDictType(genCodeTableColumns.getDictType());
            genCodeTemplateCfgColumns.setSort(genCodeTableColumns.getSort());
            genCodeTemplateCfgColumns.setSettings(genCodeTableColumns.getSettings());
            genCodeTemplateCfgColumns.setRemark(genCodeTableColumns.getRemark());
            flag.set(genCodeTemplateCfgColumnsService.insertGenCodeTemplateCfgColumns(genCodeTemplateCfgColumns));
        });
        return toAjax(flag.get());
    }

    /**
     * 编辑
     *
     * @param id
     * @param modelMap
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap modelMap) {
        GenCodeTemplateCfgColumns genCodeTemplateCfgColumns = genCodeTemplateCfgColumnsService.selectGenCodeTemplateCfgColumnsById(id);
        GenCodeComponentTable genCodeComponentTable = genCodeComponentTableService.selectGenCodeComponentTableById(genCodeTemplateCfgColumns.getTableId());
        modelMap.put("genCodeTemplateCfgColumns", genCodeTemplateCfgColumns);
        modelMap.put("genCodeComponentTable", genCodeComponentTable);
        modelMap.put("id", id);
        return prefix + "/editTemplateField";
    }

    /**
     * 编辑字段
     * @param genCodeTemplateCfgColumns
     * @return
     */
    @RequiresPermissions("tool:genCodeFunctionTableField:edit")
    @Log(title = "字段维护", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(GenCodeTemplateCfgColumns genCodeTemplateCfgColumns) {
        genCodeTemplateCfgColumns.setUpdateTime(new Date());
        genCodeTemplateCfgColumns.setUpdateBy(String.valueOf(ShiroUtils.getUserId()));
        return toAjax(genCodeTemplateCfgColumnsService.updateGenCodeTemplateCfgColumns(genCodeTemplateCfgColumns));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @RequiresPermissions("tool:genCodeFunctionTableField:remove")
    @Log(title = "字段维护", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(genCodeTemplateCfgColumnsService.deleteGenCodeTemplateCfgColumnsByIds(ids));
    }
}
