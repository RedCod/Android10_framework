package com.common;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.digest.DigestUtils;


public class MD5Class {
	/**
	 * MD5 işlemleri
	 */
	
	public MD5Class() {
		//
	}

	/**
	 * Convert data to md5 hex.
	 * @param data  //any string value
	 * @return		//return md5hex value.
	 */
	public static String md5Hex(String data) {
	        return DigestUtils.md2Hex(data);
	}//md5Hex()
	
	
}
