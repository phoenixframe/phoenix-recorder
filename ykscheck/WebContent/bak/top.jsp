<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Top.jsp' starting page</title>
    
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
.STYLE2 {
	font-family: "微软雅黑";
	font-size: xx-large;
}

body,td,th {
	font-family: 微软雅黑;
	color: #CCCCFF;
}

body {
	background-color: #FFFFFF;
	background-image: url(images/top-bg.jpg);
	background-repeat: repeat-x;
}

.STYLE4 {
	font-size: medium;
	font-weight: bold;
}
-->
</style>
  </head>
  
  <body>
   <p class="STYLE2"><strong><strong><img src="images/x-office-spreadsheet.png" width="32" height="32" border="0" /></strong>自动化测试系统</strong>
   <span class="STYLE4">
  Automated Test System  </span></p>
  </body>
</html>
