package com.youku.yks.dao;

import java.util.List;

import com.youku.yks.bean.UserBean;

/**
 * 对用户进行操作的接口
 * @author mengfeiyang
 *
 */
public interface UserDao {
	/**
	 * 添加一个用户
	 * @param user
	 * @return
	 */
	int addUser(UserBean user);
	
	/**
	 * 删除一个用户
	 * @param id
	 * @return
	 */
	int delUser(int id);
	
	/**
	 * 删除一个用户
	 * @param name
	 * @return
	 */
	int delUser(String name);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	int updateUser(UserBean user);
	
	/**
	 * 获取一个用户信息
	 * @param id
	 * @return
	 */
	UserBean getUser(int id);
	
	/**
	 * 获取用户信息  根据name
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	UserBean getUser(String name) throws Exception;
	
	/**
	 * 获取所有用户
	 * @return
	 */
	List<UserBean> getAllUser();
	
	/**
	 * 根据用户初始化的级别获取用户
	 * @param level
	 * @return
	 */
	List<UserBean> getAllUserByLevel(int level);

}
