<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>
<%@ page import="com.youku.yks.tools.*"%>

<%
	CaseTypeDao caseTypeDao = new CaseTypeImpl();
	CaseDao caseDao = new CaseImpl();
	UserDao userDao = new UserDaoImpl();
	VidDao vidDao = new VidImpl();
	HostDao hostDao = new HostImpl();
	InterfaceDao reqData = new InterfaceDaoImpl();
	List<InterfaceBean> reqDataBeanList = null;
	List<VidBean> vidList = null;
	List<HostBean> hostList = null;
%>

<%
	String id = request.getParameter("id");
    UserBean userBean = (UserBean)session.getAttribute("user");
	List<CaseTypeBean> caseTypeList = caseTypeDao.getAllEnabledCaseTypeByUser(userBean.getId());
	CaseBean caseBean = caseDao.getUnitCase(Integer.parseInt(id));
  
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
<jsp:include page="../checkin.jsp" flush="true"/>
<div class="pageContent">
	<form method="post" action="do/docase.jsp?type=update" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
				<label>用例详细信息</label>
			</div>
			<div class="divider">divider</div>
			<p>
				<label>Id：</label>
				<input type="text" readonly="true" name="id" size=25 value=<%=caseBean.getId()%>>
				<span class="inputInfo">库中的id</span>
			</p>
			<p>
				<label>所属用户：</label>
				<input type="hidden" name="userId" size=25 value=<%=caseBean.getUserId()%>>
				<input type="text" readonly="true" name="userName" size=25 value=<%=userDao.getUser(caseBean.getUserId()).getName()%>>
				<span class="inputInfo">用户名称</span>
			</p>
			<p>
				<label>所属用例类型：</label>
				  <select class="combox" name="caseTypeSelect" id="caseTypeSelect">
						<option value="所有用例类型">所有用例类型</option>
						<%
							for(CaseTypeBean caseType : caseTypeList){
											if(caseType.getId() == caseBean.getTypeNameId()){
						%>						    
						    <option selected="selected" value=<%=caseType.getId()%>><%=caseType.getTypeName()%></option>
						    <%
						    	}else{
						    %>
								<option value=<%=caseType.getId()%>><%=caseType.getTypeName()%></option>
							<%
								}
											}
							%>
					</select>
			</p>
			
			<p>
				<label>VideoId：</label>
				  <select class="combox" name="vidSel" id="vidSel">
						<%
							for(VidBean vidBean : vidList){
											if(vidBean.getId() == caseBean.getVid()){
						%>						    
						       <option selected="selected" value=<%=vidBean.getId()%>><%=vidBean.getVid()%> -- <%=vidBean.getRemark()%></option>
						    <%
						    	}else{
						    %>
								<option value=<%=vidBean.getId()%>><%=vidBean.getVid()%> -- <%=vidBean.getRemark()%></option>
							<%
								}
											}
							%>
					</select>
					<span class="inputInfo">Vid参数</span>
			</p>
			
			<p>
				<label>HostName：</label>
				  <select class="combox" name="hostSel" id="hostSel">
						<%
							for(HostBean hostBean : hostList){
											if(hostBean.getId() == caseBean.getHostId()){
						%>						    
						    <option selected="selected" value=<%=hostBean.getId()%>><%=hostBean.getHostName()%> -- <%=hostBean.getRemark()%></option>
						    <%
						    	}else{
						    %>
								<option value=<%=hostBean.getId()%>><%=hostBean.getHostName()%> -- <%=hostBean.getRemark()%></option>
							<%
								}
											}
							%>
					</select>
					<span class="inputInfo">域名或IP</span>
			</p>
			
			<p>
				<label>接口数据：</label>
				  <select class="combox" name="interfaceSel" id="interfaceSel">
						<%
							for(InterfaceBean reqDataBean : reqDataBeanList){
											if(reqDataBean.getId() == caseBean.getHostId()){
						%>						    
						    <option selected="selected" value=<%=reqDataBean.getId() %>><%=reqDataBean.get_interface() %></option>
						    <%
							}else{
							%>
								<option value=<%=reqDataBean.getId() %>><%=reqDataBean.get_interface() %></option>
							<%
							}
						}
						    %>
					</select>
					<span class="inputInfo">URL中的接口</span>
			</p>
			
			<p>
				<label>用例名称：</label>
				<input type="text" class="required" name="caseName" size=25 value=<%=caseBean.getCaseNode() %>>
				<span class="inputInfo">用例名称</span>
			</p>
			
			<p>
				<label>是否可用：</label>
				<input type="hidden" name="orgLookup.orgName" value="${orgLookup.orgNum}"/>
				<input type="text" size=25 class="required" name="orgLookup.orgNum" value="<%=caseBean.getIsDisabled() %>" suggestFields="orgNum,orgName" suggestUrl="files/isDisabled.html" lookupGroup="orgLookup" />
				<span class="inputInfo">初始化状态</span>
			</p>
			
		    <p>
				<label>备注：</label>
				<input type="text" name="remark" size=25 value=<%=caseBean.getRemark() %>>
				<span class="inputInfo">功能介绍</span>
			</p>
			
			<p>
				<label>最后修改时间：</label>
                <span class="inputInfo"><%= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(caseBean.getCreateTime())%></span>
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