<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>
<%@ page import="com.youku.yks.tools.*"%>
<jsp:include page="../checkin.jsp" flush="true"/>

<%
	VidDao vidDao = new VidImpl();
	HostDao hostDao = new HostImpl();
	CaseTypeDao caseTypeDao = new CaseTypeImpl();
	InterfaceDao reqData = new InterfaceDaoImpl();
	CaseBean caseBean = new CaseBean();
	List<InterfaceBean> reqDataBeanList = null;
	List<VidBean> vidList = null;
	List<HostBean> hostList = null;

%>

<%
  UserBean userBean = (UserBean)session.getAttribute("user");
  String caseTypeId = request.getParameter("typeid").equals("")?"-1":request.getParameter("typeid");
  
  List<CaseTypeBean> caseTypeList = caseTypeDao.getAllEnabledCaseTypeByUser(userBean.getId()); 
  if(userBean.getLevel() == 0){
	  reqDataBeanList = reqData.getAllInterface();
	  vidList = vidDao.getAllVid();
	  hostList = hostDao.getAllHost();
  } else {
	  vidList = vidDao.getAllVidByUser(userBean.getId());
	  hostList = hostDao.getAllHostByUser(userBean.getId());
	  reqDataBeanList = reqData.getAllInterfaceByUser(userBean.getId());
  }
%>

<div class="pageContent">
	<form method="post" action="./do/docase.jsp?type=add" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>选择用例类型名称：</label>
				  <select class="combox" name="caseTypeSelect" id="caseTypeSelect">
						<option value="所有用例类型">所有用例类型</option>
						<%
						for(CaseTypeBean caseType : caseTypeList){
							if(caseType.getId() == Integer.parseInt(caseTypeId)){
							%>
							<option selected="selected" value=<%=caseType.getId() %>><%=caseType.getTypeName() %></option>
							<%
							}else {
						 %>
						    <option value=<%=caseType.getId() %>><%=caseType.getTypeName() %></option>
						<%
						  }
						}
						%>
					</select>
			</p>
			
			<p>
				<label>VideoId：</label>
				  <select class="combox" name="vidSel" id="vidSel">
				  <option value="-1">请选择vid</option>
						<%
							for(VidBean vidBean : vidList){		
						%>						    
	
							<option value=<%=vidBean.getId()%>><%=vidBean.getVid()%> -- <%=vidBean.getRemark() %></option>
						<%
							}
						%>
				  </select>
				  <span class="inputInfo">Vid参数</span>
			</p>
			
			<p>
				<label>HostName：</label>
				  <select class="combox" name="hostSel" id="hostSel">
				  <option value="-1">请选择Host</option>
						<%
							for(HostBean hostBean : hostList){
						%>						    
							<option value=<%=hostBean.getId()%>><%=hostBean.getHostName()%> -- <%=hostBean.getRemark()%></option>
						<%
							}
						%>
					</select>
					<span class="inputInfo">域名或IP</span>
			</p>
			
			<p>
				<label>接口数据：</label>
				  <select class="combox" name="interfaceSel" id="interfaceSel">
				  <option value="-1">请选择interface</option>
						<%
							for(InterfaceBean reqDataBean : reqDataBeanList){
						%>						    
							<option value=<%=reqDataBean.getId() %>><%=reqDataBean.get_interface() %></option>
						<%
							}
						%>
					</select>
					<span class="inputInfo">URL中的接口</span>
			</p>
			
			<p>
				<label>用例名称：</label>
				<input type="text" class="required" name="caseName" size=25>
				<span class="inputInfo">用例名称</span>
			</p>
			<p>
				<label>所属用户：</label>
				<input type="hidden" name="userId" size=25 value=<%=userBean.getId() %>>
				<input type="text" name="userName" size=25 readonly="true" value=<%=userBean.getName() %>>
				<span class="inputInfo">用户名称</span>
			</p>
			<p>
				<label>是否可用：</label>
				<input type="hidden" name="orgLookup.orgNum" value="${orgLookup.orgNum}"/>
				<input type="text" size=25 class="required" name="orgLookup.orgName" value="" suggestFields="orgNum,orgName" suggestUrl="files/isDisabled.html" lookupGroup="orgLookup" />
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