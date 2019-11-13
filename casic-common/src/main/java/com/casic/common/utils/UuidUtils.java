package com.casic.common.utils;

import java.util.UUID;

public class UuidUtils {
	
	public static String getUUIDString() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		return uuid;
	}
	
	public static String getFullUUIDString() {
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}
	
	public static void main(String[] args) {
		System.out.println(getUUIDString());
	}
}
