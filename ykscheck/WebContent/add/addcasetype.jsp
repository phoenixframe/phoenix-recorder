<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.youku.yks.bean.UserBean;" %>
<jsp:include page="../checkin.jsp" flush="true"/>
<%UserBean userBean = (UserBean)session.getAttribute("user"); %>
<div class="pageContent">
	<form method="post" action="./do/docasetype.jsp?type=add" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>当前用户名：</label>
				<input name="userId" type="hidden" size="30" value="<%=userBean.getId() %>" readonly="true"/>
				<input name="userName" type="text" size="30" value="<%=userBean.getName() %>" readonly="true"/>
			</p>
			<p>
				<label>输入用例类型名称：</label>
				<input name="caseTypeName" type="text" size="30" value="" class="required" alt="请输入用例类型名称"/>
			</p>
			<p>
				<label>是否可用：</label>
				<input type="hidden" name="orgLookup.orgNum" value="${orgLookup.orgNum}"/>
				<input type="text" size="30" class="required" name="orgLookup.orgName" value="" suggestFields="orgNum,orgName" suggestUrl="files/isDisabled.html" lookupGroup="orgLookup" />
				<span class="inputInfo">初始状态</span>
			</p>

		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" onclick="$.pdialog.closeCurrent();">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>