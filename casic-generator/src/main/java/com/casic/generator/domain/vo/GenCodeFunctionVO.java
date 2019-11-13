package com.casic.generator.domain.vo;

import com.casic.generator.domain.GenCodeFunction;

import java.util.List;

public class GenCodeFunctionVO extends GenCodeFunction {

    /**
     * 组件名称
     */
    private String componentName;
    /**
     * 包含数据源
     */
    private List<String> tableIds;

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public List<String> getTableIds() {
        return tableIds;
    }

    public void setTableIds(List<String> tableIds) {
        this.tableIds = tableIds;
    }
}
