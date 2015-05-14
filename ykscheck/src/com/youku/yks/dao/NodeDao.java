package com.youku.yks.dao;

import java.util.List;

import com.youku.yks.bean.NodeBean;
import com.youku.yks.bean.UserBean;

/**
 * 对用例节点进行操作的接口<br>
 * 
 * <em>开发时间：2014年7月29日 9:03
 * @author mengfeiyang
 * @version 1.0
 * @since JDK 1.7 
 *
 */
public interface NodeDao {
	/**
	 * 增加一个用例的节点
	 * @param node
	 * @return
	 */
	int addNode(NodeBean node);
	
	/**
	 * 删除一条节点记录，根据id
	 * @param id
	 * @return
	 */
	int deleNode(int id);
	
	/**
	 * 对一条记录进行更新
	 * @param node
	 * @return
	 */
	int updateNode(NodeBean node);
	
	/**
	 * 获取所有的节点，包括可用与不可用
	 * @return
	 */
	List<NodeBean> getAllNode();
	
	/**
	 * 获取所有的节点，包括可用与不可用
	 * @return
	 */
	List<NodeBean> getAllNodeByUser(UserBean userBean);
	
	/**
	 * 获得所有可用的节点
	 * @return
	 */
	List<NodeBean> getAllEnabledNodeByUser(int userId);
	
	/**
	 * 根据用例的id获取所有的节点
	 * @param id
	 * @return
	 */
	List<NodeBean> getAllNodeByCaseNodeId(int id,int userId);
	
	/**
	 * 根据用例id获取所有可用的节点
	 * @param id
	 * @return
	 */
	List<NodeBean> getAllEnabledNodeByCaseNodeId(int id,int userID);
	
	/**
	 * 根据id获取单条的Node信息
	 * @param id
	 * @return
	 */
	NodeBean getUnitNode(int id);

}
