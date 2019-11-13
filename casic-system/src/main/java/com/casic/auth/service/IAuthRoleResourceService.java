package com.casic.auth.service;

import java.util.List;

import com.casic.common.web.domain.bo.AuthRoleResource;

/**
 * 资源角色关联 服务层
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
public interface IAuthRoleResourceService 
{
	/**
     * 查询资源角色关联信息
     * 
     * @param iD 资源角色关联ID
     * @return 资源角色关联信息
     */
	public AuthRoleResource selectRoleResourceById(Integer id);
	
	/**
     * 查询资源角色关联列表
     * 
     * @param roleResource 资源角色关联信息
     * @return 资源角色关联集合
     */
	public List<AuthRoleResource> selectRoleResourceList(AuthRoleResource roleResource);
	
	/**
     * 新增资源角色关联
     * 
     * @param roleResource 资源角色关联信息
     * @return 结果
     */
	public int insertRoleResource(AuthRoleResource roleResource);
	
	/**
     * 修改资源角色关联
     * 
     * @param roleResource 资源角色关联信息
     * @return 结果
     */
	public int updateRoleResource(AuthRoleResource roleResource);
		
	/**
     * 删除资源角色关联信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteRoleResourceByIds(String ids);
	
}
