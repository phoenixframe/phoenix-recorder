<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>
<%@ page import="com.youku.yks.tools.*"%>

<%
	UserDao userDao = new UserDaoImpl();
	PagingCommon pagingCommon = new PagingCommon();
	UserPaging userPaging = new UserPaging();
	List<UserBean> userList = new ArrayList<UserBean>();
%>

<%
	request.setCharacterEncoding("UTF-8"); //设置编码集
	String strPageNum = request.getParameter("pageNum");
	String numPerPage = request.getParameter("numPerPage");
	String checkKey = request.getParameter("keyword");

	pagingCommon.setNumPerPage(numPerPage);
	pagingCommon.setStrPageNum(strPageNum);
	pagingCommon.setTotalRow(userPaging.getUserTotalRow());

	PagingBean pagingBean = pagingCommon.getPagingData();

	int totalRow = pagingBean.getTotalRow();
	int totalPage = pagingBean.getTotalPage();
	int currentPage = pagingBean.getCurrentPage();
	int initPerPageRows = pagingBean.getNumPerPage();

	if (checkKey != null && !checkKey.equals("")) {
		try {
			UserBean user = userDao.getUser(checkKey);
			userList.add(user);
		} catch (Exception e) {

		}
	} else {
		checkKey = "";
		userList = userPaging
				.getUserQuery(currentPage, initPerPageRows); //分页查询
	}
%>
<jsp:include page="check.jsp" flush="true"/>
<form id="pagerForm" method="post" action="user.jsp">
	<input type="hidden" name="status" value="${param.status}">
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value=<%=initPerPageRows%> />
	<input type="hidden" name="orderField" value="asc" />
</form>	
	
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="user.jsp" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					输入用户名称：<input type="text" name="keyword" size=40 value=<%=checkKey%>>
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
		    <li><a title="添加新用户" class="add" href="add/adduser.jsp" target="dialog" rel="user_page"><span>添加</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="#" target="dwzExport" targetType="navTab" title="确实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr align="center">
			    <th width="8%">Id</th>
			    <th width="20%">用户名</th>
			    <th width="25%">密码</th>
			    <th width="15%">Level</th>
			    <th width="20%">说明</th>
			    <th width="12%">操作</th>
			</tr>
		</thead>
		<tbody>
		<%
			for (UserBean userBean : userList) {
		%>
		<tr target="sid_caseType" rel="1"  align="center">
			<td><%=userBean.getId()%></td>
			<td><%=userBean.getName()%></td>
			<td><%=userBean.getPassword()%></td>
			<td><%=userBean.getLevel()%></td>
			<td><%=userBean.getRemark()%></td>
			<td>
				<a title="要删除 “<%=userBean.getName()%>” 用户吗？该用户下的所有用例数据都会被删除且不可恢复！" target="ajaxTodo" href="do/douser.jsp?type=delete&id=<%=userBean.getId()%>" class="btnDel">删除</a>
				<a target="dialog" mask="true" title="编辑" href="detail/userdetail.jsp?id=<%=userBean.getId()%>" class="btnEdit">编辑</a>
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