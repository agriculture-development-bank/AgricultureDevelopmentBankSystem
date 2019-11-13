package com.casic.system.domain;


import com.casic.common.annotation.Excel;

/**
 * 导入用户模板
 *
 * @author wuleichao
 * @date 2019-04-29
 */
public class SysUserTemplate {
    /**
     * 登录名称
     */
    @Excel(name = "loginName", type = Excel.Type.ALL)
    private String loginName;
    
    /**
     * 父部门名称
     */
    @Excel(name = "parentDeptName", type = Excel.Type.ALL)
    private String parentDeptName;
    
    /**
     * 部门名称
     */
    @Excel(name = "deptName", type = Excel.Type.ALL)
    private String deptName;
    
    /**
     * 用户名称
     */
    @Excel(name = "userName", type = Excel.Type.ALL)
    private String userName;
    
    /**
     * 密码
     */
    @Excel(name = "password", type = Excel.Type.ALL)
    private String password;


    /**
     * 邮箱
     */
    @Excel(name = "email", type = Excel.Type.ALL)
    private String email;


    /**
     * 手机号码
     */
    @Excel(name = "phonenumber", type = Excel.Type.ALL)
    private String phonenumber;

    /**
     * 用户性别
     */
    @Excel(name = "sex", type = Excel.Type.ALL)
    private String sex;
    
    /**
     * 用户密级 （1:非密 2:秘密 3:机密 4:绝密 5:已脱密）
     */
    @Excel(name = "secrecyLevel", type = Excel.Type.ALL)
    private String secrecyLevel;
    
    /** 
     * 用户状态（0在职，1离职，2.退休，3借调）
     */
    @Excel(name = "userStatus", type = Excel.Type.ALL)
    private String userStatus;
    
    /**
     * 身份证号
     */
    @Excel(name = "identityCard", type = Excel.Type.ALL)
    private String identityCard;

    /**
     * 入职日期
     */
    @Excel(name = "employDate", type = Excel.Type.ALL)
    private String employDate;
    
	public String getParentDeptName() {
		return parentDeptName;
	}

	public void setParentDeptName(String parentDeptName) {
		this.parentDeptName = parentDeptName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmployDate() {
		return employDate;
	}

	public void setEmployDate(String employDate) {
		this.employDate = employDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getSecrecyLevel() {
		return secrecyLevel;
	}

	public void setSecrecyLevel(String secrecyLevel) {
		this.secrecyLevel = secrecyLevel;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	@Override
	public String toString() {
		return "SysUserTemplate [loginName=" + loginName + ", parentDeptName=" + parentDeptName + ", deptName="
				+ deptName + ", userName=" + userName + ", password=" + password + ", email=" + email + ", phonenumber="
				+ phonenumber + ", sex=" + sex + ", secrecyLevel=" + secrecyLevel + ", userStatus=" + userStatus
				+ ", identityCard=" + identityCard + ", employDate=" + employDate + "]";
	}
	
   
}
