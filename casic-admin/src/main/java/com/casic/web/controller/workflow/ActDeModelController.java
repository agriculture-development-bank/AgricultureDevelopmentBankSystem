package com.casic.web.controller.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.casic.common.utils.StringUtils;
import com.casic.common.utils.UuidUtils;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.web.base.BaseController;
import org.casic.workflow.domain.ActProcessType;
import org.casic.workflow.service.IActProcessTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.casic.workflow.service.IProcessOperateService;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.casic.common.annotation.Log;
import com.casic.common.enums.BusinessType;
import org.casic.workflow.domain.ActDeModel;
import org.casic.workflow.service.IActDeModelService;
import com.casic.common.base.AjaxResult;

import javax.servlet.http.HttpServletRequest;

/**
 * 流程模型 信息操作处理
 * 
 * @author yuzengwen
 * @date 2019-04-03
 */
@Controller
@RequestMapping("/workflow/actDeModel")
public class ActDeModelController extends BaseController
{
    private String prefix = "workflow/actDeModel";
	private static final Logger log = LoggerFactory.getLogger(ActDeModelController.class);
	
	@Autowired
	private IActDeModelService deModelService;
	@Autowired
	private IActProcessTypeService processTypeService;
	@Autowired
	private ModelService modelService;
	@Autowired
	private IProcessOperateService processOperateService;
	
	@RequiresPermissions("workflow:actDeModel:view")
	@GetMapping()
	public String deModel()
	{
	    return prefix + "/actDeModel";
	}
	
	/**
	 * 查询流程模型列表
	 */
	@RequiresPermissions("workflow:actDeModel:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ActDeModel deModel)
	{
		startPage();
        List<ActDeModel> list = deModelService.selectDeModelList(deModel);
		return getDataTable(list);
	}
	
	/**
	 * 新增流程模型
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存流程模型
	 */
	@RequiresPermissions("workflow:actDeModel:add")
	@Log(title = "流程模型", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ActDeModel deModel)
	{		
		return toAjax(deModelService.insertDeModel(deModel));
	}

	/**
	 * 修改流程模型
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, ModelMap mmap)
	{
		ActDeModel deModel = deModelService.selectDeModelById(id);
		mmap.put("deModel", deModel);
	    return prefix + "/edit";
	}

	/**
	 * 流程与系统模块关联 模型
	 */
	@GetMapping("/deploy/{id}")
	public String deploy(@PathVariable("id") String id, ModelMap mmap){
		ActDeModel deModel = deModelService.selectDeModelById(id);
		//0代表流程，2代表表单
		int modelType = deModel.getModelType();
		ActProcessType processType = processTypeService.selectProcessTypeByProcessId(id);
		if(processType == null){
			processType = new ActProcessType();
			processType.setProcessId(id);
		}

		//获取流程下表单
		List<String> containForms = new ArrayList<String>();
		Model model = modelService.getModel(id);
		BpmnModel bpmnModel = modelService.getBpmnModel(model);
		containForms = processOperateService.getProcessContainForms(bpmnModel);

		mmap.put("processType",processType);
		mmap.put("containForms",containForms);

		return prefix + "/actProcessType";
	}

	/**
	 * 流程部署
	 */
	@RequiresPermissions("workflow:actDeModel:deploy")
	@Log(title = "流程部署", businessType = BusinessType.OTHER)
	@PostMapping("/deploy")
	@ResponseBody
	public AjaxResult deploySave(HttpServletRequest request, ActProcessType processType){
		try{
			ActDeModel deModel = deModelService.selectDeModelById(processType.getProcessId());
			//0代表流程，2代表表单
			int modelType = deModel.getModelType();
			String modelName = deModel.getName();
			byte[] thumbnail = deModel.getThumbnail();
			boolean deployFlag = false;
			if(modelType == 0){
				Map deployResult = deModelService.deployProcessByModelId(processType.getProcessId());
				if("success".equals(deployResult.get("status"))){
					String processDefineId = deployResult.get("processDefineId").toString();
					List<String> formList = (List)deployResult.get("containForms");
					String containFormsStr = "";
					if(formList.size()>0){
						boolean formDeployFlag = true;
						for(String formKey : formList){
							boolean ret = deModelService.deployFormByModelKey(formKey);
							if(!ret){
								formDeployFlag = false;
							}
							containFormsStr += formKey + ",";
						}
						if(formDeployFlag){
							deployFlag = true;
						}
					}else {
						deployFlag = true;
					}
					if(deployFlag){
						processType.setReProcdefId(processDefineId);
						processType.setContainForms(containFormsStr);
						if(StringUtils.isEmpty(processType.getId())){
							processType.setId(UuidUtils.getUUIDString());
							processTypeService.insertProcessType(processType);
						}else{
							processTypeService.updateProcessType(processType);
						}
					}
				}
			}
		}catch (Exception e){
			log.error("部署失败",e);
			return AjaxResult.error();
		}

		return AjaxResult.success();
	}
	
	/**
	 * 修改保存流程模型
	 */
	@RequiresPermissions("workflow:actDeModel:edit")
	@Log(title = "流程模型", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ActDeModel deModel)
	{		
		return toAjax(deModelService.updateDeModel(deModel));
	}
	
	/**
	 * 删除流程模型
	 */
	@RequiresPermissions("workflow:actDeModel:remove")
	@Log(title = "流程模型", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(deModelService.deleteDeModelByIds(ids));
	}
	
}
