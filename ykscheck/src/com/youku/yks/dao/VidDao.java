package com.youku.yks.dao;

import java.util.List;

import com.youku.yks.bean.VidBean;

/**
 * 对Vid进行操作的接口
 * @author mengfeiyang
 *
 */
public interface VidDao {
	/**
	 * 增加一条记录
	 * @param vidBean
	 * @return
	 */
	int addVid(VidBean vidBean);
	
	/**
	 * 删除一条记录，根据id
	 * @param id
	 * @return
	 */
	int deleVidById(int id);
	
	/**
	 * 更新一条记录
	 * @param vidBean
	 * @return
	 */
	int updateVid(VidBean vidBean);
	
	/**
	 * 获取所有Vid
	 * @return
	 */
	List<VidBean> getAllVid();
	
	/**
	 * 获取所有Vid，根据userId
	 * @return
	 */
	List<VidBean> getAllVidByUser(int userId);
	
	/**
	 * 获取一条记录，根据id
	 * @param id
	 * @return
	 */
	VidBean getVidById(int id);
	
	/**
	 * 获取一条记录，根据Name
	 * @param vid
	 * @return
	 * @throws Exception 
	 */
	VidBean getVidByName(String vid,int userId) throws Exception;
}
