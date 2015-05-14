package com.youku.yks.dao.impl;

import java.util.List;

import com.youku.yks.batchoper.BatchDataOper;
import com.youku.yks.bean.LogBatchBean;
import com.youku.yks.dao.LogBatch;
import com.youku.yks.tools.GetNow;

public class LogBatchImpl extends BatchDataOper implements LogBatch {
	/**
	 * 创建一个批次号，并返回批次号的Id
	 */
	@Override
	public int ceateBatchId(int userId) {
		String batchId = GetNow.getCurrentTime("yyyyMMddHHmmssSSS");
		String sql = "INSERT INTO l_batch(userId,batchvalue) VALUES('"+userId+"','"+batchId+"');";
		return getIncId(sql);
	}

	@Override
	public LogBatchBean getBatchId(int id) {
		String sql = "SELECT * FROM l_batch WHERE id='"+id+"';";
		return getBatchLog(sql).get(0);
	}

	@Override
	public int deleLogBatch(int id) {
		String sql  ="DELETE FROM l_batch WHERE id = '"+id+"';";
		return getUpdateResult(sql);
	}

	@Override
	public int deleLogBatch(String batchValue) {
		String sql  ="DELETE FROM l_batch WHERE batchvalue = '"+batchValue+"';";
		return getUpdateResult(sql);
	}

	@Override
	public List<LogBatchBean> getAllBatch() {
		String sql = "SELECT * FROM l_batch;";
		return getBatchLog(sql);
	}

	@Override
	public LogBatchBean getBatchId(String batch) {
		String sql = "SELECT * FROM l_batch WHERE batchvalue='"+batch+"';";
		return getBatchLog(sql).get(0);
	}

	@Override
	public List<LogBatchBean> getAllBatchByUserId(int userId) {
		String sql = "SELECT * FROM l_batch WHERE userId='"+userId+"';";
		return getBatchLog(sql);
	}

}
