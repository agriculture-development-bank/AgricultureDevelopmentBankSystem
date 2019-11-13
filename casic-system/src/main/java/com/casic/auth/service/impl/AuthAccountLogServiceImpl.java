package com.casic.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casic.auth.mapper.AuthAccountLogMapper;
import com.casic.auth.service.IAuthAccountLogService;
import com.casic.common.support.Convert;
import com.casic.common.web.domain.bo.AuthAccountLog;

/**
 * 登录注册登出记录 服务层实现
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
@Service
public class AuthAccountLogServiceImpl implements IAuthAccountLogService 
{
	@Autowired
	private AuthAccountLogMapper accountLogMapper;

	/**
     * 查询登录注册登出记录信息
     * 
     * @param iD 登录注册登出记录ID
     * @return 登录注册登出记录信息
     */
    @Override
	public AuthAccountLog selectAccountLogById(Integer id)
	{
	    return accountLogMapper.selectAccountLogById(id);
	}
	
	/**
     * 查询登录注册登出记录列表
     * 
     * @param accountLog 登录注册登出记录信息
     * @return 登录注册登出记录集合
     */
	@Override
	public List<AuthAccountLog> selectAccountLogList(AuthAccountLog accountLog)
	{
	    return accountLogMapper.selectAccountLogList(accountLog);
	}
	
    /**
     * 新增登录注册登出记录
     * 
     * @param accountLog 登录注册登出记录信息
     * @return 结果
     */
	@Override
	public int insertAccountLog(AuthAccountLog accountLog)
	{
	    return accountLogMapper.insertAccountLog(accountLog);
	}
	
	/**
     * 修改登录注册登出记录
     * 
     * @param accountLog 登录注册登出记录信息
     * @return 结果
     */
	@Override
	public int updateAccountLog(AuthAccountLog accountLog)
	{
	    return accountLogMapper.updateAccountLog(accountLog);
	}

	/**
     * 删除登录注册登出记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteAccountLogByIds(String ids)
	{
		return accountLogMapper.deleteAccountLogByIds(Convert.toStrArray(ids));
	}
	
}
