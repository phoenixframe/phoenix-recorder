<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>
<%@ page import="com.youku.yks.tools.*"%>

<%
	CodeDao codeDao = new CodeDaoImpl();
    UserDao userDao = new UserDaoImpl();
    LogBatch batchDao = new LogBatchImpl();
    CaseTypeDao caseTypeDao = new CaseTypeImpl();
    PagingCommon pagingCommon = new PagingCommon();
    MonPaging monPaging = new MonPaging();
	UserBean userBean = (UserBean)session.getAttribute("user");
	HashMap<Integer, String> codeMap = codeDao.getAllCode();
	List<CaseTypeBean> caseTypeList = caseTypeDao.getAllEnabledCaseTypeByUser(userBean.getId());
	List<CodeBean> runCodeList = codeDao.getCodeList(2, 5);	
%>

<%
	request.setCharacterEncoding("UTF-8"); //设置编码集
	String strPageNum = request.getParameter("pageNum");
	String numPerPage = request.getParameter("numPerPage");	
	String caseTypeSel = request.getParameter("caseType");
	String statusType = request.getParameter("statusType");
	String refreshTime = request.getParameter("refreshtime");
	if(refreshTime == null || refreshTime.trim().equals("")) refreshTime = "2";
	int type = 0;
	if(caseTypeSel!=null && !caseTypeSel.equals("所有用例类型")){
		type = Integer.parseInt(caseTypeSel);
		monPaging.setCaseType(caseTypeSel);	
	}
	int status = -1;
	if(statusType!=null && !statusType.equals("全部状态类型")){
		status = Integer.parseInt(statusType);
		monPaging.setStatus(status);	
	}

	monPaging.setUserBean(userBean);
	pagingCommon.setNumPerPage(numPerPage);
	pagingCommon.setStrPageNum(strPageNum);
	pagingCommon.setTotalRow(monPaging.getMonitorTotalRow());
	
	PagingBean pagingBean = pagingCommon.getPagingData();
	
	int totalRow = pagingBean.getTotalRow();
	int totalPage = pagingBean.getTotalPage();
	int currentPage = pagingBean.getCurrentPage();
	int initPerPageRows = pagingBean.getNumPerPage();
	List<MonitorBean> moniList = monPaging.getMonitorQuery(currentPage,initPerPageRows); //分页查询
	
%>
	 <script type="text/javascript">
	    var time = $("#refreshtime").val();
	    var t;
	    function dorefresh(){
	    	if($("#autorefresh").is(':checked')){
	    	   $("#monitorData").click();
	    	}
	    }	    	   		    	
	   t = setTimeout(dorefresh,time*1000);
     </script>	  

<!-- 		<script type="text/javascript">
		  var timeset;
		  var time;
		    function dorefresh(){
		    	if($("#autorefresh").is(':checked')){
		    	   $("#submitData").click();
		    	   $("#refreshtime").val("4");
		    	} else {
		    		clearInterval(timeset);
		    	}		    	   		    	
		    }
		    function refresh(){			    	
		    	if($("#autorefresh").is(':checked')	){		    		
		    		time = $("#refreshtime").val();		    		
		    		timeset = setInterval(dorefresh,time*1000);
		    	}		    
		    }
        </script> -->

<jsp:include page="check.jsp" flush="true"/>
<form id="pagerForm" method="post" action="monitor.jsp">
    <input type="hidden" name="caseType" value="<%=caseTypeSel == null?"所有用例类型":caseTypeSel %>" />
    <input type="hidden" name="statusType" value="<%=statusType == null?"全部状态类型":statusType %>" />
    <input type="hidden" name="refreshtime" value="<%=refreshTime == null?"2":refreshTime %>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value=<%=initPerPageRows %> />
	<input type="hidden" name="orderField" value="asc" />
</form>	
	
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="monitor.jsp" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<label>用例类型筛选：</label>
					<select class="combox" name="caseType" id="caseType" >
						<option value="所有用例类型">所有用例类型</option>
						<%
						for(CaseTypeBean caseType : caseTypeList){
							if(caseType.getId() == type){
						%>						        
						        <option selected="selected" value=<%=caseType.getId() %>><%=caseType.getTypeName() %></option>
						<%
						    } else {
						    	%>
						    	<option value=<%=caseType.getId() %>><%=caseType.getTypeName() %></option>
						    	<%
						    }
						}
						%>
					</select>
				</td>	
				<td>
					<label>运行状态筛选：</label>
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
				<td> | </td>
				<td>
				<label>自动刷新：<input type="checkbox" name="autorefresh" id="autorefresh" checked='checked'></label>
				</td>	
				<td>
				<label>时间设置：<input title="刷新间隔时间,请输入数字" id="refreshtime" name="refreshtime" style="width:25px" value=<%=refreshTime %>>秒</label>
				</td>	
				<td>
				   <div class="buttonActive"><div class="buttonContent"><button id="monitorData" type="submit">提交数据</button></div></div>
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
			<li class="line">line</li>
			<li><a class="icon" href="#" target="dwzExport" targetType="navTab" title="确实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr align="center">
			    <th width="15%">所属用户</th>
			    <th width="10%">批次号</th>
			    <th width="20%">批次值</th>
			    <th width="20%">用例类型</th>
			    <th width="10%">当前状态</th>
			    <th width="15%">创建时间</th>
			    <th width="10%">操作</th>
			</tr>
		</thead>
		<tbody>
		<%
			for (MonitorBean moniBean : moniList) {
		%>
		<tr target="sid_case" rel="1"  align="center">
			<td><%=userDao.getUser(batchDao.getBatchId(moniBean.getBatchId()).getUserId()).getName() %></td>
			<td><%=batchDao.getBatchId(moniBean.getBatchId()).getId() %></td>
			<td><a target="navTab" rel="batchDetail" mask="true" title="<%=batchDao.getBatchId(moniBean.getBatchId()).getBatchString() %>批次详情" href="log.jsp?org1.orgName=<%=batchDao.getBatchId(moniBean.getBatchId()).getBatchString() %>&org1.id=<%=batchDao.getBatchId(moniBean.getBatchId()).getId() %>" ><%=batchDao.getBatchId(moniBean.getBatchId()).getBatchString() %></a></td>
			<td><%=caseTypeDao.getUnitCaseType(moniBean.getTypeId()).getTypeName() %></td>
			<td><%=codeMap.get(moniBean.getResult()) %></td>
			<td><%=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(batchDao.getBatchId(moniBean.getBatchId()).getCreateTime()) %></td>
			<td><a target="navTab" rel="lineChartView" mask="true" title="查看批次走势" href="chart/table/barchart.jsp?caseType=<%=moniBean.getTypeId() %>&org1.id=<%=moniBean.getBatchId() %>&org1.orgName=<%=batchDao.getBatchId(moniBean.getBatchId()).getBatchString() %>" class="btnLook">查看结构图</a></td>
		</tr>
		<%
			}
		%>


		</tbody>
	</table>
	
	<%-- 分页开始 --%>
<%-- 	<div class="panelBar">
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
	</div> --%>
	
	<%-- 分页结束 --%>
</div>