package com.casic.auth.mapper;

import java.util.List;

import com.casic.common.web.domain.bo.AuthRole;	

/**
 * 角色 数据层
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
public interface AuthRoleMapper 
{
	/**
     	* 查询角色信息
     * 
     * @param id 角色ID
     * @return 角色信息
     */
	public AuthRole selectRoleById(Integer id);
	
	/**
	 * 查询角色列表
     * 
     * @param role 角色信息
     * @return 角色集合
     */
	public List<AuthRole> selectRoleList(AuthRole role);
	
	/**
     	* 新增角色
     * 
     * @param role 角色信息
     * @return 结果
     */
	public int insertRole(AuthRole role);
	
	/**
     	* 修改角色
     * 
     * @param role 角色信息
     * @return 结果
     */
	public int updateRole(AuthRole role);
	
	/**
     	* 删除角色
     * 
     * @param iD 角色ID
     * @return 结果
     */
	public int deleteRoleById(Integer id);
	
	/**
	 * 批量删除角色
     * 
     * @param iDs 需要删除的数据ID
     * @return 结果
     */
	public int deleteRoleByIds(String[] ids);
	
	
	public AuthRole checkRoleCodeUnique(String code);
	
	
}