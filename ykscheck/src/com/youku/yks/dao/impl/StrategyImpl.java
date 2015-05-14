package com.youku.yks.dao.impl;

import java.util.List;

import com.youku.yks.batchoper.BatchDataOper;
import com.youku.yks.bean.StrategyBean;
import com.youku.yks.dao.StrategyDao;

public class StrategyImpl extends BatchDataOper implements StrategyDao {

	@Override
	public int addStrategy(StrategyBean strategyBean) {
		String sql = "INSERT INTO p_strategy VALUES(0,'"+strategyBean.getUserId()
	            +"','"+strategyBean.getClientIP()
				+"','"+strategyBean.getStartTime()
				+"','"+strategyBean.getUrl()
				+"','"+strategyBean.getVusers()
				+"','"+strategyBean.getRunType()
				+"','"+strategyBean.getRunTimeSet()
				+"','"+strategyBean.getIterationSet()
				+"','"+strategyBean.getAlternation()
				+"','"+strategyBean.getCheckpoint()
				+"','"+strategyBean.getExpectValue()
				+"','"+strategyBean.getMaxTimeout()
				+"','"+strategyBean.getRemark()
				+"');";
		return getIncId(sql);
	}

	@Override
	public int deleStrategy(int id) {
		String sql = "DELETE FROM p_strategy WHERE id='"+id+"'";
		return getUpdateResult(sql);
	}

	@Override
	public int updateStrategy(StrategyBean strategyBean) {
		String sql = "UPDATE p_strategy SET userId='"+strategyBean.getUserId()
				+"', clientIP='"+strategyBean.getClientIP()
				+"',startTime='"+strategyBean.getStartTime()
				+"',url='"+strategyBean.getUrl()
				+"',vuser='"+strategyBean.getVusers()
				+"',runType='"+strategyBean.getRunType()
				+"',runTimeSet='"+strategyBean.getRunTimeSet()
				+"',iterationSet='"+strategyBean.getIterationSet()
				+"',alternation='"+strategyBean.getAlternation()
				+"',checkpoint='"+strategyBean.getCheckpoint()
				+"',expectValue='"+strategyBean.getExpectValue()
				+"',maxTimeout='"+strategyBean.getMaxTimeout()
				+"',remark='"+strategyBean.getRemark()
				+"' WHERE id='"+strategyBean.getId()
				+"'";
		return getUpdateResult(sql);
	}

	@Override
	public StrategyBean getStrategyBean(int id) {
		String sql = "SELECT * FROM p_strategy WHERE id='"+id+"'";
		return getBatchStrategy(sql).get(0);
	}

	@Override
	public List<StrategyBean> getAllStrategyBean() {
		String sql = "SELECT * FROM p_strategy";
		return getBatchStrategy(sql);
	}

}
