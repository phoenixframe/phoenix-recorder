<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.youku.yks.perf.*" %>
<%@ page import="java.util.concurrent.Future" %>
<%@ page import="java.util.concurrent.ExecutionException" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.concurrent.ConcurrentHashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map.Entry" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.math.RoundingMode" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.youku.yks.bean.FlushBean" %>

<%
   PerformanceManag perf = (PerformanceManag)session.getAttribute("flush");
   ConcurrentHashMap<Long,FlushBean> flushMap = perf.getFlushMap();
   Object[] rateData = perf.getRateData();
   double TotalPass = Double.parseDouble(rateData[1]+"");
   double TotalError = Double.parseDouble(rateData[2]+"");
   double TotalException = Double.parseDouble(rateData[8]+"");
   //double SuccRate = new BigDecimal(TotalPass/(TotalPass+TotalError)).setScale(2, RoundingMode.HALF_UP).doubleValue();
%>
	 <script type="text/javascript">	
	 var t;
	    function dorefresh(){
	    	var str = $("#submitData").text();
	    	if( str == "停止刷新"){
	    		clearTimeout(t);
	    		$("#submitData").text("自动刷新");
	    	} else {
	    		t = setTimeout(refresh,1000);
	    		$("#submitData").text("停止刷新");
	    	}
	    }
	    function refresh(){
	    	$.ajaxSettings.global=false; 
	    	navTab.reloadFlag("flushview");
	    	$.ajaxSettings.global=true;
	    }
	    t = setTimeout(refresh,1000);
     </script>	


<jsp:include page="check.jsp" flush="true"/>
<div class="pageHeader">
	<form onsubmit="return validateCallback(this);" action="do/doshutdown.jsp?type=shutdownPool" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
			    <td>
			                           开始时间：<%=perf.getStartTime() %>
			    </td>
			    <td>
			                          已持续：<%=perf.getDurationTime() %>s
					&nbsp;&nbsp;|&nbsp;&nbsp;响应时间(avg)：<%=rateData[3] %>ms
					&nbsp;&nbsp; Max：<%=rateData[13] %>ms
				</td>
				<td>|</td>
				<td>
				             每秒吞吐量：<%=rateData[0] %>bytes
				</td>
				<td>
				   |&nbsp;&nbsp;&nbsp;成功率：<%=rateData[6] %>
				</td>	
				<td>
				             失败率：<%=rateData[7] %>
				</td>
				<td></td>
			</tr>
			<tr>
			<td>
			当前状态：
			<%if(rateData[9]!=null){ %>
			   <font color=red><%=rateData[9] %></font>
			<%} else if(rateData[4].equals(0)) { %>
			   <font color=blue>执行完成</font>
			<%} else if(!rateData[4].equals(0)) {%>
			   <font color=blue>正在执行...</font>
			<%} %>
			</td>
			    <td>
			       Total：<%=perf.getStrategyBean().getVusers() %> &nbsp;
			       Running：<%=rateData[11] %>&nbsp;
			       Complete：<%=rateData[12] %>&nbsp;
			       Stop：<%=rateData[10] %>
			    </td>
			    <td>|</td>
			    <td>
			                        每秒通过事物(tps)：<%=rateData[5] %>&nbsp;&nbsp;
			       Max：<%=rateData[14] %>                 
			    </td>	
				<td>
				   |&nbsp;&nbsp;&nbsp;总成功：<%=rateData[1] %>
				</td>	
				<td>
				             总失败：<%=rateData[2] %>
				</td>
				<td>
				             总异常：<%=rateData[8] %>
				</td>				
			</tr>
		</table>
		<div class="subBar">
			<ul>
			    <li>
			        <div class="buttonActive"><div class="buttonContent"><button type="button" id="submitData" onclick="dorefresh()" >停止刷新</button></div></div>
			    </li>
			    <li>
			        <div class="buttonActive"><div class="buttonContent"><button type="submit">停止测试</button></div></div>
			    </li>
			    <li>
			        <div class="buttonActive"><a title="监控图表查看" rel="flushchart" target="navTab" href="chart/table/flushchart.jsp" class="Info"><span>监控图表</span></a></div>
			    </li>
			    <li>
			        <div class="buttonActive"><div class="buttonContent"><button type="submit">结果入库</button></div></div>
			    </li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="126">
		<thead>
			<tr align="center">
			    <th width="8%">VuserId</th>
			    <th width="8%">当前状态</th>
			    <th width="8%">执行统计</th>
			    <th width="8%">剩余统计</th>
			    <th width="8%">错误统计</th>
			    <th width="8%">异常统计</th>
			    <th width="15%">过程信息</th>
			    <th width="14%">响应时间（ms）</th>
			    <th width="15%">吞吐量（bytes)</th>
			    <th width="8%">操作</th>
			</tr>
		</thead>
		<tbody>
		<%
			Iterator iter = flushMap.entrySet().iterator();
		       while(iter.hasNext()) {
		    	   Entry entry = (Entry)iter.next();
		    	   long key = (long)entry.getKey();
		    	   FlushBean flushBean = (FlushBean)entry.getValue();
		%>
		<tr target="sid_flushData" rel="1"  align="center">
			  <td><%=key %></td>
			  <td><%=flushBean.getStatus() %></td>
			  <td><%=flushBean.getCurrenCount() %></td>
			  <td><%=flushBean.getRemainCount() %></td>
			  <td><%=flushBean.getErrorCount() %></td>
			  <td><%=flushBean.getExceptionCount() %></td>
			  <td><%=flushBean.getErrorInfo() %></td>
			  <td><%=flushBean.getResponseTime() %></td>
			  <td><%=flushBean.getThroughtBytes() %></td>
			<td>
				<a title="要停止Vuser “<%=key %>” 吗？" target="ajaxTodo" href="do/doshutdown.jsp?type=shutdownThread&threadId=<%=key %>" class="btnDel">停止</a>
			</td>
		</tr>
		<%
			}
		%>

		</tbody>
	</table>
</div>