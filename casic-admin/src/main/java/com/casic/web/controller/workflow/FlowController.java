package com.casic.web.controller.workflow;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.casic.workflow.domain.FlowBusinessRecords;
import org.casic.workflow.service.IFlowBusinessRecordsService;
import org.casic.workflow.service.IProcessOperateService;
import org.flowable.form.model.FormField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.casic.common.base.AjaxResult;
import com.casic.common.utils.StringUtils;
import com.casic.common.utils.UuidUtils;
import com.casic.framework.web.base.BaseController;
import com.casic.system.domain.SysUser;

/**
 * Created by Administrator on 2019/3/18.
 */
@Controller
public class FlowController extends BaseController {
    @Autowired
    private IProcessOperateService processOperateService;
    @Autowired
    private IFlowBusinessRecordsService recordsService;

    /**
     * 流程设计器
     */
    @GetMapping("/workflow/designer")
    public String designer(ModelMap mmap)
    {
        return "workflow/index";
    }

    @PostMapping("/workflow/approvalInfo")
    public String selCandidate(ModelMap mmap,String candidateGroups,String candidateUsers,String taskId,String proId,String condition,
                               String taskType,String candidateVar,String conditionVar,String entityId,String backTarget){
        //获取候选人List
        List<SysUser> candidateList = processOperateService.getCandidateList(candidateGroups,candidateUsers,candidateVar);

        //获取节点上的表单
        List<FormField> form = processOperateService.getTaskForm(taskId);

        //获取流程历史提交记录
        List<FlowBusinessRecords> processRecordList = recordsService.selectBusinessRecordsListByProInstanceId(proId);
        List<Map<String,Object>> newRecordList = new ArrayList<Map<String,Object>>();
        for(FlowBusinessRecords records : processRecordList){
            Map<String,Object> recordMap = new HashMap<String, Object>();
            recordMap.put("record",records);
            String formJson = records.getFormJson();
            List<Map<String,Object>> formObj = new ArrayList<Map<String, Object>>();
            //获取表单中填写的数据
            if(StringUtils.isNotEmpty(formJson)){
                formObj = JSONObject.parseObject(formJson,List.class);
            }
            recordMap.put("form",formObj);
            newRecordList.add(recordMap);
        }

        mmap.put("candidateList",candidateList);
        mmap.put("candidateListJson",JSON.toJSONString(candidateList));
        mmap.put("taskId",taskId);
        mmap.put("proId",proId);
        mmap.put("taskType",taskType);
        mmap.put("processRecordList",newRecordList);
        mmap.put("form",form);
        mmap.put("conditionVar",conditionVar);
        mmap.put("condition",condition);
        mmap.put("entityId",entityId);
        mmap.put("backTarget", backTarget);

        return "workflow/approvalInfo";
    }

    @PostMapping("/workflow/queryWorkflow")
    public String queryWorkflow(ModelMap mmap,String candidateGroups,String candidateUsers,String taskId,String proId,String condition,														 
                               String taskType,String candidateVar,String conditionVar,String entityId,String backTarget,String comment,String enableCommentStore,String commentArray,String decideFlag){
        //获取候选人List
        List<SysUser> candidateList = processOperateService.getCandidateList(candidateGroups,candidateUsers,candidateVar);

        //获取节点上的表单
        List<FormField> form = processOperateService.getTaskForm(taskId);

        //获取流程历史提交记录
        List<FlowBusinessRecords> processRecordList = recordsService.selectBusinessRecordsListByProInstanceId(proId);
        List<Map<String,Object>> newRecordList = new ArrayList<Map<String,Object>>();
        for(FlowBusinessRecords records : processRecordList){
            Map<String,Object> recordMap = new HashMap<String, Object>();
            recordMap.put("record",records);
            String formJson = records.getFormJson();
            List<Map<String,Object>> formObj = new ArrayList<Map<String, Object>>();
            //获取表单中填写的数据
            if(StringUtils.isNotEmpty(formJson)){
                formObj = JSONObject.parseObject(formJson,List.class);
            }
            recordMap.put("form",formObj);
            newRecordList.add(recordMap);
        }

        mmap.put("candidateList",candidateList);
        mmap.put("candidateListJson",JSON.toJSONString(candidateList));
        mmap.put("taskId",taskId);
        mmap.put("proId",proId);
        mmap.put("taskType",taskType);
        mmap.put("processRecordList",newRecordList);
        mmap.put("form",form);
        mmap.put("conditionVar",conditionVar);
        mmap.put("condition",condition);
        mmap.put("entityId",entityId);
        mmap.put("backTarget", backTarget);
        mmap.put("comment", comment);
        mmap.put("enableCommentStore", enableCommentStore);
        mmap.put("commentArray", commentArray);
        mmap.put("decideFlag", decideFlag);
        return "workflow/selectApprover";
    }

    /**
     * hl
     * 部门领导—》资产管理部门领导之间不弹出选择人审批人的框，默认是所有资产管理部门领导，谁看到谁审批。
     * manyPtcipantFlow 多个参与者
     */
    @PostMapping("/workflow/serveralCandidateFlow")
    @ResponseBody
    public AjaxResult serveralCandidateFlow(ModelMap mmap,String candidateGroups,String candidateUsers,String taskId,String proId,
    		String condition,String taskType,String candidateVar,String conditionVar,String entityId,String backTarget,
    		String comment,String enableCommentStore,String commentArray,HttpServletRequest request){
    	
    	AjaxResult result = success();
        //获取候选人
        List<SysUser> candidateList = processOperateService.getCandidateList(candidateGroups,candidateUsers,candidateVar);
        //userList为userid，通过逗号隔开
        String userList = "";
        if (candidateList != null && candidateList.size() > 0) {
			for (SysUser sysUser : candidateList) {
				String userId = String.valueOf(sysUser.getUserId());
				userList += userId + ",";
			}
		}
        
        result = submitApply(taskId, proId, userList, comment, condition,conditionVar, "", entityId, commentArray, enableCommentStore,"", request);
        return result;
    }

    /**
     *
     * @param taskId 当前任务id
     * @param proId 流程id
     * @param userList 下个节点处理人
     * @param comment  审批意见
     * @return
     */
    @PostMapping("/workflow/submitApply")
    @ResponseBody
    public AjaxResult submitApply(String taskId, String proId, String userList, String comment,String condition,
                                  String conditionVar,String formJson,String entityId,String commentArray,String enableCommentStore,String decideFlag, HttpServletRequest request){
        try {
            String userId = this.getUserId()+"";
            Map<String,Object> form = new HashMap<String, Object>();

            //获取表单中填写的数据
            if(StringUtils.isNotEmpty(formJson)){
                form = JSONObject.parseObject(formJson,Map.class);
            }
            //流程中的临时变量（例如网关中每个分支的条件节点的变量）
            conditionVar = StringEscapeUtils.unescapeHtml4(conditionVar);
            JSONObject variablesMap = JSONObject.parseObject(conditionVar);
            Map<String,Object> variables = new HashMap<String,Object>();
            if(StringUtils.isNotEmpty(condition)){
                for(String key : variablesMap.keySet()){
                    if(condition.indexOf(key) >= 0){
                        variables.put(key,variablesMap.get(key));
                    }
                }
            }
            //提交至下一步
            Boolean over = processOperateService.commitProcess(taskId,userId, variables,comment,form);
            if (over) {
                //添加下一步的处理人
                if(StringUtils.isNotEmpty(userList)){
                    processOperateService.setNextAssignee(proId, userList);
                }

                //把流程处理记录加入flow_business_records表中
                processOperateService.createProcessRecord(taskId,proId,comment,userId,formJson,entityId,"PASS");

                //获取服务器跟路径
                String path = request.getContextPath(); 
                String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
                //执行监听
                processOperateService.executeProcessListener(basePath,taskId,entityId,userId,"AFTER",decideFlag);

            }
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }



    @GetMapping("/workflow/IoProcessImg/{processDefinitionId}/{processInstanceId}")
    public String ioProcessImg(@PathVariable("processDefinitionId")String processDefinitionId,@PathVariable("processInstanceId")String processInstanceId, HttpServletRequest request, HttpServletResponse response)throws IOException {
        InputStream in = processOperateService.getProcessImgIo(processDefinitionId,processInstanceId);
        ServletOutputStream out = null;
        try {
            response.setContentType("multipart/form-data");
            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = in.read(buffer)) != -1){
                out.write(buffer,0,len);
            }
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
            in.close();
        }
        return null;
    }

    @PostMapping("/workflow/flowBack")
    @ResponseBody
    public AjaxResult flowBack(String taskId, String proId, String comment,String entityId,String targetId,String commentArray,String enableCommentStore, HttpServletRequest request){
        try {
            String userId = this.getUserId()+"";
            Boolean over = processOperateService.processBack(taskId,targetId);
            if(over){
                //把流程处理记录加入flow_business_records表中
                processOperateService.createProcessRecord(taskId,proId,comment,userId,"",entityId,"BACK");

                //获取服务器跟路径
                String path = request.getContextPath(); 
                String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
                //执行监听
                processOperateService.executeProcessListener(basePath,taskId,entityId,userId,"BACK","");
                return AjaxResult.success();
            }else{
                return AjaxResult.error();
            }

        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    @PostMapping("/workflow/flowWithdraw")
    @ResponseBody
    public AjaxResult flowWithdraw(String proId,String entityId, HttpServletRequest request){
        try {
            String userId = this.getUserId()+"";
            Map<String,Object> result = processOperateService.processWithdraw(proId,userId);

            if("success".equals(result.get("result").toString())){
                //当前taskId
                String taskId = processOperateService.findActiveTaskIdByProcessInstanceId(proId);

                //把流程处理记录加入flow_business_records表中
                processOperateService.createProcessRecord(taskId,proId,"撤回",userId,"",entityId,"WITHDRAW");

                //获取服务器跟路径
                String path = request.getContextPath(); 
                String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
                //执行监听
                processOperateService.executeProcessListener(basePath,taskId,entityId,userId,"WITHDRAW","");
                return AjaxResult.success();
            }else{
                return AjaxResult.error(result.get("msg").toString());
            }

        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error();
        }
    }
}
