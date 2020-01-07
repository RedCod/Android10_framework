package com.example.trident.smart;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.MyLog;
import com.example.trident.common.TimePicker;
import com.example.trident.smart.custom.CustomListAdapter_DoNotDisturbDevices;

import java.util.List;

public class ActivityTimeSegment extends AppCompatActivity {

    /** using by "ActivityMessageCenterSettings.java"
     *  "Zaman Bölümü(Segmenti) Ekle.
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_segment);
        //
        LinearLayout linearLayout_Back                        = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                                    = (Button)findViewById(R.id.button_Back);
        Button button_Save                                    = (Button)findViewById(R.id.button_Save);
        LinearLayout linearLayout_From                        = (LinearLayout)findViewById(R.id.linearLayout_From);
        final TextView textView_ScheduleTime_From             = (TextView)findViewById(R.id.textView_ScheduleTime_From);
        LinearLayout linearLayout_To                          = (LinearLayout)findViewById(R.id.linearLayout_To);
        final TextView textView_ScheduleTime_To               = (TextView)findViewById(R.id.textView_ScheduleTime_To);
        LinearLayout linearLayout_GoToRepeat                  = (LinearLayout)findViewById(R.id.linearLayout_GoToRepeat);
        final TextView textView_Repeat                        = (TextView)findViewById(R.id.textView_Repeat);
        LinearLayout linearLayout_GoToDoNotDisturbDevicesPage = (LinearLayout)findViewById(R.id.linearLayout_GoToDoNotDisturbDevicesPage);


        ///Default:@{
        String r_content = getIntent().getStringExtra(BetweenActivities.repeat_content);//get from "ActivityRepeatedSchedule.java"
        if(r_content != null)
            textView_Repeat.setText(r_content);

        /** !!!!!!!!!!!!!!!!!!!!
         * GET:
         * -selected_value
         * -repeat
         *
         */
        String slct_value = getIntent().getStringExtra(BetweenActivities.selected_value);//get from "ActivityMessageCenterSettings.java"
        if(slct_value != null){
            String[] splitTime = slct_value.split("-");
            textView_ScheduleTime_From.setText(splitTime[0].trim());
            textView_ScheduleTime_To.setText(splitTime[1].trim());
        }


        ///Default:@}

        ///Events:@[
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
        button_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**TODO:
                 * -Eğer herhangi bir cihaz seçilmemişse "Bir DND cihazı eklemelisiniz." uyarısı verilmeli ve işlem durdurulmalıdır.
                 * Mesaj: Toast.makeText(getApplicationContext(),getString(R.string.msg_timesegment),Toast.LENGTH_SHORT).show();
                 */
                CustomListAdapter_DoNotDisturbDevices customListAdapterDoNotDisturbDevices = new CustomListAdapter_DoNotDisturbDevices();
                List<String> listDeviceName = customListAdapterDoNotDisturbDevices.getListItemContent();
                List<Boolean> listDeviceState = customListAdapterDoNotDisturbDevices.getListStateContent();
                boolean have_selected_device = false;
                for(int i =0; i<listDeviceName.size();i++){
                    if(MyLog.DEGUB)MyLog.d("kerim","item:" + listDeviceName.get(i) +",state:" + listDeviceState.get(i));
                    if(listDeviceState.get(i) == true){
                        have_selected_device = true;
                        if(MyLog.DEGUB)MyLog.d("kerim","SEÇİLEN CİHAZ VAR.");
                        break;
                    }
                }

                if(have_selected_device == false){
                    Toast.makeText(getApplicationContext(),getString(R.string.msg_timesegment),Toast.LENGTH_SHORT).show();
                    return;
                }
                //and go back:
                Toast.makeText(getApplicationContext(),"Zaman bölümü eklendi.",Toast.LENGTH_SHORT).show();
                goToBack();
            }
        });
        linearLayout_From.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: show time dialog,for "From" value select.
                //and then set:
                TimePicker myTimePicker = new TimePicker();
                String[] str = textView_ScheduleTime_From.getText().toString().split(":");
                int h = Integer.parseInt(str[0]);
                int m = Integer.parseInt(str[1]);
                myTimePicker.showTimePickerAndSet(ActivityTimeSegment.this,textView_ScheduleTime_From,h,m);
            }
        });
        linearLayout_To.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo:show time dialog,for "To" value select.
                //and then set:
                TimePicker myTimePicker = new TimePicker();
                String[] str = textView_ScheduleTime_To.getText().toString().split(":");
                int h = Integer.parseInt(str[0]);
                int m = Integer.parseInt(str[1]);
                myTimePicker.showTimePickerAndSet(ActivityTimeSegment.this,textView_ScheduleTime_To,h,m);
            }
        });
        linearLayout_GoToRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to repeat page.
                //and then set result:
                Intent intent = new Intent(getApplicationContext(),ActivityRepeatedSchedule.class);
                intent.putExtra(BetweenActivities.where_come_from,"ActivityTimeSegment.java");
                intent.putExtra(BetweenActivities.repeat_content,textView_Repeat.getText());
                startActivity(intent);
            }
        });
        linearLayout_GoToDoNotDisturbDevicesPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to "Do Not Disturb Devices" Page:
              Intent intent = new Intent(getApplicationContext(),ActivityDoNotDisturbDevices.class);
              startActivity(intent);
            }
        });
        ///events:@}


    }//onCreate


    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivityMessageCenterSettings.class);
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
