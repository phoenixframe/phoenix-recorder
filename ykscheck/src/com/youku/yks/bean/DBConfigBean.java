package com.youku.yks.bean;

/**
 * 数据库信息bean
 * @author mengfeiyang
 *
 */
public class DBConfigBean {
	private String driver;
	private String url;
	private String username;
	private String password;
	private int maxconn;
	public DBConfigBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DBConfigBean(String driver, String url, String username,
			String password, int maxconn) {
		super();
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
		this.maxconn = maxconn;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getMaxconn() {
		return maxconn;
	}
	public void setMaxconn(int maxconn) {
		this.maxconn = maxconn;
	}
	
}
