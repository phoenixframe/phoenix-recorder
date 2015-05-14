<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.controller.ResultAnalyse" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>

<%
  UserBean userBean = (UserBean)session.getAttribute("user");
  CaseTypeDao caseTypeDao = new CaseTypeImpl();
  CaseDao caseDao = new CaseImpl();
  ResultAnalyse resultAna = new ResultAnalyse();
  List<CaseTypeBean> caseTypeList = null;
  List<String> resultList = null;
%>

<%
   String caseTypeSel = request.getParameter("caseType");
   String logBatchId = request.getParameter("org1.id");
   String batchValue = request.getParameter("org1.orgName");
   if(userBean == null){
	     caseTypeList = caseTypeDao.getAllCaseType();
   } else {
	     caseTypeList = caseTypeDao.getAllCaseTypeByUser(userBean.getId());
   }
   
   if(logBatchId !=null && caseTypeSel != null && !caseTypeSel.equals("-1")){
	   resultList = resultAna.getResultRate(Integer.parseInt(logBatchId),Integer.parseInt(caseTypeSel));
   }else{
	   batchValue = "";
   }
%>


<script type="text/javascript" charset="utf-8">
/* Title settings */		
title = "批次下各用例类型统计";
titleXpos = 730;
titleYpos = 85;

/* Pie Data */
pieRadius = 130;
pieXpos = 500;
pieYpos = 180;
pieData = [114922, 351315, 172095, 166565, 53329, 18060, 8074, 1941, 1393, 1104, 2110];
pieLegend = [
"%%.%% – Firefox", 
"%%.%% – Internet Explorer", 
"%%.%% – Chrome", 
"%%.%% – Safari", 
"%%.%% – Opera", 
"%%.%% – Mozilla", 
"%%.%% – Mozilla Compatible Agent", 
"%%.%% – Opera Mini", 
"%%.%% – SeaMonkey", 
"%%.%% – Camino", 
"%%.%% – Other"];

pieLegendPos = "east";

$(function () {
	var r = Raphael("chartHolder");
	 
	r.text(titleXpos, titleYpos, title).attr({"font-size": 20});
	
	var pie = r.piechart(pieXpos, pieYpos, pieRadius, pieData, {legend: pieLegend, legendpos: pieLegendPos});
	pie.hover(function () {
		this.sector.stop();
		this.sector.scale(1.1, 1.1, this.cx, this.cy);
		if (this.label) {
			this.label[0].stop();
			this.label[0].attr({ r: 7.5 });
			this.label[1].attr({"font-weight": 800});
		}
	}, function () {
		this.sector.animate({ transform: 's1 1 ' + this.cx + ' ' + this.cy }, 500, "bounce");
		if (this.label) {
			this.label[0].animate({ r: 5 }, 500, "bounce");
			this.label[1].attr({"font-weight": 400});
		}
	});
	
});
</script>

<div class="pageHeader">
	<form rel="piechart" onsubmit="return navTabSearch(this);" action="./chart/table/barchart.jsp" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<label>选择批次：</label>
				    <input id="inputOrg1" name="org1.id" value="<%=logBatchId %>" type="hidden"/>
				    <input name="org1.orgName" readonly="true" type="text" postField="keyword" size=40 value=<%=batchValue %>>				
				</td>	
				<td>
				    <a class="btnLook" href="lookback/backlogbatch.jsp" lookupGroup="org1">查找带回</a>	
				</td>
				<td>
					<label>用例类型筛选：</label>
					<select class="combox" name="caseType" id="caseType">
					 <option value="-1">请选择用例类型</option>
						<%
						for(CaseTypeBean caseType : caseTypeList){
							if((caseType.getId()+"").equals(caseTypeSel)){
						%>						        
						        <option selected="selected" value=<%=caseType.getId() %>><%=caseType.getTypeName() %></option>
						<%
						    }else{
						    	%>
						    	<option value=<%=caseType.getId() %>><%=caseType.getTypeName() %></option>
						    	<%
						    }
						}
						%>
					</select>					
				</td>							
				<td>
				   <div class="buttonActive"><div class="buttonContent"><button type="submit">提交数据</button></div></div>
				</td>			
			</tr>
		</table>
		<div class="subBar">
			<ul>
			    <li></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div align="center" id="chartHolder"></div>
