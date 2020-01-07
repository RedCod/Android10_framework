package com.example.trident.common;

import com.example.trident.smart.R;

public class DeviceIcon {
    /**
     * Device Type Icon.
     */

   /* Integer[] icons = {
            R.drawable.lamp, //0=lamp
            R.drawable.time_lapse, //1=delay /time interval
            R.drawable.wall_plug //2=wall_plug
    };*/
    public static int LAMP       = R.drawable.lamp;
    public static int WALL_PLUG  = R.drawable.wall_plug;
    public static int TIME_LAPSE = R.drawable.time_lapse;
    public static int AUTOMATION = R.drawable.automation_ico;

    ///for "ActivityAutomationSettings.java" listViewConditions:@{
    public static int TEMPERATURE    = R.drawable.temperature;//SICAKLIK
    public static int HUMIDITY       = R.drawable.humidity; //NEM
    public static int WEATHER        = R.drawable.weather; //(bulut)HAVA DURUMU
    public static int AIR_QUALITY    = R.drawable.air_quality;//HAVA KALİTESİ
    public static int SUNRISE_SUNSET = R.drawable.sunset;//GÜN DOĞUMU/GÜN BATIMI
    public static int SCHEDULE       = R.drawable.schedule;//PROGRAM
    public static int SWITCH         = R.drawable.plug_switch;
    ///for "ActivityAutomationSettings.java" listViewConditions:@}






}
