package org.casic.workflow.service.impl;

import java.util.List;

import org.casic.workflow.domain.FlowBusinessData;
import org.casic.workflow.mapper.FlowBusinessDataMapper;
import org.casic.workflow.service.IFlowBusinessDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.casic.common.support.Convert;

/**
 * 业务数据，一个流程就一份数据 服务层实现
 * 
 * @author yuzengwen
 * @date 2019-04-11
 */
@Service
public class FlowBusinessDataServiceImpl implements IFlowBusinessDataService
{
	@Autowired
	private FlowBusinessDataMapper businessDataMapper;

	/**
     * 查询业务数据，一个流程就一份数据信息
     * 
     * @param id 业务数据，一个流程就一份数据ID
     * @return 业务数据，一个流程就一份数据信息
     */
    @Override
	public FlowBusinessData selectBusinessDataById(String id)
	{
	    return businessDataMapper.selectBusinessDataById(id);
	}
	
	/**
     * 查询业务数据，一个流程就一份数据列表
     * 
     * @param businessData 业务数据，一个流程就一份数据信息
     * @return 业务数据，一个流程就一份数据集合
     */
	@Override
	public List<FlowBusinessData> selectBusinessDataList(FlowBusinessData businessData)
	{
	    return businessDataMapper.selectBusinessDataList(businessData);
	}
	
    /**
     * 新增业务数据，一个流程就一份数据
     * 
     * @param businessData 业务数据，一个流程就一份数据信息
     * @return 结果
     */
	@Override
	public int insertBusinessData(FlowBusinessData businessData)
	{
	    return businessDataMapper.insertBusinessData(businessData);
	}
	
	/**
     * 修改业务数据，一个流程就一份数据
     * 
     * @param businessData 业务数据，一个流程就一份数据信息
     * @return 结果
     */
	@Override
	public int updateBusinessData(FlowBusinessData businessData)
	{
	    return businessDataMapper.updateBusinessData(businessData);
	}

	/**
     * 删除业务数据，一个流程就一份数据对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteBusinessDataByIds(String ids)
	{
		return businessDataMapper.deleteBusinessDataByIds(Convert.toStrArray(ids));
	}
	
}
