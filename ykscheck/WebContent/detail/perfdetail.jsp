<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>

<%
   StrategyDao strategyDao = new StrategyImpl();
   UserDao userDao = new UserDaoImpl();
   StrategyDataDao strategyDataDao = new StrategyDataImpl();
%>

<%
   String id = request.getParameter("id");
   StrategyBean strategyBean = strategyDao.getStrategyBean(Integer.parseInt(id));
   List<StrategyDataBean> strategyDataList = strategyDataDao.getAllStrategyData(Integer.parseInt(id));
%>

<jsp:include page="../checkin.jsp" flush="true"/>
<div class="pageHeader">
	<form onsubmit="return validateCallback(this);" action="do/doshutdown.jsp?type=shutdownPool" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>			
			    <td colspan="4">
			                         策略名称：<%=strategyBean.getRemark() %>
			    </td>
			</tr>
			<tr>    
			    <td>
			                         策略Id：<%=strategyBean.getId() %>			    
			    </td>
			    <td>
			                         所属用户：<%=userDao.getUser(strategyBean.getUserId()).getName() %>			    
			    </td>
			    <td>
			                         负载机IP：<%=strategyBean.getClientIP() %>			    
			    </td>
			    <td>
			                         开始时间：<%=strategyBean.getStartTime() %>			    
			    </td>
		     </tr>
		     <tr>	    
			    <td>
			                        并发数：<%=strategyBean.getVusers() %>			    
			    </td>
			            <%if(strategyBean.getRunType().equals("byIteration")) { %>
			                 <td>并发方式：  按迭代次数</td>
			                 <td>迭代次数：<%=strategyBean.getIterationSet() %></td>
			            <%} else {%>
			                 <td>并发方式：  按运行时间</td>
			                 <td>运行时间：<%=strategyBean.getRunTimeSet() %>s</td>
			            <%} %>
				<td>
				            迭代间隔时间：<%=strategyBean.getAlternation() %>ms
				</td>
			</tr>
			<tr>	
				<td>
				            检查点：<%=strategyBean.getCheckpoint() %>
				</td>	
				<td>
				           期望值：<%=strategyBean.getExpectValue() %>
				</td>
				<td>
				         超时时间：<%=strategyBean.getMaxTimeout() %>ms
				</td>
				<td>&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td colspan="4">
				         测试地址：<%=strategyBean.getUrl() %>
				</td>				
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="126">
		<thead>
			<tr align="center">
			    <th width="5%">Id</th>
			    <th width="7%">所属策略</th>
			    <th width="8%">TPS</th>
			    <th width="8%">最大TPS</th>
			    <th width="8%">持续时间</th>
			    <th width="8%">响应时间</th>
			    <th width="8%">最大响应时间</th>
			    <th width="8%">吞吐量</th>
			    <th width="8%">通过数</th>
			    <th width="8%">失败数</th>
			    <th width="8%">异常数</th>
			    <th width="8%">通过率</th>
			    <th width="8%">失败率</th>
			</tr>
		</thead>
		<tbody>
		<%
             for(StrategyDataBean strateData : strategyDataList) {
		%>
		<tr target="sid_flushData" rel="1"  align="center">
			  <td><%=strateData.getId() %></td>
			  <td><%=strategyBean.getRemark() %></td>
			  <td><%=strateData.getTps() %></td>
			  <td><%=strateData.getMaxTPS() %></td>
			  <td><%=strateData.getDurationTime() %></td>
			  <td><%=strateData.getResponseTime() %></td>
			  <td><%=strateData.getMaxResponseTime() %></td>
			  <td><%=strateData.getThroughputBytes() %></td>
			  <td><%=strateData.getPassCount() %></td>
			  <td><%=strateData.getFailCount() %></td>
			  <td><%=strateData.getExceptionCount() %></td>
			  <td><%=strateData.getPassRate() %></td>
			  <td><%=strateData.getFailRate() %></td>
		</tr>
		<%
			}
		%>

		</tbody>
	</table>
</div>