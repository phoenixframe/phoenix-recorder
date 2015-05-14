package com.youku.yks.paging;

import com.youku.yks.bean.PagingBean;

/**
 * 数据进行分页显示
 * @author mengfeiyang
 *
 */
public class PagingCommon extends PagingController {
	private String strPageNum;//当前页数,前端返回的数据为String类型，在此直接使用。这样做可省去在前端对数据的判断操作。
	private String numPerPage;//每页显示多少行
	private int initPageNum;
	private int perPageNum;
	private int totalRow;
	
	public int getTotalRow() {
		return totalRow;
	}


	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}


	public String getStrPageNum() {
		return strPageNum;
	}


	public void setStrPageNum(String strPageNum) {
		this.strPageNum = strPageNum;
	}


	public String getNumPerPage() {
		return numPerPage;
	}


	public void setNumPerPage(String numPerPage) {
		this.numPerPage = numPerPage;
	}

	/**
	 * 设置当前页，每页显示的条数。如果发生异常，则初始化当前页为1，每页显示10条。
	 * 由于前端通过getParameter获取的值为String类型，为了不在前端进行逻辑判断，故采用此方法
	 * @author mengfeiyang
	 * @return
	 */
	public PagingBean getPagingData(){
		try{
			initPageNum = Integer.parseInt(strPageNum);
			perPageNum = Integer.parseInt(numPerPage);		
		}catch(Exception e){
			initPageNum = 1;
			perPageNum = 20;
			setStrPageNum(initPageNum+"");
			setNumPerPage(perPageNum+"");			
		}
		init(initPageNum,perPageNum,totalRow);
		return setPagingBean();
	}

}
