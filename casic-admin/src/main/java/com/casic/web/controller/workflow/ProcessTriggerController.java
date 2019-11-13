package com.casic.web.controller.workflow;

import com.alibaba.fastjson.JSONObject;
import com.casic.common.utils.StringUtils;
import com.casic.common.utils.UuidUtils;
import org.casic.workflow.domain.FlowBusinessCount;
import org.casic.workflow.domain.FlowBusinessData;
import org.casic.workflow.domain.FlowBusinessObject;
import org.casic.workflow.domain.FlowBusinessRecords;
import org.casic.workflow.service.IFlowBusinessCountService;
import org.casic.workflow.service.IFlowBusinessDataService;
import org.casic.workflow.service.IFlowBusinessObjectService;
import org.casic.workflow.service.IFlowBusinessRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app")
public class ProcessTriggerController {
    @Autowired
    private IFlowBusinessCountService businessCountService;
    @Autowired
    private IFlowBusinessRecordsService businessRecordsService;
    @Autowired
    private IFlowBusinessObjectService businessObjectService;
    @Autowired
    private IFlowBusinessDataService businessDataService;

    @RequestMapping("/workflow/businessObjectListener")
    public String processListener(String processInstanceId, String processDefinitionId, String taskId, String entityId,String userId){
        String result = "success";

        //流程完成，往flow_business_count中保存记录
        FlowBusinessCount flowBusinessCount = new FlowBusinessCount();
        flowBusinessCount.setId(UuidUtils.getUUIDString());
        flowBusinessCount.setInstanceId(processInstanceId);
        flowBusinessCount.setProcessId(processDefinitionId);
        flowBusinessCount.setTaskId(taskId);
        flowBusinessCount.setUserId(userId);
        flowBusinessCount.setOptionResult("SUCCESS");
        flowBusinessCount.setOptionDate(new Date());
        flowBusinessCount.setTableName("flow_business_object");
        flowBusinessCount.setObjId(entityId);
        businessCountService.insertBusinessCount(flowBusinessCount);

        //把表单中填写的数据回填到业务实体中
        FlowBusinessRecords recordsQuery = new FlowBusinessRecords();
        recordsQuery.setObjId(entityId);
        recordsQuery.setProcessId(processDefinitionId);
        recordsQuery.setInstanceId(processInstanceId);
        List<FlowBusinessRecords> recordsList = businessRecordsService.selectBusinessRecordsList(recordsQuery);
        List<Map<String,Object>> dataList = new ArrayList<Map<String, Object>>();
        for(FlowBusinessRecords records : recordsList){
            String formJson = records.getFormJson();
            if(StringUtils.isNotEmpty(formJson)){
                List<Map<String,Object>> form = JSONObject.parseObject(formJson,List.class);
                dataList.addAll(form);
            }
        }

        FlowBusinessObject businessObject = businessObjectService.selectBusinessObjectById(entityId);
        for(Map data : dataList){
            if("name".equals(data.get("id"))){
                businessObject.setName(data.get("value").toString());

                FlowBusinessData flowBusinessData = new FlowBusinessData();
                flowBusinessData.setId(UuidUtils.getUUIDString());
                flowBusinessData.setProcessId(processDefinitionId);
                flowBusinessData.setInstanceId(processInstanceId);
                flowBusinessData.setDataKey(data.get("id").toString());
                flowBusinessData.setDataValue(data.get("value").toString());
                flowBusinessData.setDataName(data.get("name").toString());
                flowBusinessData.setOptionDate(new Date());
                flowBusinessData.setTableName("flow_business_object");
                flowBusinessData.setObjId(entityId);
                businessDataService.insertBusinessData(flowBusinessData);
            }
            if("code".equals(data.get("id"))){
                businessObject.setCode(data.get("value").toString());

                FlowBusinessData flowBusinessData = new FlowBusinessData();
                flowBusinessData.setId(UuidUtils.getUUIDString());
                flowBusinessData.setProcessId(processDefinitionId);
                flowBusinessData.setInstanceId(processInstanceId);
                flowBusinessData.setDataKey(data.get("id").toString());
                flowBusinessData.setDataValue(data.get("value").toString());
                flowBusinessData.setDataName(data.get("name").toString());
                flowBusinessData.setOptionDate(new Date());
                flowBusinessData.setTableName("flow_business_object");
                flowBusinessData.setObjId(entityId);
                businessDataService.insertBusinessData(flowBusinessData);
            }
            if("sn".equals(data.get("id"))){
                businessObject.setSn(data.get("value").toString());

                FlowBusinessData flowBusinessData = new FlowBusinessData();
                flowBusinessData.setId(UuidUtils.getUUIDString());
                flowBusinessData.setProcessId(processDefinitionId);
                flowBusinessData.setInstanceId(processInstanceId);
                flowBusinessData.setDataKey(data.get("id").toString());
                flowBusinessData.setDataValue(data.get("value").toString());
                flowBusinessData.setDataName(data.get("name").toString());
                flowBusinessData.setOptionDate(new Date());
                flowBusinessData.setTableName("flow_business_object");
                flowBusinessData.setObjId(entityId);
                businessDataService.insertBusinessData(flowBusinessData);
            }
        }

        businessObjectService.updateBusinessObject(businessObject);

        return result;
    }
}
