<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>
<%@ page import="com.youku.yks.tools.*"%>
<jsp:include page="check.jsp" flush="true"/>

<%
   CaseTypeDao caseTypeDao = new CaseTypeImpl();
   CodeDao codeDao = new CodeDaoImpl();
   CaseDao caseDao = new CaseImpl();
   NodeDao nodeDao = new NodeImpl();
   UserDao userDao = new UserDaoImpl(); 
   UserBean userBean = (UserBean)session.getAttribute("user");
   List<CaseTypeBean> caseTypeList = new ArrayList<CaseTypeBean>();
   HashMap<Integer,String> codeMap = codeDao.getAllCode();
%>

<%
   String caseTypeId = request.getParameter("caseTypeId");
   if(caseTypeId == null){
	   if(userBean.getLevel() == 0){
	       caseTypeList = caseTypeDao.getAllCaseType();
	   } else {
		   caseTypeList = caseTypeDao.getAllCaseTypeByUser(userBean.getId());
	   }
   } else {
	   caseTypeList.add(caseTypeDao.getUnitCaseType(Integer.parseInt(caseTypeId)));
   }
%>

<h2 class="contentTitle">用例结构视图</h2>
<div style="float:center; display:block; margin:10px; overflow:auto; width:96%; height:80%; border:solid 1px #CCC; line-height:21px; background:#FFF;">
<ul class="tree">
   <%for(CaseTypeBean caseType : caseTypeList){%>
	<li><a><%=caseType.getTypeName() %> [ <%=codeMap.get(caseType.getIsDisabled()) %>]</a>
	    <ul><%
	         List<CaseBean> caseList = caseDao.getAllCaseByTypeNameId(caseType.getId(), caseType.getUserid());
	         for(CaseBean caseBean : caseList){ 	         
	         %>
	        <li><a><%=caseBean.getCaseNode() %> [ <%=codeMap.get(caseBean.getIsDisabled()) %> ]</a>
	            <ul><%
	                  List<NodeBean> nodeList = nodeDao.getAllNodeByCaseNodeId(caseBean.getId(), caseBean.getUserId()); 
	                  for(NodeBean nodeBean : nodeList){
	                %>
	                 <li><a><%=nodeBean.getParamNode() %> [ <%=codeMap.get(nodeBean.getIsDisabled()) %> ]</a></li>	
	                 <%} %>            
	            </ul>	        
	        </li>
	        <%} %>
	    </ul>	
	</li>
	<%} %>	
</ul>
</div>