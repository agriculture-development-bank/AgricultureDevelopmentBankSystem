package com.casic.common.web.domain.bo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.casic.common.base.BaseEntity;

import java.util.Date;

/**
 * 用户Jwt相关表 auth_user_token
 * 
 * @author yuzengwen
 * @date 2018-12-06
 */
public class AuthUserToken extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	public AuthUserToken(){

	}
	
	/** 主键 */
	private Integer id;
	/** 用户UID */
	private String userId;
	/** jwtString */
	private String jwt;
	/** 创建时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
	/** token_key */
	private String tokenKey;
	/** user_key */
	private String userKey;
	/** ip */
	private String ip;

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getUserId() 
	{
		return userId;
	}
	public void setJwt(String jwt) 
	{
		this.jwt = jwt;
	}

	public String getJwt() 
	{
		return jwt;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}
	public void setUpdateTime(Date updateTime) 
	{
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() 
	{
		return updateTime;
	}
	public void setTokenKey(String tokenKey) 
	{
		this.tokenKey = tokenKey;
	}

	public String getTokenKey() 
	{
		return tokenKey;
	}
	public void setUserKey(String userKey) 
	{
		this.userKey = userKey;
	}

	public String getUserKey() 
	{
		return userKey;
	}
	public void setIp(String ip) 
	{
		this.ip = ip;
	}

	public String getIp() 
	{
		return ip;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("jwt", getJwt())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("tokenKey", getTokenKey())
            .append("userKey", getUserKey())
            .append("ip", getIp())
            .toString();
    }
}
