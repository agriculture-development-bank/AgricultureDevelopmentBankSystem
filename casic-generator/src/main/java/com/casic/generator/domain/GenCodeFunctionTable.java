package com.casic.generator.domain;

import com.casic.common.base.BaseEntity;

/**
 * 功能与组件表的中间表实体类
 */
public class GenCodeFunctionTable extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String id;

    private String functionId;

    private String tableId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
}
