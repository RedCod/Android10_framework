package com.example.trident.smart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.trident.common.BetweenActivities;

import java.util.List;

public class ActivityTimeZone extends AppCompatActivity {
    /**
     * "Saat Dilimi"
     * used by "ActivityPersonalCenter.java"
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_zone);
        //
        LinearLayout linearLayout_Back = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back  =(Button)findViewById(R.id.button_Back);
        EditText editText_Search = (EditText)findViewById(R.id.editText_Search);
        ListView listView_TimeZone = (ListView)findViewById(R.id.listView_TimeZone);
        ///Default:
        final ArrayAdapter<CharSequence> arrayAdapter= ArrayAdapter.createFromResource(this,R.array.time_zone_arr,android.R.layout.simple_list_item_1);
        listView_TimeZone.setAdapter(arrayAdapter);

        ///Events:@{
        linearLayout_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBack(null);
            }
        });
        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBack(null);
            }
        });
        editText_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arrayAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        listView_TimeZone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = arrayAdapter.getItem(i).toString();
                goToBack(item);
            }
        });

        ///events:@}

    }//onCreate



    private void goToBack(String s_value){
        Intent intent = new Intent(getApplicationContext(),ActivityPersonalCenter.class);
        intent.putExtra(BetweenActivities.selected_value,s_value);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            goToBack(null);
        }
        else
            return super.onKeyDown(keyCode, event);

        return false;
    }


}
