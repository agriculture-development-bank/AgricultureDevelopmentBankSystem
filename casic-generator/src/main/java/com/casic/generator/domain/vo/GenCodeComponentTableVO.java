package com.casic.generator.domain.vo;

import com.casic.generator.domain.GenCodeComponentTable;

/**
 * 数据库表设计-列表视图对象
 */
public class GenCodeComponentTableVO extends GenCodeComponentTable {

    /**
     * 组件名称
     */
    private String componentName;

    /**
     * 表类型名称
     */
    private String tableTypeName;

    /**
     * 数据源名称
     */
    private String dataSourceName;
    /**
     * 所所属领域
     */
    private String domainName;

    /**
     * 领域编码
     */
    private String domainCode;

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getTableTypeName() {
        return tableTypeName;
    }

    public void setTableTypeName(String tableTypeName) {
        this.tableTypeName = tableTypeName;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainCode() {
        return domainCode;
    }

    public void setDomainCode(String domainCode) {
        this.domainCode = domainCode;
    }
}
