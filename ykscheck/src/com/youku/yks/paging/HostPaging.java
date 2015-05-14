package com.youku.yks.paging;

import java.util.List;

import com.youku.yks.bean.HostBean;
import com.youku.yks.bean.UserBean;

public class HostPaging extends PagingController{
	private String ruleValue;
	private UserBean userBean;
	
	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public String getRuleValue() {
		return ruleValue;
	}

	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}

	public int getHostTotalRow(){
		String sql = "";
		if(ruleValue == null){
			if(userBean.getLevel() == 0){
				 sql = "SELECT COUNT(*) FROM t_host";
			}else {
				sql = "SELECT COUNT(*) FROM t_host WHERE userId='"+userBean.getId()+"'";
			}
		  
		} else {
			if(userBean.getLevel() == 0){
				sql = "SELECT COUNT(*) FROM t_host WHERE hostName='"+ruleValue+"'";
			} else {
				sql = "SELECT COUNT(*) FROM t_host WHERE hostName='"+ruleValue+"' AND userId='"+userBean.getId()+"'";
			}
		   
		}
		return getVersion(sql);
	}
	
	public List<HostBean> getHostQuery(int initPageNum,int perPageNum){		
		int num = (initPageNum-1) * perPageNum; //要被排除的行数
		String sql = "";
		if(ruleValue == null){
			if(userBean.getLevel() == 0){
				sql = "SELECT * FROM t_host limit "+num+","+perPageNum+"";
			} else {
				sql = "SELECT * FROM t_host WHERE userId='"+userBean.getId()+"' limit "+num+","+perPageNum+"";
			}		   
		}else{
			if(userBean.getLevel() == 0){
				sql = "SELECT * FROM t_host WHERE hostName='"+ruleValue+"' limit "+num+","+perPageNum+"";
			} else {
				sql = "SELECT * FROM t_host WHERE hostName='"+ruleValue+"' AND userId='"+userBean.getId()+"' limit "+num+","+perPageNum+"";
			}			
		}
		return super.getBatchHost(sql);
	}

}
