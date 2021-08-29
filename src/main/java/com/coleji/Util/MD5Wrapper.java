package com.coleji.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Wrapper {
	private MD5Wrapper() {
		// can't be instantiated
	}
	
	// in oracle, do this to mirror:
	// utl_raw.cast_to_raw(dbms_obfuscation_toolkit.md5(input_string => 'whatevertheoriginalstringwas'))
	
	public static String getMD5Hash(String original) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(original.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString().toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
