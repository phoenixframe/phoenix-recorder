package com.youku.yks.bean;

/**
 * 用户信息表
 * @author mengfeiyang
 *
 */
public class UserBean {
	private int id;
	private String name;
	private String password;
	private int level;
	private String remark;
	
	public UserBean(int id, String name, String password, int level,
			String remark) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.level = level;
		this.remark = remark;
	}
	public UserBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
