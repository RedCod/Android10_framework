package com.common;

public class Log {
	
	public static final boolean DEBUG = true;
	public static void println(String tag,String l) {
		System.out.println("[" + tag + "]:"+ l);
	}

}
