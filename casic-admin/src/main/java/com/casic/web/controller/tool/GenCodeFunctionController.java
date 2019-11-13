package com.casic.web.controller.tool;

import com.alibaba.fastjson.JSONObject;
import com.casic.auth.service.IAuthResourceService;
import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.constant.Constants;
import com.casic.common.enums.BusinessType;
import com.casic.common.support.Convert;
import com.casic.common.utils.StringUtils;
import com.casic.common.utils.UuidUtils;
import com.casic.common.utils.ZipUtils;
import com.casic.common.utils.file.FileHelper;
import com.casic.common.web.domain.bo.AuthResource;
import com.casic.common.web.domain.bo.OAuthClientDetails;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.util.ShiroUtils;
import com.casic.framework.web.base.BaseController;
import com.casic.generator.domain.*;
import com.casic.generator.domain.gen.ColumnInfo;
import com.casic.generator.domain.gen.ParentTableInfo;
import com.casic.generator.domain.gen.TableInfo;
import com.casic.generator.domain.gen.TreeTableInfo;
import com.casic.generator.domain.vo.GenCodeComponentVo;
import com.casic.generator.domain.vo.GenCodeFunctionVO;
import com.casic.generator.service.*;
import com.casic.generator.util.GenCodeVelocityUtils;
import com.casic.system.service.ISysDictDataService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.casic.common.utils.StringHelper.lineToHump;

/**
 * 功能设计-控制器
 */
@Controller
@RequestMapping("/tool/genCodeFunction")
public class GenCodeFunctionController extends BaseController {

    private String prefix = "/tool/genCodeComponentTable/genCodeFunction";
    @Autowired
    private IGenCodeFunctionService genCodeFunctionService;
    @Autowired
    private IGenCodeComponentService genCodeComponentService;
    @Autowired
    private IGenCodeComponentTableService genCodeComponentTableService;
    @Autowired
    private IAuthResourceService authResourceService;
    @Autowired
    private ISysDictDataService dictDataService;
    @Autowired
    private IGenCodeTemplateCfgColumnsService genCodeTemplateCfgColumnsService;
    @Autowired
    private IGenCodeTableColumnsService genCodeTableColumnsService;

    //获取配置文件中文件的路径

    @Value("${web.copy-path}")
    private String copyPath;

    public static Map<String, String> javaProperty2SqlColumnMap = new HashMap<>();

    static {
        javaProperty2SqlColumnMap.put("Integer", "INTEGER");
        javaProperty2SqlColumnMap.put("Short", "tinyint");
        javaProperty2SqlColumnMap.put("Long", "bigint");
        javaProperty2SqlColumnMap.put("BigDecimal", "decimal(19,2)");
        javaProperty2SqlColumnMap.put("Double", "double precision not null");
        javaProperty2SqlColumnMap.put("Float", "float");
        javaProperty2SqlColumnMap.put("Boolean", "bit");
        javaProperty2SqlColumnMap.put("Timestamp", "datetime");
        javaProperty2SqlColumnMap.put("String", "VARCHAR(255)");
    }

    /**
     * 功能设计视图
     *
     * @param id 组件id
     * @param modelMap
     * @return
     */
    @RequiresPermissions("tool:genCodeFunction:view")
    @GetMapping("/fun/{id}")
    public String view(@PathVariable("id") String id, ModelMap modelMap) {
        modelMap.put("id", id);
        return prefix + "/genCodeFunction";
    }

    /**
     * 功能设计列表
     *
     * @param genCodeFunction 功能表实体类
     * @return
     */
    @RequiresPermissions("tool:genCodeFunction:list")
    @PostMapping("/list")
    @ResponseBody
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public TableDataInfo list(GenCodeFunction genCodeFunction, GenCodeComponentTable componentTable) {
        startPage();
        Map<String, Object> params = new HashMap<>();
        params.put("componentId", componentTable.getComponentId());
        List<GenCodeFunction> list = genCodeFunctionService.selectGenCodeFunctionListByComponentId(params);
        List<GenCodeFunctionVO> genCodeFunctionVOS = new ArrayList<>();
        list.forEach(p -> {
            String ob = JSONObject.toJSONString(p);
            GenCodeFunctionVO genCodeFunctionVO = JSONObject.parseObject(ob, GenCodeFunctionVO.class);
            GenCodeComponent genCodeComponent = genCodeFunctionService.selectGenCodeComponentByFunctionId(p.getId());
            genCodeFunctionVO.setComponentName(genCodeComponent.getConmponentName());
            List<Map> maps = genCodeFunctionService.selectTableByFunctionId(p.getId());
            List<String> tables = new ArrayList<>();
            maps.stream().forEach(s -> {
                String table_id = s.get("table_id").toString();
                GenCodeComponentTable genCodeComponentTable = genCodeComponentTableService.selectGenCodeComponentTableById(table_id);
                if(genCodeComponentTable != null){
                    tables.add(genCodeComponentTable.getTableEnName());
                } else {
                    //table或已经被删除，同时从关联表删除
                    genCodeComponentTableService.deleteGenCodeFunctionTableRel(table_id);
                }
            });
            genCodeFunctionVO.setTableIds(tables);
            genCodeFunctionVOS.add(genCodeFunctionVO);
        });
        return getDataTable(genCodeFunctionVOS);
    }

    /**
     * 新增功能视图
     *
     * @param componentId 组件id
     * @param type 功能类型
     * @param modelMap
     * @return
     */
    @GetMapping("/add/{componentId}/{type}")
    public String add(@PathVariable("componentId") String componentId, @PathVariable("type") String type, ModelMap modelMap) {
        GenCodeComponentVo genCodeComponentVo = genCodeComponentService.selectGenCodeComponentById(componentId);
        GenCodeComponentTable genCodeComponentTable = new GenCodeComponentTable();
        genCodeComponentTable.setComponentId(componentId);
        List<GenCodeComponentTable> genCodeComponentTables = genCodeComponentTableService.selectGenCodeComponentTableList(genCodeComponentTable);
        AuthResource resource = new AuthResource();
        String sysCode = resource.getSysCode();
        if("ALL".equals(sysCode)) {
            resource.setSysCode(null);
        }
        if(StringUtils.isNotEmpty(componentId)){
            resource.setSysCode(componentId);
        }
        List<AuthResource> list = authResourceService.selectResourceList(resource);
        modelMap.put("componentId", componentId);
        modelMap.put("genCodeComponentVo", genCodeComponentVo);
        modelMap.put("menus", list);
        modelMap.put("genCodeComponentTables", genCodeComponentTables);
        String categoryId = "";
        if ("1".equals(type)) {
            categoryId = dictDataService.selectDictValue("tool_genCodeFunction_categoryId", "单表功能");
        } else if ("2".equals(type)) {
            categoryId = dictDataService.selectDictValue("tool_genCodeFunction_categoryId", "父子表功能");
        } else if ("3".equals(type)) {
            categoryId = dictDataService.selectDictValue("tool_genCodeFunction_categoryId", "树形表功能");
        } else if ("4".equals(type)) {
            categoryId = dictDataService.selectDictValue("tool_genCodeFunction_categoryId", "一父多子表功能");
        }
        modelMap.put("categoryId", categoryId);
        return prefix + "/addSingleTable";
    }

    /**
     * 新增功能
     *
     * @param genCodeFunction  功能实体类
     * @return
     */
    @RequiresPermissions("tool:genCodeFunction:add")
    @Log(title = "功能设计", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult addSave(GenCodeFunction genCodeFunction, String componentId, String tableIds ) {
        String uuid = UuidUtils.getUUIDString();
        String funcMenuId = genCodeFunction.getFuncMenuId();
        GenCodeComponentVo genCodeComponentVo = genCodeComponentService.selectGenCodeComponentById(componentId);
        if (genCodeFunction != null && StringUtils.isEmpty(genCodeFunction.getId())) {
            genCodeFunction.setId(uuid);
            genCodeFunction.setCreateBy(String.valueOf(ShiroUtils.getUserId()));
            genCodeFunction.setCreateTime(new Date());
            genCodeFunction.setDelFlag("0");
        }
        int i = genCodeFunctionService.insertGenCodeFunction(genCodeFunction);
        int componentFuncFlag = 0;
        Map<String, Object> params = new HashMap<>();
        if (i > 0 && !StringUtils.isEmpty(componentId)) {
            params.put("componentId", componentId);
            params.put("id", UuidUtils.getUUIDString());
            params.put("funcId", uuid);
            componentFuncFlag = genCodeFunctionService.insertGenCodeComponentFunction(params);
        }
        List<GenCodeFunctionTable> list = new ArrayList<>();
        int flag = 0;
        if (i > 0 && !StringUtils.isEmpty(tableIds)) {
            String[] ids = tableIds.split(",");
            for (String tableId : ids) {
                GenCodeFunctionTable genCodeFunctionTable = new GenCodeFunctionTable();
                genCodeFunctionTable.setId(UuidUtils.getUUIDString());
                genCodeFunctionTable.setFunctionId(uuid);
                genCodeFunctionTable.setTableId(tableId);
                list.add(genCodeFunctionTable);

                //更新菜单访问链接，确定权限标识
                AuthResource authResource = new AuthResource();
                GenCodeComponentTable genCodeComponentTable = genCodeComponentTableService.selectGenCodeComponentTableById(tableId);
                authResource.setId(Integer.valueOf(funcMenuId));
                authResource.setUri("/" + toLowerCaseFirstOne(genCodeFunction.getFuncEnName()) + "/" +toLowerCaseFirstOne(genCodeComponentTable.getTableEnName()));
                authResource.setPerms(toLowerCaseFirstOne(genCodeFunction.getFuncEnName()) + ":" + toLowerCaseFirstOne(genCodeComponentTable.getTableEnName()) + ":" + "view");
                authResourceService.updateResource(authResource);
            }
            flag = genCodeFunctionService.insertGenCodeFunctionTable(list);
        }

        return toAjax((i > 0 && componentFuncFlag > 0 && flag > 0) ? 1 : 0);
    }

    /**
     * 编辑视图
     *
     * @param id  功能设计id
     * @param modelMap
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap modelMap) {
        GenCodeFunction genCodeFunction = genCodeFunctionService.selectGenCodeFunctionById(id);
        GenCodeComponent genCodeComponent = genCodeFunctionService.selectGenCodeComponentByFunctionId(genCodeFunction.getId());
        modelMap.put("genCodeFunction", genCodeFunction);
        modelMap.put("genCodeComponent", genCodeComponent);
        modelMap.put("id", id);

        GenCodeComponentTable genCodeComponentTable = new GenCodeComponentTable();
        genCodeComponentTable.setComponentId(genCodeComponent.getId());
        List<GenCodeComponentTable> genCodeComponentTables = genCodeComponentTableService.selectGenCodeComponentTableList(genCodeComponentTable);
        AuthResource resource = new AuthResource();
        String sysCode = resource.getSysCode();
        if("ALL".equals(sysCode)) {
            resource.setSysCode(null);
        }
        if(StringUtils.isNotEmpty(genCodeComponent.getId())){
            resource.setSysCode(genCodeComponent.getId());
        }
        List<AuthResource> list = authResourceService.selectResourceList(resource);

        List<Map> maps = genCodeFunctionService.selectTableByFunctionId(genCodeFunction.getId());
        List<Map<String, Object>> tables = new ArrayList<>();
        List<String> tableIds = new ArrayList<>();
        maps.stream().forEach(s -> {
            GenCodeComponentTable obj = genCodeComponentTableService.selectGenCodeComponentTableById(s.get("table_id").toString());
            Map map = new HashMap();
            map.clear();
            map.put("componentTableId", obj.getId());
            map.put("componentTableName", obj.getTableEnName());
            tableIds.add(obj.getId());
            tables.add(map);
        });
        AuthResource resource1 = authResourceService.selectResourceById(Integer.valueOf(genCodeFunction.getFuncMenuId()));
        modelMap.put("componentId", genCodeComponent.getId());
        modelMap.put("menus", list);
        modelMap.put("genCodeComponentTables", genCodeComponentTables);
        modelMap.put("tables", tables);
        modelMap.put("resource1", resource1);
        modelMap.put("tableIds", StringUtils.join(tableIds.toArray(), ','));
        return prefix + "/editSingleTable";
    }

    /**
     * 编辑
     * @param genCodeFunction 功能设计实体类
     * @return
     */
    @RequiresPermissions("tool:genCodeFunction:edit")
    @Log(title = "功能设计", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult editSave(GenCodeFunction genCodeFunction, String tableIds) {
        genCodeFunction.setUpdateBy(String.valueOf(ShiroUtils.getUserId()));
        genCodeFunction.setUpdateTime(new Date());
        int i = genCodeFunctionService.updateGenCodeFunction(genCodeFunction);
        //根据功能id修改功能与表之间的关系
        String functionId = genCodeFunction.getId();
        String[] ids = tableIds.split(",");
        Set<String> set = new HashSet<>(Arrays.asList(ids));
        //删除旧的关系
        int delFlag = genCodeFunctionService.deleteGenCodeFunctionTableByFunctionId(functionId);
        //保存新的关系
        List<GenCodeFunctionTable> list = new ArrayList<>();
        set.stream().forEach(s -> {
            GenCodeFunctionTable genCodeFunctionTable = new GenCodeFunctionTable();
            genCodeFunctionTable.setId(UuidUtils.getUUIDString());
            genCodeFunctionTable.setFunctionId(functionId);
            genCodeFunctionTable.setTableId(s);
            list.add(genCodeFunctionTable);
        });
        int flag = genCodeFunctionService.insertGenCodeFunctionTable(list);
        return toAjax((i > 0 && flag > 0) ? 1 : 0);
    }

    /**
     * 删除
     * @param ids 需要删除的数据的id字符串
     * @return
     */
    @RequiresPermissions("tool:genCodeFunction:remove")
    @Log(title = "功能设计", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult remove(String ids) {
        String[] funtionIds = Convert.toStrArray(ids);
        int flag = genCodeFunctionService.deleteGenCodeFunctionByIds(ids);
        if (flag > 0) {
            //删除功能与表之间的关系
            int delFlag = genCodeFunctionService.deleteGenCodeFunctionTableByFunctionIds(funtionIds);
        }
        return toAjax(flag);
    }

    /**
     * 选择菜单树
     *
     * @param clientId
     * @param mmap
     * @return
     */
    @GetMapping("/selectMenuTree/{clientId}")
    public String selectMenuTree (@PathVariable("clientId") String clientId, ModelMap mmap) {
        mmap.put("clientId", clientId);
        mmap.put("menu", genCodeFunctionService.selectResourceByClientId(clientId));
        return prefix + "/tree";
    }

    /**
     * 加载所有菜单列表树
     */
    @GetMapping("/menuTreeData/{clientId}")
    @ResponseBody
    public List<Map<String, Object>> menuTreeData(@PathVariable("clientId") String clientId) {
        List<Map<String, Object>> tree = genCodeFunctionService.menuTreeData(clientId);
        return tree;
    }

    /**
     * 生成sql脚本
     *
     * @param id 功能id
     */
    @GetMapping("/generatorSqlScript/{id}")
    @ResponseBody
    public void generatorSqlScript(HttpServletResponse response, @PathVariable("id") String id) {
        List<Map> maps = genCodeFunctionService.selectTableByFunctionId(id);
        GenCodeComponent genCodeComponent = genCodeFunctionService.selectGenCodeComponentByFunctionId(id);
        String conmponentCode = genCodeComponent.getConmponentCode();
        String root = StringUtils.substringBefore(this.copyPath, ":") + ":" + File.separator + "casic-oauth2-client-" + conmponentCode + File.separator + "sql";
        File fp = new File(root);
        if (!fp.exists()) {
            fp.mkdirs();
        }
        String path = root + File.separator + "sql.sql";

        List<String> sqlList = new ArrayList<>();
        maps.stream().forEach(p -> {
            String tableId = p.get("table_id").toString();
            GenCodeComponentTable genCodeComponentTable = genCodeComponentTableService.selectGenCodeComponentTableById(tableId);
            GenCodeTemplateCfgColumns genCodeTemplateCfgColumns = new GenCodeTemplateCfgColumns();
            genCodeTemplateCfgColumns.setTableId(tableId);
            genCodeTemplateCfgColumns.setComponentId(genCodeComponentTable.getComponentId());
            genCodeTemplateCfgColumns.setFunctionId(id);
            List<GenCodeTemplateCfgColumns> templateCfgColumns = genCodeTemplateCfgColumnsService.selectGenCodeTemplateCfgColumnsList(genCodeTemplateCfgColumns);
            String tableName = genCodeComponentTable.getTableEnName();
            String comments = genCodeComponentTable.getComments();
            StringBuilder sb = new StringBuilder();
            sb.append("\r\ndrop table if exists  ").append(tableName).append(";\r\n");
            sb.append("create table ").append(tableName).append(" ( \r\n");
            templateCfgColumns.stream().forEach(s -> {
                String column = s.getColumnName();
                String javaType = s.getJavaType();
                sb.append(column);
                sb.append(" ").append(javaProperty2SqlColumnMap.get(javaType)).append(" ");
                if ("0".equals(s.getIsNull())) {
                    sb.append(" not null ").append(" ");
                }
                if ("1".equals(s.getIsPk())) {
                    sb.append(" PRIMARY KEY ");
                }
                sb.append(" comment ").append("'").append(s.getComments()).append("'");
                sb.append(",\n ");

            });
            String sql = sb.toString();
            //去掉最后一个逗号
            int lastIndexOf = sql.lastIndexOf(",");
            sql = sql.substring(0 ,lastIndexOf) + sql.substring(lastIndexOf + 1);
            sql = sql.substring(0, sql.length() -1) + " ) ENGINE =INNODB DEFAULT  CHARSET= utf8;\r\n";
            sql = sql + "alter table " +  tableName + " comment '" + comments +"';\r\n";
            //确定外键关系
            sqlList.add(sql);
        });
        //将文件进行打包下载
        writeStringToFile(path, StringUtils.join(sqlList, "\r\n"));
        try {
            String downloadName = "sql.zip";
            OutputStream out = response.getOutputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ZipOutputStream zip = new ZipOutputStream(outputStream);
            // 添加到zip
            zip.putNextEntry(new ZipEntry(path));
            IOUtils.write(StringUtils.join(sqlList, "\r\n"), zip, Constants.UTF8);
            zip.closeEntry();
            IOUtils.closeQuietly(zip);
            //服务器存储地址
            byte[] data =  outputStream.toByteArray();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + downloadName);
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream; charset=UTF-8");
            IOUtils.write(data, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //删除原目录
            File file = new File(root);
            ZipUtils.delFile(file);
        }
    }

    /**
     * 将字符串写入文本
     * @param filePath
     * @param sql
     */
    private void writeStringToFile(String filePath, String sql) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件,如果为 true，则将字节写入文件末尾处，而不是写入文件开始处
            FileWriter writer = new FileWriter(filePath, true);
            writer.write(sql);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据功能生成源码
     *
     * @param id  功能id
     * @param categoryId 功能类型
     * @return
     */
    @RequiresPermissions("tool:genCodeFunction:code")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/generatorCode/{id}/{categoryId}")
    @ResponseBody
    public void generatorCode(HttpServletResponse response, @PathVariable("id") String id, @PathVariable("categoryId") String categoryId) {
        GenCodeFunction genCodeFunction = genCodeFunctionService.selectGenCodeFunctionById(id);
        //根据功能id查询组件名称
        GenCodeComponent genCodeComponent = genCodeFunctionService.selectGenCodeComponentByFunctionId(id);
        String componentName = genCodeComponent.getConmponentCode();
        List<Map> maps = genCodeFunctionService.selectTableByFunctionId(id);
        String packageName = genCodeFunction.getPackageName();
        String funcEnName = lineToHump(genCodeFunction.getFuncEnName());
        if (maps != null && maps.size() > 0) {
            genCodeByCategoryId(id, categoryId, componentName, maps, packageName, funcEnName);
        }
        downloadZip(response, genCodeComponent.getConmponentCode());
    }

    private void genCodeByCategoryId(@PathVariable("id") String id, @PathVariable("categoryId") String categoryId, String componentName, List<Map> maps, String packageName, String funcEnName) {
        if ("scurd".equals(categoryId)) {
            //单表生成
            scurdTableGenerator(id, componentName, maps, packageName, funcEnName);
        } else if ("mcurd".equals(categoryId)) {
            //父子表
            mcurdTableGenerator(id, categoryId, componentName, maps, packageName, funcEnName);
        } else if ("treecurd".equals(categoryId)) {
            //树形表
            treecurdTableGenerator(id, componentName, maps, packageName, funcEnName);
        } else if ("mmcurd".equals(categoryId)) {
            //一父多子表
            mmcurdTableGenerator(id, categoryId, componentName, maps, packageName, funcEnName);
        }
    }

    /**
     * 一父多子表生成
     * @param id
     * @param categoryId
     * @param componentName
     * @param maps
     * @param packageName
     * @param funcEnName
     */
    private void mmcurdTableGenerator(@PathVariable("id") String id, @PathVariable("categoryId") String categoryId, String componentName, List<Map> maps, String packageName, String funcEnName) {
        ParentTableInfo parentTableInfo = new ParentTableInfo();
        List<TableInfo> childList = new ArrayList<TableInfo>();
        maps.stream().forEach(p -> {
            String tableId = p.get("table_id").toString();
            GenCodeComponentTable codeComponentTable = genCodeComponentTableService.selectGenCodeComponentTableById(tableId);
            GenCodeTemplateCfgColumns genCodeTemplateCfgColumns = new GenCodeTemplateCfgColumns();
            genCodeTemplateCfgColumns.setTableId(tableId);
            genCodeTemplateCfgColumns.setFunctionId(id);
            List<GenCodeTemplateCfgColumns> genCodeTemplateCfgColumnsList = genCodeTemplateCfgColumnsService.selectGenCodeTemplateCfgColumnsList(genCodeTemplateCfgColumns);

            //确定主子表
            if ("s".equals(codeComponentTable.getTableType())) {
                //子表
                TableInfo childTableInfo = getTableInfo(codeComponentTable, genCodeTemplateCfgColumnsList);
                childList.add(childTableInfo);
            } else {
                //主表
                parentTableInfo.setTableName(codeComponentTable.getTableEnName());
                parentTableInfo.setTableComment(codeComponentTable.getTableName());
                parentTableInfo.setClassName(toUpperCaseFirstOne(codeComponentTable.getClassName()));
                parentTableInfo.setClassname(toLowerCaseFirstOne(codeComponentTable.getClassName()));
                List<ColumnInfo> masterColumnInfo = new ArrayList<>();
                genCodeTemplateCfgColumnsList.stream().forEach(m-> {
                    //确定主键
                    ColumnInfo master_columnInfo = getColumnInfo(m);
                    if ("1".equals(m.getIsPk())) {
                        //主键
                        parentTableInfo.setPrimaryKey(master_columnInfo);
                    } else if ("0".equals(m.getIsPk())) {
                        masterColumnInfo.add(master_columnInfo);
                    }
                });
                parentTableInfo.setColumns(masterColumnInfo);
            }
        });
        parentTableInfo.setChildTableList(childList);
        if (parentTableInfo.getChildTableList() != null && parentTableInfo.getChildTableList().size() > 0) {
            GenCodeVelocityUtils.templateGenerator(parentTableInfo, packageName, categoryId, componentName, funcEnName, StringUtils.substringBefore(this.copyPath, ":") + ":");
        }
    }

    /**
     * 树形表生成
     * @param id
     * @param componentName
     * @param maps
     * @param packageName
     * @param funcEnName
     */
    private void treecurdTableGenerator(@PathVariable("id") String id, String componentName, List<Map> maps, String packageName, String funcEnName) {
        TreeTableInfo treeTableInfo = new TreeTableInfo();
        maps.stream().forEach(p -> {
            GenCodeComponentTable codeComponentTable = genCodeComponentTableService.selectGenCodeComponentTableById(p.get("table_id").toString());
            treeTableInfo.setTableName(codeComponentTable.getTableEnName());
            treeTableInfo.setTableComment(codeComponentTable.getComments());
            treeTableInfo.setClassName(toUpperCaseFirstOne(codeComponentTable.getClassName()));
            treeTableInfo.setClassname(toLowerCaseFirstOne(codeComponentTable.getClassName()));

            GenCodeTemplateCfgColumns genCodeTemplateCfgColumns = new GenCodeTemplateCfgColumns();
            genCodeTemplateCfgColumns.setTableId(p.get("table_id").toString());
            genCodeTemplateCfgColumns.setFunctionId(id);
            List<GenCodeTemplateCfgColumns> genCodeTemplateCfgColumnsList = genCodeTemplateCfgColumnsService.selectGenCodeTemplateCfgColumnsList(genCodeTemplateCfgColumns);

            List<ColumnInfo> columnInfos = new ArrayList<>();
            genCodeTemplateCfgColumnsList.stream().forEach(s -> {
                ColumnInfo columnInfo = getColumnInfo(s);
                //确定父节点
                GenCodeComponentTable genCodeComponentTable = genCodeComponentTableService.selectGenCodeComponentTableById(s.getTableId());
                List<GenCodeTableColumns> list = genCodeTableColumnsService.selectGenCodeTableColumnsByTableId(s.getTableId());
                Optional<GenCodeTableColumns> first = list.stream().filter(v -> "1".equals(v.getIsFK())).findFirst();
                GenCodeTableColumns genCodeTableColumns1 = first.get();
                GenCodeTableColumns genCodeTableColumns = genCodeTableColumnsService.selectGenCodeTableColumnsById(genCodeTableColumns1.getFkFieldName());
               if ("1".equals(s.getIsPk())) {
                    treeTableInfo.setPrimaryKey(columnInfo);
                    return;
                } else {
                   if (!s.getColumnName().equals(genCodeTableColumns1.getColumnName())) {
                       columnInfos.add(columnInfo);
                   } else {
                       treeTableInfo.setParentId(columnInfo);
                   }
               }
            });
            treeTableInfo.setColumns(columnInfos);
        });
        if (treeTableInfo.getColumns() != null && treeTableInfo.getColumns().size() > 0) {
            GenCodeVelocityUtils.templateGenerator(treeTableInfo, packageName, componentName, funcEnName, StringUtils.substringBefore(this.copyPath, ":") + ":");
        }
    }

    private ColumnInfo getColumnInfo(GenCodeTemplateCfgColumns s) {
        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.setColumnName(s.getColumnName());
        columnInfo.setColumnComment(s.getComments());
        columnInfo.setAttrName(toUpperCaseFirstOne(s.getJavaField()));
        columnInfo.setAttrname(toLowerCaseFirstOne(s.getJavaField()));
        columnInfo.setAttrType(s.getJavaType());
        columnInfo.setDataType(s.getJdbcType());
        columnInfo.setIsInsert(s.getIsInsert());
        columnInfo.setIsEdit(s.getIsEdit());
        columnInfo.setIsList(s.getIsList());
        columnInfo.setIsQuery(s.getIsQuery());
        columnInfo.setShowType(s.getShowType());
        columnInfo.setQueryType(s.getQueryType());
        columnInfo.setDictType(s.getDictType());
        return columnInfo;
    }

    /**
     * 父子表生成
     *  @param id
     * @param categoryId
     * @param componentName
     * @param maps
     * @param packageName
     * @param funcEnName
     */
    private void mcurdTableGenerator(@PathVariable("id") String id, @PathVariable("categoryId") String categoryId, String componentName, List<Map> maps, String packageName, String funcEnName) {
        ParentTableInfo parentTableInfo = new ParentTableInfo();
        maps.stream().forEach(p -> {
            String tableId = p.get("table_id").toString();
            GenCodeComponentTable codeComponentTable = genCodeComponentTableService.selectGenCodeComponentTableById(tableId);
            GenCodeTemplateCfgColumns genCodeTemplateCfgColumns = new GenCodeTemplateCfgColumns();
            genCodeTemplateCfgColumns.setTableId(tableId);
            genCodeTemplateCfgColumns.setFunctionId(id);
            List<GenCodeTemplateCfgColumns> genCodeTemplateCfgColumnsList = genCodeTemplateCfgColumnsService.selectGenCodeTemplateCfgColumnsList(genCodeTemplateCfgColumns);

            //确定主子表
            if ("s".equals(codeComponentTable.getTableType())) {
                //子表
                TableInfo childTableInfo = getTableInfo(codeComponentTable, genCodeTemplateCfgColumnsList);
                parentTableInfo.setChildTable(childTableInfo);
            } else {
                //主表
                parentTableInfo.setTableName(codeComponentTable.getTableEnName());
                parentTableInfo.setTableComment(codeComponentTable.getTableName());
                parentTableInfo.setClassName(toUpperCaseFirstOne(codeComponentTable.getClassName()));
                parentTableInfo.setClassname(toLowerCaseFirstOne(codeComponentTable.getClassName()));
                List<ColumnInfo> masterColumnInfo = new ArrayList<>();
                genCodeTemplateCfgColumnsList.stream().forEach(m-> {
                    //确定主键
                    ColumnInfo master_columnInfo = getColumnInfo(m);
                    if ("1".equals(m.getIsPk())) {
                        //主键
                        parentTableInfo.setPrimaryKey(master_columnInfo);
                    } else if ("0".equals(m.getIsPk())) {
                        masterColumnInfo.add(master_columnInfo);
                    }
                });
                parentTableInfo.setColumns(masterColumnInfo);
            }
        });
        GenCodeVelocityUtils.templateGenerator(parentTableInfo, packageName, categoryId, componentName, funcEnName, StringUtils.substringBefore(this.copyPath, ":") + ":");
    }

    private TableInfo getTableInfo(GenCodeComponentTable codeComponentTable, List<GenCodeTemplateCfgColumns> genCodeTemplateCfgColumnsList) {
        TableInfo childTableInfo = new TableInfo();
        childTableInfo.setTableName(codeComponentTable.getTableEnName());
        childTableInfo.setTableComment(codeComponentTable.getTableName());
        childTableInfo.setClassName(toUpperCaseFirstOne(codeComponentTable.getClassName()));
        childTableInfo.setClassname(toLowerCaseFirstOne(codeComponentTable.getClassName()));
        List<ColumnInfo> slaveColumnInfo = new ArrayList<>();
        genCodeTemplateCfgColumnsList.stream().forEach(s -> {
            ColumnInfo slave_columnInfo = getColumnInfo(s);
            if ("1".equals(s.getIsPk())) {
                childTableInfo.setPrimaryKey(slave_columnInfo);
            } else if ("0".equals(s.getIsPk())) {
                slaveColumnInfo.add(slave_columnInfo);
            }
        });
        childTableInfo.setColumns(slaveColumnInfo);
        return childTableInfo;
    }

    /**
     * 单表生成
     *  @param id
     * @param componentName
     * @param maps
     * @param packageName
     * @param funcEnName
     */
    private void scurdTableGenerator(@PathVariable("id") String id, String componentName, List<Map> maps, String packageName, String funcEnName) {
        maps.stream().forEach(p -> {
            TableInfo tableInfo = new TableInfo();
            GenCodeTemplateCfgColumns genCodeTemplateCfgColumns = new GenCodeTemplateCfgColumns();
            genCodeTemplateCfgColumns.setTableId(p.get("table_id").toString());
            genCodeTemplateCfgColumns.setFunctionId(id);
            List<GenCodeTemplateCfgColumns> genCodeTemplateCfgColumnsList = genCodeTemplateCfgColumnsService.selectGenCodeTemplateCfgColumnsList(genCodeTemplateCfgColumns);
            if (genCodeTemplateCfgColumnsList != null && genCodeTemplateCfgColumnsList.size() > 0) {
                List<ColumnInfo> columnInfos = new ArrayList<>();
                genCodeTemplateCfgColumnsList.stream().forEach(s -> {
                    ColumnInfo columnInfo = getColumnInfo(s);
                    if ("0".equals(s.getIsPk())) {
                        columnInfos.add(columnInfo);
                    } else if ("1".equals(s.getIsPk())) {
                        tableInfo.setPrimaryKey(columnInfo);
                    }
                });
                tableInfo.setColumns(columnInfos);
                GenCodeComponentTable genCodeComponentTable = genCodeComponentTableService.selectGenCodeComponentTableById(p.get("table_id").toString());
                tableInfo.setTableName(genCodeComponentTable.getTableEnName());
                tableInfo.setTableComment(genCodeComponentTable.getComments());
                tableInfo.setClassName(toUpperCaseFirstOne(genCodeComponentTable.getClassName()));
                tableInfo.setClassname(toLowerCaseFirstOne(genCodeComponentTable.getClassName()));

                GenCodeVelocityUtils.templateGenerator(tableInfo, packageName, componentName, funcEnName, StringUtils.substringBefore(this.copyPath, ":") + ":");
            }
        });
    }

    /**
     * 组件代码生成
     *
     * @param componentId 组件id
     */
    @RequiresPermissions("tool:genCodeFunction:genComponentCode")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/genComponentCode/{componentId}")
    @ResponseBody
    public void genComponentCode(HttpServletResponse response, @PathVariable("componentId") String componentId) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("componentId", componentId);
        genProjectStructure(componentId);
        GenCodeComponentVo componentVo = genCodeComponentService.selectGenCodeComponentById(componentId);
        //查询所有的功能
        List<GenCodeFunction> genCodeFunctions = genCodeFunctionService.selectGenCodeFunctionListByComponentId(params);
        if (genCodeFunctions != null && genCodeFunctions.size() > 0) {
            genCodeFunctions.stream().forEach(p -> {
                //查询单个功能所包含的表
                String packageName = p.getPackageName();
                String funcEnName = lineToHump(p.getFuncEnName());
                String categoryId = p.getCategoryId();
                GenCodeComponent genCodeComponent = genCodeComponentService.selectGenCodeComponentById(componentId);
                String componentCode = genCodeComponent.getConmponentCode();
                String functionId = p.getId();
                List<Map> maps = genCodeFunctionService.selectTableByFunctionId(functionId);
                if (maps != null && maps.size() > 0) {
                    genCodeByCategoryId(functionId, categoryId, componentCode, maps, packageName, funcEnName);
                    genSQlFile(maps, p.getId());
                }
            });
        }

        downloadZip(response, componentVo.getConmponentCode());
    }

    private void genSQlFile(List<Map> maps, String id) {
        GenCodeComponent genCodeComponent = genCodeFunctionService.selectGenCodeComponentByFunctionId(id);
        String conmponentCode = genCodeComponent.getConmponentCode();
        String root = StringUtils.substringBefore(this.copyPath, ":") + ":" + File.separator + "casic-oauth2-client-" + conmponentCode + File.separator + "sql";
        File fp = new File(root);
        if (!fp.exists()) {
            fp.mkdirs();
        }
        String path = root + File.separator + "sql.sql";

        List<String> sqlList = new ArrayList<>();
        maps.stream().forEach(p -> {
            String tableId = p.get("table_id").toString();
            GenCodeComponentTable genCodeComponentTable = genCodeComponentTableService.selectGenCodeComponentTableById(tableId);
            GenCodeTemplateCfgColumns genCodeTemplateCfgColumns = new GenCodeTemplateCfgColumns();
            genCodeTemplateCfgColumns.setTableId(tableId);
            genCodeTemplateCfgColumns.setComponentId(genCodeComponentTable.getComponentId());
            genCodeTemplateCfgColumns.setFunctionId(id);
            List<GenCodeTemplateCfgColumns> templateCfgColumns = genCodeTemplateCfgColumnsService.selectGenCodeTemplateCfgColumnsList(genCodeTemplateCfgColumns);
            String tableName = genCodeComponentTable.getTableEnName();
            String comments = genCodeComponentTable.getComments();
            StringBuilder sb = new StringBuilder();
            sb.append("\r\ndrop table if exists  ").append(tableName).append(";\r\n");
            sb.append("create table ").append(tableName).append(" ( \r\n");
            templateCfgColumns.stream().forEach(s -> {
                String column = s.getColumnName();
                String javaType = s.getJavaType();
                sb.append(column);
                sb.append(" ").append(javaProperty2SqlColumnMap.get(javaType)).append(" ");
                if ("0".equals(s.getIsNull())) {
                    sb.append(" not null ").append(" ");
                }
                if ("1".equals(s.getIsPk())) {
                    sb.append(" PRIMARY KEY ");
                }
                sb.append(" comment ").append("'").append(s.getComments()).append("'");
                sb.append(",\n ");

            });
            String sql = sb.toString();
            //去掉最后一个逗号
            int lastIndexOf = sql.lastIndexOf(",");
            sql = sql.substring(0 ,lastIndexOf) + sql.substring(lastIndexOf + 1);
            sql = sql.substring(0, sql.length() -1) + " ) ENGINE =INNODB DEFAULT  CHARSET= utf8;\r\n";
            sql = sql + "alter table " +  tableName + " comment '" + comments +"';\r\n";
            //确定外键关系
            sqlList.add(sql);
        });
        //将文件进行打包下载
        writeStringToFile(path, StringUtils.join(sqlList, "\r\n"));
    }

    /**
     * 下载压缩包
     * @param response
     * @param componentCode
     */
    private void downloadZip(HttpServletResponse response, String componentCode) {
        String targetPath = StringUtils.substringBefore(this.copyPath, ":") + ":" + File.separator + "casic-oauth2-client-" + componentCode;
        //将文件进行打包下载
        try {
            String downloadName = "casic-oauth2-client-" + componentCode + ".zip";
            OutputStream out = response.getOutputStream();
            //服务器存储地址
            byte[] data = ZipUtils.createZip(targetPath);
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + downloadName);
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream; charset=UTF-8");
            IOUtils.write(data, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //删除原目录
            File file = new File(targetPath);
            ZipUtils.delFile(file);
        }
    }

    /**
     * 生成项目结构
     *
     * @param componentId 组件id
     */
    private void genProjectStructure(String componentId) {
        GenCodeComponentVo genCodeComponentVo = genCodeComponentService.selectGenCodeComponentById(componentId);
        if (genCodeComponentVo != null && StringUtils.isNotEmpty(genCodeComponentVo.getClientId()) ) {
            String conmponentCode = genCodeComponentVo.getConmponentCode();
            OAuthClientDetails oAuthClientDetails = genCodeFunctionService.selectOauthClientDetailsByComponentId(componentId);
            if (oAuthClientDetails != null && StringUtils.isNotEmpty(oAuthClientDetails.getClientId())) {
                String clientId = oAuthClientDetails.getClientId();
                String rootPath = StringUtils.substringBefore(this.copyPath, ":") + ":" + File.separator +  "casic-system-manager-sso" + File.separator;
                try {
                    String originPath = this.copyPath;
                    String targetPath = StringUtils.substringBefore(this.copyPath, ":") + ":" + File.separator + "casic-oauth2-client-" + conmponentCode + "\\src\\main\\resources";
                    File fp = new File(targetPath);
                    if (!fp.exists()) {
                        fp.mkdirs();
                    }
                    File file = new File(originPath);
                    if (file.exists()) {
                        FileHelper.copyDir(originPath,targetPath);
                    }
                    buildProjectStructure(rootPath, conmponentCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 首字母转小写
     * @param s 需要转换的字符串
     * @return
     */
    private static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0))){
            return s;
        }else{
            return Character.toLowerCase(s.charAt(0)) + s.substring(1);
        }
    }

    /**
     * 首字母转大写
     * @param s 需要转换的字符串
     * @return
     */
    private static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0))){
            return s;
        }else{
            return Character.toUpperCase(s.charAt(0)) + s.substring(1);
        }
    }


    /**
     * 创建多级目录
     *
     * @param directories
     * @param rootPath
     * @return
     */
    public static File createMultilevelDirectory(String[] directories, String rootPath) {
        if (directories.length == 0) {
            return null;
        }
        File root = new File(rootPath);
        for (int i = 0; i < directories.length; i++) {
            File directory = new File(root, directories[i]);
            directory.mkdir();
            root = directory;
        }
        return root;
    }

    /**
     * 创建带有多级目录的文件
     *
     * @param directories
     * @param fileName
     * @param rootName
     * @return
     * @throws IOException
     */
    public static File createFileWithMultilevelDirectory(String[] directories,String fileName,String rootName) throws IOException {
    //调用上面的创建多级目录的方法
        File filePath =  createMultilevelDirectory(directories,rootName);
        File file = new File(filePath,fileName);
        file.createNewFile();
        return  file;
    }

    /**
     * 两个数组合并
     *
     * @param first
     * @param second
     * @param <T>
     * @return
     */
    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    /**
     * 多个数组合并
     *
     * @param first
     * @param rest
     * @param <T>
     * @return
     */
    public static <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }


    public static File getLocalFileName(String template, String path, String componentCode) throws Exception {
        if (template.contains("pom.xml.vm")) {
            //创建pom.xml
            String filePath = path + File.separator + "pom.xml";
            File file = new File(filePath);
            return file;
        } else if (template.contains("application.yml.vm")) {
            //2、新建application.yml文件
            String[] directories = {"src","main","resources"};
            String rootName = new File(path).getAbsolutePath();
            return createFileWithMultilevelDirectory(directories,"application.yml",rootName);
        } else {
            String[] split = template.split("/");
            List<String> list = Arrays.asList(split);
            Collections.replaceAll(list, "client", componentCode);
            List<String> collect = list.stream().filter(p -> !p.endsWith(".vm") && !p.equals("vm")).collect(Collectors.toList());
            List<String> fileNames = list.stream().filter(s -> s.endsWith(".vm")).collect(Collectors.toList());
            String fileName = StringUtils.substringBefore(fileNames.get(0), ".vm");
            String[] directories = {"src","main","java", "com","casic"};
            String[] objects = new String[collect.size()];
            for(int i=0,j = collect.size();i<j;i++){
                objects[i] = collect.get(i);
            }
            String[] both = concatAll(directories, objects);
            String rootName = new File(path).getAbsolutePath();
            return createFileWithMultilevelDirectory(both,fileName,rootName);
        }
    }

    /**
     * 根据文件路径获取该路径下的所有文件
     *
     * @param directory  文件路径
     * @param templates 模板集合
     * @throws Exception 异常信息
     */
    public static void getFileList(String directory, List<String> templates) throws Exception {
        File f = new File(directory);
        File[] files = f.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                String str = StringUtils.substringAfterLast(files[i].getAbsolutePath(), "resources" + File.separator).replaceAll("\\\\","/");
                templates.add(str);
            } else {
                getFileList(files[i].getAbsolutePath(), templates);
            }
        }
    }

    /**
     * 构建项目结构
     *
     * @param rootPath 路径
     * @param componentCode 组件编码
     * @throws Exception
     */
    private static void buildProjectStructure(String rootPath, String componentCode) throws Exception {
        //1、创建项目目录
        String clientPath = "casic-oauth2-client-" + componentCode;
        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        // 载入（获取）模板对象
        List<String> templates = new ArrayList<>();
        templates.add("vm/pom.xml.vm");
        templates.add("vm/application.yml.vm");
        String s = System.getProperty("user.dir") + File.separator + "casic-system-manager" + File.separator + "casic-generator\\src\\main\\resources\\vm\\client";
        getFileList(s, templates);
        VelocityContext ctx = new VelocityContext();
        for (String template : templates) {
            //渲染模板
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            ctx.put("clientCode", componentCode);
            ctx.put("client", componentCode);
            ctx.put("up_client", toUpperCaseFirstOne(componentCode));
            File file = getLocalFileName(template, rootPath + clientPath, componentCode);
            if ("Application.java".equals(file.getName())) {
                String parent = file.getParent();
                file.delete();
                file = new File(parent + File.separator +  "SsoClient" + toUpperCaseFirstOne(componentCode) + "Application.java");
            }
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                BufferedWriter writer  = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));
                tpl.merge(ctx, writer);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
