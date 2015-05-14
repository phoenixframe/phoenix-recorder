<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../checkin.jsp" flush="true"/>
<div class="pageContent">
	<form method="post" action="./do/docode.jsp?type=add" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>状态码：</label>
				<input name="code" type="text" size="30" value="" class="required"/>
			</p>
			
			<p>
				<label>说明：</label>
				<input name="codeValue" type="text" size="30" value="" class="required"/>
			</p>

		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" >保存数据</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">关闭窗口</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>