<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
  </head>

<Frameset id="frame1" rows="10%,*"  framespacing="3" frameborder="yes" border="3" bordercolor="#000000">
	<frame name="top" src="Top.jsp"  scrolling="No">
	<Frameset id="frame2" cols="15%,*" framespacing="3" frameborder="yes" border="3" bordercolor="#000000">
		<frame name="leftframe" src="Left.jsp">
		<frame name="rightframe" src="Right.jsp">
<!-- 		<Frameset id="frame3" frameborder="yes" cols="*">
			<frame name="bottomframe" src="Bottom.jsp">
		</Frameset> -->
	</Frameset>
</Frameset>
</html>
