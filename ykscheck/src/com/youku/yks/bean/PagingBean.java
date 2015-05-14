package com.youku.yks.bean;

/**
 * 分页使用的数据类型Bean,各字段意义：<br>
 *   numPerPage：每页显示的行数<br>
 *   totalRow：总行数<br>
 *   totalPage:总页数<br>
 *   currentPage:当前页数<br>
 * @author mengfeiyang
 *
 */
public class PagingBean {
	private int numPerPage = 1;
	private int totalRow;
	private int totalPage;
	private int currentPage;	
	
	public PagingBean(int numPerPage, int totalRow, int totalPage,
			int currentPage) {
		super();
		this.numPerPage = numPerPage;
		this.totalRow = totalRow;
		this.totalPage = totalPage;
		this.currentPage = currentPage;
	}
	public PagingBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}
	public int getTotalRow() {
		return totalRow;
	}
	public void setTotalRow(int totalRow) {
		
		this.totalRow = totalRow;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage() {	
		//通过总行数设置总页数
		if (totalRow % numPerPage == 0) {//如果总行数除以每页显示的行数余数为零时，总页数等于它们的商
			totalPage = totalRow / numPerPage;
		} else {//否则，总页数等于它们的商加1
			totalPage = totalRow / numPerPage + 1;
		}		
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
}
