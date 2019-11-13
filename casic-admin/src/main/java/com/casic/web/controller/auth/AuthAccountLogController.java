package com.casic.web.controller.auth;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.casic.framework.web.base.BaseController;
import com.casic.auth.service.IAuthAccountLogService;
import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.enums.BusinessType;
import com.casic.common.web.domain.bo.AuthAccountLog;
import com.casic.common.web.page.TableDataInfo;

/**
 * 登录注册登出记录 信息操作处理
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
@Controller
@RequestMapping("/auth/accountLog")
public class AuthAccountLogController extends BaseController
{
    private String prefix = "auth/accountLog";
	
	@Autowired
	private IAuthAccountLogService accountLogService;
	
	@RequiresPermissions("auth:accountLog:view")
	@GetMapping()
	public String accountLog()
	{
	    return prefix + "/accountLog";
	}
	
	/**
	 * 查询登录注册登出记录列表
	 */
	@RequiresPermissions("auth:accountLog:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(AuthAccountLog accountLog)
	{
		startPage();
        List<AuthAccountLog> list = accountLogService.selectAccountLogList(accountLog);
		return getDataTable(list);
	}
	
	
	/**
	 * 删除登录注册登出记录
	 */
	@RequiresPermissions("auth:accountLog:remove")
	@Log(title = "登录注册登出记录", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(accountLogService.deleteAccountLogByIds(ids));
	}
	
}
