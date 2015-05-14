<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>
<%@ page import="com.youku.yks.tools.*"%>

<%
    LogBatch logBatchDao = new LogBatchImpl();
    PagingCommon pagingCommon = new PagingCommon();
    UserDao userDao = new UserDaoImpl();
    UserBean userBean = (UserBean)session.getAttribute("user");
    LogBatchPaging logBatchPaging = new LogBatchPaging();
%>

<%
	request.setCharacterEncoding("UTF-8"); //设置编码集
	String strPageNum = request.getParameter("pageNum");
	String numPerPage = request.getParameter("numPerPage");	
	
	logBatchPaging.setUserBean(userBean);
	pagingCommon.setNumPerPage(numPerPage);
	pagingCommon.setStrPageNum(strPageNum);
	pagingCommon.setTotalRow(logBatchPaging.getLogBatchTotalRow());
	
	PagingBean pagingBean = pagingCommon.getPagingData();
	
	int totalRow = pagingBean.getTotalRow();
	int totalPage = pagingBean.getTotalPage();
	int currentPage = pagingBean.getCurrentPage();
	int initPerPageRows = pagingBean.getNumPerPage();

	List<LogBatchBean> logBatchList = logBatchPaging.getLogBatchQuery(currentPage,initPerPageRows); //分页查询

%>
<%-- <jsp:include page="../checkin.jsp" flush="true"/> --%>
<form id="pagerForm" action="./lookback/backlogbatch.jsp">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value=<%=initPerPageRows %> />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" method="post" action="./lookback/backlogbatch.jsp" onsubmit="return dwzSearch(this, 'dialog');">
	<div class="searchBar">
		<ul class="searchContent">
           <span>查询带回所需LOG批次数据</span>
		</ul>
	</div>
	</form>
</div>
<div class="pageContent">

	<table class="table" layoutH="118" targetType="dialog" width="100%" rel="backlogbatch">
		<thead>
			<tr>
				<th orderfield="id">Id编号</th>
				<th orderfield="orgName">批次号</th>
				<th orderfield="createtime">创建时间</th>
				<th width="80">查找带回</th>
			</tr>
		</thead>
		<tbody>
		<%for(LogBatchBean logBean : logBatchList){ %>
			<tr>
				<td><%=logBean.getId() %></td>
				<td><%=logBean.getBatchString() %></td>
				<td><%=GetNow.getFormatTime(logBean.getCreateTime()) %></td>
				<td align="center">
					<a class="btnSelect" href="javascript:$.bringBack({id:'<%=logBean.getId() %>', orgName:'<%=logBean.getBatchString() %>', orgNum:'<%=logBean.getId() %>'})" title="查找带回">选择</a>
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
			<select name="numPerPage" onchange="dialogPageBreak({numPerPage:this.value})">
				<option value=>当前每页:<%=initPerPageRows%></option>
				<option value="20">20</option>
				<option value="30">30</option>
				<option value="50">50</option>
				<option value="100">100</option>
			</select>
			<span>条，共<%=totalRow%>条</span>
		</div>
		<div class="pagination" targetType="dialog" totalCount=<%=totalRow%> numPerPage=<%=initPerPageRows%> pageNumShown=10 currentPage=<%=currentPage%>></div>
	</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">关闭窗口</button></div></div>
				</li>
			</ul>
		</div>
</div>