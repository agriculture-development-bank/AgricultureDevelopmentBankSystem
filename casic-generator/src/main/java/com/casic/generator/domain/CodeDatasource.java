package com.casic.generator.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.casic.common.base.BaseEntity;
import java.util.Date;

/**
 * 数据源管理表 gen_code_datasource
 * 
 * @author fangzhi
 * @date 2019-04-03
 */
public class CodeDatasource extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键ID */
	private String id;
	/** 数据源名 */
	private String sourceName;
	/** 数据源类型 Mysql Oracle MsSQL */
	private Integer sourceType;
	/** jdbc类名 */
	private String sourceClass;
	/** 链接url */
	private String sourceUrl;
	/** 数据库名称  */
	private String databaseName;
	/** 用户名 */
	private String sourceUser;
	/** 密码 */
	private String sourcePass;
	/** 数据源描述 */
	private String sourceRemark;
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

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getId() 
	{
		return id;
	}
	public void setSourceName(String sourceName) 
	{
		this.sourceName = sourceName;
	}

	public String getSourceName() 
	{
		return sourceName;
	}
	public void setSourceType(Integer sourceType) 
	{
		this.sourceType = sourceType;
	}

	public Integer getSourceType() 
	{
		return sourceType;
	}
	public void setSourceClass(String sourceClass) 
	{
		this.sourceClass = sourceClass;
	}

	public String getSourceClass() 
	{
		return sourceClass;
	}
	public void setSourceUrl(String sourceUrl) 
	{
		this.sourceUrl = sourceUrl;
	}

	public String getSourceUrl() 
	{
		return sourceUrl;
	}
	public void setSourceUser(String sourceUser) 
	{
		this.sourceUser = sourceUser;
	}

	public String getSourceUser() 
	{
		return sourceUser;
	}
	public void setSourcePass(String sourcePass) 
	{
		this.sourcePass = sourcePass;
	}

	public String getSourcePass() 
	{
		return sourcePass;
	}
	public void setSourceRemark(String sourceRemark) 
	{
		this.sourceRemark = sourceRemark;
	}

	public String getSourceRemark() 
	{
		return sourceRemark;
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

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sourceName", getSourceName())
            .append("sourceType", getSourceType())
            .append("sourceClass", getSourceClass())
            .append("sourceUrl", getSourceUrl())
            .append("sourceUser", getSourceUser())
            .append("sourcePass", getSourcePass())
            .append("sourceRemark", getSourceRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
			.append("databaseName", getDatabaseName())
            .toString();
    }
}
