package org.casic.workflow.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.casic.common.base.BaseEntity;
import java.util.Date;

/**
 * 业务统计表 flow_business_count
 * 
 * @author yuzengwen
 * @date 2019-04-11
 */
public class FlowBusinessCount extends BaseEntity
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
	/** 最终流程的出借结果 */
	private String optionResult;
	/** 操作时间 */
	private Date optionDate;
	/** 实体对应表名称 */
	private String tableName;
	/** 操作实体id */
	private String objId;
	/** 删除标记 */
	private String delFlag;
	/** 创建人 */
	private String createBy;
	/** 创建时间 */
	private Date createTime;
	/** 更新人 */
	private String updateBy;
	/** 更新时间 */
	private Date updateTime;
	/** 备注 */
	private String remark;

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
	public void setOptionResult(String optionResult) 
	{
		this.optionResult = optionResult;
	}

	public String getOptionResult() 
	{
		return optionResult;
	}
	public void setOptionDate(Date optionDate) 
	{
		this.optionDate = optionDate;
	}

	public Date getOptionDate() 
	{
		return optionDate;
	}
	public void setTableName(String tableName) 
	{
		this.tableName = tableName;
	}

	public String getTableName() 
	{
		return tableName;
	}
	public void setObjId(String objId) 
	{
		this.objId = objId;
	}

	public String getObjId() 
	{
		return objId;
	}
	public void setDelFlag(String delFlag) 
	{
		this.delFlag = delFlag;
	}

	public String getDelFlag() 
	{
		return delFlag;
	}
	public void setCreateBy(String createBy) 
	{
		this.createBy = createBy;
	}

	public String getCreateBy() 
	{
		return createBy;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}
	public void setUpdateBy(String updateBy) 
	{
		this.updateBy = updateBy;
	}

	public String getUpdateBy() 
	{
		return updateBy;
	}
	public void setUpdateTime(Date updateTime) 
	{
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() 
	{
		return updateTime;
	}
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}

	public String getRemark() 
	{
		return remark;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("processId", getProcessId())
            .append("instanceId", getInstanceId())
            .append("taskId", getTaskId())
            .append("userId", getUserId())
            .append("optionResult", getOptionResult())
            .append("optionDate", getOptionDate())
            .append("tableName", getTableName())
            .append("objId", getObjId())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
