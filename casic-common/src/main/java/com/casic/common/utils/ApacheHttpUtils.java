/******************************************************************************
 *                                                                             
 *                      Woodare PROPRIETARY INFORMATION                        
 *                                                                             
 *          The information contained herein is proprietary to Woodare         
 *           and shall not be reproduced or disclosed in whole or in part      
 *                    or used for any design or manufacture                    
 *              without direct written authorization from FengDa.              
 *                                                                             
 *            Copyright (c) 2013 by Woodare.  All rights reserved.             
 *                                                                             
 *****************************************************************************/
package com.casic.common.utils;

import java.util.*;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;


/**
 * ClassName: ApacheHttpUtils
 * @description
 * @author yuzengwen
 * @Date Feb 15, 2016
 */
public class ApacheHttpUtils {

	public static String postJson(String url, String jsonData) throws Exception {
		CloseableHttpClient httpclient = HttpClients.custom().build();
		HttpPost post = null;
		String resData = null;
		CloseableHttpResponse result = null;
		try {
			post = new HttpPost(url);
			post.setConfig(RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(60000).build());
			post.setHeader("Content-Type", "application/json");
			if (jsonData != null) {
				HttpEntity entity2 = new StringEntity(jsonData, Consts.UTF_8);
				post.setEntity(entity2);
			}
			result = httpclient.execute(post);
			if (HttpStatus.SC_OK == result.getStatusLine().getStatusCode()) {
				resData = EntityUtils.toString(result.getEntity());
			}
		} finally {
			if (result != null) {
				result.close();
			}
			if (post != null) {
				post.releaseConnection();
			}
			httpclient.close();
		}
		return resData;
	}

	public static String postForm(String url, Map<String,String> params) throws Exception {
		CloseableHttpClient httpclient = HttpClients.custom().build();
		HttpPost post = null;
		String resData = null;
		CloseableHttpResponse result = null;
		try {
			post = new HttpPost(url);
			post.setConfig(RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000).build());
			post.setHeader("Content-Type", "application/x-www-form-urlencoded");
			Set<String> keySet = params.keySet();
			List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
			for(String key : keySet) {
				BasicNameValuePair basicNameValuePair = new BasicNameValuePair(key, params.get(key));
				nvps.add(basicNameValuePair);
			}
			post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			result = httpclient.execute(post);
			if (HttpStatus.SC_OK == result.getStatusLine().getStatusCode()) {
				resData = EntityUtils.toString(result.getEntity());
			}
		} finally {
			if (result != null) {
				result.close();
			}
			if (post != null) {
				post.releaseConnection();
			}
			httpclient.close();
		}
		return resData;
	}
	
	public static String get(String url) throws Exception {
		CloseableHttpClient httpclient = HttpClients.custom().build();
		HttpGet get = null;
		String resData = null;
		CloseableHttpResponse result = null;
		try {
			get = new HttpGet(url);
			get.setConfig(RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000).build());
			result = httpclient.execute(get);
			if (HttpStatus.SC_OK == result.getStatusLine().getStatusCode()) {
				resData = EntityUtils.toString(result.getEntity(),"UTF-8");
			}
		} finally {
			if (result != null) {
				result.close();
			}
			if (get != null) {
				get.releaseConnection();
			}
			httpclient.close();
		}
		return resData;
	}
	
	public static void main(String[] args) {
		/*
		String infAddress = "http://127.0.0.1:8080/open_serv_handle/process/v1/qhLegalXzxf";
		Map<String,String> params = new HashMap<String,String>();
		params.put("cf_xdr_mc", "青海宜化化工有限责任公司");
		
		params.put("username", "gz_gsj");
		params.put("password", "123456");
		params.put("appkey", "24dd58f3-f996-4b85-aa5f-9df80bdcc3af");
		
		try {
			String post = ApacheHttpUtils.postForm(infAddress, params);
			System.out.println(post);
			Integer i = 0;
			Integer j = 0;
			System.out.println(i == j);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		
		JSONObject jsonData = new JSONObject();
		try {
			if(jsonData.isEmpty()){
//				jsonData.put("tokenKey", "GetTokenKey");
//				jsonData.put("username",  "admin");
//				String tokenData = ApacheHttpUtils.postJson("http://127.0.0.1:10010/auth-token/account/GetTokenKey", jsonData.toJSONString());
//
//				System.out.println(tokenData);
//				//{"data":{"tokenKey":"zljnoz9i3ikeqvwc","userKey":"9YF3OO"},"meta":{"msg":"issued tokenKey success","code":1000,"success":true,"timestamp":1540197565918}}
//				JSONObject tokenObj = JSONObject.parseObject(tokenData);
//				JSONObject dataObj = tokenObj.getJSONObject("data");
//				String tokenKey = dataObj.getString("tokenKey");
//				String userKey = dataObj.getString("userKey");
//				
//				params.put("username", "admin");
//				params.put("password", AESUtil.aesEncode("111111",tokenKey));
//				params.put("timestamp", System.currentTimeMillis()+"");
//				params.put("userKey", userKey);
//				params.put("methodName", "login");
//				String postJson = ApacheHttpUtils.postJson("http://127.0.0.1:10010/api-token/account/login", JSON.toJSONString(params));
//				System.out.println(postJson);
			}
				
			
//			String infAddress = "http://127.0.0.1:10010/west-api/test/sayHi";
//			String jwt = "eyJhbGciOiJIUzUxMiIsInppcCI6IkRFRiJ9.eNokjNsOgjAQRP9ln2nSK235GbOwi6IChrbGxPjvtuFpkpkz5wv3vMAARBQnVk7YaQzComcRvPSi94FJkyQdJXSQylhh7mc3GWO1mr0djY0eHZPR_axCQIkVXFKqIJZ8E3l_8CYSH28-2oIZBuWs1j5WRwf8eZ2FdWdx7E9u75aXa-GU661UwYYrNyutywa_PwAAAP__.tNdhmtF9LpCA4sqZitNtj6VK0R_1tlP0vZH4y-avqbblCSfTmzoUxF-TWb6TmvTMiyKlbjJ9UMNWl1w0_bkdfw";

//			params.clear();
//			params.put("authorization", jwt);
//			String postJson = ApacheHttpUtils.postJson(infAddress, JSON.toJSONString(params));
//			System.out.println(postJson);
//			Thread.sleep(1 * 1000);
		
//			infAddress = "http://127.0.0.1:10010/west-api/resource/getCurrSysResources?sysCode=02";
//			params.clear();
//			params.put("authorization", jwt);
//			String postJson = ApacheHttpUtils.postJson(infAddress, JSON.toJSONString(params));
//			System.out.println(postJson);
//			Thread.sleep(1 * 1000);

			String miyao = "fdac10d2-073c-406b-b301-f19fc0e846b5";
			String uuidString = UuidUtils.getUUIDString();
			String infAddress = "http://172.18.22.181:7081/appointment/openappoint?uuid="+uuidString;
			Map<String,String> params = new HashMap<String,String>();
			params.put("projid", "YCT000001");
			//chkval = MD5 (projid + 密钥)
			String chkval = Md5Utils.hash(params.get("projid") + miyao);
			params.put("chkval", chkval);
			params.put("corpname", "杭州东恒彩印有限公司");
			params.put("corpaddr", "杭州市下城区石祥路59-12号102室");
			params.put("legalresp", "陈建新");
			params.put("gsPlatType", "0");
			params.put("gstype", "5");

			try {
				String post = ApacheHttpUtils.postForm(infAddress, params);
				System.out.println(post);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	
}


