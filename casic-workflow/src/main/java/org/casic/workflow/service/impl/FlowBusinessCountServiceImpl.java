package org.casic.workflow.service.impl;

import java.util.List;

import org.casic.workflow.domain.FlowBusinessCount;
import org.casic.workflow.mapper.FlowBusinessCountMapper;
import org.casic.workflow.service.IFlowBusinessCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.casic.common.support.Convert;

/**
 * 业务统计 服务层实现
 * 
 * @author yuzengwen
 * @date 2019-04-11
 */
@Service
public class FlowBusinessCountServiceImpl implements IFlowBusinessCountService
{
	@Autowired
	private FlowBusinessCountMapper businessCountMapper;

	/**
     * 查询业务统计信息
     * 
     * @param id 业务统计ID
     * @return 业务统计信息
     */
    @Override
	public FlowBusinessCount selectBusinessCountById(String id)
	{
	    return businessCountMapper.selectBusinessCountById(id);
	}
	
	/**
     * 查询业务统计列表
     * 
     * @param businessCount 业务统计信息
     * @return 业务统计集合
     */
	@Override
	public List<FlowBusinessCount> selectBusinessCountList(FlowBusinessCount businessCount)
	{
	    return businessCountMapper.selectBusinessCountList(businessCount);
	}
	
    /**
     * 新增业务统计
     * 
     * @param businessCount 业务统计信息
     * @return 结果
     */
	@Override
	public int insertBusinessCount(FlowBusinessCount businessCount)
	{
	    return businessCountMapper.insertBusinessCount(businessCount);
	}
	
	/**
     * 修改业务统计
     * 
     * @param businessCount 业务统计信息
     * @return 结果
     */
	@Override
	public int updateBusinessCount(FlowBusinessCount businessCount)
	{
	    return businessCountMapper.updateBusinessCount(businessCount);
	}

	/**
     * 删除业务统计对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteBusinessCountByIds(String ids)
	{
		return businessCountMapper.deleteBusinessCountByIds(Convert.toStrArray(ids));
	}
	
}
