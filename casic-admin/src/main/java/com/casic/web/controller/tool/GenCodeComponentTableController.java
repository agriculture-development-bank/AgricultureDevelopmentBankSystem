package com.casic.web.controller.tool;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.enums.BusinessType;
import com.casic.common.utils.JdbcTypeConvertJavaType;
import com.casic.common.utils.StringHelper;
import com.casic.common.utils.StringUtils;
import com.casic.common.utils.UuidUtils;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.util.ShiroUtils;
import com.casic.framework.web.base.BaseController;
import com.casic.generator.domain.CodeDatasource;
import com.casic.generator.domain.GenCodeComponentTable;
import com.casic.generator.domain.GenCodeTableColumns;
import com.casic.generator.domain.gen.TableInfo;
import com.casic.generator.domain.vo.GenCodeComponentTableVO;
import com.casic.generator.domain.vo.GenCodeComponentVo;
import com.casic.generator.service.ICodeDatasourceService;
import com.casic.generator.service.IGenCodeComponentService;
import com.casic.generator.service.IGenCodeComponentTableService;
import com.casic.generator.service.IGenCodeTableColumnsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 组件对应的数据库表设计-控制器
 * @author qh
 * @since 2019-4-22 17:47
 */
@Controller
@RequestMapping("/tool/genCodeComponentTable")
public class GenCodeComponentTableController extends BaseController {

    private String prefix = "/tool/genCodeComponentTable";

    @Autowired
    private IGenCodeComponentTableService genCodeComponentTableService;
    @Autowired
    private IGenCodeComponentService genCodeComponentService;
    @Autowired
    private ICodeDatasourceService codeDatasourceService;
    @Autowired
    private IGenCodeTableColumnsService genCodeTableColumnsService;

    /**
     * 列表视图
     * @return 列表页面
     */
    @RequiresPermissions("tool:genCodeComponentTable:view")
    @GetMapping("/componentTableDesign/{id}")
    public String view(@PathVariable("id") String id, ModelMap modelMap) {
        modelMap.put("id", id);
        return prefix + "/genCodeComponentTable";
    }

    /**
     * 数据源视图
     * @param sourceType
     * @param modelMap
     * @return
     */
    @RequiresPermissions("tool:genCodeComponentTable:view")
    @GetMapping("/selectView/{sourceType}")
    public String selectView(@PathVariable("sourceType") String sourceType, ModelMap modelMap) {
        CodeDatasource codeDatasource = new CodeDatasource();
        List<CodeDatasource> codeDatasources = codeDatasourceService.selectCodeDatasourceList(codeDatasource);
        if ("1".equals(sourceType)) {
            codeDatasources = codeDatasources.stream().filter(p -> "基础平台数据库".equals(p.getSourceName())).collect(Collectors.toList());
        }
        modelMap.put("codeDatasources", codeDatasources);
        return prefix + "/selectView";
    }

    /**
     * 行业领域管理列表
     * @param genCodeComponentTable 数据库设计表
     * @return 列表数据
     */
    @RequiresPermissions("tool:genCodeComponentTable:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GenCodeComponentTable genCodeComponentTable) {
        startPage();
        List<GenCodeComponentTable> list = genCodeComponentTableService.selectGenCodeComponentTableList(genCodeComponentTable);
        List<GenCodeComponentTableVO> genCodeComponentTableVOS = new ArrayList<>();
        list.stream().forEach(p -> {
            GenCodeComponentVo genCodeComponentVo = genCodeComponentService.selectGenCodeComponentById(p.getComponentId());
            String ob = JSONObject.toJSONString(p);
            GenCodeComponentTableVO genCodeComponentTableVO = JSONObject.parseObject(ob, GenCodeComponentTableVO.class);
            genCodeComponentTableVO.setComponentName(genCodeComponentVo.getConmponentName());
            CodeDatasource codeDatasource = codeDatasourceService.selectCodeDatasourceById(p.getDatasourceId());
            genCodeComponentTableVO.setDataSourceName(codeDatasource.getSourceName());
            genCodeComponentTableVOS.add(genCodeComponentTableVO);
        });
        return getDataTable(genCodeComponentTableVOS);
    }

    /**
     * 新增视图
     * @param componentId 组件类型
     * @param tableType 表类型
     * @param modelMap
     * @return
     */
    @GetMapping("/add/{componentId}/{tableType}")
    public String add(@PathVariable("componentId") String componentId,@PathVariable("tableType") String tableType, ModelMap modelMap) {
        GenCodeComponentVo genCodeComponent = genCodeComponentService.selectGenCodeComponentById(componentId);
        modelMap.put("genCodeComponent", genCodeComponent);
        modelMap.put("componentId", componentId);
        String addUrl = "";
        if ("s".equals(tableType) || "m".equals(tableType)) {
            addUrl = "/addRegularTable";
        } else if ("r".equals(tableType)){
            addUrl = "/addAssociationTable";
        }
        GenCodeComponentTable genCodeComponentTable = new GenCodeComponentTable();
        genCodeComponentTable.setComponentId(componentId);
        genCodeComponentTable.setDatasourceId(genCodeComponent.getDatasourceId());
        List<GenCodeComponentTable> genCodeComponentTables = genCodeComponentTableService.selectGenCodeComponentTableList(genCodeComponentTable);
        modelMap.put("list", genCodeComponentTables);
        return prefix + addUrl;
    }

    /**
     * 新增数据库表
     * @param genCodeComponentTable 数据库设计实体类
     * @return
     */
    @RequiresPermissions("tool:genCodeComponentTable:add")
    @Log(title = "数据库表设计", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult addSave(GenCodeComponentTable genCodeComponentTable) {
        String uuid = UuidUtils.getUUIDString();
        if (genCodeComponentTable != null && StringUtils.isEmpty(genCodeComponentTable.getId())) {
            genCodeComponentTable.setId(uuid);
            genCodeComponentTable.setDelFlag("0");
            genCodeComponentTable.setCreateBy(String.valueOf(ShiroUtils.getUserId()));
            genCodeComponentTable.setCreateTime(new Date());
            CodeDatasource codeDatasource = new CodeDatasource();
            codeDatasource.setSourceName("基础平台数据库");
            List<CodeDatasource> codeDatasources = codeDatasourceService.selectCodeDatasourceList(codeDatasource);
            genCodeComponentTable.setDatasourceId(codeDatasources.get(0).getId());
        }
        int flag = genCodeComponentTableService.insertGenCodeComponentTable(genCodeComponentTable);
        if ("m".equals(genCodeComponentTable.getTableType()) || "s".equals(genCodeComponentTable.getTableType())) {
            AtomicInteger s = new AtomicInteger();
            if (flag == 1) {
                //插入公共字段
                String str = "";
                /*String str = "[\n" +
                        "    {\n" +
                        "        \"id\": '"+ UuidUtils.getUUIDString()+"'" +",\n" +
                        "        \"tableId\": '"+ uuid+"'"+"," +
                        "        \"columnName\": \"id\"," +
                        "        \"columnZhName\": \"主键\",\n" +
                        "        \"comments\": \"主键\",\n" +
                        "        \"jdbcType\": \"varchar\",\n" +
                        "        \"javaType\": \"String\",\n" +
                        "        \"javaField\": \"id\",\n" +
                        "        \"len\": 36,\n" +
                        "        \"isFk\": 0,\n" +
                        "        \"isPk\": 1,\n" +
                        "        \"isNull\": 0,\n" +
                        "        \"sort\": 1,\n" +
                        "        \"delFlag\": 0,\n" +
                        "        \"createTime\": 0,\n" +
                        "        \"createBy\": 1,\n" +
                        "        \"updateTime\": \"\",\n" +
                        "        \"updateBy\": \"\",\n" +
                        "        \"remark\": \"\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"id\": '"+ UuidUtils.getUUIDString()+"'" +",\n" +
                        "        \"tableId\": '"+ uuid+"'"+",\n" +
                        "        \"columnName\": \"del_flag\",\n" +
                        "        \"columnZhName\": \"删除标志\",\n" +
                        "        \"comments\": \"删除标志\",\n" +
                        "        \"jdbcType\": \"char\",\n" +
                        "        \"javaType\": \"String\",\n" +
                        "        \"javaField\": \"delFlag\",\n" +
                        "        \"len\": 1,\n" +
                        "        \"isFk\": 0,\n" +
                        "        \"isPk\": 0,\n" +
                        "        \"isNull\": 0,\n" +
                        "        \"sort\": 2,\n" +
                        "        \"delFlag\": 0,\n" +
                        "        \"createTime\": 0,\n" +
                        "        \"createBy\": 1,\n" +
                        "        \"updateTime\": \"\",\n" +
                        "        \"updateBy\": \"\",\n" +
                        "        \"remark\": \"\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"id\": '"+ UuidUtils.getUUIDString()+"'" +",\n" +
                        "        \"tableId\": '"+ uuid+"'"+",\n" +
                        "        \"columnName\": \"create_by\",\n" +
                        "        \"columnZhName\": \"创建人\"," +
                        "        \"comments\": \"创建人\",\n" +
                        "        \"jdbcType\": \"varchar\",\n" +
                        "        \"javaType\": \"String\",\n" +
                        "        \"javaField\": \"createBy\",\n" +
                        "        \"len\": 36,\n" +
                        "        \"isPk\": 0,\n" +
                        "        \"isFk\": 0,\n" +
                        "        \"isNull\": 0,\n" +
                        "        \"sort\": 3,\n" +
                        "        \"delFlag\": 0,\n" +
                        "        \"createTime\": 0,\n" +
                        "        \"createBy\": 1,\n" +
                        "        \"updateTime\": \"\",\n" +
                        "        \"updateBy\": \"\",\n" +
                        "        \"remark\": \"\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"id\": '"+ UuidUtils.getUUIDString()+"'" +",\n" +
                        "        \"tableId\": '"+ uuid+"'"+",\n" +
                        "        \"columnName\": \"create_time\",\n" +
                        "        \"columnZhName\": \"创建日期\",\n" +
                        "        \"comments\": \"创建日期\",\n" +
                        "        \"jdbcType\": \"datetime\",\n" +
                        "        \"javaType\": \"Date\",\n" +
                        "        \"javaField\": \"createTime\",\n" +
                        "        \"len\": \"\",\n" +
                        "        \"isFk\": 0,\n" +
                        "        \"isPk\": 0,\n" +
                        "        \"isNull\": 0,\n" +
                        "        \"sort\": 4,\n" +
                        "        \"delFlag\": 0,\n" +
                        "        \"createTime\": 0,\n" +
                        "        \"createBy\": 1,\n" +
                        "        \"updateTime\": \"\",\n" +
                        "        \"updateBy\": \"\",\n" +
                        "        \"remark\": \"\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"id\": '"+ UuidUtils.getUUIDString()+"'" +",\n" +
                        "        \"tableId\": '"+ uuid+"'"+",\n" +
                        "        \"columnName\": \"update_by\",\n" +
                        "        \"columnZhName\": \"更新人\",\n" +
                        "        \"comments\": \"更新人\",\n" +
                        "        \"jdbcType\": \"varchar\",\n" +
                        "        \"javaType\": \"String\",\n" +
                        "        \"javaField\": \"updateBy\",\n" +
                        "        \"len\": 36,\n" +
                        "        \"isFk\": 0,\n" +
                        "        \"isPk\": 0,\n" +
                        "        \"isNull\": 0,\n" +
                        "        \"sort\": 5,\n" +
                        "        \"delFlag\": 0,\n" +
                        "        \"createTime\": 0,\n" +
                        "        \"createBy\": 1,\n" +
                        "        \"updateTime\": \"\",\n" +
                        "        \"updateBy\": \"\",\n" +
                        "        \"remark\": \"\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"id\": '"+ UuidUtils.getUUIDString()+"'" +",\n" +
                        "        \"tableId\": '"+ uuid+"'"+",\n" +
                        "        \"columnName\": \"update_time\",\n" +
                        "        \"columnZhName\": \"更新日期\",\n" +
                        "        \"comments\": \"更新日期\",\n" +
                        "        \"jdbcType\": \"datetime\",\n" +
                        "        \"javaType\": \"Date\",\n" +
                        "        \"javaField\": \"updateTime\",\n" +
                        "        \"len\": \"\",\n" +
                        "        \"isFk\": 0,\n" +
                        "        \"isPk\": 0,\n" +
                        "        \"isNull\": 0,\n" +
                        "        \"sort\": 6,\n" +
                        "        \"delFlag\": 0,\n" +
                        "        \"createTime\": 0,\n" +
                        "        \"createBy\": 1,\n" +
                        "        \"updateTime\": \"\",\n" +
                        "        \"updateBy\": \"\",\n" +
                        "        \"remark\": \"\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"id\": '"+ UuidUtils.getUUIDString()+"'" +",\n" +
                        "        \"tableId\": '"+ uuid+"'"+",\n" +
                        "        \"columnName\": \"remark\",\n" +
                        "        \"columnZhName\": \"备注\",\n" +
                        "        \"comments\": \"备注\",\n" +
                        "        \"jdbcType\": \"varchar\",\n" +
                        "        \"javaType\": \"String\",\n" +
                        "        \"javaField\": \"remark\",\n" +
                        "        \"len\": 255,\n" +
                        "        \"isFk\": 0,\n" +
                        "        \"isPk\": 0,\n" +
                        "        \"isNull\": 0,\n" +
                        "        \"sort\": 7,\n" +
                        "        \"delFlag\": 0,\n" +
                        "        \"createTime\": 0,\n" +
                        "        \"createBy\": 1,\n" +
                        "        \"updateTime\": \"\",\n" +
                        "        \"updateBy\": \"\",\n" +
                        "        \"remark\": \"\"\n" +
                        "    }\n" +
                        "]";*/
                List<GenCodeTableColumns> objs = JSON.parseArray(str,GenCodeTableColumns.class);
                objs.stream().forEach(p -> {
                    s.set(genCodeTableColumnsService.insertGenCodeTableColumns(p));
                });
            }
        }
        return toAjax(flag);
    }


    /**
     * 编辑视图
     * @param id 主键
     * @param modelMap
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap modelMap) {
        GenCodeComponentTable genCodeComponentTable = genCodeComponentTableService.selectGenCodeComponentTableById(id);
        GenCodeComponentVo genCodeComponent = genCodeComponentService.selectGenCodeComponentById(genCodeComponentTable.getComponentId());
        modelMap.put("genCodeComponentTable", genCodeComponentTable);
        modelMap.put("genCodeComponent", genCodeComponent);
        modelMap.put("domainName", genCodeComponent.getDomain().getDomainName());
        modelMap.put("domainCode", genCodeComponent.getDomain().getDomainCode());
        modelMap.put("conmponentName", genCodeComponent.getConmponentName());
        modelMap.put("tableType", genCodeComponentTable.getTableType());


        GenCodeComponentTable codeComponentTable = new GenCodeComponentTable();
        codeComponentTable.setComponentId(genCodeComponentTable.getComponentId());
        codeComponentTable.setDatasourceId(genCodeComponentTable.getDatasourceId());
        List<GenCodeComponentTable> genCodeComponentTables = genCodeComponentTableService.selectGenCodeComponentTableList(codeComponentTable);
        modelMap.put("list", genCodeComponentTables);
        String editUrl = "";
        if ("s".equals(genCodeComponentTable.getTableType()) || "m".equals(genCodeComponentTable.getTableType())) {
            editUrl = "/editRegularTable";
        } else if ("r".equals(genCodeComponentTable.getTableType())){
            editUrl = "/editAssociationTable";
        }
        return prefix + editUrl;
    }

    /**
     * 编辑数据库设计
     * @param genCodeComponentTable 数据库设计表
     * @return
     */
    @RequiresPermissions("tool:genCodeComponentTable:edit")
    @Log(title = "数据库表设计", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(GenCodeComponentTable genCodeComponentTable) {
        genCodeComponentTable.setUpdateBy(String.valueOf(ShiroUtils.getUserId()));
        genCodeComponentTable.setUpdateTime(new Date());
        return toAjax(genCodeComponentTableService.updateGenCodeComponentTable(genCodeComponentTable));
    }

    /**
     * 删除数据库设计
     *
     * @param ids 主键字符串
     * @return
     */
    @RequiresPermissions("tool:genCodeComponentTable:remove")
    @Log(title = "数据库表设计", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(genCodeComponentTableService.deleteGenCodeComponentTableByIds(ids));
    }

    @RequiresPermissions("tool:genCodeComponentTable:tableList")
    @PostMapping("/tableList")
    @ResponseBody
    public TableDataInfo tableList(TableInfo tableInfo, CodeDatasource codeDatasource) {
        startPage();
        List<TableInfo> tableInfos = genCodeComponentTableService.selectTableList(tableInfo.getTableName(), codeDatasource.getDatabaseName());
        return getDataTable(tableInfos);
    }

    /**
     * 从本地仓库导入
     *
     * @param tableNames
     * @return
     */
    @RequestMapping("/importByLocal")
    @ResponseBody
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public AjaxResult importByLocal(@RequestParam(value = "tableNames[]")String[] tableNames, @RequestParam("componentId") String componentId) {
        List<String> list = Arrays.asList(tableNames);
        String tableSchema = "casic_manage_system";
        GenCodeComponentVo genCodeComponentVo = genCodeComponentService.selectGenCodeComponentById(componentId);
        AtomicInteger flag = new AtomicInteger(0);
        list.stream().forEach(tableName -> {
            //根据表名查询表信息
            Map<String, Object> map = genCodeComponentTableService.selectTableInfoByTableNameAndTableSchema(tableSchema, tableName);
            String tableId = UuidUtils.getUUIDString();
            int i = saveByImportByLocal(componentId, tableId, genCodeComponentVo, tableName, map);
            flag.set(i);
            if (flag.get() > 0) {
                //插入表中的字段
                List<Map<String, Object>> maps = genCodeComponentTableService.selectColumnsByTableSchemaAndTableName(tableSchema, tableName);
                saveTableColumn(tableId, componentId, maps);
            }
        });
        return toAjax(flag.get());
    }

    private void saveTableColumn(String tableId, String componentId, List<Map<String, Object>> maps) {
        maps.stream().forEach(s -> {
            GenCodeTableColumns genCodeTableColumns = new GenCodeTableColumns();
            genCodeTableColumns.setId(UuidUtils.getUUIDString());
            genCodeTableColumns.setTableId(tableId);
            genCodeTableColumns.setColumnName(s.get("COLUMN_NAME") != null ? s.get("COLUMN_NAME").toString() : "");
            genCodeTableColumns.setColumnZhName(s.get("COLUMN_COMMENT") != null ? s.get("COLUMN_COMMENT").toString() : "");
            genCodeTableColumns.setComments(s.get("COLUMN_COMMENT") != null ? s.get("COLUMN_COMMENT").toString() : "");
            genCodeTableColumns.setJdbcType(s.get("DATA_TYPE").toString());
            genCodeTableColumns.setJavaField(StringHelper.lineToHump(s.get("COLUMN_NAME").toString()));
            if (s.get("CHARACTER_MAXIMUM_LENGTH") != null && StringUtils.isNotEmpty(s.get("CHARACTER_MAXIMUM_LENGTH").toString())) {
                genCodeTableColumns.setLen(Integer.parseInt(s.get("CHARACTER_MAXIMUM_LENGTH").toString()));
            }
            if (s.get("COLUMN_KEY") != null && StringUtils.isNotEmpty(s.get("COLUMN_KEY").toString())) {
                if ("PRI".equalsIgnoreCase(s.get("COLUMN_KEY").toString())) {
                    genCodeTableColumns.setIsPk("1");
                    genCodeTableColumns.setIsFK("0");
                } else if ("MUL".equalsIgnoreCase(s.get("COLUMN_KEY").toString())){
                    //设置外键
                    genCodeTableColumns.setIsPk("0");
                    genCodeTableColumns.setIsFK("1");
                    //根据外键设置外键表和关联字段
                    String table_name = s.get("TABLE_NAME").toString();
                    String column_name = s.get("COLUMN_NAME").toString();
                    String table_schema = s.get("TABLE_SCHEMA").toString();
                    Map<String, Object>  parasms= genCodeComponentTableService.selectTableByTableSchemaAndTableName(table_schema, table_name, column_name);
                    if (parasms != null && parasms.size() > 0) {
                        String referenced_table_name = parasms.get("referenced_table_name") != null ? parasms.get("referenced_table_name").toString() : "";
                        String referenced_column_name = parasms.get("referenced_column_name") != null ? parasms.get("referenced_column_name").toString() : "";
                        //1、设置外键所在表
                        GenCodeComponentTable componentTable = getGenCodeComponentTable(componentId, referenced_table_name);
                        genCodeTableColumns.setFkTableName(componentTable.getId());
                        //2、设置外键所属列
                        GenCodeTableColumns codeTableColumns = new GenCodeTableColumns();
                        codeTableColumns.setTableId(componentTable.getId());
                        codeTableColumns.setColumnName(referenced_column_name);
                        List<GenCodeTableColumns> list = genCodeTableColumnsService.selectGenCodeTableColumnsList(codeTableColumns);
                        Optional<GenCodeTableColumns> first1 = list.stream().findFirst();
                        GenCodeTableColumns genCodeTableColumns1 = first1.get();
                        genCodeTableColumns.setFkFieldName(genCodeTableColumns1.getId());

                        //更新表的类型（存在外键的是子表）
                        GenCodeComponentTable g = new GenCodeComponentTable();
                        g.setTableType("s");
                        g.setId(tableId);
                        genCodeComponentTableService.updateGenCodeComponentTable(g);
                    }
                } else {
                    genCodeTableColumns.setIsPk("0");
                    genCodeTableColumns.setIsFK("0");
                }
            } else {
                genCodeTableColumns.setIsPk("0");
                genCodeTableColumns.setIsFK("0");
            }
            if (s.get("IS_NULLABLE") != null && StringUtils.isNotEmpty(s.get("IS_NULLABLE").toString())) {
                if ("YES".equalsIgnoreCase(s.get("IS_NULLABLE").toString())) {
                    genCodeTableColumns.setIsNull("1");
                } else if ("NO".equalsIgnoreCase(s.get("IS_NULLABLE").toString())){
                    genCodeTableColumns.setIsNull("0");
                }

            }
            genCodeTableColumns.setSort(new BigDecimal(s.get("ORDINAL_POSITION").toString()));
            genCodeTableColumns.setDelFlag("0");
            genCodeTableColumns.setCreateBy(ShiroUtils.getUserId().toString());
            genCodeTableColumns.setCreateTime(new Date());
            genCodeTableColumns.setJavaType(JdbcTypeConvertJavaType.mysqlColumnMap2JavaProperty.get(s.get("DATA_TYPE").toString()));
            genCodeTableColumnsService.insertGenCodeTableColumns(genCodeTableColumns);
        });
    }

    private GenCodeComponentTable getGenCodeComponentTable(String componentId, String referenced_table_name) {
        GenCodeComponentTable genCodeComponentTable = new GenCodeComponentTable();
        genCodeComponentTable.setComponentId(componentId);
        genCodeComponentTable.setTableEnName(referenced_table_name);
        List<GenCodeComponentTable> genCodeComponentTables = genCodeComponentTableService.selectGenCodeComponentTableList(genCodeComponentTable);
        Optional<GenCodeComponentTable> first = genCodeComponentTables.stream().findFirst();
        return first.get();
    }

    private int saveByImportByLocal(@RequestParam("componentId") String componentId, String tableId, GenCodeComponentVo genCodeComponentVo, String tableName, Map<String, Object> map) {
        GenCodeComponentTable genCodeComponentTable = new GenCodeComponentTable();
        genCodeComponentTable.setId(tableId);
        genCodeComponentTable.setTableEnName(tableName);
        genCodeComponentTable.setComponentId(componentId);
        genCodeComponentTable.setDatasourceId(StringUtils.isEmpty(genCodeComponentVo.getDatasourceId()) ? "" : genCodeComponentVo.getDatasourceId());
        genCodeComponentTable.setComments(map.get("table_comment") != null && StringUtils.isEmpty(map.get("table_comment").toString()) ? "" : map.get("table_comment").toString());
        genCodeComponentTable.setClassName(StringHelper.toUpperCaseFirstOne(StringHelper.lineToHump(tableName)));
        genCodeComponentTable.setTableName(map.get("table_comment") != null ? map.get("table_comment").toString() : "");
        genCodeComponentTable.setTableType("m");
        genCodeComponentTable.setDelFlag("0");
        genCodeComponentTable.setCreateBy(ShiroUtils.getUserId().toString());
        genCodeComponentTable.setCreateTime(new Date());
        return genCodeComponentTableService.insertGenCodeComponentTable(genCodeComponentTable);
    }

    /**
     * 校验字段的唯一性
     *
     * @param fieldValue
     * @return
     */
    @PostMapping("/checkFieldUnique")
    @ResponseBody
    public String checkFieldUnique(GenCodeComponentTable genCodeComponentTable, String fieldValue, String filedName) {
        return genCodeComponentTableService.checkFieldUnique(genCodeComponentTable, StringUtils.trim(filedName), fieldValue);
    }
}
