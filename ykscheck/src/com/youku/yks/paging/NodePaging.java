package com.youku.yks.paging;

import java.util.List;

import com.youku.yks.bean.NodeBean;
import com.youku.yks.bean.UserBean;

/**
 * 对node节点进行分页控制
 * @author mengfeiyang
 *
 */
public class NodePaging  extends PagingController{
	
	private String ruleValue;
	private UserBean userBean;

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}

	public int getNodeTotalRow(){
		String sql = "";
		if(ruleValue == null){
			if(userBean.getLevel() == 0){
		       sql = "SELECT COUNT(*) FROM t_node";
			} else {
			   sql = "SELECT COUNT(*) FROM t_node WHERE userId='"+userBean.getId()+"'";
			}
		} else {
			if(userBean.getLevel() == 0){
				 sql = "SELECT COUNT(*) FROM t_node WHERE caseNodeId='"+ruleValue+"'";
			} else {
				 sql = "SELECT COUNT(*) FROM t_node WHERE caseNodeId='"+ruleValue+"' AND userId='"+userBean.getId()+"'";
			}
		  
		}
		return getVersion(sql);
	}
	
	public List<NodeBean> getNodeQuery(int initPageNum,int perPageNum){		
		int num = (initPageNum-1) * perPageNum; //要被排除的行数
		String sql = "";
		if(ruleValue == null){
			if(userBean.getLevel() == 0){
		        sql = "SELECT * FROM t_node limit "+num+","+perPageNum+"";
			} else {
				sql = "SELECT * FROM t_node WHERE userId='"+userBean.getId()+"' limit "+num+","+perPageNum+"";
			}
		}else{
			if(userBean.getLevel() == 0){
				sql = "SELECT * FROM t_node WHERE caseNodeId='"+ruleValue+"' limit "+num+","+perPageNum+"";
			}else{
				sql = "SELECT * FROM t_node WHERE caseNodeId='"+ruleValue+"' AND userId='"+userBean.getId()+"' limit "+num+","+perPageNum+"";
			}			
		}
		return super.getBatchNode(sql);
	}

}
