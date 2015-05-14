package com.youku.yks.dao.impl;

import java.util.List;

import com.youku.yks.batchoper.BatchDataOper;
import com.youku.yks.bean.NodeBean;
import com.youku.yks.bean.UserBean;
import com.youku.yks.dao.NodeDao;

/**
 * Node节点实现类
 * @author mengfeiyang
 *
 */
public class NodeImpl extends BatchDataOper implements NodeDao {
	/**
	 * 增加一个用例的节点
	 * @param node
	 * @return
	 */
	@Override
	public int addNode(NodeBean node) {
		String sql = "INSERT INTO t_node VALUES(0,'"+node.getIsDisabled()
				+"','"+node.getUserId()
				+"','"+node.getCaseNodeId()
				+"','"+node.getParamNode()
				+"','"+node.getRemark()+"',0);";
		
		return getUpdateResult(sql);
	}

	/**
	 * 删除一条节点记录，根据id
	 * @param id
	 * @return
	 */
	@Override
	public int deleNode(int id) {
		String sql = "DELETE FROM t_node WHERE id='"+id+"';";
		return getUpdateResult(sql);
	}

	/**
	 * 对一条记录进行更新
	 * @param node
	 * @return
	 */
	@Override
	public int updateNode(NodeBean node) {
		String vsql = "SELECT version FROM t_node WHERE  id='"+node.getId()+"';";		
		int version = getVersion(vsql);
		
		String sql = "UPDATE t_node SET isDisabled='"+node.getIsDisabled()
				+"',caseNodeId='"+node.getCaseNodeId()
				+"',paramNode='"+node.getParamNode()
				+"',remark='"+node.getRemark()
				+"',version = version + 1 WHERE id='"+node.getId()+"' and version='"+version+"';";
		
		return getUpdateResult(sql);
	}

	/**
	 * 获取所有的节点，包括可用与不可用
	 * @return
	 */
	@Override
	public List<NodeBean> getAllNode() {
		String sql = "SELECT * FROM t_node;";
		return getBatchNode(sql);
	}

	/**
	 * 根据用例的id获取所有的节点
	 * @param id
	 * @return
	 */
	@Override
	public List<NodeBean> getAllNodeByCaseNodeId(int id,int userId) {
		String sql = "SELECT * FROM t_node WHERE caseNodeId='"+id+"' AND userId='"+userId+"';";
		return getBatchNode(sql);
	}

	/**
	 * 根据用例id获取所有可用的节点
	 * @param id
	 * @return
	 */
	@Override
	public List<NodeBean> getAllEnabledNodeByCaseNodeId(int id,int userId) {
		String sql = "SELECT * FROM t_node WHERE isDisabled='1' AND caseNodeId='"+id+"' AND userId='"+userId+"';";
		return getBatchNode(sql);
	}
	
	/**
	 * 根据id获取单条的Node信息
	 * @param id
	 * @return
	 */
	@Override
	public NodeBean getUnitNode(int id) {
		String sql = "SELECT * FROM t_node WHERE id='"+id+"';";
		return getBatchNode(sql).get(0);
	}

	@Override
	public List<NodeBean> getAllNodeByUser(UserBean userBean) {
		String sql = "SELECT * FROM t_node WHERE userId='"+userBean.getId()+"'";
		return getBatchNode(sql);
	}

	@Override
	public List<NodeBean> getAllEnabledNodeByUser(int userId) {
		String sql = "SELECT * FROM t_node WHERE isDisabled='1' AND userId='"+userId+"'";
		return getBatchNode(sql);
	}

}
