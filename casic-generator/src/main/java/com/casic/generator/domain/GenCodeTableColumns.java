package com.casic.generator.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.casic.common.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 模块对应的字段表 gen_code_table_columns
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
public class GenCodeTableColumns extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private String id;
	/**  */
	private String tableId;
	/**  */
	private String columnName;
	/**  */
	private String columnZhName;
	/**  */
	private String comments;
	/** 长度 */
	private Integer len;
	/**  */
	private String jdbcType;
	/** String Long Integer Object 以及自定义实体等 */
	private String javaType;
	/**  */
	private String javaField;
	/** 是否是外键 */
	private String isFK;
	/** 如果是外键，则外键表名必须填写 **/
	private String fkTableName;
	/** 外键名称 **/
	private String fkFieldName;

	private String isPk;
	/**  */
	private String isNull;
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
	public void setColumnZhName(String columnZhName) 
	{
		this.columnZhName = columnZhName;
	}

	public String getColumnZhName() 
	{
		return columnZhName;
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

	public Integer getLen() {
		return len;
	}

	public void setLen(Integer len) {
		this.len = len;
	}

	public String getIsFK() {
		return isFK;
	}

	public void setIsFK(String isFK) {
		this.isFK = isFK;
	}

	public String getFkTableName() {
		return fkTableName;
	}

	public void setFkTableName(String fkTableName) {
		this.fkTableName = fkTableName;
	}

	public String getFkFieldName() {
		return fkFieldName;
	}

	public void setFkFieldName(String fkFieldName) {
		this.fkFieldName = fkFieldName;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("tableId", getTableId())
            .append("columnName", getColumnName())
            .append("columnZhName", getColumnZhName())
            .append("comments", getComments())
            .append("jdbcType", getJdbcType())
            .append("javaType", getJavaType())
            .append("javaField", getJavaField())
			.append("isFK", getIsPk())
			.append("fkTableName", getFkTableName())
			.append("fkFieldName", getFkFieldName())
            .append("isPk", getIsPk())
            .append("isNull", getIsNull())
            .append("dictType", getDictType())
            .append("settings", getSettings())
            .append("sort", getSort())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
			.append("len", len)
            .toString();
    }
}
