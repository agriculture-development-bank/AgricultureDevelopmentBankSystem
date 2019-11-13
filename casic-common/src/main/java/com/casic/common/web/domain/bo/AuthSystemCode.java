package com.casic.common.web.domain.bo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.casic.common.base.BaseEntity;

import java.util.Date;

/**
 * 参数配置表 auth_system_code
 * 
 * @author yuzengwen
 * @date 2018-10-26
 */
public class AuthSystemCode extends BaseEntity {
	private static final long serialVersionUID = 1L;
	public AuthSystemCode(){

	}
	/** 主键 */
	private Integer id;
	/** 系统名称 */
	private String sysName;
	/** 系统编码 */
	private String sysCode;
	/** 创建者 */
	private String createBy;
	/** 创建时间 */
	private Date createTime;
	/** 更新者 */
	private String updateBy;
	/** 更新时间 */
	private Date updateTime;
	/** 备注 */
	private String remark;

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setSysName(String sysName) 
	{
		this.sysName = sysName;
	}

	public String getSysName() 
	{
		return sysName;
	}
	public void setSysCode(String sysCode) 
	{
		this.sysCode = sysCode;
	}

	public String getSysCode() 
	{
		return sysCode;
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
            .append("sysName", getSysName())
            .append("sysCode", getSysCode())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
    
    public static final String ALL_SYSTEM_CODE = "ALL";
	public static final String ALL_SYSTEM_NAME = "全局";
    
}
