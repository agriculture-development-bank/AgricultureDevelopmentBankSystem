package com.casic.web.controller.workflow;

import com.alibaba.fastjson.JSON;
import com.casic.common.annotation.Log;
import com.casic.common.base.AjaxResult;
import com.casic.common.enums.BusinessType;
import com.casic.common.utils.DateUtils;
import com.casic.common.utils.StringUtils;
import com.casic.common.utils.UuidUtils;
import com.casic.common.web.page.TableDataInfo;
import com.casic.framework.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.casic.workflow.domain.FlowBusinessObject;
import org.casic.workflow.service.IFlowBusinessObjectService;
import org.casic.workflow.service.IProcessOperateService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 操作实体 信息操作处理
 *
 * @author yuzengwen
 * @date 2019-04-10
 */
@Controller
@RequestMapping("/workflow/flowBusinessObject")
public class FlowBusinessObjectController extends BaseController
{
	private String prefix = "workflow/flowBusinessObject";

	@Autowired
	private IFlowBusinessObjectService businessObjectService;
	@Autowired
	private IProcessOperateService processOperateService;

	@RequiresPermissions("workflow:flowBusinessObject:view")
	@GetMapping("")
	public String businessObject()
	{
		String p = prefix + "/businessObject";
		return p;
	}

	/**
	 * 查询操作实体列表
	 */
	@RequiresPermissions("workflow:flowBusinessObject:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(FlowBusinessObject businessObject)
	{
		startPage();
		List<FlowBusinessObject> list = businessObjectService.selectBusinessObjectList(businessObject);
		return getDataTable(list);
	}

	/**
	 * 新增操作实体
	 */
	@GetMapping("/add")
	public String add()
	{
		return prefix + "/add";
	}

	/**
	 * 新增保存操作实体
	 */
	@RequiresPermissions("workflow:flowBusinessObject:add")
	@Log(title = "操作实体", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(FlowBusinessObject businessObject,String inputDateStr,String outputDateStr)
	{
		String userId = this.getUserId()+"";
		String businessKey = UuidUtils.getUUIDString();
		businessObject.setId(businessKey);
		businessObject.setCreateBy(userId);
		businessObject.setCreateTime(new Date());
		if(StringUtils.isNotEmpty(inputDateStr)){
			Date inputDate = DateUtils.dateTime(DateUtils.YYYY_MM_DD,inputDateStr);
			businessObject.setInputDate(inputDate);
		}
		if(StringUtils.isNotEmpty(outputDateStr)){
			Date outputDate = DateUtils.dateTime(DateUtils.YYYY_MM_DD,outputDateStr);
			businessObject.setOutputDate(outputDate);
		}

		//启动流程
		ProcessInstance processInstance =
				processOperateService.startProcessInstanceByProDefKey("pro_device_buy",businessKey,userId);
		int rows = 0;
		if(processInstance != null){
			//把ProcessInstanceId和ProcessDefinitionId存入业务表，后续查询流程使用
			businessObject.setProcessInstanceId(processInstance.getId());
			businessObject.setProcessDefinitionId(processInstance.getProcessDefinitionId());
			rows = businessObjectService.insertBusinessObject(businessObject);
		}
		return toAjax(rows);
	}

	/**
	 * 实体详情
	 */
	@RequiresPermissions("workflow:flowBusinessObject:info")
	@GetMapping("/info/{id}")
	public String info(@PathVariable("id") String id, ModelMap mmap){
		FlowBusinessObject businessObject = businessObjectService.selectBusinessObjectById(id);
		mmap.put("businessObject", businessObject);
		String inputDateStr = "";
		if(businessObject.getInputDate() != null){
			inputDateStr = DateUtils.dateTime(businessObject.getInputDate());
		}
		String outputDateStr = "";
		if(businessObject.getOutputDate() != null){
			outputDateStr = DateUtils.dateTime(businessObject.getOutputDate());
		}
		mmap.put("inputDateStr", inputDateStr);
		mmap.put("outputDateStr",outputDateStr);

		String processInstanceId = businessObject.getProcessInstanceId();
		String processDefinitionId = businessObject.getProcessDefinitionId();
		String activeTaskId = this.processOperateService.findActiveTaskIdByProcessInstanceId(processInstanceId);
		List<Map> routeList = new ArrayList<Map>();
		Boolean rightFlag = this.processOperateService.getUserRight(this.getUserId()+"",processInstanceId);

		if(rightFlag){
			routeList = this.processOperateService.findOutComeListByProcessInstanceId(processInstanceId);
		}
		mmap.put("processInstanceId",processInstanceId);
		mmap.put("processDefinitionId",processDefinitionId);
		String routeListJson = JSON.toJSONString(routeList);
		mmap.put("routeList",routeListJson);
		mmap.put("activeTaskId",activeTaskId);

		return prefix + "/info";
	}

	/**
	 * 修改操作实体
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, ModelMap mmap)
	{
		FlowBusinessObject businessObject = businessObjectService.selectBusinessObjectById(id);
		mmap.put("businessObject", businessObject);
		String inputDateStr = "";
		if(businessObject.getInputDate() != null){
			inputDateStr = DateUtils.dateTime(businessObject.getInputDate());
		}
		String outputDateStr = "";
		if(businessObject.getOutputDate() != null){
			outputDateStr = DateUtils.dateTime(businessObject.getOutputDate());
		}
		mmap.put("inputDateStr", inputDateStr);
		mmap.put("outputDateStr",outputDateStr);

		return prefix + "/edit";
	}

	/**
	 * 修改保存操作实体
	 */
	@RequiresPermissions("workflow:flowBusinessObject:edit")
	@Log(title = "操作实体", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(FlowBusinessObject businessObject,String inputDateStr,String outputDateStr)
	{
		String userId = this.getUserId()+"";
		businessObject.setUpdateBy(userId);
		businessObject.setUpdateTime(new Date());
		if(StringUtils.isNotEmpty(inputDateStr)){
			Date inputDate = DateUtils.dateTime(DateUtils.YYYY_MM_DD,inputDateStr);
			businessObject.setInputDate(inputDate);
		}
		if(StringUtils.isNotEmpty(outputDateStr)){
			Date outputDate = DateUtils.dateTime(DateUtils.YYYY_MM_DD,outputDateStr);
			businessObject.setOutputDate(outputDate);
		}
		return toAjax(businessObjectService.updateBusinessObject(businessObject));
	}

	/**
	 * 删除操作实体
	 */
	@RequiresPermissions("workflow:flowBusinessObject:remove")
	@Log(title = "操作实体", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		String[] idArray = ids.split(",");
		for(int i=0; i < idArray.length; i++){
			FlowBusinessObject businessObject =  businessObjectService.selectBusinessObjectById(idArray[i]);
			String processInstanceId = businessObject.getProcessInstanceId();
			processOperateService.deleteProcessInstance(processInstanceId);
		}

		return toAjax(businessObjectService.deleteBusinessObjectByIds(ids));
	}
}
