package com.example.trident.common;

/**
 * Device Type
 */
public class DeviceType {

       public static String LAMP = "lamp";//for lamp
       public static String WALL_PLUG = "wall_plug";//wall_plug
       public static String TIME_LAPSE = "time_lapse"; //time interval
       public static String AUTOMATION = "automation";//automation

       ///for "ActivityAutomationSettings.java" listViewConditions:@{
       public static String TEMPERATURE    = "temperature";
       public static String HUMIDITY       = "humidity"; //NEM
       public static String WEATHER        = "weather"; //(bulut)HAVA DURUMU
       public static String AIR_QUALITY    = "air_quality";//HAVA KALİTESİ
       public static String SUNRISE_SUNSET = "sunrise_sunset";//GÜN DOĞUMU/GÜN BATIMI
       public static String SCHEDULE       = "schedule";//PROGRAM
       public static String SWITCH         = "switch"; //Switch //LAMBA vb.
       public static String DEVICE         = "device";//Device , Condition için herhangi bir cihazın(lamba vb) seçilebilmesi için
       ///for "ActivityAutomationSettings.java" listViewConditions:@}
}
