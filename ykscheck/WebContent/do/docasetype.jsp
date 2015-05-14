<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.tools.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%
	CaseTypeDao caseTypeDao = new CaseTypeImpl();
	CaseTypeBean caseTypeBean = new CaseTypeBean();
%>

<%
	String requestType = request.getParameter("type");
    String caseTypeName = request.getParameter("caseTypeName");
    String isDisabled = request.getParameter("orgLookup.orgNum"); 
    String userId = request.getParameter("userId");
	if (requestType.equals("add")) {
		caseTypeBean.setUserid(Integer.parseInt(userId));
		caseTypeBean.setTypeName(caseTypeName);
		caseTypeBean.setIsDisabled(Integer.parseInt(isDisabled));
		int r = caseTypeDao.addCaseType(caseTypeBean);
		if (r > 0) {
			out.print("{\"statusCode\":\"200\",\"message\":\"用例类型 <font color=blue>"
					+ caseTypeBean.getTypeName()
					+ "</font> 创建成功\",\"navTabId\":\"\",\"callbackType\":\"$.pdialog.closeCurrent(); \",\"forwardUrl\":\"\"}");
		} else {
			out.print("{\"statusCode\":\"300\",\"message\":\"用例类型创建失败，数据有误!\",\"navTabId\":\"\",\"callbackType\":\"\",\"forwardUrl\":\"\"}");
		}
	} else if(requestType.equals("delete")){
		String id = request.getParameter("id");
		AlertInfoWrite.alertInfo(out, caseTypeDao.deleCaseType(Integer.parseInt(id)));
	} else if(requestType.equals("update")){
		String id = request.getParameter("id");
		caseTypeBean.setUserid(Integer.parseInt(userId));
		caseTypeBean.setId(Integer.parseInt(id));
		caseTypeBean.setTypeName(caseTypeName);
		caseTypeBean.setIsDisabled(Integer.parseInt(isDisabled));
		int r = caseTypeDao.updateCaseType(caseTypeBean);
		AlertInfoWrite.alertInfo(out, r);
	}
%>
