<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>
<%@ page import="com.youku.yks.tools.*"%>

<%    
    PagingCommon pagingCommon = new PagingCommon();
    DiaryDao diaryDao = new DiaryDaoImpl();
    UserDao userDao = new UserDaoImpl();
    UserBean userBean = (UserBean)session.getAttribute("user");
    DiaryPaging diaryPaging = new DiaryPaging(); 
    DiaryBean diaryBean = new DiaryBean();
    List<DiaryBean> diaryList = new ArrayList<DiaryBean>();
%>

<%
	request.setCharacterEncoding("UTF-8"); //设置编码集
	String strPageNum = request.getParameter("pageNum");
	String numPerPage = request.getParameter("numPerPage");	
	String checkKey = request.getParameter("keyword");
	String startTime = request.getParameter("startTime");
	String endTime = request.getParameter("endTime");
	int displayContentLimit = 30;
 
	if(checkKey != null && !checkKey.trim().equals(""))diaryPaging.setRuleValue(checkKey);else checkKey = "";	
	if(startTime != null && !startTime.trim().equals(""))diaryPaging.setStartTime(startTime);else startTime = "";	
	if(endTime != null && !endTime.trim().equals(""))diaryPaging.setEndTime(endTime);else endTime = "";
	
	diaryPaging.setUserBean(userBean);
	pagingCommon.setNumPerPage(numPerPage);
	pagingCommon.setStrPageNum(strPageNum);
	pagingCommon.setTotalRow(diaryPaging.getDiaryTotalRow());
	
	PagingBean pagingBean = pagingCommon.getPagingData();
	
	int totalRow = pagingBean.getTotalRow();
	int totalPage = pagingBean.getTotalPage();
	int currentPage = pagingBean.getCurrentPage();
	int initPerPageRows = pagingBean.getNumPerPage();
	
	diaryList = diaryPaging.getDiaryQuery(currentPage,initPerPageRows); //分页查询

%>
<jsp:include page="check.jsp" flush="true"/>
<form id="pagerForm" method="post" action="diary.jsp">
	<input type="hidden" name="keyword" value=<%=checkKey == null?"":checkKey %> >
	<input type="hidden" name="startTime" value="<%=startTime == null?"":startTime %>" >
	<input type="hidden" name="endTime" value="<%=endTime == null?"":endTime %>" >
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value=<%=initPerPageRows %> />
	<input type="hidden" name="orderField" value="desc" />
</form>	
	
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="diary.jsp" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
				    <span>内容关键字：</span>
					<input type="text" name="keyword" size=40 value=<%=checkKey %>>
				</td>
				<td>|</td>
				<td>
				<label>开始时间：</label>
				<input type="text" value="<%=startTime %>" title="点击设置时间"  name="startTime" class="date" dateFmt="yyyy-MM-dd" readonly="true"/>				
			    </td>
				<td>
				<label>结束时间：</label>
				<input type="text" name="endTime" value="<%=endTime %>" title="点击设置时间" class="date" dateFmt="yyyy-MM-dd" readonly="true"/>
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
		    <li><a title="添加一篇新的日志" class="add" href="add/adddiary.jsp" target="navTab" rel="adddiary_page"><span>添加</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="#" target="dwzExport" targetType="navTab" title="确实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr align="center">
			    <th width="10%">Id</th>
			    <th width="15%">所属用户</th>
			    <th width="45%">日志内容</th>
			    <th width="15%">创建时间</th>
			    <th width="15%">操作</th>
			</tr>
		</thead>
		<tbody>
		<%
			for (DiaryBean diary : diaryList) {
		%>
		<tr target="sid_diary" rel="1"  align="center">
			<td><%=diary.getId()%></td>
			<td><%=userDao.getUser(diary.getUserId()).getName() %></td>
			<td><a target="navTab" rel="diary_detail" mask="true" title="点击查看详细内容" href="detail/diarydetail.jsp?id=<%=diary.getId() %>" ><%=diary.getContent() %></a></td>
			<td><%=GetNow.getFormatTime(diary.getCreatetime()) %></td>
			<td>
				<a title="要删除id为 “<%=diary.getId() %>” 的日志吗？删除后数据不可恢复！" target="ajaxTodo" href="do/dodiary.jsp?type=dele&id=<%=diary.getId()%>" class="btnDel">删除</a>
				<a target="navTab" mask="true" title="编辑" href="detail/diarydetail.jsp?id=<%=diary.getId()%>" class="btnEdit">编辑</a>
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
		
		<div class="pagination" targetType="navTab" totalCount=<%=totalRow%> numPerPage=<%=initPerPageRows%> pageNumShown=<%=totalPage%> currentPage=<%=currentPage%>></div>
	</div>
</div>