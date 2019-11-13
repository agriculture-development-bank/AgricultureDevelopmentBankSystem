package com.casic.web.controller.auth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.casic.common.web.domain.bo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.casic.auth.service.IAuthResourceService;
import com.casic.auth.service.IAuthRoleResourceService;
import com.casic.auth.service.IAuthRoleService;
import com.casic.auth.service.IAuthUserRoleService;
import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.constant.UserConstants;
import com.casic.common.enums.BusinessType;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.web.base.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 角色 信息操作处理
 * @author yuzengwen
 * @date 2018-10-25
 */
@Controller
@RequestMapping("/auth/role")
public class AuthRoleController extends BaseController
{
    private String prefix = "auth/role";
	
	@Autowired
	private IAuthRoleService authRoleService;
	
	@Autowired
	private IAuthRoleResourceService authRoleResourceService;
	
	@Autowired
	private IAuthResourceService authResourceService;
	
	@Autowired
	private IAuthUserRoleService authUserRoleService;
	
	
	@RequiresPermissions("auth:role:view")
	@GetMapping()
	public String role(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		String clientId = request.getParameter("clientId");
		model.addAttribute("clientId",clientId);
		return prefix + "/role";
	}
	
	/**
	 * 查询角色列表
	 */
	@RequiresPermissions("auth:role:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(AuthRole role)
	{
		startPage();
        List<AuthRole> list = authRoleService.selectRoleList(role);
		return getDataTable(list);
	}
	
	
	/**
	 * 查询角色列表,带选中状态
	 */
	@RequiresPermissions("auth:role:list")
	@PostMapping("/getUserRoles")
	@ResponseBody
	public List<AuthRole> getUserRoles(String sysCode,String userId)
	{
		AuthRole authRole = new AuthRole();
		authRole.setSysCode(sysCode);
        List<AuthRole> authSysRoles = authRoleService.selectRoleList(authRole);
        
        AuthUserRole authUserRole = new AuthUserRole();
		authUserRole.setUserId(userId);
        List<AuthUserRole> userRoleSelect = authUserRoleService.selectUserRoleList(authUserRole);
        
        authSysRoles.forEach(r -> {
			r.setFlag(false);
			userRoleSelect.forEach(roleSel -> {
				if(roleSel.getRoleId() == r.getId()) {
					r.setFlag(true);
				}
			});
		});
		return authSysRoles;
	}
	
	/**
	 * 新增角色
	 */
	@GetMapping("/add")
	public String add(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		String clientId = request.getParameter("clientId");
		model.addAttribute("clientId",clientId);
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存角色
	 */
	@RequiresPermissions("auth:role:add")
	@Log(title = "角色", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(AuthRole role)
	{	
		role.setCreateTime(new Date());
		role.setUpdateTime(new Date());
		return toAjax(authRoleService.insertRole(role));
	}

	/**
	 * 修改角色
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		AuthRole role = authRoleService.selectRoleById(id);
		mmap.put("role", role);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存角色
	 */
	@RequiresPermissions("auth:role:edit")
	@Log(title = "角色", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(AuthRole role)
	{		
		role.setUpdateTime(new Date());
		return toAjax(authRoleService.updateRole(role));
	}
	
	/**
	 * 删除角色
	 */
	@RequiresPermissions("auth:role:remove")
	@Log(title = "角色", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(authRoleService.deleteRoleByIds(ids));
	}
	
	
   /**
	  * 校验橘色编码
    */
   @PostMapping("/checkRoleCodeUnique")
   @ResponseBody
   public String checkRoleCodeUnique(String code)
   {
       return authRoleService.checkRoleCodeUnique(code);
   }
	
   
   
   
   /**
    * 修改保存数据权限
    */
   @Log(title = "应用角色管理", businessType = BusinessType.UPDATE)
   @GetMapping("/roleOperate/{roleId}")
   public String roleOperate(@PathVariable("roleId") String roleId,ModelMap mmap)
   {
	   AuthRole authRole = authRoleService.selectRoleById(Integer.parseInt(roleId));
	   mmap.put("authRole", authRole);
       return prefix + "/rule";
   }
   
   
   /**
    * 修改保存数据权限
    */
   @Log(title = "应用角色管理", businessType = BusinessType.UPDATE)
   @GetMapping("/roleResourceTreeData")
   @ResponseBody
   public List<Map<String, Object>> roleResourceTreeData(
   		@RequestParam("roleId") String roleId,
		@RequestParam("sysCode") String sysCode )
   {
	   List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
	   AuthResource sqlResource = new AuthResource();
	   if(!AuthSystemCode.ALL_SYSTEM_CODE.equals(sysCode)){
		   sqlResource.setSysCode(sysCode);
	   }
       List<AuthResource> resourceList = authResourceService.selectResourceList(sqlResource);
	   if(StringUtils.isNotEmpty(roleId)) {
		   AuthRoleResource authRoleResource = new AuthRoleResource();
		   authRoleResource.setRoleId(Integer.parseInt(roleId));
		   List<AuthRoleResource> authRoleResources = authRoleResourceService.selectRoleResourceList(authRoleResource);
		   List<String> nodes = new ArrayList<String>();
		   for(AuthRoleResource resRole : authRoleResources) {
			   Integer resourceId = resRole.getResourceId();
			   AuthResource resource = authResourceService.selectResourceById(resourceId);
			   if(resource != null) {
				   String nodeName = resource.getId() + resource.getName();
				   nodes.add(nodeName);
			   }
		   }
		   trees = getTrees(resourceList,Boolean.TRUE,nodes);
		   
	   } else {
		   trees = getTrees(resourceList,Boolean.TRUE,null);
	   }
	   return trees;
   }
   
   
   
   /**
          * 对象转部门树
   *
   * @param resourceList 部门列表
   * @param isCheck 是否需要选中
   * @param roleResourcetList 角色已存在菜单列表
   * @return
   */
  private List<Map<String, Object>> getTrees(List<AuthResource> resourceList, boolean isCheck, List<String> roleResourcetList)
  {
      List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
      for (AuthResource res : resourceList)
      {
          if ( res.getStatus() == UserConstants.RESOURCE_NORMAL)
          {
              Map<String, Object> resMap = new HashMap<String, Object>();
              resMap.put("id", res.getId());
              resMap.put("pId", res.getParentId());
              resMap.put("name", res.getName());
              resMap.put("title", res.getName());
              if (isCheck)
              {
            	  resMap.put("checked", roleResourcetList.contains( (res.getId() + res.getName()) ) );
              }
              else
              {
            	  resMap.put("checked", false);
              }
              trees.add(resMap);
          }
      }
      return trees;
  }
  
  
  /**
	 * 删除角色
	 */
	@RequiresPermissions("auth:role:edit")
	@Log(title = "角色", businessType = BusinessType.UPDATE)
	@PostMapping( "/saveOperate")
	@ResponseBody
	public AjaxResult saveOperate(String roleId,String resourceIds)
	{		
		String[] resIds = resourceIds.split(",");
		List<String> asList = Arrays.asList(resIds);
		return toAjax(authRoleService.updateRoleResources(roleId, asList));
	}
	
  
  
  
 
}







