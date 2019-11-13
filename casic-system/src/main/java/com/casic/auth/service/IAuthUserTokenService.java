package com.casic.auth.service;

import java.util.List;

import com.casic.common.web.domain.bo.AuthUserToken;

/**
 * 用户Jwt相关 服务层
 * 
 * @author yuzengwen
 * @date 2018-12-06
 */
public interface IAuthUserTokenService 
{
	/**
     * 查询用户Jwt相关信息
     * 
     * @param id 用户Jwt相关ID
     * @return 用户Jwt相关信息
     */
	public AuthUserToken selectUserTokenById(Integer id);
	
	/**
     * 查询用户Jwt相关列表
     * 
     * @param userToken 用户Jwt相关信息
     * @return 用户Jwt相关集合
     */
	public List<AuthUserToken> selectUserTokenList(AuthUserToken userToken);
	
	/**
     * 新增用户Jwt相关
     * 
     * @param userToken 用户Jwt相关信息
     * @return 结果
     */
	public int insertUserToken(AuthUserToken userToken);
	
	/**
     * 修改用户Jwt相关
     * 
     * @param userToken 用户Jwt相关信息
     * @return 结果
     */
	public int updateUserToken(AuthUserToken userToken);
		
	/**
     * 删除用户Jwt相关信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteUserTokenByIds(String ids);
	
}
