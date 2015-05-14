package com.youku.yks.tools;

import com.youku.yks.bean.DBConfigBean;
import com.youku.yks.servlet.Servlet;

public class GlobalParameter {	
	
	public static DBConfigBean dbconfig = new DBConfigBean(Servlet.getValues().get("driver"),Servlet.getValues().get("url"),Servlet.getValues().get("userName"),Servlet.getValues().get("password"),(Integer.parseInt(Servlet.getValues().get("maxsize"))));
	public static final String DB_url=Servlet.getValues().get("url");
	public static final String DB_UserName=Servlet.getValues().get("userName");
	public static final String DB_Password=Servlet.getValues().get("password");;
	
	public static final String successInfo = "{\"statusCode\":\"200\",\"message\":\"提交数据成功!\",\"navTabId\":\"\",\"callbackType\":\"\",\"forwardUrl\":\"\"}";
	public static final String failInfo = "{\"statusCode\":\"300\",\"message\":\"提交失败，数据有误!\",\"navTabId\":\"\",\"callbackType\":\"\",\"forwardUrl\":\"\"}";
	
	public static void main(String[] args) {
	  new GlobalParameter();
	  
	}
	
}
