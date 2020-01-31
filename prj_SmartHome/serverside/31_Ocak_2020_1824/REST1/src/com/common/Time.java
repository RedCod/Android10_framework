package com.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.TimeZone;

public class Time {
	
	public Time() {
		//const.
	}
	
	/**
	 * Get time zone.(local)
	 * @return  Exp:"Europa/Istanbul"
	 */
	public String getTimeZone() {
		TimeZone timeZone = TimeZone.getDefault();
		return timeZone.getID();
	}
	
	/**
	 * Get current time.
	 * @return  HH:mm
	 */
	public static String getCurrentTime_HHmm() {
		return getCurrentTime_Custom("HH:mm");
	}
	
	/**
	 * Get current time.
	 * @return HH:mm:ss
	 */
	public static String getCurrentTime_HHmmss() {
		return getCurrentTime_Custom("HH:mm:ss");
	}

	/**
	 * Get current time as custom.
	 * @param pattern can be "HH:mm" or "HH:mm:ss"
	 * @return HH:mm,HH:mm:ss
	 */
	public static String getCurrentTime_Custom(String pattern) {
		   DateTimeFormatter dataTimeFormatter = DateTimeFormatter.ofPattern(pattern);  
		   LocalDateTime localDateTime_Now = LocalDateTime.now();  
		   return dataTimeFormatter.format(localDateTime_Now); 
	}

	

}
