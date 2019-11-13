package org.casic.workflow.service;

import org.casic.workflow.domain.FlowBusinessObject;

import java.util.List;

/**
 * 操作实体 服务层
 * 
 * @author yuzengwen
 * @date 2019-04-10
 */
public interface IFlowBusinessObjectService
{
	/**
     * 查询操作实体信息
     * 
     * @param id 操作实体ID
     * @return 操作实体信息
     */
	public FlowBusinessObject selectBusinessObjectById(String id);
	
	/**
     * 查询操作实体列表
     * 
     * @param businessObject 操作实体信息
     * @return 操作实体集合
     */
	public List<FlowBusinessObject> selectBusinessObjectList(FlowBusinessObject businessObject);
	
	/**
     * 新增操作实体
     * 
     * @param businessObject 操作实体信息
     * @return 结果
     */
	public int insertBusinessObject(FlowBusinessObject businessObject);
	
	/**
     * 修改操作实体
     * 
     * @param businessObject 操作实体信息
     * @return 结果
     */
	public int updateBusinessObject(FlowBusinessObject businessObject);
		
	/**
     * 删除操作实体信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteBusinessObjectByIds(String ids);
	
}
