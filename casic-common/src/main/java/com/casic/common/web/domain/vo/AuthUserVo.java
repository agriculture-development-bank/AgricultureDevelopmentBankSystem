package com.casic.common.web.domain.vo;

import com.casic.common.web.domain.bo.AuthUser;

public class AuthUserVo extends AuthUser {
    private String deptName;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
