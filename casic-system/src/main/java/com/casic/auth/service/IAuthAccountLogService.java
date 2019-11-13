package com.casic.auth.service;

import java.util.List;

import com.casic.common.web.domain.bo.AuthAccountLog;

/**
 * 登录注册登出记录 服务层
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
public interface IAuthAccountLogService 
{
	/**
     * 查询登录注册登出记录信息
     * 
     * @param iD 登录注册登出记录ID
     * @return 登录注册登出记录信息
     */
	public AuthAccountLog selectAccountLogById(Integer id);
	
	/**
     * 查询登录注册登出记录列表
     * 
     * @param accountLog 登录注册登出记录信息
     * @return 登录注册登出记录集合
     */
	public List<AuthAccountLog> selectAccountLogList(AuthAccountLog accountLog);
	
	/**
     * 新增登录注册登出记录
     * 
     * @param accountLog 登录注册登出记录信息
     * @return 结果
     */
	public int insertAccountLog(AuthAccountLog accountLog);
	
	/**
     * 修改登录注册登出记录
     * 
     * @param accountLog 登录注册登出记录信息
     * @return 结果
     */
	public int updateAccountLog(AuthAccountLog accountLog);
		
	/**
     * 删除登录注册登出记录信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAccountLogByIds(String ids);
	
}
