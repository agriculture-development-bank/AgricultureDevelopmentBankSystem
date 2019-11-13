package com.casic.common.web.domain.vo;

import java.util.Date;
import java.util.List;

public class GenCodeDomainVo {
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

}
