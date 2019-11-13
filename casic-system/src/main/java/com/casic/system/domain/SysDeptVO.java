package com.casic.system.domain;

import java.util.ArrayList;
import java.util.List;


public class SysDeptVO {

    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 父部门ID
     */
    private String parentId;

    /**
     * 部门名称
     */
    private String deptName;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    private List<SysDeptVO> childDept = new ArrayList<>();

    public List<SysDeptVO> getChildDept() {
        return childDept;
    }

    public void setChildDept(List<SysDeptVO> childDept) {
        this.childDept = childDept;
    }
}
