<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>
<%@ page import="com.youku.yks.tools.*"%>
<%
	VidDao vidDao = new VidImpl();
  HostDao hostDao = new HostImpl();
  InterfaceDao reqData = new InterfaceDaoImpl();
  List<VidBean> vidList = null;
  List<HostBean> hostList = null;
  List<InterfaceBean> reqDataBeanList = null;
  
  UserBean userBean = (UserBean)session.getAttribute("user");
%>
<% 
  String caseNodeId = request.getParameter("caseNodeId").equals("")?"":request.getParameter("caseNodeId");
  String caseNodeName = request.getParameter("caseNodeName").equals("")?"":request.getParameter("caseNodeName");
  if(userBean.getLevel() == 0){
     reqDataBeanList = reqData.getAllInterface();
     vidList = vidDao.getAllVid();
     hostList = hostDao.getAllHost();
  } else {
	  reqDataBeanList = reqData.getAllInterfaceByUser(userBean.getId());
	  vidList = vidDao.getAllVidByUser(userBean.getId());
	  hostList = hostDao.getAllHostByUser(userBean.getId());
  }
%>
<jsp:include page="../checkin.jsp" flush="true"/>
<div class="pageContent">
	<form method="post" action="./do/dodata.jsp?type=add" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
				<label>添加测试数据</label>
			</div>
			<div class="divider">divider</div>
			
			<p> 
			    <label>所属用户：</label>
				<input name="userId" value="<%=userBean.getId()%>" type="hidden"/>
				<input name="userName" readonly="true" type="text" size=25 value=<%=userBean.getName()%>>	
			</p>
			
			<p> 
			    <label>所属节点：</label>
				<input id="inputOrg1" name="org1.orgNum" value="<%=caseNodeId %>" type="hidden"/>
				<input name="org1.orgName" readonly="true" class="required" type="text" value="<%=caseNodeName %>" postField="keyword" size=25>
			    <a class="btnLook" href="./lookback/backnode.jsp" lookupGroup="org1">查找带回</a>	
			</p>			
			
			<p>
				<label>请求类型：</label>
				<input type="hidden" name="orgLookup.orgName" value="${orgLookup.orgNum}"/>
				<input type="text" size=25 class="required" name="orgLookup.orgName" suggestFields="orgNum,orgName" suggestUrl="./files/requestype.html" lookupGroup="orgLookup" />
				<span class="inputInfo">初始化状态</span>
			</p>
			
		    <p>
				<label>JSON路径：</label>
				<input type="text" name="jsonPath" class="required" value="" size=25>
				<span class="inputInfo">取实际值</span>
			</p>
			
		    <p>
				<label>期望值：</label>
				<input type="text" name="expectValue" value="" size=25>
				<span class="inputInfo">与结果比较</span>
			</p>
			
		    <p>
				<label>备注：</label>
				<input type="text" name="remark" size=25>
				<span class="inputInfo">功能介绍</span>
			</p>

		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存数据</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">关闭窗口</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>