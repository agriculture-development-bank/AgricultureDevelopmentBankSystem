package org.casic.workflow.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.casic.common.base.BaseEntity;
import java.util.Date;

/**
 * 操作实体表 flow_business_object
 * 
 * @author yuzengwen
 * @date 2019-04-10
 */
public class FlowBusinessObject extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private String id;
	/** 名称 */
	private String name;
	/** 编码 */
	private String code;
	/** 设备sn号码 */
	private String sn;
	/** 设备价格 */
	private Double amount;
	/** 入库时间 */
	private Date inputDate;
	/** 出库时间 */
	private Date outputDate;
	/** 删除标记 */
	private String delFlag;
	/**  */
	private String processInstanceId;
	/**  */
	private String processDefinitionId;

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}
	public void setCode(String code) 
	{
		this.code = code;
	}

	public String getCode() 
	{
		return code;
	}
	public void setSn(String sn) 
	{
		this.sn = sn;
	}

	public String getSn() 
	{
		return sn;
	}
	public void setAmount(Double amount) 
	{
		this.amount = amount;
	}

	public Double getAmount() 
	{
		return amount;
	}
	public void setInputDate(Date inputDate) 
	{
		this.inputDate = inputDate;
	}

	public Date getInputDate() 
	{
		return inputDate;
	}
	public void setOutputDate(Date outputDate) 
	{
		this.outputDate = outputDate;
	}

	public Date getOutputDate() 
	{
		return outputDate;
	}
	public void setDelFlag(String delFlag) 
	{
		this.delFlag = delFlag;
	}

	public String getDelFlag() 
	{
		return delFlag;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("code", getCode())
            .append("sn", getSn())
            .append("amount", getAmount())
            .append("inputDate", getInputDate())
            .append("outputDate", getOutputDate())
            .toString();
    }
}
