package com.casic.common.web.domain.bo;

import com.casic.common.base.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 客户端配置表 OAuth_Client_Details
 * 
 * @author yuzengwen
 * @date 2019-04-14
 */
public class OAuthClientDetails extends BaseEntity {
	private static final long serialVersionUID = 1L;
	public OAuthClientDetails(){

	}
	/** 主键 */
	private String clientId;
	/** 应用名称 */
	private String clientName;
	/** 客户端所能访问的资源ids */
	private String resourceIds;
	/** 用于指定客户端(client)的访问密钥 */
	private String clientSecret;
	/** 指定客户端申请的权限范围,可选值包括read,write,trust; */
	private String scope = "all";
	/** 指定客户端支持的grant_type,可选值包括authorization_code,password,refresh_token,implicit,client_credentials, 若支持多个grant_type用逗号 */
	private String authorizedGrantTypes = "authorization_code,refresh_token";
	/** 客户端的重定向URI,可为空, 当grant_type为authorization_code或implicit时, 在Oauth的流程中会使用并检查与注册时填写的redirect_uri是否一致.  */
	private String webServerRedirectUri;
	/** 指定客户端所拥有的Spring Security的权限值,可选 */
	private String authorities;
	/** 设定客户端的access_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 12, 12小时).  */
	private Integer accessTokenValidity;
	/** 设定客户端的refresh_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 24 * 30, 30天).  */
	private Integer refreshTokenValidity;
	/** 这是一个预留的字段,在Oauth的流程中没有实际的使用,可选,但若设置值,必须是JSON格式的数据 */
	private String additionalInformation;
	/** 设置用户是否自动Approval操作, 默认值为 'false', 可选值包括 'true','false', 'read','write'.
	 该字段只适用于grant_type="authorization_code"的情况,当用户登录成功后,若该值为'true'或支持的scope值,则会跳过用户Approve的页面, 直接授权.
	 该字段与 trusted 有类似的功能, 是 spring-security-oauth2 的 2.0 版本后添加的新属性.  */
	private String autoapprove = "true";

	//客户端主页
	private String clientIndexPage;

	//客户端icon
	private String clientIcon;

	//客户端简介
	private String clientIntroduce;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}

	public String getWebServerRedirectUri() {
		return webServerRedirectUri;
	}

	public void setWebServerRedirectUri(String webServerRedirectUri) {
		this.webServerRedirectUri = webServerRedirectUri;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	public Integer getAccessTokenValidity() {
		return accessTokenValidity;
	}

	public void setAccessTokenValidity(Integer accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}

	public Integer getRefreshTokenValidity() {
		return refreshTokenValidity;
	}

	public void setRefreshTokenValidity(Integer refreshTokenValidity) {
		this.refreshTokenValidity = refreshTokenValidity;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public String getAutoapprove() {
		return autoapprove;
	}

	public void setAutoapprove(String autoapprove) {
		this.autoapprove = autoapprove;
	}

	public String getClientIndexPage() {
		return clientIndexPage;
	}

	public void setClientIndexPage(String clientIndexPage) {
		this.clientIndexPage = clientIndexPage;
	}

	public String getClientIcon() {
		return clientIcon;
	}

	public void setClientIcon(String clientIcon) {
		this.clientIcon = clientIcon;
	}

	public String getClientIntroduce() {
		return clientIntroduce;
	}

	public void setClientIntroduce(String clientIntroduce) {
		this.clientIntroduce = clientIntroduce;
	}

}
