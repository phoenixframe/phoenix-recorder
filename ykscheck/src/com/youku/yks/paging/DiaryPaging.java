package com.youku.yks.paging;

import java.util.List;

import com.youku.yks.bean.DiaryBean;
import com.youku.yks.bean.UserBean;

public class DiaryPaging extends PagingController {
	private String ruleValue;
	private UserBean userBean;
	private String startTime;
	private String endTime;
		
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

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

	public int getDiaryTotalRow(){
		String sql = "";
		
		if(ruleValue == null){
			if(userBean.getLevel() == 0){
				if(startTime != null && endTime == null){
					 sql = "SELECT COUNT(*) FROM k_diary WHERE DATE(createtime) >= '"+startTime+"'";
				} else if (endTime != null && startTime == null){
					sql = "SELECT COUNT(*) FROM k_diary WHERE DATE(createtime) <= '"+endTime+"'";
				} else if (endTime != null && startTime != null){
					sql = "SELECT COUNT(*) FROM k_diary WHERE DATE(createtime) BETWEEN '"+startTime+"' AND '"+endTime+"'";
				} else {
					 sql = "SELECT COUNT(*) FROM k_diary";
				}		       
			}else{
				if(startTime != null && endTime == null){
					 sql = "SELECT COUNT(*) FROM k_diary WHERE DATE(createtime) >= '"+startTime+"' AND userId='"+userBean.getId()+"'";
				} else if (endTime != null && startTime == null){
					sql = "SELECT COUNT(*) FROM k_diary WHERE DATE(createtime) <= '"+endTime+"' AND userId='"+userBean.getId()+"'";
				} else if (endTime != null && startTime != null){
					sql = "SELECT COUNT(*) FROM k_diary WHERE DATE(createtime) BETWEEN '"+startTime+"' AND '"+endTime+"' AND userId='"+userBean.getId()+"'";
				} else {
					 sql = "SELECT COUNT(*) FROM k_diary WHERE userId='"+userBean.getId()+"'";
				}
			}
		} else {
			if(userBean.getLevel() == 0){
				if(startTime != null && endTime == null){
					 sql = "SELECT COUNT(*) FROM k_diary WHERE DATE(createtime) >= '"+startTime+"' AND content LIKE '%"+ruleValue+"%'";
				} else if (endTime != null && startTime == null){
					sql = "SELECT COUNT(*) FROM k_diary WHERE DATE(createtime) <= '"+endTime+"'  AND content LIKE '%"+ruleValue+"%'";
				} else if (endTime != null && startTime != null){
					sql = "SELECT COUNT(*) FROM k_diary WHERE DATE(createtime) BETWEEN '"+startTime+"' AND '"+endTime+"' AND content LIKE '%"+ruleValue+"%'";
				} else {
					 sql = "SELECT COUNT(*) FROM k_diary  WHERE content LIKE '"+ruleValue+"'";
				}
			}else{
				if(startTime != null && endTime == null){
					 sql = "SELECT COUNT(*) FROM k_diary WHERE DATE(createtime) >= '"+startTime+"' AND userId='"+userBean.getId()+"' AND content LIKE '%"+ruleValue+"%'";
				} else if (endTime != null && startTime == null){
					sql = "SELECT COUNT(*) FROM k_diary WHERE DATE(createtime) <= '"+endTime+"' AND userId='"+userBean.getId()+"' AND content LIKE '%"+ruleValue+"%'";
				} else if (endTime != null && startTime != null){
					sql = "SELECT COUNT(*) FROM k_diary WHERE DATE(createtime) BETWEEN '"+startTime+"' AND '"+endTime+"' AND userId='"+userBean.getId()+"' AND content LIKE '%"+ruleValue+"%'";
				} else {
					 sql = "SELECT COUNT(*) FROM k_diary WHERE userId='"+userBean.getId()+"' AND content LIKE '%"+ruleValue+"%'";
				}
			}
		}
		return getVersion(sql);
	}
	
	public List<DiaryBean> getDiaryQuery(int initPageNum,int perPageNum){		
		int num = (initPageNum-1) * perPageNum; //要被排除的行数
		String sql = "";
		
		if(ruleValue == null){
			if(userBean.getLevel() == 0){
				if(startTime != null && endTime == null){
					sql = "SELECT * FROM k_diary WHERE DATE(createtime) >= '"+startTime+"' order by id desc limit "+num+","+perPageNum+"";
				} else if (endTime != null && startTime == null){
					sql = "SELECT * FROM k_diary WHERE DATE(createtime) <= '"+endTime+"' order by id desc limit "+num+","+perPageNum+"";
				} else if (endTime != null && startTime != null){
					sql = "SELECT * FROM k_diary WHERE DATE(createtime) BETWEEN '"+startTime+"' AND '"+endTime+"' order by id desc limit "+num+","+perPageNum+"";
				} else {
					sql = "SELECT * FROM k_diary order by id desc limit "+num+","+perPageNum+"";
				}
			}else{
				if(startTime != null && endTime == null){
					sql = "SELECT * FROM k_diary WHERE DATE(createtime) >= '"+startTime+"' AND userId='"+userBean.getId()+"' order by id desc limit "+num+","+perPageNum+"";
				} else if (endTime != null && startTime == null){
					sql = "SELECT * FROM k_diary WHERE DATE(createtime) <= '"+endTime+"' AND userId='"+userBean.getId()+"' order by id desc limit "+num+","+perPageNum+"";
				} else if (endTime != null && startTime != null){
					sql = "SELECT * FROM k_diary WHERE DATE(createtime) BETWEEN '"+startTime+"' AND '"+endTime+"' AND userId='"+userBean.getId()+"' order by id desc limit "+num+","+perPageNum+"";
				} else {
					sql = "SELECT * FROM k_diary WHERE userId='"+userBean.getId()+"' order by id desc limit "+num+","+perPageNum+"";
				}
			}
		} else {
			if(userBean.getLevel() == 0){
				if(startTime != null && endTime == null){
					sql = "SELECT * FROM k_diary WHERE DATE(createtime) >= '"+startTime+"' AND content LIKE '%"+ruleValue+"%' limit "+num+","+perPageNum+"";
				} else if (endTime != null && startTime == null){
					sql = "SELECT * FROM k_diary WHERE DATE(createtime) <= '"+endTime+"'  AND content LIKE '%"+ruleValue+"%' limit "+num+","+perPageNum+"";
				} else if (endTime != null && startTime != null){
					sql = "SELECT * FROM k_diary WHERE DATE(createtime) BETWEEN '"+startTime+"' AND '"+endTime+"' AND content LIKE '%"+ruleValue+"%' limit "+num+","+perPageNum+"";
				} else {
					sql = "SELECT * FROM k_diary  WHERE content LIKE '%"+ruleValue+"%' limit "+num+","+perPageNum+"";
				}
			}else{
				if(startTime != null && endTime == null){
					sql = "SELECT * FROM k_diary WHERE DATE(createtime) >= '"+startTime+"' AND userId='"+userBean.getId()+"' AND content LIKE '%"+ruleValue+"%' limit "+num+","+perPageNum+"";
				} else if (endTime != null && startTime == null){
					sql = "SELECT * FROM k_diary WHERE DATE(createtime) <= '"+endTime+"' AND userId='"+userBean.getId()+"' AND content LIKE '%"+ruleValue+"%' limit "+num+","+perPageNum+"";
				} else if (endTime != null && startTime != null){
					sql = "SELECT * FROM k_diary WHERE DATE(createtime) BETWEEN '"+startTime+"' AND '"+endTime+"' AND userId='"+userBean.getId()+"' AND content LIKE '%"+ruleValue+"%' limit "+num+","+perPageNum+"";
				} else {
					sql = "SELECT * FROM k_diary WHERE userId='"+userBean.getId()+"' AND content LIKE '%"+ruleValue+"%' limit "+num+","+perPageNum+"";
				}
			}
		}
		return super.getBatchDiary(sql);
	}

}
