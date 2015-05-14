<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>
<%@ page import="com.youku.yks.tools.*"%>

<%    
    PagingCommon pagingCommon = new PagingCommon();
    LogBatch logBatchDao = new LogBatchImpl();
    UserDao userDao = new UserDaoImpl();
    UserBean userBean = (UserBean)session.getAttribute("user");
    LogBatchPaging logBatchPaging = new LogBatchPaging(); 
    LogBatchBean logBatchBean = new LogBatchBean();
    List<LogBatchBean> logBatchList = new ArrayList<LogBatchBean>();
%>

<%
	request.setCharacterEncoding("UTF-8"); //设置编码集
	String strPageNum = request.getParameter("pageNum");
	String numPerPage = request.getParameter("numPerPage");	
	String checkKey = request.getParameter("keyword");
	String startTime = request.getParameter("startTime");
	String endTime = request.getParameter("endTime");

	if(checkKey != null && !checkKey.trim().equals(""))logBatchPaging.setRuleValue(checkKey);else checkKey = "";	
	if(startTime != null && !startTime.trim().equals(""))logBatchPaging.setStartTime(startTime);else startTime = "";	
	if(endTime != null && !endTime.trim().equals(""))logBatchPaging.setEndTime(endTime);else endTime = "";
	
	logBatchPaging.setUserBean(userBean);
	pagingCommon.setNumPerPage(numPerPage);
	pagingCommon.setStrPageNum(strPageNum);
	pagingCommon.setTotalRow(logBatchPaging.getLogBatchTotalRow());
	
	PagingBean pagingBean = pagingCommon.getPagingData();
	
	int totalRow = pagingBean.getTotalRow();
	int totalPage = pagingBean.getTotalPage();
	int currentPage = pagingBean.getCurrentPage();
	int initPerPageRows = pagingBean.getNumPerPage();
	
	logBatchList = logBatchPaging.getLogBatchQuery(currentPage,initPerPageRows); //分页查询

%>

<jsp:include page="check.jsp" flush="true"/>
<form id="pagerForm" method="post" action="logmag.jsp">
	<input type="hidden" name="keyword" value=<%=checkKey == null?"":checkKey %> >
	<input type="hidden" name="startTime" value="<%=startTime == null?"":startTime %>" >
	<input type="hidden" name="endTime" value="<%=endTime == null?"":endTime %>" >
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value=<%=initPerPageRows %> />
	<input type="hidden" name="orderField" value="desc" />
</form>	
	
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="logmag.jsp" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
				    <span>日志批次值：</span>
					<input type="text" name="keyword" size=40 value=<%=checkKey %>>
				</td>
				<td>|</td>
				<td>
				<label>开始时间：</label>
				<input type="text" value="<%=startTime %>" title="点击设置时间"  name="startTime" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true"/>				
			    </td>
				<td>
				<label>结束时间：</label>
				<input type="text" name="endTime" value="<%=endTime %>" title="点击设置时间" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true"/>
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
			    <th width="30%">日志批次</th>
			    <th width="25%">创建时间</th>
			    <th width="15%">操作</th>
			</tr>
		</thead>
		<tbody>
		<%
			for (LogBatchBean logBatch : logBatchList) {
		%>
		<tr target="sid_logbatch" rel="1"  align="center">
			<td><%=logBatch.getId()%></td>
			<td><%=userDao.getUser(logBatch.getUserId()).getName() %></td>
			<td><a target="navTab" rel="log_detail" mask="true" title="<%=logBatch.getBatchString() %>批次详细信息" href="log.jsp?org1.orgName=<%=logBatch.getBatchString() %>&org1.id=<%=logBatch.getId() %>" ><%=logBatch.getBatchString()%></a></td>
			<td><%=GetNow.getFormatTime(logBatch.getCreateTime()) %></td>
			<td>
				<a title="要删除日志批次 “<%=logBatch.getBatchString()%>” 吗？该批次下的日志信息都会被删除，且删除后数据不可恢复！" target="ajaxTodo" href="do/dolog.jsp?id=<%=logBatch.getId()%>" class="btnDel">删除</a>
				<a target="navTab" mask="true" title="<%=logBatch.getBatchString() %>批次详细信息" href="log.jsp?org1.orgName=<%=logBatch.getBatchString() %>&org1.id=<%=logBatch.getId() %>" class="btnInfo">详细</a>
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