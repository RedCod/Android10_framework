package com.example.trident.smart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.MyLog;
import com.example.trident.common.Static;

public class ActivityRepeatedSchedule extends AppCompatActivity {
    /** "Tekrarla"
     * used by:
     * -"ActivityAddScheduleSub.java"
     * -"ActivitySchedule.java"
     * -"ActivityValidTimePeriod.java"
     * -"ActivityTimeSegment.java"
     *
     *
     */

    CheckBox checkBox_Sunday;
    CheckBox checkBox_Monday;
    CheckBox checkBox_Tuesday;
    CheckBox checkBox_Wednesday;
    CheckBox checkBox_Thursday;
    CheckBox checkBox_Friday;
    CheckBox checkBox_Saturday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeated_schedule);
        //
        Static.WHERE_COME_FROM = getIntent().getStringExtra(BetweenActivities.where_come_from);
        String repeat_content = getIntent().getStringExtra(BetweenActivities.repeat_content);

        LinearLayout linearLayout_Back = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back             = (Button)findViewById(R.id.button_Back);
///SUNDAY:-------------------
        LinearLayout linearLayout_Sunday    = (LinearLayout)findViewById(R.id.linearLayout_Sunday);
        checkBox_Sunday                     = (CheckBox)findViewById(R.id.checkBox_Sunday);
///MONDAY:-------------------
        LinearLayout linearLayout_Monday    = (LinearLayout)findViewById(R.id.linearLayout_Monday);
        checkBox_Monday                     = (CheckBox)findViewById(R.id.checkBox_Monday);
///TUESDAY:------------------
        LinearLayout linearLayout_Tuesday   = (LinearLayout)findViewById(R.id.linearLayout_Tuesday);
        checkBox_Tuesday                    = (CheckBox)findViewById(R.id.checkBox_Tuesday);
///WEDNESDAY:------------------
        LinearLayout linearLayout_Wednesday = (LinearLayout)findViewById(R.id.linearLayout_Wednesday);
        checkBox_Wednesday                  = (CheckBox)findViewById(R.id.checkBox_Wednesday);
///THURSDAY:------------------
        LinearLayout linearLayout_Thursday  = (LinearLayout)findViewById(R.id.linearLayout_Thursday);
        checkBox_Thursday                   = (CheckBox)findViewById(R.id.checkBox_Thursday);
///FRIDAY:------------------
        LinearLayout linearLayout_Friday    = (LinearLayout)findViewById(R.id.linearLayout_Friday);
        checkBox_Friday                     = (CheckBox)findViewById(R.id.checkBox_Friday);
///SATURDAY:------------------
        LinearLayout linearLayout_Saturday  = (LinearLayout) findViewById(R.id.linearLayout_Saturday);
        checkBox_Saturday                   = (CheckBox)findViewById(R.id.checkBox_Saturday);
///////////////////////////////
        String[] str_repeat_content = repeat_content.split(",");
        for(int i = 0;i<str_repeat_content.length;i++){
            if(str_repeat_content[i].equals(getString(R.string.only_once))){//only once
                //oly once//Sadece bir kez:
                //do nothing.
            }else if(str_repeat_content[i].equals(getString(R.string.weekend))){//only weekend.
                checkBox_Sunday.setChecked(true);//pazar
                checkBox_Saturday.setChecked(true);//cumartesi
            } else if(str_repeat_content[i].equals(getString(R.string.work_day))){//only work day.
                checkBox_Monday.setChecked(true);//pazartesi
                checkBox_Tuesday.setChecked(true);//salı
                checkBox_Wednesday.setChecked(true);//çarşamba
                checkBox_Thursday.setChecked(true);//perşembe
                checkBox_Friday.setChecked(true);//cuma
            } else if(str_repeat_content[i].equals(getString(R.string.everyday))){
                //everyday //Hergün
                //todo: set checked all CheckBox:
                checkBox_Sunday.setChecked(true);
                checkBox_Monday.setChecked(true);
                checkBox_Tuesday.setChecked(true);
                checkBox_Wednesday.setChecked(true);
                checkBox_Thursday.setChecked(true);
                checkBox_Friday.setChecked(true);
                checkBox_Saturday.setChecked(true);
            }else{
                if(str_repeat_content[i].equals(getString(R.string.Sunday)))
                    checkBox_Sunday.setChecked(true);
                if(str_repeat_content[i].equals(getString(R.string.Monday)))
                    checkBox_Monday.setChecked(true);
                if(str_repeat_content[i].equals(getString(R.string.Tuesday)))
                    checkBox_Tuesday.setChecked(true);
                if(str_repeat_content[i].equals(getString(R.string.Wednesday)))
                    checkBox_Wednesday.setChecked(true);
                if(str_repeat_content[i].equals(getString(R.string.Thursday)))
                    checkBox_Thursday.setChecked(true);
                if(str_repeat_content[i].equals(getString(R.string.Friday)))
                    checkBox_Friday.setChecked(true);
                if(str_repeat_content[i].equals(getString(R.string.Saturday)))
                    checkBox_Saturday.setChecked(true);
            }
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
                goToBack();
            }
        });

        //sunday:
        linearLayout_Sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCheckedToCheckBox(checkBox_Sunday);
            }
        });
        //monday:
        linearLayout_Monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCheckedToCheckBox(checkBox_Monday);
            }
        });
        //tuesday:
        linearLayout_Tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCheckedToCheckBox(checkBox_Tuesday);
            }
        });
        //wednesday:
        linearLayout_Wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCheckedToCheckBox(checkBox_Wednesday);
            }
        });
        //thursday:
        linearLayout_Thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCheckedToCheckBox(checkBox_Thursday);
            }
        });
        //friday:
        linearLayout_Friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCheckedToCheckBox(checkBox_Friday);
            }
        });
        //saturday:
        linearLayout_Saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCheckedToCheckBox(checkBox_Saturday);
            }
        });
    }//onCreate


    private void setCheckedToCheckBox(CheckBox checkBox){
            checkBox.setChecked(!checkBox.isChecked());
    }
    private void goToBack(){
        //TODO: get selected days and send...:
        String sunday    = getString(R.string.Sunday);//pazar
        String monday    = getString(R.string.Monday);//pazartesi
        String tuesday   = getString(R.string.Tuesday);//salı
        String wednesday = getString(R.string.Wednesday);//çarşamba
        String thursday  = getString(R.string.Thursday);//perşembe
        String friday    = getString(R.string.Friday);//cuma
        String saturday  = getString(R.string.Saturday);//cumartesi
        String repeat_content = "";



        int count = 0;
        if(checkBox_Sunday.isChecked()){
            repeat_content += sunday; //pazar
            count++;
        }
        if(checkBox_Monday.isChecked()){
            if(count > 0)
                repeat_content += "," + monday; //pazartesi
            else
                repeat_content += monday;
            count++;
        }
        if(checkBox_Tuesday.isChecked()){
            if(count > 0)
                repeat_content += "," + tuesday;//salı
            else
                repeat_content += tuesday;
            count++;
        }
        if(checkBox_Wednesday.isChecked()){
            if(count > 0)
                repeat_content += "," + wednesday;//çarşamba
            else
                repeat_content += wednesday;
            count++;
        }
        if(checkBox_Thursday.isChecked()){
            if(count > 0)
                repeat_content += "," + thursday;//perşembe
            else
                repeat_content += thursday;
            count++;
        }
        if(checkBox_Friday.isChecked()){
            if(count > 0)
                repeat_content += "," + friday;//cuma
            else
                repeat_content += friday;
            count++;
        }
        if(checkBox_Saturday.isChecked()){
            if(count > 0)
                repeat_content += "," + saturday;//cumartesi
            else
                repeat_content += saturday;
            count++;
        }
/////////////
        if(count == 0)//onlye once
             repeat_content = getString(R.string.only_once);
        else if(count == 2 && checkBox_Sunday.isChecked() && checkBox_Saturday.isChecked())//only weekend.
            repeat_content = getString(R.string.weekend);
        else if(count == 5 && checkBox_Monday.isChecked() && checkBox_Tuesday.isChecked() && checkBox_Wednesday.isChecked() && checkBox_Thursday.isChecked() && checkBox_Friday.isChecked())//only work day.
            repeat_content = getString(R.string.work_day);
        else if(count == 7)//everyday
            repeat_content = getString(R.string.everyday);

        Intent intent = null;
        if(Static.WHERE_COME_FROM.equals("ActivitySchedule.java"))
            intent = new Intent(getApplicationContext(),ActivitySchedule.class);
        else if(Static.WHERE_COME_FROM.equals("ActivityAddScheduleSub.java"))
            intent = new Intent(getApplicationContext(),ActivityAddScheduleSub.class);
        else if(Static.WHERE_COME_FROM.equals("ActivityValidTimePeriod.java"))
             intent = new Intent(getApplicationContext(),ActivityValidTimePeriod.class);
        else if(Static.WHERE_COME_FROM.equals("ActivityTimeSegment.java"))
            intent = new Intent(getApplicationContext(),ActivityTimeSegment.class);

        intent.putExtra("what_return","repeat");//ATENTION: just for ActivityAddScheduleSub.java
        intent.putExtra(BetweenActivities.repeat_content,repeat_content);
        startActivity(intent);
        count = 0;
        repeat_content = "";
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
