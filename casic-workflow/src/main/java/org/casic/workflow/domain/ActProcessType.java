package org.casic.workflow.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.casic.common.base.BaseEntity;

/**
 * 流程部署表 act_process_type
 * 
 * @author yuzengwen
 * @date 2019-04-03
 */
public class ActProcessType extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	/** 主键 */
	private String id;
	/** 应用编码 */
	private String sysCode;
	/** 应用名称 */
	private String name;
	/** 流程id */
	private String processId;
	/** 流程部署id */
	private String reProcdefId;
	/** 流程包含form */
	private String containForms;

	private ActReProcdef reProcdef;


	/** 状态 */
	private Long status;

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}
	public void setSysCode(String sysCode)
	{
		this.sysCode = sysCode;
	}

	public String getSysCode()
	{
		return sysCode;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
	public void setProcessId(String processId)
	{
		this.processId = processId;
	}

	public String getProcessId()
	{
		return processId;
	}
	public void setReProcdefId(String reProcdefId)
	{
		this.reProcdefId = reProcdefId;
	}

	public String getReProcdefId()
	{
		return reProcdefId;
	}
	public void setStatus(Long status)
	{
		this.status = status;
	}

	public Long getStatus()
	{
		return status;
	}

	public ActReProcdef getReProcdef() {
		return reProcdef;
	}

	public void setReProcdef(ActReProcdef reProcdef) {
		this.reProcdef = reProcdef;
	}

	public String getContainForms() {
		return containForms;
	}

	public void setContainForms(String containForms) {
		this.containForms = containForms;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
				.append("id", getId())
				.append("sysCode", getSysCode())
				.append("name", getName())
				.append("processId", getProcessId())
				.append("reProcdefId", getReProcdefId())
				.append("status", getStatus())
				.append("containForms", getContainForms())
				.toString();
	}
}
