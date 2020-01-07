package com.example.trident.smart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.trident.common.BetweenActivities;
import com.example.trident.smart.custom.CustomListAdapter_AddScheduler;

public class ActivityAddSchedule extends AppCompatActivity {
    /**
     * "Program Ekle"
     * called by "ActivityLampControl.java" :
     *
     */

    ListView listView_Schedule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        //

        LinearLayout linearLayout_Back         = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                     = (Button)findViewById(R.id.button_Back);
        TextView textView_ScheduleAccuracyInf  = (TextView)findViewById(R.id.textView_ScheduleAccuracyInf);
        LinearLayout linearLayout_Empty        = (LinearLayout)findViewById(R.id.linearLayout_Empty);
        LinearLayout linearLayout_ScheduleList = (LinearLayout)findViewById(R.id.linearLayout_ScheduleList);
        /*final ListView*/ listView_Schedule   = (ListView)findViewById(R.id.listView_Schedule);
        Button button_AddSchedule              = (Button)findViewById(R.id.button_AddSchedule);


        boolean if_schedule_list_empty = false;//representative value.(true=empty) //TODO: this value is taken from the database.
        if(if_schedule_list_empty == true){
            //List is empty:
            textView_ScheduleAccuracyInf.setVisibility(View.INVISIBLE);//hide
            linearLayout_Empty.setVisibility(View.VISIBLE);//show linearLayout_Empty
            linearLayout_ScheduleList.setVisibility(View.INVISIBLE);//hide linearLayout_ScheduleList
        }else{
            //there are programs in the List:
            textView_ScheduleAccuracyInf.setVisibility(View.VISIBLE);//show
            linearLayout_Empty.setVisibility(View.INVISIBLE);//hide linearLayout_Empty
            linearLayout_ScheduleList.setVisibility(View.VISIBLE);//show linearLayout_ScheduleList
            loadScheduleList();
        }

        linearLayout_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBack();
            }
        });
        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //GO BACK:
                goToBack();
            }
        });

        button_AddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to "Add Schedule Sub" page.
               Intent intent = new Intent(getApplicationContext(),ActivityAddScheduleSub.class);
               intent.putExtra("what_return","Add");//for preparing "ActivityAddScheduleSub.java" page.
               startActivity(intent);
            }
        });

    }//onCreate

    /**
     *TODO: get schedule data from database and load "listView_Schedule".
     * used "CustomListAdapter_AddScheduler.java"
     */
    private void loadScheduleList(){
        String[] arr_schedule_time = {"13:20","19:05","04:15"};//TODO: this value is taken from the database.
        String[] arr_schedule_day = {"Pazar,Pazartesi,Salı,Çarşamba,Perşembe,Cuma,Cumartesi","Sadece bir kez","Her gün"};//TODO: this value is taken from the database.
        String[] arr_schedule_swith = {"ON","OFF","OFF"};//TODO: this value is taken from the database.
        CustomListAdapter_AddScheduler customListAdapterAddScheduler = new CustomListAdapter_AddScheduler(this,arr_schedule_time,arr_schedule_day,arr_schedule_swith);
        listView_Schedule.setAdapter(customListAdapterAddScheduler);
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

    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivityLampControl.class);
        startActivity(intent);
    }

}
