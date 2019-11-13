package org.casic.workflow.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.casic.common.utils.ApacheHttpUtils;
import com.casic.common.utils.StringUtils;
import com.casic.common.utils.UuidUtils;
import com.casic.system.domain.SysDictData;
import com.casic.system.domain.SysUser;
import com.casic.system.service.ISysDictDataService;
import com.casic.system.service.ISysUserService;
import org.casic.workflow.common.CasicFlowableEngine;
import org.casic.workflow.domain.FlowBusinessRecords;
import org.casic.workflow.domain.FlowBusinessTrigger;
import org.casic.workflow.service.IFlowBusinessRecordsService;
import org.casic.workflow.service.IFlowBusinessTriggerService;
import org.casic.workflow.service.IProcessOperateService;
import org.flowable.bpmn.model.*;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.form.api.FormInfo;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.form.engine.FormEngine;
import org.flowable.form.model.FormField;
import org.flowable.form.model.Option;
import org.flowable.form.model.OptionFormField;
import org.flowable.form.model.SimpleFormModel;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

/**
 * Created by Administrator on 2019/3/21.
 */
@Service
public class ProcessOperateServiceImpl implements IProcessOperateService {

    private ProcessEngine casicProcessEngine = CasicFlowableEngine.casicProcessEngine;

    private FormEngine casicFormEngine = CasicFlowableEngine.casicFormEngine;

    //private String execute_url_prefix="http://localhost";

    @Autowired
    private ISysDictDataService dictDataService;
    @Autowired
    private IFlowBusinessRecordsService recordsService;
    @Autowired
    private IFlowBusinessTriggerService triggerService;
    @Autowired
    private ISysUserService sysUserService;

    private RuntimeService runtimeService = casicProcessEngine.getRuntimeService();
    private TaskService taskService = casicProcessEngine.getTaskService();
    private RepositoryService repositoryService = casicProcessEngine.getRepositoryService();
    private HistoryService historyService = casicProcessEngine.getHistoryService();
    private FormService formService = casicProcessEngine.getFormService();
    private FormRepositoryService formRepositoryService = casicFormEngine.getFormRepositoryService();


    @Override
    public ProcessInstance startProcessInstanceByProDefKey(String procDefKey, String businessKey, String userId) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("INITIATOR",userId);
        //设置流程发起人
        Authentication.setAuthenticatedUserId(userId);
        //参数1，流程key值，参数2是businessKey值，参数3是流程变量map对象。
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(procDefKey, businessKey, variables);
        //这个方法最终使用一个ThreadLocal类型的变量进行存储，也就是与当前的线程绑定，所以流程实例启动完毕之后，需要设置为null，防止多线程的时候出问题。
        Authentication.setAuthenticatedUserId(null);

        return processInstance;
    }

    /**
     * 根据BusinessKey 当前taskId
     *
     * @param processInstanceId
     * @return
     */
    @Override
    public String findActiveTaskIdByProcessInstanceId(String processInstanceId) {
        String taskId = "";
        //获取当前task
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
        if (task != null) {
            taskId = task.getId();
        }

        return taskId;
    }

    /**
     * 根据BUsinessKey 获取当前task
     */
    @Override
    public Task findActiveTaskByProcessInstanceId(String processInstanceId){
        //获取当前task
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
        return task;
    }

    /**
     * 判断当前登陆人是否有审批权限
     *
     * @param userId
     * @param processInstanceId 业务字段
     * @return boolean
     */
    @Override
    public Boolean getUserRight(String userId, String processInstanceId) {
        Boolean ret = false;

        //获取流程实例
        ProcessInstance pi = this.runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (pi != null) {
            List<Task> taskList = this.taskService.createTaskQuery().taskCandidateOrAssigned(userId).list();
            if (taskList.size() > 0) {
                for (Task task : taskList) {
                    if (pi.getId().equals(task.getProcessInstanceId())) {
                        ret = true;
                    }
                }
            }
        }

        return ret;
    }

    /**
     * 根据processInstanceId 获取流程下一步可提交的路由信息
     *
     * @param processInstanceId
     * @return
     */
    @Override
    public List<Map> findOutComeListByProcessInstanceId(String processInstanceId) {
        //返回存放连线的名称集合
        List<Map> list = new ArrayList<Map>();
        //获取流程实例
        ProcessInstance pi = this.runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if(pi!=null){
            //获取当前task
            Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
            // 当前审批节点
            ExecutionEntity ee = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
            String crruentActivityId = ee.getActivityId();
            BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
            FlowNode flowNode = (FlowNode) bpmnModel.getFlowElement(crruentActivityId);

            // 输出连线
            List<SequenceFlow> outFlows = flowNode.getOutgoingFlows();
            for (SequenceFlow sequenceFlow : outFlows)
            {
                // 下一个审批节点
                FlowElement targetFlow = sequenceFlow.getTargetFlowElement();
                if (targetFlow instanceof ExclusiveGateway)
                {
                    // 如果下个审批节点为网关节点
                    String transName = sequenceFlow.getName();
                    String transType = sequenceFlow.getDocumentation();
                    ExclusiveGateway ex = (ExclusiveGateway)targetFlow;
                    List<SequenceFlow> exOutFlows = ex.getOutgoingFlows();
                    for(SequenceFlow sf : exOutFlows){
                        FlowElement exTarget = sf.getTargetFlowElement();
                        String targetId = exTarget.getId();
                        if(exTarget instanceof EndEvent){
                            String condition = sf.getConditionExpression();
                            if(StringUtils.isNotEmpty(condition)){
                                condition = condition.trim();
                                if(condition==""){
                                    condition = null;
                                }
                            }
                            if (StringUtils.isNotBlank(transName)) {
                                Map map = new HashMap();
                                map.put("condition", condition);
                                map.put("name", transName);
                                map.put("candidateGroups", null);
                                map.put("candidateUsers", null);
                                map.put("taskType", "endEvent");
                                map.put("transType", transType);
                                map.put("targetId", targetId);
                                list.add(map);
                            }
                        }else if(exTarget instanceof UserTask){
                            String condition = sf.getConditionExpression();
                            if(StringUtils.isNotEmpty(condition)){
                                condition = condition.trim();
                                if(condition==""){
                                    condition = null;
                                }
                            }
                            UserTask exUserTask = (UserTask)exTarget;
                            List<String> candidateGroups = exUserTask.getCandidateGroups();
                            List<String> candidateUsers = exUserTask.getCandidateUsers();
                            String candidateGroupsStr = "";
                            for(int i=0;i<candidateGroups.size();i++){
                                if(i != 0){
                                    candidateGroupsStr += ",";
                                }
                                candidateGroupsStr += candidateGroups.get(i);
                            }
                            String candidateUsersStr = "";
                            for(int i=0;i<candidateUsers.size();i++){
                                if(i != 0){
                                    candidateUsersStr += ",";
                                }
                                candidateUsersStr += candidateUsers.get(i);
                            }
                            if (StringUtils.isNotBlank(transName)) {
                                Map map = new HashMap();
                                map.put("condition", condition);
                                map.put("name", transName);
                                map.put("candidateGroups", candidateGroupsStr);
                                map.put("candidateUsers", candidateUsersStr);
                                map.put("taskType", "userTask");
                                map.put("transType", transType);
                                map.put("targetId", targetId);
                                list.add(map);
                            }
                        }
                    }
                }else if(targetFlow instanceof EndEvent){
                    // 如果下个审批节点为结束节点
                    String transName = sequenceFlow.getName();
                    String transType = sequenceFlow.getDocumentation();
                    String targetId = targetFlow.getId();
                    String condition = sequenceFlow.getConditionExpression();
                    if(StringUtils.isNotEmpty(condition)){
                        condition = condition.trim();
                        if(condition==""){
                            condition = null;
                        }
                    }
                    String userType = "";
                    if (StringUtils.isNotBlank(transName)) {
                        Map map = new HashMap();
                        map.put("condition", condition);
                        map.put("name", transName);
                        map.put("candidateGroups", null);
                        map.put("candidateUsers", null);
                        map.put("taskType", "endEvent");
                        map.put("transType", transType);
                        map.put("targetId", targetId);
                        list.add(map);
                    }
                }else if(targetFlow instanceof UserTask)
                {
                    String transName = sequenceFlow.getName();
                    String transType = sequenceFlow.getDocumentation();
                    String targetId = targetFlow.getId();
                    String condition = sequenceFlow.getConditionExpression();
                    if(StringUtils.isNotEmpty(condition)){
                        condition = condition.trim();
                        if(condition==""){
                            condition = null;
                        }
                    }
                    UserTask userTask = (UserTask) targetFlow;
                    List<String> candidateGroups = userTask.getCandidateGroups();
                    List<String> candidateUsers = userTask.getCandidateUsers();
                    String candidateGroupsStr = "";
                    for(int i=0;i<candidateGroups.size();i++){
                        if(i != 0){
                            candidateGroupsStr += ",";
                        }
                        candidateGroupsStr += candidateGroups.get(i);
                    }
                    String candidateUsersStr = "";
                    for(int i=0;i<candidateUsers.size();i++){
                        if(i != 0){
                            candidateUsersStr += ",";
                        }
                        candidateUsersStr += candidateUsers.get(i);
                    }
                    if (StringUtils.isNotBlank(transName)) {
                        Map map = new HashMap();
                        map.put("condition", condition);
                        map.put("name", transName);
                        map.put("candidateGroups", candidateGroupsStr);
                        map.put("candidateUsers", candidateUsersStr);
                        map.put("taskType", "userTask");
                        map.put("transType", transType);
                        map.put("targetId", targetId);
                        list.add(map);
                    }
                }
            }
        }

        return list;
    }

    /**
     * 审批通过(驳回直接跳回功能需后续扩展)
     *
     * @param taskId    当前任务ID
     * @param userId    审批人ID
     * @param variables 流程存储参数
     * @throws Exception
     */
    @Override
    public Boolean commitProcess(String taskId,String userId, Map<String, Object> variables, String comment,Map<String, Object> formProperty) throws Exception {
        Boolean flag = false;
        if (variables == null) {
            variables = new HashMap<String, Object>();
        }
        // 跳转节点为空，默认提交操作
        if (StringUtils.isNotBlank(taskId)) {
            //先认领/拾取任务  在通过审批,否则组任务无法追踪审批痕迹
            taskService.claim(taskId, userId);
            if (StringUtils.isNotEmpty(comment)) {
                taskService.addComment(taskId, null, comment);
            }

            String formKey = formService.getTaskFormData(taskId).getFormKey();
            if(StringUtils.isNotEmpty(formKey)){
                FormInfo info = formRepositoryService.getFormModelByKey(formKey);
                String formDefinitionId = info.getId();
                taskService.completeTaskWithForm(taskId,formDefinitionId,null,formProperty,variables);
            }else{
                taskService.complete(taskId, variables);
            }

            flag = true;
        }
        return flag;
    }

    @Override
    public void setNextAssignee(String proId, String userList) {
        //获取当前task
        Task task = taskService.createTaskQuery().processInstanceId(proId).active().singleResult();
        String[] candidateUsers = userList.split(",");
        if (candidateUsers != null && candidateUsers.length > 0) {
            if (candidateUsers.length == 1) {
                taskService.claim(task.getId(), candidateUsers[0]);
            } else {
                for (int i = 0; i < candidateUsers.length; i++) {
                    taskService.addCandidateUser(task.getId(), candidateUsers[i]);
                }
            }
        }
    }

    /**
     * 获得历史节点的表单
     */
    public List<FormField> getHisTaskForm(String taskId) {
        List<FormField> form = new ArrayList<FormField>();
        HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        String formKey = task.getFormKey();
        if(StringUtils.isNotEmpty(formKey)){
            FormInfo info = formRepositoryService.getFormModelByKey(formKey);
            SimpleFormModel sfm = (SimpleFormModel) info.getFormModel();
            form = sfm.getFields();
        }

        for (FormField field : form){
            if(field instanceof OptionFormField){
                OptionFormField optionField = (OptionFormField) field;
                String expression = optionField.getOptionsExpression();
                if(StringUtils.isNotEmpty(expression)){
                    List<SysDictData> dictList = dictDataService.selectDictDataByType(expression);
                    List<Option> optionList = new ArrayList<>();
                    for(SysDictData dict : dictList){
                        Option option = new Option();
                        option.setId(dict.getDictValue());
                        option.setName(dict.getDictLabel());
                        optionList.add(option);
                    }
                    optionField.setOptions(optionList);
                    field = optionField;
                }
            }
        }

        return form;
    }

    /**
     * 获得当前节点的表单
     * @param taskId
     */
    @Override
    public List<FormField> getTaskForm(String taskId) {
        List<FormField> form = new ArrayList<FormField>();
        String formKey = formService.getTaskFormData(taskId).getFormKey();
        if(StringUtils.isNotEmpty(formKey)){
            FormInfo info = formRepositoryService.getFormModelByKey(formKey);
            SimpleFormModel sfm = (SimpleFormModel) info.getFormModel();
            form = sfm.getFields();
        }

        for (FormField field : form){
            if(field instanceof OptionFormField){
                OptionFormField optionField = (OptionFormField) field;
                String expression = optionField.getOptionsExpression();
                if(StringUtils.isNotEmpty(expression)){
                    List<SysDictData> dictList = dictDataService.selectDictDataByType(expression);
                    List<Option> optionList = new ArrayList<>();
                    for(SysDictData dict : dictList){
                        Option option = new Option();
                        option.setId(dict.getDictValue());
                        option.setName(dict.getDictLabel());
                        optionList.add(option);
                    }
                    optionField.setOptions(optionList);
                    field = optionField;
                }
            }
        }

        return form;
    }

    /**
     * 获取流程中包含的表单key List
     * @param bpmnModel
     */
    @Override
    public List<String> getProcessContainForms(BpmnModel bpmnModel){
        List<String> forms = new ArrayList<String>();
        List<UserTask> userTaskList =  bpmnModel.getProcesses().get(0).findFlowElementsOfType(UserTask.class);
        for(UserTask userTask : userTaskList){
            String formKey = userTask.getFormKey();
            if(StringUtils.isNotEmpty(formKey)){
                if(!forms.contains(formKey)){
                    forms.add(formKey);
                }
            }
        }
        return forms;
    }

    /**
     * 获取流程图
     */
    public InputStream getProcessImgIo(String processDefinitionId,String processInstanceId){
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
        List<String> highLightedActivities = new ArrayList<String>();
        if (runtimeService.createExecutionQuery().executionId(processInstanceId).count() > 0) {
            highLightedActivities = runtimeService.getActiveActivityIds(processInstanceId);
        }else{
            HistoricProcessInstance hisInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String endActivityId = hisInstance.getEndActivityId();
            highLightedActivities.add(endActivityId);
        }
        List<String> highLightedFlows = new ArrayList<String>();
        InputStream in = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,"PNG",highLightedActivities,highLightedFlows,"微软雅黑","微软雅黑","微软雅黑",null,1.0,true);
        return in;
    }
    /**
     * 获取候选人列表
     */
    public List<SysUser> getCandidateList(String candidateGroups, String candidateUsers, String candidateVar){
        JSONObject variablesMap = JSONObject.parseObject(candidateVar);
        List<String> userIdList = new ArrayList<String>();
        List<SysUser> candidateList = new ArrayList<SysUser>();
        if(StringUtils.isNotEmpty(candidateGroups) && !"null".equals(candidateGroups.toLowerCase())){
            String[] candidateGroupsArray = candidateGroups.split(",");
            for(String candidateGroup : candidateGroupsArray){
                String[] dimensionArray = candidateGroup.split("-");
                String deptCode = "";
                String roleCode = "";
                for(String dimension : dimensionArray){
                    String[] dimArray = dimension.split("_");
                    String key = dimArray[0];
                    String value = dimArray[1];
                    if("dept".equals(key)){
                        if(value.indexOf("$")<0){
                            deptCode = value;
                        }else{
                            String variablesKey = value.substring(1);
                            deptCode = variablesMap.getString(variablesKey);
                        }
                    }
                    /*if("career".equals(key)){
                        if(value.indexOf("$")<0){
                            careerCode = value;
                        }else{
                            String variablesKey = value.substring(1);
                            careerCode = variablesMap.getString(variablesKey);
                        }
                    }*/
                    if("roleCode".equals(key)){
                        if(value.indexOf("$")<0){
                            roleCode = value;
                        }else{
                            String variablesKey = value.substring(1);
                            roleCode = variablesMap.getString(variablesKey);
                        }
                    }
                }
                // List<SysUser> userList = sysUserService.selectCandidateList(deptCode,careerCode);
                List<SysUser> userList = sysUserService.selectCandidateListByDeptAndRole(deptCode,roleCode);
                for(SysUser user : userList){
                    userIdList.add(user.getUserId());
                }
            }
        }

        if(StringUtils.isNotEmpty(candidateUsers) && !"null".equals(candidateUsers.toLowerCase())){
            String[] candidateUsersArray = candidateUsers.split(",");
            for(String candidateUser : candidateUsersArray){
                String loginName = "";
                if(candidateUser.indexOf("$")>0){
                    String variablesKey = candidateUser.substring(1);
                    loginName = variablesMap.getString(variablesKey);
                }else{
                    loginName = candidateUser;
                }
                SysUser user = sysUserService.selectUserByLoginName(loginName);
                userIdList.add(user.getUserId());
            }
        }

        //去除重复的userId
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = userIdList.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (set.add(element)){
                newList.add(element);
            }
        }
        userIdList.clear();
        userIdList.addAll(newList);

        for(String userId : userIdList){
            SysUser user = sysUserService.selectUserById(userId);
            candidateList.add(user);
        }

        return candidateList;
    }

    /**
     * 流程提交成功往flow_business_records表中插入记录
     */
    @Override
    public int createProcessRecord(String taskId, String proId,String comment,String userId, String formJson ,String entityId,String type){
        FlowBusinessRecords records = new FlowBusinessRecords();
        records.setId(UuidUtils.getUUIDString());
        records.setInstanceId(proId);
        records.setTaskId(taskId);

        HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        String processDefId = task.getProcessDefinitionId();

        records.setProcessId(processDefId);
        records.setUserId(userId);
        records.setUserComment(comment);
        records.setObjId(entityId);
        records.setType(type);

        Map<String,Object> form = new HashMap<String, Object>();
        //获取表单中填写的数据
        String newFormJson = "";
        if(StringUtils.isNotEmpty(formJson)){
            form = JSONObject.parseObject(formJson,Map.class);
            List<FormField> taskForm =  this.getHisTaskForm(taskId);

            for(FormField field : taskForm){
                for(String key : form.keySet()){
                    if(key.equals(field.getId())){
                        field.setValue(form.get(key));
                    }
                }
            }

            newFormJson = JSONObject.toJSONString(taskForm);
        }
        records.setFormJson(newFormJson);

        records.setOptionDate(new Date());
        records.setCreateTime(new Date());
        records.setCreateBy(userId);

        return recordsService.insertBusinessRecords(records);
    }

    /**
     * 执行流程监听
     */
    public void executeProcessListener(String execute_url_prefix,String taskId,String entityId,String userId,String triggerType,String decideFlag)throws Exception{
        //说明：参数decideFlag标识：是否需要分管资产局级领导审批，借用申领流程需要局级审批时，传值"1",其他情况不传值
    	HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        String processDefId = task.getProcessDefinitionId();
        String proInstanceId = task.getProcessInstanceId();
        String stepKey = task.getTaskDefinitionKey();

        FlowBusinessTrigger triggerQuery = new FlowBusinessTrigger();
        triggerQuery.setProcessId(processDefId);
        triggerQuery.setStepKey(stepKey);
        triggerQuery.setTriggerType(triggerType);
        List<FlowBusinessTrigger> triggerList =  triggerService.selectBusinessTriggerList(triggerQuery);
        if(triggerList.size()>0){
            FlowBusinessTrigger listener = triggerList.get(0);
            String url = listener.getTriggerUrl();
            url = predealUrl(execute_url_prefix,url);
            Map<String,String> params = new HashMap<String,String>();
            params.put("processInstanceId",proInstanceId);
            params.put("entityId",entityId);
            params.put("processDefinitionId",processDefId);
            params.put("userId",userId);
            params.put("taskId",taskId);
            params.put("decideFlag",decideFlag);
            ApacheHttpUtils.postForm(url,params);
        }
    }

    private String predealUrl(String execute_url_prefix, String url) {
        if(url == null || url.trim().isEmpty()){
            return execute_url_prefix;
        }else{
            if(url.startsWith("/")){
                return execute_url_prefix+url;
            }else{
                return execute_url_prefix+ "/"+url;
            }
        }
    }

    /**
     * 删除流程实例
     */
    public void deleteProcessInstance(String processInstanceId){
        if (runtimeService.createExecutionQuery().executionId(processInstanceId).count() > 0) {
            //流程未结束
            runtimeService.deleteProcessInstance(processInstanceId,"管理员强制删除");
        }else{
            //流程已结束
            historyService.deleteHistoricProcessInstance(processInstanceId);
        }
    }

    /**
     * 流程回退
     */
    public boolean processBack(String taskId, String targetId){
        boolean flag = false;
        if(StringUtils.isNotEmpty(taskId) && StringUtils.isNotEmpty(targetId)){
            HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
            String proInstanceId = task.getProcessInstanceId();
            String currTaskKey = task.getTaskDefinitionKey();
            List<String> currTaskKeys = new ArrayList<>();
            currTaskKeys.add(currTaskKey);
            runtimeService.createChangeActivityStateBuilder()
                    .processInstanceId(proInstanceId)
                    .moveActivityIdsToSingleActivityId(currTaskKeys, targetId)
                    .changeState();
            flag = true;
        }
        return flag;
    }

    /**
     * 流程撤回
     */
    public Map<String,Object> processWithdraw(String processInstanceId,String userId){
        Map<String,Object> result = new HashMap<String,Object>();
        String msg = "";
        //获取流程实例
        ProcessInstance pi = this.runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if(StringUtils.isNotEmpty(userId)){
            if(pi != null){
                //获取当前task
                Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
                // 当前审批节点
                ExecutionEntity ee = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
                String crruentActivityId = ee.getActivityId();
                BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
                FlowNode flowNode = (FlowNode) bpmnModel.getFlowElement(crruentActivityId);
                String  currTaskKey = flowNode.getId();

                List<FlowBusinessRecords> recordsList = recordsService.selectBusinessRecordsListByProInstanceId(processInstanceId);
                if(recordsList.size() > 0){
                    FlowBusinessRecords record = recordsList.get(recordsList.size()-1);
                    if(userId.equals(record.getUserId())){
                        String taskId = record.getTaskId();
                        HistoricTaskInstance hisTask = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
                        String targetKey = hisTask.getTaskDefinitionKey();
                        if(targetKey.equals(currTaskKey)){
                            msg = "您已撤回，无法重复撤回";
                        }else{
                            List<String> currTaskKeys = new ArrayList<>();
                            currTaskKeys.add(currTaskKey);
                            runtimeService.createChangeActivityStateBuilder()
                                    .processInstanceId(processInstanceId)
                                    .moveActivityIdsToSingleActivityId(currTaskKeys, targetKey)
                                    .changeState();
                        }

                    }else{
                        msg = "您提交的申请已被处理，无法撤回";
                    }
                }else{
                    msg = "流程并未发起";
                }
            }else{
                msg = "流程已结束或不存在";
            }
        }else {
            msg = "执行人不能为空";
        }

        result.put("msg",msg);
        if(StringUtils.isNotEmpty(msg)){
            result.put("result", "failed");
        }else{
            result.put("result", "success");
        }

        return result;
    }

    /**
     * 获得待办任务列表
     */
    @Override
    public List<Task> getWaitTaskList(String userId){
        List<Task> list = taskService.createTaskQuery().taskCandidateOrAssigned(userId).orderByTaskCreateTime().desc().list();

        return list;
    }


}
