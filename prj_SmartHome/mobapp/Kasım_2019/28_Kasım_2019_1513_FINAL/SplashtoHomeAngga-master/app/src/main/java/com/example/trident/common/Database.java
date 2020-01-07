package com.example.trident.common;

public class Database {
    /**
     * database operations
     */

    public Database(){
        //Constructor
    }



///PRIVATE:









///PUBLIC:

    /**
     * Load Lamp settings from database,for "Lamp Control".
     * @param lamp_name //"Lamp name or ID"
     * @return // true = loading is successfully.
     */
    public boolean loadLampSettingsForLampControl(String lamp_name){
        //called from ".."
         /*TODO: getting from database:
           -Lamp status ON/OFF
           -Lamp is RGB or not
           -Lamp color point
           -Saturation value.
           -Bright value
           -Colour temp //for white mod,so if lamp not RGB.
          */

         //fixme:representative values:
        boolean lamp_on_off_status = true;//get from database.
        boolean rgb_status = true;//get from database
        String last_selected_colour_point = "126.6f,93.6f"; //X,Y point.//get from database.
        int bright_level_of_WHITE = 59;//get from database.
        int colour_temp_level_of_WHITE = 91;
        int saturation_level_of_COLOUR = 100;
        int bright_level_of_COLOUR = 63;
        //
        Lamp.setONOFF(lamp_on_off_status);
        Lamp.setRGB(rgb_status);
        Lamp.setColorXYPoint(last_selected_colour_point);
        //white:
        Lamp.setBrightLevelOfWhite(bright_level_of_WHITE);
        Lamp.setColourTempLevelOfWhite(colour_temp_level_of_WHITE);
        //colour:
        Lamp.setSaturationLevelOfColour(saturation_level_of_COLOUR);
        Lamp.setBrightLevelOfColour(bright_level_of_COLOUR);

        return true;//fix
    }

}
