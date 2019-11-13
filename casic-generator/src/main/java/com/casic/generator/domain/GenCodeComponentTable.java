package com.casic.generator.domain;

import com.casic.common.base.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 组件对应的数据配置表 gen_code_component_table
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
public class GenCodeComponentTable extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private String id;
	/** t_system_user */
	private String tableName;
	/**  */
	private String tableEnName;
	/**  */
	private String componentId;
	/**  */
	private String datasourceId;
	/** 表的描述 */
	private String comments;
	/** 包名 */
	private String packageName;
	/** 例如：TSystemUser 跟模块的包名结合 */
	private String className;
	/** m:主表 s:子表 r:关联表 */
	private String tableType;
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
	public void setTableName(String tableName) 
	{
		this.tableName = tableName;
	}

	public String getTableName() 
	{
		return tableName;
	}
	public void setTableEnName(String tableEnName) 
	{
		this.tableEnName = tableEnName;
	}

	public String getTableEnName() 
	{
		return tableEnName;
	}
	public void setComponentId(String componentId) 
	{
		this.componentId = componentId;
	}

	public String getComponentId() 
	{
		return componentId;
	}
	public void setDatasourceId(String datasourceId) 
	{
		this.datasourceId = datasourceId;
	}

	public String getDatasourceId() 
	{
		return datasourceId;
	}
	public void setComments(String comments) 
	{
		this.comments = comments;
	}

	public String getComments() 
	{
		return comments;
	}
	public void setClassName(String className) 
	{
		this.className = className;
	}

	public String getClassName() 
	{
		return className;
	}
	public void setTableType(String tableType) 
	{
		this.tableType = tableType;
	}

	public String getTableType() 
	{
		return tableType;
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

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("tableName", getTableName())
            .append("tableEnName", getTableEnName())
            .append("componentId", getComponentId())
            .append("datasourceId", getDatasourceId())
            .append("comments", getComments())
			.append("packageName", getPackageName())
            .append("className", getClassName())
            .append("tableType", getTableType())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
