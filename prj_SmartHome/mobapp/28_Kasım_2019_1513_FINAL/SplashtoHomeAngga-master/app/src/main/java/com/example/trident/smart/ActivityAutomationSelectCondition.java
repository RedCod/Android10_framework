package com.example.trident.smart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.MyLog;

public class ActivityAutomationSelectCondition extends AppCompatActivity {

    /**
     * used by "ActivityAutomationSettings.java"
     * "Koşul Seç" sayfası(sıcaklık,nem,hava,hava kalitesi,gün doğumu/gün batımı,program,cihaz).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automation_select_condition);
        //

        LinearLayout linearLayout_Back              = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                          = (Button)findViewById(R.id.button_Back);
        LinearLayout linearLayout_GoToTemperature   = (LinearLayout)findViewById(R.id.linearLayout_GoToTemperature);
        LinearLayout linearLayout_GoToHumidity      = (LinearLayout)findViewById(R.id.linearLayout_GoToHumidity);
        LinearLayout linearLayout_GoToWeather       = (LinearLayout)findViewById(R.id.linearLayout_GoToWeather);
        LinearLayout linearLayout_GoToAirQuality    = (LinearLayout)findViewById(R.id.linearLayout_GoToAirQuality);
        LinearLayout linearLayout_GoToSunriseSunset = (LinearLayout)findViewById(R.id.linearLayout_GoToSunriseSunset);
        LinearLayout linearLayout_GoToSchedule      = (LinearLayout)findViewById(R.id.linearLayout_GoToSchedule);
        LinearLayout linearLayout_GoToDevice        = (LinearLayout)findViewById(R.id.linearLayout_GoToDevice);
        ///Event:@{
        linearLayout_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to back
                goToBack();
            }
        });
        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to back:
                goToBack();
            }
        });
        linearLayout_GoToTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to Temperature page:
                Intent intent = new Intent(getApplicationContext(),ActivityTemperature.class);
                intent.putExtra(BetweenActivities.for_what,getIntent().getStringExtra(BetweenActivities.for_what));
                intent.putExtra(BetweenActivities.where_come_from,"ActivityAutomationSelectCondition.java");
                startActivity(intent);
            }
        });
        linearLayout_GoToHumidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to Humidity page
                Intent intent = new Intent(getApplicationContext(),ActivityHumidity.class);
                intent.putExtra(BetweenActivities.for_what,getIntent().getStringExtra(BetweenActivities.for_what));
                intent.putExtra(BetweenActivities.where_come_from,"ActivityAutomationSelectCondition.java");
                startActivity(intent);
            }
        });
        linearLayout_GoToWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to Weather page
                Intent intent = new Intent(getApplicationContext(),ActivityWeather.class);
                intent.putExtra(BetweenActivities.for_what,getIntent().getStringExtra(BetweenActivities.for_what));
                intent.putExtra(BetweenActivities.where_come_from,"ActivityAutomationSelectCondition.java");
                startActivity(intent);
            }
        });
        linearLayout_GoToAirQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to Air Quality
                Intent intent = new Intent(getApplicationContext(),ActivityAirQuality.class);
                intent.putExtra(BetweenActivities.for_what,getIntent().getStringExtra(BetweenActivities.for_what));
                intent.putExtra(BetweenActivities.where_come_from,"ActivityAutomationSelectCondition.java");
                startActivity(intent);
            }
        });
        linearLayout_GoToSunriseSunset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to Sunrise and Sunset page
                Intent intent = new Intent(getApplicationContext(),ActivitySunriseSunset.class);
                intent.putExtra(BetweenActivities.for_what,getIntent().getStringExtra(BetweenActivities.for_what));
                intent.putExtra(BetweenActivities.where_come_from,"ActivityAutomationSelectCondition.java");
                startActivity(intent);
            }
        });
        linearLayout_GoToSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to Schedule page
                Intent intent = new Intent(getApplicationContext(),ActivitySchedule.class);
                intent.putExtra(BetweenActivities.for_what,getIntent().getStringExtra(BetweenActivities.for_what));
                intent.putExtra(BetweenActivities.where_come_from,"ActivityAutomationSelectCondition.java");
                startActivity(intent);
            }
        });
        linearLayout_GoToDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to Device page
                Intent intent = new Intent(getApplicationContext(),ActivitySmartSelectAction.class);
                intent.putExtra(BetweenActivities.where_come_from,"ActivityAutomationSelectCondition.java");
                intent.putExtra(BetweenActivities.for_what,getIntent().getStringExtra(BetweenActivities.for_what));//we will need when we back here this class.
                startActivity(intent);
            }
        });
        ///Event:@}
    }//onCreate

    private void goToBack(){
        String for_what = getIntent().getStringExtra(BetweenActivities.for_what);//we will send back.
        Intent intent = new Intent(getApplicationContext(),ActivityAutomationSettings.class);
        intent.putExtra(BetweenActivities.for_what,for_what);
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
