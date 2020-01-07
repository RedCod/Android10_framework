package com.example.trident.smart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.MyLog;

public class ActivitySwitch extends AppCompatActivity {
    /**
     *called by "ActivitySmartSettings.java,Fragment_FragmentHome_RoomDevices.java"
     *"RecyclerViewAdapter_AutomationSettingsItem.java"
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
        //
        LinearLayout linearLayout_Back   = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back               = (Button)findViewById(R.id.button_Back);
        final RadioButton radioButton_ON = (RadioButton)findViewById(R.id.radioButton_ON);
        RadioButton radioButton_OFF      = (RadioButton)findViewById(R.id.radioButton_OFF);

        final int position = getIntent().getIntExtra(BetweenActivities.position,-1);

        MyLog.d("kerim[Switch]", "forWhat:" + getIntent().getStringExtra("for_what"));;
        MyLog.d("kerim[Switch]","itemName:" + getIntent().getStringExtra("item_name"));
        MyLog.d("kerim[Switch]","device_type:" + getIntent().getStringExtra(BetweenActivities.device_type));

        ///Event:@{
        linearLayout_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBack(position,null);
            }
        });
        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBack(position,null);
            }
        });
        radioButton_ON.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                goToWithValues(position,"Switch:ON");
            }
        });
        radioButton_OFF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                goToWithValues(position,"Switch:OFF");
            }
        });
        ///event:@}

    }//onCreate

    private void goToBack(int position,String selected_value){

        String upper_class     = getIntent().getStringExtra("upper_class");
        String where_come_from = getIntent().getStringExtra(BetweenActivities.where_come_from);
        String for_what        = getIntent().getStringExtra("for_what");
        //for debug:
        MyLog.d("kerim-switch_wherefrom:",""+where_come_from);
        MyLog.d("kerim-switch_upperclass:",""+upper_class);
        MyLog.d("kerim-switch-position:",""+position);

        Intent intent = null;
        //if(getIntent().getStringExtra("where_from") != null && getIntent().getStringExtra("where_from").equals("ActivitySmartSelectAction.java")){
        if(where_come_from !=null && where_come_from.equals("ActivitySmartSelectAction.java")){
            intent = new Intent(getApplicationContext(),ActivitySmartSelectAction.class);
            startActivity(intent);
        }else if(where_come_from != null && where_come_from.equals("ActivitySmartSettings.java")){
            intent = new Intent(getApplicationContext(), ActivitySmartSettings.class);
            intent.putExtra(BetweenActivities.for_what, "Edit_Switch");//let's consider it an "Edit"
            intent.putExtra(BetweenActivities.position, position);
            intent.putExtra(BetweenActivities.selected_value, selected_value);
            startActivity(intent);
        }else if(where_come_from != null && for_what.equals("Edit_Switch") && where_come_from.equals("ActivityAutomationSettings.java")){
            intent = new Intent(getApplicationContext(), ActivityAutomationSettings.class);
            intent.putExtra(BetweenActivities.for_what, "Edit_Switch");//let's consider it an "Edit"
            intent.putExtra(BetweenActivities.position, position);
            intent.putExtra(BetweenActivities.selected_value, selected_value);
        }else if(where_come_from !=null && for_what.equals("Edit_Condition") && where_come_from.equals("ActivityAutomationSettings.java")){
            intent = new Intent(getApplicationContext(), ActivityAutomationSettings.class);
            intent.putExtra(BetweenActivities.for_what, "Edit_Condition");//let's consider it an "Edit"
            intent.putExtra(BetweenActivities.position, position);
            intent.putExtra(BetweenActivities.selected_value, selected_value);
        }
        startActivity(intent);
    }

    private void goToWithValues(int position,String selected_value){
        String upper_class     = getIntent().getStringExtra("upper_class");
        String where_come_from = getIntent().getStringExtra(BetweenActivities.where_come_from);
        String for_what = getIntent().getStringExtra(BetweenActivities.for_what);
        //for debug:
        if(MyLog.DEGUB) {
            MyLog.d("kerim","switch-position:" + position);
            MyLog.d("kerim-switch_wherefrom:", "" + where_come_from);
            MyLog.d("kerim-switch_upperclass:", "" + upper_class);
            MyLog.d("kerim-switch-position:", "" + position);
            MyLog.d("kerim[Switch]","device_type:" + getIntent().getStringExtra(BetweenActivities.device_type));
        }
        String back_way ="";
        if(where_come_from.equals("ActivitySmartSelectAction.java"))
            back_way = upper_class;//bu durumda upper_class'a göndeceğiz.
        else
            back_way = where_come_from;


        if(back_way != null && back_way.equals("ActivitySmartSettings.java")){
            Intent intent = new Intent(getApplicationContext(), ActivitySmartSettings.class);
            if(position ==-1) {
                //for Add:
                intent.putExtra(BetweenActivities.for_what, "Add_Action");//let's consider it an "Edit"
                //intent.putExtra("position", position);//do not send.
                intent.putExtra(BetweenActivities.device_name, getIntent().getStringExtra(BetweenActivities.device_name));
                intent.putExtra(BetweenActivities.device_type, getIntent().getStringExtra(BetweenActivities.device_type));
                intent.putExtra(BetweenActivities.device_state, getIntent().getStringExtra(BetweenActivities.device_state));
                intent.putExtra(BetweenActivities.device_location, getIntent().getStringExtra(BetweenActivities.device_location));
                intent.putExtra(BetweenActivities.selected_value, selected_value);
            }else{
                //for Edit:
                intent.putExtra(BetweenActivities.for_what, "Edit_Switch");//let's consider it an "Edit"
                intent.putExtra(BetweenActivities.position, position);
                intent.putExtra(BetweenActivities.selected_value, selected_value);
            }
            startActivity(intent);
        }else if (back_way !=null && (for_what.equals("Add_Action") || for_what.equals("Edit_Switch")) && back_way.equals("ActivityAutomationSettings.java")){
            //we came here for "Add_Action or Edit_Switch" //Action add and Action Edit.
            Intent intent = new Intent(getApplicationContext(), ActivityAutomationSettings.class);
            if(position == -1){
                //for Add:
                intent.putExtra(BetweenActivities.for_what, "Add_Action");//let's consider it an "Edit"
                //intent.putExtra("position", position);//do not send.
                intent.putExtra(BetweenActivities.device_name, getIntent().getStringExtra(BetweenActivities.device_name));
                intent.putExtra(BetweenActivities.device_type, getIntent().getStringExtra(BetweenActivities.device_type));
                intent.putExtra(BetweenActivities.device_state, getIntent().getStringExtra(BetweenActivities.device_state));
                intent.putExtra(BetweenActivities.device_location, getIntent().getStringExtra(BetweenActivities.device_location));
                intent.putExtra(BetweenActivities.selected_value, selected_value);
            }else{
                //for Edit:
                intent.putExtra(BetweenActivities.for_what, "Edit_Switch");//let's consider it an "Edit"
                intent.putExtra(BetweenActivities.position, position);
                intent.putExtra(BetweenActivities.selected_value, selected_value);
            }
            startActivity(intent);
        }else if(back_way !=null && (for_what.equals("Add_Condition") || for_what.equals("Edit_Condition")) && (back_way.equals("ActivityAutomationSettings.java") || back_way.equals("ActivityAutomationSelectCondition.java"))){
            //FOR TEST 17Ekm 16:50 FOR condition device adding:
            Intent intent = new Intent(getApplicationContext(), ActivityAutomationSettings.class);
            if(position == -1){
                //for Add:
                intent.putExtra(BetweenActivities.for_what, "Add_Condition");
                //intent.putExtra("position", position);//do not send.
                intent.putExtra(BetweenActivities.item_name, "");
                intent.putExtra(BetweenActivities.current_city, getIntent().getStringExtra(BetweenActivities.device_name));
                intent.putExtra(BetweenActivities.selected_value, selected_value);
                //
                MyLog.d("kerim[ActivitySwitch]","Device Name:"+ getIntent().getStringExtra(BetweenActivities.device_name));
                MyLog.d("kerim[ActivitySwtich]","Device Type:" + getIntent().getStringExtra(BetweenActivities.device_type));
                MyLog.d("kerim[ActivitySwitch]", "Device State:" + getIntent().getStringExtra(BetweenActivities.device_state));
                MyLog.d("kerim[ActivitySwitch]","Device Loc:" + getIntent().getStringExtra(BetweenActivities.device_location));
                MyLog.d("kerim[ActivitySwithc]","Selected value:" + selected_value);
            }else{
                //for Edit:
                intent.putExtra(BetweenActivities.for_what, "Edit_Condition");//let's consider it an "Edit"
                intent.putExtra(BetweenActivities.position, position);
                intent.putExtra(BetweenActivities.item_name, "");
                intent.putExtra(BetweenActivities.current_city, getIntent().getStringExtra(BetweenActivities.device_name));
                intent.putExtra(BetweenActivities.selected_value, selected_value);
            }
            intent.putExtra(BetweenActivities.device_type, getIntent().getStringExtra(BetweenActivities.device_type));
            startActivity(intent);
        }else if(back_way !=null && back_way.equals("ActivityAutomationSettings.java")){

        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            goToBack(getIntent().getIntExtra(BetweenActivities.position,-1),null);
        }
        else
            return super.onKeyDown(keyCode, event);
        return false;
    }
}
