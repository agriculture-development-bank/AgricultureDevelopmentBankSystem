package com.casic.web.controller.tool;

import com.alibaba.fastjson.JSONObject;
import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.enums.BusinessType;
import com.casic.common.utils.StringUtils;
import com.casic.common.utils.UuidUtils;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.util.ShiroUtils;
import com.casic.framework.web.base.BaseController;
import com.casic.generator.domain.CodeDatasource;
import com.casic.generator.domain.GenCodeComponentTable;
import com.casic.generator.domain.GenCodeDomain;
import com.casic.generator.domain.GenCodeTableColumns;
import com.casic.generator.domain.vo.GenCodeComponentTableVO;
import com.casic.generator.domain.vo.GenCodeComponentVo;
import com.casic.generator.domain.vo.GenCodeTableColumnsVO;
import com.casic.generator.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 表结构设计--控制器
 * @author qh
 * @since 2019-4-25 10:05
 */
@Controller
@RequestMapping(value = "/tool/genCodeComponentTableColumns")
public class GenCodeComponentTableColumnsController extends BaseController {

    private String prefix = "/tool/genCodeComponentTable/genCodeComponentTableColumns";

    @Autowired
    private IGenCodeComponentTableService genCodeComponentTableService;
    @Autowired
    private IGenCodeComponentService genCodeComponentService;
    @Autowired
    private ICodeDatasourceService codeDatasourceService;
    @Autowired
    private IGenCodeDomainService genCodeDomainService;
    @Autowired
    private IGenCodeTableColumnsService genCodeTableColumnsService;

    /**
     * 列表视图
     * @return 列表页面
     */
    @RequiresPermissions("tool:genCodeComponentTableColumns:view")
    @GetMapping("/tableFieldMaintenance/{id}")
    public String view(@PathVariable("id") String id, ModelMap modelMap) {
        modelMap.put("id", id);
        GenCodeComponentTable genCodeComponentTable = genCodeComponentTableService.selectGenCodeComponentTableById(id);
        GenCodeComponentVo genCodeComponentVo = genCodeComponentService.selectGenCodeComponentById(genCodeComponentTable.getComponentId());
        CodeDatasource codeDatasource = codeDatasourceService.selectCodeDatasourceById(genCodeComponentTable.getDatasourceId());
        GenCodeDomain genCodeDomain = genCodeDomainService.selectGenCodeDomainById(genCodeComponentVo.getDomain().getId());
        String ob = JSONObject.toJSONString(genCodeComponentTable);
        GenCodeComponentTableVO genCodeComponentTableVO = JSONObject.parseObject(ob, GenCodeComponentTableVO.class);
        genCodeComponentTableVO.setDataSourceName(codeDatasource.getSourceName());
        genCodeComponentTableVO.setComponentName(genCodeComponentVo.getConmponentName());
        genCodeComponentTableVO.setDomainName(genCodeDomain.getDomainName());
        genCodeComponentTableVO.setDomainCode(genCodeDomain.getDomainCode());
        modelMap.put("genCodeComponentTableVO", genCodeComponentTableVO);
        modelMap.put("tableType", genCodeComponentTable.getTableType());
        return prefix + "/genCodeComponentTableColumns";
    }

    /**
     * 数据库表字段列表
     *
     * @param genCodeTableColumns 模块对应的字段表
     * @return
     */
    @RequiresPermissions("tool:genCodeComponentTableColumns:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GenCodeTableColumns genCodeTableColumns) {
        startPage();
        List<GenCodeTableColumns> list = genCodeTableColumnsService.selectGenCodeTableColumnsList(genCodeTableColumns);
        List<GenCodeTableColumnsVO> genCodeTableColumnsVOList =  new ArrayList<>();
        list.stream().forEach(p -> {
            String isFK = p.getIsFK();
            GenCodeTableColumnsVO genCodeTableColumnsVO = new GenCodeTableColumnsVO();
            if (StringUtils.isNotEmpty(isFK) && "1".equals(isFK)) {
                String ob = JSONObject.toJSONString(p);
                String fkTableName = p.getFkTableName();
                String fkFieldName = p.getFkFieldName();
                genCodeTableColumnsVO = JSONObject.parseObject(ob, GenCodeTableColumnsVO.class);
                GenCodeComponentTable genCodeComponentTable = genCodeComponentTableService.selectGenCodeComponentTableById(fkTableName);
                genCodeTableColumnsVO.setFkTableValue(genCodeComponentTable.getTableEnName());
                GenCodeTableColumns codeTableColumns = genCodeTableColumnsService.selectGenCodeTableColumnsById(fkFieldName);
                genCodeTableColumnsVO.setFkFieldValue(codeTableColumns.getColumnName());
                genCodeTableColumnsVOList.add(genCodeTableColumnsVO);
            } else{ //如果不是外键，不加到列表中？
                String ob = JSONObject.toJSONString(p);
                genCodeTableColumnsVO = JSONObject.parseObject(ob, GenCodeTableColumnsVO.class);
                genCodeTableColumnsVOList.add(genCodeTableColumnsVO);
            }
        });
        return getDataTable(genCodeTableColumnsVOList);
    }

    /**
     * 新增字段视图
     *
     * @param tableId 表id
     * @param modelMap
     * @return
     */
    @GetMapping("/add/{tableId}")
    public String add(@PathVariable("tableId") String tableId, ModelMap modelMap) {
        GenCodeComponentTable genCodeComponentTable = genCodeComponentTableService.selectGenCodeComponentTableById(tableId);
        modelMap.put("genCodeComponentTable", genCodeComponentTable);
        modelMap.put("tableId", tableId);
        String addUrl = "";
        if ("s".equals(genCodeComponentTable.getTableType()) || "m".equals(genCodeComponentTable.getTableType())) {
            addUrl = "/addRegularTableColumns";
        } else if ("r".equals(genCodeComponentTable.getTableType())) {
            addUrl = "/addAssociationTableColumns";
        }
        GenCodeComponentTable codeComponentTable = new GenCodeComponentTable();
        codeComponentTable.setComponentId(genCodeComponentTable.getComponentId());
        codeComponentTable.setDatasourceId(genCodeComponentTable.getDatasourceId());
        List<GenCodeComponentTable> genCodeComponentTables = genCodeComponentTableService.selectGenCodeComponentTableList(codeComponentTable);
        modelMap.put("list", genCodeComponentTables);
        return prefix + addUrl;
    }

    /**
     * 新增表字段
     *
     * @param genCodeTableColumns 模块对应的字段表
     * @return
     */
    @RequiresPermissions("tool:genCodeComponentTableColumns:add")
    @Log(title = "表结构设计", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(GenCodeTableColumns genCodeTableColumns) {
        if (genCodeTableColumns != null && StringUtils.isEmpty(genCodeTableColumns.getId())) {
            genCodeTableColumns.setId(UuidUtils.getUUIDString());
            genCodeTableColumns.setDelFlag("0");
            genCodeTableColumns.setCreateBy(String.valueOf(ShiroUtils.getUserId()));
            genCodeTableColumns.setCreateTime(new Date());
        }
        return toAjax(genCodeTableColumnsService.insertGenCodeTableColumns(genCodeTableColumns));
    }

    /**
     * 编辑视图
     *
     * @param id 主键
     * @param modelMap
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap modelMap) {
        GenCodeTableColumns genCodeTableColumns = genCodeTableColumnsService.selectGenCodeTableColumnsById(id);
        GenCodeComponentTable genCodeComponentTable = genCodeComponentTableService.selectGenCodeComponentTableById(genCodeTableColumns.getTableId());
        modelMap.put("genCodeComponentTable", genCodeComponentTable);
        modelMap.put("genCodeTableColumns", genCodeTableColumns);
        String editUrl = "";
        if ("s".equals(genCodeComponentTable.getTableType()) || "m".equals(genCodeComponentTable.getTableType())) {
            editUrl = "/editRegularTableColumns";
        } else if ("r".equals(genCodeComponentTable.getTableType())) {
            editUrl = "/editAssociationTableColumns";
        }
        GenCodeComponentTable codeComponentTable = new GenCodeComponentTable();
        codeComponentTable.setComponentId(genCodeComponentTable.getComponentId());
        codeComponentTable.setDatasourceId(genCodeComponentTable.getDatasourceId());
        List<GenCodeComponentTable> genCodeComponentTables = genCodeComponentTableService.selectGenCodeComponentTableList(codeComponentTable);
        modelMap.put("list", genCodeComponentTables);
        return prefix + editUrl;
    }

    /**
     * 编辑数据库表字段
     *
     * @param genCodeTableColumns 模块对应的字段表
     * @return
     */
    @RequiresPermissions("tool:genCodeComponentTableColumns:edit")
    @Log(title = "表结构设计", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(GenCodeTableColumns genCodeTableColumns) {
        genCodeTableColumns.setUpdateBy(String.valueOf(ShiroUtils.getUserId()));
        genCodeTableColumns.setUpdateTime(new Date());
        return toAjax(genCodeTableColumnsService.updateGenCodeTableColumns(genCodeTableColumns));
    }

    /**
     * 删除表结构设计
     *
     * @param ids 需要删除的数据主键字符串
     * @return
     */
    @RequiresPermissions("tool:genCodeComponentTableColumns:remove")
    @Log(title = "表结构设计", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(genCodeTableColumnsService.deleteGenCodeTableColumnsByIds(ids));
    }

    /**
     * 校验字段的唯一性
     *
     * @param fieldValue
     * @return
     */
    @PostMapping("/checkFieldUnique")
    @ResponseBody
    public String checkFieldUnique(GenCodeTableColumns codeTableColumns, String fieldValue, String filedName) {
        return genCodeTableColumnsService.checkFieldUnique(codeTableColumns, StringUtils.trim(filedName), fieldValue);
    }

    /**
     * 根据id查询表中的字段
     *
     * @param tableId
     * @return
     */
    @PostMapping("/selectTableFieldByTableId/{tableId}")
    @ResponseBody
    public List selectTableFieldByTableId(@PathVariable("tableId") String tableId) {
        return genCodeComponentTableService.selectTableFieldByTableId(tableId);
    }

}
