<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.youku.yks.bean.UserBean" %>

<head>
<title>优酷播放服务测试</title>
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<link href="themes/azure/style.css" rel="stylesheet" type="text/css" media="screen" />
<link href="themes/css/core.css" rel="stylesheet" type="text/css" media="screen" />
<link href="themes/css/print.css" rel="stylesheet" type="text/css" media="print" />
<link href="uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen" />
<!--[if IE]>
<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<!--[if lte IE 10]>
<script src="js/speedup.js" type="text/javascript"></script>
<![endif]-->

<script src="js/jquery-1.7.2.js" type="text/javascript"></script>
<script src="xheditor/xheditor-1.2.1.min.js" type="text/javascript"></script>
<script src="xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
<script src="js/jquery.cookie.js" type="text/javascript"></script>

<script src="js/jquery.validate.js" type="text/javascript"></script>
<script src="js/jquery.bgiframe.js" type="text/javascript"></script>
    
<script src="js/dwz.min.js" type="text/javascript"></script>
<script src="js/dwz.regional.zh.js" type="text/javascript"></script>

<script type="text/javascript">
	$(function() {
		DWZ.init("dwz.frag.xml", {
			loginUrl : "login_dialog.html",
			loginTitle : "登录",
			statusCode : {
				ok:200,
				error:300,		
				timeout:301,	
				loginfail:302
			}, //【可选】
			pageInfo : {
				pageNum : "pageNum",
				numPerPage : "numPerPage",
				orderField : "orderField",
				orderDirection : "orderDirection"
			}, //【可选】
			debug : false, // 调试模式 【true|false】
			callback : function() {
				initEnv();
				$("#themeList").theme({
					themeBase : "themes"
				}); // themeBase 相对于index页面的主题base路径
			}
		});
	});	
</script>
    <%
    UserBean user = (UserBean)session.getAttribute("user");
       if(user ==null){
	%>
     <script type="text/javascript">
             function loginCheck(){
            	  alertMsg.error('您还未登陆，请先登陆!<br><font style="color:red">登陆成功后会刷新页面，请注意数据保存！</font>');
	              $.pdialog.open("login_dialog.jsp", "登陆", "登陆","width:100px,height:100px,max:true,mask:true,mixable:true,resizable:true");
             }
           setTimeout(loginCheck,500);
          // window.onload = loginCheck;
     </script>
    <%
       }else{
    %>
    <script type="text/javascript">
             function loginResult(){
            	 alertMsg.correct("已登陆系统！");
             }
           setTimeout(loginResult,500);
     </script>   
    <%
       }
    %>
</head>

<body>
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo">标志</a>
				<ul class="nav">
				<%
				if(user == null) {
				%>
				    <li><a title="登陆" href="login_dialog.jsp" target="dialog" rel="login_page"><span>点击登陆</span></a></li>		
				    <li></li>		
				<%
				} else {
					%>
					<li><a>欢迎您：<%=user.getName() %> [ <%=user.getRemark() %> ]</a></li>
					<li><a href="login.jsp?type=loginout">退出</a></li>
					<li></li>
					<%
				}
				%>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="azure"><div class="selected">天蓝</div></li>
					<li theme="default"><div>蓝色</div></li>
					<li theme="green"><div>绿色</div></li>
					<li theme="purple"><div>紫色</div></li>
					<li theme="silver"><div>银色</div></li>
				</ul>
			</div>

			<!-- navMenu -->

		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse">
						<div></div>
					</div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse">
					<h2>主菜单</h2>
					<div>收缩</div>
				</div>

				<div class="accordion" fillSpace="sidebar">	
				<%if(user != null) {%>			
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>实验室
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="run.jsp" target="navTab" rel="runtask">执行测试</a></li>
							<!-- <li><a href="log.jsp" target="navTab" rel="taskmanager">任务调度</a></li> -->
							<li><a href="tree.jsp" target="dialog" rel="treeview">用例结构视图</a></li>
						</ul>
					</div>
					
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>过程监控
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
                            <li><a href="monitor.jsp" target="navTab" rel="monitor">用例监控</a></li>
						</ul>
					</div>
					<%} %>
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>日志检视
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<%if(user != null) {%>
							<li><a href="logmag.jsp" target="navTab" rel="logamag">日志批次查看</a></li>
							<%} %>
							<li><a href="log.jsp" target="navTab" rel="logview">详细日志查看</a></li>
						</ul>
					</div>
					
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>统计报表
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
						    <li><a href="chart/table/barchart.jsp" target="navTab" rel="barchart">单批次走势</a></li>
							<li><a href="chart/table/linechart.jsp" target="navTab" rel="linechart">用例类型走势</a></li>							
							<!-- <li><a href="chart/table/piechart.jsp" target="navTab" rel="chart">饼图检视</a></li> -->
						</ul>
					</div>
					<%if(user != null) {%>
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>数据维护
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="casetype.jsp" target="navTab" rel="casetypemag">用例类型维护</a></li>
							<li><a href="host.jsp" target="navTab" rel="hostmag">Host地址维护</a></li>
							<li><a href="reqdata.jsp" target="navTab" rel="requestdatamag">请求接口维护</a></li>
							<li><a href="param.jsp" target="navTab" rel="reqparammag">地址参数维护</a></li>
							<li><a href="vid.jsp" target="navTab" rel="videoidmag">Vid参数维护</a></li>
							<li><a href="case.jsp" target="navTab" rel="casemag">用例维护</a></li>
							<li><a href="node.jsp" target="navTab" rel="nodemag">用例节点维护</a></li>
							<li><a href="data.jsp" target="navTab" rel="casedatamag">用例数据维护</a></li>
						</ul>
					</div>
					<%} %>
					<%if(user != null && user.getLevel() == 0) {%>
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>系统管理
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="user.jsp" target="navTab" rel="usermag">用户管理</a></li>
							<li><a href="code.jsp" target="navTab" rel="errorcodemag">错误码管理</a></li>
						</ul>
					</div>
					<%} %>
					<%if(user != null) {%>
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>并发测试
						</h2>
					</div>

					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="flush.jsp" target=navTab rel="flushmag">并发测试管理</a></li>
<!-- 							<li><a href="flushview.jsp" target=navTab rel="flushview">测试过程查看</a></li>
							<li><a href="chart/table/flushchart.jsp" target=navTab rel="flushchart">监控图表查看</a></li> -->
							<li><a href="history.jsp" target=navTab rel="flushhistory">历史测试结果</a></li>
						</ul>
					</div>
					<%} %>
					
					<%if(user != null) {%>
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>周报管理
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
						
							<li><a href="diary.jsp" target=navTab rel="diary">工作周报管理</a></li>						
						</ul>
					</div>
					<%} %>
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>其他工具扩展
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
						<!-- <li><a href="diary.jsp" target=navTab rel="diary">工作周报管理</a></li> -->
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span
										class="home_icon">系统首页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div>
					<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">系统首页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="accountInfo">
							<div class="right">
								<p>使用问题请联系：mengfeiyang@youku.com</p>
								<p>支持的浏览器：IE，Chrome，Firefox，Safari</p>
							</div>
							<p><span>欢迎使用播放服务测试系统</span></p>
							<p style="color:blue">更新日期：2014.9.5</p>
						</div>
						<div class="pageFormContent" layoutH="80" style="margin-right:0px">
						<div class="pageFormContent">
							<h2>系统升级日志:</h2>
							<div class="divider"></div>
							<div class="unit">1、后台开发完成，支持Jenkins部署  2014.8.1</div>
							<div class="unit">2、完成用例类型、用例、节点、数据、请求参数、视频vid，Host地址数据的维护页面   2014.8.7</div>
							<div class="unit">3、完成用户权限验证  ， 并增加了拦截器   2014.8.8</div>
							<div class="unit">4、完成实验室、过程监控   2014.8.11</div>
							<div class="unit">5、完成各统计报表   2014.8.14</div>
							<div class="unit">6、解决部分浏览器js兼容性 ，但IE下仍有部分功能不能正常使用   2014.8.15</div>
							<div class="unit">7、过程监控页面添加自动刷新功能   2014.8.16</div>
							<div class="unit">8、新增日志管理工具   2014.8.21</div>
							<div class="unit">9、新增并发测试工具及并发测试监控、并发测试过程数据收集工具   2014.9.5</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	<div id="footer">youku.com | 播放服务测试</div>
	
    <script src="js/jquery.uploadify.js" type="text/javascript"></script>
    <script type="text/javascript" src="chart/highcharts.js"></script>
    <script type="text/javascript" src="chart/exporting.js"></script>
</body>
</html>