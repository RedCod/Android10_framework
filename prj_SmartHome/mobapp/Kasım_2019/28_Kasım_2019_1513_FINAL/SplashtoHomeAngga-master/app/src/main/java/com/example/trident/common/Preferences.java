package com.example.trident.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    /**
     * using  by:
     * -MainActivity.java
     * -ActivitySettings.java
     * -
     */

    /** !!!!!!!!!!!!! DİKKAT !!!!!!!!!!!!!!!!!
     * -Activity'ler arası veri taşıma "Intent" yerine "SharedPreference" kullanılacak.
     * -(ok)User login olduktan sonra bilgileri SharedPreference olarak tutulacak.User quit yapınca login bilgileri buradan silinecek.(otomatik login için saklanan veriler).
     * -Application ile ilgili diğer ayar ve datalar SharedPreference olarak tutulacak.
     */

    public  Preferences(){
        ///....
    }

    /**
     * Save preferences as Int
     * @param context
     * @param pref
     * @param key
     * @param value
     * @return
     */
    public static boolean saveAsInt(Context context,String pref,String key,int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(pref, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        boolean state = editor.commit();
        return state;
    }

    /**
     * Save preferences as String
     * @param context
     * @param pref
     * @param key
     * @param value
     * @return
     */
    public static boolean saveAsString(Context context,String pref,String key,String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(pref, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        boolean state = editor.commit();
        return state;
    }

    /**
     * Save pereferences as Boolean
     * @param context
     * @param pref
     * @param key
     * @param value
     * @return
     */
    public static boolean saveAsBoolean(Context context,String pref,String key,boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(pref, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        boolean state = editor.commit();
        return state;
    }

    /**
     * Get preferences value as Integer
     * @param context
     * @param pref
     * @param key
     * @return
     */
    public static int loadAsInt(Context context,String pref,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(pref, Activity.MODE_PRIVATE);
        int int_value = sharedPreferences.getInt(key, 0);//if no value,normally get "-1". But we selection item with position value. So if not value in the preferences,get '0'.
        return int_value;
    }

    /**
     * Get preferences value as String
     * @param context
     * @param pref
     * @param key
     * @return
     */
    public static String loadAsString(Context context,String pref,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(pref, Activity.MODE_PRIVATE);
        String str_value = sharedPreferences.getString(key,"null");
        return str_value;
    }

    /**
     * Get preferences value as Boolean.
     * @param context
     * @param pref
     * @param key
     * @return
     */
    public static Boolean loadAsBoolean(Context context,String pref,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(pref, Activity.MODE_PRIVATE);
        Boolean bln_value = sharedPreferences.getBoolean(key,false);
        return bln_value;
    }

    /**
     * Delete preferences
     * @param context
     * @param pref
     * @param key
     */
    public static void delete(Context context,String pref,String key){
        SharedPreferences preferences = context.getSharedPreferences(pref, 0);
        preferences.edit().remove(key).commit();
    }

    /**
     * Delete all preferences.
     * @param context
     * @param pref
     */
    public static void deleteAll(Context context,String pref){
        SharedPreferences preferences = context.getSharedPreferences(pref, 0);
        preferences.edit().clear().commit();
    }

}
