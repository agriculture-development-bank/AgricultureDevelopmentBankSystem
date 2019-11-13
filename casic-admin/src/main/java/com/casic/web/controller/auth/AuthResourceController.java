package com.casic.web.controller.auth;

import java.util.List;
import java.util.Map;

import com.casic.common.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.casic.auth.service.IAuthResourceService;
import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.enums.BusinessType;
import com.casic.common.web.domain.bo.AuthResource;
import com.casic.common.web.domain.bo.AuthSystemCode;
import com.casic.framework.web.base.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 资源(菜单,资源) 信息操作处理
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
@Controller
@RequestMapping("/auth/resource")
public class AuthResourceController extends BaseController
{
    private String prefix = "auth/resource";
	
	@Autowired
	private IAuthResourceService resourceService;
	
	@RequiresPermissions("auth:resource:view")
	@GetMapping()
	public String resource(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		String clientId = request.getParameter("clientId");
		model.addAttribute("clientId",clientId);
	    return prefix + "/resource";
	}

	@RequiresPermissions("auth:resource:view")
	@RequestMapping("/componentResource/{clientId}")
	public String componentResource(@PathVariable("clientId") String clientId,ModelMap mmap)
	{
		if(clientId == null){
			clientId = "";
		}
		mmap.put("clientId",clientId);
		return prefix + "/componentResource";
	}
	
	/**
	 * 查询资源(菜单,资源)列表
	 */
	@RequiresPermissions("auth:resource:list")
	@RequestMapping("/list")
	@ResponseBody
	public List<AuthResource> list(String sysCode, AuthResource resource,Model model)
	{
		startPage();
		if(StringUtils.isNotEmpty(sysCode)){
			resource.setSysCode(sysCode);
		}
        List<AuthResource> list = resourceService.selectResourceList(resource);
		model.addAttribute("clientId",sysCode);
		return list;
	}
	
	/**
	 * 新增资源(菜单,资源)
	 */
	@GetMapping("/add/{parentId}/{clientId}")
	public String add(@PathVariable("parentId") Integer parentId,@PathVariable("clientId")String clientId, ModelMap mmap) {
		AuthResource menu = null;
		if (AuthResource.START_PARENT_ID != parentId)
		{
			menu = resourceService.selectResourceById(parentId);
		}
		else
		{
			menu = new AuthResource();
			menu.setId(AuthResource.START_PARENT_ID);
			menu.setName("ROOT");
		}
		mmap.put("menu", menu);
		if(clientId == null || "".equals(clientId)){
			clientId = menu.getSysCode();
		}
		mmap.put("clientId",clientId);
		return prefix + "/add";
	}
	
	/**
	 * 新增保存资源(菜单,资源)
	 */
	@RequiresPermissions("auth:resource:add")
	@Log(title = "资源", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(AuthResource resource)
	{
		AuthResource parentResource = resourceService.selectResourceById(resource.getParentId());
		String parentIds = "";
		if(parentResource != null){
			parentIds = parentResource.getParentIds() + "," + resource.getParentId();
		}else{
			parentIds = resource.getParentId()+"";
		}
		resource.setParentIds(parentIds);
		return toAjax(resourceService.insertResource(resource));
	}

	/**
	 * 修改资源(菜单,资源)
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		AuthResource resource = resourceService.selectResourceById(id);
		mmap.put("resource", resource);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存资源(菜单,资源)
	 */
	@RequiresPermissions("auth:resource:edit")
	@Log(title = "资源菜单", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(AuthResource resource)
	{
		AuthResource parentResource = resourceService.selectResourceById(resource.getParentId());
		String parentIds = "";
		if(parentResource != null){
			parentIds = parentResource.getParentIds() + "," + resource.getParentId();
		}else{
			parentIds = resource.getParentId()+"";
		}

		resource.setParentIds(parentIds);
		return toAjax(resourceService.updateResource(resource));
	}
	
	/**
	 * 删除资源(菜单,资源)
	 */
	@RequiresPermissions("auth:resource:remove")
	@Log(title = "资源", businessType = BusinessType.DELETE)
	@PostMapping( "/remove/{id}")
	@ResponseBody
	public AjaxResult remove(@PathVariable("id")String ids)
	{
		if(resourceService.selectCountResourceByParentId(Integer.parseInt(ids))>0){
			return error(1, "存在子菜单,不允许删除");
		}
		return toAjax(resourceService.deleteResourceByIds(ids));
	}
	
	/**
	 * 选择菜单树
     */
    @GetMapping("/selectMenuTree/{resourceId}/{opt}")
    public String selectMenuTree(@PathVariable("resourceId") Integer resourceId, @PathVariable("opt") String opt,ModelMap mmap)
    {
		AuthResource selectResource = resourceService.selectResourceById(resourceId);
    	if(selectResource != null) {
    		mmap.put("opt", opt);
    		mmap.put("resource", selectResource);
    	}
        return prefix + "/seltree";
    }
	
    
    /**
     	* 加载所有菜单列表树
     */
    @GetMapping("/menuTreeData/{sysCode}")
    @ResponseBody
    public List<Map<String, Object>> menuTreeData(@PathVariable("sysCode") String sysCode)
    {
		if(AuthSystemCode.ALL_SYSTEM_CODE.equals(sysCode)){
			sysCode = "";
		}
        List<Map<String, Object>> tree = resourceService.menuTreeData(sysCode);
        return tree;
    }
	
}





