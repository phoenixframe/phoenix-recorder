package com.youku.yks.dao;

import java.util.List;

import com.youku.yks.bean.CaseTypeBean;

/**
 * 对用例类型进行操作的接口<br>
 * 
 * <em>开发时间：2014年7月28日 16:19
 * @author mengfeiyang
 * @version 1.0
 * @since JDK 1.7 
 *
 */
public interface CaseTypeDao {
	
	/**
	 * 增加一个用例的类型
	 * @param caseType
	 * @return
	 */
	int addCaseType(CaseTypeBean caseType);
	
	/**
	 * 删除一个用例类型，根据用例类型条目的id
	 * @param TypeId
	 * @return
	 */
	int deleCaseType(int TypeId);
	
	/**
	 * 更新一个用例类型，须传入一个完整的CaseTypeBean对象
	 * @param caseType
	 * @return
	 */
	int updateCaseType(CaseTypeBean caseType);
	
	/**
	 * 返回全部的用例类型
	 * @return
	 */
	List<CaseTypeBean> getAllCaseType();
	
	/**
	 * 返回全部的用例类型,根据当前用户
	 * @return
	 */
	List<CaseTypeBean> getAllCaseTypeByUser(int userid);
	
	/**
	 * 返回全部的可用的用例类型
	 * @return
	 */
	List<CaseTypeBean> getAllEnabledCaseType();
	
	/**
	 * 返回全部可用的用例类型,根据当前用户
	 * @return
	 */
	List<CaseTypeBean> getAllEnabledCaseTypeByUser(int userid);
	
	/**
	 * 返回全部的不可用的用例类型
	 * @return
	 */
	List<CaseTypeBean> getAllDisabledCaseType();
	
	/**
	 * 返回全部的不可用的用例类型
	 * @return
	 */
	List<CaseTypeBean> getAllDisabledCaseTypeByUser(int userid);
	
	/**
	 * 返回一条用例类型，须提供TypeId
	 * @param TypeId
	 * @return
	 */
	CaseTypeBean getUnitCaseType(int TypeId);
	
	/**
	 * 返回一条用例类型，根据名称返回，须提供TypeName,userid
	 * @param TypeName
	 * @return
	 */
	CaseTypeBean getUnitCaseType(String TypeName,int userid) throws Exception;

}
