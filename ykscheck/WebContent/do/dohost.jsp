<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.tools.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%
    HostDao hostDao = new HostImpl();
    HostBean hostBean = new HostBean();
%>

<%
	String requestType = request.getParameter("type");
    String id = request.getParameter("id");
    String hostName = request.getParameter("hostName");
    String hostType = request.getParameter("hostType");
    String userId = request.getParameter("userId");
    
	if (requestType.equals("add")) {	
		hostBean.setUserId(Integer.parseInt(userId));
		hostBean.setRemark(hostType);
		hostBean.setHostName(hostName);
		int r = hostDao.addHost(hostBean);
		AlertInfoWrite.alertInfo(out, r);
	} else if(requestType.equals("delete")){	
		AlertInfoWrite.alertInfo(out, hostDao.deleHost(Integer.parseInt(id)));
	} else if(requestType.equals("update")){
		hostBean.setId(Integer.parseInt(id));
		hostBean.setRemark(hostType);
		hostBean.setHostName(hostName);
		int r = hostDao.updateHost(hostBean);
		AlertInfoWrite.alertInfo(out, r);
	}
%>
