package com.youku.yks.controller;

import java.sql.Connection;

import com.youku.yks.bean.DBConfigBean;
import com.youku.yks.dao.impl.DBPoolImpl;
import com.youku.yks.tools.GlobalParameter;

/**
 * 连接池管理器
 * @author mengfeiyang
 * 
 */
public class DBConnectionManager {
	static private DBConnectionManager instance;// 唯一数据库连接池管理实例类
	private DBPoolImpl dbPool = new DBPoolImpl();// 连接池

	/**
	 * 实例化管理类
	 */
	public DBConnectionManager() {
		createPool(GlobalParameter.dbconfig);
	}

	/**
	 * 得到唯一实例管理类
	 * 
	 * @return
	 */
	static synchronized public DBConnectionManager getInstance() {
		if (instance == null) {
			instance = new DBConnectionManager();
		}
		return instance;

	}

	/**
	 * 释放连接
	 * 
	 * @param name
	 * @param con
	 */
	public void freeConnection(Connection con) {
			dbPool.freeConnection(con);// 释放连接
	}

	/**
	 * 得到一个连接根据连接池的名字name
	 * 
	 * @param name
	 * @return
	 */
	public Connection getConnection() {
		return dbPool.getConnection();
	}

	/**
	 * 释放所有连接
	 */
	public synchronized void release() {

		dbPool.release();
	}

	/**
	 * 创建连接池
	 * 
	 * @param props
	 */
	private void createPool(DBConfigBean dbConfig) {
		dbPool.setDriver(dbConfig.getDriver());
		dbPool.setUrl(dbConfig.getUrl());
		dbPool.setUser(dbConfig.getUsername());
		dbPool.setPassword(dbConfig.getPassword());
		dbPool.setMaxConn(dbConfig.getMaxconn());
	}
}
