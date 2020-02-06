package com.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Date {

	
	public Date() {
		//..
	}
	
	/**
	 * Get current date.
	 * @return "2019/12/25"
	 */
	public static String getCurrentDate_yyyyMMdd() {
		return getCurrentDate_Custom("yyyy/MM/dd");
	}
	
	/**
	 * Get current date. Day/Month/Year
	 * @return "25/12/2019"
	 */
	public static String getCurrentDate_ddMMyyyy() {
		return getCurrentDate_Custom("dd/MM/yyyy");
	}
	
	/**
	 * 
	 * @param pattern "dd-MM-yyyy" or "MM-dd-yyyy" or "yyyy-MM-dd". Also seperator can be "/" or "-".
	 * @return
	 */
	public static String getCurrentDate_Custom(String pattern) {
	   	 DateTimeFormatter dataTimeFormatter = DateTimeFormatter.ofPattern(pattern);  
		 LocalDateTime localDateTime_Now = LocalDateTime.now();  
		 return dataTimeFormatter.format(localDateTime_Now); 
	}
	
}
