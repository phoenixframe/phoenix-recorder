<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.controller.ResultAnalyse" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>

<%
    DiaryDao diaryDao = new DiaryDaoImpl();
%>


<%
   String id = request.getParameter("id");
   String content = diaryDao.getDiary(Integer.parseInt(id)).getContent();
%>

	<form method="post" action="./do/dodiary.jsp?type=update&id=<%=id %>" class="pageForm required-validate" onsubmit="return iframeCallback(this)">
		<div class="pageFormContent" layoutH="55">
			
			<div class="unit" align="center">
				<textarea readonly="true" class="editor" name="description" style="height:1000px;width:100%;"><%=content %></textarea>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
