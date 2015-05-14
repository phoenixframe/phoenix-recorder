<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>
<%@ page import="com.youku.yks.tools.*"%>
<script type="text/javascript">
var options = {
	axis: "0 0 1 1", // Where to put the labels (trbl)
	axisxstep: 10, // How many x interval labels to render (axisystep does the same for the y axis)
	shade:false, // true, false
	smooth:true, //æ²çº¿
	symbol:"circle"
};

$(function () {
	
	// Make the raphael object
	var r = Raphael("chartHolder"); 
	
	var lines = r.linechart(
		20, // X start in pixels
		10, // Y start in pixels
		600, // Width of chart in pixels
		400, // Height of chart in pixels
		[[.5,1.5,2,2.5,3,3.5,4,4.5,5],[.5,1.5,2,2.5,3,3.5,4,4.5,5]], // Array of x coordinates equal in length to ycoords
		[[7,11,9,16,3,19,12,12,15],[1,2,3,4,3,6,7,5,9]], // Array of y coordinates equal in length to xcoords
		options // opts object
	).hoverColumn(function () {
        this.tags = r.set();

        for (var i = 0, ii = this.y.length; i < ii; i++) {
            this.tags.push(r.tag(this.x, this.y[i], this.values[i], 160, 10).insertBefore(this).attr([{ fill: "#fff" }, { fill: this.symbols[i].attr("fill") }]));
        }
    }, function () {
        this.tags && this.tags.remove();
    });

    lines.symbols.attr({ r: 6 });
});
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="data.jsp" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<label>查询条件：</label>
				<input id="inputOrg1" name="org1.id" value="" type="hidden"/>
				<input name="org1.orgName" type="text" postField="keyword" size=40 value=>				
				</td>	
				<td>
				<a class="btnLook" href="lookback/backnode.jsp" lookupGroup="org1">查询带回</a>	
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
<div id="chartHolder" style="width: 650px;height: 450px"></div>
