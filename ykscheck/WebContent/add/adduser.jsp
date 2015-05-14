<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../checkin.jsp" flush="true"/>
<div class="pageContent">
	<form method="post" action="./do/douser.jsp?type=add" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>用户名：</label>
				<input name="userName" type="text" size="30" value="" class="required"/>
			</p>
			
			<p>
				<label>密码：</label>
				<input name="password" type="text" size="30" value="" class="required"/>
			</p>
			<p>
				<label>Level设置：</label>
				<input type="hidden" name="orgLookup.orgNum" value="${orgLookup.orgNum}"/>
				<input type="text" class="required" name="orgLookup.orgName" value="" suggestFields="orgNum,orgName" suggestUrl="files/level.html" lookupGroup="orgLookup" />
				<span class="inputInfo">初始化状态设置</span>
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