package com.example.trident.common;

import android.content.Context;

import com.example.trident.smart.R;

public class MonthsAndDays {

    /**
     * Ay ve gün ile alakalı işmeler.
     */

    /*//Days:
      Sunday    = Pazar
      Monday    = Pazartesi
      Tuesday   = Salı
      Wednesday = Çarşamba
      Thursday  = Perşembe
      Friday    = Cuma
      Saturday  = Cumartesi
     */
    /*//Months:
      January   = Ocak
      February  = Şubat
      March     = Mart
      April     = Nisan
      May       = Mayıs
      June      = Haziran
      July      = Temmuz
      August    = Ağustos
      September = Eylül
      October   = Ekim
      November  = Kasım
      December  = Aralık
     */


    Context context;
    public MonthsAndDays(Context _context){
        this.context = _context;
    }

    /**
     * for test: 17Ekm_10:14.
     * Shorten day names.
     * @param day_name name of Days.
     */
    public String shortenDayNames(String day_name){

        if(!day_name.equals(context.getString(R.string.only_once)) && !day_name.equals(context.getString(R.string.weekend)) && !day_name.equals(context.getString(R.string.everyday))
                && !day_name.equals(context.getString(R.string.work_day))){//if none..
            String[] arr = day_name.split(","); //Pazartesi,Salı,Çarşamba....
            String day ="";
            for(int i=0;i<arr.length;i++){
                if(i !=0)
                    day += ",";
                if(arr[i].equals(context.getString(R.string.Sunday)))
                    day += context.getString(R.string.Sunday_short);
                else if(arr[i].equals(context.getString(R.string.Monday)))
                    day += context.getString(R.string.Monday_short);
                else if(arr[i].equals(context.getString(R.string.Tuesday)))
                    day += context.getString(R.string.Tuesday_short);
                else if(arr[i].equals(context.getString(R.string.Wednesday)))
                    day += context.getString(R.string.Wednesday_short);
                else if(arr[i].equals(context.getString(R.string.Thursday)))
                    day += context.getString(R.string.Thursday_short);
                else if(arr[i].equals(context.getString(R.string.Friday)))
                    day += context.getString(R.string.Friday_short);
                else if(arr[i].equals(context.getString(R.string.Saturday)))
                    day += context.getString(R.string.Saturday_short);
            }
            day_name = day;
        }
        return  day_name;
    }//shortenDayNames
}
