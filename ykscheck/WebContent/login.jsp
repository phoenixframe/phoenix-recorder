<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%@ page import="com.youku.yks.paging.*"%>
<%@ page import="com.youku.yks.tools.*"%>

<%!UserDao userDao = new UserDaoImpl();%>

<%
	String uname = request.getParameter("username");
	String pass = request.getParameter("password");
	String type = request.getParameter("type");

	if (type.equals("login")) {
		if (uname == null || pass == null) {
			response.sendRedirect("index.jsp");
		} else {
			try {
				UserBean user= userDao.getUser(uname);
				if (user != null
						&& user.getPassword()
								.equals(pass)
						&& user.getName()
								.equals(uname)) {
					session.setAttribute("user", user);
				}
			} catch (Exception e) {
				
			}
			response.sendRedirect("index.jsp");
		}
	} else if (type.equals("loginout")) {
		session.invalidate();
		response.sendRedirect("index.jsp");		
	}
%>