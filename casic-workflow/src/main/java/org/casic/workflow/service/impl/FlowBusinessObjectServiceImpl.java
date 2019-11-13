package org.casic.workflow.service.impl;

import java.util.List;

import org.casic.workflow.domain.FlowBusinessObject;
import org.casic.workflow.mapper.FlowBusinessObjectMapper;
import org.casic.workflow.service.IFlowBusinessObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.casic.common.support.Convert;

/**
 * 操作实体 服务层实现
 * 
 * @author yuzengwen
 * @date 2019-04-10
 */
@Service
public class FlowBusinessObjectServiceImpl implements IFlowBusinessObjectService
{
	@Autowired
	private FlowBusinessObjectMapper businessObjectMapper;

	/**
     * 查询操作实体信息
     * 
     * @param id 操作实体ID
     * @return 操作实体信息
     */
    @Override
	public FlowBusinessObject selectBusinessObjectById(String id)
	{
	    return businessObjectMapper.selectBusinessObjectById(id);
	}
	
	/**
     * 查询操作实体列表
     * 
     * @param businessObject 操作实体信息
     * @return 操作实体集合
     */
	@Override
	public List<FlowBusinessObject> selectBusinessObjectList(FlowBusinessObject businessObject)
	{
	    return businessObjectMapper.selectBusinessObjectList(businessObject);
	}
	
    /**
     * 新增操作实体
     * 
     * @param businessObject 操作实体信息
     * @return 结果
     */
	@Override
	public int insertBusinessObject(FlowBusinessObject businessObject)
	{
	    return businessObjectMapper.insertBusinessObject(businessObject);
	}
	
	/**
     * 修改操作实体
     * 
     * @param businessObject 操作实体信息
     * @return 结果
     */
	@Override
	public int updateBusinessObject(FlowBusinessObject businessObject)
	{
	    return businessObjectMapper.updateBusinessObject(businessObject);
	}

	/**
     * 删除操作实体对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteBusinessObjectByIds(String ids)
	{
		return businessObjectMapper.deleteBusinessObjectByIds(Convert.toStrArray(ids));
	}
	
}
