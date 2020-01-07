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
import android.widget.TextView;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.MyLog;
import com.example.trident.common.Static;

public class ActivityCities extends AppCompatActivity {

    /**
     * used by :
     * + "ActivityTemperature.java"
     * +
     *
     * @param savedInstanceState
     */
    private String WHERE_COME_FROM ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        //

        /**TODO:
         * NOTE: Otomatik konum bulma özelliği V2 de eklenecektir.
         * *******************************************************
         */

        Static.WHERE_COME_FROM = getIntent().getStringExtra(BetweenActivities.where_come_from);

        LinearLayout linearLayout_Back = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back             = (Button)findViewById(R.id.button_Back);
        EditText editText_Search       = (EditText)findViewById(R.id.editText_Search);
        ListView listView_Cities       = (ListView)findViewById(R.id.listView_Cities);

        final ArrayAdapter<CharSequence> arrayAdapter= ArrayAdapter.createFromResource(this,R.array.Turkey_Cities,android.R.layout.simple_list_item_1);
        listView_Cities.setAdapter(arrayAdapter);

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
        listView_Cities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String current_city  = arrayAdapter.getItem(i).toString();
                selectCityAndGo(current_city);
            }
        });
        ///Events:@}


    }//onCreate

    private void selectCityAndGo(String current_city){
        //todo: GO WHERE?
        Intent intent = null;
        if(Static.WHERE_COME_FROM.equals("ActivityTemperature.java"))
            intent = new Intent(getApplicationContext(),ActivityTemperature.class);
         else if(Static.WHERE_COME_FROM.equals("ActivityHumidity.java"))
            intent = new Intent(getApplicationContext(),ActivityHumidity.class);
         else if(Static.WHERE_COME_FROM.equals("ActivityWeather.java"))
            intent = new Intent(getApplicationContext(),ActivityWeather.class);
        else if(Static.WHERE_COME_FROM.equals("ActivityAirQuality.java"))
            intent = new Intent(getApplicationContext(),ActivityAirQuality.class);
        else if(Static.WHERE_COME_FROM.equals("ActivitySunriseSunset.java"))
            intent = new Intent(getApplicationContext(),ActivitySunriseSunset.class);

        intent.putExtra(BetweenActivities.current_city,current_city);
        startActivity(intent);
    }
    private void goToBack(){
        Intent intent = null;
        if(Static.WHERE_COME_FROM.equals("ActivityTemperature.java"))
            intent = new Intent(getApplicationContext(),ActivityTemperature.class);
        else if(Static.WHERE_COME_FROM.equals("ActivityHumidity.java"))
            intent = new Intent(getApplicationContext(),ActivityHumidity.class);
        else if(Static.WHERE_COME_FROM.equals("ActivityWeather.java"))
            intent = new Intent(getApplicationContext(),ActivityWeather.class);
        else if(Static.WHERE_COME_FROM.equals("ActivityAirQuality.java"))
            intent = new Intent(getApplicationContext(),ActivityAirQuality.class);
        else if(Static.WHERE_COME_FROM.equals("ActivitySunriseSunset.java"))
            intent = new Intent(getApplicationContext(),ActivitySunriseSunset.class);
        //todo: wehere come from:
        //..
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
