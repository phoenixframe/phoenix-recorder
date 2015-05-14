<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>
<%@ page import="com.youku.yks.tools.*"%>

<%
	DataDao dataDao = new DataImpl();
    UserDao userDao = new UserDaoImpl();
    DataPaging dataPaging = new DataPaging();
    PagingCommon pagingCommon = new PagingCommon();
    UserBean userBean = (UserBean)session.getAttribute("user");
	List<DataBean> dataList = new ArrayList<DataBean>();
%>

<%
	request.setCharacterEncoding("UTF-8"); //设置编码集
	String strPageNum = request.getParameter("pageNum");
	String numPerPage = request.getParameter("numPerPage");	
	String getCaseNodeId = request.getParameter("org1.id");

	String nodeName = "";
	if(getCaseNodeId!=null && !getCaseNodeId.equals("")){
		nodeName = request.getParameter("org1.orgName");
		dataPaging.setNodeValue(nodeName);
		dataPaging.setCaseNodeId(getCaseNodeId);
	}
    dataPaging.setUserBean(userBean);
	pagingCommon.setNumPerPage(numPerPage);
	pagingCommon.setStrPageNum(strPageNum);
	pagingCommon.setTotalRow(dataPaging.getDataTotalRow());
	
	PagingBean pagingBean = pagingCommon.getPagingData();
	
	int totalRow = pagingBean.getTotalRow();
	int totalPage = pagingBean.getTotalPage();
	int currentPage = pagingBean.getCurrentPage();
	int initPerPageRows = pagingBean.getNumPerPage();
		
	dataList = dataPaging.getDataQuery(currentPage,initPerPageRows); //分页查询

%>
<jsp:include page="check.jsp" flush="true"/>
<form id="pagerForm" method="post" action="data.jsp">
	<input type="hidden" name="org1.id" value=<%=getCaseNodeId == null?"":getCaseNodeId %>>
	<input type="hidden" name="org1.orgName" value=<%=nodeName == null?"":nodeName %>>
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value=<%=initPerPageRows %>>
	<input type="hidden" name="orderField" value="asc" />
</form>	
	
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="data.jsp" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<label>数据筛选：</label>
				<input id="inputOrg1" name="org1.id" value="<%=getCaseNodeId %>" type="hidden"/>
				<input name="org1.orgName" readonly="true" type="text" postField="keyword" size=40 value=<%=nodeName %>>				
				</td>	
				<td>
				<a class="btnLook" href="lookback/backnode.jsp" lookupGroup="org1">查找带回</a>	
				</td>
				<td>
				   <div class="buttonActive"><div class="buttonContent"><button type="submit">提交筛选</button></div></div>
				</td>			
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
		    <li><a title="添加测试数据" class="add" href="add/adddata.jsp?caseNodeId=<%=getCaseNodeId == null?"":getCaseNodeId %>&caseNodeName=<%=nodeName == null?"":nodeName %>" target="dialog" rel="adddata_page"><span>添加</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="#" target="dwzExport" targetType="navTab" title="确实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr align="center">
			    <th width="10%">Id</th>
			    <th width="10%">所属用户</th>
			    <th width="10%">请求类型</th>
			    <th width="25%">JsonPath</th>
			    <th width="20%">期望值</th>
			    <th width="15%">备注</th>
			    <th width="10%">操作</th>
			</tr>
		</thead>
		<tbody>
		<%
			for (DataBean dataBean : dataList) {
		%>
		<tr target="sid_node" rel="1"  align="center">
			<td><%=dataBean.getId()%></td>
			<td><%=userDao.getUser(dataBean.getUserId()).getName() %></td>
			<td><%=dataBean.getRequestType() %></td>
			<td><%=dataBean.getJsonPath() %></td>
			<td><%=dataBean.getExpect() %></td>
			<td><%=dataBean.getRemark() %></td>
			<td>
				<a title="确认删除吗？删除的数据将不可恢复！" target="ajaxTodo" href="do/dodata.jsp?type=delete&id=<%=dataBean.getId()%>" class="btnDel">删除</a>
				<a rel="datadetail" target="dialog" mask="true" title="编辑" href="detail/datadetail.jsp?id=<%=dataBean.getId()%>" class="btnEdit">编辑</a>
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