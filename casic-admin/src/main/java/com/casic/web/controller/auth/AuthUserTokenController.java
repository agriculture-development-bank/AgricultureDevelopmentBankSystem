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

import com.casic.auth.service.IAuthUserTokenService;
import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.enums.BusinessType;
import com.casic.common.web.domain.bo.AuthUserToken;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.web.base.BaseController;

/**
 * 用户Jwt相关 信息操作处理
 * 
 * @author yuzengwen
 * @date 2018-12-06
 */
@Controller
@RequestMapping("/auth/userToken")
public class AuthUserTokenController extends BaseController
{
    private String prefix = "auth/userToken";
	
	@Autowired
	private IAuthUserTokenService userTokenService;
	
	@RequiresPermissions("auth:userToken:view")
	@GetMapping()
	public String userToken()
	{
	    return prefix + "/userToken";
	}
	
	/**
	 * 查询用户Jwt相关列表
	 */
	@RequiresPermissions("auth:userToken:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(AuthUserToken userToken)
	{
		startPage();
        List<AuthUserToken> list = userTokenService.selectUserTokenList(userToken);
		return getDataTable(list);
	}
	
	/**
	 * 新增用户Jwt相关
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存用户Jwt相关
	 */
	@RequiresPermissions("auth:userToken:add")
	@Log(title = "用户Jwt相关", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(AuthUserToken userToken)
	{		
		return toAjax(userTokenService.insertUserToken(userToken));
	}

	/**
	 * 修改用户Jwt相关
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		AuthUserToken userToken = userTokenService.selectUserTokenById(id);
		mmap.put("userToken", userToken);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存用户Jwt相关
	 */
	@RequiresPermissions("auth:userToken:edit")
	@Log(title = "用户Jwt相关", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(AuthUserToken userToken)
	{		
		userToken.setUpdateTime(new Date());
		return toAjax(userTokenService.updateUserToken(userToken));
	}
	
	/**
	 * 删除用户Jwt相关
	 */
	@RequiresPermissions("auth:userToken:remove")
	@Log(title = "用户Jwt相关", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(userTokenService.deleteUserTokenByIds(ids));
	}
	
}
