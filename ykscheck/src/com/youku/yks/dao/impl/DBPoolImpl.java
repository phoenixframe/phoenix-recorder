package com.youku.yks.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.youku.yks.dao.DBPoolDao;

/**
 * 创建连接池
 * @author mengfeiyang
 * 
 */
public class DBPoolImpl implements DBPoolDao{
	private Connection con = null;
	private int inUsed = 0; // 使用的连接数
	private List<Connection> freeConnections = new ArrayList<Connection>();// 容器，空闲连接
	private int minConn; // 最小连接数
	private int maxConn; // 最大连接
	private String password; // 密码
	private String url; // 数据库连接地址
	private String driver; // 驱动
	private String user; // 用户名

	public DBPoolImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 创建连接池
	 * 
	 * @param driver
	 * @param name
	 * @param URL
	 * @param user
	 * @param password
	 * @param maxConn
	 */
	public DBPoolImpl(String driver, String URL, String user, String password,
			int maxConn) {
		this.driver = driver;
		this.url = URL;
		this.user = user;
		this.password = password;
		this.maxConn = maxConn;
	}

	/**
	 * 用完，释放连接
	 * 
	 * @param con
	 */
	public synchronized void freeConnection(Connection con) {
		this.freeConnections.add(con);// 添加到空闲连接的末尾
		this.inUsed--;
	}

	/**
	 * 
	 * 从连接池里得到连接
	 * 
	 * @return
	 */
	public synchronized Connection getConnection() {
		Connection con = null;
		if (this.freeConnections.size() > 0) {
			con = (Connection) this.freeConnections.get(0);
			this.freeConnections.remove(0);// 如果连接分配出去了，就从空闲连接里删除
			if (con == null) con = getConnection(); // 继续获得连接
			//System.out.println("已从连接池中获取了连接");
		} else {
			//System.out.println("已从新创建了连接。。。");
			con = newConnection(); // 新建连接
		}
		if (this.maxConn == 0 || this.maxConn < this.inUsed) {
			con = null;//等待 超过最大连接时
		}
		if (con != null) {
			this.inUsed++;
			//System.out.println("已有 "+inUsed+" 在使用");
		}
		return con;
	}

	/**
	 * 释放全部连接
	 * 
	 */
	public synchronized void release() {
		Iterator<Connection> allConns = this.freeConnections.iterator();
		while (allConns.hasNext()) {
			Connection con = (Connection) allConns.next();
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		this.freeConnections.clear();

	}

	/**
	 * 创建新连接
	 * 
	 * @return
	 */
	public Connection newConnection() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return con;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	/**
	 * @return the driver
	 */
	public String getDriver() {
		return driver;
	}

	/**
	 * @param driver
	 *            the driver to set
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}

	/**
	 * @return the maxConn
	 */
	public int getMaxConn() {
		return maxConn;
	}

	/**
	 * @param maxConn
	 *            the maxConn to set
	 */
	public void setMaxConn(int maxConn) {
		this.maxConn = maxConn;
	}

	/**
	 * @return the minConn
	 */
	public int getMinConn() {
		return minConn;
	}

	/**
	 * @param minConn
	 *            the minConn to set
	 */
	public void setMinConn(int minConn) {
		this.minConn = minConn;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
}
