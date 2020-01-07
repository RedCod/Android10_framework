package com.example.trident.common;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;

import com.example.trident.smart.R;

public class Colours {
    //NOT USED.........



    public Colours(){

    }

    /**
     * get my theme color.
     * @return ColorStateList  my theme
     */
    public static ColorStateList getColorMyTheme(){
        int[][] states = new int[][] {
                new int[] { android.R.attr.state_enabled}, // enabled
                //new int[] {-android.R.attr.state_enabled}, // disabled
                //new int[] {-android.R.attr.state_checked}, // unchecked
                //new int[] { android.R.attr.state_pressed}  // pressed
        };
        int[] colors = new int[] {
                //Color.BLACK,
                //Color.RED,
                //Color.GREEN,
                //Color.BLUE
                R.color.color_my_theme


        };
     return  new ColorStateList(states,colors);
    }

    /**
     *
     * @param context  this
     * @return int  color my theme.
     */
   public static int getColorMyTheme(Context context){
        return context.getResources().getColor(R.color.color_my_theme);
   }


}
