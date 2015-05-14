package com.youku.yks.dao.impl;

import java.util.List;

import com.youku.yks.batchoper.BatchDataOper;
import com.youku.yks.bean.UserBean;
import com.youku.yks.dao.UserDao;

public class UserDaoImpl extends BatchDataOper implements UserDao {

	@Override
	public int addUser(UserBean user) {
		String sql = "INSERT INTO t_user VALUES(0,'"+user.getName()+"','"+user.getPassword()+"','"+user.getLevel()+"','"+user.getRemark()+"');";
		return getUpdateResult(sql);
	}

	@Override
	public int delUser(int id) {
		String sql = "DELETE FROM t_user WHERE id='"+id+"'";
		return getUpdateResult(sql);
	}

	@Override
	public int delUser(String name) {
		String sql = "DELETE FROM t_user WHERE name='"+name+"'";
		return getUpdateResult(sql);
	}

	@Override
	public int updateUser(UserBean user) {
		String sql = "UPDATE t_user SET name='"+user.getName()+"',password='"+user.getPassword()+"',level='"+user.getLevel()+"',remark='"+user.getRemark()+"' WHERE id='"+user.getId()+"'";
		return getUpdateResult(sql);
	}

	@Override
	public UserBean getUser(int id) {
		String sql = "SELECT * FROM t_user WHERE id='"+id+"'";
        return getBatchUser(sql).get(0);
	}

	@Override
	public UserBean getUser(String name) throws Exception{
		String sql = "SELECT * FROM t_user WHERE name='"+name+"'";
		return getBatchUser(sql).get(0);
	}

	@Override
	public List<UserBean> getAllUser() {
		String sql = "SELECT * FROM t_user";
		return getBatchUser(sql);
	}

	@Override
	public List<UserBean> getAllUserByLevel(int level) {
		String sql = "SELECT * FROM t_user WHERE level='"+level+"'";
		return getBatchUser(sql);
	}

}
