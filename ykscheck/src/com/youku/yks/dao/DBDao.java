package com.youku.yks.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 数据库连接接口
 * @author mengfeiyang
 *
 */
public interface DBDao {
	
	Connection getConn();	
	void close(ResultSet set, PreparedStatement pre, Connection con);

}
