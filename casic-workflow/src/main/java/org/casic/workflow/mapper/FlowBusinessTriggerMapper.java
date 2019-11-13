package org.casic.workflow.mapper;

import org.casic.workflow.domain.FlowBusinessTrigger;

import java.util.List;

/**
 * 流程节点触发器 数据层
 * 
 * @author yuzengwen
 * @date 2019-04-11
 */
public interface FlowBusinessTriggerMapper
{
	/**
     * 查询流程节点触发器信息
     * 
     * @param id 流程节点触发器ID
     * @return 流程节点触发器信息
     */
	public FlowBusinessTrigger selectBusinessTriggerById(String id);
	
	/**
     * 查询流程节点触发器列表
     * 
     * @param businessTrigger 流程节点触发器信息
     * @return 流程节点触发器集合
     */
	public List<FlowBusinessTrigger> selectBusinessTriggerList(FlowBusinessTrigger businessTrigger);
	
	/**
     * 新增流程节点触发器
     * 
     * @param businessTrigger 流程节点触发器信息
     * @return 结果
     */
	public int insertBusinessTrigger(FlowBusinessTrigger businessTrigger);
	
	/**
     * 修改流程节点触发器
     * 
     * @param businessTrigger 流程节点触发器信息
     * @return 结果
     */
	public int updateBusinessTrigger(FlowBusinessTrigger businessTrigger);
	
	/**
     * 删除流程节点触发器
     * 
     * @param id 流程节点触发器ID
     * @return 结果
     */
	public int deleteBusinessTriggerById(String id);
	
	/**
     * 批量删除流程节点触发器
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteBusinessTriggerByIds(String[] ids);
	
}