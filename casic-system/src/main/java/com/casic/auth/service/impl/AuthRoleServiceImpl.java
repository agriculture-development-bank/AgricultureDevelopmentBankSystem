package com.casic.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.casic.auth.mapper.AuthRoleMapper;
import com.casic.auth.mapper.AuthRoleResourceMapper;
import com.casic.auth.service.IAuthRoleService;
import com.casic.common.constant.UserConstants;
import com.casic.common.support.Convert;
import com.casic.common.utils.StringUtils;
import com.casic.common.web.domain.bo.AuthRole;

/**
 * 角色 服务层实现
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
@Service
public class AuthRoleServiceImpl implements IAuthRoleService 
{
	@Autowired
	private AuthRoleMapper roleMapper;
	
	@Autowired
	private AuthRoleResourceMapper roleResourceMapper;
	

	/**
	 * 查询角色信息
     * 
     * @param id 角色id
     * @return 角色信息
     */
    @Override
	public AuthRole selectRoleById(Integer id)
	{
	    return roleMapper.selectRoleById(id);
	}
	
	/**
	 * 查询角色列表
     * 
     * @param role 角色信息
     * @return 角色集合
     */
	@Override
	public List<AuthRole> selectRoleList(AuthRole role)
	{
	    return roleMapper.selectRoleList(role);
	}
	
    /**
     	* 新增角色
     * 
     * @param role 角色信息
     * @return 结果
     */
	@Override
	public int insertRole(AuthRole role)
	{
	    return roleMapper.insertRole(role);
	}
	
	/**
               * 修改角色
     * 
     * @param role 角色信息
     * @return 结果
     */
	@Override
	public int updateRole(AuthRole role)
	{
	    return roleMapper.updateRole(role);
	}

	/**
	 * 删除角色对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRoleByIds(String ids)
	{
		return roleMapper.deleteRoleByIds(Convert.toStrArray(ids));
	}

	
	@Override
	public String checkRoleCodeUnique(String code) {
		AuthRole authRole = roleMapper.checkRoleCodeUnique(code);
		if (StringUtils.isNotNull(authRole) && !code.equals(authRole.getCode())) {
            return UserConstants.ROLE_KEY_NOT_UNIQUE;
        }
		return UserConstants.ROLE_KEY_UNIQUE;
	}
	
	
    /**
	     * 修改数据权限信息
	 * @param role 角色信息
	 * @return 结果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateRoleResources(String roleId,List<String> resourceIds)
	{
	    // 删除角色与资源关联
		List<String> roles = new ArrayList<String>();
		roles.add(roleId);
		roleResourceMapper.deleteResourceByRoleIds(roles);
		// 新增角色和资源信息
		int rows = roleResourceMapper.batchInsertRoleResources(roleId, resourceIds);
	    return rows;
	}

}






