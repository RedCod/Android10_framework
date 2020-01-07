package com.example.trident.common;

public class Lamp {
/**for LAMP everything.
 * retains the settings of the device taken from the database.
 */




    private static boolean ON_STATUS = false; //is ON=true
    private static boolean OFF_STATUS = false;//is OFF=true,
    private static boolean RGB_STATUS = false;//is RGB = true
    private static int BRIGHT_LEVEL_OF_WHITE = 0;
    private static int COLOUR_TEMP_LEVEL_OF_WHITE = 0;
    private static int SATURATION_LEVEL_OF_COLOUR = 0;
    private static int BRIGHT_LEVEL_OF_COLOUR = 0;
    public Lamp(){
        //
    }
    /**
     * lamp is on,return true.
     * @return
     */
    public static boolean isON(){
        if(ON_STATUS)
            return  true;
        else
            return false;
    }

    /**
     * lamp is off,return true
     * @return
     */
    public static boolean isOFF(){
        if(OFF_STATUS)
            return true;
        else
            return false;
    }

    /**
     * set lamp ON or OFF status value.
     * @param status //status value from database.
     */
    public static void setONOFF(boolean status){
        ON_STATUS   = status;
        OFF_STATUS  = !status;
    }

    public static void setON(boolean status){
        ON_STATUS = status;
        OFF_STATUS = !status;
    }
    public static void setOFF(boolean status){
        OFF_STATUS = status;
        ON_STATUS = !status;
    }
    /**
     * get lamp "ON" or "OFF" status as String
     * @return "ON" or "OFF"
     */
    public static String getONOFF(){
        if(ON_STATUS)
            return "ON";
        else
            return  "OFF";
    }

    /**
     * set lamp RGB status.Lamp is RGB =true,else =false
     * @param status
     */
    public static void setRGB(boolean status){
        RGB_STATUS = status;
    }
    /**
     * if RGB is available,return true,else return false.
     * @return
     */
    public static boolean isRGB(){
        return  RGB_STATUS;
    }


    private static float X_point = 0.0f;
    private static float Y_point = 0.0f;
    /**
     * colour pallette x y point of the user's last selected color.
     * @param _point
     */
    public static void setColorXYPoint(String _point){
        String[] point = _point.split(",");
        X_point = Float.parseFloat(point[0]);
        Y_point = Float.parseFloat(point[1]);
    }

    /**
     * get last selected color X point.
     * @return
     */
    public static float getColorXPoint(){
        return  X_point;
    }

    /**
     * get last selected color Y point.
     * @return
     */
    public static float getColorYPoint(){
        return  Y_point;
    }


    /**
     * set bright level for lamp.
     * @param level
     */
    public static void setBrightLevelOfWhite(int level){ BRIGHT_LEVEL_OF_WHITE =  level; }

    /**
     * get bright level for lamp.
     * @return
     */
    public static int getBrightLevelOfWhite(){ return BRIGHT_LEVEL_OF_WHITE; }

    /**
     * set colour temp level,for lamp.
     * @param temp
     */
    public static void setColourTempLevelOfWhite(int temp) {COLOUR_TEMP_LEVEL_OF_WHITE = temp;}

    /**
     * get colour temp,for lamp.
     * @return
     */
    public static int getColourTempLevelOfWhite(){return COLOUR_TEMP_LEVEL_OF_WHITE;}

    /**
     * set saturation level for lamp.
     * @param saturation
     */
    public static void setSaturationLevelOfColour(int saturation){SATURATION_LEVEL_OF_COLOUR = saturation;}

    /**
     * get saturation level of "COLOUR" for lamp.
     * @return
     */
    public static int getSaturationLevelOfColour(){return SATURATION_LEVEL_OF_COLOUR;}


    /**
     * set bright level of "COLOUR" item option.
     * @param level
     */
    public static void setBrightLevelOfColour(int level){BRIGHT_LEVEL_OF_COLOUR = level;}

    /**
     *get bright level of "COLOUR" item option. (when selected)
     * @return
     */
    public static int getBrightLevelOfColour(){return BRIGHT_LEVEL_OF_COLOUR;}

}
