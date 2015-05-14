package com.youku.yks.paging;

import com.youku.yks.batchoper.BatchDataOper;
import com.youku.yks.bean.PagingBean;

/**
 * 分页控制器，用于为分页提供一个通用的方法
 * @author mengfeiyang
 *
 */
public class PagingController extends BatchDataOper {
	private int strPageNum;;
	private int numPerPage;;
	private int totalRow;
	
	/**
	 * 初始化前端传出的数据<br>
	 * 
	 * @author mengfeyang
	 * @param strPageNum
	 * @param numPerPage
	 * @param totalRow
	 */
	public void init(int strPageNum,int numPerPage,int totalRow){
		this.strPageNum = strPageNum;
		this.numPerPage = numPerPage;
		this.totalRow = totalRow;
	}
	
	public PagingBean setPagingBean(){
		PagingBean pagingBean = new PagingBean();
		
		pagingBean.setTotalRow(totalRow); //设置总行数   				
		pagingBean.setNumPerPage(numPerPage); //每页显示条数
		pagingBean.setCurrentPage(strPageNum); //设置当前页 
		pagingBean.setTotalPage(); //设置总页数 

		return pagingBean;
	}
	
	
}

