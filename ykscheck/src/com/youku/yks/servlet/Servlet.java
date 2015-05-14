package com.youku.yks.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.youku.yks.tools.XmlPaser;

/**
 * 对工程初始化操作
 * Servlet implementation class Servlet
 * @author mengfeiyang
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static HashMap<String,String> values = new HashMap<String,String>();
	
	public static HashMap<String, String> getValues() {
		return values;
	}

	public static void setValues(HashMap<String, String> values) {
		Servlet.values = values;
	}

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String projPath = config.getServletContext().getRealPath("/");
		setValues(XmlPaser.getValues(projPath+"/WEB-INF/db.xml"));
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
