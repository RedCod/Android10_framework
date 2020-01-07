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
import android.widget.TextView;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.MyLog;

public class ActivityBindMobilePhone extends AppCompatActivity {

    /**"Cep Telefonu Bağla"
     *
     * used by "ActivityAccountSecurity.java"
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_mobile_phone);
        //
        LinearLayout linearLayout_Back             = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                         = (Button)findViewById(R.id.button_Back);
        LinearLayout linearLayout_GoToCountryPage  = (LinearLayout)findViewById(R.id.linearLayout_GoToCountryPage);
        TextView textView_Country                  = (TextView)findViewById(R.id.textView_Country);
        final EditText editText_PhoneNumber        = (EditText)findViewById(R.id.editText_PhoneNumber);
        final Button button_ObtainVerificationCode = (Button)findViewById(R.id.button_ObtainVerificationCode);

        //Default:
        String country = getIntent().getStringExtra(BetweenActivities.selected_value);//get from "ActivitySelectCountryAndRegion.java"
        if(country == null)
            textView_Country.setText("Turkey+90");
        else
            textView_Country.setText(country);


        ///Event:@{
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
        linearLayout_GoToCountryPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCountryAndRegionPage();
            }
        });
        button_ObtainVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: send verification code to  "phone number":
                String phone_number = editText_PhoneNumber.getText().toString().trim();
                //...
                //...

                //if verification code send,go to "ActivityCodeVerification.java"
                goToCodeVerificationPage();
            }
        });
        editText_PhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.length() > 0) {
                    button_ObtainVerificationCode.setEnabled(true);
                    button_ObtainVerificationCode.setBackgroundColor(getResources().getColor(R.color.color_my_theme));
                }else {
                    button_ObtainVerificationCode.setEnabled(false);
                    button_ObtainVerificationCode.setBackgroundColor(getResources().getColor(R.color.color_Gray2));
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        ///event:@}

        //go to "ActivityCodeVerification.java" page.
    }//onCreate

    private void goToCodeVerificationPage(){
        /**
         *-Sayfaya gittiğimizde Back işleminde bu sayfaya geleceğiz.
         *-İşlem onay işleminde ise "ActivityAccountSecurity.java" sayfasına direkt geçeceğiz.
         */
        Intent intent = new Intent(getApplicationContext(),ActivityCodeVerification.class);
        intent.putExtra(BetweenActivities.where_come_from,"ActivityBindMobilePhone.java");
        startActivity(intent);
    }

    private void goToCountryAndRegionPage(){
            Intent intent = new Intent(getApplicationContext(),ActivitySelectCountryAndRegion.class);
            intent.putExtra(BetweenActivities.where_come_from,"ActivityBindMobilePhone.java");
            startActivity(intent);
    }

    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivityAccountSecurity.class);
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
