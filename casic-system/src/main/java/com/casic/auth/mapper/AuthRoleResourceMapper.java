package com.casic.auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.casic.common.web.domain.bo.AuthRoleResource;	

/**
 * 资源角色关联 数据层
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
public interface AuthRoleResourceMapper 
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
     * 删除资源角色关联
     * 
     * @param iD 资源角色关联ID
     * @return 结果
     */
	public int deleteRoleResourceById(Integer id);
	
	/**
     * 批量删除资源角色关联
     * 
     * @param iDs 需要删除的数据ID
     * @return 结果
     */
	public int deleteRoleResourceByIds(String[] ids);
	
	/**
	 * 批量删除资源角色关联
     * 
     * @param iDs 需要删除的数据ID
     * @return 结果
     */
	public int deleteResourceByRoleIds(@Param("roleIds") List<String> roleIds);
	
	
	public int batchInsertRoleResources(@Param("roleId") String roleId, @Param("resourceIds") List<String> resourceIds);
	
	
}