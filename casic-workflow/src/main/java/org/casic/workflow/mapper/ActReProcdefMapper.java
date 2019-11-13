package org.casic.workflow.mapper;

import org.casic.workflow.domain.ActReProcdef;
import java.util.List;	

/**
 * 流程部署 数据层
 * 
 * @author yuzengwen
 * @date 2019-04-08
 */
public interface ActReProcdefMapper
{
	/**
     * 查询流程部署信息
     * 
     * @param id 流程部署ID
     * @return 流程部署信息
     */
	public ActReProcdef selectReProcdefById(String id);
	
	/**
     * 查询流程部署列表
     * 
     * @param reProcdef 流程部署信息
     * @return 流程部署集合
     */
	public List<ActReProcdef> selectReProcdefList(ActReProcdef reProcdef);
	
	/**
     * 新增流程部署
     * 
     * @param reProcdef 流程部署信息
     * @return 结果
     */
	public int insertReProcdef(ActReProcdef reProcdef);
	
	/**
     * 修改流程部署
     * 
     * @param reProcdef 流程部署信息
     * @return 结果
     */
	public int updateReProcdef(ActReProcdef reProcdef);
	
	/**
     * 删除流程部署
     * 
     * @param id 流程部署ID
     * @return 结果
     */
	public int deleteReProcdefById(String id);
	
	/**
     * 批量删除流程部署
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteReProcdefByIds(String[] ids);
	
}