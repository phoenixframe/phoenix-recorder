package com.youku.yks.bean;

public class NodeBean {
	private int id;
	private int isDisabled;
	private int userId;
	private int caseNodeId;
	private String paramNode;
	private String remark;
		
	
	public NodeBean(int id, int isDisabled, int userId, int caseNodeId, String paramNode,
			String remark) {
		super();
		this.id = id;
		this.isDisabled = isDisabled;
		this.userId = userId;
		this.caseNodeId = caseNodeId;
		this.paramNode = paramNode;
		this.remark = remark;
	}
	public NodeBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIsDisabled() {
		return isDisabled;
	}
	public void setIsDisabled(int isDisabled) {
		this.isDisabled = isDisabled;
	}
	public int getCaseNodeId() {
		return caseNodeId;
	}
	public void setCaseNodeId(int caseNodeId) {
		this.caseNodeId = caseNodeId;
	}
	public String getParamNode() {
		return paramNode;
	}
	public void setParamNode(String paramNode) {
		this.paramNode = paramNode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
