package com.youku.yks.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
/**
 * 向另一个WEB工程地址模拟http请求，并接收返回的数据<br>
 * 返回的数据的格式为xml，需要解析需要的字符<br>
 * <em>编写日期：2013年12月20日 16:27</em>
 * @author mengfeiyang
 * @since JDK 1.7 及以上
 * @since Tomcat 3.0
 * @since httpunit 1.7
 *
 */

public class GetXml {
	/**
	 * 向另一个WEB工程地址模拟http请求，并接收返回的数据<br>
	 * 返回的数据的格式为xml，需要解析需要的字符<br>
	 * 设置参数时，参数名与参数值的分割符为“=>”
	 * <em>编写日期：2013年12月20日 16:27</em>
	 * @param url
	 * @param parameter
	 * @author mengfeiyang
	 * @since JDK 1.7 及以上
	 * @since Tomcat 3.0
	 *
	 */
	public static String getResponseByPost(String url, List<String> parameter,String paramType) throws Exception{
		
		WebConversation wc = new WebConversation();// 建立一个WebConversation实例		
		WebRequest req = new PostMethodWebRequest(url);// 向指定的URL发出请求，获取响应

		for (String para : parameter) {
			String[] parValue = para.split("=>");
			if(paramType.equals("setParameter")){
			   req.setParameter(parValue[0], parValue[1]);
			}else if(paramType.equals("setHeaderField")){
				req.setHeaderField(parValue[0], parValue[1]);
			}
		}
		WebResponse wr = wc.getResponse(req);

		return wr.getText();
	}
	
	/**
	 * 向另一个WEB工程地址模拟http请求，并接收返回的数据<br>
	 * 返回的数据的格式为xml，需要解析需要的字符<br>
	 * 设置参数时，参数名与参数值的分割符为“=>”
	 * <em>编写日期：2013年12月20日 16:27</em>
	 * @param url
	 * @param parameter
	 * @author mengfeiyang
	 * @since JDK 1.7 及以上
	 * @since Tomcat 3.0
	 *
	 */
	public static String getResponseByGet(String url, List<String> parameter,String paraType) throws Exception{
		
		WebConversation wc = new WebConversation();// 建立一个WebConversation实例		
		WebRequest req = new GetMethodWebRequest(url);// 向指定的URL发出请求，获取响应
		
		for (String para : parameter) {
			String[] parValue = para.split("=>");
			if(paraType.equals("setParameter")){
			   req.setParameter(parValue[0], parValue[1]);
			}else if(paraType.equals("setHeaderField")){
				req.setHeaderField(parValue[0], parValue[1]);
			}
		}
		WebResponse wr = wc.getResponse(req);

			return wr.getText();
	}
	
	/**
	 * 向另一个WEB工程地址模拟http请求，并接收返回的数据<br>
	 * 返回的数据的格式为xml，需要解析需要的字符<br>
	 * <em>编写日期：2013年12月20日 16:27</em>
	 * @param url
	 * @author mengfeiyang
	 * @throws IOException 
	 * @throws SAXException 
	 * @since JDK 1.7 及以上
	 * @since Tomcat 3.0
	 *
	 */
	public static String getResponseByGet(String url) throws Exception {
		
		WebConversation wc = new WebConversation();// 建立一个WebConversation实例	
		WebRequest req = new GetMethodWebRequest(url);// 向指定的URL发出请求，获取响应		
		
		WebResponse wr = wc.getResponse(req);		
		
		return wr.getText();			
	}
	
	/**
	 * 向另一个WEB工程地址模拟http请求，并接收返回的数据<br>
	 * 返回的数据的格式为xml，需要解析需要的字符<br>
	 * <em>编写日期：2013年12月20日 16:27</em>
	 * @param url
	 * @author mengfeiyang
	 * @throws IOException 
	 * @throws SAXException 
	 * @since JDK 1.7 及以上
	 * @since Tomcat 3.0
	 *
	 */
	public static String getResponseByGet(String url,int connTimeOut,int readTimeout) throws Exception {
		
		WebConversation wc = new WebConversation();// 建立一个WebConversation实例	
		wc.set_connectTimeout(connTimeOut);
		wc.set_readTimeout(readTimeout);
		
		WebRequest req = new GetMethodWebRequest(url);// 向指定的URL发出请求，获取响应		
		
		WebResponse wr = wc.getResponse(req);		
		return wr.getText();			
	}
	
	public static int getResponseCodeByGet(String url) throws Exception {
		
		WebConversation wc = new WebConversation();// 建立一个WebConversation实例			
		WebRequest req = new GetMethodWebRequest(url);// 向指定的URL发出请求，获取响应		
		WebResponse wr = wc.getResponse(req);
		
		return wr.getResponseCode();			
	}
	
	/**
	 * 向另一个WEB工程地址模拟http请求，并接收返回的数据<br>
	 * 返回的数据的格式为xml，需要解析需要的字符<br>
	 * <em>编写日期：2013年12月20日 16:27</em>
	 * @param url
	 * @author mengfeiyang
	 * @since JDK 1.7 及以上
	 * @since Tomcat 3.0
	 *
	 */
	public static String getResponseByPost(String url) throws Exception {

		WebConversation wc = new WebConversation();// 建立一个WebConversation实例
		WebRequest req = new PostMethodWebRequest(url);// 向指定的URL发出请求，获取响应
		WebResponse wr = wc.getResponse(req);
		
		return wr.getText();
	}
	
	public static void main(String[] args) throws Exception {
		List<String> ss = new ArrayList<String>();
		/*				ss.add("pid=>69b81504767483cf");
		ss.add("guid=>9c553730ef5b6c8c542bfd31b5e25b69");
		ss.add("data=>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");
		String s = GetXml.getResponseByPost(
				"http://test1.api.3g.youku.com/test/v3/decrypt?",ss
				);
	    System.out.println(s);*/
		
		//http://10.10.69.155/v2/videos/files.json?client_id=d87c5f45d421da98&client_secret=96237e2ebdbcefb13abb0becf3a7623c&video_id=XNzE4OTA0NDQ0
		ss.add("client_id=>d87c5f45d421da98");
		ss.add("client_secret=>96237e2ebdbcefb13abb0becf3a7623c");
		ss.add("video_id=>XNzE4OTA0NDQ0");
		
		String c = GetXml.getResponseByPost("http://10.10.69.155/v2/videos/files.json?", ss,"setParameter");
		System.out.println(c);	
	}

}
