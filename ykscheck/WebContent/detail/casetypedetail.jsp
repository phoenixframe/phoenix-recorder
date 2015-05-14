<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>

	<%
	CaseTypeDao caseTypeDao = new CaseTypeImpl();
	CodeDao codeDao = new CodeDaoImpl();
	UserDao userDao = new UserDaoImpl();
	int id = Integer.parseInt(request.getParameter("id"));
	CaseTypeBean caseType = caseTypeDao.getUnitCaseType(id);
	HashMap<Integer,String> codeMap = codeDao.getAllCode();
	%>
<jsp:include page="../checkin.jsp" flush="true"/>
<div class="pageContent">
	<form method="post" action="./do/docasetype.jsp?type=update" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone)">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>用例类型详细信息</label>
			</div>
			<div class="divider">divider</div>
			<div class="unit">
				<label>所属id：</label>
				<input readonly="true" size="25" name="id" type="text" value=<%=caseType.getId() %>>
			</div>
			<div class="unit">
				<label>所属用户：</label>
				<input readonly="true" size="25" name="userId" type="hidden" value=<%=caseType.getUserid() %>>
				<input readonly="true" size="25" readonly="true" name="userName" type="text" value=<%=userDao.getUser(caseType.getUserid()).getName() %>>
			</div>
			<div class="unit">
				<label>是否可用：</label>
				<input type="hidden" name="orgLookup.orgNum" value=<%=caseType.getIsDisabled() %>>
				<input type="text" size=25 class="required" name="orgLookup.orgName" value="<%=codeMap.get(caseType.getIsDisabled()) %>" suggestFields="orgNum,orgName" suggestUrl="files/isDisabled.html" lookupGroup="orgLookup" />
				<span class="inputInfo">初始化状态</span>
			</div>
			<div class="unit">
				<label>用例类型名称：</label>
				<input type="text" size="25" name="caseTypeName" class="required" value=<%=caseType.getTypeName() %>>				
			</div>
			<div class="unit">
				<label>创建时间：</label>
				<span class="inputInfo"><%=caseType.getCreateTime()%></span>
			</div>
			<div class="divider">divider</div>
		</div>
			<div class="formBar">
		<ul>
		    <li><div class="buttonActive"><div class="buttonContent"><button type="submit" onclick="$.pdialog.closeCurrent();">提交更新</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭窗口</button></div></div></li>
		</ul>
	</div>
	</form>
</div>
