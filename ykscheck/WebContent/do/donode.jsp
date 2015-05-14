<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.tools.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%
    NodeDao nodeDao = new NodeImpl();
    NodeBean nodeBean = new NodeBean();
%>

<%
	String requestType = request.getParameter("type");
    String id = request.getParameter("id");
    String caseId = request.getParameter("org1.id");
    String nodeName = request.getParameter("caseNodeName");
    String isDisabled = request.getParameter("orgLookup.orgNum"); 
    String remark = request.getParameter("remark");
    String userId = request.getParameter("userId");
    
	if (requestType.equals("add")) {	
		nodeBean.setUserId(Integer.parseInt(userId));
		nodeBean.setCaseNodeId(Integer.parseInt(caseId));
		nodeBean.setIsDisabled(Integer.parseInt(isDisabled));
		nodeBean.setParamNode(nodeName);
		nodeBean.setRemark(remark);
		int r = nodeDao.addNode(nodeBean);
		AlertInfoWrite.alertInfo(out, r);
	} else if(requestType.equals("delete")){	
		AlertInfoWrite.alertInfo(out, nodeDao.deleNode(Integer.parseInt(id)));
	} else if(requestType.equals("update")){
		nodeBean.setId(Integer.parseInt(id));
		nodeBean.setUserId(Integer.parseInt(userId));
		nodeBean.setCaseNodeId(Integer.parseInt(caseId));
		nodeBean.setIsDisabled(Integer.parseInt(isDisabled));
		nodeBean.setParamNode(nodeName);
		nodeBean.setRemark(remark);
		int r = nodeDao.updateNode(nodeBean);
		AlertInfoWrite.alertInfo(out, r);
	}
%>
