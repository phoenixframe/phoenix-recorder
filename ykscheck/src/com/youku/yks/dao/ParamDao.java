package com.youku.yks.dao;

import java.util.List;

import com.youku.yks.bean.ParamBean;

public interface ParamDao {

	/**
	 * 增加一条数据
	 * @param paramBean
	 * @return
	 */
	int addParam(ParamBean paramBean);
	
	/**
	 * 删除一条记录
	 * @param id
	 * @return
	 */
	int deleParam(int id);
	
	/**
	 * 更新一条记录
	 * @param paramBean
	 * @return
	 */
	int updateParam(ParamBean paramBean);
	
	/**
	 * 获取所有的记录
	 * @return
	 */
	List<ParamBean> getAllParam();
	
	/**
	 * 获取所有的记录
	 * @return
	 */
	List<ParamBean> getAllParamByUser(int userId);
	
	/**
	 * 获取所有的记录,根据请求接口的Id
	 * @return
	 */
	List<ParamBean> getAllParamByInterfaceId(int interId);
	
	
	/**
	 * 获取一条记录
	 * @return
	 */
	ParamBean getParam(int id);
}
