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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.BetweenActivities;

public class ActivityAddSharing extends AppCompatActivity {
    /** "Paylaşım Ekle"
     * call by "ActivitySharedDevice.java"
     *
     */

    //public static String GET_COUNTRY_VALUE_FROM_CHILD_PAGE ="Turkey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sharing);
        //
        LinearLayout linearLayout_Back             = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                         = (Button)findViewById(R.id.button_Back);
        final Button button_Done                         = (Button)findViewById(R.id.button_Done);
        TextView textView_LabelCountryAndRegion    = (TextView)findViewById(R.id.textView_LabelCountryAndRegion);
        TextView textView_CountryAndRegion         = (TextView)findViewById(R.id.textView_CountryAndRegion);
        ImageView imageView_CountryAndRegion_arrow = (ImageView)findViewById(R.id.imageView_CountryAndRegion_arrow);
        final EditText editText_Account            = (EditText)findViewById(R.id.editText_Account);


        //todo:
        String country = getIntent().getStringExtra(BetweenActivities.selected_value);//get from "ActivitySelectCountryAndRegion.java"
        if(country == null)
            textView_CountryAndRegion.setText("Turkey+90");
          else
            textView_CountryAndRegion.setText(country);
        //Event:
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

        button_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //done:
                //todo: save  and gotoback:
                String account = editText_Account.getText().toString().trim();
                //..
                goToBack();
            }
        });

        textView_LabelCountryAndRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to Country and region page:
                goToCountryAndRegion();
            }
        });
        textView_CountryAndRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to Country and region page:
                goToCountryAndRegion();
            }
        });
        imageView_CountryAndRegion_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to Country and region page:
                goToCountryAndRegion();
            }
        });

        editText_Account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length() > 0)
                    button_Done.setEnabled(true);
                else
                    button_Done.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }//onCreate




    private void goToCountryAndRegion(){
        Intent intent = new Intent(getApplicationContext(),ActivitySelectCountryAndRegion.class);
        intent.putExtra(BetweenActivities.where_come_from,"ActivityAddSharing.java");
        startActivity(intent);
    }
    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivitySharedDevice.class);
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
