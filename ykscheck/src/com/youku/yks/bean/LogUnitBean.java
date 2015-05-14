package com.youku.yks.bean;

public class LogUnitBean {
	private int id;
	private int batchId;
	private int vid;
	private int result;
	private int typeId;
	private int caseNodeId;
	private int paramNodeId;
	private String url;
	private String remark;
	
	public LogUnitBean(int id, int batchId, int vid, int result,
			int typeId, int caseNodeId, int paramNodeId,String url, 
			String remark) {
		super();
		this.id = id;
		this.batchId = batchId;
		this.vid = vid;
		this.result = result;
		this.typeId = typeId;
		this.caseNodeId = caseNodeId;
		this.paramNodeId = paramNodeId;
		this.url = url;
		this.remark = remark;
	}
	public LogUnitBean() {
		super();
		// TODO Auto-generated constructor stub
	}	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public int getCaseNodeId() {
		return caseNodeId;
	}
	public void setCaseNodeId(int caseNodeId) {
		this.caseNodeId = caseNodeId;
	}
	public int getParamNodeId() {
		return paramNodeId;
	}
	public void setParamNodeId(int paramNodeId) {
		this.paramNodeId = paramNodeId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBatchId() {
		return batchId;
	}
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}
	public int getVid() {
		return vid;
	}
	public void setVid(int vid) {
		this.vid = vid;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
