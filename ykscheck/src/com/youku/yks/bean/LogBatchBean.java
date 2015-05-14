package com.youku.yks.bean;

import java.util.Date;

public class LogBatchBean {
	private int id;
	private int userId;
	private String batchString;
	private Date createTime;
	
	public LogBatchBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LogBatchBean(int id, int userId, String batchString, Date createTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.batchString = batchString;
		this.createTime = createTime;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBatchString() {
		return batchString;
	}
	public void setBatchString(String batchString) {
		this.batchString = batchString;
	}
	

}
