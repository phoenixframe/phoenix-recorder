<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.youku.yks.perf.*" %>
<%@ page import="com.youku.yks.tools.*" %>

<%
    PerformanceManag perf = (PerformanceManag)session.getAttribute("flush");
    String type = request.getParameter("type");
    String threadId = request.getParameter("threadId");
    if(type.equals("shutdownPool")){
    int r = perf.shutdownPool();
       if(r == 1){
    	   AlertInfoWrite.alertInfo(out, r);
    	   perf.stopTimer();
    	   //session.removeAttribute("flush");
       } else {
    	   AlertInfoWrite.alertInfo(out, 0);
       }
    }else if (type.equals("shutdownThread")){
    	 AlertInfoWrite.alertInfo(out, perf.shutdownThread(Integer.parseInt(threadId)));
    }
    
%>
