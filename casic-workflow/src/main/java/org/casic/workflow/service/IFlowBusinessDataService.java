package org.casic.workflow.service;

import org.casic.workflow.domain.FlowBusinessData;

import java.util.List;

/**
 * 业务数据，一个流程就一份数据 服务层
 * 
 * @author yuzengwen
 * @date 2019-04-11
 */
public interface IFlowBusinessDataService
{
	/**
     * 查询业务数据，一个流程就一份数据信息
     * 
     * @param id 业务数据，一个流程就一份数据ID
     * @return 业务数据，一个流程就一份数据信息
     */
	public FlowBusinessData selectBusinessDataById(String id);
	
	/**
     * 查询业务数据，一个流程就一份数据列表
     * 
     * @param businessData 业务数据，一个流程就一份数据信息
     * @return 业务数据，一个流程就一份数据集合
     */
	public List<FlowBusinessData> selectBusinessDataList(FlowBusinessData businessData);
	
	/**
     * 新增业务数据，一个流程就一份数据
     * 
     * @param businessData 业务数据，一个流程就一份数据信息
     * @return 结果
     */
	public int insertBusinessData(FlowBusinessData businessData);
	
	/**
     * 修改业务数据，一个流程就一份数据
     * 
     * @param businessData 业务数据，一个流程就一份数据信息
     * @return 结果
     */
	public int updateBusinessData(FlowBusinessData businessData);
		
	/**
     * 删除业务数据，一个流程就一份数据信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteBusinessDataByIds(String ids);
	
}
