package com.example.trident.smart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.MyLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ActivitySelectCountryAndRegion extends AppCompatActivity {
    /** "Ülke/Bölge Seçin"
     *
     * called by "ActivityLinkedAccout.java","ActivityAddMember.java","ActivityAddSharing.java","ActivityBindMobilePhone.java"
     */



    private static String TAG = "[ActivitySelectCountryAndRegion]";
    Map<String, Integer> mapIndexListView;
    ListView  listView_CountryAndRegion;
    ConstraintLayout constraintLayout_Main;
    private String  where_come_from = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_country_and_region);
        //
        constraintLayout_Main                   = (ConstraintLayout)findViewById(R.id.constraintLayout_Main);
        LinearLayout linearLayout_Back          = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                      = (Button)findViewById(R.id.button_Back);
        EditText editText_Search                = (EditText)findViewById(R.id.editText_Search);
        /*ListView*/  listView_CountryAndRegion = (ListView)findViewById(R.id.listView_CountryAndRegion);

        //Default:
        where_come_from = getIntent().getStringExtra(BetweenActivities.where_come_from);

        String[] array_country_or_region = getResources().getStringArray(R.array.countries);
        Arrays.sort(array_country_or_region);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, array_country_or_region);
        listView_CountryAndRegion.setAdapter(adapter);

//for Region Alphabet index:@{
        getIndexListView(array_country_or_region);
        displayIndex();
//for Region Alphabet index:@}

//Back:@{
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
//Back:@}
        editText_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  //TODO: search in ListView.
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //adapter.getFilter().filter(editable);
            }
        });
        listView_CountryAndRegion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String country_value = adapterView.getItemAtPosition(i).toString();

                if(where_come_from == null)
                    return;

                if(where_come_from.equals("ActivityLinkedAccout.java")){
                    //go to: "ActivityLinkedAccout.java"
                    Intent intent = new Intent(getApplicationContext(),ActivityLinkedAccout.class);
                    intent.putExtra(BetweenActivities.selected_value,country_value);
                    startActivity(intent);
                }else if(where_come_from.equals("ActivityAddMember.java")){
                    //go to: "ActivityAddMember.java"
                    Intent intent =  new Intent(getApplicationContext(),ActivityAddMember.class);
                    intent.putExtra(BetweenActivities.selected_value,country_value);
                    startActivity(intent);
                }else if(where_come_from.equals("ActivityAddSharing.java")){
                    Intent intent =  new Intent(getApplicationContext(),ActivityAddSharing.class);
                    intent.putExtra(BetweenActivities.selected_value,country_value);
                    startActivity(intent);
                }else if(where_come_from.equals("ActivityBindMobilePhone.java")){
                    Intent intent =  new Intent(getApplicationContext(),ActivityBindMobilePhone.class);
                    intent.putExtra(BetweenActivities.selected_value,country_value);
                    startActivity(intent);
                }else{
                    //do nothing.
                }
            }
        });
    }//onCreate

    private void getIndexListView(String[] _country_or_region) {
        mapIndexListView = new LinkedHashMap<String, Integer>();
        String index;
        for (int i = 0; i < _country_or_region.length; i++) {
            index = _country_or_region[i].substring(0, 1);
            if (mapIndexListView.get(index) == null)
                mapIndexListView.put(index, i);
        }//for
    }
    private void displayIndex() {
        LinearLayout indexLayout                    = (LinearLayout) findViewById(R.id.linearLayout_SideIndex);
        TextView textView_SideIndex                 = null;
        List<String> indexList                      = new ArrayList<String>(mapIndexListView.keySet());
        for (String index : indexList) {
            textView_SideIndex                      = (TextView) getLayoutInflater().inflate(R.layout.side_index_item, null);
            textView_SideIndex.setText(index);
            indexLayout.addView(textView_SideIndex);
///Alphabety Popup:@{
             final View viewPopup                   = getLayoutInflater().inflate(R.layout.side_index_item_popup, null); // however you'll inflate your view
             final TextView textView_SideIndexPopup = (TextView)viewPopup.findViewById(R.id.textView_SideIndexPopup);
             final PopupWindow[] popup              = new PopupWindow[1];
             popup[0]                               = new PopupWindow(viewPopup, 120, 120, true);
///Alphabety Popup:@}
            textView_SideIndex.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch(motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // touch down code
                            int width = constraintLayout_Main.getWidth() / 2 - 90;
                            int height = constraintLayout_Main.getHeight() / 2 + 50;
                            popup[0].showAtLocation(viewPopup, Gravity.NO_GRAVITY, width, height);
                            textView_SideIndexPopup.setText(((TextView) view).getText());
                            listView_CountryAndRegion.setSelection(mapIndexListView.get(((TextView)view).getText().toString()));
                            break;
                        case MotionEvent.ACTION_MOVE:
                            // touch move code
                            //Toast.makeText(getApplicationContext(),"action move" ,Toast.LENGTH_SHORT).show();
                            break;
                        case MotionEvent.ACTION_UP:
                            // touch up code
                            //Toast.makeText(getApplicationContext(),"action up" ,Toast.LENGTH_SHORT).show();
                                popup[0].dismiss();
                            break;
                    }
                    return true;
                }
            });//setOnTouchListener
        }//for
    }//displayIndex.

    private void goToBack(){
        if(where_come_from == null)
            return;
        if(where_come_from.equals("ActivityLinkedAccout.java")){
            //go to: "ActivityLinkedAccout.java"
            Intent intent = new Intent(getApplicationContext(),ActivityLinkedAccout.class);
            startActivity(intent);
        }else if(where_come_from.equals("ActivityAddMember.java")){
            //go to: "ActivityAddMember.java"
            Intent intent =  new Intent(getApplicationContext(),ActivityAddMember.class);
            startActivity(intent);
        }else if(where_come_from.equals("ActivityAddSharing.java")){
            Intent intent =  new Intent(getApplicationContext(),ActivityAddSharing.class);
            startActivity(intent);
        }else if(where_come_from.equals("ActivityBindMobilePhone.java")){
            Intent intent =  new Intent(getApplicationContext(),ActivityBindMobilePhone.class);
            startActivity(intent);
        }else{
            //nothing
        }
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
