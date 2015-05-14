package com.youku.yks.bean;

import java.util.Date;

public class CaseBean {
	private int id;
	private int isDisabled;
	private int userId;
	private int typeNameId;
	private int vid;
	private int hostId;
	private int interfaceId;
	private String caseNode;
	private String remark;
	private Date createTime;
		
	public CaseBean(int id, int isDisabled, int userId, int typeNameId,
			int vid, int hostId, int interfaceId, String caseNode,
			String remark, Date createTime) {
		super();
		this.id = id;
		this.isDisabled = isDisabled;
		this.userId = userId;
		this.typeNameId = typeNameId;
		this.vid = vid;
		this.hostId = hostId;
		this.interfaceId = interfaceId;
		this.caseNode = caseNode;
		this.remark = remark;
		this.createTime = createTime;
	}

	public CaseBean() {
		super();
	}

	public int getVid() {
		return vid;
	}
	public void setVid(int vid) {
		this.vid = vid;
	}
	public int getHostId() {
		return hostId;
	}
	public void setHostId(int hostId) {
		this.hostId = hostId;
	}
	public int getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(int interfaceId) {
		this.interfaceId = interfaceId;
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
	public int getTypeNameId() {
		return typeNameId;
	}
	public void setTypeNameId(int typeNameId) {
		this.typeNameId = typeNameId;
	}
	public String getCaseNode() {
		return caseNode;
	}
	public void setCaseNode(String caseNode) {
		this.caseNode = caseNode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
