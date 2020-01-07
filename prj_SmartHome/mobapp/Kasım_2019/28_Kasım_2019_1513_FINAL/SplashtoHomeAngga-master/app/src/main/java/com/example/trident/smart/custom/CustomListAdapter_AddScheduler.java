package com.example.trident.smart.custom;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.MyLog;
import com.example.trident.smart.ActivityAddScheduleSub;
import com.example.trident.smart.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomListAdapter_AddScheduler extends ArrayAdapter<String> {
    /**
     * this class using by ListView in ActivityAddScheduler.java class.
     */

    private final Activity context;
    private String[] arr_schedule_time;
    private String[] arr_schedule_day;
    private String[] arr_schedule_swith;
    /*TextView textView_Item;
    TextView textView_ItemDescribtion;
    TextView textView_ItemDescribtion2;*/

    TextView[] textViews_Item;
    TextView[] textViews_ItemDescribtion;
    TextView[] textViews_ItemDescribtion2;
    public CustomListAdapter_AddScheduler(Activity context, String[] arr_schedule_time,String[] arr_schedule_day,String[] arr_schedule_swith) {
        super(context, R.layout.listview_addschedule, arr_schedule_time);

        this.context            = context;
        this.arr_schedule_time  = arr_schedule_time;
        this.arr_schedule_day   = arr_schedule_day;
        this.arr_schedule_swith = arr_schedule_swith;

        ///
        int number_of_controls_to_be_created = arr_schedule_time.length;
        textViews_Item             = new TextView[number_of_controls_to_be_created];
        textViews_ItemDescribtion  = new TextView[number_of_controls_to_be_created];
        textViews_ItemDescribtion2 = new TextView[number_of_controls_to_be_created];
    }



    public View getView(final int position,View view,ViewGroup parent) {
        //textView_Item
        //textView_ItemDescribtion
        //textView_ItemDescribtion2
        //switchButton_ONorOFF

        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.listview_addschedule, null,true);
        LinearLayout     linearLayout_Item = (LinearLayout)rowView.findViewById(R.id.linearLayout_Item);
        TextView textView_Item             = (TextView)rowView.findViewById(R.id.textView_Item);
        TextView textView_ItemDescribtion  = (TextView)rowView.findViewById(R.id.textView_ItemDescribtion);
        TextView textView_ItemDescribtion2 = (TextView)rowView.findViewById(R.id.textView_ItemDescribtion2);
        final Switch switchButton_ONorOFF  = (Switch)rowView.findViewById(R.id.switchButton_ONorOFF);

        textView_Item.setText(arr_schedule_time[position]);
        textView_ItemDescribtion.setText(arr_schedule_day[position]);
        textView_ItemDescribtion2.setText("Switch:" +arr_schedule_swith[position]);

        textViews_Item[position]             = textView_Item;
        textViews_ItemDescribtion[position]  = textView_ItemDescribtion;
        textViews_ItemDescribtion2[position] = textView_ItemDescribtion2;

        if(arr_schedule_swith[position].equals("ON")) {
            switchButton_ONorOFF.setChecked(true);
            setControlsEnabled(position,true);
        }else {
            switchButton_ONorOFF.setChecked(false);
            setControlsEnabled(position,false);
        }
        switchButton_ONorOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context.getApplicationContext(),"switch:"+switchButton_ONorOFF.isChecked(),Toast.LENGTH_SHORT).show();
                //set TextView controls enabled true or false.

                setControlsEnabled(position,switchButton_ONorOFF.isChecked());
                //
                ///TODO:make the "schedule" active or inactive from the database:
                //....
            }
        });

        linearLayout_Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context.getApplicationContext(),"click:"+arr_schedule_time[position],Toast.LENGTH_SHORT).show();
                //TODO: go to "Edit Schedule" page.

                //TODO:if we go from this class, change page title "Edit Shecudle".
                String clock   = arr_schedule_time[position];
                String repeat  = arr_schedule_day[position];
                String switch_on_off = arr_schedule_swith[position];

                Intent intent = new Intent(context.getApplicationContext(), ActivityAddScheduleSub.class);
                intent.putExtra("what_return","Edit");//for preparing "ActivityAddScheduleSub.java" page.
               /* intent.putExtra("clock","13 20");
                intent.putExtra("repeat","Only once");
                intent.putExtra("switch","ON");*/
                intent.putExtra("clock",clock);
                intent.putExtra("repeat_content",repeat);
                intent.putExtra("switch",switch_on_off);
                context.startActivity(intent);
            }
        });
        //linearLayout_Item.setLongClickable(true);
        linearLayout_Item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //TODO: show delete item dialog:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage(context.getString(R.string.delete_item));
                alertDialogBuilder.setPositiveButton(context.getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(context.getApplicationContext(),"You clicked yes    button",Toast.LENGTH_LONG).show();
                                /**
                                 * TODO:Delete "schedule" item from database.
                                 * TODO: ID values for items(delete items) are assigned here from ActivityAddSchedule.java(because get from database) class. And here the selected item is deleted "where=ID" value.
                                 */
                            }
                        });
                 alertDialogBuilder.setNegativeButton(context.getString(R.string.cancel),new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing.
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setAllCaps(false);
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setAllCaps(false);

                Toast.makeText(context.getApplicationContext(),"long:"+arr_schedule_time[position],Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return rowView;
    };

    private void setControlsEnabled(int _position,boolean _state){
        if(_state == true)
            textViews_Item[_position].setTextColor(Color.BLACK);
        else
            textViews_Item[_position].setTextColor(context.getResources().getColor(R.color.color_White2));

        textViews_Item[_position].setEnabled(_state);
        textViews_ItemDescribtion[_position].setEnabled(_state);
        textViews_ItemDescribtion2[_position].setEnabled(_state);
    }

}
