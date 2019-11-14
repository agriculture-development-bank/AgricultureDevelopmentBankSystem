function buildFlowParam(data){
    var param ={};
    param.entityId = data.entityId;
    param.activeTaskId = data.activeTaskId;
    param.processInstanceId = data.processInstanceId;
    param.backTarget = '';
    if(data.candidateVar){
        param.candidateVar= data.candidateVar;
    }
    for(var i=0; i<data.routeList.length; i++){
        if(data.routeList[i].candidateGroups == undefined ||data.routeList[i].candidateGroups == null ){
            continue;
        }
        param.taskType = data.routeList[i].taskType;
        param.condition = data.routeList[i].condition;
        param.conditionVar = data.conditionVar;
        param.targetId = data.routeList[i].targetId;
        if(data.routeList[i].transType && data.routeList[i].transType=='BACK'){
            param.backTarget = routeList[i].targetId;
        }
        param.candidateGroups = data.routeList[i].candidateGroups;
        if(!$.common.isEmpty(data.routeList[i].candidateUsers)){
            param.candidateUsers = data.routeList[i].candidateUsers;
        }
    }
    return param;
}

function flowDialog (btnParams) {
    var taskId = btnParams.activeTaskId;
    var proId = btnParams.processInstanceId;
    var candidateGroups = btnParams.candidateGroups;
    var candidateUsers = btnParams.candidateUsers;
    var taskType = btnParams.taskType;
    var entityId = btnParams.entityId;
    var backTarget = btnParams.backTarget;
    var condition = btnParams.condition;
    var candidateVar = {};
    if(btnParams.candidateVar){
        candidateVar = btnParams.candidateVar;
    }

    var candidateVarJson = JSON.stringify(candidateVar);
    var conditionVar = {};
    if(btnParams.conditionVar){
        conditionVar = btnParams.conditionVar;
    }
    var conditionVarJson = JSON.stringify(conditionVar);
    $.ajax({
        url:'/workflow/queryWorkflow',
        type:'post',
        data: {
            "taskId" : taskId,
            "proId" : proId,
            "taskType" : taskType,
            "candidateGroups" : candidateGroups,
            "candidateUsers" : candidateUsers,
            'entityId' : entityId,
            "condition" : condition,
            "candidateVar" : candidateVarJson,
            "conditionVar" : conditionVarJson,
            "backTarget" : backTarget,
            "comment" : btnParams.comment,
            "commentArray" : btnParams.commentArray,
            "enableCommentStore" : btnParams.enableCommentStore,
            "decideFlag" : btnParams.decideFlag   //该参数标识：是否需要分管资产局级领导审批
        },
        dataType : 'html',
        success : function (html) {
            var index = layer.open({
                type : 1,
                title : '流程审批',
                shadeClose: true,
                shade: 0.3,
                offset: "10%",
                shadeClose : false,
                area:  ['750px', '460px'],
                content: html,
                success: function(layero, indexOut){

                    if(!$(".flow-sel-tpl .flow-sel").hasClass("active")){
                        $(".flow-sel-tpl .flow-sel").addClass("active");
                    }
                    var HTMLtpl = $(".flow-sel-tpl").html();
                    $(".flow-sel-tpl .flow-sel").removeClass("active");
                    var indexIn = layer.open({
                        type : 1,
                        title : '选择审批人员',
                        move: false,
                        resize:false,
                        shade: 0.3,
                        offset: "10%",
                        shadeClose : false,
                        area: ['750px', '460px'],
                        content: HTMLtpl,
                        cancel:function(){
                            layer.close(indexOut);
                        },
                        end: function(){
                            layer.close(indexOut);
                        },
                    });

                }
            });
        }
    });
}

/**
 * hl  直接提交到多个审批人，不弹出审批人选择框
 * @param btnParams
 * @returns
 */
function serveralCandidateFlow (btnParams) {
	
    var taskId = btnParams.activeTaskId;
    var proId = btnParams.processInstanceId;
    var candidateGroups = btnParams.candidateGroups;
    var candidateUsers = btnParams.candidateUsers;
    var taskType = btnParams.taskType;
    var entityId = btnParams.entityId;
    var backTarget = btnParams.backTarget;
    var condition = btnParams.condition;
    var candidateVar = {};
    if(btnParams.candidateVar){
        candidateVar = btnParams.candidateVar;
    }

    var candidateVarJson = JSON.stringify(candidateVar);
    var conditionVar = {};
    if(btnParams.conditionVar){
        conditionVar = btnParams.conditionVar;
    }
    var conditionVarJson = JSON.stringify(conditionVar);
    $.modal.loading("正在处理中，请稍后...");
    $.ajax({
        url:'/workflow/serveralCandidateFlow',
        type:'post',
        dataType: 'json',
        async : false,
        data: {
            "taskId" : taskId,
            "proId" : proId,
            "taskType" : taskType,
            "candidateGroups" : candidateGroups,
            "candidateUsers" : candidateUsers,
            'entityId' : entityId,
            "condition" : condition,
            "candidateVar" : candidateVarJson,
            "conditionVar" : conditionVarJson,
            "backTarget" : backTarget,
            "comment" : btnParams.comment,
            "commentArray" : btnParams.commentArray,
            "enableCommentStore" : btnParams.enableCommentStore
        },
        success: function (result) {
        			$.modal.closeLoading();
                if (result.code == web_status.SUCCESS) {
                    layer.msg("操作成功！",
                        {
                            time: 1000,
                        },
                        function () {
                        		
                            //刷新个人申请页面
                            refreshMenuItem('/sastind/apply');
                            //刷新审批页面
                            refreshMenuItem('/sastind/approval');
                            //刷新主页面待办任务
                            refreshMenuItem('/sastind/main');
                            var curUrl = window.location.pathname + window.location.search;
                            var tabs = $(".menuTabs .menuTab",window.parent.document);
                            var targetUrl = ""
                            $.each(tabs,function (i,el) {
                                var dataId = $(el).data("id");
                                if(dataId){
                                    if(curUrl.indexOf(dataId)>=0){
                                        var df = curUrl.length - dataId.length;
                                        if(df < 2){
                                            targetUrl = dataId;
                                        }
                                    }
                                }
                            })
                            $.modal.closeTab(targetUrl);
                        }
                    )
                } else {
                    layer.msg("操作失败！",
                        {
                            time: 1000,
                        },
                        function () {
                            location.reload();
                        }
                    )
                }

            }
    });
}

function flowback(obj) {
    var flag = '0';
    if(obj.comment && $.trim(obj.comment) != ""){
        $.modal.loading("正在处理中，请稍后...");
        $.ajax({
            url: ctx + 'workflow/flowBack',
            type: 'post',
            dataType: 'json',
            async: false,
            data: {
                "taskId" : obj.activeTaskId,
                "proId" : obj.processInstanceId,
                "comment" : obj.comment,
                "entityId" : obj.entityId,
                "targetId" : obj.backTarget,
                "commentArray" :obj.commentArray,
                "enableCommentStore" :obj.enableCommentStore
            },
            success: function (result) {
                $.modal.closeLoading();
                if (result.code == web_status.SUCCESS) {
                    flag = '1';
                    
                    //刷新个人申请页面
                    refreshMenuItem('/sastind/apply');
                    //刷新审批页面
                    refreshMenuItem('/sastind/approval');
                    //刷新主页面待办任务
                    refreshMenuItem('/sastind/main');
                } else {
                    layer.msg("操作失败！",
                        {
                            time: 1000,
                        },
                        function () {
                            location.reload();
                        }
                    )
                }

            }
        })
    }else{
        layer.msg("意见不能为空！");
    }
    return flag;
}

function buildAuditParam(type,params){
    var param ={};
    param.activeTaskId = params.activeTaskId;
    param.entityId = params.entityId;
    param.processInstanceId = params.processInstanceId;
    param.backTarget = '';
    //是否启用意见明细存储
    param.enableCommentStore = 'true';
    var routeList = params.btnData;
    if(params.conditionVar){
        param.conditionVar= params.conditionVar;
    }
    if(params.candidateVar){
        param.candidateVar= params.candidateVar;
    }
    
    param.decideFlag = params.decideFlag;
    
    var decideFlag = params.decideFlag;
    //debugger
    if(type=='1'){
    	//debugger;
    	if(decideFlag == undefined){//除了借用申领流程，其他流程bureauFlag都为undefined（null）
    		for(var i=0;i<routeList.length;i++){
                if(routeList[i].transType == undefined){
                    param.candidateGroups = routeList[i].candidateGroups;
                    if(!$.common.isEmpty(routeList[i].candidateUsers)){
                        param.candidateUsers = routeList[i].candidateUsers;
                    }
                    param.taskType = routeList[i].taskType;
                    param.targetId = routeList[i].targetId;
                    param.condition = routeList[i].condition;
                }

            }
    	}else{
    		var dflag = '${bureauFlag=="'+decideFlag+'"}'
    		//${bureauFlag=="0"}
    		for(var i=0;i<routeList.length;i++){
                if(routeList[i].transType == undefined && routeList[i].condition == dflag){
                    param.candidateGroups = routeList[i].candidateGroups;
                    if(!$.common.isEmpty(routeList[i].candidateUsers)){
                        param.candidateUsers = routeList[i].candidateUsers;
                    }
                    param.taskType = routeList[i].taskType;
                    param.targetId = routeList[i].targetId;
                    param.condition = routeList[i].condition;
                }

            }
    	}
        
    }else if(type=='0'){
        for(var i=0;i<routeList.length;i++){
            if(routeList[i].transType && routeList[i].transType=='BACK'){
                param.backTarget = routeList[i].targetId;
                param.taskType = routeList[i].taskType;
                param.condition = routeList[i].condition;
            }
        }
    }

    return param;
}


function flowEnd(param) {
	$.modal.loading("正在处理中，请稍后...");
    var flag = 0;
    var comment=param.comment;
    var candidateVar = {};
    if(param.candidateVar){
        candidateVar = param.candidateVar;
    }

    var candidateVarJson = JSON.stringify(candidateVar);
    var conditionVar = {};
    if(param.conditionVar){
        conditionVar = param.conditionVar;
    }
    var conditionVarJson = JSON.stringify(conditionVar);
    $.ajax({
        url: ctx + 'workflow/submitApply',
        type: 'post',
        async: false,
        dataType: 'json',
        data: {
            "taskId" : param.activeTaskId,
            "proId" : param.processInstanceId,
            "comment" : param.comment,
            "condition" :param.condition,
            "conditionVar" : conditionVarJson,
            "entityId" : param.entityId,
            "formJson" :'',
            "commentArray" :param.commentArray,
            "enableCommentStore" :param.enableCommentStore
        },
        success: function (result) {
        		$.modal.closeLoading();	
            if (result.code == web_status.SUCCESS) {
                flag = 1;
                //debugger	
                //刷新个人申请页面
                refreshMenuItem('/sastind/apply');
                //刷新审批页面
                refreshMenuItem('/sastind/approval');
                //刷新主页面待办任务
                refreshMenuItem('/sastind/main');
                
            } else {
                layer.msg("操作失败！",
                    {
                        time: 1000,
                    },
                    function () {
                        location.reload();
                    }

                )
            }

        }
    })
    return flag;
}