package com.casic.auth.mapper;

import java.util.List;

import com.casic.common.web.domain.bo.AuthAccountLog;	

/**
 * 登录注册登出记录 数据层
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
public interface AuthAccountLogMapper 
{
	/**
     * 查询登录注册登出记录信息
     * 
     * @param iD 登录注册登出记录ID
     * @return 登录注册登出记录信息
     */
	public AuthAccountLog selectAccountLogById(Integer iD);
	
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
     * 删除登录注册登出记录
     * 
     * @param iD 登录注册登出记录ID
     * @return 结果
     */
	public int deleteAccountLogById(Integer iD);
	
	/**
     * 批量删除登录注册登出记录
     * 
     * @param iDs 需要删除的数据ID
     * @return 结果
     */
	public int deleteAccountLogByIds(String[] iDs);
	
}