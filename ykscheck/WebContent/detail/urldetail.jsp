<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.controller.DataDTO" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>

	<%
	String url = request.getParameter("url");
	
	%>
	
<div class="pageContent">
	<form method="post" action="" class="pageForm" onsubmit="return navTabSearch(this,navTabAjaxDone);">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>URL地址查看</label>
			</div>
			<div class="divider">divider</div>
			<div class="unit">
				<span class="inputInfo"><label>URL地址为：</label></span>
				<a href='<%=url %>' target="_blank"><%=url %></a>
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
