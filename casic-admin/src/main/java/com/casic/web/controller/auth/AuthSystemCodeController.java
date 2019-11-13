package com.casic.web.controller.auth;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.casic.auth.service.IAuthSystemCodeService;
import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.enums.BusinessType;
import com.casic.common.web.domain.bo.AuthSystemCode;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.util.ShiroUtils;
import com.casic.system.domain.SysUser;
import com.casic.framework.web.base.BaseController;

/**
 * 系统编码配置 信息操作处理
 * 
 * @author yuzengwen
 * @date 2018-10-26
 */
@Controller
@RequestMapping("/auth/systemCode")
public class AuthSystemCodeController extends BaseController
{
    private String prefix = "auth/systemCode";
	
	@Autowired
	private IAuthSystemCodeService systemCodeService;
	
	@RequiresPermissions("auth:systemCode:view")
	@GetMapping()
	public String systemCode()
	{
	    return prefix + "/systemCode";
	}
	
	/**
	 * 查询系统编码配置列表
	 */
	@RequiresPermissions("auth:systemCode:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(AuthSystemCode systemCode)
	{
		startPage();
        List<AuthSystemCode> list = systemCodeService.selectSystemCodeList(systemCode);
		return getDataTable(list);
	}
	
	/**
	 * 新增系统编码配置
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存系统编码配置
	 */
	@RequiresPermissions("auth:systemCode:add")
	@Log(title = "系统编码配置", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(AuthSystemCode systemCode)
	{	
		SysUser currUser = getSysUser();
		systemCode.setCreateBy(currUser.getLoginName());
		systemCode.setCreateTime(new Date());
		systemCode.setUpdateBy(currUser.getLoginName());
		systemCode.setUpdateTime(new Date());
		return toAjax(systemCodeService.insertSystemCode(systemCode));
	}

	/**
	 * 修改系统编码配置
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		AuthSystemCode systemCode = systemCodeService.selectSystemCodeById(id);
		mmap.put("systemCode", systemCode);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存系统编码配置
	 */
	@RequiresPermissions("auth:systemCode:edit")
	@Log(title = "系统编码配置", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(AuthSystemCode systemCode)
	{	
		SysUser currUser = ShiroUtils.getUser();
		systemCode.setUpdateBy(currUser.getLoginName());
		systemCode.setUpdateTime(new Date());
		return toAjax(systemCodeService.updateSystemCode(systemCode));
	}
	
	/**
	 * 删除系统编码配置
	 */
	@RequiresPermissions("auth:systemCode:remove")
	@Log(title = "系统编码配置", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(systemCodeService.deleteSystemCodeByIds(ids));
	}
	
}
