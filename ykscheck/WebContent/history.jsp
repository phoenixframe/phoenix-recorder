<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>
<%@ page import="com.youku.yks.tools.*"%>

<%    
    PagingCommon pagingCommon = new PagingCommon();
    StrategyDao strategyDao = new StrategyImpl();
    UserDao userDao = new UserDaoImpl();
    UserBean userBean = (UserBean)session.getAttribute("user");
    StrategyPaging strategyPaging = new StrategyPaging(); 
%>

<%
	request.setCharacterEncoding("UTF-8"); //设置编码集
	String strPageNum = request.getParameter("pageNum");
	String numPerPage = request.getParameter("numPerPage");	
	String startTime = request.getParameter("startime");
	if(startTime != null && !startTime.trim().equals(""))strategyPaging.setStartTime(startTime);else startTime = "";	
	
	strategyPaging.setUserBean(userBean);
	pagingCommon.setNumPerPage(numPerPage);
	pagingCommon.setStrPageNum(strPageNum);
	pagingCommon.setTotalRow(strategyPaging.getStrategyBatchTotalRow());
	
	PagingBean pagingBean = pagingCommon.getPagingData();
	
	int totalRow = pagingBean.getTotalRow();
	int totalPage = pagingBean.getTotalPage();
	int currentPage = pagingBean.getCurrentPage();
	int initPerPageRows = pagingBean.getNumPerPage();
	
	List<StrategyBean> strategyBeanList = strategyPaging.getStrategyBatchQuery(currentPage,initPerPageRows); //分页查询

%>
<jsp:include page="check.jsp" flush="true"/>
<form id="pagerForm" method="post" action="history.jsp">
	<input type="hidden" value="<%=startTime == null?"":startTime%>" name="startime">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value=<%=initPerPageRows %>>
	<input type="hidden" name="orderField" value="desc" />
</form>	
	
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="history.jsp" method="get">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
				<label>开始时间：</label>
				<input type="text" value="<%=startTime %>" title="点击设置时间"  name="startime" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true"/>				
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
			<li class="line">line</li>
			<li><a class="icon" href="#" target="dwzExport" targetType="navTab" title="确实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr align="center">
			    <th width="15%">Id</th>
			    <th width="15%">所属用户</th>
			    <th width="15%">客户端IP</th>
			    <th width="25%">场景名称</th>
			    <th width="15%">开始时间</th>
			    <th width="15%">操作</th>
			</tr>
		</thead>
		<tbody>
		<%
			for (StrategyBean strategyBean : strategyBeanList) {
		%>
		<tr target="sid_strategy" rel="1"  align="center">
			<td><%=strategyBean.getId()%></td>
			<td><%=userDao.getUser(strategyBean.getUserId()).getName() %></td>
			<td><%=strategyBean.getClientIP() %></td>
			<td><%=strategyBean.getRemark() %></td>
			<td><%=strategyBean.getStartTime() %></td>
			<td>
				<a title="要删除测试结果 “<%=strategyBean.getRemark()%>” 吗？该结果下的数据信息都会被删除，且删除后数据不可恢复！" target="ajaxTodo" href="do/dostrategy.jsp?id=<%=strategyBean.getId()%>" class="btnDel">删除</a>
				<a target="navTab" rel="perfData" mask="true" title="<%=strategyBean.getRemark() %>详细数据" href="detail/perfdetail.jsp?id=<%=strategyBean.getId() %>" class="btnInfo">详细</a>
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