package com.casic.generator.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.casic.common.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 存贮当前方案所需要的字段，是table_columns的子集表 gen_code_template_cfg_columns
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
public class GenCodeTemplateCfgColumns extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private String id;
	/** 关联列的id **/
	private String tableColumnId;
	/**  */
	private String componentId;
	/** 功能id */
	private String functionId;
	/**  */
	private String tableId;
	/**  */
	private String columnName;
	/**  */
	private String comments;
	/**  */
	private String jdbcType;
	/**  */
	private String javaType;
	/**  */
	private String javaField;
	/**  */
	private String isPk;
	/**  */
	private String isNull;
	/**  */
	private String isInsert;
	/**  */
	private String isEdit;
	/**  */
	private String isList;
	/**  */
	private String isQuery;
	/** 查询方式（等于、不等于、大于、小于、范围、左LIKE、右LIKE、左右LIKE） */
	private String queryType;
	/** 字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择） */
	private String showType;
	/**  */
	private String dictType;
	/**  */
	private String settings;
	/**  */
	private BigDecimal sort;
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
	public void setComponentId(String componentId) 
	{
		this.componentId = componentId;
	}

	public String getComponentId() 
	{
		return componentId;
	}
	public void setTableId(String tableId) 
	{
		this.tableId = tableId;
	}

	public String getTableId() 
	{
		return tableId;
	}
	public void setColumnName(String columnName) 
	{
		this.columnName = columnName;
	}

	public String getColumnName() 
	{
		return columnName;
	}
	public void setComments(String comments) 
	{
		this.comments = comments;
	}

	public String getComments() 
	{
		return comments;
	}
	public void setJdbcType(String jdbcType) 
	{
		this.jdbcType = jdbcType;
	}

	public String getJdbcType() 
	{
		return jdbcType;
	}
	public void setJavaType(String javaType) 
	{
		this.javaType = javaType;
	}

	public String getJavaType() 
	{
		return javaType;
	}
	public void setJavaField(String javaField) 
	{
		this.javaField = javaField;
	}

	public String getJavaField() 
	{
		return javaField;
	}
	public void setIsPk(String isPk) 
	{
		this.isPk = isPk;
	}

	public String getIsPk() 
	{
		return isPk;
	}
	public void setIsNull(String isNull) 
	{
		this.isNull = isNull;
	}

	public String getIsNull() 
	{
		return isNull;
	}
	public void setIsInsert(String isInsert) 
	{
		this.isInsert = isInsert;
	}

	public String getIsInsert() 
	{
		return isInsert;
	}
	public void setIsEdit(String isEdit) 
	{
		this.isEdit = isEdit;
	}

	public String getIsEdit() 
	{
		return isEdit;
	}
	public void setIsList(String isList) 
	{
		this.isList = isList;
	}

	public String getIsList() 
	{
		return isList;
	}
	public void setIsQuery(String isQuery) 
	{
		this.isQuery = isQuery;
	}

	public String getIsQuery() 
	{
		return isQuery;
	}
	public void setQueryType(String queryType) 
	{
		this.queryType = queryType;
	}

	public String getQueryType() 
	{
		return queryType;
	}
	public void setShowType(String showType) 
	{
		this.showType = showType;
	}

	public String getShowType() 
	{
		return showType;
	}
	public void setDictType(String dictType) 
	{
		this.dictType = dictType;
	}

	public String getDictType() 
	{
		return dictType;
	}
	public void setSettings(String settings) 
	{
		this.settings = settings;
	}

	public String getSettings() 
	{
		return settings;
	}
	public void setSort(BigDecimal sort) 
	{
		this.sort = sort;
	}

	public BigDecimal getSort() 
	{
		return sort;
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

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getTableColumnId() {
		return tableColumnId;
	}

	public void setTableColumnId(String tableColumnId) {
		this.tableColumnId = tableColumnId;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("tableColumnId", getTableColumnId())
            .append("componentId", getComponentId())
            .append("tableId", getTableId())
            .append("columnName", getColumnName())
            .append("comments", getComments())
            .append("jdbcType", getJdbcType())
            .append("javaType", getJavaType())
            .append("javaField", getJavaField())
            .append("isPk", getIsPk())
            .append("isNull", getIsNull())
            .append("isInsert", getIsInsert())
            .append("isEdit", getIsEdit())
            .append("isList", getIsList())
            .append("isQuery", getIsQuery())
            .append("queryType", getQueryType())
            .append("showType", getShowType())
            .append("dictType", getDictType())
            .append("settings", getSettings())
            .append("sort", getSort())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
			.append("functionId", getFunctionId())
            .toString();
    }
}
