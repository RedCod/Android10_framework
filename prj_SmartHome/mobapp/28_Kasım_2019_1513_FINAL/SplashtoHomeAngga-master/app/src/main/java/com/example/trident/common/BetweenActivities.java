package com.example.trident.common;

public class BetweenActivities {
    /**********************************************************************************
     * Activity'ler arasında veri-içerik gönderirken kullanılan etiketleri barındırır.
     *
     **********************************************************************************
     */


    /**
     * Scenario name.
     * Her Senaryou Item birçok ayar-içerik barındırır.
     * Bunların görüntülenebilmesi için "ActivitySmartSettings.java" sayfasına gidilir.
     * @see com.example.trident.smart.ActivitySmartSettings
     */
    public static String scenario_name = "scenario_name";


    /**
     * Automation name.
     * Her otomasyon Item birçok ayar-içerik barındırır.
     * Bunların görüntülenmesi için "ActivityAutomationSettings.java" sayfasına gidilir.
     * @see com.example.trident.smart.ActivityAutomationSettings
     */
    public static String automation_name = "automation_name";

    /**
     * for what,whar purpose.
     */
    public static String for_what = "for_what";

    /**
     *Index values for listView,Spinner Items.
     */
    public static String position = "position";

    /**
     * Where come from.
     * Specifies specifically from which class we are going.
     * Exp: from "A.java" to "B.java". This content "A.java"
     */
    public static String where_come_from = "where_come_from";

    /**
     * Current City value.
     */
    public static String current_city = "current_city";

    /**
     * Items values for listView,Spinner Items.
     */
    public static String item_name = "item_name";


    /**
     * Second Items values for listView,Spinner Items.
     */
    public static String item_name_extra ="item_name_extra";//second item text.

    /**
     * Selected value.
     */
    public static String selected_value = "selected_value";

    /**
     * Device Type.
     */
    public static String device_type = "device_type";

    /**
     * Page title.
     */
    public static String page_title = "page_title";

    /**
     * for "ActivityRepeatedSchedule.java" and other releated classes.
     */
    public static String repeat_content = "repeat_content";


    /** //:@{
     * for "BottomNavigationView" on "ActivityMainPage.java"
     * for parse tabs.
     */
    public static String TAB_HOME  = "tab_home";
    public static String TAB_SMART = "tab_smart";
    public static String TAB_ME    = "tab_me";
    //@}

    /** //:@{
     * for "ActivitySmartScenes.java"
     * to parse "Görünüm" and "Otomasyon".
     */
    public static String SCENE_OR_AUTOMATION = "scene_or_automation";
    public static String SCENE = "scene";
    public static String AUTOMATION = "automation";
    //:@}



///:@{ for DEVICE fixme:
    /**
     * Device name.  fixme: Replace with item_name.
     */
    public static String device_name = "device_name";

    /**
     * Device state. online,offline,etc.
     */
    public static String device_state = "device_state";

    /**
     * Device location. Mutfak,Oturma odası,etc.
     */
    public static String device_location = "device_location";

    /**
     *Check state.
     */
    public static String check_state = "check_state";

///:@ for DEVICE}



}
