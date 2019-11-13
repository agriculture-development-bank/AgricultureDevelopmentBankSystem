package com.casic.common.utils;

import com.alibaba.fastjson.JSONObject;

public class MsgUtils {
	
	/**
	 * 获得消息编码
	 * @param mesage
	 * @return
	 */
	public static Integer getMsgCode(String message) {
		JSONObject jsonMsg = JSONObject.parseObject(message);
		JSONObject jsonObject = jsonMsg.getJSONObject("meta");
		Integer code = jsonObject.getInteger("code");
		return code;
	}
	
	/**
	 * 获得消息内容
	 * @param mesage
	 * @return
	 */
	public static String getMsgContent(String message) {
		JSONObject jsonMsg = JSONObject.parseObject(message);
		JSONObject jsonObject = jsonMsg.getJSONObject("meta");
		String msg = jsonObject.getString("msg");
		return msg;
	}
	
	/**
	 * 获得消息数据
	 * @param mesage
	 * @return
	 */
	public static JSONObject getMsgData(String message) {
		JSONObject jsonMsg = JSONObject.parseObject(message);
		JSONObject jsonObject = jsonMsg.getJSONObject("data");
		return jsonObject;
	}
	
	/**
	 * 是否调用成功
	 * @param mesage
	 * @return
	 */
	public static Boolean isSuccess(String message) {
		JSONObject jsonMsg = JSONObject.parseObject(message);
		JSONObject jsonObject = jsonMsg.getJSONObject("meta");
		Boolean isSuccess = jsonObject.getBoolean("success");
		return isSuccess;
	}
	
}
