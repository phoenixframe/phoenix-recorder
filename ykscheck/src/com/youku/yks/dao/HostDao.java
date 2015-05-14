package com.youku.yks.dao;

import java.util.List;

import com.youku.yks.bean.HostBean;

/**
 * 对Host进行操作的接口
 * @author mengfeiyang
 * @version 1.0
 * @since JDK 1.7
 */
public interface HostDao {
	/**
	 * 新增一个Host
	 * @param hostBean
	 * @return
	 */
	int addHost(HostBean hostBean);
	
	/**
	 * 删除一个Host，根据Id
	 * @param id
	 * @return
	 */
	int deleHost(int id);
	
	/**
	 * 更新一个Host
	 * @param hostBean
	 * @return
	 */
	int updateHost(HostBean hostBean);
	
	/**
	 * 获取库中所有的Host记录
	 * @return
	 */
	List<HostBean> getAllHost();
	
	/**
	 * 获取库中所有的Host记录
	 * @return
	 */
	List<HostBean> getAllHostByUser(int userId);
	
	/**
	 * 获取一条Host记录，根据Id
	 * @param id
	 * @return
	 */
	HostBean getHost(int id);
	
	/**
	 * 获取一条Host记录，根据Host名称
	 * @param hostName
	 * @return
	 */
	HostBean getHost(String hostName,int userId) throws Exception;

}
