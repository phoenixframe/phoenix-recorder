package com.youku.yks.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.youku.yks.batchoper.BatchDataOper;
import com.youku.yks.bean.LogUnitBean;
import com.youku.yks.dao.LogUnitDao;

public class LogUnitImpl extends BatchDataOper implements LogUnitDao {
	@Override
	public int addUnitLog(LogUnitBean logUnit) {
		String sql = "INSERT INTO l_unit VALUES(0,'"+logUnit.getBatchId()
				+"','"+logUnit.getVid()
				+"','"+logUnit.getResult()
				+"','"+logUnit.getTypeId()
				+"','"+logUnit.getCaseNodeId()
				+"','"+logUnit.getParamNodeId()
				+"','"+logUnit.getUrl()
				+"','"+logUnit.getRemark()+"',0);";
		return getIncId(sql);
	}

	@Override
	public int deleUnitLog(int id) {
		String sql = "DELETE FROM l_unit WHERE id='"+id+"';";
		return getUpdateResult(sql);		
	}

	@Override
	public int updateUnitLog(LogUnitBean logUnit) {
		String vsql = "SELECT version FROM l_unit WHERE  id='"+logUnit.getId()+"';";		
		int version = getVersion(vsql);
		String sql = "UPDATE l_unit SET batchId='"+logUnit.getBatchId()
				+"',vid='"+logUnit.getVid()
				+"',result='"+logUnit.getResult()
				+"',typeId='"+logUnit.getTypeId()
				+"',caseNodeId='"+logUnit.getCaseNodeId()
				+"',paramNodeId='"+logUnit.getParamNodeId()
				+"',url='"+logUnit.getUrl()
				+"',remark='"+logUnit.getRemark()
				+"',version = version + 1 WHERE id='"+logUnit.getId()
				+"' AND version='"+version+"';";
		return getUpdateResult(sql);
	}

	@Override
	public List<LogUnitBean> getAllLog() {
		String sql  ="SELECT * FROM l_unit ;";
		return getBatchLogUnit(sql);
	}

	@Override
	public LogUnitBean getUnitLog(int id) {
		String sql  ="SELECT * FROM l_unit WHERE id='"+id+"';";
		return getBatchLogUnit(sql).get(0);
	}

	@Override
	public List<LogUnitBean> getAllByBatchId(int batchId) {
		String sql  ="SELECT * FROM l_unit WHERE batchId='"+batchId+"';";
		return getBatchLogUnit(sql);
	}

	@Override
	public List<LogUnitBean> getAllPassLog(int batchId) {
		String sql  ="SELECT * FROM l_unit WHERE batchId='"+batchId+"' AND result = '3';";
		return getBatchLogUnit(sql);
	}

	@Override
	public List<LogUnitBean> getAllFailLog(int batchId) {
		String sql  ="SELECT * FROM l_unit WHERE batchId='"+batchId+"' AND result = '4';";
		return getBatchLogUnit(sql);
	}

	@Override
	public List<LogUnitBean> getAllRuningLog(int batchId) {
		String sql  ="SELECT * FROM l_unit WHERE batchId='"+batchId+"' AND result = '2';";
		return getBatchLogUnit(sql);
	}
	
	@Override
	public List<LogUnitBean> getAllRuningLog() {
		String sql  ="SELECT * FROM l_unit WHERE result = '2';";
		return getBatchLogUnit(sql);
	}

	@Override
	public int getResultCount(int batchId, int caseTypeId, int result ,int caseNodeId) {
		String sql = "SELECT COUNT(l.result) AS rate FROM l_unit l WHERE l.batchId='"+batchId+"' AND l.result='"+result+"' AND l.typeId='"+caseTypeId+"' AND l.caseNodeId='"+caseNodeId+"';";
		return getVersion(sql);
	}

	@Override
	public List<String> getCaseNode(int batchId, int caseTypeId) {
		String sql = "SELECT DISTINCT l.caseNodeId FROM l_unit l WHERE l.batchId='"+batchId+"' AND l.typeId='"+caseTypeId+"';";
		return getValue(sql, 1);
	}

	@Override
	public HashMap<Integer,Integer> getPassCount(int caseTypeId, int limit) {
		String sql = "SELECT l.batchId,COUNT(l.result) AS total FROM l_unit l WHERE l.typeId='"+caseTypeId+"' AND l.result='3' GROUP BY l.batchId ORDER BY l.id DESC LIMIT 0,"+limit+"";
		return getBatchRate(sql);
	}

	@Override
	public HashMap<Integer,Integer> getFailCount(int caseTypeId, int limit) {
		String sql = "SELECT l.batchId,COUNT(l.result) AS total FROM l_unit l WHERE l.typeId='"+caseTypeId+"' AND l.result='4' GROUP BY l.batchId ORDER BY l.id DESC LIMIT 0,"+limit+"";
		return getBatchRate(sql);
	}

	@Override
	public List<String> getBatchList(int caseTypeId,int limit) {
		String sql = "SELECT DISTINCT l.batchId FROM l_unit l WHERE l.typeId='"+caseTypeId+"' ORDER BY l.id DESC LIMIT 0,"+limit+"";
		return getValue(sql, 1);
	}
}
