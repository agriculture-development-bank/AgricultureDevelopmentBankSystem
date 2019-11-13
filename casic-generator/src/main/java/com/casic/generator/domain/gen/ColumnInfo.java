package com.casic.generator.domain.gen;

/**
 * ry数据库表列信息
 * 
 * @author yuzengwen
 */
public class ColumnInfo
{
    /** 字段名称 */
    private String columnName;

    /** 字段类型 */
    private String dataType;

    /** 列描述 */
    private String columnComment;

    /** Java属性类型 */
    private String attrType;
    
    /** 集合 List Set Map*/
    private String collection;

    /** Java属性名称(第一个字母大写)，如：user_name => UserName */
    private String attrName;

    /** Java属性名称(第一个字母小写)，如：user_name => userName */
    private String attrname;
    
    /** 是否可插入操作 */
    private String isInsert;
    
    /** 是否可编辑操作 */
    private String isEdit;
    
    /** 是否是否需要列表显示 */
    private String isList;
    
    /** 查询方式 等于：1、不等于：2、大于：3、小于：4、范围：5、左LIKE：6、右LIKE：7、左右LIKE：8 */
    private String queryType;
    
    /** 是否为查询条件*/
    private String isQuery;
    
    /** 显示类型  字段生成方案（文本框：1、文本域：2、下拉框：3、复选框：4、单选框：5、字典选择：6、人员选择：7、部门选择：8、区域选择：9）*/
    private String showType;
    
    /** 字典类型*/
    private String dictType;
    
	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}

	public String getIsInsert() {
		return isInsert;
	}

	public void setIsInsert(String isInsert) {
		this.isInsert = isInsert;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public String getIsList() {
		return isList;
	}

	public void setIsList(String isList) {
		this.isList = isList;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public String getDataType()
    {
        return dataType;
    }

    public void setDataType(String dataType)
    {
        this.dataType = dataType;
    }

    public String getColumnComment()
    {
        return columnComment;
    }

    public void setColumnComment(String columnComment)
    {
        this.columnComment = columnComment;
    }

    public String getAttrName()
    {
        return attrName;
    }

    public void setAttrName(String attrName)
    {
        this.attrName = attrName;
    }

    public String getAttrname()
    {
        return attrname;
    }

    public void setAttrname(String attrname)
    {
        this.attrname = attrname;
    }

    public String getAttrType()
    {
        return attrType;
    }

    public void setAttrType(String attrType)
    {
        this.attrType = attrType;
    }
}
