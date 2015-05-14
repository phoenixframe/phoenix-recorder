package com.youku.yks.paging;

import java.util.List;

import com.youku.yks.bean.InterfaceBean;
import com.youku.yks.bean.UserBean;

public class InterfacePaging extends PagingController{
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

	public int getReqDataTotalRow(){
		String sql = "";
		if(ruleValue == null){
			if(userBean.getLevel() == 0){
		       sql = "SELECT COUNT(*) FROM t_interface";
			} else {
			   sql = "SELECT COUNT(*) FROM t_interface WHERE userId='"+userBean.getId()+"'";
			}
		} else {
			if(userBean.getLevel() == 0){
		       sql = "SELECT COUNT(*) FROM t_interface WHERE interface='"+ruleValue+"'";
			} else {
				sql = "SELECT COUNT(*) FROM t_interface WHERE interface='"+ruleValue+"' AND userId='"+userBean.getId()+"'";
			}
		}
		return getVersion(sql);
	}
	
	public List<InterfaceBean> getReqDataQuery(int initPageNum,int perPageNum){		
		int num = (initPageNum-1) * perPageNum; //要被排除的行数
		String sql = "";
		if(ruleValue == null){
			if(userBean.getLevel() ==0 ){
		         sql = "SELECT * FROM t_interface limit "+num+","+perPageNum+"";
			} else {
				sql = "SELECT * FROM t_interface WHERE userId='"+userBean.getId()+"' limit "+num+","+perPageNum+"";
			}
		}else{
			if(userBean.getLevel() == 0){
			    sql = "SELECT * FROM t_interface WHERE interface='"+ruleValue+"' limit "+num+","+perPageNum+"";
			} else {
				sql = "SELECT * FROM t_interface WHERE interface='"+ruleValue+"' AND userId='"+userBean.getId()+"' limit "+num+","+perPageNum+"";
			}
		}
		return super.getBatchRequestData(sql);
	}
}
