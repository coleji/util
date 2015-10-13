package com.coleji.Util;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MACAddress {
	private MACAddress() {
		// cannot be initialized
	}
	
	public static String getMACAddress() {
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			byte[] mac = null;
			while (interfaces.hasMoreElements()) {
				NetworkInterface ni = interfaces.nextElement();
				mac = ni.getHardwareAddress();
				if (mac == null) {
					continue;
				}
				
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < mac.length; i++) {
					sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
				}
				return sb.toString();
			}
		} catch (SocketException e){
				
			e.printStackTrace();
				
		}
		return null;
	}
}
