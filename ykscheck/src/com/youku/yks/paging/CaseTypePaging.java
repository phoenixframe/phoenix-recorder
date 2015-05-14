package com.youku.yks.paging;

import java.util.List;

import com.youku.yks.bean.CaseTypeBean;
import com.youku.yks.bean.UserBean;

public class CaseTypePaging extends PagingController {	
    private UserBean user;
    
	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public int getCaseTypeTotalRow(){
		String sql = "";
		if(user != null && user.getLevel() == 0){
			sql  = "SELECT COUNT(*) FROM t_type";
		} else {
			sql  = "SELECT COUNT(*) FROM t_type WHERE userid='"+user.getId()+"'";
		}

		return getVersion(sql);
	}
	
	public List<CaseTypeBean> getCaseTypeQuery(int initPageNum,int perPageNum){		
		int num = (initPageNum-1) * perPageNum; //要被排除的行数
		String sql;
		if(user != null && user.getLevel() == 0){
			sql  = "SELECT * FROM t_type limit "+num+","+perPageNum+"";
		} else {
			sql  = "SELECT * FROM t_type WHERE userid='"+user.getId()+"' limit "+num+","+perPageNum+"";
		}
		return super.getBatchCaseType(sql);
	}

}
