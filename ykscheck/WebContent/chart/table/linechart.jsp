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
   int limit = 20;
   String caseTypeId = request.getParameter("caseType");
   
   if(userBean == null){
	     caseTypeList = caseTypeDao.getAllCaseType();
       } else {
	     caseTypeList = caseTypeDao.getAllCaseTypeByUser(userBean.getId());
     }
   if(caseTypeId != null && !caseTypeId.equals("-1")){
	  resultList = resultAna.getLineChartData(Integer.parseInt(caseTypeId), limit);
   }
%>

		<% 
	if(caseTypeId != null && !caseTypeId.equals("-1")){
		   String caseTypeName = caseTypeDao.getUnitCaseType(Integer.parseInt(caseTypeId)).getTypeName();
			out.println("	<script type=\"text/javascript\">");
			out.println("   $(function () {");
			out.println("      $('#chartHolder').highcharts({");
			out.println("          chart: {");
			out.println("              type: 'spline'");
			out.println("          },");
			out.println("          title: {");
			out.println("              text: '"+caseTypeName +"最近"+limit+"批走势图'");
			out.println("          },");
			out.println("          subtitle: {");
			out.println("              text: '"+GetNow.getCurrentTime()+"'");
			out.println("          },");
 			out.println("          xAxis: [{");
			out.println("              categories: ["+resultList.get(2)+"],");
			out.println("          labels: { ");
		    out.println("              rotation: -45,  //逆时针旋转45°，标签名称太长。"); 
		    out.println("              align: 'right'  //设置右对齐"); 
		    out.println("              } ");
			out.println("          }],"); 
/* 			out.println("          xAxis: {");
			out.println("              categories: ["+resultList.get(2)+"],");
			out.println("          },"); */
			out.println("          yAxis: {");
			out.println("              title: {");
			out.println("                  text: '次数统计'");
			out.println("              },");
			out.println("              labels: {");
			out.println("                  formatter: function() {");
			out.println("                      return this.value");
			out.println("                  }");
			out.println("              }");
			out.println("          },");
			out.println("          tooltip: {");
			out.println("              crosshairs: true,");
			out.println("              shared: true");
			out.println("          },");
			out.println("          plotOptions: {");
			out.println("              spline: {");
			out.println("                  marker: {");
			out.println("                      radius: 4,");
			out.println("                      lineColor: '#666666',");
			out.println("                      lineWidth: 1");
			out.println("                  }");
			out.println("              }");
			out.println("          },");
			out.println("          series: [{");
			out.println("              name: 'Success',");
			out.println("              marker: {");
			out.println("                  symbol: 'square'");
			out.println("              },");
			out.println("              data: ["+resultList.get(0)+"]");
			out.println("          }, {");
			out.println("              name: 'Fail',");
			out.println("              marker: {");
			out.println("                  symbol: 'diamond'");
			out.println("              },");
			out.println("              data: ["+resultList.get(1)+"]");
			out.println("          }]");
			out.println("      });");
			out.println("  });");
			out.println("  ");
			out.println("");
			out.println("	</script>");
	}
			%>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="./chart/table/linechart.jsp" rel="lineChart" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<label>用例类型筛选：</label>
					<select class="combox" name="caseType" id="caseType">
					         <option value="-1">请选择用例类型</option>
						<%
						for(CaseTypeBean caseType : caseTypeList){
							if((caseType.getId()+"").equals(caseTypeId)){
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
				   <div class="buttonActive"><div class="buttonContent"><button type="submit">提交数据</button></div></div>
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

<div id="chartHolder" style="min-width: 310px; height: 480px; margin: 0 auto"></div>
