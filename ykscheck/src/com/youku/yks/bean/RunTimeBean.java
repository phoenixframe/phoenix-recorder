package com.youku.yks.bean;

public class RunTimeBean {
	private int userId;
	private int caseTypeId;
	
	public RunTimeBean(int userId, int caseTypeId) {
		super();
		this.userId = userId;
		this.caseTypeId = caseTypeId;
	}
	public RunTimeBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getCaseTypeId() {
		return caseTypeId;
	}
	public void setCaseTypeId(int caseTypeId) {
		this.caseTypeId = caseTypeId;
	}
	

}
