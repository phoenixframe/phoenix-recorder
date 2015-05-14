package com.youku.yks.paging;

import java.util.List;

import com.youku.yks.bean.CaseBean;
import com.youku.yks.bean.UserBean;

/**
 * 对测试用例分页做控制
 * @author mengfeiyang
 *
 */
public class CasePaging extends PagingController{
	
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

	public int getCaseTotalRow(){
		String sql = "";
		
		if(ruleValue == null){
			if(userBean.getLevel() == 0){
		        sql = "SELECT COUNT(*) FROM t_case";
			}else{
				sql = "SELECT COUNT(*) FROM t_case WHERE userId='"+userBean.getId()+"'";
			}
		} else {
			if(userBean.getLevel() == 0){
		        sql = "SELECT COUNT(*) FROM t_case WHERE typeNameId='"+ruleValue+"'";
			}else{
				sql = "SELECT COUNT(*) FROM t_case WHERE typeNameId='"+ruleValue+"' AND userId='"+userBean.getId()+"'";
			}
		}
		return getVersion(sql);
	}
	
	public List<CaseBean> getCaseQuery(int initPageNum,int perPageNum){		
		int num = (initPageNum-1) * perPageNum; //要被排除的行数
		String sql = "";
		
		if(ruleValue == null){
			if(userBean.getLevel() == 0){
		        sql = "SELECT * FROM t_case limit "+num+","+perPageNum+"";
			}else{
				sql = "SELECT * FROM t_case WHERE userId='"+userBean.getId()+"' limit "+num+","+perPageNum+"";
			}
		} else {
			if(userBean.getLevel() == 0){
		        sql = "SELECT * FROM t_case WHERE typeNameId='"+ruleValue+"' limit "+num+","+perPageNum+"";
			}else{
				sql = "SELECT * FROM t_case WHERE typeNameId='"+ruleValue+"' AND userId='"+userBean.getId()+"' limit "+num+","+perPageNum+"";
			}
		}
		return super.getBatchCase(sql);
	}

}
