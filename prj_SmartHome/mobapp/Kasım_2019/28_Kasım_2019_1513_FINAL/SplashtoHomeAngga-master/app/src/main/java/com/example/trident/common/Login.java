package com.example.trident.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.trident.smart.MainActivity;

public class Login{
    /**
     * App. Login işmelleri.
     * -Login ol.
     * -Çıkış yap.
     * -
     */
    Activity activity;
    public Login(Activity activity){
        this.activity = activity;
    }

    /**
     * return true if succesfuly.
     * @return
     */
    public boolean login(){

        return true;
    }

    /**
     * Log out.
     *
     */
    public void logout(){

        /*
          //todo: kullanıcı giriş bilgilerini cihazdan(Preferences ile tutulan) sil ve app login sayfasına git.
         */

        //go to Login page:
        Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
        activity.startActivity(intent);
    }

}
