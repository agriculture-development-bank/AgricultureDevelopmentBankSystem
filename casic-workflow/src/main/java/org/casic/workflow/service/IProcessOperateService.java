package org.casic.workflow.service;

import com.casic.system.domain.SysUser;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.form.model.FormField;
import org.flowable.task.api.Task;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/3/21.
 */
public interface IProcessOperateService {
    /**
     * 根据流程定义key启动流程
     *
     * @param procDefKey
     * @param businessKey
     * @param userId
     * @return
     */
    public ProcessInstance startProcessInstanceByProDefKey(String procDefKey, String businessKey, String userId);

    /**
     * 根据BusinessKey 获取当前taskId
     *
     * @param processInstanceId
     * @return
     */
    public String findActiveTaskIdByProcessInstanceId(String processInstanceId);

    /**
     * 根据BUsinessKey 获取当前task
     */
    public Task findActiveTaskByProcessInstanceId(String processInstanceId);

    /**
     * 判断当前登陆人是否有审批权限
     *
     * @param userId
     * @param processInstanceId 业务字段
     * @return boolean
     */
    public Boolean getUserRight(String userId, String processInstanceId);

    /**
     * 根据processInstanceId 获取下个节点信息
     *
     * @param processInstanceId
     * @return
     */
    public List<Map> findOutComeListByProcessInstanceId(String processInstanceId);

    /***
     * 提交至下一步
     * @param taskId
     * @param userId
     * @param variables
     * @param comment
     */
    public Boolean commitProcess(String taskId, String userId, Map<String, Object> variables, String comment, Map<String, Object> formProperty) throws Exception;

    /**
     * 设置下个节点的处理人
     * @param proId
     * @param userList
     */
    public void setNextAssignee(String proId, String userList);

    /**
     * 获得历史节点的表单
     */
    public List<FormField> getHisTaskForm(String taskId);

    /**
     * 获得当前节点的表单
     * @param taskId
     */
    public List<FormField> getTaskForm(String taskId);

    /**
     * 获取流程中包含的表单key List
     * @param bpmnModel
     */
    public List<String> getProcessContainForms(BpmnModel bpmnModel);

    /**
     * 获取流程图
     */
    public InputStream getProcessImgIo(String processDefinitionId,String processInstanceId);

    /**
     * 获取候选人列表
     */
    public List<SysUser> getCandidateList(String candidateGroups, String candidateUsers, String candidateVar);

    /**
     * 流程提交成功往flow_business_records表中插入记录
     */
    public int createProcessRecord(String taskId, String proId,String comment,String userId, String formJson,String entityId,String type);

    /**
     * 执行流程监听
     */
    public void executeProcessListener(String execute_url_prefix,String taskId,String entityId,String userId,String triggerType,String decideFlag) throws Exception;

    /**
     * 删除流程实例
     */
    public void deleteProcessInstance(String processInstanceId);

    /**
     * 流程回退
     */
    public boolean processBack(String taskId, String targetId);

    /**
     * 流程撤回
     */
    public Map<String,Object> processWithdraw(String processInstanceId,String userId);

    /**
     * 获得待办任务列表
     */
    public List<Task> getWaitTaskList(String userId);

}
