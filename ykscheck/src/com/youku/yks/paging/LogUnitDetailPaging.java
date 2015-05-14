package com.youku.yks.paging;

import java.util.List;

import com.youku.yks.bean.LogUnitBean;

public class LogUnitDetailPaging extends PagingController{
	private String ruleValue;
	private String batchId;
	private int status = 5;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getRuleValue() {
		return ruleValue;
	}

	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}
	public int getUnitLogTotalRow(){
		String sql = "";
			if(batchId == null){
				if(status == 5){
				    sql = "SELECT COUNT(*) FROM l_unit";
				} else {
					sql = "SELECT COUNT(*) FROM l_unit WHERE result='"+status+"'";
				}
			}else{
				if(status == 5){
				    sql = "SELECT COUNT(*) FROM l_unit WHERE batchId='"+batchId+"'";
				}else {
					sql = "SELECT COUNT(*) FROM l_unit WHERE result='"+status+"' AND batchId='"+batchId+"'";
				}
			}	    
		return getVersion(sql);
	}
	
	public List<LogUnitBean> getUnitLogQuery(int initPageNum,int perPageNum){		
		int num = (initPageNum-1) * perPageNum; //要被排除的行数
		String sql = "";
			if(batchId == null){
				if(status == 5){
				    sql = "SELECT * FROM l_unit limit "+num+","+perPageNum+"";
				} else {
					sql = "SELECT * FROM l_unit WHERE result='"+status+"' limit "+num+","+perPageNum+"";
				}
			}else{
				if(status == 5){
				    sql = "SELECT * FROM l_unit WHERE batchId='"+batchId+"' limit "+num+","+perPageNum+"";
				} else {
					sql = "SELECT * FROM l_unit WHERE result='"+status+"' AND batchId='"+batchId+"' limit "+num+","+perPageNum+"";
				}
			}
		return getBatchLogUnit(sql);
	}

}
