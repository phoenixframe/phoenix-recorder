package com.youku.yks.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 获取当前时间，并以各种形式返回。<br>
 * 并且可以对时间差进行计算<br>
 * @author mengfeiyang
 *
 */

public class GetNow {
	
	/**
	 * 获取当前日期的各种形式。
	 * 如果不填写dateType,则返回当前的日期。
	 * 如果dateType为yyyy，返回当前时间的‘年’<br>
	 * 如果dateType为MM，返回当前时间的‘月’<br>
	 * 如果dateType为dd，返回当前时间的‘日’<br>
	 * @param dateType
	 * @return
	 */
	public static String getDate(String... dateType) {
		Calendar ca = Calendar.getInstance();

		int year = ca.get(Calendar.YEAR);
		int month = ca.get(Calendar.MONTH);
		int day = ca.get(Calendar.DATE);

		try {
			String type = dateType[0];
			switch (type) {
			case "yyyy":
				return year + "年";
			case "MM":
				return (month + 1) + "月";
			case "dd":
				return day + "日";
			default:
				return year + "-" + (month + 1) + "-" + (day);
			}
		} catch (Exception e) {
			return year + "-" + (month + 1) + "-" + (day);
		}
	}
	
	/**
	 * 返回默认的当前的时间。返回格式为：yyyy-MM-dd HH:mm:ss<br>
	 * @author mengfeiyang
	 * @return
	 */	
	public static String getCurrentTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("",Locale.SIMPLIFIED_CHINESE); 
	
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
		//sdf.applyPattern("HH:mm:ss,SSS");
		Date currDate = new Date();
		String timeStr = sdf.format(currDate); 
				
		return timeStr;
	}
	
	/**
	 * 将当前获得的毫秒数转换成指定格式的时间.<br>
	 * 如："yyyy-MM-dd HH:mm:ss"<br>
	 * @author mengfeiyang
	 * @param PATTERN
	 * @return
	 */	
	public static String getCurrentTime(String PATTERN){
		SimpleDateFormat sdf = new SimpleDateFormat("",Locale.SIMPLIFIED_CHINESE); 		
		sdf.applyPattern(PATTERN);
		Date currDate = new Date();
		String timeStr = sdf.format(currDate); 
				
		return timeStr;
	}
	
	public static String getFormatTime(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		return sdf.format(date);
	}
	
	/**
	 * 将指定的毫秒数转换成指定格式的时间.<br>
	 * 如："yyyy-MM-dd HH:mm:ss"<br>
	 * @author mengfeiyang
	 * @param PATTERN
	 * @return
	 */	
	public static String getCurrentTime(long time,String PATTERN){
		SimpleDateFormat sdf = new SimpleDateFormat("",Locale.SIMPLIFIED_CHINESE); 		
		sdf.applyPattern(PATTERN);
		String timeStr = sdf.format(time); 
				
		return timeStr;
	}

}
