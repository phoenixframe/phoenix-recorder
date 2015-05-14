<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>
<%@ page import="com.youku.yks.tools.*"%>
<%
	CodeDao codeDao = new CodeDaoImpl();
    UserDao userDao = new UserDaoImpl();
    UserBean userBean = (UserBean)session.getAttribute("user");
	PagingCommon pagingCommon = new PagingCommon();
	CaseTypeDao caseTypeDao = new CaseTypeImpl();
	CaseTypePaging caseTypePaging = new CaseTypePaging();
	List<CaseTypeBean> caseTypeList = new ArrayList<CaseTypeBean>();
	HashMap<Integer, String> codeMap = codeDao.getAllCode();
%>

<%
	request.setCharacterEncoding("UTF-8"); //设置编码集
	String strPageNum = request.getParameter("pageNum");
	String numPerPage = request.getParameter("numPerPage");
	String checkKey = request.getParameter("keyword");
    try{
	    caseTypePaging.setUser(userBean);
    }catch(Exception e){}
	
	pagingCommon.setNumPerPage(numPerPage);
	pagingCommon.setStrPageNum(strPageNum);
	pagingCommon.setTotalRow(caseTypePaging.getCaseTypeTotalRow());

	PagingBean pagingBean = pagingCommon.getPagingData();

	int totalRow = pagingBean.getTotalRow();
	int totalPage = pagingBean.getTotalPage();
	int currentPage = pagingBean.getCurrentPage();
	int initPerPageRows = pagingBean.getNumPerPage();

	if (checkKey != null && !checkKey.equals("")) {
		try {
			CaseTypeBean getCaseType = caseTypeDao
					.getUnitCaseType(checkKey,userBean.getId());
			caseTypeList.add(getCaseType);
		} catch (Exception e) {
		}
	} else {
		checkKey = "";
		caseTypeList = caseTypePaging.getCaseTypeQuery(currentPage,
				initPerPageRows); //分页查询
	}
%>
<jsp:include page="check.jsp" flush="true"/>
<form id="pagerForm" method="post" action="run.jsp">
    <input type="hidden" name="keyword" value=<%=checkKey==null?"":checkKey%>>
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value=<%=initPerPageRows%> />
	<input type="hidden" name="orderField" value="asc" />
</form>	
	
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="run.jsp" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					输入用例类型名称：<input type="text" name="keyword" size=40 value=<%=checkKey%>>
				</td>
				<td><div class="buttonActive"><div class="buttonContent"><button type="submit">提交查询</button></div></div></td>				
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
	<table class="table" width="100%" layoutH="145">
		<thead>
			<tr align="center">
			    <th width="5%"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
			    <th width="12%">Id</th>
			    <th width="13%">是否可用</th>
			    <th width="15%">所属用户</th>
			    <th width="20%">用例类型名称</th>
			    <th width="20%">最后修改时间</th>
			    <th width="15%">操作</th>
			</tr>
		</thead>
		<tbody>
		<%
			for (CaseTypeBean caseType : caseTypeList) {
		%>
		<tr target="sid_caseType" rel="1"  align="center">
		    <td><input name="ids" value="<%=caseType.getId() %>" type="checkbox"></td>
			<td><%=caseType.getId()%></td>
			<td><%=codeMap.get(caseType.getIsDisabled())%></td>
			<td><%=userDao.getUser(caseType.getUserid()).getName() %></td>
			<td><%=caseType.getTypeName()%></td>			
			<td><%=caseType.getCreateTime()%></td>
			<td>
				<a target="dialog" mask="true" title="查看结构图" href="tree.jsp?caseTypeId=<%=caseType.getId()%>" class="btnLook">查看结构图</a>
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
		<div class="formBar">
			<ul>
			    <li><div class="buttonActive"><a target="selectedTodo" rel="ids" href="do/dorun.jsp" class="Info"><span>提交数据</span></a></div></li>
				<!-- <li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交数据</button></div></div></li> -->
				<li><div class="buttonActive"><a target="navTab" rel="monitor" href="monitor.jsp?statusType=2" class="Info"><span>查看进展</span></a></div></li>
			</ul>
		</div>
</div>