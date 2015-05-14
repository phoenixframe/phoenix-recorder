<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.tools.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%
    UserDao userDao = new UserDaoImpl();
    UserBean userBean = new UserBean();
%>

<%
	String requestType = request.getParameter("type");
    String id = request.getParameter("id");
    String userName = request.getParameter("userName");
    String password = request.getParameter("password");
    String level = request.getParameter("orgLookup.orgNum");
    String levelName = request.getParameter("orgLookup.orgName");
    
	if (requestType.equals("add")) {	
        userBean.setName(userName);
        userBean.setPassword(password);
        userBean.setLevel(Integer.parseInt(level));
        userBean.setRemark(levelName);
		int r = userDao.addUser(userBean);
		AlertInfoWrite.alertInfo(out, r);
	} else if(requestType.equals("delete")){	
		AlertInfoWrite.alertInfo(out, userDao.delUser(Integer.parseInt(id)));
	} else if(requestType.equals("update")){
		userBean.setId(Integer.parseInt(id));
        userBean.setName(userName);
        userBean.setPassword(password);
        userBean.setLevel(Integer.parseInt(level));
        userBean.setRemark(levelName);
		int r = userDao.updateUser(userBean);
		AlertInfoWrite.alertInfo(out, r);
	}
%>
