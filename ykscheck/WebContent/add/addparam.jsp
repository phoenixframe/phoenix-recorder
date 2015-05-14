<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>
<%@ page import="com.youku.yks.tools.*"%>
<jsp:include page="../checkin.jsp" flush="true"/>
<%
	ParamDao paramDao = new ParamDaoImpl();
  InterfaceDao reqDataDao = new InterfaceDaoImpl();
  UserBean userBean = (UserBean)session.getAttribute("user");
  List<InterfaceBean> reqDataList = reqDataDao.getAllInterfaceByUser(userBean.getId());
%>

<div class="pageContent">
	<form method="post" action="./do/doparam.jsp?type=add" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		   <p>
				<label>当前用户：</label>
				<input type="hidden" value="<%=userBean.getId()%>" name="userId" size=25>
				<input type="text" value="<%=userBean.getName()%>" name="userName" size=25 readonly="true" >
			</p>
			<p>
				<label>选择所属接口：</label>
				  <select class="combox" name="interType" id="interType">
						<%
							for(InterfaceBean reqdataType : reqDataList){
						%>
						    <option value=<%=reqdataType.getId() %>><%=reqdataType.get_interface() %> </option>
						<%
						  }
						%>
					</select>
			</p>
			<p>
				<label>参数名称：</label>
				<input type="text" name="paramName" size=25 class="required">
				<span class="inputInfo">参数名称</span>
			</p>
			<p>
				<label>参数类型：</label>
				<input type="hidden" name="orgLookup.orgNum" value="${orgLookup.orgNum}"/>
				<input type="text" size=25 class="required" name="orgLookup.orgName" value="" suggestFields="orgNum,orgName" suggestUrl="files/paramtype.html" lookupGroup="orgLookup" />
				<span class="inputInfo">参数属性</span>
			</p>
			
		    <p>
				<label>参数值：</label>
				<input type="text" name="paramValue" size=25 class="required">
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