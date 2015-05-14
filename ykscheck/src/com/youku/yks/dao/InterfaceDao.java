package com.youku.yks.dao;

import java.util.List;

import com.youku.yks.bean.InterfaceBean;

/**
 * 对参数操作的接口
 * @author mengfeiyang
 *
 */
public interface InterfaceDao {
	/**
	 * 增加一个接口
	 * @param requestData
	 * @return
	 */
	int addInterface(InterfaceBean requestData);
	
	/**
	 * 删除一个接口，根据Id
	 * @param id
	 * @return
	 */
	int deleInterface(int id);
	
	/**
	 * 更新接口
	 * @param requestData
	 * @return
	 */
	int updateInterface(InterfaceBean requestData);
	
	/**
	 * 获取所有接口内容
	 * @return
	 */
	List<InterfaceBean> getAllInterface();
	
	/**
	 * 获取所有接口内容,根据用户
	 * @return
	 */
	List<InterfaceBean> getAllInterfaceByUser(int userId);
	
	/**
	 * 获取一条接口内容,根据Id
	 * @return
	 */
	InterfaceBean getInterface(int id);
	
	/**
	 * 获取一条接口内容,根据Id
	 * @return
	 * @throws Exception 
	 */
	InterfaceBean getInterface(String name,int userId) throws Exception;
}
