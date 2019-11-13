package org.casic.workflow.mapper;

import org.casic.workflow.domain.FlowBusinessCount;

import java.util.List;

/**
 * 业务统计 数据层
 * 
 * @author yuzengwen
 * @date 2019-04-11
 */
public interface FlowBusinessCountMapper
{
	/**
     * 查询业务统计信息
     * 
     * @param id 业务统计ID
     * @return 业务统计信息
     */
	public FlowBusinessCount selectBusinessCountById(String id);
	
	/**
     * 查询业务统计列表
     * 
     * @param businessCount 业务统计信息
     * @return 业务统计集合
     */
	public List<FlowBusinessCount> selectBusinessCountList(FlowBusinessCount businessCount);
	
	/**
     * 新增业务统计
     * 
     * @param businessCount 业务统计信息
     * @return 结果
     */
	public int insertBusinessCount(FlowBusinessCount businessCount);
	
	/**
     * 修改业务统计
     * 
     * @param businessCount 业务统计信息
     * @return 结果
     */
	public int updateBusinessCount(FlowBusinessCount businessCount);
	
	/**
     * 删除业务统计
     * 
     * @param id 业务统计ID
     * @return 结果
     */
	public int deleteBusinessCountById(String id);
	
	/**
     * 批量删除业务统计
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteBusinessCountByIds(String[] ids);
	
}