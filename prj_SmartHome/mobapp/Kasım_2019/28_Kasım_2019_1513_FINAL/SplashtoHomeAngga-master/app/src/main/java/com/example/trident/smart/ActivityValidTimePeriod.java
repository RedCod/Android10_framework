package com.example.trident.smart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.trident.common.BetweenActivities;

public class ActivityValidTimePeriod extends AppCompatActivity {
    /**
     * called by "ActivityAutomationSettings.java
     *
     */
    AppCompatRadioButton radioButton_AllDay,radioButton_Day,radioButton_Night,radioButton_Customized;
    TextView textView_CustomizedDescp;
    private static String VALID_TIME =null;
    private static String REPEAT_CONTENT = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valid_time_period);
        //
        LinearLayout linearLayout_Back              = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                          = (Button)findViewById(R.id.button_Back);
        Button button_Done                          = (Button)findViewById(R.id.button_Done);
        /*final AppCompatRadioButton*/ radioButton_AllDay     = (AppCompatRadioButton)findViewById(R.id.radioButton_AllDay);
        /*final AppCompatRadioButton*/ radioButton_Day        = (AppCompatRadioButton)findViewById(R.id.radioButton_Day);
        /*final AppCompatRadioButton*/ radioButton_Night      = (AppCompatRadioButton)findViewById(R.id.radioButton_Night);
        /*final AppCompatRadioButton*/ radioButton_Customized = (AppCompatRadioButton)findViewById(R.id.radioButton_Customized);
        /*final TextView*/ textView_CustomizedDescp                 = (TextView)findViewById(R.id.textView_CustomizedDescp);
        LinearLayout linearLayout_GoToRepeat        = (LinearLayout)findViewById(R.id.linearLayout_GoToRepeat);
        final TextView textView_Repeat              = (TextView)findViewById(R.id.textView_Repeat);

        String valid_time = getIntent().getStringExtra("valid_time"); //get from "ActivityAutomationSettings.java"
        if(valid_time !=null)
            VALID_TIME = valid_time;
        if(VALID_TIME.equals(getString(R.string.all_day)))
            radioButton_AllDay.setChecked(true);
        else if(VALID_TIME.equals(getString(R.string.day)))
            radioButton_Day.setChecked(true);
        else if(VALID_TIME.equals(getString(R.string.night)))
            radioButton_Night.setChecked(true);
        else { //if(REPEAT_CONTENT.equals(getString(R.string.customized)))
            radioButton_Customized.setChecked(true);
            textView_CustomizedDescp.setText(VALID_TIME);
        }
        String repeat_content = getIntent().getStringExtra(BetweenActivities.repeat_content);
        if(repeat_content !=null)
            REPEAT_CONTENT = repeat_content;
        textView_Repeat.setText(REPEAT_CONTENT);
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
                if(radioButton_AllDay.isChecked())
                    VALID_TIME = getString(R.string.all_day);
                else if(radioButton_Day.isChecked())
                    VALID_TIME = getString(R.string.day);
                else if(radioButton_Night.isChecked())
                    VALID_TIME = getString(R.string.night);
                else if(radioButton_Customized.isChecked())
                    VALID_TIME = textView_CustomizedDescp.getText().toString();
                Intent intent = new Intent(getApplicationContext(),ActivityAutomationSettings.class);
                intent.putExtra(BetweenActivities.for_what,"Add_ValidTime");
                intent.putExtra("valid_time",VALID_TIME);
                intent.putExtra(BetweenActivities.repeat_content,textView_Repeat.getText());
                startActivity(intent);
            }
        });

        radioButton_AllDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)VALID_TIME = getString(R.string.all_day);
            }
        });
        radioButton_Day.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)VALID_TIME = getString(R.string.day);
            }
        });
        radioButton_Night.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)VALID_TIME = getString(R.string.night);
            }
        });
        radioButton_Customized.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogForCustomizedTime();
            }
        });
        linearLayout_GoToRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityRepeatedSchedule.class);
                intent.putExtra(BetweenActivities.where_come_from,"ActivityValidTimePeriod.java");
                intent.putExtra(BetweenActivities.repeat_content,textView_Repeat.getText());
                startActivity(intent);
            }
        });
        ///Events:@}

    }//onCreate

    private void showDialogForCustomizedTime(){
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog_for_customizedtime, viewGroup, false);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
       final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        LinearLayout linearLayout_From            = (LinearLayout)dialogView.findViewById(R.id.linearLayout_From);
        LinearLayout linearLayout_To              = (LinearLayout)dialogView.findViewById(R.id.linearLayout_To);
        final TextView textView_ScheduleTime_From = (TextView)dialogView.findViewById(R.id.textView_ScheduleTime_From);
        final TextView textView_ScheduleTime_To   = (TextView)dialogView.findViewById(R.id.textView_ScheduleTime_To);
        final Button button_Confirm               = (Button)dialogView.findViewById(R.id.button_Confirm);
        Button button_Cancel                      = (Button)dialogView.findViewById(R.id.button_Cancel);

        String get_customized_time = textView_CustomizedDescp.getText().toString();
        if(get_customized_time.contains("-")){
            VALID_TIME = get_customized_time;
            String[] arr_M = get_customized_time.split("-");//main
            textView_ScheduleTime_From.setText(arr_M[0].trim());
            textView_ScheduleTime_To.setText(arr_M[1].trim());
        }else{
            textView_ScheduleTime_From.setText("00:00");
            textView_ScheduleTime_To.setText("23:59");
        }

        linearLayout_From.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: show data time picker for "From":
                String[] arr_F = textView_ScheduleTime_From.getText().toString().split(":");
                int h = Integer.parseInt(arr_F[0]);
                int m = Integer.parseInt(arr_F[1]);
                showTimePickerAndSet(textView_ScheduleTime_From,h,m);
            }
        });
        linearLayout_To.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo: show data time pciker for "To":
                String[] arr_T = textView_ScheduleTime_To.getText().toString().split(":");
                int h = Integer.parseInt(arr_T[0]);
                int m = Integer.parseInt(arr_T[1]);
                showTimePickerAndSet(textView_ScheduleTime_To,h,m);
            }
        });
        button_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String confirm_time = textView_ScheduleTime_From.getText() +" - " + textView_ScheduleTime_To.getText();
                        VALID_TIME = confirm_time;
                textView_CustomizedDescp.setText(VALID_TIME);
                alertDialog.dismiss();
            }
        });
        button_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }//showDailogForCustomizedTime()

    private void showTimePickerAndSet(final TextView textView,int hour,int minute){
       /* TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityValidTimePeriod.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String strselected_hour = "";
                String strselected_minute = "";
                if(selectedHour >=0 && selectedHour < 10)
                    strselected_hour = "0"+selectedHour;
                else
                    strselected_hour = ""+selectedHour;

                if(selectedMinute >=0 && selectedMinute < 10)
                    strselected_minute = "0"+selectedMinute;
                else
                    strselected_minute =""+selectedMinute;
                textView.setText(strselected_hour +":" +strselected_minute);
            }
        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
        timePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.ok), timePickerDialog);
        timePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.cancel), timePickerDialog);
        timePickerDialog.show();
        if(hour != -1 || minute !=-1)
            timePickerDialog.updateTime(hour,minute);

        */
       //fix:061120191335
        com.example.trident.common.TimePicker myTimePicker = new com.example.trident.common.TimePicker();
        myTimePicker.showTimePickerAndSet(ActivityValidTimePeriod.this,textView,hour,minute);
    }

    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivityAutomationSettings.class);
        intent.putExtra(BetweenActivities.for_what,"Add_ValidTime");
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
