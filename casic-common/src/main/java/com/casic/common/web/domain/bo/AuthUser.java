package com.casic.common.web.domain.bo;

import java.io.Serializable;
import java.util.Date;

public class AuthUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private String uid;

    private String username;

    private String password;

    private String salt;

    private String realName;

    private String avatar;

    private String phone;

    private String email;

    private Integer sex;

    /**
     * (1.正常 2.锁定 3.删除 4.非法)
     */
    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String createWhere;
    
    private String sysCode;

    private String[] authRoleIds;

    private String userCareer;

    private String sysUserId;

    public AuthUser(){

    }
    public AuthUser(String username){
        this.username = username;
    }
    
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateWhere() {
        return createWhere;
    }

    public void setCreateWhere(String createWhere) {
        this.createWhere = createWhere;
    }

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String[] getAuthRoleIds() {
		return authRoleIds;
	}

	public void setAuthRoleIds(String[] authRoleIds) {
		this.authRoleIds = authRoleIds;
	}


    public boolean isAdmin()
    {
        return isAdmin(this.uid);
    }

    public static boolean isAdmin(String userId)
    {
        return userId != null && "1".equals(userId);
    }

    public String getUserCareer() {
        return userCareer;
    }

    public void setUserCareer(String userCareer) {
        this.userCareer = userCareer;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }
}