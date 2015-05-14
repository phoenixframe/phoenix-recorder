package com.youku.yks.dao;

import java.util.HashMap;
import java.util.List;

import com.youku.yks.bean.LogUnitBean;

/**
 * 对日志单元进行操作的接口
 * @author mengfeiyang
 *
 */
public interface LogUnitDao {
	/**
	 * 增加一条记录
	 * @param logUnit
	 * @return
	 */
	int addUnitLog(LogUnitBean logUnit);
	
	/**
	 * 删除一条记录，根据Id
	 * @param id
	 * @return
	 */
	int deleUnitLog(int id);
	
	/**
	 * 更新一条日志记录
	 * @param logUnit
	 * @return
	 */
	int updateUnitLog(LogUnitBean logUnit);
	
	/**
	 * 获取所有日志单元
	 * @return
	 */
	List<LogUnitBean> getAllLog();
	
	/**
	 * 获取一条日志单元
	 * @return
	 */
	LogUnitBean getUnitLog(int id);
	
	/**
	 * 根据Log批次获取所有Log
	 * @param batchId
	 * @return
	 */
	List<LogUnitBean> getAllByBatchId(int batchId);
	
	/**
	 * 获取指定LOG批次下，执行状态为Pass的LOG
	 * @param result
	 * @return
	 */
	List<LogUnitBean> getAllPassLog(int batchId);
	
	/**
	 * 获取指定LOG批次下，执行状态为Fail的LOG
	 * @param result
	 * @return
	 */
	List<LogUnitBean> getAllFailLog(int batchId);
	
	/**
	 * 获取正在执行的条目,根据batchId
	 * @param status
	 * @return
	 */
	List<LogUnitBean> getAllRuningLog(int batchId);
	
	/**
	 * 获取正在执行的条目
	 * @param status
	 * @return
	 */
	List<LogUnitBean> getAllRuningLog();
	
	/**
	 * 对用例进行统计
	 * @param batchId
	 * @param caseTypeId
	 * @return
	 */
	List<String> getCaseNode(int batchId,int caseTypeId);
	
	/**
	 * 对结果进行统计,此种统计方法适用于获取单一用例的结果
	 * @param batchId
	 * @param caseTypeId
	 * @param caseNodeId
	 * @return
	 */
	int getResultCount(int batchId,int caseTypeId,int result ,int caseNodeId);
	
	/**
	 * 统计结果为Pass的数据<br>
	 * 此种方法适用于针对单一用例类型在多个批次下的结果
	 * limit为获取数据的最大Id
	 * @param caseTypeId
	 * @return
	 */
	HashMap<Integer,Integer> getPassCount(int caseTypeId,int limit);
	
	/**
	 * 统计结果为Fail的数据<br>
	 * 此种方法适用于针对单一用例类型在多个批次下的结果
	 * limit为获取数据的最大Id
	 * @param caseTypeId
	 * @return
	 */
	HashMap<Integer,Integer> getFailCount(int caseTypeId,int limit);
	
	/**
	 * 返回测试结果中单一用例类型所对应的多个批次
	 * @param caseTypeId
	 * @return
	 */
	List<String> getBatchList(int caseTypeId,int limit);
}
