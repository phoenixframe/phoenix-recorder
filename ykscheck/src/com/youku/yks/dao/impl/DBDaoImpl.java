package com.youku.yks.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import com.youku.yks.controller.DBConnectionManager;
import com.youku.yks.dao.DBDao;

/**
 * 对数据库进行操作的接口实现类
 * @author mengfeiyang
 *
 */
public class DBDaoImpl implements DBDao{
	private Connection  conn = null;
	private DBConnectionManager connection = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	

	/**
	 * 当前系统并发量不会很大，故采用此方式。
	 * @param sql
	 * @return
	 */
	protected int getVersion(String sql){
		getConn();
		int version = 0;
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				version = rs.getInt(1);
			}			
			//return new SimpleDateFormat("mmssSSS").format(version);
			return version;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}
		return version;
	}
	
	/**
	 * 获得操作后递增的Id
	 */
	public int getIncId(String sql) {
		getConn();
		String sql2 = "SELECT LAST_INSERT_ID() AS uid;";
		int id = 0;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.execute();
			rs = stmt.executeQuery(sql2);
			while (rs.next()) {
				id = rs.getInt(1);
			}
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt, conn);
		}
		return id;
	}
	
	/**
	 * 获取指定偏移量的值，根据游标移动，横向无值时取纵向。
	 * @param sql
	 * @return
	 */
	protected LinkedList<String> getValue(String sql,int offset){
		getConn();
		LinkedList<String> ilist = new LinkedList<String>();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();			
			while(rs.next()){
				for(int i=0;i<offset;i++){
				   ilist.add(rs.getString(i+1));
				}
			}			
			return ilist;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}
		return ilist;
	}
	
	/**
	 * 获取对数据库进行增、删、改操作后所影响的行数。
	 * @author mengfeiyang
	 * @param sql
	 * @return
	 */
	protected int getUpdateResult(String sql){
		getConn();
		int rows = 0;
		try {
			stmt = conn.prepareStatement(sql);
			rows = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}
		return rows;
	}
	
	/**
	 * 获得数据库连接
	 */
	@Override
	public Connection getConn() {
		connection = DBConnectionManager .getInstance();
		conn = connection.getConnection();
        return conn;
	}

	/**
	 * 释放资源的方法
	 */
	@Override
	public void close(ResultSet set, PreparedStatement pre, Connection conn) {
    	try {
     	   if(set!=null){
     	    set.close();
     	   }
     	   if(pre!=null){
     	    pre.close();
     	   }
     	   if(conn!=null){
     		  connection.freeConnection(conn);
     	   }
     	  } catch (SQLException e) {
     	   // TODO Auto-generated catch block
     	   e.printStackTrace();
     	  }
	}

}
