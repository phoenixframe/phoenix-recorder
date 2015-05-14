<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.youku.yks.bean.*" %>
<%UserBean userBean = (UserBean)session.getAttribute("user"); %>

<jsp:include page="../checkin.jsp" flush="true"/>
<div class="pageContent">
	<form method="post" action="./do/dohost.jsp?type=add" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>当前用户：</label>
				<input name="userId" type="hidden" size="30" value=<%=userBean.getId() %>>
				<input name="userName" type="text" size="30" value="<%=userBean.getName() %>" readonly="true">
			</p>
			<p>
				<label>请输入Host值：</label>
				<input name="hostName" type="text" size="30" value="" class="required"/>
			</p>
			
			<p>
				<label>Host备注：</label>
				<input name="hostType" type="text" size="30" value="" class="required"/>
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