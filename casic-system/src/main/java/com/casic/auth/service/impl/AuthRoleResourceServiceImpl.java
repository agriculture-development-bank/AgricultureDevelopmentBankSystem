package com.casic.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casic.auth.mapper.AuthRoleResourceMapper;
import com.casic.auth.service.IAuthRoleResourceService;
import com.casic.common.support.Convert;
import com.casic.common.web.domain.bo.AuthRoleResource;

/**
 * 资源角色关联 服务层实现
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
@Service
public class AuthRoleResourceServiceImpl implements IAuthRoleResourceService 
{
	@Autowired
	private AuthRoleResourceMapper roleResourceMapper;

	/**
     * 查询资源角色关联信息
     * 
     * @param iD 资源角色关联ID
     * @return 资源角色关联信息
     */
    @Override
	public AuthRoleResource selectRoleResourceById(Integer id)
	{
	    return roleResourceMapper.selectRoleResourceById(id);
	}
	
	/**
     * 查询资源角色关联列表
     * 
     * @param roleResource 资源角色关联信息
     * @return 资源角色关联集合
     */
	@Override
	public List<AuthRoleResource> selectRoleResourceList(AuthRoleResource roleResource)
	{
	    return roleResourceMapper.selectRoleResourceList(roleResource);
	}
	
    /**
     * 新增资源角色关联
     * 
     * @param roleResource 资源角色关联信息
     * @return 结果
     */
	@Override
	public int insertRoleResource(AuthRoleResource roleResource)
	{
	    return roleResourceMapper.insertRoleResource(roleResource);
	}
	
	/**
     * 修改资源角色关联
     * 
     * @param roleResource 资源角色关联信息
     * @return 结果
     */
	@Override
	public int updateRoleResource(AuthRoleResource roleResource)
	{
	    return roleResourceMapper.updateRoleResource(roleResource);
	}

	/**
     * 删除资源角色关联对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteRoleResourceByIds(String ids)
	{
		return roleResourceMapper.deleteRoleResourceByIds(Convert.toStrArray(ids));
	}
	
}
