package com.example.trident.smart;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.MyLog;

import java.util.Calendar;

public class ActivityAddScheduleSub extends AppCompatActivity {
    /** "Program Ekle-Düzenle"
     *call from "ActivityAddSchedule.java" and "CustomListAdapter_AddScheduler.java"
     *
     */

    private static String WHICH_SITUATION = "null";
    TextView textView_SchedulePageTitle;
    TextView textView_ScheduleTime;
    TextView textView_DaysOfRepeat;
    TextView textView_Switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule_sub);
        //
        //linearLayout_Back
        //button_Back
        //textView_SchedulePageTitle
        //button_Save
        //textView_DaysOfRepeat
        //imageView_DaysOfRepeat_arrow
        //textView_Switch
        //imageView_Switch_arrow

        LinearLayout linearLayout_Back         = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                     = (Button)findViewById(R.id.button_Back);
        textView_SchedulePageTitle             = (TextView)findViewById(R.id.textView_SchedulePageTitle);
        Button button_Save                     = (Button)findViewById(R.id.button_Save);
        textView_ScheduleTime                  = (TextView)findViewById(R.id.textView_ScheduleTime);
        LinearLayout linearLayout_Repeated     = (LinearLayout)findViewById(R.id.linearLayout_Repeated);
        textView_DaysOfRepeat                  = (TextView)findViewById(R.id.textView_DaysOfRepeat);
        ImageView imageView_DaysOfRepeat_arrow = (ImageView)findViewById(R.id.imageView_DaysOfRepeat_arrow);
        LinearLayout linearLayout_Switch       = (LinearLayout)findViewById(R.id.linearLayout_Switch);
        textView_Switch                        = (TextView)findViewById(R.id.textView_Switch);
        ImageView imageView_Switch_arrow       = (ImageView)findViewById(R.id.imageView_Switch_arrow);


        ///////////////
        Intent intent = getIntent();
        String what_return = intent.getStringExtra("what_return");//just for this class
        if(what_return.equals("Add")){///this value get from "ActivityAddSchedule.java" //we come here from "ActivityAddSchedule.java" class.
            //if came to here for "Add Schedule":
            WHICH_SITUATION = "Add";
            textView_SchedulePageTitle.setText(R.string.add_schedule);
            //set default adjustment:
            Calendar calendarCurrenTime = Calendar.getInstance();
            textView_ScheduleTime.setText( editTime(calendarCurrenTime.get(Calendar.HOUR_OF_DAY),calendarCurrenTime.get(Calendar.MINUTE)));//set current time.
            textView_DaysOfRepeat.setText(getString(R.string.only_once));
            textView_Switch.setText("ON");
            //TODO:prepare this page for Add Schedule:
        }else if(what_return.equals("Edit")){///this value get from "ActivityAddSchedule.java" //we come here from "ActivityAddSchedule.java" class.
            //if came to here for "Edit Schedule":
            WHICH_SITUATION = "Edit";
            textView_SchedulePageTitle.setText(R.string.edit_schedule);
            //TODO:prepare this page for Edit Schedule:
            String clock          = intent.getStringExtra("clock");
            String repeat_content = intent.getStringExtra("repeat_content");
            String switch_on_of   = intent.getStringExtra("switch");
            textView_ScheduleTime.setText(clock);
            textView_DaysOfRepeat.setText(repeat_content);
            textView_Switch.setText(switch_on_of);
        }else if(what_return.equals("repeat")){//we come here from "ActivityRepeatedSchedule.java" class.
            String repeat_content = intent.getStringExtra("repeat_content");
            textView_SchedulePageTitle.setText(TITLE);
            textView_ScheduleTime.setText(TIME);
            textView_DaysOfRepeat.setText(repeat_content);
            textView_Switch.setText(SWITCH);
        }
        //////////////////////////////////////////////////////////////////////////////////////////////

///GO TO BACK:@{
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
///GO TO BACK:@}

        button_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: save changes to database. !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //...todo
                //and finally go to back:
                goToBack();
            }
        });
        textView_ScheduleTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog  timePickerDialog = new TimePickerDialog(ActivityAddScheduleSub.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        textView_ScheduleTime.setText(editTime(selectedHour,selectedMinute));
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
                timePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.ok), timePickerDialog);
                timePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getString(R.string.cancel), timePickerDialog);
                timePickerDialog.show();
            }//onClick
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

///Show "ON-OFF" dialog:@{
        linearLayout_Switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSwitchDialog();
            }
        });
        textView_Switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:show "ON-OFF" dialog:
                showSwitchDialog();
            }
        });
        imageView_Switch_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:show "ON-OFF" dialog:
                showSwitchDialog();
            }
        });
///Show "ON-OFF" dialog:@}

    }//onCreate

    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivityAddSchedule.class);
        intent.putExtra(BetweenActivities.where_come_from,"ActivityAddScheduleSub.java");
        startActivity(intent);
    }

    //when we come back from "ActivityRepeatedSchedule.java",we will need:
    private static String TITLE  = "";
    private static String TIME   = "";
    private static String REPEAT = "";
    private static String SWITCH = "";
    private void goToRepeatedActivity(){
        TITLE  = textView_SchedulePageTitle.getText().toString();
        TIME   = textView_ScheduleTime.getText().toString();
        REPEAT = textView_DaysOfRepeat.getText().toString();
        SWITCH = textView_Switch.getText().toString();
        Intent intent = new Intent(getApplicationContext(),ActivityRepeatedSchedule.class);
        intent.putExtra(BetweenActivities.where_come_from,"ActivityAddScheduleSub.java");
        intent.putExtra("repeat_content",REPEAT);
        startActivity(intent);
    }

    private String editTime(int selected_hour,int selected_minute){
        String strselected_hour = "";
        String strselected_minute = "";
        if(selected_hour >=0 && selected_hour < 10)
            strselected_hour = "0"+selected_hour;
        else
            strselected_hour = ""+selected_hour;

        if(selected_minute >=0 && selected_minute < 10)
            strselected_minute = "0"+selected_minute;
        else
            strselected_minute =""+selected_minute;

        return strselected_hour + ":" + strselected_minute;
    }

    /**
     * Show Switch dialog for choose   "ON" or "OFF".
     */
    private void showSwitchDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ActivityAddScheduleSub.this);
        //dialogBuilder.setTitle("Bu üyeyi kaldırmak istediğinizden emin misiniz?");
        //TextView Above(custom title):@{
        final TextView textView_Above = new TextView(getApplicationContext());
        textView_Above.setText("Switch");
        textView_Above.setTextColor(Color.BLACK);
        textView_Above.setPadding(50,35,0,40);
        //textView_Above.setGravity(Gravity.CENTER);
        textView_Above.setTypeface(textView_Above.getTypeface(), Typeface.BOLD);
        textView_Above.setTextSize(17);
        dialogBuilder.setCustomTitle(textView_Above);
        //@}
        RadioGroup radioGroup = new RadioGroup(getApplicationContext());
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        radioGroup.setPadding(40,0,0,0);
        dialogBuilder.setView(radioGroup);
        //RadioButton ON:@{
        final RadioButton radioButton_ON = new RadioButton(getApplicationContext());
        radioButton_ON.setText("ON");
        radioButton_ON.setTextColor(getResources().getColor(R.color.color_Black));
        radioButton_ON.setTextSize(18);
        radioButton_ON.setHeight(120);
        radioButton_ON.setButtonTintList(new ColorStateList(new int[][] {new int[]{android.R.attr.state_enabled}},new int[] {getResources().getColor(R.color.color_my_theme)}));
        radioGroup.addView(radioButton_ON);
        //@}
        //RadioButton OFF:@{
        final RadioButton radioButton_OFF = new RadioButton(getApplicationContext());
        radioButton_OFF.setText("OFF");
        radioButton_OFF.setTextColor(getResources().getColor(R.color.color_Black));
        radioButton_OFF.setTextSize(18);
        radioButton_OFF.setButtonTintList(new ColorStateList(new int[][] {new int[]{android.R.attr.state_enabled}},new int[] {getResources().getColor(R.color.color_my_theme)}));
        radioGroup.addView(radioButton_OFF);
        //@}
        if(WHICH_SITUATION.equals("Add")){
            radioButton_ON.setChecked(true);//default
        }else if(WHICH_SITUATION.equals("Edit")){
            if(textView_Switch.getText().equals("ON"))
                radioButton_ON.setChecked(true);
               else
                radioButton_OFF.setChecked(true);
        }
        radioButton_ON.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                radioButton_OFF.setChecked(!b);
            }
        });
        radioButton_OFF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                radioButton_ON.setChecked(!b);
            }
        });


        dialogBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                 //todo: apply switch:
                if(radioButton_ON.isChecked()) {
                    textView_Switch.setText("ON");
                    return;
                }
                if(radioButton_OFF.isChecked()) {
                    textView_Switch.setText("OFF");
                    //return;
                }
            }
        });
        dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass. do nothing
            }
        });
        dialogBuilder.create().show();
    }///

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
