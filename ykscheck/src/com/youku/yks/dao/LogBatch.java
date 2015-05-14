package com.youku.yks.dao;

import java.util.List;

import com.youku.yks.bean.LogBatchBean;

/**
 * 对日志批次进行操作的接口
 * @author mengfeiyang
 *
 */
public interface LogBatch {
	/**
	 * 创建一个日志批次
	 * @return
	 */
	int ceateBatchId(int userId);
	
	/**
	 * 返回一个日志批次的详细信息
	 * @param id
	 * @return
	 */
	LogBatchBean getBatchId(int id);
	
	/**
	 * 返回一个日志批次的详细信息
	 * @param id
	 * @return
	 */
	LogBatchBean getBatchId(String batch);
	
	/**
	 * 根据Id删除一个批次
	 * @param id
	 * @return
	 */
	int deleLogBatch(int id);
	
	/**
	 * 根据批次的value值删除一个批次
	 * @param batchValue
	 * @return
	 */
	int deleLogBatch(String batchValue);
	
	/**
	 * 获取表中所有的Log批次
	 * @return
	 */
	List<LogBatchBean> getAllBatch();
	
	/**
	 * 获取表中所有的Log批次
	 * @return
	 */
	List<LogBatchBean> getAllBatchByUserId(int userId);
}
