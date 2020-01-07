package com.example.trident.smart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.trident.common.BetweenActivities;
import com.example.trident.common.Keyboard;
import com.example.trident.common.MyLog;


public class ActivityTimeLapse extends AppCompatActivity {
    /**
     * used by "RecyclerViewAdapter_AutomationSettingsItem.java"
     *
     */

   // private static int position = 0;
    EditText editText_Minute;
    EditText editText_Second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_lapse);
        //
       // position = getIntent().getIntExtra("position",-1);
        //
        LinearLayout linearLayout_Back = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back = (Button)findViewById(R.id.button_Back);
        Button button_OK   = (Button)findViewById(R.id.button_OK);
        editText_Minute = (EditText)findViewById(R.id.editText_Minute);
        editText_Second = (EditText)findViewById(R.id.editText_Second);

        //Default:
        Keyboard.getInstance(getApplicationContext()).showKeyboard();
        String get_selected_item_time_value = getIntent().getStringExtra(BetweenActivities.selected_value);//get from "RecyclerViewAdapter_AutomationSettingsItem.java"
        if(get_selected_item_time_value != null){
            //came here for editing:
            String[] str_v = get_selected_item_time_value.split(":");
            editText_Minute.setText(addZeroPer(str_v[0]));
            editText_Second.setText(addZeroPer(str_v[1]));
        }

        ///Event:@{
        linearLayout_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //goToBack(null);
                goToBack();
            }
        });
        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //goToBack(null);
                goToBack();
            }
        });
        button_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String minute = editText_Minute.getText().toString();
                String second = editText_Second.getText().toString();

                if(minute.length() < 1)
                    minute = "00";

                if(second.length() < 1
                        || ((second.equals("0") || second.equals("00") && (minute.equals("0") || minute.equals("00"))))

                ) {
                    Toast.makeText(getApplicationContext(), getString(R.string.msg_delaytime), Toast.LENGTH_SHORT).show();
                    return;
                }

                String selected_value = minute +":" +second;

                goToBackWithValues(selected_value);
            }
        });
        editText_Minute.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0) {
                    int int_value = Integer.parseInt(charSequence.toString());
                    if (int_value > 59) {
                        editText_Minute.setText("59");
                        editText_Minute.setSelection(editText_Minute.length());
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        editText_Second.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0) {
                    int int_value = Integer.parseInt(charSequence.toString());
                    if (int_value > 59) {
                        editText_Second.setText("59");
                        editText_Second.setSelection(editText_Second.length());
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        editText_Minute.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
               if(!focus)
                   editText_Minute.setText(addZeroPer(editText_Minute.getText().toString()));
               else
                   editText_Minute.setSelection(editText_Minute.length());
            }
        });

        editText_Second.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if(!focus)
                    editText_Second.setText(addZeroPer(editText_Second.getText().toString()));
                else
                    editText_Second.setSelection(editText_Second.length());
            }
        });
        ///event:@}
    }//onCreate

    private String addZeroPer(String content){
        if(content.length() < 1)
            content = "0";
        int int_value = Integer.parseInt(content);
        if(int_value < 10)
            content = "0" +int_value;
        return  content;
    }

    /**
     * using with button_OK.
     * @param selected_value
     */
    private void goToBackWithValues(String selected_value){
        Keyboard.getInstance(getApplicationContext()).hideKeyboard();
        String upper_class = getIntent().getStringExtra("upper_class");//can be one of them:"ActivitySmartSettings.java,ActivityAutomationSettings.java,
        String where_from = getIntent().getStringExtra("where_come_from");

        if(MyLog.DEGUB) {
            MyLog.d("kerim-timelapse_wherefrom:", "" + where_from);
            MyLog.d("kerim-timelapse_upperclass:", "" + upper_class);
            MyLog.d("kerim-timelapse-position:", "" + getIntent().getIntExtra("position", -1));
        }
        String back_way ="";
        if(where_from.equals("ActivitySmartSelectAction.java"))
            back_way = upper_class;//bu durumda upper_class'a göndeceğiz.
        else
            back_way = where_from;

        if(MyLog.DEGUB)MyLog.d("kerim","way_back:" + back_way);

        if(back_way !=null && back_way.equals("ActivitySmartSettings.java")) {
            Intent intent = new Intent(getApplicationContext(), ActivitySmartSettings.class);
            int position = getIntent().getIntExtra("position", -1);
            if(position == -1){
                //for add:
                intent.putExtra("for_what", "Add_Action_TimeLapse");//let's consider it an "Add_Action"
                //intent.putExtra("position", getIntent().getIntExtra("position", -1));//do not send.
                intent.putExtra("selected_value", selected_value);
            }else {
                //for edit:
                intent.putExtra("for_what", "Edit_TimeLapse");//let's consider it an "Edit"
                intent.putExtra("position", position);
                intent.putExtra("selected_value", selected_value);
            }
            startActivity(intent);
        }else if(back_way !=null && back_way.equals("ActivityAutomationSettings.java")) {
            Intent intent = new Intent(getApplicationContext(), ActivityAutomationSettings.class);
            int position = getIntent().getIntExtra("position", -1);
            if(position == -1){
                //for Add:
                intent.putExtra("for_what", "Add_Action_TimeLapse");//let's consider it an "Edit"
                //intent.putExtra("position", position);//do not send.
                intent.putExtra("selected_value", selected_value);
            }else{
                //for Edit:
                intent.putExtra("for_what", "Edit_TimeLapse");//let's consider it an "Edit"
                intent.putExtra("position", position);
                intent.putExtra("selected_value", selected_value);
            }
            startActivity(intent);
        }else
        if(MyLog.DEGUB)MyLog.d("kerim","ELSE(time lapse)");


       /* if(where_from !=null && where_from.equals("ActivitySmartSelectAction.java")){
            Intent intent = new Intent(getApplicationContext(), ActivitySmartSettings.class);
            intent.putExtra("for_what", "Add_Action_TimeLapse");//let's consider it an "Add_Action"
            //intent.putExtra("position", getIntent().getIntExtra("position", -1));
            intent.putExtra("selected_value", selected_value);
            startActivity(intent);
        }else if(where_from !=null && where_from.equals("ActivitySmartSettings.java")) {
            Intent intent = new Intent(getApplicationContext(), ActivitySmartSettings.class);
            intent.putExtra("for_what", "Edit_TimeLapse");//let's consider it an "Edit"
            intent.putExtra("position", getIntent().getIntExtra("position", -1));
            intent.putExtra("selected_value", selected_value);
            startActivity(intent);
        }else if(where_from !=null && where_from.equals("ActivityAutomationSettings.java")){
            Intent intent = new Intent(getApplicationContext(), ActivityAutomationSettings.class);
            intent.putExtra("for_what", "Edit_TimeLapse");//let's consider it an "Edit"
            intent.putExtra("position", getIntent().getIntExtra("position", -1));
            intent.putExtra("selected_value", selected_value);
            startActivity(intent);
        }*/
    }

    private void goToBack(){
        Keyboard.getInstance(getApplicationContext()).hideKeyboard();
        String where_from = getIntent().getStringExtra("where_come_from");
        if(where_from != null && where_from.equals("ActivitySmartSelectAction.java")){
            Intent intent = new Intent(getApplicationContext(),ActivitySmartSelectAction.class);
            startActivity(intent);
        }else if(where_from !=null && where_from.equals("ActivitySmartSettings.java")) {
            Intent intent = new Intent(getApplicationContext(), ActivitySmartSettings.class);
            intent.putExtra("for_what", "Edit_TimeLapse");//let's consider it an "Edit"
            intent.putExtra("position", getIntent().getIntExtra("position", -1));
            //intent.putExtra("selected_value", );
            startActivity(intent);
        }else if(where_from !=null && where_from.equals("ActivityAutomationSettings.java")) {
            Intent intent = new Intent(getApplicationContext(), ActivityAutomationSettings.class);
            intent.putExtra("for_what", "Edit_TimeLapse");//let's consider it an "Edit"
            intent.putExtra("position", getIntent().getIntExtra("position", -1));
            startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            //goToBack(null);
            goToBack();
        }
        else
            return super.onKeyDown(keyCode, event);
        return false;
    }
}
