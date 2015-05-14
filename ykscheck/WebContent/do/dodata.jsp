<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.tools.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%
    DataDao dataDao = new DataImpl();
    DataBean dataBean = new DataBean();
%>

<%
	String requestType = request.getParameter("type");
    String id = request.getParameter("id");
    String paramNodeId = request.getParameter("org1.orgNum");
    String reqDataType = request.getParameter("orgLookup.orgName");
    String jsonPath = request.getParameter("jsonPath");
    String expect = request.getParameter("expectValue");
    String remark = request.getParameter("remark");
    String userId = request.getParameter("userId");
    
	if (requestType.equals("add")) {	
		dataBean.setUserId(Integer.parseInt(userId));
		dataBean.setJsonPath(jsonPath);
		dataBean.setExpect(expect);
		dataBean.setParamNodeId(Integer.parseInt(paramNodeId));
		dataBean.setRemark(remark);
		dataBean.setRequestType(reqDataType);
		int r = dataDao.addData(dataBean);
		AlertInfoWrite.alertInfo(out, r);
	} else if(requestType.equals("delete")){	
		AlertInfoWrite.alertInfo(out, dataDao.deleData(Integer.parseInt(id)));
	} else if(requestType.equals("update")){
		dataBean.setUserId(Integer.parseInt(userId));
		dataBean.setId(Integer.parseInt(id));
		dataBean.setExpect(expect);
		dataBean.setJsonPath(jsonPath);
		dataBean.setParamNodeId(Integer.parseInt(paramNodeId));
		dataBean.setRemark(remark);
		dataBean.setRequestType(reqDataType);
		int r = dataDao.updateData(dataBean);
		AlertInfoWrite.alertInfo(out, r);
	}
%>
