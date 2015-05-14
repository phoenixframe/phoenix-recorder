package com.youku.yks.paging;

import java.util.List;

import com.youku.yks.bean.StrategyBean;
import com.youku.yks.bean.UserBean;

public class StrategyPaging extends PagingController {
	private UserBean userBean;
	private String startTime;

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStartTime(){
		return startTime;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public int getStrategyBatchTotalRow(){
		String sql = "";
		if(startTime == null){
			sql = "SELECT COUNT(*) FROM p_strategy";				   
		} else {
			sql = "SELECT COUNT(*) FROM p_strategy WHERE startTime >= '"+startTime+"' AND userId='"+userBean.getId()+"';";
		} 
		return getVersion(sql);
	}
	
	public List<StrategyBean> getStrategyBatchQuery(int initPageNum,int perPageNum){		
		int num = (initPageNum-1) * perPageNum; //要被排除的行数
		String sql = "";
		if(startTime == null){
					sql = "SELECT * FROM p_strategy order by id desc limit "+num+","+perPageNum+"";				
			} else {
					sql = "SELECT * FROM p_strategy WHERE startTime >= '"+startTime+"' AND userId='"+userBean.getId()+"' order by id desc limit "+num+","+perPageNum+"";
			}		   
		return super.getBatchStrategy(sql);
	}
}

