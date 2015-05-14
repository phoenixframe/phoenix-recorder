package com.youku.yks.bean;

public class VidBean {
	private int id;
	private int userId;
	private String vid;
	private String vidName;
	private String remark;	
		
	public VidBean(int id, int userId, String vid, String vidName, String remark) {
		super();
		this.id = id;
		this.userId = userId;
		this.vid = vid;
		this.vidName = vidName;
		this.remark = remark;
	}

	public VidBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getVidName() {
		return vidName;
	}
	public void setVidName(String vidName) {
		this.vidName = vidName;
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
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
