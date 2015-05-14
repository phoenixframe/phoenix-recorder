package com.youku.yks.paging;

import java.util.List;

import com.youku.yks.bean.UserBean;
import com.youku.yks.bean.VidBean;

/**
 * 对vid分页做控制
 * @author mengfeiyang
 *
 */
public class VidPaging extends PagingController {
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

	public int getVidTotalRow(){
		String sql = "";
		if(ruleValue == null){
			if(userBean.getLevel() == 0){
				sql = "SELECT COUNT(*) FROM t_vid";
			} else {
				sql = "SELECT COUNT(*) FROM t_vid WHERE userId='"+userBean.getId()+"'";
			}
		   
		} else {
			if(userBean.getLevel() == 0){
				sql = "SELECT COUNT(*) FROM t_vid WHERE vid='"+ruleValue+"'";
			} else {
				sql = "SELECT COUNT(*) FROM t_vid WHERE vid='"+ruleValue+"' AND userId='"+userBean.getId()+"'";
			}		   
		}
		return getVersion(sql);
	}
	
	public List<VidBean> getVidQuery(int initPageNum,int perPageNum){		
		int num = (initPageNum-1) * perPageNum; //要被排除的行数
		String sql = "";
		if(ruleValue == null){
			if(userBean.getLevel() == 0){
				sql = "SELECT * FROM t_vid limit "+num+","+perPageNum+"";
			} else {
				sql = "SELECT * FROM t_vid WHERE userId='"+userBean.getId()+"' limit "+num+","+perPageNum+"";
			}
		   
		}else{
			if(userBean.getLevel() == 0){
				sql = "SELECT * FROM t_vid WHERE vid='"+ruleValue+"' limit "+num+","+perPageNum+"";
			}else{
				sql = "SELECT * FROM t_vid WHERE vid='"+ruleValue+"' AND userId='"+userBean.getId()+"' limit "+num+","+perPageNum+"";
			}
		}
		return super.getBatchVid(sql);
	}
}
