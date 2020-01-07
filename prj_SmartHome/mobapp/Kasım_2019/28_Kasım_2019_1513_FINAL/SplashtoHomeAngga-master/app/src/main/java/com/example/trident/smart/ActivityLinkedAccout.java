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
import android.widget.Switch;
import android.widget.TextView;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.Family;

public class ActivityLinkedAccout extends AppCompatActivity {//SAYFA:"Bağlanan Hesap"

    //public static String GET_COUNTRY_VALUE_FROM_CHILD_PAGE = "Turkey+90";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_accout);
        //
        LinearLayout  linearLayout_Back              = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                           = (Button)findViewById(R.id.button_Back);
        final Button button_Add                      = (Button)findViewById(R.id.button_Add);
        TextView textView_CountryAndRegion           = (TextView)findViewById(R.id.textView_CountryAndRegion);
        ImageView imageView_CountryAndRegion_arrow   = (ImageView)findViewById(R.id.imageView_CountryAndRegion_arrow);
        EditText editText_Account                    = (EditText)findViewById(R.id.editText_Account);
        final Switch switchButton_SetAsAdministrator = (Switch)findViewById(R.id.switchButton_SetAsAdministrator);


        String country = getIntent().getStringExtra(BetweenActivities.selected_value);//came from "ActivitySelectCountryAndRegion.java
        if(country == null)
            textView_CountryAndRegion.setText("Turkey+90");//restore default value.
        else
            textView_CountryAndRegion.setText(country);////set Country value from "ActivitySelectCountryRegion.java" class or default value.

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
        //button_Add
        button_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to Add:
            }
        });


        //textView_CountryAndRegion
        textView_CountryAndRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to "ActivitySelectCountryAndRegion.java"
                Intent intent = new Intent(getApplicationContext(),ActivitySelectCountryAndRegion.class);
                intent.putExtra(BetweenActivities.where_come_from,"ActivityLinkedAccout.java");//we send this class name. for "Back" button and select "Region" in the "ActivitySelectCountryAndRegion".
                startActivity(intent);
            }
        });

        //imageView_CountryAndRegion_arrow
        imageView_CountryAndRegion_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to "ActivitySelectCountryAndRegion.java"
                Intent intent = new Intent(getApplicationContext(),ActivitySelectCountryAndRegion.class);
                intent.putExtra(BetweenActivities.where_come_from,"ActivityLinkedAccout.java");//we send this class name. for "Back" and select "Region" button in the "ActivitySelectCountryAndRegion".
                startActivity(intent);
            }
        });

        //editText_Account
        editText_Account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 if(charSequence.toString().trim().length() < 1)
                     button_Add.setEnabled(false);
                 else
                     button_Add.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //switchButton_SetAsAdministrator
        switchButton_SetAsAdministrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //Family family = new Family(getApplicationContext());
                Family family = Family.getInstance(getApplicationContext());
                if(switchButton_SetAsAdministrator.isChecked() == true){
                    //TODO: set as Administrator this Member.
                    family.setFamilyAdministrator_ON();
                }else{
                    //TODO: if member as an Administrator,remove admin authority.
                    family.setFamilyAdministrator_OFF();
                }
            }
        });

    }//onCreate

    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivityFamilyMembers.class);
        startActivity(intent);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
           Intent intent = new Intent(getApplicationContext(),ActivityFamilyMembers.class);
           startActivity(intent);
        }
        else
            return super.onKeyDown(keyCode, event);

        return false;
    }

}
