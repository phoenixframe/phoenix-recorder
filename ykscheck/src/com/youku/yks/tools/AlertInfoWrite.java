package com.youku.yks.tools;

import javax.servlet.jsp.JspWriter;

/**
 * 统一向前端回写操作结果信息
 * @author mengfeiyang
 *
 */
public class AlertInfoWrite {
	
	public static void alertInfo(JspWriter out,int r) throws Exception{
		if (r > 0) {
			out.print(GlobalParameter.successInfo);
		} else {
			out.print(GlobalParameter.failInfo);
		} 
	}
}
