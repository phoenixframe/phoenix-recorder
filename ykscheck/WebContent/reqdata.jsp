<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>
<%@ page import="com.youku.yks.tools.*"%>

<%
	PagingCommon pagingCommon = new PagingCommon();
    UserDao userDao = new UserDaoImpl();
    InterfaceDao reqDataDao = new InterfaceDaoImpl();
    InterfacePaging reqDataPaging = new InterfacePaging();
    InterfaceBean reqDataBean = new InterfaceBean();
    UserBean userBean = (UserBean)session.getAttribute("user");
    List<InterfaceBean> reqDataList = new ArrayList<InterfaceBean>();
%>

<%
	request.setCharacterEncoding("UTF-8"); //设置编码集
	String strPageNum = request.getParameter("pageNum");
	String numPerPage = request.getParameter("numPerPage");	
	String checkKey = request.getParameter("keyword");

	reqDataPaging.setUserBean(userBean);
	pagingCommon.setNumPerPage(numPerPage);
	pagingCommon.setStrPageNum(strPageNum);
	pagingCommon.setTotalRow(reqDataPaging.getReqDataTotalRow());
	
	PagingBean pagingBean = pagingCommon.getPagingData();
	
	int totalRow = pagingBean.getTotalRow();
	int totalPage = pagingBean.getTotalPage();
	int currentPage = pagingBean.getCurrentPage();
	int initPerPageRows = pagingBean.getNumPerPage();
		
	if(checkKey!=null && !checkKey.equals("")){
		try{
		    InterfaceBean getReqData =reqDataDao.getInterface(checkKey,userBean.getId());
		    reqDataList.add(getReqData);		
		}catch(Exception e){}
	}else{
		checkKey = "";
		reqDataList = reqDataPaging.getReqDataQuery(currentPage,initPerPageRows); //分页查询
	}
%>
<jsp:include page="check.jsp" flush="true"/>
<form id="pagerForm" method="post" action="reqdata.jsp">
	<input type="hidden" name="keyword" value="<%=checkKey == null?"":checkKey %>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value=<%=initPerPageRows%> />
	<input type="hidden" name="orderField" value="asc" />
</form>	
	
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="reqdata.jsp" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					输入接口参数名称：<input type="text" name="keyword" size=40 value=<%=checkKey%>>
				</td>
				<td><div class="buttonActive"><div class="buttonContent"><button type="submit">提交数据</button></div></div></td>
				
			</tr>
		</table>
		<div class="subBar">
			<ul>
			    <li></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
		    <li><a title="添加一个新的接口参数" class="add" href="add/addreqdata.jsp" target="dialog" rel="reqdata_page"><span>添加</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="#" target="dwzExport" targetType="navTab" title="确实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr align="center">
			    <th width="15%">Id</th>
			    <th width="30%">所属用户</th>
			    <th width="40%">参数名称</th>
			    <th width="15%">操作</th>
			</tr>
		</thead>
		<tbody>
		<%
			for (InterfaceBean reqData : reqDataList) {
		%>
		<tr target="sid_caseType" rel="1"  align="center">
			<td><%=reqData.getId()%></td>
			<td><%=userDao.getUser(reqData.getUserId()).getName() %></td>
			<td><%=reqData.get_interface()%></td>
			<td>
				<a title="要删除 “<%=reqData.get_interface()%>” 接口参数吗？删除后将不可恢复！" target="ajaxTodo" href="do/doreqdata.jsp?type=delete&id=<%=reqData.getId()%>" class="btnDel">删除</a>
				<a target="dialog" mask="true" title="编辑" href="detail/reqdatadetail.jsp?id=<%=reqData.getId()%>" class="btnEdit">编辑</a>
			</td>
		</tr>
		<%
			}
		%>


		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value=>当前每页:<%=initPerPageRows%></option>
				<option value="20">20</option>
				<option value="30">30</option>
				<option value="50">50</option>
				<option value="100">100</option>
			</select>
			<span>条，共<%=totalRow%>条</span>
		</div>
		
		<div class="pagination" targetType="navTab" totalCount=<%=totalRow%> numPerPage=<%=initPerPageRows%> pageNumShown=10 currentPage=<%=currentPage%>></div>
	</div>
</div>