package com.youku.yks.paging;

import java.util.List;

import com.youku.yks.bean.DataBean;
import com.youku.yks.bean.UserBean;

public class DataPaging extends PagingController{
		
	private String nodeValue;
	private String caseNodeId;
	private UserBean userBean;
	

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public String getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

	public String getCaseNodeId() {
		return caseNodeId;
	}

	public void setCaseNodeId(String caseNodeId) {
		this.caseNodeId = caseNodeId;
	}

	public int getDataTotalRow(){
		String sql = "";
		if(nodeValue == null){
			if(userBean.getLevel() == 0){
		        sql = "SELECT COUNT(*) FROM t_data";
			} else {
				sql = "SELECT COUNT(*) FROM t_data WHERE userId='"+userBean.getId()+"'";
			}
		} else {
			if(userBean.getLevel() == 0){
		        sql = "SELECT COUNT(*) FROM t_data d, t_node n WHERE d.paramNodeId = n.id AND n.Id='"+caseNodeId+"' AND n.paramNode='"+nodeValue+"';";
			} else {
				sql = "SELECT COUNT(*) FROM t_data d, t_node n WHERE d.paramNodeId = n.id AND n.Id='"+caseNodeId+"' AND n.paramNode='"+nodeValue+"' AND d.userId='"+userBean.getId()+"';";
			}
		}
		return getVersion(sql);
	}
	
	public List<DataBean> getDataQuery(int initPageNum,int perPageNum){		
		int num = (initPageNum-1) * perPageNum; //要被排除的行数
		String sql = "";
		if(nodeValue == null){
			if(userBean.getLevel() == 0){
		        sql = "SELECT * FROM t_data limit "+num+","+perPageNum+"";
			}else{
				sql = "SELECT * FROM t_data WHERE userId='"+userBean.getId()+"' limit "+num+","+perPageNum+"";
			}
		}else{
			if(userBean.getLevel() == 0){
			   sql = "SELECT * FROM t_data d, t_node n WHERE d.paramNodeId = n.id AND n.Id='"+caseNodeId+"' AND n.paramNode='"+nodeValue+"' limit "+num+","+perPageNum+"";
			}else{
				sql = "SELECT * FROM t_data d, t_node n WHERE d.paramNodeId = n.id AND n.Id='"+caseNodeId+"' AND n.paramNode='"+nodeValue+"' AND d.userId='"+userBean.getId()+"' limit "+num+","+perPageNum+"";
			}
		}
		return super.getBatchCaseData(sql);
	}

}
