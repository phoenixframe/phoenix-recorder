<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>
<%@ page import="com.youku.yks.tools.*"%>

<%
	CodeDao codeDao = new CodeDaoImpl();
    CaseDao caseDao = new CaseImpl();
    UserDao userDao = new UserDaoImpl();
    NodeDao nodeDao = new NodeImpl();

    PagingCommon pagingCommon = new PagingCommon();
    NodePaging nodePaging = new NodePaging();
    UserBean userBean = (UserBean)session.getAttribute("user");
	List<NodeBean> nodeList = new ArrayList<NodeBean>();
	HashMap<Integer, String> codeMap = codeDao.getAllCode();
%>

<%
	request.setCharacterEncoding("UTF-8"); //设置编码集
	String strPageNum = request.getParameter("pageNum");
	String numPerPage = request.getParameter("numPerPage");	
	String getCaseNode = request.getParameter("org1.id");
	String caseNodeName = "";
	if(getCaseNode!=null){
		caseNodeName = request.getParameter("org1.orgName");
		nodePaging.setRuleValue(getCaseNode);
	}
    nodePaging.setUserBean(userBean);
	pagingCommon.setNumPerPage(numPerPage);
	pagingCommon.setStrPageNum(strPageNum);
	pagingCommon.setTotalRow(nodePaging.getNodeTotalRow());
	
	PagingBean pagingBean = pagingCommon.getPagingData();
	
	int totalRow = pagingBean.getTotalRow();
	int totalPage = pagingBean.getTotalPage();
	int currentPage = pagingBean.getCurrentPage();
	int initPerPageRows = pagingBean.getNumPerPage();
	
	
	nodeList = nodePaging.getNodeQuery(currentPage,initPerPageRows); //分页查询

%>
<jsp:include page="check.jsp" flush="true"/>
<form id="pagerForm" method="post" action="node.jsp">
	<input type="hidden" name="org1.id" value=<%=getCaseNode == null?"":getCaseNode %>>
	<input type="hidden" name="org1.orgName" value=<%=caseNodeName == null?"":caseNodeName %>>
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value=<%=initPerPageRows %> />
	<input type="hidden" name="orderField" value="asc" />
</form>	
	
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="node.jsp" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<label>用例筛选：</label>
				<input id="inputOrg1" name="org1.id" value="" type="hidden"/>
				<input name="org1.orgName" readonly="true" type="text" postField="keyword" size=40 value=<%=caseNodeName %>>				
				</td>	
				<td>
				<a class="btnLook" href="lookback/backcase.jsp" lookupGroup="org1">查找带回</a>	
				</td>
				<td>
				   <div class="buttonActive"><div class="buttonContent"><button type="submit">提交数据</button></div></div>
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
		    <li><a title="添加一个新的用例节点" class="add" href="add/addnode.jsp?org1.id=<%=getCaseNode == null?"":getCaseNode%>&org1.orgName=<%=caseNodeName == null?"":caseNodeName %>" target="dialog" rel="addnode_page"><span>添加</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="#" target="dwzExport" targetType="navTab" title="确实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr align="center">
			    <th width="10%">Id</th>
			    <th width="15%">是否可用</th>
			    <th width="17%">所属用户</th>
			    <th width="25%">节点名称</th>
			    <th width="20%">信息备注</th>
			    <th width="10%">操作</th>
			</tr>
		</thead>
		<tbody>
		<%
			for (NodeBean nodeBean : nodeList) {
		%>
		<tr target="sid_node" rel="1"  align="center">
			<td><%=nodeBean.getId()%></td>
			<td><%=codeMap.get(nodeBean.getIsDisabled())%></td>
			<td><%=userDao.getUser(nodeBean.getUserId()).getName() %></td>
			<td><a target="navTab" rel="nodeData" title="用例数据维护" href="data.jsp?org1.id=<%=nodeBean.getId() %>&org1.orgName=<%=nodeBean.getParamNode() %>"><%=nodeBean.getParamNode() %></a></td>
			<td><%=nodeBean.getRemark()%></td>
			<td>
				<a title="要删除 “<%=nodeBean.getParamNode() %>” 节点吗？该节点下的所有数据都会被删除且不可恢复！" target="ajaxTodo" href="do/donode.jsp?type=delete&id=<%=nodeBean.getId()%>" class="btnDel">删除</a>
				<a rel="nodedetail" target="dialog" mask="true" title="编辑" href="detail/nodedetail.jsp?id=<%=nodeBean.getId()%>" class="btnEdit">编辑</a>
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