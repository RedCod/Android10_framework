package com.example.trident.common;

import com.example.trident.smart.R;

import java.util.Set;

public class SettingCover {//used by "ActivitySmartSettingCover.java,"

    /**
     * Ayarlara ait sayfa başlık tema resimlerini barındırır.
     * Exp: Smart > Görünüm,Otomasyon
     */

   private Integer[] cover_images_ids = {
            R.drawable.coffe,R.drawable.coffe2,
            R.drawable.coffe,R.drawable.coffe2,
            R.drawable.coffe,R.drawable.coffe2,
            R.drawable.coffe,R.drawable.coffe2,
            R.drawable.coffe,R.drawable.coffe2,
            R.drawable.coffe,R.drawable.coffe2,
            R.drawable.coffe,R.drawable.coffe2,
            R.drawable.coffe,R.drawable.coffe2,
            R.drawable.coffe,R.drawable.coffe2
    };
   public SettingCover(){

   }

   public int getCount(){
       return cover_images_ids.length;
   }

   public int getImage(int id){
       if(id < getCount())
           return cover_images_ids[id];
       else
           return cover_images_ids[0];
   }

   public Integer[] getImagesAsArray(){
       return cover_images_ids;
   }


}
