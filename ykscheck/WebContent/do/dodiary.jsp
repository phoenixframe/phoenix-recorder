<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.tools.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>

<%
	UserBean userBean = (UserBean)session.getAttribute("user");
	DiaryBean diaryBean = new DiaryBean();
	DiaryDao diaryDao = new DiaryDaoImpl();
%>

<%
  String content = request.getParameter("description");
  if(content != null){
	  content = new String(content.getBytes("ISO-8859-1"),"UTF-8");
  }
  String id = request.getParameter("id");
  String type = request.getParameter("type");
  if(type.equals("add")){
	  diaryBean.setContent(content);
	  diaryBean.setUserId(userBean.getId());
	  AlertInfoWrite.alertInfo(out, diaryDao.addDiary(diaryBean));
  } else if (type.equals("dele")){
	  AlertInfoWrite.alertInfo(out,diaryDao.deleDiary(Integer.parseInt(id)));
  } else if (type.equals("update")){
	  diaryBean.setContent(content);
	  diaryBean.setId(Integer.parseInt(id));
	  diaryBean.setUserId(userBean.getId());
	  AlertInfoWrite.alertInfo(out, diaryDao.updateDiary(diaryBean));
  }
  
%>