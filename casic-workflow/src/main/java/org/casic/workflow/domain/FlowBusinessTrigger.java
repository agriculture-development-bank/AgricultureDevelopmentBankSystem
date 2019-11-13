package org.casic.workflow.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.casic.common.base.BaseEntity;
import java.util.Date;

/**
 * 流程节点触发器表 flow_business_trigger
 * 
 * @author yuzengwen
 * @date 2019-04-11
 */
public class FlowBusinessTrigger extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private String id;
	/** 流程id */
	private String processId;
	/** 操作实体id */
	private String stepKey;
	/** 前置：before
            后置：after */
	private String triggerType;
	/** 由于客户端与引擎之间是进程隔离，所以通过rest网址调用本地实例 */
	private String triggerUrl;
	/** 操作用户id */
	private String userId;
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
	public void setStepKey(String stepKey) 
	{
		this.stepKey = stepKey;
	}

	public String getStepKey() 
	{
		return stepKey;
	}
	public void setTriggerType(String triggerType) 
	{
		this.triggerType = triggerType;
	}

	public String getTriggerType() 
	{
		return triggerType;
	}
	public void setTriggerUrl(String triggerUrl) 
	{
		this.triggerUrl = triggerUrl;
	}

	public String getTriggerUrl() 
	{
		return triggerUrl;
	}
	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getUserId() 
	{
		return userId;
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
            .append("stepKey", getStepKey())
            .append("triggerType", getTriggerType())
            .append("triggerUrl", getTriggerUrl())
            .append("userId", getUserId())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
