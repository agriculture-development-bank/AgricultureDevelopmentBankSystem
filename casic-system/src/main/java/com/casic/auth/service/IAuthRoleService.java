package com.casic.auth.service;

import java.util.List;

import com.casic.common.web.domain.bo.AuthRole;

/**
 * 角色 服务层
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
public interface IAuthRoleService 
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
	 * 删除角色信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteRoleByIds(String ids);
	
	public String checkRoleCodeUnique(String code);
	
	 /**
	  * 修改数据权限信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    public int updateRoleResources(String roleId, List<String> resourceIds);
	
	
	
}
