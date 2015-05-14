<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>

<%
	CodeDao codeDao = new CodeDaoImpl();
    UserDao userDao = new UserDaoImpl();
    LogBatch logBatch = new LogBatchImpl();
    UserBean userBean = (UserBean)session.getAttribute("user");
    PagingCommon pagingCommon = new PagingCommon();
    LogUnitDetailPaging logUnitDetailPaging = new LogUnitDetailPaging();
	HashMap<Integer, String> codeMap = codeDao.getAllCode();
	List<CodeBean> runCodeList = codeDao.getCodeList(2, 5);	
%>

<%
	request.setCharacterEncoding("UTF-8"); //设置编码集
	String strPageNum = request.getParameter("pageNum");
	String numPerPage = request.getParameter("numPerPage");			
	String logBatchName = request.getParameter("org1.orgName");
	String statusType = request.getParameter("statusType");
	String logBatchId = "";
	if(logBatchName!=null && !"".equals(logBatchName)){
		logBatchId = request.getParameter("org1.id");	
		logUnitDetailPaging.setBatchId(logBatchId);
		logUnitDetailPaging.setRuleValue(logBatchName);
	}else{
		logBatchName = "";
	}
	int status = -1;
	if(statusType!=null && !statusType.equals("全部状态类型")){
		status = Integer.parseInt(statusType);
		logUnitDetailPaging.setStatus(status);	 
	}
	pagingCommon.setNumPerPage(numPerPage);
	pagingCommon.setStrPageNum(strPageNum);
	pagingCommon.setTotalRow(logUnitDetailPaging.getUnitLogTotalRow());
	
	PagingBean pagingBean = pagingCommon.getPagingData();
	
	int totalRow = pagingBean.getTotalRow();
	int totalPage = pagingBean.getTotalPage();
	int currentPage = pagingBean.getCurrentPage();
	int initPerPageRows = pagingBean.getNumPerPage();

	List<LogUnitBean> logUnitDetailList = logUnitDetailPaging.getUnitLogQuery(currentPage,initPerPageRows); //分页查询
%>
<form id="pagerForm" method="post" action="log.jsp">
	<input type="hidden" name="org1.id" value="<%=logBatchId == null?"":logBatchId %>">
	<input type="hidden" name="org1.orgName" value="<%=logBatchName == null?"":logBatchName %>">
	<input type="hidden" name="statusType" value="<%=statusType == null?"全部状态类型":statusType %>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value=<%=initPerPageRows %> />
	<input type="hidden" name="orderField" value="asc" />
</form>	
	
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="log.jsp" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<label>日志批次号：</label>
				    <input id="inputOrg1" name="org1.id" value="<%=logBatchId %>" type="hidden"/>
				    <input name="org1.orgName" readonly="true" type="text" postField="keyword" size=40 value=<%=logBatchName %>>				
				</td>	
				<td>
				     <a class="btnLook" href="lookback/backlogbatch.jsp" lookupGroup="org1">查找带回</a>	
				</td>
				<td>
				     <label>结果状态：</label>
				     <select class="combox" name="statusType" id="statusType" >
						<option value="全部状态类型">全部状态类型</option>
						<%
						for(CodeBean codeBean : runCodeList){
							if(codeBean.getCode() == status){
						%>						        
						        <option selected="selected" value=<%=codeBean.getCode() %>><%=codeBean.getValue() %></option>
						<%
						    }else{
						    	%>
						    	<option value=<%=codeBean.getCode() %>><%=codeBean.getValue() %></option>
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
			    <th width="8%">BatchId</th>
			    <th width="10%">执行结果</th>
			    <th width="15%">url</th>
			    <th width="55%">备注</th>
			    <th width="12%">操作</th>
			</tr>
		</thead>
		<tbody>
		<%
			for (LogUnitBean logUnit : logUnitDetailList) {
		%>
		<tr target="sid_log" rel="1"  align="center">
			<td><%=logUnit.getBatchId()%></td>
			<td><%=codeMap.get(logUnit.getResult())%></td>
			<td><a title="URL详细" target="_blank" href="<%=logUnit.getUrl() %>" >点击访问</a></td>
			<td align="left"><%=logUnit.getRemark()%></td>
			<td>
			    <%if(userBean.getLevel() == 0 || userBean.getId() == userDao.getUser(logBatch.getBatchId(logUnit.getBatchId()).getUserId()).getId() ){ %>
				<a title="删除这条日志记录吗？" target="ajaxTodo" href="do/dologunit.jsp?id=<%=logUnit.getId()%>" class="btnDel">删除</a>
				<%} %>
				<a title="详细" target="dialog" href="detail/logunitdetail.jsp?id=<%=logUnit.getId()%>" class="btnInfo">详细</a>
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