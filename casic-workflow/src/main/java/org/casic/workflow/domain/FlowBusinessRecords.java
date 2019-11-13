package org.casic.workflow.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.casic.common.base.BaseEntity;
import java.util.Date;

/**
 * 流程业务记录表 flow_business_records
 *
 * @author yuzengwen
 * @date 2019-04-10
 */
public class FlowBusinessRecords extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	/**  */
	private String id;
	/** 流程id */
	private String processId;
	/** 流程实例id */
	private String instanceId;
	/** 步骤id */
	private String taskId;
	/** 操作用户id */
	private String userId;
	/** 用户意见 */
	private String userComment;
	/** 操作时间 */
	private Date optionDate;
	/** 操作实体id */
	private String objId;
	/** 用户表单信息 */
	private String formJson;
	/** 删除标记 */
	private String delFlag;

	/**
	 * 审批类型 : "PASS","BACK","WITHDRAW"
	 */
	private String type;

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}
	public void setProcessId(String processId)
	{
		this.processId = processId;
	}

	public String getProcessId()
	{
		return processId;
	}
	public void setInstanceId(String instanceId)
	{
		this.instanceId = instanceId;
	}

	public String getInstanceId()
	{
		return instanceId;
	}
	public void setTaskId(String taskId)
	{
		this.taskId = taskId;
	}

	public String getTaskId()
	{
		return taskId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getUserId()
	{
		return userId;
	}
	public void setUserComment(String userComment)
	{
		this.userComment = userComment;
	}

	public String getUserComment()
	{
		return userComment;
	}
	public void setOptionDate(Date optionDate)
	{
		this.optionDate = optionDate;
	}

	public Date getOptionDate()
	{
		return optionDate;
	}
	public void setObjId(String objId)
	{
		this.objId = objId;
	}

	public String getObjId()
	{
		return objId;
	}
	public void setFormJson(String formJson)
	{
		this.formJson = formJson;
	}

	public String getFormJson()
	{
		return formJson;
	}
	public void setDelFlag(String delFlag)
	{
		this.delFlag = delFlag;
	}

	public String getDelFlag()
	{
		return delFlag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
				.append("id", getId())
				.append("processId", getProcessId())
				.append("instanceId", getInstanceId())
				.append("taskId", getTaskId())
				.append("userId", getUserId())
				.append("userComment", getUserComment())
				.append("optionDate", getOptionDate())
				.append("objId", getObjId())
				.append("formJson", getFormJson())
				.toString();
	}
}
