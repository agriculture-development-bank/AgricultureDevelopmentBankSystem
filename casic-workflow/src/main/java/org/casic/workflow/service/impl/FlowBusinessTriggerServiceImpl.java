package org.casic.workflow.service.impl;

import java.util.List;

import org.casic.workflow.domain.FlowBusinessTrigger;
import org.casic.workflow.mapper.FlowBusinessTriggerMapper;
import org.casic.workflow.service.IFlowBusinessTriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.casic.common.support.Convert;

/**
 * 流程节点触发器 服务层实现
 * 
 * @author yuzengwen
 * @date 2019-04-11
 */
@Service
public class FlowBusinessTriggerServiceImpl implements IFlowBusinessTriggerService
{
	@Autowired
	private FlowBusinessTriggerMapper businessTriggerMapper;

	/**
     * 查询流程节点触发器信息
     * 
     * @param id 流程节点触发器ID
     * @return 流程节点触发器信息
     */
    @Override
	public FlowBusinessTrigger selectBusinessTriggerById(String id)
	{
	    return businessTriggerMapper.selectBusinessTriggerById(id);
	}
	
	/**
     * 查询流程节点触发器列表
     * 
     * @param businessTrigger 流程节点触发器信息
     * @return 流程节点触发器集合
     */
	@Override
	public List<FlowBusinessTrigger> selectBusinessTriggerList(FlowBusinessTrigger businessTrigger)
	{
	    return businessTriggerMapper.selectBusinessTriggerList(businessTrigger);
	}
	
    /**
     * 新增流程节点触发器
     * 
     * @param businessTrigger 流程节点触发器信息
     * @return 结果
     */
	@Override
	public int insertBusinessTrigger(FlowBusinessTrigger businessTrigger)
	{
	    return businessTriggerMapper.insertBusinessTrigger(businessTrigger);
	}
	
	/**
     * 修改流程节点触发器
     * 
     * @param businessTrigger 流程节点触发器信息
     * @return 结果
     */
	@Override
	public int updateBusinessTrigger(FlowBusinessTrigger businessTrigger)
	{
	    return businessTriggerMapper.updateBusinessTrigger(businessTrigger);
	}

	/**
     * 删除流程节点触发器对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteBusinessTriggerByIds(String ids)
	{
		return businessTriggerMapper.deleteBusinessTriggerByIds(Convert.toStrArray(ids));
	}
	
}
