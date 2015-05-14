<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
<!--
.STYLE1 {
	font-family: "微软雅黑";
	color: #336699;
}

body {
	background-color: #CCCCCC;
	background-repeat: repeat;
}

body,td,th {
	font-family: 微软雅黑;
}

a {
	font-family: 微软雅黑;
	color: #0000FF;
}

a:link {
	text-decoration: none;
}

a:visited {
	text-decoration: none;
	color: #0000FF;
}

a:hover {
	text-decoration: none;
	color: #FF0066;
}

a:active {
	text-decoration: none;
	color: #3333FF;
}

.STYLE3 {
	font-size: large;
}
-->
</style>
<base target="rightframe">
  </head>

<body>
	<p class="STYLE1">
		<a href="start.jsp" target="rightframe" class="STYLE3"><img
			src="images/006.gif" width="16" height="16" border="0" />报告概览</a>
	</p>
	<p class="STYLE1">
		<a href="详细报告/PlanDetail.html" target="rightframe" class="STYLE3"><img
			src="images/006.gif" width="16" height="16" border="0" />计划详细报告</a>
	</p>
	<p class="STYLE1">
		<a href="详细报告/CaseDetail.html" target="rightframe" class="STYLE3"><img
			src="images/006.gif" width="16" height="16" border="0" />用例详细报告</a>
	</p>
	<p class="STYLE1">
		<a href="详细报告/CheckPointDetail.html" target="rightframe" class="STYLE3"><img
			src="images/006.gif" width="16" height="16" border="0" />检查点详细报告</a>
	</p>
	<p class="STYLE1">
		<a href="详细报告/VideoPlanBack.html" target="rightframe" class="STYLE3"><img
			src="images/006.gif" width="16" height="16" border="0" />执行过程回放</a>
	</p>
	<p class="STYLE1">&nbsp;</p>

</body>
</html>
