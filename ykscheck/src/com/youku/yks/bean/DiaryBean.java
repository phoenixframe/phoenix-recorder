package com.youku.yks.bean;

import java.util.Date;

public class DiaryBean {
	private int id;
	private int userId;
	private String content;
	private Date createtime;
	
	public DiaryBean(int id, int userId, String content, Date createtime) {
		super();
		this.id = id;
		this.userId = userId;
		this.content = content;
		this.createtime = createtime;
	}
	public DiaryBean() {
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	

}
