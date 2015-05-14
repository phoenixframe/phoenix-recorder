<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>

	<%
		InterfaceDao reqDataDao = new InterfaceDaoImpl();
		UserDao userDao = new UserDaoImpl();
		int id = Integer.parseInt(request.getParameter("id"));
		InterfaceBean reqDataBean = reqDataDao.getInterface(id);
	%>
<jsp:include page="../checkin.jsp" flush="true"/>
<div class="pageContent">
	<form method="post" action="./do/doreqdata.jsp?type=update" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone)">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>接口参数详细信息</label>
			</div>
			<div class="divider">divider</div>
			<div class="unit">
				<label>所属id：</label>
				<input readonly="true" size="25" name="id" type="text" value=<%=reqDataBean.getId() %>>
			</div>
			<div class="unit">
				<label>所属用户：</label>				
				<input readonly="true" size="25" name="userName" type="text" value=<%=userDao.getUser(reqDataBean.getUserId()).getName() %>>
			</div>
			<div class="unit">
				<label>参数名称：</label>
				<input type="text" size="25" name="reqDataName" class="required" value=<%=reqDataBean.get_interface() %>>				
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
