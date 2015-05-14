package com.youku.yks.dao.impl;

import java.util.List;

import com.youku.yks.batchoper.BatchDataOper;
import com.youku.yks.bean.CaseTypeBean;
import com.youku.yks.dao.CaseTypeDao;

/**
 * 对用例类型进行操作的接口实例<br>
 * 
 * <em>开发时间：2014年7月28日 16:26
 * @author mengfeiyang
 * @version 1.0
 * @since JDK 1.7 
 *
 */
public class CaseTypeImpl extends BatchDataOper implements CaseTypeDao {
	
	/**
	 * 增加一个用例类型
	 */
	@Override
	public int addCaseType(CaseTypeBean caseType) {
		String sql = "INSERT INTO t_type(isDisabled,userId,typeName) VALUES('"+caseType.getIsDisabled()+"','"+caseType.getUserid()+"','"+caseType.getTypeName()+"');";
		return getUpdateResult(sql);
	}

	/**
	 * 删除一个用例类型，根据id
	 */
	@Override
	public int deleCaseType(int TypeId) {
		String sql = "DELETE FROM t_type where id='"+TypeId+"';";
		return getUpdateResult(sql);
	}
	
	/**
	 * 更新用例类型，根据Id
	 */
	@Override
	public int updateCaseType(CaseTypeBean caseType) {
		String vsql = "SELECT version FROM t_type WHERE  id='"+caseType.getId()+"';";		
		int version = getVersion(vsql);
		String sql = "UPDATE t_type SET isDisabled = '"+caseType.getIsDisabled()+"',typeName = '"+caseType.getTypeName()+"' ,version = version + 1 WHERE id = '"+caseType.getId()+"' AND version = '"+version+"';";
		return getUpdateResult(sql);
	}

	/**
	 * 获取库中所有的用例类型
	 */
	@Override
	public List<CaseTypeBean> getAllCaseType() {		
		String sql = "SELECT * FROM t_type;";
		return getBatchCaseType(sql);
	}

	/**
	 * 获取一个用例类型的信息，根据id
	 */
	@Override
	public CaseTypeBean getUnitCaseType(int TypeId) {
		String sql = "SELECT * FROM t_type WHERE id = '"+TypeId+"';";
		return getBatchCaseType(sql).get(0);
	}
	
	/**
	 * 获取全部可用的用例类型
	 */
	@Override
	public List<CaseTypeBean> getAllEnabledCaseType() {
		String sql = "SELECT * FROM t_type WHERE isDisabled='1';";
		return getBatchCaseType(sql);
	}

	/**
	 * 获取全部不可用的用例类型
	 */
	@Override
	public List<CaseTypeBean> getAllDisabledCaseType() {
		String sql = "SELECT * FROM t_type WHERE isDisabled='0';";
		return getBatchCaseType(sql);
	}

	@Override
	public List<CaseTypeBean> getAllCaseTypeByUser(int userid) {
		String sql = "SELECT * FROM t_type WHERE userid='"+userid+"';";
		return getBatchCaseType(sql);
	}

	@Override
	public List<CaseTypeBean> getAllEnabledCaseTypeByUser(int userid) {
		String sql = "SELECT * FROM t_type WHERE userid='"+userid+"' AND isDisabled='1';";
		return getBatchCaseType(sql);
	}

	@Override
	public List<CaseTypeBean> getAllDisabledCaseTypeByUser(int userid) {
		String sql = "SELECT * FROM t_type WHERE userid='"+userid+"' AND isDisabled='0';";
		return getBatchCaseType(sql);
	}

	@Override
	public CaseTypeBean getUnitCaseType(String TypeName, int userid)
			throws Exception {
		String sql = "SELECT * FROM t_type WHERE userid='"+userid+"' AND typeName='"+TypeName+"';";
		return getBatchCaseType(sql).get(0);
	}

}
