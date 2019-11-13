package com.casic.web.controller.auth;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.casic.auth.service.IAuthOperationLogService;
import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.enums.BusinessType;
import com.casic.common.web.domain.bo.AuthOperationLog;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.web.base.BaseController;

/**
 * 操作日志 信息操作处理
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
@Controller
@RequestMapping("/auth/operationLog")
public class AuthOperationLogController extends BaseController
{
    private String prefix = "auth/operationLog";
	
	@Autowired
	private IAuthOperationLogService operationLogService;
	
	@RequiresPermissions("auth:operationLog:view")
	@GetMapping()
	public String operationLog()
	{
	    return prefix + "/operationLog";
	}
	
	/**
	 * 查询操作日志列表
	 */
	@RequiresPermissions("auth:operationLog:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(AuthOperationLog operationLog)
	{
		startPage();
        List<AuthOperationLog> list = operationLogService.selectOperationLogList(operationLog);
		return getDataTable(list);
	}
	
	
	/**
	 * 删除操作日志
	 */
	@RequiresPermissions("auth:operationLog:remove")
	@Log(title = "操作日志", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(operationLogService.deleteOperationLogByIds(ids));
	}
	
}
