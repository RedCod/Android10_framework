package com.example.trident.smart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.MyLog;
import com.example.trident.common.Preferences;

import java.io.File;
import java.io.LineNumberReader;
import java.text.DecimalFormat;
import java.util.List;

public class ActivitySettings extends AppCompatActivity {

    /**
     * "Ayarlar"
     * called by "FragmentMe.java"
     *
     */
    TextView textView_CacheSize;
    private String PREF_settings = "Settings";
    //private String PREF_KEY = ""
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //
        LinearLayout linearLayout_Back       = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                   = (Button)findViewById(R.id.button_Back);
        Switch switchButton_Sound            = (Switch)findViewById(R.id.switchButton_Sound);
        Switch switchButton_PushNotification = (Switch)findViewById(R.id.switchButton_PushNotification);
        LinearLayout linearLayout_About      = (LinearLayout)findViewById(R.id.linearLayout_About);
        LinearLayout linearLayout_ClearCache = (LinearLayout)findViewById(R.id.linearLayout_ClearCache);
        textView_CacheSize                   = (TextView)findViewById(R.id.textView_CacheSize);
        LinearLayout linearLayout_LogOut     = (LinearLayout)findViewById(R.id.linearLayout_LogOut);


///Default:@{
        //load settings:
        switchButton_Sound.setChecked(Preferences.loadAsBoolean(getApplicationContext(),PREF_settings,"sound"));
        switchButton_PushNotification.setChecked(Preferences.loadAsBoolean(getApplicationContext(),PREF_settings,"push_notification"));

        //load cache size:
        viewCacheSize();
///Default:@}

        ///Events:@{
        linearLayout_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBack();
            }
        });
        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBack();
            }
        });
        switchButton_Sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //
                MyLog.d("kerim[settings]","SwitchSound:" + b);
                Preferences.saveAsBoolean(getApplicationContext(),PREF_settings,"sound",b);
            }
        });
        switchButton_PushNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //
                MyLog.d("kerim[settings]","SwitchPNotif:" + b);
                Preferences.saveAsBoolean(getApplicationContext(),PREF_settings,"push_notification",b);
            }
        });
        linearLayout_About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to "Hakkında" page:
                Intent intent = new Intent(getApplicationContext(),ActivityAbout.class);
                startActivity(intent);
            }
        });
        linearLayout_ClearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo:Clear cache:
                //..
                //and set cache info to textView:
                clearApplicationData();
                viewCacheSize();
            }
        });
        linearLayout_LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: Log out (Oturumu Kapat):
                Preferences.delete(getApplicationContext(),PREF_settings,"login_username");//delete login information(username and password)
                Preferences.delete(getApplicationContext(),PREF_settings,"login_password");//delete login information(username and password)
                Toast.makeText(getApplicationContext(),"Çıkış yapıldı",Toast.LENGTH_SHORT).show();
                //go to login page:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        ///events:@}

    }//onCreate

    private void viewCacheSize(){
        double cache_size = 0;
        cache_size += getDirSize(getCacheDir());
        cache_size += getDirSize(getExternalCacheDir());
        //cache_size = Math.floor(cache_size* 1000) / 1000;//noktadan sonraki basamakları arttırmak için bölüm rakamını arttır. Örn: Math.floor(dob* 10000) / 10000;
        textView_CacheSize.setText(threeDigit(cache_size)+"MB");
    }

    private double threeDigit(double size){
        size  = Math.floor(size * 1000) / 1000;//noktadan sonraki basamakları arttırmak için bölüm rakamını arttır. Örn: Math.floor(dob* 10000) / 10000;
        return size;
    }
    private double getDirSize(File dir){
        long size = 0;
        for (File file : dir.listFiles()) {
            //MyLog.d("kerim[dir]","path:"+ file.getAbsolutePath() +",filename:" +file.getName());
            if (file != null && file.isDirectory()) {
                size += getDirSize(file);
            } else if (file != null && file.isFile()) {
                size += file.length();
            }
        }
        double sizeMB = (double)  size / 1024 / 1024L;//convert to MB
        return sizeMB;
    }
/*
    private long getCacheSize(){
        File dir = this.getApplicationContext().getCacheDir();
        return dir.length();
    }*/

    private void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if(appDir.exists()){
            String[] children = appDir.list();
            for(String s : children){
                if(!s.equals("lib")){
                    deleteDir(new File(appDir, s));
                    // Log.i("TAG", "File /data/data/APP_PACKAGE/" + s +" DELETED");
                }
            }
        }
    }
    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
    ///////////

    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivityMainPage.class);
        intent.putExtra(BetweenActivities.where_come_from,"ActivitySettings.java:ME");
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            goToBack();
        }
        else
            return super.onKeyDown(keyCode, event);

        return false;
    }

}
