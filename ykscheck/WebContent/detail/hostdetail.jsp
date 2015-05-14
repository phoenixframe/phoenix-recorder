<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>

	<%
	HostDao hostDao = new HostImpl();
	int id = Integer.parseInt(request.getParameter("id"));
	HostBean hostBean = hostDao.getHost(id);
	UserBean userBean = (UserBean)session.getAttribute("user");
	%>
<jsp:include page="../checkin.jsp" flush="true"/>
<div class="pageContent">
	<form method="post" action="./do/dohost.jsp?type=update" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone)">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>host详细信息</label>
			</div>
			<div class="divider">divider</div>
			<div class="unit">
				<label>所属id：</label>
				<input readonly="true" size="25" name="id" type="text" value=<%=hostBean.getId() %>>
			</div>
			<div class="unit">
				<label>所属用户：</label>
				<input readonly="true" size="25" name="userName" type="text" value=<%=userBean.getName() %>>
			</div>
			<div class="unit">
				<label>Host值：</label>
				<input type="text" size="25" name="hostName" class="required" value=<%=hostBean.getHostName() %>>				
			</div>
			<div class="unit">
				<label>Host备注：</label>
				<input type="text" size="25" name="hostType" class="required" value=<%=hostBean.getRemark() %>>
				<span class="inputInfo">Host类型</span>			
			</div>

		</div>
			<div class="formBar">
		<ul>
		    <li><div class="buttonActive"><div class="buttonContent"><button type="submit" >提交更新</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭窗口</button></div></div></li>
		</ul>
	</div>
	</form>
</div>
