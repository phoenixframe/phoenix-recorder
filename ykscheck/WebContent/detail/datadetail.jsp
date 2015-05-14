<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>
<%@ page import="com.youku.yks.tools.*"%>
<%
  String id = request.getParameter("id"); 
  DataDao dataDao = new DataImpl();
  CaseDao caseDao = new CaseImpl();
  NodeDao nodeDao = new NodeImpl();
  
  UserBean userBean = (UserBean)session.getAttribute("user");
  DataBean dataBean = dataDao.getData(Integer.parseInt(id));
  NodeBean nodeBean = nodeDao.getUnitNode(dataBean.getParamNodeId());     
%>
<jsp:include page="../checkin.jsp" flush="true"/>
<div class="pageContent">
	<form method="post" action="./do/dodata.jsp?type=update" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
				<label>测试数据详细信息</label>
			</div>
			<div class="divider">divider</div>
			<p>
				<label>Id：</label>
				<input type="text" readonly="true" name="id" size=25 value=<%=dataBean.getId() %>>
			</p>
			<p>
				<label>所属用户：</label>
				<input type="hidden" name="userId" size=25 value=<%=userBean.getId() %>>
				<input type="text" readonly="true" name="userName" size=25 value=<%=userBean.getName() %>>
			</p>
			<p>
				<label>所属用例：</label>
				<input type="text" readonly="true" name="casename" size=25 value=<%=caseDao.getUnitCase(nodeBean.getCaseNodeId()).getCaseNode() %>>
			</p>
			
			<p> 
			    <label>所属用例节点：</label>
				<input id="inputOrg1" name="org1.orgNum" value="<%=nodeBean.getId() %>" type="hidden"/>
				<input name="org1.orgName" readonly="true" class="required" type="text" postField="keyword" size=25 value=<%=nodeBean.getParamNode() %>>
			    <a class="btnLook" href="./lookback/backnode.jsp" lookupGroup="org1">查找带回</a>	
			</p>
			
			<p>
				<label>请求类型：</label>
				<input type="hidden" name="orgLookup.orgNum" value="${orgLookup.orgNum}"/>
				<input type="text" size=25 class="required" name="orgLookup.orgName" value="<%=dataBean.getRequestType() %>" suggestFields="orgNum,orgName" suggestUrl="./files/requestype.html" lookupGroup="orgLookup" />
				<span class="inputInfo">初始化状态</span>
			</p>
			
			<p>
				<label>JSON路径：</label>
				<input type="text" class="required" name="jsonPath" size=25 value=<%=dataBean.getJsonPath() %>>
				<span class="inputInfo">获取实际值</span>
			</p>
			
		   <p>
				<label>期望值：</label>
				<input type="text" name="expectValue" size=25 value=<%=dataBean.getExpect() %>>
				<span class="inputInfo">与结果比较</span>
			</p>
			
		    <p>
				<label>备注：</label>
				<input type="text" name="remark" size=25 value=<%=dataBean.getRemark() %>>
				<span class="inputInfo">功能介绍</span>
			</p>

		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存修改</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">关闭窗口</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>