package com.casic.framework.web.service;

import java.util.ArrayList;
import java.util.List;

import com.casic.common.web.domain.bo.OAuthClientDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casic.auth.service.IAuthRoleService;
import com.casic.auth.service.IOAuthClientDetailsService;
import com.casic.common.web.domain.bo.AuthRole;
import com.casic.common.web.domain.bo.AuthSystemCode;

/**
 * west首创 html调用 thymeleaf 实现字典读取
 * 
 * @author yuzengwen
 */
@Service("authSystem")
public class AuthSystemService
{
    
    @Autowired
    private IOAuthClientDetailsService oauthClientDetailsService;
    
    @Autowired
    private IAuthRoleService authRoleService;

	/**
	 * 根据类型查询字典数据信息
	 * @param sysCode 系统编码
	 * @return 参数键值
	 */
	public List<AuthSystemCode> getSysCodeList(String sysCode)
	{
		OAuthClientDetails oauthClientDetails = new OAuthClientDetails();
		if(sysCode != null && !"".equals(sysCode)) {
			oauthClientDetails.setClientId(sysCode);
		}
		List<OAuthClientDetails> oAuthClientDetails = oauthClientDetailsService.selectOAuthClientDetailsList(oauthClientDetails);
		List<AuthSystemCode> authSystemCodes = new ArrayList<AuthSystemCode>();
		for (OAuthClientDetails oAuthClientDetail : oAuthClientDetails) {
			AuthSystemCode systemCode = new AuthSystemCode();
			systemCode.setSysCode(oAuthClientDetail.getClientId());
			systemCode.setSysName(oAuthClientDetail.getClientName());
			authSystemCodes.add(systemCode);
		}
		AuthSystemCode systemCode = new AuthSystemCode();
		systemCode.setSysCode(AuthSystemCode.ALL_SYSTEM_CODE);
		systemCode.setSysName(AuthSystemCode.ALL_SYSTEM_NAME);
		authSystemCodes.add(systemCode);
		return authSystemCodes;
	}

    /**
     	* 根据类型和字典键值查询字典数据信息
     * 
     * @param sysCode 系统编码
     * @return 字典标签
     */
    public String getSysCodeLabel(String sysCode)
    {
    	List<AuthSystemCode> sysCodes = getSysCodeList(sysCode);
    	if(sysCodes != null && sysCodes.size() == 1) {
    		return sysCodes.get(0).getSysName();
    	}
        return "";
    }
    
    
    /**
	 	* 根据类型和字典键值查询字典数据信息
	 * 
	 * @param sysCode 系统编码
	 * @return 字典标签
	 */
	public List<AuthRole> getRoleBySysCode(String sysCode)
	{
		AuthRole authRole = new AuthRole();
		authRole.setSysCode(sysCode);
		List<AuthRole> selRoles = authRoleService.selectRoleList(authRole);
	    return selRoles;
	}

    
}
