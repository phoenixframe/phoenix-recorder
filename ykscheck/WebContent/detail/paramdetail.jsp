<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>
<%@ page import="com.youku.yks.tools.*"%>
<%
	String id = request.getParameter("id");
  ParamDao paramDao = new ParamDaoImpl();
  InterfaceDao reqDataDao = new InterfaceDaoImpl();
  UserDao userDao = new UserDaoImpl();
  ParamBean paramBean = paramDao.getParam(Integer.parseInt(id));
  int userId = reqDataDao.getInterface(paramBean.getInterfaceId()).getUserId();
  List<InterfaceBean> reqDataList = reqDataDao.getAllInterfaceByUser(userId);
%>
<jsp:include page="../checkin.jsp" flush="true"/>
<div class="pageContent">
	<form method="post" action="do/doparam.jsp?type=update" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
				<label>参数详细信息</label>
			</div>
			<div class="divider">divider</div>
			<p>
				<label>Id：</label>
				<input type="text" readonly="true" name="id" size=25 value=<%=paramBean.getId()%>>
				<span class="inputInfo">库中的id</span>
			</p>
			<p>
				<label>所属用户：</label>
				<input type="text" readonly="true" name="userName" size=25 value=<%=userDao.getUser(userId).getName()%>>
				<span class="inputInfo">用户名称</span>
			</p>
			<p>
				<label>所属接口：</label>
				  <select class="combox" name="interType" id="interType">
						<%
							for(InterfaceBean reqDataType : reqDataList){
											if(reqDataType.getId() == paramBean.getInterfaceId()){
						%>						    
						    <option selected="selected" value=<%=reqDataType.getId() %>><%=reqDataType.get_interface() %></option>
						    <%
							}else{
							%>
								<option value=<%=reqDataType.getId() %>><%=reqDataType.get_interface() %></option>
							<%
							}
						}
						    %>
					</select>
			</p>
			
			<p>
				<label>参数名称：</label>
				<input type="text" name="paramName" value="<%=paramBean.getParamName() %>" size=25 class="required">
				<span class="inputInfo">参数名称</span>
			</p>
			<p>
				<label>参数类型：</label>
				<input type="hidden" name="orgLookup.orgNum" value="${orgLookup.orgNum}"/>
				<input type="text" size=25 class="required" name="orgLookup.orgName" value="<%=paramBean.getParamType() %>" suggestFields="orgNum,orgName" suggestUrl="files/paramtype.html" lookupGroup="orgLookup" />
				<span class="inputInfo">参数属性</span>
			</p>
			
		    <p>
				<label>参数值：</label>
				<input type="text" name="paramValue" value="<%=paramBean.getParamValue() %>" size=25 class="required">
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