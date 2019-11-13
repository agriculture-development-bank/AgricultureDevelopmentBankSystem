package com.casic.auth.service;

import java.util.List;

import com.casic.common.web.domain.bo.AuthOperationLog;

/**
 * 操作日志 服务层
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
public interface IAuthOperationLogService 
{
	/**
     * 查询操作日志信息
     * 
     * @param iD 操作日志ID
     * @return 操作日志信息
     */
	public AuthOperationLog selectOperationLogById(Integer iD);
	
	/**
     * 查询操作日志列表
     * 
     * @param operationLog 操作日志信息
     * @return 操作日志集合
     */
	public List<AuthOperationLog> selectOperationLogList(AuthOperationLog operationLog);
	
	/**
     * 新增操作日志
     * 
     * @param operationLog 操作日志信息
     * @return 结果
     */
	public int insertOperationLog(AuthOperationLog operationLog);
	
	/**
     * 修改操作日志
     * 
     * @param operationLog 操作日志信息
     * @return 结果
     */
	public int updateOperationLog(AuthOperationLog operationLog);
		
	/**
     * 删除操作日志信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteOperationLogByIds(String ids);
	
}
