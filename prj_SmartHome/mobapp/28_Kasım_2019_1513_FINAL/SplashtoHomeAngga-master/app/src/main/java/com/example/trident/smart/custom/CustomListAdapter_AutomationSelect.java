package com.example.trident.smart.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trident.common.MyLog;
import com.example.trident.smart.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomListAdapter_AutomationSelect extends BaseAdapter {///used by "ActivityAutomationSelect.java"

    /**
     * FIXME:if the problem persists = https://www.itworld.com/article/2705632/how-to-make-smooth-scrolling-listviews-in-android.html
     *
     * TODO: !!!!!!!!!!!! ATTENTION !!!!!!!!!!!!!!!!!!!
     * todo: The function of this page should be completed according to the values from the database.
     *
     *
     */

    private LayoutInflater inflter;
    private Context context;
    private String[] itemname; //todo: get these value get from database.
    private boolean[] checked;//todo: get these value get from database.
    boolean first_running = true;

    public CustomListAdapter_AutomationSelect(Context context,String[] itemname){
        inflter = (LayoutInflater.from(context));
        this.context  = context;
        this.itemname = itemname;
        checked = new boolean[itemname.length];

        //todo:get these value get from database.
        for(int i=0;i<itemname.length;i++) {//represented values.
            if (i % 2 == 0)
                checked[i] = true;
            else
                checked[i] = false; //todo:fixme: this represent value.  //get from database.

            hashMap_SelectedItem.put(itemname[i],checked[i]);
        }
    }

    @Override
    public int getCount() {
        return itemname.length;
    }

    @Override
    public Object getItem(int i) {
        return itemname[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    ArrayList<Button> arrayList_ButtonEnable = new ArrayList<Button>();
    ArrayList<Button> arrayList_ButtonDisable = new ArrayList<Button>();
    HashMap<String,Boolean> hashMap_SelectedItem = new HashMap<String,Boolean>();
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.listview_automation_select, null);
       final CheckBox checkBox_SelectAutomation = (CheckBox)view.findViewById(R.id.checkBox_SelectAutomation);
        LinearLayout linearLayout_ScenarioItem  = (LinearLayout) view.findViewById(R.id.linearLayout_ScenarioItem);
        TextView textView_ScenarioName          = (TextView) view.findViewById(R.id.textView_ScenarioName);
       final Button button_TriggerEnable        = (Button) view.findViewById(R.id.button_TriggerEnable);
       final Button button_TriggerDisable       = (Button) view.findViewById(R.id.button_TriggerDisable);


        if(arrayList_ButtonEnable.size() < itemname.length) {
            arrayList_ButtonEnable.add(button_TriggerEnable);
            arrayList_ButtonDisable.add(button_TriggerDisable);
        }else {
            arrayList_ButtonEnable.set(position, button_TriggerEnable);
            arrayList_ButtonDisable.set(position,button_TriggerDisable);
        }
        if(checked[position]) {
            checkBox_SelectAutomation.setChecked(true);
            showButtons(arrayList_ButtonEnable.get(position),arrayList_ButtonDisable.get(position));
            setButtonTriggerEnable_Green(arrayList_ButtonEnable.get(position),arrayList_ButtonDisable.get(position));
        }else {
            checkBox_SelectAutomation.setChecked(false);
            hideButtons(arrayList_ButtonEnable.get(position),arrayList_ButtonDisable.get(position));
            //setAllButton_Gray(arrayList_ButtonEnable.get(position),arrayList_ButtonDisable.get(position);
        }
        textView_ScenarioName.setText(itemname[position]);

        ///Event:@{
        checkBox_SelectAutomation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check) {
                    checked[position] = true;
                    showButtons(arrayList_ButtonEnable.get(position),arrayList_ButtonDisable.get(position));
                    button_TriggerEnable.setBackgroundColor(context.getResources().getColor(R.color.color_green2));
                    button_TriggerEnable.setTextColor(context.getResources().getColor(R.color.color_White));
                    hashMap_SelectedItem.put(itemname[position],true);
                }else {
                    checked[position] = false;
                    button_TriggerEnable.setBackgroundColor(context.getResources().getColor(R.color.color_Gray2));
                    button_TriggerEnable.setTextColor(context.getResources().getColor(R.color.color_Gray));
                    hideButtons(arrayList_ButtonEnable.get(position),arrayList_ButtonDisable.get(position));
                    hashMap_SelectedItem.put(itemname[position],false);
                }
            }
        });
        linearLayout_ScenarioItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox_SelectAutomation.setChecked(true);
                showButtons(arrayList_ButtonEnable.get(position),arrayList_ButtonDisable.get(position));
            }
        });
        button_TriggerEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: make activate setting for Trigger Enable
                //activate this and then deactive the other settings(TriggerDisable)
                setButtonTriggerEnable_Green(arrayList_ButtonEnable.get(position),arrayList_ButtonDisable.get(position));
            }
        });
        button_TriggerDisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: make activate setting for Trigger Disable.
                //activate this and then deactive the other settings(TriggerEnable)
            }
        });
        ///event:@}

       return view;
    }////used by "ActivityAutomationSelect.java"


    public HashMap getHashMap_SelectedItem(){
        return  hashMap_SelectedItem;
    }

    private void setButtonTriggerEnable_Green(Button btnTriggerEnable_green,Button btnTriggerDisable_gray){

        btnTriggerEnable_green.setBackgroundColor(context.getResources().getColor(R.color.color_green2));
        btnTriggerEnable_green.setTextColor(context.getResources().getColor(R.color.color_White));
        //changed button Disable:
        btnTriggerDisable_gray.setBackgroundColor(context.getResources().getColor(R.color.color_Gray2));
        btnTriggerDisable_gray.setTextColor(context.getResources().getColor(R.color.color_Gray));
    }
    private void setButtonTriggerDisable_Green(Button button_TriggerDisable_gray,Button buttonTriggerEnable_green){
        button_TriggerDisable_gray.setBackgroundColor(context.getResources().getColor(R.color.color_green2));
        button_TriggerDisable_gray.setTextColor(context.getResources().getColor(R.color.color_White));
        //changed button Enable:
        buttonTriggerEnable_green.setBackgroundColor(context.getResources().getColor(R.color.color_Gray2));
        buttonTriggerEnable_green.setTextColor(context.getResources().getColor(R.color.color_Gray));
    }
    private void setAllButton_Gray(Button buttonTriggerEnable_green,Button button_TriggerDisable_green){
        buttonTriggerEnable_green.setBackgroundColor(context.getResources().getColor(R.color.color_Gray2));
        buttonTriggerEnable_green.setTextColor(context.getResources().getColor(R.color.color_Gray));
        button_TriggerDisable_green.setBackgroundColor(context.getResources().getColor(R.color.color_Gray2));
        button_TriggerDisable_green.setTextColor(context.getResources().getColor(R.color.color_Gray));
    }
    private void showButtons(Button btnTriggerEnable,Button btnTriggerDisable){
        btnTriggerEnable.setVisibility(View.VISIBLE);
        btnTriggerDisable.setVisibility(View.VISIBLE);
    }
    private void hideButtons(Button btnTriggerEnable,Button btnTriggerDisable){
        btnTriggerEnable.setVisibility(View.GONE);
        btnTriggerDisable.setVisibility(View.GONE);
    }
}
