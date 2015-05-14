<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.tools.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%
    CodeDao codeDao = new CodeDaoImpl();
    CodeBean codeBean = new CodeBean();
%>

<%
	String requestType = request.getParameter("type");
    String code = request.getParameter("code");
    String codeValue = request.getParameter("codeValue");

	if (requestType.equals("add")) {	
		codeBean.setCode(Integer.parseInt(code));
		codeBean.setValue(codeValue);
		int r = codeDao.addCode(codeBean);
		AlertInfoWrite.alertInfo(out, r);
	} else if(requestType.equals("delete")){	
		AlertInfoWrite.alertInfo(out, codeDao.deleCode(Integer.parseInt(code)));
	} else if(requestType.equals("update")){
		codeBean.setCode(Integer.parseInt(code));
		codeBean.setValue(codeValue);
		int r = codeDao.updateCode(codeBean);
		AlertInfoWrite.alertInfo(out, r);
	}
%>
