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
    CaseTypeDao caseTypeDao = new CaseTypeImpl();
    UserDao userDao = new UserDaoImpl();
    PagingCommon pagingCommon = new PagingCommon();
    CasePaging casePaging = new CasePaging();
    UserBean userBean = (UserBean)session.getAttribute("user");
	HashMap<Integer, String> codeMap = codeDao.getAllCode();
%>

<%
	request.setCharacterEncoding("UTF-8"); //设置编码集
	String strPageNum = request.getParameter("pageNum");
	String numPerPage = request.getParameter("numPerPage");	

	casePaging.setUserBean(userBean);
	pagingCommon.setNumPerPage(numPerPage);
	pagingCommon.setStrPageNum(strPageNum);
	pagingCommon.setTotalRow(casePaging.getCaseTotalRow());
	
	PagingBean pagingBean = pagingCommon.getPagingData();
	
	int totalRow = pagingBean.getTotalRow();
	int totalPage = pagingBean.getTotalPage();
	int currentPage = pagingBean.getCurrentPage();
	int initPerPageRows = pagingBean.getNumPerPage();

	List<CaseBean> caseList = casePaging.getCaseQuery(currentPage,initPerPageRows); //分页查询

%>
<jsp:include page="../checkin.jsp" flush="true"/>
<form id="pagerForm" action="lookback/backcase.jsp">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value=<%=initPerPageRows %> />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" method="post" action="lookback/backcase.jsp" onsubmit="return dwzSearch(this, 'dialog');">
	<div class="searchBar">
		<ul class="searchContent">
           <span>查询带回所需用例数据</span>
		</ul>
	</div>
	</form>
</div>
<div class="pageContent">

	<table class="table" layoutH="118" targetType="dialog" width="100%" rel="backcase">
		<thead>
			<tr>
				<th orderfield="id">Id编号</th>
				<th orderfield="orgNum">是否可用</th>
				<th orderfield="uname">所属用户</th>
				<th orderfield="casetype">用例类型</th>
				<th orderfield="orgName">用例名称</th>
				<th orderfield="remark">备注</th>
				<th orderfield="createtime">创建时间</th>
				<th width="80">查找带回</th>
			</tr>
		</thead>
		<tbody>
		<%for(CaseBean caseBean : caseList){ %>
			<tr>
				<td><%=caseBean.getId() %></td>
				<td><%=codeDao.getCode(caseBean.getIsDisabled()) %></td>
				<td><%=userDao.getUser(caseBean.getUserId()).getName() %></td>
				<td><%=caseTypeDao.getUnitCaseType(caseBean.getTypeNameId()).getTypeName() %></td>
				<td><%=caseBean.getCaseNode() %></td>
				<td><%=caseBean.getRemark() %></td>
				<td><%=GetNow.getFormatTime(caseBean.getCreateTime()) %></td>
				<td align="center">
					<a class="btnSelect" href="javascript:$.bringBack({id:'<%=caseBean.getId() %>', orgName:'<%=caseBean.getCaseNode() %>', orgNum:'<%=caseBean.getId() %>'})" title="查找带回">选择</a>
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