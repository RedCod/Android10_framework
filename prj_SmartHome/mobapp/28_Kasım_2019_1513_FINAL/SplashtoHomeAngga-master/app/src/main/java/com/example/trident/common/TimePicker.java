package com.example.trident.common;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TextView;
import com.example.trident.smart.R;

import java.util.Calendar;

public class TimePicker {


    /**
     * TimePicker görütülemek için.
     *
     * used by:
     * -ActivityTimeSegment.java
     * -ActivityValidTimePeriod.java
     * -ActivitySchedule.java
     *
     * Exp for using:
      TimePicker myTimePicker = new TimePicker();
      myTimePicker.showTimePickerAndSet(ActivityTimeSegment.this,textView_ScheduleTime_From,5,15);
     */
    public TimePicker(){

    }

    /**
     *
     * @param context  send exp:"ActivityTimeSegment.this"
     * @param textView textView reference to set the value to
     * @param hour    exp: Like 3,5,12 note: do not put zero(0) value to head
     * @param minute  exp: 20
     */
    public void showTimePickerAndSet(Context context ,final TextView textView, int hour, int minute){
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(android.widget.TimePicker timePicker, int selectedHour, int selectedMinute) {
                textView.setText(prepareHour(selectedHour) +":" + prepareMinute(selectedMinute));
            }
        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
        timePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, context.getString(R.string.ok), timePickerDialog);
        timePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, context.getString(R.string.cancel), timePickerDialog);
        timePickerDialog.show();
        if(hour != -1 || minute !=-1)
            timePickerDialog.updateTime(hour,minute);
    }


    /**
     * @param context send exp:"ActivityTimeSegment.this"
     * @param textView textView reference to set the value to.
     */
    public void showTimePickerAndSet(Context context,final TextView textView){
       /* String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        MyLog.d("kerim[TimePicker]","currentTime:" + currentTime);
        String[] arr_currentTime = currentTime.split(":");//split time value.
        int hour = Integer.parseInt(arr_currentTime[0]);//hour
        int minute = Integer.parseInt(arr_currentTime[1]);
        */
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(android.widget.TimePicker timePicker, int selectedHour, int selectedMinute) {
                textView.setText(prepareHour(selectedHour) +":" + prepareMinute(selectedMinute));
            }
        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
        timePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, context.getString(R.string.ok), timePickerDialog);
        timePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, context.getString(R.string.cancel), timePickerDialog);
        timePickerDialog.show();
        //if(hour != -1 || minute !=-1)
          //  timePickerDialog.updateTime(hour,minute);
    }

    /**
     * Arranges the value of int by String
     * @param h hour
     * @return  02,12,..
     */
    public String prepareHour(int h){
        String str_h = "";
        if(h >=0 && h < 10)
            str_h = "0"+h;
        else
            str_h = ""+h;
        return str_h;
    }

    /**
     * Arranges the value of int by String
     * @param m 50,59,20,00,..
     * @return
     */
    public String prepareMinute(int m){
        String str_m ="";
        if(m >=0 && m < 10)
            str_m = "0"+m;
        else
            str_m =""+m;
        return str_m;
    }

}
