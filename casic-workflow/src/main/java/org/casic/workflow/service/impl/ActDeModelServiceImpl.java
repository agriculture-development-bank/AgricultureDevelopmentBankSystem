package org.casic.workflow.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.casic.common.utils.spring.SpringUtils;
import org.casic.workflow.common.CasicFlowableEngine;
import org.casic.workflow.service.IActDeModelService;
import org.casic.workflow.service.IProcessOperateService;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.casic.workflow.mapper.ActDeModelMapper;
import org.casic.workflow.domain.ActDeModel;
import com.casic.common.support.Convert;
import org.flowable.form.api.FormDefinition;
import org.flowable.form.api.FormDeployment;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.form.engine.FormEngine;

/**
 * 流程模型 服务层实现
 * 
 * @author yuzengwen
 * @date 2019-04-03
 */
@Service
public class ActDeModelServiceImpl implements IActDeModelService
{
	private ProcessEngine casicProcessEngine = CasicFlowableEngine.casicProcessEngine;

	private FormEngine casicFormEngine = CasicFlowableEngine.casicFormEngine;

	@Autowired
	private ActDeModelMapper actDeModelMapper;
	@Autowired
	private ModelService modelService;
	@Autowired
	private IProcessOperateService processOperateService;


	/**
     * 查询流程模型信息
     * 
     * @param id 流程模型ID
     * @return 流程模型信息
     */
    @Override
	public ActDeModel selectDeModelById(String id)
	{
	    return actDeModelMapper.selectDeModelById(id);
	}
	
	/**
     * 查询流程模型列表
     * 
     * @param deModel 流程模型信息
     * @return 流程模型集合
     */
	@Override
	public List<ActDeModel> selectDeModelList(ActDeModel deModel)
	{
	    return actDeModelMapper.selectDeModelList(deModel);
	}
	
    /**
     * 新增流程模型
     * 
     * @param deModel 流程模型信息
     * @return 结果
     */
	@Override
	public int insertDeModel(ActDeModel deModel)
	{
	    return actDeModelMapper.insertDeModel(deModel);
	}
	
	/**
     * 修改流程模型
     * 
     * @param deModel 流程模型信息
     * @return 结果
     */
	@Override
	public int updateDeModel(ActDeModel deModel)
	{
	    return actDeModelMapper.updateDeModel(deModel);
	}

	/**
     * 删除流程模型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteDeModelByIds(String ids)
	{
		return actDeModelMapper.deleteDeModelByIds(Convert.toStrArray(ids));
	}

	/**
	 * 部署流程
	 *
	 * @param processModelId
	 */
	@Override
	public Map deployProcessByModelId(String processModelId){
		Model model = modelService.getModel(processModelId);
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("status","error");
		if (model.getModelEditorJson() != null) {
			BpmnModel bpmnModel = modelService.getBpmnModel(model);
			String resourceName = model.getName().replaceAll(" ", "_") + ".bpmn";
			RepositoryService repositoryService = casicProcessEngine.getRepositoryService();
			Deployment deployment = repositoryService.createDeployment().addBpmnModel(resourceName,bpmnModel).deploy();
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
					.deploymentId(deployment.getId())
					.singleResult();

			if(processDefinition == null){
				resultMap.put("status","error");
			}else{
				resultMap.put("status","success");
				resultMap.put("processDefineId",processDefinition.getId());
				List<String> forms = processOperateService.getProcessContainForms(bpmnModel);
				resultMap.put("containForms",forms);
			}
		}

		return resultMap;
	}

	/**
	 * 部署表单
	 * @param formModelKey
	 */
	@Override
	public boolean deployFormByModelKey(String formModelKey){
		ActDeModel actDeModel = actDeModelMapper.selectFormModelByKey(formModelKey);
		boolean deployFlag = false;
		if(actDeModel != null){
			String formJson = actDeModel.getModelEditorJson();
			String resourceName = actDeModel.getName().replaceAll(" ", "_") + ".form";
			FormRepositoryService formRepositoryService = casicFormEngine.getFormRepositoryService();
			FormDeployment formDeployment = formRepositoryService.createDeployment().addString(resourceName,formJson).deploy();

			FormDefinition formDefinition = formRepositoryService.createFormDefinitionQuery().deploymentId(formDeployment.getId()).singleResult();

			if(formDefinition != null){
				deployFlag = true;
			}
		}
		return deployFlag;
	}
}
