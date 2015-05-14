<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("user") == null) {
%>
<script type="text/javascript">   
	location.assign("./index.jsp");
</script>
<%
	}
%>