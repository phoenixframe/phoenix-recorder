package com.youku.yks.dao;

import java.util.List;

import com.youku.yks.bean.StrategyBean;

/**
 * 对并发测试策略操作的接口
 * @author mengfeiyang
 *
 */
public interface StrategyDao {
	/**
	 * 新增并发测试策略
	 * @param strategyBean
	 * @return
	 */
	int addStrategy(StrategyBean strategyBean);
	/**
	 * 删除一条策略
	 * @param id
	 * @return
	 */
	int deleStrategy(int id);
	/**
	 * 更新测试策略
	 * @param strategyBean
	 * @return
	 */
	int updateStrategy(StrategyBean strategyBean);
	/**
	 * 获取一条测试策略
	 * @param id
	 * @return
	 */
	StrategyBean getStrategyBean(int id);
	/**
	 * 获取所有测试策略
	 * @return
	 */
	List<StrategyBean> getAllStrategyBean();

}
