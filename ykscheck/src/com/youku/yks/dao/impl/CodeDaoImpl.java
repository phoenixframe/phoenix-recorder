package com.youku.yks.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.youku.yks.batchoper.BatchDataOper;
import com.youku.yks.bean.CodeBean;
import com.youku.yks.dao.CodeDao;

public class CodeDaoImpl extends BatchDataOper implements CodeDao {

	@Override
	public int addCode(CodeBean code) {
		String sql = "INSERT INTO t_code VALUES('"+code.getCode()+"','"+code.getValue()+"');";
		return getUpdateResult(sql);
	}

	@Override
	public int deleCode(int codeId) {
		String sql = "DELETE FROM t_code WHERE code='"+codeId+"'";
		return getUpdateResult(sql);
	}

	@Override
	public int updateCode(CodeBean Code) {
		String sql = "UPDATE t_code SET code='"+Code.getCode()+"',value='"+Code.getValue()+"' WHERE code='"+Code.getCode()+"'";
		return getUpdateResult(sql);	
	}

	@Override
	public HashMap<Integer, String> getAllCode() {
		String sql = "SELECT * FROM t_code;";
		return getBatchCode(sql);
	}

	@Override
	public String getCode(int codeId) {
		return getAllCode().get(codeId);
	}

	@Override
	public List<CodeBean> getCodeList(int start, int end) {
		String sql = "SELECT * FROM t_code WHERE code BETWEEN '"+start+"' AND '"+end+"';";
		return getBatchCodeList(sql);
	}	
	
}
