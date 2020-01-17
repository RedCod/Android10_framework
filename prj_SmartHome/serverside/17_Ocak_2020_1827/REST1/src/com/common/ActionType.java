package com.common;

public class ActionType {
	/**
	 * ACTION TYPE	-  İŞLEM TİPLERİ. 
	 * Senaryo,otomasyon vb. içeriklerini ayrıştırma.
	 */
	public static String DEVICE       = "device";			//işlem tipi "cihaz". Device , Listelenirken veya otomasyon condition için herhangi bir cihazın(lamba vb) seçilebilmesi için
	public static String DEVICE_GROUP = "device_group";  //işlem tipi "cihaz grubu". Listelenirken cihaz grubu olarak işaretlenmesi için kullanılacak.
	public static String TIME_LAPSE   = "time_lapse"; //işlem tipi "gecikme"
	public static String SCENARIO     = "scenario";     //işlem tipi "senaryo"
	public static String AUTOMATION   = "automation";//işlem tipi "otomasyon"
	
    ///for Automation Conditions(otomasyon ŞARTLAR):@{
    public static String TEMPERATURE    = "temperature"; //Sıcaklık
    public static String HUMIDITY       = "humidity"; //NEM
    public static String WEATHER        = "weather"; //(bulut)HAVA DURUMU
    public static String AIR_QUALITY    = "air_quality";//HAVA KALİTESİ
    public static String SUNRISE_SUNSET = "sunrise_sunset";//GÜN DOĞUMU/GÜN BATIMI
    public static String WIND_SPEED 	= "wind_speed"; //wind speed(rüzgar hızı) 
    public static String SCHEDULE       = "schedule";//PROGRAM
    public static String SWITCH         = "switch"; //Switch //LAMBA vb. Şartlar kısmında tüm cihaz tiplerini temsil eder.
    ///for Automation Conditions(otomasyon ŞARTLAR):@}
}
