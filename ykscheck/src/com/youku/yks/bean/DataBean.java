package com.youku.yks.bean;

public class DataBean {
	private int id;
	private int userId;
	private int paramNodeId;	
	private String requestType;
	private String jsonPath;
	private String expect;
	private String remark;
	
	public DataBean(int id, int userId, int paramNodeId, String requestType,
			String jsonPath, String expect, String remark) {
		super();
		this.id = id;
		this.userId = userId;
		this.paramNodeId = paramNodeId;
		this.requestType = requestType;
		this.jsonPath = jsonPath;
		this.expect = expect;
		this.remark = remark;
	}
	public DataBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getParamNodeId() {
		return paramNodeId;
	}
	public void setParamNodeId(int paramNodeId) {
		this.paramNodeId = paramNodeId;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getJsonPath() {
		return jsonPath;
	}
	public void setJsonPath(String jsonPath) {
		this.jsonPath = jsonPath;
	}
	public String getExpect() {
		return expect;
	}
	public void setExpect(String expect) {
		this.expect = expect;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
