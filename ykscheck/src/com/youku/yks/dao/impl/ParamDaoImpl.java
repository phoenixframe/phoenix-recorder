package com.youku.yks.dao.impl;

import java.util.List;

import com.youku.yks.batchoper.BatchDataOper;
import com.youku.yks.bean.ParamBean;
import com.youku.yks.dao.ParamDao;

public class ParamDaoImpl extends BatchDataOper implements ParamDao {

	@Override
	public int addParam(ParamBean paramBean) {
		String sql = "INSERT INTO t_parameter VALUES(0,'"+paramBean.getUserId()
				+"','"+paramBean.getInterfaceId()
				+"','"+paramBean.getParamName()
				+"','"+paramBean.getParamType()
				+"','"+paramBean.getParamValue()
				+"',0);";
		return getUpdateResult(sql);
	}

	@Override
	public int deleParam(int id) {
		String sql = "DELETE FROM t_parameter WHERE id='"+id+"';";
		return getUpdateResult(sql);
	}

	@Override
	public int updateParam(ParamBean paramBean) {
		String vsql = "SELECT version FROM t_parameter WHERE  id='"+paramBean.getId()+"';";		
		int version = getVersion(vsql);
		String sql = "UPDATE t_parameter SET interfaceid='"+paramBean.getInterfaceId()
				+"',paramName='"+paramBean.getParamName()
				+"',paramType='"+paramBean.getParamType()
				+"',paramValue='"+paramBean.getParamValue()
				+"',version = version + 1 WHERE id='"+paramBean.getId()
				+"' and version='"+version+"';";
		return getUpdateResult(sql);
	}

	@Override
	public List<ParamBean> getAllParam() {
		String sql = "SELECT * FROM t_parameter;";
		return getBatchParam(sql);
	}

	@Override
	public ParamBean getParam(int id) {
		String sql = "SELECT * FROM t_parameter WHERE id='"+id+"';";
		
		return getBatchParam(sql).get(0);
	}

	@Override
	public List<ParamBean> getAllParamByInterfaceId(int interId) {
		String sql = "SELECT * FROM t_parameter WHERE interfaceid='"+interId+"';";
		return getBatchParam(sql);
	}

	@Override
	public List<ParamBean> getAllParamByUser(int userId) {
		String sql = "SELECT * FROM t_parameter WHERE userId='"+userId+"';";
		return getBatchParam(sql);
	}
}
