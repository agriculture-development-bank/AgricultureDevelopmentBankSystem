package com.casic.generator.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.casic.common.base.BaseEntity;
import java.util.Date;
import java.util.List;

/**
 * 可支持生成的领域配置表 gen_code_domain
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
public class GenCodeDomain extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键ID */
	private String id;
	/** 领域名称 */
	private String domainName;
	/**  */
	private String domainCode;
	/**  */
	private String delFlag;
	/**  */
	private String createBy;
	/**  */
	private Date createTime;
	/**  */
	private String updateBy;
	/**  */
	private Date updateTime;
	/**  */
	private String remark;

	private List<GenCodeComponent> componentList;

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
	public void setDomainName(String domainName) 
	{
		this.domainName = domainName;
	}

	public String getDomainName() 
	{
		return domainName;
	}
	public void setDomainCode(String domainCode) 
	{
		this.domainCode = domainCode;
	}

	public String getDomainCode() 
	{
		return domainCode;
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

	public List<GenCodeComponent> getComponentList() {
		return componentList;
	}

	public void setComponentList(List<GenCodeComponent> componentList) {
		this.componentList = componentList;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("domainName", getDomainName())
            .append("domainCode", getDomainCode())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
