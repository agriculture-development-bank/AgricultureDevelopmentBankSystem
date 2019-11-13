package com.casic.auth.service.impl;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.casic.auth.mapper.AuthResourceMapper;
import com.casic.auth.mapper.OAuthClientDetailsMapper;
import com.casic.common.web.domain.bo.*;
import com.casic.common.web.domain.vo.AuthUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casic.auth.mapper.AuthUserMapper;
import com.casic.auth.mapper.AuthUserRoleMapper;
import com.casic.auth.service.IAuthUserService;
import com.casic.common.constant.UserConstants;
import com.casic.common.support.Convert;
import com.casic.common.utils.StringUtils;

/**
 * 用户 服务层实现
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
@Service
public class AuthUserServiceImpl implements IAuthUserService 
{
	@Autowired
	private AuthUserMapper userMapper;

	@Autowired
	private AuthUserRoleMapper authUserRoleMapper;

	@Autowired
	private OAuthClientDetailsMapper oauthClientDetailsMapper;

    @Autowired
    private AuthResourceMapper resourceMapper;


    /**
     * 查询用户信息
     * 
     * @param uid 用户ID
     * @return 用户信息
     */
    @Override
	public AuthUserVo selectUserById(String uid)
	{
	    return userMapper.selectUserById(uid);
	}

	@Override
	public AuthUserVo selectUserByLoginName(String username) {
		AuthUserVo u = userMapper.selectUserByLoginName(username);
		return u;
	}

	/**
     * 查询用户列表
     * 
     * @param user 用户信息
     * @return 用户集合
     */
	@Override
	public List<AuthUser> selectUserList(AuthUser user,String clientId)
	{
	    return userMapper.selectUserList(user,clientId);
	}
	
    /**
     * 新增用户
     * 
     * @param user 用户信息
     * @return 结果
     */
	@Override
	public int insertUser(AuthUser user)
	{
	    return userMapper.insertUser(user);
	}
	
	/**
	 * 修改用户
     * 
     * @param user 用户信息
     * @return 结果
     */
	@Override
	public int updateUser(AuthUser user,String clientId)
	{
		if(user != null && user.getAuthRoleIds() != null && user.getAuthRoleIds().length > 0) {
			String userId = user.getUid();
			String[] authRoleIds = user.getAuthRoleIds();
			authUserRoleMapper.deleteUserRoleByUserId(userId,clientId);
			authUserRoleMapper.batchInsertUserRoles(userId, authRoleIds);
		}
	    return userMapper.updateUser(user);
	}

	/**
	 * 删除用户对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteUserByIds(String ids,String clientId)
	{
		String[] idArray = Convert.toStrArray(ids);
		List<String> idList = Arrays.asList(idArray);
		int row = idList.size();
		idList.stream().forEach(id->{
			AuthUser authUser = userMapper.selectUserById(id);
			userMapper.deleteIntoUserClientRel(authUser.getSysUserId(),clientId);
			authUserRoleMapper.deleteUserRoleByUserId(id,clientId);
		});
		return row;
	}

	/**
	 * 用户名唯一性校验
	 * userName
	 */
	@Override
	public String checkUserNameUnique(String userName) {
		AuthUser authUser = userMapper.checkUserNameUnique(userName);
		if (StringUtils.isNotNull(authUser) && !userName.equals(authUser.getUsername())) {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
		return UserConstants.USER_NAME_UNIQUE;
	}


	
	@Override
	public String checkPhoneUnique(AuthUser user) {
		AuthUser authUser = userMapper.checkPhoneUnique(user);
        if (StringUtils.isNotNull(authUser) && !authUser.getUid().equals(user.getUid()) )
        {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
	}

	
	@Override
	public String checkEmailUnique(AuthUser user) {
		AuthUser authUser = userMapper.checkEmailUnique(user);
        if (StringUtils.isNotNull(authUser) && !authUser.getUid().equals(user.getUid()) )
        {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
	}

    /**
     * JSON.toJSONString(maps,SerializerFeature.WRITE_MAP_NULL_FEATURES, SerializerFeature.QuoteFieldNames);
     * 保留为null的空属性到json中
     * @param sysUserId
     * @return
     */
	@Override
	public JSONObject selectResourcesByUserId(String uid,String sysUserId) {
		//通过sysUserId查询当前用户有那些应用的访问权限
		String clients = selectClientBySysUserId(sysUserId);
		JSONObject obj = new JSONObject();
		if(clients != null && !"".equals(clients)){
			String[] clientIds = clients.split(",");
			for(String c : clientIds){
				List<AuthResource> currAllMenus = userMapper.selectResourcesByUserId(uid, c);
				currAllMenus = getChildPerms(currAllMenus, AuthResource.START_PARENT_ID);
				String jsonMenu = JSON.toJSONString(currAllMenus,SerializerFeature.WRITE_MAP_NULL_FEATURES, SerializerFeature.QuoteFieldNames);
				obj.put(c,jsonMenu);
			}
		}

		/*if(AuthSystemCode.ALL_SYSTEM_CODE.equals(sysCode)){
			List<OAuthClientDetails> oAuthClientDetails = oauthClientDetailsMapper.selectOAuthClientDetailsList(new OAuthClientDetails());
			for(OAuthClientDetails oAuthClientDetail : oAuthClientDetails){
				String clientId = oAuthClientDetail.getClientId();
				List<AuthResource> currAllMenus = userMapper.selectResourcesByUserId(uid, clientId);
				currAllMenus = getChildPerms(currAllMenus, AuthResource.START_PARENT_ID);
				String jsonMenu = JSON.toJSONString(currAllMenus,SerializerFeature.WRITE_MAP_NULL_FEATURES, SerializerFeature.QuoteFieldNames);
				obj.put(clientId,jsonMenu);
			}
		}else {
			List<AuthResource> currMenus = userMapper.selectResourcesByUserId(uid, sysCode);
			currMenus = getChildPerms(currMenus, AuthResource.START_PARENT_ID);
			String jsonMenu = JSON.toJSONString(currMenus,SerializerFeature.WRITE_MAP_NULL_FEATURES, SerializerFeature.QuoteFieldNames);
			obj.put(sysCode,jsonMenu);
		}*/

		return obj;
	}



    /**
     * 得到子节点列表
     */
    private List<AuthResource> getChildList(List<AuthResource> list, AuthResource t)
    {
        List<AuthResource> tlist = new ArrayList<AuthResource>();
        Iterator<AuthResource> it = list.iterator();
        while (it.hasNext())
        {
            AuthResource n = (AuthResource) it.next();
            if (n.getParentId().longValue() == t.getId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<AuthResource> list, AuthResource t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<AuthResource> list, AuthResource t)
    {
        // 得到子节点列表
        List<AuthResource> childList = getChildList(list, t);
        t.setChildren(childList);
        for (AuthResource tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                // 判断是否有子节点
                Iterator<AuthResource> it = childList.iterator();
                while (it.hasNext())
                {
                    AuthResource n = (AuthResource) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }


	/**
	 * 根据父节点的ID获取所有子节点
	 *
	 * @param list 分类表
	 * @param parentId 传入的父节点ID
	 * @return String
	 */
	public List<AuthResource> getChildPerms(List<AuthResource> list, int parentId)
	{
		List<AuthResource> returnList = new ArrayList<AuthResource>();
		for (Iterator<AuthResource> iterator = list.iterator(); iterator.hasNext();)
		{
			AuthResource t = (AuthResource) iterator.next();
			// 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
			if (t.getParentId() == parentId)
			{
				recursionFn(list, t);
				returnList.add(t);
			}
		}
		return returnList;
	}

	/**
	 * 根据sysUserId查询AuthUser
	 * @param sysUserId
	 * @return
	 */
	public AuthUser selectUserBySysUserId(String sysUserId){
		return userMapper.selectUserBySysUserId(sysUserId);
	}

	/**
	 * 根据条件查询用户对象
	 *
	 * @return 用户信息集合信息
	 */
	@Override
	public List<AuthUserVo> selectCandidateList(String deptCode, String careerCode){
		return userMapper.selectCandidateList(deptCode,careerCode);
	}

	@Override
	public int insertIntoUserClientRel(String sysUserId, String clientId) {
		return userMapper.insertIntoUserClientRel(sysUserId,clientId);
	}


	@Override
	public int isExistsUserClientRel(String sysUserId, String clientId) {
		return userMapper.isExistsUserClientRel(sysUserId,clientId);
	}

	@Override
	public String selectClientBySysUserId(String sysUserId) {
		return userMapper.selectClientBySysUserId(sysUserId);
	}

}











