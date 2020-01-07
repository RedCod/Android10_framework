package com.example.trident.common;

import android.util.Log;

public class MyLog {
    public static boolean DEGUB = true;

    public MyLog(){}
    public static void d(String _TAG,String _msg){
        Log.d(_TAG,_msg);
    }
    public static void e(String _TAG,String _msg){
        Log.e(_TAG,_msg);
    }
    public static void i(String _TAG,String _msg){
        Log.i(_TAG,_msg);
    }
    public static void v(String _TAG,String _msg){
        Log.v(_TAG,_msg);
    }
    public static void w(String _TAG,String _msg){
        Log.w(_TAG,_msg);
    }
    public static void println(String _TAG,String _msg){
        Log.v(_TAG,_msg);
    }
}
