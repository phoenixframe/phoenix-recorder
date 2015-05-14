<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.controller.ResultAnalyse" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.tools.GetNow" %>
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

<%
	if(logBatchId !=null && caseTypeSel != null && !caseTypeSel.equals("-1")){
		out.println("		<script type=\"text/javascript\">");
		out.println("   $(function () {");
		out.println("        $('#chartHolder2').highcharts({");
		out.println("            chart: {");
		out.println("                type: 'column'");
		out.println("            },");
		out.println("            title: {");
		out.println("                text: '批次号："+batchValue+"'");
		out.println("            },");
		out.println("            subtitle: {");
		out.println("                text: '"+GetNow.getCurrentTime()+"'");
		out.println("            },");
		out.println("            xAxis: {");
		out.println("                categories: ["+resultList.get(2)+"]");
		out.println("            },");
		out.println("            yAxis: {");
		out.println("                min: 0,");
		out.println("                title: {");
		out.println("                    text: '次数统计'");
		out.println("                }");
		out.println("            },");
		out.println("            tooltip: {");
		out.println("                headerFormat: '<span style=\"font-size:10px\">{point.key}</span><table>',");
		out.println("                pointFormat: '<tr><td style=\"color:{series.color};padding:0\">{series.name}: </td>' +");
		out.println("                    '<td style=\"padding:0\"><b>{point.y}</b></td></tr>',");
		out.println("                footerFormat: '</table>',");
		out.println("                shared: true,");
		out.println("                useHTML: true");
		out.println("            },");
		out.println("            plotOptions: {");
		out.println("                column: {");
		out.println("                    pointPadding: 0.2,");
		out.println("                    borderWidth: 0");
		out.println("                }");
		out.println("            },");
		out.println("            series: [{");
		out.println("                name: 'Success',");
		out.println("                data: ["+resultList.get(0)+"]");   
		out.println("            }, {");
		out.println("                name: 'Fail',");
		out.println("                data: ["+resultList.get(1)+"]");
		out.println("            }]");
		out.println("        });");
		out.println("    });");
		out.println("		</script>");
	}
%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="./chart/table/barchart.jsp" rel="barChart" method="post">
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
					<select class="combox" name="caseType" id="caseType" style="width:150px">
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
<div id="chartHolder2" style="min-width: 310px; height: 480px; margin: 0 auto"></div>