package com.casic.common.constant;

public class Const {
//	public static final long refreshPeriodTime = 36000L; //seconds为单位,10 hours
	public static final long refreshPeriodTime = 100 * 60 * 60L; //seconds为单位,6分钟
	public static final long tokenSavedPeriodTime = refreshPeriodTime / 2; //seconds为单位,6分钟
	
	public static final int jwtKeySuccessCode = 6666; //验证通过
	public static final int jwtIssuedTokenKeyCode = 1000; //签发状态-成功/失败issued tokenKey success
	public static final int errorRequestCode = 1111; //错误的请求
	public static final int loginFailCode = 1002; //登陆失败
	
	public static final int jwtRebuildTokenCode = 1005; //过期重签发new jwt编码
	public static final int jwtExpiredCode = 1006; //jwt过期
	public static final int jwtErrorCode = 1007; //jwt错误
	public static final int jwtNoPermissionCode = 1008; //jwt 无权限访问资源
	
	public static final String jwtSignIssuer = "auth-token-server";
	public static String RESOURCE_SERVER_NAME = "oauth2-server";
	public static final String INVALID_CLIENT_DESCRIPTION = "客户端验证失败，如错误的client_id/client_secret。";
	public static final String INVALID_CODE_DESCRIPTION = "错误的授权码";



	public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
	public static final String SIGNING_KEY = "casic706";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	
	
}
