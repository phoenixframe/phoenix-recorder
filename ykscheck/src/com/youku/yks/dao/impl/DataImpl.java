package com.youku.yks.dao.impl;

import java.util.List;

import com.youku.yks.batchoper.BatchDataOper;
import com.youku.yks.bean.DataBean;
import com.youku.yks.dao.DataDao;

public class DataImpl extends BatchDataOper implements DataDao {

	@Override
	public int addData(DataBean dataBean) {
		String sql = "INSERT INTO t_data VALUES(0,'"+dataBean.getUserId()
	            +"','"+dataBean.getParamNodeId()
				+"','"+dataBean.getRequestType()
				+"','"+dataBean.getJsonPath()
				+"','"+dataBean.getExpect()
				+"','"+dataBean.getRemark()+"',0);";
		
		return getUpdateResult(sql);		
	}

	@Override
	public int deleData(int id) {
		String sql = "DELETE FROM t_data WHERE id='"+id+"';";
		return getUpdateResult(sql);
	}

	@Override
	public int updateData(DataBean dataBean) {
		String vsql = "SELECT version FROM t_data WHERE  id='"+dataBean.getId()+"';";		
		int version = getVersion(vsql);
		String sql = "UPDATE t_data SET paramNodeId = '"+dataBean.getParamNodeId()
				+"',requestType='"+dataBean.getRequestType()
				+"',jsonPath='"+dataBean.getJsonPath()
				+"',expect='"+dataBean.getExpect()
				+"',remark='"+dataBean.getRemark()
				+"',version = version + 1 WHERE id='"+dataBean.getId()
				+"' AND version='"+version+"';";
		
		return getUpdateResult(sql);
	}

	@Override
	public List<DataBean> getAllData() {
		String sql = "SELECT * FROM t_data;";
		return getBatchCaseData(sql);
	}

	@Override
	public List<DataBean> getAllDataByParamNodeId(int nid,int userId) {
		String sql="SELECT * FROM t_data WHERE paramNodeId='"+nid+"' AND userId='"+userId+"';";
		return getBatchCaseData(sql);
	}

	@Override
	public DataBean getData(int vid) {
		String sql = "SELECT * FROM t_data WHERE id='"+vid+"';";
		return getBatchCaseData(sql).get(0);
	}

	@Override
	public List<DataBean> getAllDataByUser(int userId) {
		String sql = "SELECT * FROM t_data WHERE userId='"+userId+"'";
		return getBatchCaseData(sql);
	}

	@Override
	public DataBean getData(int vid, int userId) {
		String sql = "SELECT * FROM t_data WHERE userId='"+userId+"'";
		return getBatchCaseData(sql).get(0);
	}
	
}
