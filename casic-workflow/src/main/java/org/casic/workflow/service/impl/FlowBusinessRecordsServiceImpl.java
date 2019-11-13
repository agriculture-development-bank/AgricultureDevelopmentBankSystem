package org.casic.workflow.service.impl;

import java.util.List;

import org.casic.workflow.domain.FlowBusinessRecords;
import org.casic.workflow.mapper.FlowBusinessRecordsMapper;
import org.casic.workflow.service.IFlowBusinessRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.casic.common.support.Convert;

/**
 * 流程业务记录 服务层实现
 * 
 * @author yuzengwen
 * @date 2019-04-10
 */
@Service
public class FlowBusinessRecordsServiceImpl implements IFlowBusinessRecordsService
{
	@Autowired
	private FlowBusinessRecordsMapper businessRecordsMapper;

	/**
     * 查询流程业务记录信息
     * 
     * @param id 流程业务记录ID
     * @return 流程业务记录信息
     */
    @Override
	public FlowBusinessRecords selectBusinessRecordsById(String id)
	{
	    return businessRecordsMapper.selectBusinessRecordsById(id);
	}

	/**
	 * 根据流程实例Id查询流程业务记录信息 按时间正序
	 */
	@Override
	public List<FlowBusinessRecords> selectBusinessRecordsListByProInstanceId(String proInstanceId){
		return businessRecordsMapper.selectBusinessRecordsListByProInstanceId(proInstanceId);
	}
	
	/**
     * 查询流程业务记录列表
     * 
     * @param businessRecords 流程业务记录信息
     * @return 流程业务记录集合
     */
	@Override
	public List<FlowBusinessRecords> selectBusinessRecordsList(FlowBusinessRecords businessRecords)
	{
	    return businessRecordsMapper.selectBusinessRecordsList(businessRecords);
	}
	
    /**
     * 新增流程业务记录
     * 
     * @param businessRecords 流程业务记录信息
     * @return 结果
     */
	@Override
	public int insertBusinessRecords(FlowBusinessRecords businessRecords)
	{
	    return businessRecordsMapper.insertBusinessRecords(businessRecords);
	}
	
	/**
     * 修改流程业务记录
     * 
     * @param businessRecords 流程业务记录信息
     * @return 结果
     */
	@Override
	public int updateBusinessRecords(FlowBusinessRecords businessRecords)
	{
	    return businessRecordsMapper.updateBusinessRecords(businessRecords);
	}

	/**
     * 删除流程业务记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteBusinessRecordsByIds(String ids)
	{
		return businessRecordsMapper.deleteBusinessRecordsByIds(Convert.toStrArray(ids));
	}
}
