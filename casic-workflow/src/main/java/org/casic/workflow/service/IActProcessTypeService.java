package org.casic.workflow.service;

import org.casic.workflow.domain.ActProcessType;
import java.util.List;

/**
 * 流程部署 服务层
 * 
 * @author yuzengwen
 * @date 2019-04-03
 */
public interface IActProcessTypeService
{
	/**
     * 查询流程部署信息
     * 
     * @param id 流程部署ID
     * @return 流程部署信息
     */
	public ActProcessType selectProcessTypeById(String id);

	/**
	 * 查询流程部署信息
	 *
	 * @param processId 流程ID
	 * @return 流程部署信息
	 */
	public ActProcessType selectProcessTypeByProcessId(String processId);
	
	/**
     * 查询流程部署列表
     * 
     * @param processType 流程部署信息
     * @return 流程部署集合
     */
	public List<ActProcessType> selectProcessTypeList(ActProcessType processType);
	
	/**
     * 新增流程部署
     * 
     * @param processType 流程部署信息
     * @return 结果
     */
	public int insertProcessType(ActProcessType processType);
	
	/**
     * 修改流程部署
     * 
     * @param processType 流程部署信息
     * @return 结果
     */
	public int updateProcessType(ActProcessType processType);
		
	/**
     * 删除流程部署信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProcessTypeByIds(String ids);
	
}
