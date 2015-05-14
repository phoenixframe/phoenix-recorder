<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.lab.OperaCenter" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.tools.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>

<%
  CaseTypeDao caseTypeDao = new CaseTypeImpl();
  LogBatch logBatchDao = new LogBatchImpl();
  UserBean userBean = (UserBean)session.getAttribute("user");
%>

<%
   String[] ids = request.getParameterValues("ids");
   int logBatchId = logBatchDao.ceateBatchId(userBean.getId());
   for(String id : ids){
	   new OperaCenter(logBatchId
			   ,new RunTimeBean(caseTypeDao.getUnitCaseType(Integer.parseInt(id)).getUserid()
					   ,Integer.parseInt(id))
	   ).runCase();
   }   
   AlertInfoWrite.alertInfo(out, 1);
%>