package com.youku.yks.paging;

import java.util.List;

import com.youku.yks.bean.CodeBean;

public class CodePaging extends PagingController{
	private String ruleValue;
	
	public String getRuleValue() {
		return ruleValue;
	}

	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}

	public int getCodeTotalRow(){
		String sql = "";
		if(ruleValue == null){
		   sql = "SELECT COUNT(*) FROM t_code";
		} else {
		   sql = "SELECT COUNT(*) FROM t_code WHERE code='"+ruleValue+"'";
		}
		return getVersion(sql);
	}
	
	public List<CodeBean> getCodeQuery(int initPageNum,int perPageNum){		
		int num = (initPageNum-1) * perPageNum; //要被排除的行数
		String sql = "";
		if(ruleValue == null){
		   sql = "SELECT * FROM t_code limit "+num+","+perPageNum+"";
		}else{
			sql = "SELECT * FROM t_code WHERE code='"+ruleValue+"' limit "+num+","+perPageNum+"";
		}
		return super.getBatchCodeList(sql);
	}
}
