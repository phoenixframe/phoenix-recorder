<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.tools.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%
	ParamDao paramDao = new ParamDaoImpl();
    InterfaceDao reqDataDao = new InterfaceDaoImpl();
	ParamBean paramBean = new ParamBean();
%>

<%
	String requestType = request.getParameter("type");
    String id = request.getParameter("id");
    String interfaceId = request.getParameter("interType");
    String paramName = request.getParameter("paramName");
    String userId = request.getParameter("userId");
    String paramType = request.getParameter("orgLookup.orgName"); 
    String paramValue = request.getParameter("paramValue");
    
	if (requestType.equals("add")) {	
		paramBean.setUserId(Integer.parseInt(userId));
		paramBean.setInterfaceId(Integer.parseInt(interfaceId));
		paramBean.setParamName(paramName);
		paramBean.setParamType(paramType);
		paramBean.setParamValue(paramValue);
		int r = paramDao.addParam(paramBean);
		AlertInfoWrite.alertInfo(out, r);
	} else if(requestType.equals("delete")){	
		AlertInfoWrite.alertInfo(out, paramDao.deleParam(Integer.parseInt(id)));
	} else if(requestType.equals("update")){
		paramBean.setId(Integer.parseInt(id));
		paramBean.setInterfaceId(Integer.parseInt(interfaceId));
		paramBean.setParamName(paramName);
		paramBean.setParamType(paramType);
		paramBean.setParamValue(paramValue);
		int r = paramDao.updateParam(paramBean);
		AlertInfoWrite.alertInfo(out, r);
	}
%>
