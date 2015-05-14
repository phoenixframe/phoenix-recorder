<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>
<%@ page import="com.youku.yks.tools.*"%>
<jsp:include page="check.jsp" flush="true"/>

<%
	UserDao userDao = new UserDaoImpl();
    InterfaceDao reqDataDao = new InterfaceDaoImpl();
    PagingCommon pagingCommon = new PagingCommon();
    ParamDataPaging dataPaging = new ParamDataPaging();
	List<ParamBean> paramList = new ArrayList<ParamBean>();
	UserBean userBean = (UserBean)session.getAttribute("user");
	List<InterfaceBean> reqdataList = reqDataDao.getAllInterfaceByUser(userBean.getId());
%>

<%
	request.setCharacterEncoding("UTF-8"); //设置编码集
	String strPageNum = request.getParameter("pageNum");
	String numPerPage = request.getParameter("numPerPage");	
	String interType = request.getParameter("interType");
	if(interType!=null && !interType.equals("所有类型")){
		dataPaging.setRuleValue(interType);		
	}

	dataPaging.setUserBean(userBean);
	pagingCommon.setNumPerPage(numPerPage);
	pagingCommon.setStrPageNum(strPageNum);
	pagingCommon.setTotalRow(dataPaging.getParamDataTotalRow());
	
	PagingBean pagingBean = pagingCommon.getPagingData();
	
	int totalRow = pagingBean.getTotalRow();
	int totalPage = pagingBean.getTotalPage();
	int currentPage = pagingBean.getCurrentPage();
	int initPerPageRows = pagingBean.getNumPerPage();
	paramList = dataPaging.getParamDataQuery(currentPage,initPerPageRows); //分页查询
%>

<form id="pagerForm" method="post" action="param.jsp">
	<input type="hidden" name="interType" value="<%=interType == null?"所有类型":interType %>" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value=<%=initPerPageRows%> />
	<input type="hidden" name="orderField" value="asc" />
</form>	
	
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="param.jsp" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<label>接口类型筛选：</label>
					<select class="combox" name="interType" id="interType">
						<option value="所有类型">所有类型</option>
						<%
							for(InterfaceBean requestType : reqdataList){
											if((requestType.getId()+"").equals(interType)){
						%>
						        
						        <option selected="selected" value=<%=requestType.getId() %>><%=requestType.get_interface() %></option>
						<%
						    }else{
						    	%>
						    	<option value=<%=requestType.getId() %>><%=requestType.get_interface() %></option>
						    	<%
						    }
						}
						%>
					</select>
				</td>	
				<td>
				   <div class="buttonActive"><div class="buttonContent"><button type="submit">提交数据</button></div></div>
				</td>			
			</tr>
		</table>
		<div class="subBar">
			<ul>
			    <li></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
		    <li><a title="添加一个新的参数" class="add" href="add/addparam.jsp" target="dialog" rel="param_page"><span>添加</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="#" target="dwzExport" targetType="navTab" title="确实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr align="center">
			    <th width="8%">Id</th>
			    <th width="10%">所属用户</th>
			    <th width="20%">所属接口</th>
			    <th width="10%">参数名称</th>
			    <th width="17%">参数类型</th>
			    <th width="22%">参数值</th>
			    <th width="10%">操作</th>
			</tr>
		</thead>
		<tbody>
		<%
			for (ParamBean paramBean : paramList) {
		%>
		<tr target="sid_param" rel="1"  align="center">
			<td><%=paramBean.getId()%></td>
			<td><%=userDao.getUser(reqDataDao.getInterface(paramBean.getInterfaceId()).getUserId()).getName() %></td>
			<td><%=reqDataDao.getInterface(paramBean.getInterfaceId()).get_interface() %></td>
			<td><%=paramBean.getParamName() %></td>
			<td><%=paramBean.getParamType() %></td>
			<td><%=paramBean.getParamValue() %></td>
			<td>
				<a title="要删除 “<%=paramBean.getParamName() %>” 参数吗？可能会导致请求的地址不可用且不可恢复！" target="ajaxTodo" href="do/doparam.jsp?type=delete&id=<%=paramBean.getId()%>" class="btnDel">删除</a>
				<a target="dialog" mask="true" title="编辑" href="detail/paramdetail.jsp?id=<%=paramBean.getId()%>" class="btnEdit">编辑</a>
			</td>
		</tr>
		<%
			}
		%>


		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value=>当前每页:<%=initPerPageRows%></option>
				<option value="20">20</option>
				<option value="30">30</option>
				<option value="50">50</option>
				<option value="100">100</option>
			</select>
			<span>条，共<%=totalRow%>条</span>
		</div>
		
		<div class="pagination" targetType="navTab" totalCount=<%=totalRow%> numPerPage=<%=initPerPageRows%> pageNumShown=10 currentPage=<%=currentPage%>></div>
	</div>
</div>