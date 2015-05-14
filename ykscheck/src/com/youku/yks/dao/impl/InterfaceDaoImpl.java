package com.youku.yks.dao.impl;

import java.util.List;

import com.youku.yks.batchoper.BatchDataOper;
import com.youku.yks.bean.InterfaceBean;
import com.youku.yks.dao.InterfaceDao;

/**
 * 对参数进行操作的实现类
 * @author mengfeiyang
 *
 */
public class InterfaceDaoImpl extends BatchDataOper implements InterfaceDao {

	@Override
	public int addInterface(InterfaceBean requestData) {
		String sql = "INSERT INTO t_interface VALUES(0,'"+requestData.getUserId()+"','"+requestData.get_interface()+"','0')";
		return getUpdateResult(sql);
	}

	@Override
	public int deleInterface(int id) {
		String sql = "DELETE FROM t_interface WHERE id='"+id+"'";
		return getUpdateResult(sql);
	}

	@Override
	public int updateInterface(InterfaceBean requestData) {
		String vsql = "SELECT version FROM t_interface WHERE  id='"+requestData.getId()+"';";
		int version = getVersion(vsql);
		String sql = "UPDATE t_interface SET "
				+"interface='"+requestData.get_interface()
				+"',version=version+1 WHERE id='"+requestData.getId()
				+"' AND version='"+version+"'";
		return getUpdateResult(sql);
	}

	@Override
	public List<InterfaceBean> getAllInterface() {
		String sql  = "SELECT * FROM t_interface";
		return getBatchRequestData(sql);		
	}

	@Override
	public InterfaceBean getInterface(int id) {
		String sql = "SELECT * FROM t_interface WHERE id='"+id+"'";
		return getBatchRequestData(sql).get(0);
	}

	@Override
	public List<InterfaceBean> getAllInterfaceByUser(int userId) {
		String sql  = "SELECT * FROM t_interface WHERE userId='"+userId+"'";
		return getBatchRequestData(sql);
	}

	@Override
	public InterfaceBean getInterface(String name, int userId) throws Exception{
		String sql = "SELECT * FROM t_interface WHERE interface='"+name+"' AND userId='"+userId+"'";
		return getBatchRequestData(sql).get(0);
	}	
}
