package com.example.trident.smart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.DeviceType;
import com.example.trident.common.Static;

public class ActivitySunriseSunset extends AppCompatActivity {
    /**
     * using by "ActivityAutomationSelectCondition.java" and "ActivityAutomationSettings.java",
     *           "ActivityCities.java (go to next class and come back)
     * "Gün Doğumu/Gün Batımı" (Gündoğumu,Günbatımı) seç.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sunrise_sunset);
        ///
        LinearLayout linearLayout_Back            = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                        = (Button)findViewById(R.id.button_Back);
        Button button_Done                        = (Button)findViewById(R.id.button_Done);
        LinearLayout linearLayout_GoToCurrentCity = (LinearLayout)findViewById(R.id.linearLayout_GoToCurrentCity);
        final TextView textView_CurrentCity             = (TextView)findViewById(R.id.textView_CurrentCity);
        final AppCompatRadioButton radioButton_Sunrise  = (AppCompatRadioButton)findViewById(R.id.radioButton_Sunrise);
        final AppCompatRadioButton radioButton_Sunset   = (AppCompatRadioButton)findViewById(R.id.radioButton_Sunset);


        int position = getIntent().getIntExtra(BetweenActivities.position,-1);
        if(position !=-1)
            Static.POSITION = position;

        String for_what = getIntent().getStringExtra(BetweenActivities.for_what);
        if(for_what !=null)
            Static.FOR_WHAT = for_what;

        String where_come_from = getIntent().getStringExtra(BetweenActivities.where_come_from);
        if(where_come_from !=null)
            Static.WHERE_COME_FROM = where_come_from;

        String current_city = getIntent().getStringExtra(BetweenActivities.current_city);
        if(current_city !=null)
            Static.CURRENT_CITY = current_city;
        textView_CurrentCity.setText(Static.CURRENT_CITY);
        String item_name = getIntent().getStringExtra(BetweenActivities.item_name);
        if(item_name !=null)
            Static.ITEM_NAME = item_name;

        if(Static.ITEM_NAME !=null){
            String[] arr = Static.ITEM_NAME.split(":");//Gün batımı/Gün doğumu:Gündoğumu
            if(arr[1].equals(getString(R.string.sunrise)))
                radioButton_Sunrise.setChecked(true);
            else if(arr[1].equals(getString(R.string.sunset)))
                radioButton_Sunset.setChecked(true);
        }


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
        button_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Static.CURRENT_CITY == null){
                    Toast.makeText(getApplicationContext(),getString(R.string.select_city),Toast.LENGTH_SHORT).show();
                    return;
                }

                if(radioButton_Sunrise.isChecked() ==false && radioButton_Sunset.isChecked() == false){
                    Toast.makeText(getApplicationContext(),getString(R.string.msg_select_astatus),Toast.LENGTH_SHORT).show();
                    return;
                }

                String s_value = null;
                if(radioButton_Sunrise.isChecked())
                    s_value = getString(R.string.sunrise);
                else if(radioButton_Sunset.isChecked())
                    s_value = getString(R.string.sunset);
                Static.CURRENT_CITY = textView_CurrentCity.getText().toString();
                Intent intent = new Intent(getApplicationContext(),ActivityAutomationSettings.class);
                intent.putExtra(BetweenActivities.position,Static.POSITION);
                intent.putExtra(BetweenActivities.for_what,Static.FOR_WHAT);
                intent.putExtra(BetweenActivities.item_name,getString(R.string.sunrise_sunset));
                intent.putExtra(BetweenActivities.selected_value, s_value);
                intent.putExtra(BetweenActivities.current_city,Static.CURRENT_CITY);
                intent.putExtra(BetweenActivities.device_type, DeviceType.SUNRISE_SUNSET);//HUMIDITY
                //clear for new:
                Static.makeNull();
                startActivity(intent);
            }
        });
        linearLayout_GoToCurrentCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to city
                Static.PREVIOUS_CLASS = Static.WHERE_COME_FROM;//önceki sınıfı tut.
                Intent intent = new Intent(getApplicationContext(),ActivityCities.class);
                intent.putExtra(BetweenActivities.where_come_from,"ActivitySunriseSunset.java");
                startActivity(intent);
            }
        });
        radioButton_Sunrise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if(b)
                   Static.ITEM_NAME = ":" + radioButton_Sunrise.getText(); //add ":" ,because we make split(":") when installing first.
            }
        });
        radioButton_Sunset.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    Static.ITEM_NAME = ":" + radioButton_Sunset.getText();
            }
        });
        ///Events:@}


    }//onCreate


    private void goToBack(){
        Intent intent = null;
        if(Static.WHERE_COME_FROM.equals("ActivitySunriseSunset.java"))
            Static.WHERE_COME_FROM = Static.PREVIOUS_CLASS;

        if(Static.WHERE_COME_FROM.equals("ActivityAutomationSelectCondition.java"))
            intent = new Intent(getApplicationContext(), ActivityAutomationSelectCondition.class);
        else if(Static.WHERE_COME_FROM.equals("ActivityAutomationSettings.java"))
            intent = new Intent(getApplicationContext(), ActivityAutomationSettings.class);
        intent.putExtra(BetweenActivities.for_what,Static.FOR_WHAT);
       /* Static.POSITION = -1;
        Static.CURRENT_CITY = null;*/
        Static.makeNull();
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
