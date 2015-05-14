package com.youku.yks.dao.impl;

import java.util.List;

import com.youku.yks.batchoper.BatchDataOper;
import com.youku.yks.bean.HostBean;
import com.youku.yks.dao.HostDao;

public class HostImpl extends BatchDataOper implements HostDao {

	@Override
	public int addHost(HostBean hostBean) {
		String sql = "INSERT INTO t_host VALUES(0,'"+hostBean.getUserId()+"','"+hostBean.getHostName()+"','"+hostBean.getRemark()+"');";
		return getUpdateResult(sql);
	}

	@Override
	public int deleHost(int id) {
		String sql = "DELETE FROM t_host WHERE id='"+id+"'";
		return getUpdateResult(sql);
	}

	@Override
	public int updateHost(HostBean hostBean) {
		String sql = "UPDATE t_host SET hostName='"+hostBean.getHostName()+"',remark='"+hostBean.getRemark()+"' WHERE id='"+hostBean.getId()+"'";
		return getUpdateResult(sql);
	}

	@Override
	public List<HostBean> getAllHost() {
		String sql = "SELECT * FROM t_host";
		return getBatchHost(sql);
	}

	@Override
	public HostBean getHost(int id) {
		String sql = "SELECT * FROM t_host WHERE id='"+id+"'";
		return getBatchHost(sql).get(0);
	}

	@Override
	public HostBean getHost(String hostName,int userId) throws Exception{
		String sql = "SELECT * FROM t_host WHERE hostName='"+hostName+"' AND userId='"+userId+"'";
		return getBatchHost(sql).get(0);
	}

	@Override
	public List<HostBean> getAllHostByUser(int userId) {
		String sql = "SELECT * FROM t_host WHERE userId='"+userId+"'";
		return getBatchHost(sql);
	}

}
