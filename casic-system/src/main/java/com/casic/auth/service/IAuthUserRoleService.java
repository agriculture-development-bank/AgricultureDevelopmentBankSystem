package com.casic.auth.service;

import java.util.List;

import com.casic.common.web.domain.bo.AuthUserRole;

/**
 * 用户角色关联 服务层
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
public interface IAuthUserRoleService 
{
	/**
     * 查询用户角色关联信息
     * 
     * @param iD 用户角色关联ID
     * @return 用户角色关联信息
     */
	public AuthUserRole selectUserRoleById(Integer iD);
	
	/**
     * 查询用户角色关联列表
     * 
     * @param userRole 用户角色关联信息
     * @return 用户角色关联集合
     */
	public List<AuthUserRole> selectUserRoleList(AuthUserRole userRole);
	
	/**
     * 新增用户角色关联
     * 
     * @param userRole 用户角色关联信息
     * @return 结果
     */
	public int insertUserRole(AuthUserRole userRole);
	
	/**
     * 修改用户角色关联
     * 
     * @param userRole 用户角色关联信息
     * @return 结果
     */
	public int updateUserRole(AuthUserRole userRole);
		
	/**
     * 删除用户角色关联信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteUserRoleByIds(String ids);



	/**
     * 批量删除用户角色关联
     * @param roldIds 角色ID
     * @return 结果
     */
	public int batchInsertUserRoles(String userId, String[] roldIds);
	
}
