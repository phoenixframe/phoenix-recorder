package com.youku.yks.bean;

public class MonitorBean {
	private int batchId;
	private int result;
	private int typeId;
	
	public MonitorBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MonitorBean(int batchId, int result, int typeId) {
		super();
		this.batchId = batchId;
		this.result = result;
		this.typeId = typeId;
	}
	public int getBatchId() {
		return batchId;
	}
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	

}
