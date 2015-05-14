package com.youku.yks.dao.impl;

import java.util.List;

import com.youku.yks.batchoper.BatchDataOper;
import com.youku.yks.bean.StrategyDataBean;
import com.youku.yks.dao.StrategyDataDao;

public class StrategyDataImpl extends BatchDataOper implements StrategyDataDao {

	@Override
	public int addStrategyData(StrategyDataBean strategyDataBean) {
		String sql = "INSERT INTO p_data VALUES(0,'"+strategyDataBean.getStategyId()
				+"','"+strategyDataBean.getTps()
				+"','"+strategyDataBean.getMaxTPS()
				+"','"+strategyDataBean.getDurationTime()
				+"','"+strategyDataBean.getResponseTime()
				+"','"+strategyDataBean.getMaxResponseTime()
				+"','"+strategyDataBean.getThroughputBytes()
				+"','"+strategyDataBean.getPassCount()
				+"','"+strategyDataBean.getFailCount()
				+"','"+strategyDataBean.getExceptionCount()
				+"','"+strategyDataBean.getPassRate()
				+"','"+strategyDataBean.getFailRate()
				+"');";
		return getUpdateResult(sql);
	}

	@Override
	public int deleStrategyData(int id) {
		String sql = "DELETE FROM p_data WHERE id='"+id+"'";
		return getUpdateResult(sql);
	}

	@Override
	public StrategyDataBean getStrategyData(int id) {
		String sql = "SELECT * FROM p_data WHERE id='"+id+"'";
		return getBatchStrategyData(sql).get(0);
	}

	@Override
	public List<StrategyDataBean> getAllStrategyData(int StrategyId) {
		String sql = "SELECT * FROM p_data WHERE strategyId='"+StrategyId+"'";
		return getBatchStrategyData(sql);
	}

}
