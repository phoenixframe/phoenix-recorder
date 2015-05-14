<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.youku.yks.perf.*" %>
<%@ page import="java.util.concurrent.Future" %>
<%@ page import="java.util.concurrent.ExecutionException" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
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
	    function stopRefresh(){
	    	var str = $("#stopRefresh").text();
	    	if( str == "停止刷新"){
	    		clearTimeout(t);
	    		$("#stopRefresh").text("自动刷新");
	    	} else {
	    		t = setTimeout(chartrefresh,60000);
	    		$("#stopRefresh").text("停止刷新");
	    	}
	    }
	    function chartrefresh(){
	    	$.ajaxSettings.global=false; 
	    	navTab.reloadFlag("flushchart");
	    	$.ajaxSettings.global=true;
	    }
	    t = setTimeout(refresh,60000);
     </script>	
     
     		<% 
			out.println("	<script type=\"text/javascript\">");
			out.println("   $(function () {");
			out.println("      $('#chartHolder1').highcharts({");
			out.println("          chart: {");
			out.println("              type: 'spline'");
			out.println("          },");
			out.println("          title: {");
			out.println("              text: '平均响应时间'");
			out.println("          },");
			out.println("          subtitle: {");
			out.println("              text: ''");
			out.println("          },");
 			out.println("          xAxis: [{");
			out.println("              categories: [10,20,30,40,50],");
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
			out.println("              data: [1,3,6,9,4]");
			out.println("          }]");
			out.println("      });");
			out.println("  });");
			out.println("	</script>");
			%>
			
		<script type="text/javascript">
$(function () {
    $(document).ready(function() {
        Highcharts.setOptions({
            global: {
                useUTC: false
            }
        });
    
        var chart;
        $('#chartHolder2').highcharts({
            chart: {
                type: 'spline',
                animation: Highcharts.svg, // don't animate in old IE
                marginRight: 10,
                events: {
                    load: function() {
    
                        // set up the updating of the chart each second
                        var series = this.series[0];
                        setInterval(function() {
                            var x = (new Date()).getTime(), // current time
                                y = Math.random();
                            series.addPoint([x, y], true, true);
                        }, 60000);
                    }
                }
            },
            title: {
                text: '每秒吞吐量'
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 150
            },
            yAxis: {
                title: {
                    text: 'Value'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                formatter: function() {
                        return '<b>'+ this.series.name +'</b><br/>'+
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br/>'+
                        Highcharts.numberFormat(this.y, 2);
                }
            },
            legend: {
                enabled: false
            },
            exporting: {
                enabled: false
            },
            series: [{
                name: 'Random data',
                data: (function() {
                    // generate an array of random data
                    var data = [],
                        time = (new Date()).getTime(),
                        i;
    
                    for (i = -19; i <= 0; i++) {
                        data.push({
                            x: time + i * 1000,
                            y: Math.random()
                        });
                    }
                    return data;
                })()
            }]
        });
    });
    
});
		</script>
		
		<script type="text/javascript">
$(function () {
    $(document).ready(function() {
        Highcharts.setOptions({
            global: {
                useUTC: false
            }
        });
    
        var chart;
        $('#chartHolder3').highcharts({
            chart: {
                type: 'spline',
                animation: Highcharts.svg, // don't animate in old IE
                marginRight: 10,
                events: {
                    load: function() {
    
                        // set up the updating of the chart each second
                        var series = this.series[0];
                        setInterval(function() {
                            var x = (new Date()).getTime(), // current time
                                y = Math.random();
                            series.addPoint([x, y], true, true);
                        }, 60000);
                    }
                }
            },
            title: {
                text: 'TPS'
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 150
            },
            yAxis: {
                title: {
                    text: 'Value'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                formatter: function() {
                        return '<b>'+ this.series.name +'</b><br/>'+
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br/>'+
                        Highcharts.numberFormat(this.y, 2);
                }
            },
            legend: {
                enabled: false
            },
            exporting: {
                enabled: false
            },
            series: [{
                name: 'Random data',
                data: (function() {
                    // generate an array of random data
                    var data = [],
                        time = (new Date()).getTime(),
                        i;
    
                    for (i = -19; i <= 0; i++) {
                        data.push({
                            x: time + i * 1000,
                            y: Math.random()
                        });
                    }
                    return data;
                })()
            }]
        });
    });
    
});
		</script>
		
		<script type="text/javascript">
$(function () {
    $(document).ready(function() {
        Highcharts.setOptions({
            global: {
                useUTC: false
            }
        });
    
        var chart;
        $('#chartHolder4').highcharts({
            chart: {
                type: 'spline',
                animation: Highcharts.svg, // don't animate in old IE
                marginRight: 10,
                events: {
                    load: function() {
    
                        // set up the updating of the chart each second
                        var series = this.series[0];
                        setInterval(function() {
                            var x = (new Date()).getTime(), // current time
                                y = Math.random();
                            series.addPoint([x, y], true, true);
                        }, 60000);
                    }
                }
            },
            title: {
                text: '事物通过总数'
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 150
            },
            yAxis: {
                title: {
                    text: 'Value'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                formatter: function() {
                        return '<b>'+ this.series.name +'</b><br/>'+
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br/>'+
                        Highcharts.numberFormat(this.y, 2);
                }
            },
            legend: {
                enabled: false
            },
            exporting: {
                enabled: false
            },
            series: [{
                name: 'Random data',
                data: (function() {
                    // generate an array of random data
                    var data = [],
                        time = (new Date()).getTime(),
                        i;
    
                    for (i = -19; i <= 0; i++) {
                        data.push({
                            x: time + i * 1000,
                            y: Math.random()
                        });
                    }
                    return data;
                })()
            }]
        });
    });
    
});
		</script>


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
			    </td>
			    <td>|</td>
				<td>
					响应时间(avg)：<%=rateData[3] %>ms
				</td>
				<td>
				             每秒吞吐量：<%=rateData[0] %>bytes
				</td>
				<td>
				   |&nbsp;&nbsp;&nbsp;成功率：<%=rateData[6] %>
				</td>	
				<td>
				             失败率：<%=rateData[7] %>
				</td>
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
			                        每秒通过事物(tps)：<%=rateData[5] %>
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
			        <div class="buttonActive"><div class="buttonContent"><button type="button" id="stopRefresh" onclick="stopRefresh()" >停止刷新</button></div></div>
			    </li>
			    <li>
			        <div class="buttonActive"><div class="buttonContent"><button type="submit">停止测试</button></div></div>
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
<table width=95% layoutH="86">
<tr>
<td width=45%>
<div id="chartHolder1" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</td>
<td width=45%>
<div id="chartHolder2" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</td>
</tr>
<tr>
<td>
<div id="chartHolder3" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</td>
<td>
<div id="chartHolder4" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</td>
</tr>
</table>
</div>
