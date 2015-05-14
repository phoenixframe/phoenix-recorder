package com.youku.yks.dao;

import java.util.List;

import com.youku.yks.bean.DataBean;

/**
 * 数据操作接口
 * @author mengfeiyang
 *
 */
public interface DataDao {
	/**
	 * 增加一条数据
	 * @param dataBean
	 * @return
	 */
	int addData(DataBean dataBean);
	
	/**
	 * 删除一条数据，根据id
	 * @param id
	 * @return
	 */
	int deleData(int id);
	
	/**
	 * 更新一条记录
	 * @param id
	 * @return
	 */
	int updateData(DataBean dataBean);
	
	/**
	 * 获取所有数据
	 * @return
	 */
	List<DataBean> getAllData();
	
	/**
	 * 获取所有数据
	 * @return
	 */
	List<DataBean> getAllDataByUser(int userId);
	
	/**
	 * 根据paramNodeId获取数据
	 * @param nid
	 * @return
	 */
	List<DataBean> getAllDataByParamNodeId(int nid,int userId);
	
	/**
	 * 获取单条数据
	 * @param vid
	 * @return
	 */
	DataBean getData(int id);
	
	/**
	 * 获取单条数据
	 * @param vid
	 * @return
	 */
	DataBean getData(int vid,int userId);

}
