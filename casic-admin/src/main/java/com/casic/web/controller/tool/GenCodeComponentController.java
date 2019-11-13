package com.casic.web.controller.tool;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.casic.common.utils.StringUtils;
import com.casic.common.web.page.TableDataInfo;
import com.casic.generator.domain.GenCodeComponent;
import com.casic.generator.domain.GenCodeDomain;
import com.casic.generator.domain.vo.GenCodeComponentVo;
import com.casic.generator.service.IGenCodeComponentService;
import com.casic.generator.service.IGenCodeDomainService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.casic.common.annotation.Log;
import com.casic.common.enums.BusinessType;
import com.casic.framework.web.base.BaseController;
import com.casic.common.base.AjaxResult;

/**
 * 组件配置 信息操作处理
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
@Controller
@RequestMapping("/tool/genCodeComponent")
public class GenCodeComponentController extends BaseController
{
    private String prefix = "tool/genCodeComponent";
	
	@Autowired
	private IGenCodeComponentService genCodeComponentService;
	@Autowired
	private IGenCodeDomainService genCodeDomainService;
	
	@RequiresPermissions("tool:genCodeComponent:view")
	@GetMapping()
	public String genCodeComponent()
	{
	    return prefix + "/genCodeComponent";
	}
	
	/**
	 * 查询组件配置列表
	 */
	@RequiresPermissions("tool:genCodeComponent:list")
	@RequestMapping("/list")
	@ResponseBody
	public TableDataInfo list(String domainId,GenCodeComponentVo genCodeComponent)
	{
		if (StringUtils.isNotEmpty(domainId)){
			genCodeComponent.setDomainId(domainId);
		}
		startPage();
        List<GenCodeComponentVo> list = genCodeComponentService.selectGenCodeComponentList(genCodeComponent);
		return getDataTable(list);
	}
	
	/**
	 * 新增组件配置
	 */
	@GetMapping("/add")
	public String add(String domainId,String domainName, ModelMap mmap)
	{
		mmap.put("domainId",domainId);
		mmap.put("domainName",domainName);
		return prefix + "/add";
	}
	
	/**
	 * 新增保存组件配置
	 */
	@RequiresPermissions("tool:genCodeComponent:add")
	@Log(title = "组件配置", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GenCodeComponentVo genCodeComponent)
	{
		genCodeComponent.setDelFlag("0");
		genCodeComponent.setCreateBy(getUserId()+"");
		genCodeComponent.setCreateTime(new Date());
		return toAjax(genCodeComponentService.insertGenCodeComponent(genCodeComponent));
	}

	/**
	 * 修改组件配置
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, ModelMap mmap)
	{
		GenCodeComponentVo genCodeComponent = genCodeComponentService.selectGenCodeComponentById(id);
		mmap.put("genCodeComponent", genCodeComponent);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存组件配置
	 */
	@RequiresPermissions("tool:genCodeComponent:edit")
	@Log(title = "组件配置", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GenCodeComponentVo genCodeComponent)
	{
		genCodeComponent.setUpdateBy(getUserId()+"");
		genCodeComponent.setUpdateTime(new Date());
		return toAjax(genCodeComponentService.updateGenCodeComponent(genCodeComponent));
	}
	
	/**
	 * 删除组件配置
	 */
	@RequiresPermissions("tool:genCodeComponent:remove")
	@Log(title = "组件配置", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(genCodeComponentService.deleteGenCodeComponentByIds(ids));
	}

	/**
	 * 加载部门列表树
	 */
	@GetMapping("/domainComponentTreeData")
	@ResponseBody
	public List<Map<String, Object>> treeData()
	{
		List<GenCodeDomain> domainList = genCodeDomainService.selectGenCodeDomainList(new GenCodeDomain());
		List<Map<String, Object>> tree = genCodeDomainService.getDomainComponentTrees(domainList);
		return tree;
	}

	/**
	 * 校验组件名称
	 */
	@PostMapping("/checkComponentNameUnique")
	@ResponseBody
	public String checkComponentNameUnique(GenCodeComponent genCodeComponent){
		return genCodeComponentService.checkComponentNameUnique(genCodeComponent);
	}

	/**
	 * 校验组件名称
	 */
	@PostMapping("/checkComponentCodeUnique")
	@ResponseBody
	public String checkComponentCodeUnique(GenCodeComponent genCodeComponent){
		return genCodeComponentService.checkComponentCodeUnique(genCodeComponent);
	}

	/**
	 * 校验对应应用
	 */
	@PostMapping("/checkClientIdUnique")
	@ResponseBody
	public String checkClientIdUnique(GenCodeComponent genCodeComponent){
		return genCodeComponentService.checkClientIdUnique(genCodeComponent);
	}

	@RequiresPermissions("tool:genCodeComponent:view")
	@RequestMapping("/listView")
	public String listView()
	{
		return prefix + "/listView";
	}

	@RequiresPermissions("tool:genCodeComponentTable:view")
	@RequestMapping("/componentTableListView")
	public String componentTableListView()
	{
		return prefix + "/componentTableListView";
	}
	
}
