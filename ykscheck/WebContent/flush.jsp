<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.youku.yks.tools.LocalInfo" %>

<script type="text/javascript">
		$(function() {
			showExecuType();
			addStrategy();
			reduceStrategy();
			$("input[name=exetype]").click(function() {
				showExecuType();
			});
			$("input[name=addStrategy]").click(function() {
				addStrategy();
			});
			$("input[name=reduceStrategy]").click(function() {
				reduceStrategy();
			});
		});
		function reduceStrategy(){
			switch ($("input[name=reduceStrategy]:checked").attr("value")) {
			case "reduceAll":
				//$("#reduceVuser").removeAttr("class");
				$("#isReduceStrategy").hide();					
				break;
			case "reduceByUser":
				//$("#reduceVuser").attr("class","required");
				$("#isReduceStrategy").show();
				break;
			default:
				break;
			}
		}
		function addStrategy(){
			switch ($("input[name=addStrategy]:checked").attr("value")) {
			case "addAll":
				$("#isAddStrategy").hide();	
				break;
			case "userSet":
				$("#isAddStrategy").show();
				break;
			default:
				break;
			}
		}
		function showExecuType() {
			switch ($("input[name=exetype]:checked").attr("value")) {
			case "byRunTime":
				$("#isRunTime").show();
				$("#isIteration").hide();
		
				break;
			case "byIteration":
				$("#isIteration").show();
				$("#isRunTime").hide();
				break;
			default:
				break;
			}
		}
</script>

<h2 class="contentTitle">并发测试管理</h2>
<div class="pageContent">
	
	<form method="post" action="do/doflush.jsp" class="pageForm required-validate" onsubmit="return validateCallback(this)">
		<div class="pageFormContent nowrap" layoutH="97">

			<dl>
				<dt>为本次场景命名：</dt>
				<dd>
					<input type="text" size="70" name="remark" maxlength="255"/>
					<span class="info">本次场景名</span>
				</dd>
			</dl>
			<dl>
				<dt>被测地址：</dt>
				<dd>
					<input type="text" size="70" class="required url" name="url" maxlength="200"/>
					<span class="info">被测试的URL</span>
				</dd>
			</dl>
			<dl>
				<dt>并发数：</dt>
				<dd>
					<input size="70" type="text" class="required" name="runVusers" min="1" max=<%=LocalInfo.getMaxThread() %>>
					<span class="info">范围:1~<%=LocalInfo.getMaxThread() %></span>
				</dd>
			</dl>
			
			<dl>
				<dt>执行方式：</dt>
				<dd>
					<input type="radio" checked="checked" value="byIteration" name="exetype"/>按迭代次数 <input type="radio" value="byRunTime" name="exetype"/>按运行时间
				</dd>
			</dl>
			<div id="isIteration" style="display: none;">
			<dl>
				<dt>单Vuser迭代次数：</dt>
				<dd>
					<input size="70" type="text" id="iterationSet" name="iterationSet" min="1"/>
					<span class="info">输入数字</span>
				</dd>
			</dl>
			</div>
			<div id="isRunTime" style="display: none;">
			<dl>
				<dt>运行时间：</dt>
				<dd>
					<input size="70" type="text" name="runTimeSet" min="1"/>
					<span class="info">单位：秒</span>
				</dd>
			</dl>
			</div>
			
			<dl>
			<dt>迭代时间间隔：</dt>
				<dd>
					<input size="70" type="text" name="alternation" min="0"/>
					<span class="info">ms,1000ms=1s</span>
				</dd>
			</dl>
			
			<dl>
			    <dt>检查点设置：</dt>
				<dd>
					<input size="70" type="text" class="required" name="checkpoint"/>
					<span class="info">Json节点路径</span>
				</dd>
			</dl>
		    <dl>
			    <dt>设置期望值：</dt>
				<dd>
					<input size="70" value="" type="text" name="expectValue"/>
					<span class="info">期望值</span>
				</dd>
			</dl>
			<dl>
			    <dt>请求超时时间：</dt>
				<dd>
					<input size="70" type="text" name="maxTimeout" min="0"/>
					<span class="info">单位：ms</span>
				</dd>
			</dl>
			
<!-- 			<dl>
				<dt>递增策略设置：</dt>
				<dd>
					<input type="radio" checked="checked" value="addAll" name="addStrategy"/>增加全部<input type="radio" value="userSet" name="addStrategy"/>手动设置
				</dd>
			</dl>
			<div id="isAddStrategy" style="display: none;">
			<dl>
				<dt>间隔时间：</dt>
				<dd>
					<input size="70" type="text" name="addTime" min="1"/>
					<span class="info">单位：秒</span>
				</dd>
			</dl>
			<dl>
			    <dt>增加Vuser：</dt>
			    <dd>
				    <input size="70" type="text" name="addVuser" min="1"/>
				    <span class="info">每间隔时间增加Vuser</span>
			    </dd>
			</dl>
			</div>
			
			<dl>
				<dt>递减策略设置：</dt>
				<dd>
					<input type="radio" checked="checked" value="reduceAll" name="reduceStrategy"/>退出全部<input type="radio" value="reduceByUser" name="reduceStrategy"/>手动设置
				</dd>
			</dl>
			<div id="isReduceStrategy" style="display: none;">
			<dl>
				<dt>间隔时间：</dt>
				<dd>
					<input size="70" type="text" name="reduceTime" min="1"/>
					<span class="info">单位：秒</span>
				</dd>
			</dl>
			<dl>
			    <dt>退出Vuser：</dt>
			    <dd>
				    <input size="70" type="text" id="reduceVuser" name="reduceVuser" min="1"/>
				    <span class="info">每间隔时间递减Vuser</span>
			    </dd>
			</dl>
			</div> -->
			
			<div class="divider"></div>
			<ul>
			  <li>本工具目前仅能收集：</li><br>
			  <li>1、时间统计，平均响应时间，最大平均响应时间，每秒吞吐量，成功率，TPS，最大TPS</li><br>
			  <li>2、总成功数，总失败数，失败率，总异常数</li><br>
			</ul>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>				
				<li><div class="buttonActive"><a target="navTab" rel="flushview" href="flushview.jsp" class="Info"><span>查看进展</span></a></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>
