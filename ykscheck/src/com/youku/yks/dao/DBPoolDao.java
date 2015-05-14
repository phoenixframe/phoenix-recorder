package com.youku.yks.dao;

import java.sql.Connection;

/**
 * 连接池创建接口
 * @author mengfeiyang
 *
 */
public interface DBPoolDao {
	/**
	 * 释放连接
	 * @param con
	 */
	void freeConnection(Connection con);
	
	/**
	 * 获取和创建连接
	 * @return
	 */
	Connection getConnection();
	
	/**
	 * 释放所有连接
	 */
	void release();
	
	/**
	 * 新创建连接池
	 * @return
	 */
	Connection newConnection();
	

}
