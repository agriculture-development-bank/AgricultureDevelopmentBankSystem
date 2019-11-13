package com.casic.web.controller.tool;

import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.enums.BusinessType;
import com.casic.common.utils.UuidUtils;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.util.ShiroUtils;
import com.casic.framework.web.base.BaseController;
import com.casic.generator.domain.CodeDatasource;
import com.casic.generator.service.ICodeDatasourceService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 数据源管理 信息操作处理
 * 
 * @author fangzhi
 * @date 2019-04-03
 */
@Controller
@RequestMapping("/tool/codeDatasource")
public class CodeDatasourceController extends BaseController
{
    private String prefix = "tool/codeDatasource";
	
	@Autowired
	private ICodeDatasourceService codeDatasourceService;
	
	@RequiresPermissions("tool:codeDatasource:view")
	@GetMapping()
	public String codeDatasource()
	{
	    return prefix + "/codeDatasource";
	}

	@RequiresPermissions("tool:codeDatasource:view")
	@RequestMapping("/selectView")
	public String selectView(){
		return prefix + "/selectView";
	}
	
	/**
	 * 查询数据源管理列表
	 */
	@RequiresPermissions("tool:codeDatasource:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(CodeDatasource codeDatasource)
	{
		startPage();
        List<CodeDatasource> list = codeDatasourceService.selectCodeDatasourceList(codeDatasource);
		return getDataTable(list);
	}
	
	/**
	 * 新增数据源管理
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存数据源管理
	 */
	@RequiresPermissions("tool:codeDatasource:add")
	@Log(title = "数据源管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(CodeDatasource codeDatasource)
	{		
		if(codeDatasource != null && StringUtils.isEmpty(codeDatasource.getId())) {
			String uuidString = UuidUtils.getUUIDString();
			codeDatasource.setId(uuidString);
			codeDatasource.setCreateBy(String.valueOf(ShiroUtils.getUserId()));
			codeDatasource.setCreateTime(new Date());
			codeDatasource.setDelFlag("0");
		}
		return toAjax(codeDatasourceService.insertCodeDatasource(codeDatasource));
	}

	/**
	 * 修改数据源管理
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, ModelMap mmap)
	{
		CodeDatasource codeDatasource = codeDatasourceService.selectCodeDatasourceById(id);
		mmap.put("codeDatasource", codeDatasource);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存数据源管理
	 */
	@RequiresPermissions("tool:codeDatasource:edit")
	@Log(title = "数据源管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(CodeDatasource codeDatasource)
	{
		codeDatasource.setUpdateBy(String.valueOf(ShiroUtils.getUserId()));
		codeDatasource.setUpdateTime(new Date());
		return toAjax(codeDatasourceService.updateCodeDatasource(codeDatasource));
	}
	
	/**
	 * 删除数据源管理
	 */
	@RequiresPermissions("tool:codeDatasource:remove")
	@Log(title = "数据源管理", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(codeDatasourceService.deleteCodeDatasourceByIds(ids));
	}
	
}
