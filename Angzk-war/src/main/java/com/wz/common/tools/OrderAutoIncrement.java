package com.wz.common.tools;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * 订单号递增 
 * @author Johnson.Jia
 */
public class OrderAutoIncrement {
	
	private static AtomicInteger counter = new AtomicInteger(0);

	private static final String auto = "0000";
	
	private static final String ip ;
	
	static {
		StringBuffer ip_auto = new StringBuffer("000");
		try {
			InetAddress addr = InetAddress.getLocalHost();
			ip_auto.append(addr.getHostAddress().split("\\.")[3]);
		} catch (UnknownHostException e) {
			
		}
		ip = ip_auto.substring(ip_auto.length()-3);
	}
	
	/**
	 * 在初始值上自增1
	 * @return
	 */
	public static String Plus(){
		synchronized (auto.intern()) {
			int i = counter.getAndIncrement();
			StringBuilder orderNumber = new StringBuilder(auto).append(i);
			if (i >= 9999) {
				counter.set(0);
			}
			return ip + orderNumber.substring(orderNumber.length() - 3);
		}
	}

}
