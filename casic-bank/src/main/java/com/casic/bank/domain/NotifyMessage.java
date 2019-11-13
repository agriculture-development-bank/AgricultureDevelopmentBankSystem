package com.casic.bank.domain;

import java.io.Serializable;

;

public class NotifyMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 857921206177322059L;


	private String id;
	
	private String message;
	
	private String fieldId;

	private String userId;

	private String isRead;

	private String readStr;
	

	private String createDate;

	private String readTime;
	private String deptId;
	private String handlerecord;
	private String capuserecordId;
	private String deviceIp;
	private String useName;

	public String getUseName() {
		return useName;
	}

	public void setUseName(String useName) {
		this.useName = useName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	private String productId;

	public String getHandlerecord() {
		return handlerecord;
	}

	public void setHandlerecord(String handlerecord) {
		this.handlerecord = handlerecord;
	}

	public String getCapuserecordId() {
		return capuserecordId;
	}

	public void setCapuserecordId(String capuserecordId) {
		this.capuserecordId = capuserecordId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public String getReadStr() {
		return readStr;
	}

	public void setReadStr(String readStr) {
		this.readStr = readStr;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getReadTime() {
		return readTime;
	}

	public void setReadTime() {
		this.setReadTime(getReadTime());
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}

	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}
}
