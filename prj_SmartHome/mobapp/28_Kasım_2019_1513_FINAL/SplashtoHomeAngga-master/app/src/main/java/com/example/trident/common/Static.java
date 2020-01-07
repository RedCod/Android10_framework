package com.example.trident.common;

public class Static {
    /***********************************************************************************************
     * Class ve Activity'lerde global de kullanılan ortak "static" tanımlamaları barındırı.
     *
     ***********************************************************************************************
     */

    /**
     * Scenario name.
     * Her Senaryou Item birçok ayar-içerik barındırır.
     * Bunların görüntülenebilmesi için "ActivitySmartSettings.java" sayfasına gidilir.
     * @see com.example.trident.smart.ActivitySmartSettings
     */

    public static String SCENARIO_NAME = null;
    /**
     * Automation name.
     * Her otomasyon Item birçok ayar-içerik barındırır.
     * Bunların görüntülenmesi için "ActivityAutomationSettings.java" sayfasına gidilir.
     * @see com.example.trident.smart.ActivityAutomationSettings
     */
    public static String AUTOMATION_NAME = null;


    /**
     * Otomasyon eklemek için mi yoksa var olan bir otomasyonu editlemek için mi gelindi?
     */
    public static String FOR_WHAT = null; //Otomasyon eklemek için mi yoksa var olan bir otomasyonu editlemek için mi gelindi?

    /**
     * nerden(Hangi class'dan geldik. geri dönmek için ve seçimi tamamladıktan sonra gerekli).
     */
    public static String WHERE_COME_FROM = null; //nerden(Hangi class'dan geldik. geri dönmek için ve seçimi tamamladıktan sonra gerekli).


    /**
     * Previous class.
     * Exp:  A.java,B.java,C.java
     * We came from A.java to B.java and then to C.java. Now we be here(C.java).
     * So WHERE_COME_FROM = B.java, PREVIOUS_CLASS = A.java
     *
     */
    public static String PREVIOUS_CLASS = null;

    /**
     * ListView Item isimlerini tutar. Custom item'larda en üstteki/belirgin/ana item adını baz alır.
     */
    public static String ITEM_NAME = null;

    /**
     * Mevcut şehir.
     */
    public static String CURRENT_CITY = null;

    /**
     * Editleme esnasında listView itemların index/position değerleri tutulur.
     */
    public static int POSITION = -1; //Editleme esnasında listView itemların index/position değerleri tutulur.

    /**
     * repeat content for "ActivityValidTimePeriod.java" and and related classes.
     */
    //public static String REPEAT_CONTENT = null;


    /**
     *Holds the current scroll positions of pages built on ScrollView.
     * Exp: ActivitySmartSettings.java,ActivityAutomationSettings.java,
     */
    public static int CURRENT_SCROLL_POSITION = 0;

    /**
     * Make all constants null.
     */
    public static void makeNull(){
        FOR_WHAT = null;
        WHERE_COME_FROM = null;
        PREVIOUS_CLASS = null;
        ITEM_NAME  = null;
        CURRENT_CITY = null;
        POSITION = -1;
    }

}
