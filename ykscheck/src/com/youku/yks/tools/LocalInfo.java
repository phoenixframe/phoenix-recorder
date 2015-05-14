package com.youku.yks.tools;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import com.sun.management.OperatingSystemMXBean;
  
/**
 * 对机器信息操作的工具类
 * @author mengfeiyang
 *
 */
public class LocalInfo {  
	
	/**
	 * 获取机器所能创建的最大线程数，一个线程就是一个Vuser<br>
	 * 这里单纯的认为一个线程的占用内存空间为100KB<br>
	 * 获取最大线程的计算公式为：使用可用内存的一半/100用于并发测试
	 * @author mengfeiyang
	 *
	 */
    public static int getMaxThread() {     
    	OperatingSystemMXBean osmxb = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
    	long memory = osmxb.getFreePhysicalMemorySize();
    	int maxThread = new BigDecimal(((memory/1024)/2)/100).setScale(0, RoundingMode.DOWN).intValue();
    	
    	return maxThread;
    }   
    public static String getLocalIP(){
    	InetAddress addr = null;
    	try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return addr.getHostAddress().toString();
    }
    
    public static String getRealIp(){
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP
        InetAddress ip = null;
        boolean finded = false;// 是否找到外网IP
 
        Enumeration<NetworkInterface> netInterfaces;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
	        while (netInterfaces.hasMoreElements() && !finded) {
	            NetworkInterface ni = netInterfaces.nextElement();
	            Enumeration<InetAddress> address = ni.getInetAddresses();
	            while (address.hasMoreElements()) {
	                ip = address.nextElement();
	                if (!ip.isSiteLocalAddress() 
	                        && !ip.isLoopbackAddress() 
	                        && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
	                    netip = ip.getHostAddress();
	                    finded = true;
	                    break;
	                } else if (ip.isSiteLocalAddress() 
	                        && !ip.isLoopbackAddress() 
	                        && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
	                    localip = ip.getHostAddress();
	                }
	            }
	        }
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }
}
