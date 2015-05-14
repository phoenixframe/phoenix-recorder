package com.youku.yks.dao;

import java.util.List;

import com.youku.yks.bean.CaseBean;

/**
 * 对用例进行操作的接口<br>
 * 
 * <em>开发时间：2014年7月28日 20:13
 * @author mengfeiyang
 * @version 1.0
 * @since JDK 1.7 
 *
 */
public interface CaseDao {
	
	/**
	 * 获取所有的用例，包括不可用和可用
	 * @return
	 */
	List<CaseBean> getAllCase();
	
	/**
	 * 获取所有的用例，包括不可用和可用,根据当前用户获取
	 * @return
	 */
	List<CaseBean> getAllCaseByUser(int userId);
	
	/**
	 * 获取所有可用的用例
	 * @return
	 */
	List<CaseBean> getAllEnabledCase();
	
	/**
	 * 获取所有可用的用例，根据当前用户
	 * @return
	 */
	List<CaseBean> getAllEnabledCaseByUser(int userId);
	
	/**
	 * 获取所有不可用的用例
	 * @return
	 */
	List<CaseBean> getAllDisabledCase();
	
	/**
	 * 获取所有不可用的用例
	 * @return
	 */
	List<CaseBean> getAllDisabledCaseByUser(int userId);
	
	
	/**
	 * 根据TypeNameId过滤所有用例
	 * @return
	 */
	List<CaseBean> getAllCaseByTypeNameId(int typeNameId,int UserId);
	
	/**
	 * 根据TypeNameId过滤所有可用的用例
	 * @return
	 */
	List<CaseBean> getAllEnabledCaseByTypeNameId(int typeNameId , int userId);
	
	/**
	 * 根据id，获取单个用例的信息
	 * @param id
	 * @return
	 */
	CaseBean getUnitCase(int id);
	
	/**
	 * 增加一个用例
	 * @param caseBean
	 * @return
	 */
	int addCase(CaseBean caseBean);
	
	/**
	 * 删除一个用例,根据id
	 * @param id
	 * @return
	 */
	int deleCase(int id);
	
	/**
	 * 更新一个用例
	 * @param caseBean
	 * @return
	 */
	int updateCase(CaseBean caseBean);
	
}
