<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.youku.yks.perf.*" %>
<%@ page import="com.youku.yks.tools.*" %>
<%@ page import="com.youku.yks.bean.*" %>

<%
	PerformanceManag perMana = new PerformanceManag();
    StrategyBean strategy = new StrategyBean();
    UserBean userBean = (UserBean)session.getAttribute("user");
%>

<%
   String url = request.getParameter("url");
   String runVusers = request.getParameter("runVusers");
   
   String exetype = request.getParameter("exetype");   
   String iterationSet = request.getParameter("iterationSet");
   String runTimeSet = request.getParameter("runTimeSet"); 
   
   String alternation = request.getParameter("alternation");
   String checkpoint = request.getParameter("checkpoint");
   String expectValue = request.getParameter("expectValue");
   String maxTimeout = request.getParameter("maxTimeout");
   String remark = request.getParameter("remark");
   
/*    String addStrategy = request.getParameter("addStrategy");
   String addTime = request.getParameter("addTime");
   String addVuser = request.getParameter("addVuser");
   
   String reduceStrategy = request.getParameter("reduceStrategy");
   String reduceTime = request.getParameter("reduceTime");
   String reduceVuser = request.getParameter("reduceVuser"); */

   strategy.setRemark(remark);
   strategy.setUserId(userBean.getId());
   strategy.setUrl(url);
   strategy.setVusers(Integer.parseInt(runVusers));
   strategy.setRunType(exetype);
   if(exetype.equals("byRunTime")){
	   strategy.setRunTimeSet(runTimeSet);
   } else {
	   strategy.setIterationSet(Integer.parseInt(iterationSet));
   }
   
   strategy.setAlternation(Integer.parseInt(alternation));
   strategy.setCheckpoint(checkpoint);
   strategy.setExpectValue(expectValue);
   if(maxTimeout == null) maxTimeout = "-1";
   strategy.setMaxTimeout(maxTimeout);
   
/*    strategy.setAddStrategy(addStrategy);
   if(!addStrategy.equals("addAll")){
	   strategy.setAddTime(Integer.parseInt(addTime));
	   strategy.setAddVuser(Integer.parseInt(addVuser));
   }
   
   strategy.setReduceStrategy(reduceStrategy);
   if(!reduceStrategy.equals("reduceAll")){
	   strategy.setReduceTime(Integer.parseInt(reduceTime));
	   strategy.setReduceVuser(Integer.parseInt(reduceVuser));
   } */
   
   perMana.setStrategyBean(strategy);
   
 	int r = perMana.taskManager();
	
	if(r == 1){
		  AlertInfoWrite.alertInfo(out, r);
		  session.setAttribute("flush", perMana);
	}
%>