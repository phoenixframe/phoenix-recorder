<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>

	<%
	UserDao userDao = new UserDaoImpl();
	int id = Integer.parseInt(request.getParameter("id"));
	UserBean userBean = userDao.getUser(id);
	%>
<jsp:include page="../checkin.jsp" flush="true"/>
<div class="pageContent">
	<form method="post" action="./do/douser.jsp?type=update" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone)">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>用户详细信息</label>
			</div>
			<div class="divider">divider</div>
			<div class="unit">
				<label>所属id：</label>
				<input readonly="true" size="30" name="id" type="text" value=<%=userBean.getId() %>>
			</div>
			<div class="unit">
				<label>用户名：</label>
				<input type="text" size="30" name="userName" class="required" value=<%=userBean.getName() %>>				
			</div>
			<div class="unit">
				<label>密码：</label>
				<input type="text" size="30" name="password" class="required" value=<%=userBean.getPassword() %>>			
			</div>
			<div class="unit">
				<label>Level设置：</label>
				<input type="hidden" name="orgLookup.orgNum" value="<%=userBean.getLevel() %>" alt=""/>
				<input type="text" size=25 class="required" name="orgLookup.orgName" value="<%=userBean.getLevel() %>" suggestFields="orgNum,orgName" suggestUrl="files/level.html" lookupGroup="orgLookup" />
				<span class="inputInfo">初始化状态设置</span>			
			</div>
			<div class="unit">
				<label>用户说明：</label>
				<input type="text" size="30" readonly="true" name="orgLookup.orgName" value=<%=userBean.getRemark() %>>		
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
