package com.casic.web.controller.auth;

import java.util.List;
import com.casic.auth.service.IOAuthClientDetailsService;
import com.casic.common.web.domain.bo.OAuthClientDetails;
import com.casic.common.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
 * 终端定义 信息操作处理
 * @author yuzengwen
 * @date 2019-04-15
 */
@Controller
@RequestMapping("/auth/oauthClientDetails")
public class OAuthClientDetailsController extends BaseController
{
    private String prefix = "auth/oauthClientDetails";
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private IOAuthClientDetailsService oauthClientDetailsService;
	
	@RequiresPermissions("auth:oauthClientDetails:view")
	@GetMapping()
	public String oauthClientDetails()
	{
	    return prefix + "/oauthClientDetails";
	}
	
	/**
	 * 查询终端定义列表
	 */
	@RequiresPermissions("auth:oauthClientDetails:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(OAuthClientDetails oauthClientDetails)
	{
		startPage();
        List<OAuthClientDetails> list = oauthClientDetailsService.selectOAuthClientDetailsList(oauthClientDetails);
		return getDataTable(list);
	}
	
	/**
	 * 新增终端定义
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存终端定义
	 */
	@RequiresPermissions("auth:oauthClientDetails:add")
	@Log(title = "终端定义", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(OAuthClientDetails oauthClientDetails)
	{
	    if(oauthClientDetails != null){
            String planClientSecret = oauthClientDetails.getClientSecret();
            String clientSecret = passwordEncoder.encode(planClientSecret);
            oauthClientDetails.setClientSecret(clientSecret);
        }
		return toAjax(oauthClientDetailsService.insertOAuthClientDetails(oauthClientDetails));
	}

	/**
	 * 修改终端定义
	 */
	@GetMapping("/edit/{clientId}")
	public String edit(@PathVariable("clientId") String clientId, ModelMap mmap)
	{
		OAuthClientDetails oauthClientDetails = oauthClientDetailsService.selectOAuthClientDetailsById(clientId);
		mmap.put("oauthClientDetails", oauthClientDetails);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存终端定义
	 */
	@RequiresPermissions("auth:oauthClientDetails:edit")
	@Log(title = "终端定义", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(OAuthClientDetails oauthClientDetails)
	{		
		return toAjax(oauthClientDetailsService.updateOAuthClientDetails(oauthClientDetails));
	}
	
	/**
	 * 删除终端定义
	 */
	@RequiresPermissions("auth:oauthClientDetails:remove")
	@Log(title = "终端定义", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(oauthClientDetailsService.deleteOAuthClientDetailsByIds(ids));
	}

	/**
	 * 选择视图
	 * @return 页面路径
	 */
	@RequestMapping(value = "/selectView")
	public String selectView() {
		return prefix + "/selectView";
	}
	
}
