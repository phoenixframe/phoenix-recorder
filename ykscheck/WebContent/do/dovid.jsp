<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.youku.yks.bean.*" %>
<%@ page import="com.youku.yks.dao.*" %>
<%@ page import="com.youku.yks.tools.*" %>
<%@ page import="com.youku.yks.dao.impl.*" %>
<%
    VidDao vidDao = new VidImpl();
    VidBean vidBean = new VidBean();
%>

<%
	String requestType = request.getParameter("type");
    String id = request.getParameter("id");
    String vidName = request.getParameter("vidName");
    String vidValue = request.getParameter("vidValue");
    String vidType = request.getParameter("vidType");
    String userId = request.getParameter("userId");
    
	if (requestType.equals("add")) {
		vidBean.setUserId(Integer.parseInt(userId));
		vidBean.setRemark(vidType);
		vidBean.setVid(vidValue);
		vidBean.setVidName(vidName);
		int r = vidDao.addVid(vidBean);
		AlertInfoWrite.alertInfo(out, r);
	} else if(requestType.equals("delete")){	
		AlertInfoWrite.alertInfo(out, vidDao.deleVidById(Integer.parseInt(id)));
	} else if(requestType.equals("update")){
		vidBean.setId(Integer.parseInt(id));
		vidBean.setVid(vidValue);
		vidBean.setVidName(vidName);
		vidBean.setRemark(vidType);
		int r = vidDao.updateVid(vidBean);
		AlertInfoWrite.alertInfo(out, r);
	}
%>
