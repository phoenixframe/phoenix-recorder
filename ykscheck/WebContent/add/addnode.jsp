<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<% 
    UserBean userBean = (UserBean)session.getAttribute("user"); 
    String nodeId = request.getParameter("org1.id").equals("")?"":request.getParameter("org1.id");
    String nodeName = request.getParameter("org1.orgName").equals("")?"":request.getParameter("org1.orgName");

%>

<jsp:include page="../checkin.jsp" flush="true"/>
<div class="pageContent">
	<form method="post" action="./do/donode.jsp?type=add" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		    <p>
				<label>用户名称：</label>
				<input type="hidden" name="userId" size=25 value=<%=userBean.getId() %>>
				<input type="text" readonly="true" name="userName" size=25 value=<%=userBean.getName() %>>
				<span class="inputInfo">所属用户</span>
			</p>
			<p>
				<label>选择所属的用例：</label>
				<input id="inputOrg1" name="org1.id" value="<%=nodeId %>" type="hidden"/>
				<input name="org1.orgName" value="<%=nodeName %>" type="text" postField="keyword" size=25>
				<a class="btnLook" href="./lookback/backcase.jsp" lookupGroup="org1">查找带回</a>	
			</p>
			<p>
				<label>节点名称：</label>
				<input type="text" class="required" name="caseNodeName" size=25>
				<span class="inputInfo">所属用例节点</span>
			</p>
			
			<p>
				<label>是否可用：</label>
				<input type="hidden" name="orgLookup.orgNum" value="${orgLookup.orgNum}"/>
				<input type="text" size=25 class="required" name="orgLookup.orgName" value="" suggestFields="orgNum,orgName" suggestUrl="./files/isDisabled.html" lookupGroup="orgLookup" />
				<span class="inputInfo">初始化状态</span>
			</p>
			
		    <p>
				<label>备注：</label>
				<input type="text" name="remark" size=25>
				<span class="inputInfo">功能介绍</span>
			</p>

		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>