package com.casic.generator.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.casic.common.base.BaseEntity;
import java.util.Date;

/**
 * 描述代码生成功能的配置表 gen_code_function
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
public class GenCodeFunction extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键ID */
	private String id;
	/**  */
	private String funcName;
	/**  */
	private String funcEnName;
	/** 字典类型
            1:curd 单表增删改查
            2:mcurd 父子表增删改查
            3:mmcurd 一父多子增删该查 */
	private String categoryId;
	/**  */
	private String funcMenuId;
	/**  */
	private String packageName;
	/** 主要生成java类中的注释 */
	private String funcSimpleName;
	/** 主要生成java类中的作者注释 */
	private String funcAuthor;
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
	public void setFuncName(String funcName) 
	{
		this.funcName = funcName;
	}

	public String getFuncName() 
	{
		return funcName;
	}
	public void setFuncEnName(String funcEnName) 
	{
		this.funcEnName = funcEnName;
	}

	public String getFuncEnName() 
	{
		return funcEnName;
	}
	public void setCategoryId(String categoryId) 
	{
		this.categoryId = categoryId;
	}

	public String getCategoryId() 
	{
		return categoryId;
	}
	public void setFuncMenuId(String funcMenuId) 
	{
		this.funcMenuId = funcMenuId;
	}

	public String getFuncMenuId() 
	{
		return funcMenuId;
	}
	public void setPackageName(String packageName) 
	{
		this.packageName = packageName;
	}

	public String getPackageName() 
	{
		return packageName;
	}
	public void setFuncSimpleName(String funcSimpleName) 
	{
		this.funcSimpleName = funcSimpleName;
	}

	public String getFuncSimpleName() 
	{
		return funcSimpleName;
	}
	public void setFuncAuthor(String funcAuthor) 
	{
		this.funcAuthor = funcAuthor;
	}

	public String getFuncAuthor() 
	{
		return funcAuthor;
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
            .append("funcName", getFuncName())
            .append("funcEnName", getFuncEnName())
            .append("categoryId", getCategoryId())
            .append("funcMenuId", getFuncMenuId())
            .append("packageName", getPackageName())
            .append("funcSimpleName", getFuncSimpleName())
            .append("funcAuthor", getFuncAuthor())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
