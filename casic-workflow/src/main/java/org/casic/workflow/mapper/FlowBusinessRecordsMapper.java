package org.casic.workflow.mapper;

import org.casic.workflow.domain.FlowBusinessRecords;
import java.util.List;

/**
 * 流程业务记录 数据层
 * 
 * @author yuzengwen
 * @date 2019-04-10
 */
public interface FlowBusinessRecordsMapper
{
	/**
     * 查询流程业务记录信息
     * 
     * @param id 流程业务记录ID
     * @return 流程业务记录信息
     */
	public FlowBusinessRecords selectBusinessRecordsById(String id);

	/**
	 * 根据流程实例Id查询流程业务记录信息 按时间正序
	 */
	public List<FlowBusinessRecords> selectBusinessRecordsListByProInstanceId(String proInstanceId);
	
	/**
     * 查询流程业务记录列表
     * 
     * @param businessRecords 流程业务记录信息
     * @return 流程业务记录集合
     */
	public List<FlowBusinessRecords> selectBusinessRecordsList(FlowBusinessRecords businessRecords);
	
	/**
     * 新增流程业务记录
     * 
     * @param businessRecords 流程业务记录信息
     * @return 结果
     */
	public int insertBusinessRecords(FlowBusinessRecords businessRecords);
	
	/**
     * 修改流程业务记录
     * 
     * @param businessRecords 流程业务记录信息
     * @return 结果
     */
	public int updateBusinessRecords(FlowBusinessRecords businessRecords);
	
	/**
     * 删除流程业务记录
     * 
     * @param id 流程业务记录ID
     * @return 结果
     */
	public int deleteBusinessRecordsById(String id);
	
	/**
     * 批量删除流程业务记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteBusinessRecordsByIds(String[] ids);
	
}