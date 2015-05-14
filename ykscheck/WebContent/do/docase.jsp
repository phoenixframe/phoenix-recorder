<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.tools.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%
	CaseDao caseDao = new CaseImpl();
    CaseTypeDao caseType = new CaseTypeImpl();
	CaseBean caseBean = new CaseBean();
%>

<%
	String requestType = request.getParameter("type");
    String id = request.getParameter("id");
    String caseTypeId = request.getParameter("caseTypeSelect");
    String caseName = request.getParameter("caseName");
    String isDisabled = request.getParameter("orgLookup.orgNum"); 
    String remark = request.getParameter("remark");
    String userId = request.getParameter("userId");
    String vid = request.getParameter("vidSel");
    String hostId = request.getParameter("hostSel");
    String interfaceId = request.getParameter("interfaceSel");
    
	if (requestType.equals("add")) {	
		caseBean.setUserId(Integer.parseInt(userId));
		caseBean.setTypeNameId(Integer.parseInt(caseTypeId));
		caseBean.setVid(Integer.parseInt(vid));
		caseBean.setHostId(Integer.parseInt(hostId));
		caseBean.setInterfaceId(Integer.parseInt(interfaceId));
		caseBean.setCaseNode(caseName);
		caseBean.setRemark(remark);
		caseBean.setIsDisabled(Integer.parseInt(isDisabled));
		int r = caseDao.addCase(caseBean);
		AlertInfoWrite.alertInfo(out, r);
	} else if(requestType.equals("delete")){	
		AlertInfoWrite.alertInfo(out, caseDao.deleCase(Integer.parseInt(id)));
	} else if(requestType.equals("update")){
		caseBean.setId(Integer.parseInt(id));
		caseBean.setTypeNameId(Integer.parseInt(caseTypeId));
		caseBean.setVid(Integer.parseInt(vid));
		caseBean.setHostId(Integer.parseInt(hostId));
		caseBean.setInterfaceId(Integer.parseInt(interfaceId));
		caseBean.setCaseNode(caseName);
		caseBean.setRemark(remark);
		caseBean.setIsDisabled(Integer.parseInt(isDisabled));
		int r = caseDao.updateCase(caseBean);
		AlertInfoWrite.alertInfo(out, r);
	}
%>
