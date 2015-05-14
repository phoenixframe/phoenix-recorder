package com.youku.yks.paging;

import java.util.List;

import com.youku.yks.bean.UserBean;

public class UserPaging extends PagingController{
	private String ruleValue;
	
	public String getRuleValue() {
		return ruleValue;
	}

	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}

	public int getUserTotalRow(){
		String sql = "";
		if(ruleValue == null){
		   sql = "SELECT COUNT(*) FROM t_user";
		} else {
		   sql = "SELECT COUNT(*) FROM t_user WHERE id='"+ruleValue+"'";
		}
		return getVersion(sql);
	}
	
	public List<UserBean> getUserQuery(int initPageNum,int perPageNum){		
		int num = (initPageNum-1) * perPageNum; //要被排除的行数
		String sql = "";
		if(ruleValue == null){
		   sql = "SELECT * FROM t_user limit "+num+","+perPageNum+"";
		}else{
			sql = "SELECT * FROM t_user WHERE id='"+ruleValue+"' limit "+num+","+perPageNum+"";
		}
		return super.getBatchUser(sql);
	}
}
