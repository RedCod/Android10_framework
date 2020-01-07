package com.example.trident.smart;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.DeviceType;
import com.example.trident.common.MyLog;
import com.example.trident.common.Static;
import com.example.trident.common.TimePicker;

import java.util.Calendar;

public class ActivitySchedule extends AppCompatActivity {

    /**
     * using by "ActivityAutomationSelectCondition.java" and "ActivityAutomationSettings.java",
     *           = "ActivityCities.java" (go to next class and come back).
     *           = "ActivityRepeatedSchedule.java" (go to next class and come back).
     * Conditions için zamanlama-programlama sayfası.
     */
    private static String WHICH_SITUATION = null;
    //when we come back from "ActivityRepeatedSchedule.java",we will need:
    private static String TITLE  = null;
    private static String TIME   = null;
    private static String REPEAT = null;
    TextView textView_SchedulePageTitle;
    TextView textView_DaysOfRepeat;
    TextView textView_ScheduleTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        //
        LinearLayout linearLayout_Back         = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                     = (Button)findViewById(R.id.button_Back);
        textView_SchedulePageTitle             = (TextView)findViewById(R.id.textView_SchedulePageTitle);
        Button button_Done                     = (Button)findViewById(R.id.button_Done);
        LinearLayout linearLayout_Repeated     = (LinearLayout)findViewById(R.id.linearLayout_Repeated);
        textView_DaysOfRepeat                  = (TextView)findViewById(R.id.textView_DaysOfRepeat);
        ImageView imageView_DaysOfRepeat_arrow = (ImageView)findViewById(R.id.imageView_DaysOfRepeat_arrow);
        textView_ScheduleTime                  = (TextView)findViewById(R.id.textView_ScheduleTime);
        ///////////////
        int position = getIntent().getIntExtra(BetweenActivities.position,-1);
        if(position !=-1)
            Static.POSITION = position;

        String for_what = getIntent().getStringExtra(BetweenActivities.for_what);
        if(for_what !=null)
            Static.FOR_WHAT = for_what;

        MyLog.d("kerim[ActivitySchedule]","for_What:" + Static.FOR_WHAT);

        String where_come_from = getIntent().getStringExtra(BetweenActivities.where_come_from);
        if(where_come_from !=null)
            Static.WHERE_COME_FROM = where_come_from;

        String item_name = getIntent().getStringExtra(BetweenActivities.item_name);
        if(item_name !=null)
            Static.ITEM_NAME = item_name;

        MyLog.d("kerim[ActivitySchedule]","zıp:" + item_name);
        if(Static.ITEM_NAME !=null){
            //Edit için gelinmiş.
             /*
              "İş günü"
              "Her gün"
              "Hafta sonu"
              "Sadece bir kez"
              "Paz,Pzt,Sal,.. 15:35"
             */
            String[] arr = Static.ITEM_NAME.split(" ");//boşluktan itibaren alacağız.
            if(arr[0].contains(getString(R.string.Sunday_short))
                    || arr[0].contains(getString(R.string.Monday_short))
                    || arr[0].contains(getString(R.string.Tuesday_short))
                    || arr[0].contains(getString(R.string.Wednesday_short))
                    || arr[0].contains(getString(R.string.Thursday_short))
                    || arr[0].contains(getString(R.string.Friday_short))
                    || arr[0].contains(getString(R.string.Saturday_short))){
                textView_DaysOfRepeat.setText(arr[0]);//Pazar,Pazartesi,...
                textView_ScheduleTime.setText(arr[1]);// 15:20 saat bilgisini ata.
            }else{
                if(arr.length == 2) {
                    textView_DaysOfRepeat.setText(arr[0]);//"17/10" tarih saatden ayrılmış halde.
                    textView_ScheduleTime.setText(arr[1]);// 15:20 saat bilgisini ata.
                }else if(arr.length == 3) {
                    textView_DaysOfRepeat.setText(arr[0] + " " + arr[1]);//"Her gün","İş günü" veya "Hafta sonu" şeklinde parçalı haldedir. Birleştir!
                    textView_ScheduleTime.setText(arr[2]);// 15:20 saat bilgisini ata.
                }else if(arr.length == 4) {
                    //"Sadece bir kez 10:22" şeklinde parçalı:
                    textView_DaysOfRepeat.setText(arr[0] + " " + arr[1] +" " + arr[2]);
                    textView_ScheduleTime.setText(arr[3]);// 15:20 saat bilgisini ata.
                }
            }
        }else{
            //Add "(+)"  için gelinmiş.
            Calendar calendarCurrenTime = Calendar.getInstance();
            TimePicker timePicker = new TimePicker();
            //textView_ScheduleTime.setText( editTime(calendarCurrenTime.get(Calendar.HOUR_OF_DAY),calendarCurrenTime.get(Calendar.MINUTE)));//set current time.
            textView_ScheduleTime.setText(timePicker.prepareHour(calendarCurrenTime.get(Calendar.HOUR_OF_DAY)) + ":" + timePicker.prepareMinute(calendarCurrenTime.get(Calendar.MINUTE)));//set current time.
            //textView_ScheduleTime.setText("12:23");//set time now
            textView_DaysOfRepeat.setText(getString(R.string.only_once));
        }

        //this is work when we come back "ActivityRepeatedSchedule.java" to here.
        String repeat_content = getIntent().getStringExtra("repeat_content");
        if(repeat_content !=null)
            textView_DaysOfRepeat.setText(repeat_content);

       /////////////////////////
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
                //todo: Done
                Intent intent = new Intent(getApplicationContext(),ActivityAutomationSettings.class);
                intent.putExtra(BetweenActivities.position,Static.POSITION);
                intent.putExtra(BetweenActivities.for_what,Static.FOR_WHAT);
                intent.putExtra(BetweenActivities.item_name,textView_DaysOfRepeat.getText());
                intent.putExtra(BetweenActivities.selected_value, textView_ScheduleTime.getText());
                intent.putExtra(BetweenActivities.current_city,getString(R.string.schedule));//mevcut şehir yerine "Program" içeriği gönder.
                intent.putExtra(BetweenActivities.device_type, DeviceType.SCHEDULE);//SCHEDULE
                Static.makeNull();
                startActivity(intent);
            }
        });
        textView_ScheduleTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 TimePicker myTimePicker = new TimePicker();
                 String[] str_time = textView_ScheduleTime.getText().toString().split(":");
                 int h = Integer.parseInt(str_time[0]);
                 int m = Integer.parseInt(str_time[1]);
                myTimePicker.showTimePickerAndSet(ActivitySchedule.this,textView_ScheduleTime,h,m);
                //myTimePicker.showTimePickerAndSet(ActivitySchedule.this,textView_ScheduleTime);
            }
        });
///GO TO "Days Of Repeat":@{
        linearLayout_Repeated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo:go to "ActivityRepeatedSchedule.java" page:
                goToRepeatedActivity();
            }
        });
        textView_DaysOfRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo:go to "ActivityRepeatedSchedule.java" page:
                goToRepeatedActivity();
            }
        });
        imageView_DaysOfRepeat_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo:go to "ActivityRepeatedSchedule.java" page:
                goToRepeatedActivity();
            }
        });
///GO TO "Days Of Repeat":@}

        ///Events:}

    }//onCreate


    private void goToRepeatedActivity(){
        TITLE  = textView_SchedulePageTitle.getText().toString();
        TIME   = textView_ScheduleTime.getText().toString();
        REPEAT = textView_DaysOfRepeat.getText().toString();
        Static.PREVIOUS_CLASS = Static.WHERE_COME_FROM;//önceki sınıfı tut.
        Intent intent = new Intent(getApplicationContext(),ActivityRepeatedSchedule.class);
        intent.putExtra(BetweenActivities.where_come_from,"ActivitySchedule.java");
        intent.putExtra("repeat_content",REPEAT);
        startActivity(intent);
    }
    private void goToBack(){
        Intent intent = null;
        if(Static.WHERE_COME_FROM.equals("ActivitySchedule.java"))
            Static.WHERE_COME_FROM = Static.PREVIOUS_CLASS;

        if(Static.WHERE_COME_FROM.equals("ActivityAutomationSelectCondition.java"))
            intent = new Intent(getApplicationContext(), ActivityAutomationSelectCondition.class);
        else if(Static.WHERE_COME_FROM.equals("ActivityAutomationSettings.java"))
            intent = new Intent(getApplicationContext(), ActivityAutomationSettings.class);
        intent.putExtra(BetweenActivities.for_what,Static.FOR_WHAT);
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
