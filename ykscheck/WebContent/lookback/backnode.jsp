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
    PagingCommon pagingCommon = new PagingCommon();
    NodePaging nodePaging = new NodePaging();
    UserBean userBean = (UserBean)session.getAttribute("user");
	HashMap<Integer, String> codeMap = codeDao.getAllCode();
%>

<%
	request.setCharacterEncoding("UTF-8"); //设置编码集
	String strPageNum = request.getParameter("pageNum");
	String numPerPage = request.getParameter("numPerPage");	

	nodePaging.setUserBean(userBean);
	pagingCommon.setNumPerPage(numPerPage);
	pagingCommon.setStrPageNum(strPageNum);
	pagingCommon.setTotalRow(nodePaging.getNodeTotalRow());
	
	PagingBean pagingBean = pagingCommon.getPagingData();
	
	int totalRow = pagingBean.getTotalRow();
	int totalPage = pagingBean.getTotalPage();
	int currentPage = pagingBean.getCurrentPage();
	int initPerPageRows = pagingBean.getNumPerPage();

	List<NodeBean> nodeList = nodePaging.getNodeQuery(currentPage,initPerPageRows); //分页查询

%>
<jsp:include page="../checkin.jsp" flush="true"/>

<form id="pagerForm" action="lookback/backnode.jsp">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value=<%=initPerPageRows %> />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" method="post" action="lookback/backnode.jsp" onsubmit="return dwzSearch(this, 'dialog');">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<span>查找并带回所需用例节点数据</span>
			</li>	  
		</ul>
	</div>
	</form>
</div>
<div class="pageContent">

	<table class="table" layoutH="118" targetType="dialog" width="100%" rel="backnode">
		<thead>
			<tr>
				<th orderfield="id">Id编号</th>
				<th orderfield="isdisabled">是否可用</th>
				<th orderfield="orgNum">用例名称</th>
				<th orderfield="orgName">用例节点名称</th>
				<th orderfield="remark">备注</th>				
				<th width="80">查找带回</th>
			</tr>
		</thead>
		<tbody>
		<%for(NodeBean nodeBean : nodeList){ %>
			<tr>
				<td><%=nodeBean.getId() %></td>
				<td><%=codeDao.getCode(nodeBean.getIsDisabled()) %></td>
				<td><%=caseDao.getUnitCase(nodeBean.getCaseNodeId()).getCaseNode() %></td>
				<td><%=nodeBean.getParamNode() %></td>
				<td><%=nodeBean.getRemark() %></td>
				<td align="center">
					<a class="btnSelect" href="javascript:$.bringBack({id:'<%=nodeBean.getId() %>', orgName:'<%=nodeBean.getParamNode() %>', orgNum:'<%=nodeBean.getId() %>'})" title="查找带回">选择</a>
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