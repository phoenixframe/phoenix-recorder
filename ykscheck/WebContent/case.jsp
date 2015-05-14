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
    CaseTypeDao caseTypeDao = new CaseTypeImpl();
    PagingCommon pagingCommon = new PagingCommon();
    CasePaging casePaging = new CasePaging();
	List<CaseBean> caseList = new ArrayList<CaseBean>();
	UserBean userBean = (UserBean)session.getAttribute("user");
	HashMap<Integer, String> codeMap = codeDao.getAllCode();
	List<CaseTypeBean> caseTypeList = caseTypeDao.getAllEnabledCaseTypeByUser(userBean.getId());
%>

<%
	request.setCharacterEncoding("UTF-8"); //设置编码集
	String strPageNum = request.getParameter("pageNum");
	String numPerPage = request.getParameter("numPerPage");	
	String caseTypeSel = request.getParameter("caseType");
	if(caseTypeSel!=null && !caseTypeSel.equals("所有用例类型")){
		int typeId = caseTypeDao.getUnitCaseType(caseTypeSel,userBean.getId()).getId();
		casePaging.setRuleValue(typeId+"");		
	}

	casePaging.setUserBean(userBean);
	pagingCommon.setNumPerPage(numPerPage);
	pagingCommon.setStrPageNum(strPageNum);
	pagingCommon.setTotalRow(casePaging.getCaseTotalRow());
	
	PagingBean pagingBean = pagingCommon.getPagingData();
	
	int totalRow = pagingBean.getTotalRow();
	int totalPage = pagingBean.getTotalPage();
	int currentPage = pagingBean.getCurrentPage();
	int initPerPageRows = pagingBean.getNumPerPage();
	
	if(caseTypeSel!=null && !caseTypeSel.equals("所有用例类型")){
		caseList = caseDao.getAllCaseByTypeNameId(caseTypeDao.getUnitCaseType(caseTypeSel,userBean.getId()).getId(),userBean.getId());
	}else{
	    caseList = casePaging.getCaseQuery(currentPage,initPerPageRows); //分页查询
	}

%>
<jsp:include page="check.jsp" flush="true"/>
<form id="pagerForm" method="post" action="case.jsp">
	<input type="hidden" name="caseType" value="<%=caseTypeSel == null?"所有用例类型":caseTypeSel %>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value=<%=initPerPageRows %> />
	<input type="hidden" name="orderField" value="asc" />
</form>	
	
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="case.jsp" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<label>用例类型筛选：</label>
					<select class="combox" name="caseType" id="caseType" onchange = "getSelect(this)">
						<option value="所有用例类型">所有用例类型</option>
						<%
						for(CaseTypeBean caseType : caseTypeList){
							if(caseType.getTypeName().equals(caseTypeSel)){
						%>
						        
						        <option selected="selected" value=<%=caseType.getTypeName() %>><%=caseType.getTypeName() %></option>
						<%
						    }else{
						    	%>
						    	<option value=<%=caseType.getTypeName() %>><%=caseType.getTypeName() %></option>
						    	<%
						    }
						}
						%>
					</select>
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
		    <li><a title="添加一个新的用例" class="add" href="add/addcase.jsp?typeid=<%=casePaging.getRuleValue() == null?"":casePaging.getRuleValue() %>" target="dialog" rel="addcase_page"><span>添加</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="#" target="dwzExport" targetType="navTab" title="确实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr align="center">
			    <th width="8%">Id</th>
			    <th width="10%">是否可用</th>
			    <th width="10%">所属用户</th>
			    <th width="20%">用例名称</th>
			    <th width="22%">信息备注</th>
			    <th width="17%">修改时间</th>
			    <th width="10%">操作</th>
			</tr>
		</thead>
		<tbody>
		<%
			for (CaseBean caseBean : caseList) {
		%>
		<tr target="sid_case" rel="1"  align="center">
			<td><%=caseBean.getId()%></td>
			<td><%=codeMap.get(caseBean.getIsDisabled())%></td>
			<td><%=userDao.getUser(caseBean.getUserId()).getName() %></td>
			<td><a target="navTab" title="用例节点维护" href="node.jsp?org1.id=<%=caseBean.getId() %>&org1.orgName=<%=caseBean.getCaseNode() %>" ><%=caseBean.getCaseNode() %></a></td>
			<td><%=caseBean.getRemark()%></td>
			<td><%=GetNow.getFormatTime(caseBean.getCreateTime()) %></td>
			<td>
				<a title="要删除 “<%=caseBean.getCaseNode() %>” 用例吗？该用例下的所有数据都会被删除且不可恢复！" target="ajaxTodo" href="do/docase.jsp?type=delete&id=<%=caseBean.getId()%>" class="btnDel">删除</a>
				<a target="dialog" mask="true" title="编辑" href="detail/casedetail.jsp?id=<%=caseBean.getId()%>" class="btnEdit">编辑</a>
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