package com.youku.yks.dao;

import java.util.List;

import com.youku.yks.bean.StrategyDataBean;

public interface StrategyDataDao {
	/**
	 * 新增加一条记录
	 * @param strategyDataBean
	 * @return
	 */
	int addStrategyData(StrategyDataBean strategyDataBean);
	/**
	 * 删除一条记录
	 * @param id
	 * @return
	 */
	int deleStrategyData(int id);
	/**
	 * 获取一条记录
	 * @param id
	 * @return
	 */
	StrategyDataBean getStrategyData(int id);
	/**
	 * 获取所有记录，根据strategyId
	 * @param StrategyId
	 * @return
	 */
	List<StrategyDataBean> getAllStrategyData(int StrategyId);

}
