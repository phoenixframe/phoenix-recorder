package com.youku.yks.bean;

import java.util.Date;

public class StatusBean {
	private int id;
	private int batchId;
	private int caseTypeId;
	private int caseId;
	private int s_rate;
	private int f_rate;
	private Date updatetime;

	public StatusBean() {
		// TODO Auto-generated constructor stub
	}
	
	
	public StatusBean(int id, int batchId, int caseTypeId, int caseId,
			int s_rate, int f_rate, Date updatetime) {
		super();
		this.id = id;
		this.batchId = batchId;
		this.caseTypeId = caseTypeId;
		this.caseId = caseId;
		this.s_rate = s_rate;
		this.f_rate = f_rate;
		this.updatetime = updatetime;
	}


	public StatusBean(int batchId, int caseTypeId, int caseId,
			int s_rate, int f_rate) {
		super();
		this.batchId = batchId;
		this.caseTypeId = caseTypeId;
		this.caseId = caseId;
		this.s_rate = s_rate;
		this.f_rate = f_rate;
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

	public int getCaseTypeId() {
		return caseTypeId;
	}

	public void setCaseTypeId(int caseTypeId) {
		this.caseTypeId = caseTypeId;
	}

	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}

	public int getS_rate() {
		return s_rate;
	}

	public void setS_rate(int s_rate) {
		this.s_rate = s_rate;
	}

	public int getF_rate() {
		return f_rate;
	}

	public void setF_rate(int f_rate) {
		this.f_rate = f_rate;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	

}
