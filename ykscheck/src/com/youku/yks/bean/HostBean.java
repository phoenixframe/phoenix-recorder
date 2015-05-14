package com.youku.yks.bean;

/**
 * 对Host环境操作的Bean，用于快速切换测试环境
 * @author mengfeiyang
 *
 */
public class HostBean {
	private int id;
	private int userId;
	private String hostName;
	private String remark;
	
	public HostBean(int id, int userId, String hostName, String remark) {
		super();
		this.id = id;
		this.userId = userId;
		this.hostName = hostName;
		this.remark = remark;
	}
	public HostBean() {
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
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
