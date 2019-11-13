package org.casic.workflow.service;

import org.casic.workflow.domain.ActDeModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 流程模型 服务层
 * 
 * @author yuzengwen
 * @date 2019-04-03
 */
public interface IActDeModelService
{
	/**
     * 查询流程模型信息
     * 
     * @param id 流程模型ID
     * @return 流程模型信息
     */
	public ActDeModel selectDeModelById(String id);
	
	/**
     * 查询流程模型列表
     * 
     * @param deModel 流程模型信息
     * @return 流程模型集合
     */
	public List<ActDeModel> selectDeModelList(ActDeModel deModel);
	
	/**
     * 新增流程模型
     * 
     * @param deModel 流程模型信息
     * @return 结果
     */
	public int insertDeModel(ActDeModel deModel);
	
	/**
     * 修改流程模型
     * 
     * @param deModel 流程模型信息
     * @return 结果
     */
	public int updateDeModel(ActDeModel deModel);
		
	/**
     * 删除流程模型信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteDeModelByIds(String ids);

	/**
	 * 部署流程
	 *
	 * @param processModelId
	 */
	public Map deployProcessByModelId(String processModelId);

	/**
	 * 部署表单
	 * @param formModelKey
	 */
	public boolean deployFormByModelKey(String formModelKey);
	
}
