<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.tools.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%
	InterfaceDao reqDataDao = new InterfaceDaoImpl();
    InterfaceBean reqDataBean = new InterfaceBean();
%>

<%
	String requestType = request.getParameter("type");
    String id = request.getParameter("id");
    String reqDataName = request.getParameter("reqDataName");
    String userId = request.getParameter("userId");
    
	if (requestType.equals("add")) {	
		reqDataBean.setUserId(Integer.parseInt(userId));
		reqDataBean.set_interface(reqDataName);
		int r = reqDataDao.addInterface(reqDataBean);
		AlertInfoWrite.alertInfo(out, r);
	} else if(requestType.equals("delete")){	
		AlertInfoWrite.alertInfo(out, reqDataDao.deleInterface(Integer.parseInt(id)));
	} else if(requestType.equals("update")){
		reqDataBean.setId(Integer.parseInt(id));
		reqDataBean.set_interface(reqDataName);
		int r = reqDataDao.updateInterface(reqDataBean);
		AlertInfoWrite.alertInfo(out, r);
	}
%>
