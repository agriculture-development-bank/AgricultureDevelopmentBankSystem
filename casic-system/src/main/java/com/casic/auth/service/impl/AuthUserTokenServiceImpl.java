package com.casic.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casic.auth.mapper.AuthUserTokenMapper;
import com.casic.auth.service.IAuthUserTokenService;
import com.casic.common.support.Convert;
import com.casic.common.web.domain.bo.AuthUserToken;

/**
 * 用户Jwt相关 服务层实现
 * 
 * @author yuzengwen
 * @date 2018-12-06
 */
@Service
public class AuthUserTokenServiceImpl implements IAuthUserTokenService 
{
	@Autowired
	private AuthUserTokenMapper userTokenMapper;

	/**
	 * 查询用户Jwt相关信息
     * 
     * @param id 用户Jwt相关ID
     * @return 用户Jwt相关信息
     */
    @Override
	public AuthUserToken selectUserTokenById(Integer id)
	{
	    return userTokenMapper.selectUserTokenById(id);
	}
	
	/**
	 * 查询用户Jwt相关列表
     * 
     * @param userToken 用户Jwt相关信息
     * @return 用户Jwt相关集合
     */
	@Override
	public List<AuthUserToken> selectUserTokenList(AuthUserToken userToken)
	{
	    return userTokenMapper.selectUserTokenList(userToken);
	}
	
    /**
     * 新增用户Jwt相关
     * 
     * @param userToken 用户Jwt相关信息
     * @return 结果
     */
	@Override
	public int insertUserToken(AuthUserToken userToken)
	{
	    return userTokenMapper.insertUserToken(userToken);
	}
	
	/**
     	* 修改用户Jwt相关
     * 
     * @param userToken 用户Jwt相关信息
     * @return 结果
     */
	@Override
	public int updateUserToken(AuthUserToken userToken)
	{
	    return userTokenMapper.updateUserToken(userToken);
	}

	/**
     	* 删除用户Jwt相关对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteUserTokenByIds(String ids)
	{
		return userTokenMapper.deleteUserTokenByIds(Convert.toStrArray(ids));
	}
	
}
