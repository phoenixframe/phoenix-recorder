<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>

	<%
	VidDao vidDao = new VidImpl();
	UserDao userDao = new UserDaoImpl();
	UserBean userBean = (UserBean)session.getAttribute("user");
	int id = Integer.parseInt(request.getParameter("id"));
	VidBean vidBean = vidDao.getVidById(id);
	%>
<jsp:include page="../checkin.jsp" flush="true"/>
<div class="pageContent">
	<form method="post" action="./do/dovid.jsp?type=update" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone)">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>vid详细信息</label>
			</div>
			<div class="divider">divider</div>
			<div class="unit">
				<label>所属id：</label>
				<input readonly="true" size="25" name="id" type="text" value=<%=vidBean.getId() %>>
			</div>
			<div class="unit">
				<label>所属用户：</label>
				<input readonly="true" size="25" name="userName" type="text" value=<%=userDao.getUser(vidBean.getUserId()).getName() %>>
			</div>
			<div class="unit">
				<label>vid值：</label>
				<input type="text" size="25" name="vidValue" class="required" value=<%=vidBean.getVid() %>>				
			</div>
			<div class="unit">
				<label>vid名称：</label>
				<input type="text" size="25" name="vidName" class="required" value=<%=vidBean.getVidName() %>>				
			</div>
			<div class="unit">
				<label>所属类型：</label>
				<input type="text" size="25" name="vidType" class="required" value=<%=vidBean.getRemark() %>>
				<%-- <input type="text" size=25 class="required" name="orgLookup.orgName" value="<%=vidBean.getRemark() %>" suggestFields="orgNum,orgName" suggestUrl="./files/vidtype.html" lookupGroup="orgLookup" /> --%>
				<span class="inputInfo">输入所属类型</span>			
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
