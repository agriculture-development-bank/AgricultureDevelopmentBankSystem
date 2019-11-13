package com.casic.web.controller.auth;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.casic.system.domain.SysUser;
import com.casic.system.service.ISysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.casic.auth.service.IAuthRoleService;
import com.casic.auth.service.IAuthUserRoleService;
import com.casic.auth.service.IAuthUserService;
import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.enums.BusinessType;
import com.casic.common.utils.UuidUtils;
import com.casic.common.web.domain.bo.AuthRole;
import com.casic.common.web.domain.bo.AuthUser;
import com.casic.common.web.domain.bo.AuthUserRole;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.web.base.BaseController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户 信息操作处理
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
@Controller
@RequestMapping("/auth/user")
public class AuthUserController extends BaseController
{
    private String prefix = "auth/user";

	private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private IAuthUserService userService;
	
	@Autowired
	private IAuthRoleService authRoleService;
	
	@Autowired
	private IAuthUserRoleService authUserRoleService;

	@Autowired
	private ISysUserService sysUserService;
	
	@RequiresPermissions("auth:user:view")
	@GetMapping()
	public String user(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		String clientId = request.getParameter("clientId");
		model.addAttribute("clientId",clientId);
		return prefix + "/user";
	}
	
	/**
	 * 查询用户列表
	 */
	@RequiresPermissions("auth:user:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(AuthUser user,String sysCode)
	{
		startPage();
        List<AuthUser> list = userService.selectUserList(user,sysCode);
		return getDataTable(list);
	}
	
	/**
	 * 新增用户
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存用户
	 */
	@RequiresPermissions("auth:user:add")
	@Log(title = "用户", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(AuthUser user)
	{	
		if(user != null && StringUtils.isEmpty(user.getUid())) {
			String uuidString = UuidUtils.getUUIDString();
			user.setUid(uuidString);
		}
		//默认正常状态
		user.setStatus(1);
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		if(user != null && !StringUtils.isEmpty(user.getPassword())) {
			String password = passwordEncoder.encode(user.getPassword());
			user.setPassword(password);
			user.setSalt("123456");
		}
		return toAjax(userService.insertUser(user));
	}

	/**
	 * 导入保存用户
	 */
	@RequiresPermissions("auth:user:importUser")
	@Log(title = "用户", businessType = BusinessType.INSERT)
	@PostMapping("/importUser")
	@ResponseBody
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public AjaxResult importUser(String sysUserIds,String clientId)
	{
		int row = 1;
		if(StringUtils.isNotEmpty(sysUserIds)){
			String[] sysUserIdArray = sysUserIds.split(",");
			List<String> userIdList = Arrays.asList(sysUserIdArray);
			//保存到扩展表
			userIdList.stream().forEach(sysUserId->{
				AuthUser authUser = userService.selectUserBySysUserId(sysUserId);
				if(authUser == null){
					SysUser sysUser = sysUserService.selectUserById(sysUserId);
					if(sysUser != null){
						AuthUser obj = new AuthUser();
                        String uuidString = UuidUtils.getUUIDString();
                        obj.setUid(uuidString);
						obj.setUsername(sysUser.getLoginName());
						obj.setPassword(sysUser.getPassword());
						obj.setSalt(sysUser.getSalt());
						obj.setRealName(sysUser.getUserName());
						obj.setAvatar(sysUser.getAvatar());
						obj.setPhone(sysUser.getPhonenumber());
						obj.setEmail(sysUser.getEmail());
						obj.setSex(Integer.valueOf(sysUser.getSex()));
						//默认正常状态
						obj.setStatus(1);
						obj.setCreateTime(new Date());
						obj.setUpdateTime(new Date());
						obj.setSysCode(clientId);
						obj.setUserCareer(sysUser.getUserCareer());
						obj.setSysUserId(sysUserId);
						userService.insertUser(obj);
                        //保存到关系表
                        userService.insertIntoUserClientRel(sysUserId,clientId);
					}
				} else {
                    int existsUserClientRel = userService.isExistsUserClientRel(sysUserId, clientId);
                    if(existsUserClientRel == 0){
                        userService.insertIntoUserClientRel(sysUserId,clientId);
                    }
                }
			});



		}
		return toAjax(row);
	}

	/**
	 * 修改用户
	 */
	@GetMapping("/edit/{uid}")
	public String edit(@PathVariable("uid") String uid,String clientId, ModelMap mmap)
	{
		AuthUser user = userService.selectUserById(uid);
		mmap.put("user", user);
		AuthUserRole authUserRole = new AuthUserRole();
		authUserRole.setUserId(uid);
		List<AuthUserRole> userRoleSelect = authUserRoleService.selectUserRoleList(authUserRole);
		mmap.put("userRoleSelect", userRoleSelect);
		String sysCode = user.getSysCode();
		AuthRole authRole = new AuthRole();
		authRole.setSysCode(clientId);
		List<AuthRole> sysCodeUserRoles = authRoleService.selectRoleList(authRole);

		sysCodeUserRoles.forEach(role -> {
			role.setFlag(false);
			userRoleSelect.forEach(roleSel -> {
				if(roleSel.getRoleId() == role.getId()) {
					role.setFlag(true);
				}
			});
		});
		mmap.put("sysCodeUserRoles", sysCodeUserRoles);
		mmap.put("clientId",clientId);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存用户
	 */
	@RequiresPermissions("auth:user:edit")
	@Log(title = "用户", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(AuthUser user,String clientId)
	{	
		user.setUpdateTime(new Date());
		return toAjax(userService.updateUser(user,clientId));
	}
	
	/**
	 * 删除用户
	 */
	@RequiresPermissions("auth:user:remove")
	@Log(title = "用户", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids,String clientId)
	{		
		return toAjax(userService.deleteUserByIds(ids,clientId));
	}
	
	 /**
	  * 校验用户名
     */
    @PostMapping("/checkUserNameUnique")
    @ResponseBody
    public String checkUserNameUnique(String username)
    {
        return userService.checkUserNameUnique(username);
        
    }
    
    /**
     	* 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(AuthUser user)
    {
        return userService.checkPhoneUnique(user);
    }

    /**
     	* 校验email邮箱
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(AuthUser user)
    {
        return userService.checkEmailUnique(user);
    }
    
    
    public static void main(String[] args) {
    	String password = new Md5Hash("admin" + "111111" + "zn2izc").toHex().toString();
    	System.out.println(password);
		String hash = passwordEncoder.encode("111111");
    	System.out.println(hash);
	}

}
