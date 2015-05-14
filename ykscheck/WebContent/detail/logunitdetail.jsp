<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>

	<%
	int id = Integer.parseInt(request.getParameter("id"));
	LogUnitDao logUnitDao = new LogUnitImpl();
	CodeDao codeDao = new CodeDaoImpl();
	UserDao userDao = new UserDaoImpl();
	CaseTypeDao caseType = new CaseTypeImpl();
	CaseDao caseImpl = new CaseImpl();
	NodeDao nodeImpl = new NodeImpl();
	VidDao vidImpl = new VidImpl();
	LogBatch batchImpl = new LogBatchImpl();
	LogUnitBean logUnitInfo = logUnitDao.getUnitLog(id);
	HashMap<Integer,String> codeMap = codeDao.getAllCode();
	%>
	
<div class="pageContent">
	<form method="post" action="update.jsp" class="pageForm" onsubmit="return navTabSearch(this,navTabAjaxDone);">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>LOG详细信息查看</label>
			</div>
			<div class="divider">divider</div>
			<div class="unit">
				<span class="inputInfo"><label>所属id：</label></span>
				<%=logUnitInfo.getId() %>
			</div>
			<div class="unit">
				<span class="inputInfo"><label>所属用户：</label></span>
				<%=userDao.getUser(batchImpl.getBatchId(logUnitInfo.getBatchId()).getUserId()).getName() %>
			</div>
			<div class="unit">
				<span class="inputInfo"><label>所属LOG批次：</label></span>
				<%=batchImpl.getBatchId(logUnitInfo.getBatchId()).getBatchString() %>
			</div>
			<div class="unit">
				<span class="inputInfo"><label>视频Vid：</label></span>
				<%=vidImpl.getVidById(logUnitInfo.getVid()).getVid() %>
				<span class="inputInfo"><%=vidImpl.getVidById(logUnitInfo.getVid()).getRemark() %></span>
			</div>
						<div class="unit">
				<span class="inputInfo"><label>执行结果：</label></span>
				<%=codeMap.get(logUnitInfo.getResult()) %>
			</div>
						<div class="unit">
				<span class="inputInfo"><label>所属用例类型：</label></span>
				<%=caseType.getUnitCaseType(logUnitInfo.getTypeId()).getTypeName() %>
			</div>
						<div class="unit">
				<span class="inputInfo"><label>所属被测节点：</label></span>
				<%=caseImpl.getUnitCase(logUnitInfo.getCaseNodeId()).getCaseNode() %>
			</div>
			<div class="unit">
						<span class="inputInfo"><label>所属参数节点：</label></span>
				<%=nodeImpl.getUnitNode(logUnitInfo.getParamNodeId()).getParamNode() %>				
			</div>
			<div class="unit">
						<span class="inputInfo"><label>URL地址：</label></span>
				<a rel="url_page" title="URL详细" target="dialog" href="detail/urldetail.jsp?id=<%=logUnitInfo.getCaseNodeId() %>" >点击查看</a>				
			</div>
			<div class="unit">
						<span class="inputInfo"><label>备注信息：</label></span>
				<%=logUnitInfo.getRemark() %>				
			</div>
			<div class="divider">divider</div>
		</div>
			<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>
		</ul>
	</div>
	</form>
</div>
