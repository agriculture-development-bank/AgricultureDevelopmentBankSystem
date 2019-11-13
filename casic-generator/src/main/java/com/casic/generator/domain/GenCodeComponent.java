package com.casic.generator.domain;

import com.casic.common.web.domain.bo.OAuthClientDetails;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.casic.common.base.BaseEntity;
import java.util.Date;

/**
 * 组件配置表 gen_code_component
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
public class GenCodeComponent extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键ID */
	private String id;
	/** 子系统名称 */
	private String conmponentName;
	/**  */
	private String conmponentCode;
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

	/** oauth_client_details 关联键 */
	private String clientId;

	private OAuthClientDetails clientDetails;

	private String datasourceId;

	private CodeDatasource datasource;

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
	public void setConmponentName(String conmponentName) 
	{
		this.conmponentName = conmponentName;
	}

	public String getConmponentName() 
	{
		return conmponentName;
	}
	public void setConmponentCode(String conmponentCode) 
	{
		this.conmponentCode = conmponentCode;
	}

	public String getConmponentCode() 
	{
		return conmponentCode;
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

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public OAuthClientDetails getClientDetails() {
		return clientDetails;
	}

	public void setClientDetails(OAuthClientDetails clientDetails) {
		this.clientDetails = clientDetails;
	}

	public String getDatasourceId() {
		return datasourceId;
	}

	public void setDatasourceId(String datasourceId) {
		this.datasourceId = datasourceId;
	}

	public CodeDatasource getDatasource() {
		return datasource;
	}

	public void setDatasource(CodeDatasource datasource) {
		this.datasource = datasource;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("conmponentName", getConmponentName())
            .append("conmponentCode", getConmponentCode())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
			.append("clientId", getClientId())
			.append("datasourceId", getDatasourceId())
            .toString();
    }
}
