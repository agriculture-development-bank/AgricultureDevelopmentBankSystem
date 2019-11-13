package com.casic.generator.domain.gen;

import java.util.List;

import com.casic.common.base.BaseEntity;
import com.casic.common.utils.StringUtils;

/**
 * 一对多或者一对一子表 数据库表
 * 
 * @author yuzengwen
 */
public class TreeTableInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 表名称 */
    private String tableName;

    /** 表描述 */
    private String tableComment;

    /** 表的主键列信息 */
    private ColumnInfo primaryKey;
    
    /** 表记录的父id */
    private ColumnInfo parentId;
    
    /** 表的列名(不包含主键) */
    private List<ColumnInfo> columns;
    
    /** 类名(第一个字母大写) */
    private String className;

    /** 类名(第一个字母小写) */
    private String classname;

	public ColumnInfo getParentId() {
		return parentId;
	}

	public void setParentId(ColumnInfo parentId) {
		this.parentId = parentId;
	}

	public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getTableComment()
    {
        return tableComment;
    }

    public void setTableComment(String tableComment)
    {
        this.tableComment = tableComment;
    }

    public List<ColumnInfo> getColumns()
    {
        return columns;
    }

    public ColumnInfo getColumnsLast()
    {
        ColumnInfo columnInfo = null;
        if (StringUtils.isNotNull(columns) && columns.size() > 0)
        {
            columnInfo = columns.get(0);
        }
        return columnInfo;
    }

    public void setColumns(List<ColumnInfo> columns)
    {
        this.columns = columns;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getClassname()
    {
        return classname;
    }

    public void setClassname(String classname)
    {
        this.classname = classname;
    }

    public ColumnInfo getPrimaryKey()
    {
        return primaryKey;
    }

    public void setPrimaryKey(ColumnInfo primaryKey)
    {
        this.primaryKey = primaryKey;
    }

}
