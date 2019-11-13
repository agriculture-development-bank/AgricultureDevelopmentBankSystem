package com.casic.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casic.auth.mapper.AuthUserRoleMapper;
import com.casic.auth.service.IAuthUserRoleService;
import com.casic.common.support.Convert;
import com.casic.common.web.domain.bo.AuthUserRole;

/**
 * 用户角色关联 服务层实现
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
@Service
public class AuthUserRoleServiceImpl implements IAuthUserRoleService 
{
	@Autowired
	private AuthUserRoleMapper userRoleMapper;

	/**
     * 查询用户角色关联信息
     * 
     * @param iD 用户角色关联ID
     * @return 用户角色关联信息
     */
    @Override
	public AuthUserRole selectUserRoleById(Integer iD)
	{
	    return userRoleMapper.selectUserRoleById(iD);
	}
	
	/**
     * 查询用户角色关联列表
     * 
     * @param userRole 用户角色关联信息
     * @return 用户角色关联集合
     */
	@Override
	public List<AuthUserRole> selectUserRoleList(AuthUserRole userRole)
	{
	    return userRoleMapper.selectUserRoleList(userRole);
	}
	
    /**
     * 新增用户角色关联
     * 
     * @param userRole 用户角色关联信息
     * @return 结果
     */
	@Override
	public int insertUserRole(AuthUserRole userRole)
	{
	    return userRoleMapper.insertUserRole(userRole);
	}
	
	/**
     * 修改用户角色关联
     * 
     * @param userRole 用户角色关联信息
     * @return 结果
     */
	@Override
	public int updateUserRole(AuthUserRole userRole)
	{
	    return userRoleMapper.updateUserRole(userRole);
	}

	/**
     * 删除用户角色关联对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteUserRoleByIds(String ids)
	{
		return userRoleMapper.deleteUserRoleByIds(Convert.toStrArray(ids));
	}

	@Override
	public int batchInsertUserRoles(String userId, String[] roldIds) {
		int n = userRoleMapper.batchInsertUserRoles(userId, roldIds);
		return n;
	}
	
}
