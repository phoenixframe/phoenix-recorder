<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>

	<%
      CodeDao codeDao = new CodeDaoImpl();
	%>
	
	<%
	  String code = request.getParameter("code");
	  String value = codeDao.getCode(Integer.parseInt(code));
	%>
<jsp:include page="../checkin.jsp" flush="true"/>
<div class="pageContent">
	<form method="post" action="./do/docode.jsp?type=update" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone)">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>用户详细信息</label>
			</div>
			<div class="divider">divider</div>
			<div class="unit">
				<label>状态码：</label>
				<input type="text" size="30" name="code" class="required" value=<%=code %>>				
			</div>
			<div class="unit">
				<label>说明：</label>
				<input type="text" size="30" name="codeValue" class="required" value=<%=value %>>			
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
