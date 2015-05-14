package com.youku.yks.dao.impl;

import java.util.List;

import com.youku.yks.batchoper.BatchDataOper;
import com.youku.yks.bean.CaseBean;
import com.youku.yks.dao.CaseDao;

/**
 * 用例接口的实现类
 * @author mengfeiyang
 *
 */
public class CaseImpl extends BatchDataOper implements CaseDao {

	/**
	 * 获取所有的用例，包括不可用和可用
	 * @return
	 */
	@Override
	public List<CaseBean> getAllCase() {
		String sql = "SELECT * FROM t_case;";
		return getBatchCase(sql);
	}
	

	/**
	 * 获取所有可用的用例
	 * @return
	 */
	@Override
	public List<CaseBean> getAllEnabledCase() {
		String sql = "SELECT * FROM t_case WHERE isDisabled='1';";
		return getBatchCase(sql);
	}

	/**
	 * 获取所有不可用的用例
	 * @return
	 */
	@Override
	public List<CaseBean> getAllDisabledCase() {
		String sql = "SELECT * FROM t_case WHERE isDisabled='0';";
		return getBatchCase(sql);
	}
	
	/**
	 * 根据TypeNameId过滤所有用例
	 * @return
	 */
	@Override
	public List<CaseBean> getAllEnabledCaseByTypeNameId(int typeNameId,int userId) {
		String sql = "SELECT * FROM t_case WHERE typeNameId='"+typeNameId+"' AND isDisabled='1' AND userId='"+userId+"';";
		return getBatchCase(sql);
	}

	/**
	 * 根据id，获取单个用例的信息
	 * @param id
	 * @return
	 */
	@Override
	public CaseBean getUnitCase(int id) {
		String sql = "SELECT * FROM t_case WHERE id='"+id+"'";
		return getBatchCase(sql).get(0);
	}

	/**
	 * 增加一个用例
	 * @param caseBean
	 * @return
	 */
	@Override
	public int addCase(CaseBean caseBean) {
		String sql = "INSERT INTO t_case(isDisabled,userId,typeNameId,vid,hostId,interfaceId,caseNode,remark) VALUES(" +
				"'"+caseBean.getIsDisabled()+"','"+
				caseBean.getUserId()+"','"+
				caseBean.getTypeNameId()+"','"+
				caseBean.getVid()+"','"+
				caseBean.getHostId()+"','"+
				caseBean.getInterfaceId()+"','"+
				caseBean.getCaseNode()+"','"+
				caseBean.getRemark()+"');";
		
		return getUpdateResult(sql);
	}

	/**
	 * 删除一个用例,根据id
	 * @param id
	 * @return
	 */
	@Override
	public int deleCase(int id) {
		String sql = "DELETE FROM t_case WHERE id='"+id+"';";
		return getUpdateResult(sql);
	}

	/**
	 * 更新一个用例
	 * @param caseBean
	 * @return
	 */
	@Override
	public int updateCase(CaseBean caseBean) {
		String vsql = "SELECT version FROM t_case WHERE  id='"+caseBean.getId()+"';";		
		int version = getVersion(vsql);
		String sql = "UPDATE t_case SET isDisabled = '"+caseBean.getIsDisabled()
				+"',typeNameId='"+caseBean.getTypeNameId()
				+"',vid='"+caseBean.getVid()
				+"',hostId='"+caseBean.getHostId()
				+"',interfaceId='"+caseBean.getInterfaceId()
				+"',caseNode='"+caseBean.getCaseNode()
				+"',remark='"+caseBean.getRemark()
				+"', version = version + 1 WHERE id = '"+caseBean.getId()+"' AND version = '"+version+"';";
		return getUpdateResult(sql);
	}


	@Override
	public List<CaseBean> getAllCaseByUser(int userId) {
		String sql = "SELECT * FROM t_case WHERE userId='"+userId+"';";
		return getBatchCase(sql);
	}


	@Override
	public List<CaseBean> getAllEnabledCaseByUser(int userId) {
		String sql = "SELECT * FROM t_case WHERE isDisabled='1' userId='"+userId+"';";
		return getBatchCase(sql);
	}


	@Override
	public List<CaseBean> getAllDisabledCaseByUser(int userId) {
		String sql = "SELECT * FROM t_case WHERE isDisabled='0' userId='"+userId+"';";
		return getBatchCase(sql);
	}


	@Override
	public List<CaseBean> getAllCaseByTypeNameId(int typeNameId, int UserId) {
		String sql = "SELECT * FROM t_case WHERE typeNameId='"+typeNameId+"' AND userId='"+UserId+"';";
		return getBatchCase(sql);
	}

}
