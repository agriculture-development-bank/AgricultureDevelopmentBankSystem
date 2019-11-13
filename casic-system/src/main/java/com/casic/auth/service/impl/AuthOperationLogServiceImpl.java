package com.casic.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casic.auth.mapper.AuthOperationLogMapper;
import com.casic.auth.service.IAuthOperationLogService;
import com.casic.common.support.Convert;
import com.casic.common.web.domain.bo.AuthOperationLog;

/**
 * 操作日志 服务层实现
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
@Service
public class AuthOperationLogServiceImpl implements IAuthOperationLogService 
{
	@Autowired
	private AuthOperationLogMapper operationLogMapper;

	/**
     * 查询操作日志信息
     * 
     * @param iD 操作日志ID
     * @return 操作日志信息
     */
    @Override
	public AuthOperationLog selectOperationLogById(Integer iD)
	{
	    return operationLogMapper.selectOperationLogById(iD);
	}
	
	/**
     * 查询操作日志列表
     * 
     * @param operationLog 操作日志信息
     * @return 操作日志集合
     */
	@Override
	public List<AuthOperationLog> selectOperationLogList(AuthOperationLog operationLog)
	{
	    return operationLogMapper.selectOperationLogList(operationLog);
	}
	
    /**
     * 新增操作日志
     * 
     * @param operationLog 操作日志信息
     * @return 结果
     */
	@Override
	public int insertOperationLog(AuthOperationLog operationLog)
	{
	    return operationLogMapper.insertOperationLog(operationLog);
	}
	
	/**
     * 修改操作日志
     * 
     * @param operationLog 操作日志信息
     * @return 结果
     */
	@Override
	public int updateOperationLog(AuthOperationLog operationLog)
	{
	    return operationLogMapper.updateOperationLog(operationLog);
	}

	/**
     * 删除操作日志对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteOperationLogByIds(String ids)
	{
		return operationLogMapper.deleteOperationLogByIds(Convert.toStrArray(ids));
	}
	
}
