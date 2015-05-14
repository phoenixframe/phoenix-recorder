package com.youku.yks.paging;

import java.util.List;

import com.youku.yks.bean.LogBatchBean;
import com.youku.yks.bean.UserBean;

public class LogBatchPaging extends PagingController {
	private String ruleValue;
	private UserBean userBean;
	private String startTime;
	private String endTime;

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}

	public int getLogBatchTotalRow(){
		String sql = "";
		if(ruleValue == null){
			if(userBean  == null || userBean.getLevel() == 0){
				if(startTime != null && endTime == null){
					sql = "SELECT COUNT(*) FROM l_batch WHERE createtime >= '"+startTime+"';";
				} else if(endTime != null && startTime == null){
					sql = "SELECT COUNT(*) FROM l_batch WHERE createtime <= '"+endTime+"';";
				} else if(startTime != null && endTime != null){
					sql = "SELECT COUNT(*) FROM l_batch WHERE createtime BETWEEN '"+startTime+"' AND '"+endTime+"'";
				} else {
					sql = "SELECT COUNT(*) FROM l_batch";
				}				
			} else {
				if(startTime != null && endTime == null){
					sql = "SELECT COUNT(*) FROM l_batch WHERE createtime >= '"+startTime+"' AND userId='"+userBean.getId()+"';";
				} else if(endTime != null && startTime == null){
					sql = "SELECT COUNT(*) FROM l_batch WHERE createtime <= '"+endTime+"' AND userId='"+userBean.getId()+"';";
				} else if(startTime != null && endTime != null){
					sql = "SELECT COUNT(*) FROM l_batch WHERE createtime BETWEEN '"+startTime+"' AND '"+endTime+"' AND userId='"+userBean.getId()+"'";
				} else {
					sql = "SELECT COUNT(*) FROM l_batch WHERE userId='"+userBean.getId()+"'";
				}
			}		   
		} else {
			return 1;
		} 
		return getVersion(sql);
	}
	
	public List<LogBatchBean> getLogBatchQuery(int initPageNum,int perPageNum){		
		int num = (initPageNum-1) * perPageNum; //要被排除的行数
		String sql = "";
		if(ruleValue == null){
			if(userBean  == null || userBean.getLevel() == 0 ){
				if(startTime != null && endTime == null){
					sql = "SELECT * FROM l_batch WHERE createtime >= '"+startTime+"' order by id desc limit "+num+","+perPageNum+"";
				} else if(endTime != null && startTime == null){
					sql = "SELECT * FROM l_batch WHERE createtime <= '"+endTime+"' order by id desc limit "+num+","+perPageNum+"";
				} else if (startTime != null && endTime != null){
					sql = "SELECT * FROM l_batch WHERE createtime BETWEEN '"+startTime+"' AND '"+endTime+"' order by id desc limit "+num+","+perPageNum+"";
				} else {
					sql = "SELECT * FROM l_batch order by id desc limit "+num+","+perPageNum+"";
				}				
			} else {
				if(startTime != null && endTime == null){
					sql = "SELECT * FROM l_batch WHERE createtime >= '"+startTime+"' AND userId='"+userBean.getId()+"' order by id desc limit "+num+","+perPageNum+"";
				} else if(endTime != null && startTime == null){
					sql = "SELECT * FROM l_batch WHERE createtime <= '"+endTime+"' AND userId='"+userBean.getId()+"' order by id desc limit "+num+","+perPageNum+"";
				} else if(startTime != null && endTime != null){
					sql = "SELECT * FROM l_batch WHERE createtime BETWEEN '"+startTime+"' AND '"+endTime+"' AND userId='"+userBean.getId()+"' order by id desc limit "+num+","+perPageNum+"";
				} else {
					sql = "SELECT * FROM l_batch WHERE userId='"+userBean.getId()+"' order by id desc limit "+num+","+perPageNum+"";
				}
			}		   
		} else {
			sql = "SELECT * FROM l_batch WHERE batchvalue='"+ruleValue+"'";
		} 
		return super.getBatchLog(sql);
	}
}
