package org.casic.workflow.service.impl;

import java.util.List;

import org.casic.workflow.service.IActProcessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.casic.workflow.mapper.ActProcessTypeMapper;
import org.casic.workflow.domain.ActProcessType;
import com.casic.common.support.Convert;

/**
 * 流程部署 服务层实现
 * 
 * @author yuzengwen
 * @date 2019-04-03
 */
@Service
public class ActProcessTypeServiceImpl implements IActProcessTypeService
{
	@Autowired
	private ActProcessTypeMapper actProcessTypeMapper;

	/**
     * 查询流程部署信息
     * 
     * @param id 流程部署ID
     * @return 流程部署信息
     */
    @Override
	public ActProcessType selectProcessTypeById(String id)
	{
	    return actProcessTypeMapper.selectProcessTypeById(id);
	}

	/**
	 * 查询流程部署信息
	 *
	 * @param processId 流程ID
	 * @return 流程部署信息
	 */
	@Override
	public ActProcessType selectProcessTypeByProcessId(String processId){
		return actProcessTypeMapper.selectProcessTypeByProcessId(processId);
	}
	
	/**
     * 查询流程部署列表
     * 
     * @param processType 流程部署信息
     * @return 流程部署集合
     */
	@Override
	public List<ActProcessType> selectProcessTypeList(ActProcessType processType)
	{
	    return actProcessTypeMapper.selectProcessTypeList(processType);
	}
	
    /**
     * 新增流程部署
     * 
     * @param processType 流程部署信息
     * @return 结果
     */
	@Override
	public int insertProcessType(ActProcessType processType)
	{
	    return actProcessTypeMapper.insertProcessType(processType);
	}
	
	/**
     * 修改流程部署
     * 
     * @param processType 流程部署信息
     * @return 结果
     */
	@Override
	public int updateProcessType(ActProcessType processType)
	{
	    return actProcessTypeMapper.updateProcessType(processType);
	}

	/**
     * 删除流程部署对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProcessTypeByIds(String ids)
	{
		return actProcessTypeMapper.deleteProcessTypeByIds(Convert.toStrArray(ids));
	}
	
}
