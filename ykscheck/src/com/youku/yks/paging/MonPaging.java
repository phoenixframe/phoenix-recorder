package com.youku.yks.paging;

import java.util.List;

import com.youku.yks.bean.MonitorBean;
import com.youku.yks.bean.UserBean;

public class MonPaging extends PagingController {
	private int batchId;
	private String caseType;
	private int status = 5;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}
	

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}


	private UserBean userBean;
		
	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public int getMonitorTotalRow(){
		String sql = "";
		
		if(caseType == null){
			if(userBean.getLevel() == 0){
				if(status == 5){
					sql = "SELECT COUNT(DISTINCT batchId,result,typeId) AS total FROM l_unit";
				} else {
					sql = "SELECT COUNT(DISTINCT batchId,result,typeId) AS total FROM l_unit WHERE result='"+status+"'";
				}
			}else{
				if(status == 5){
				   sql = "SELECT COUNT(DISTINCT batchId,result,typeId) AS total FROM l_unit u,l_batch b WHERE b.id = u.batchId AND b.userId='"+userBean.getId()+"'";
				} else {
					sql = "SELECT COUNT(DISTINCT batchId,result,typeId) AS total FROM l_unit u,l_batch b WHERE b.id = u.batchId AND u.result='"+status+"' AND b.userId='"+userBean.getId()+"'";
				}
			}
		} else {
			if(userBean.getLevel() == 0){
				if(status == 5){
					sql = "SELECT COUNT(DISTINCT batchId,result,typeId) AS total FROM l_unit WHERE typeId='"+caseType+"'";
				} else {
					sql = "SELECT COUNT(DISTINCT batchId,result,typeId) AS total FROM l_unit WHERE typeId='"+caseType+"' AND result='"+status+"'";
				}
			}else{
				if(status == 5){
					sql = "SELECT COUNT(DISTINCT batchId,result,typeId) AS total FROM l_unit u,l_batch b WHERE u.batchId = b.id AND u.typeId='"+caseType+"' AND userId ='"+userBean.getId()+"'";
				} else {
					sql = "SELECT COUNT(DISTINCT batchId,result,typeId) AS total FROM l_unit u,l_batch b WHERE u.batchId = b.id AND u.result='"+status+"' AND u.typeId='"+caseType+"' AND userId ='"+userBean.getId()+"'";
				}
			}
		}
		return getVersion(sql);
	}
	
	public List<MonitorBean> getMonitorQuery(int initPageNum,int perPageNum){		
		int num = (initPageNum-1) * perPageNum; //要被排除的行数
		String sql = "";		
		if(caseType == null){
			if(userBean.getLevel() == 0){
				if(status == 5){
					sql = "SELECT DISTINCT batchId,result,typeId FROM l_unit order by batchId desc limit "+num+","+perPageNum+"";
				} else {
					sql = "SELECT DISTINCT batchId,result,typeId FROM l_unit WHERE result='"+status+"' order by batchId desc limit "+num+","+perPageNum+"";
				}
			}else{
				if(status == 5){
				   sql = "SELECT DISTINCT u.batchId,u.result,u.typeId FROM l_unit u,l_batch b WHERE b.id = u.batchId AND b.userId='"+userBean.getId()+"' order by u.batchId desc limit "+num+","+perPageNum+"";
				} else {
					sql = "SELECT DISTINCT u.batchId,u.result,u.typeId FROM l_unit u,l_batch b WHERE b.id = u.batchId AND u.result='"+status+"' AND b.userId='"+userBean.getId()+"' order by u.batchId desc limit "+num+","+perPageNum+"";
				}
			}
		} else {
			if(userBean.getLevel() == 0){
				if(status == 5){
					sql = "SELECT DISTINCT batchId,result,typeId FROM l_unit WHERE typeId='"+caseType+"' order by batchId desc limit "+num+","+perPageNum+"";
				} else {
					sql = "SELECT DISTINCT batchId,result,typeId FROM l_unit WHERE typeId='"+caseType+"' AND result='"+status+"' order by batchId desc limit "+num+","+perPageNum+"";
				}
			}else{
				if(status == 5){
					sql = "SELECT DISTINCT u.batchId,u.result,u.typeId FROM l_unit u,l_batch b WHERE u.batchId = b.id AND u.typeId='"+caseType+"' AND userId ='"+userBean.getId()+"' order by u.batchId desc limit "+num+","+perPageNum+"";
				} else {
					sql = "SELECT DISTINCT u.batchId,u.result,u.typeId FROM l_unit u,l_batch b WHERE u.batchId = b.id AND u.result='"+status+"' AND u.typeId='"+caseType+"' AND userId ='"+userBean.getId()+"' order by u.batchId desc limit "+num+","+perPageNum+"";
				}
			}
		}
		return super.getBatchMonitor(sql);
	}

}
