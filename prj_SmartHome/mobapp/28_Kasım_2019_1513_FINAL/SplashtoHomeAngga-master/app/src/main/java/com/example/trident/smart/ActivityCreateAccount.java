package com.example.trident.smart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.EMail;

public class ActivityCreateAccount extends AppCompatActivity {
///Create an account:

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        ///Controls:@{
        LinearLayout linearlayout_Sign_up_by = (LinearLayout)findViewById(R.id.linearlayout_Sign_up_by);
        LinearLayout linearlayout_createaccount = (LinearLayout)findViewById(R.id.linearlayout_createaccount);
        LinearLayout linearlayout_agree_terms_of_use = (LinearLayout)findViewById(R.id.linearlayout_agree_terms_of_use);
        Animation fromTop = AnimationUtils.loadAnimation(this,R.anim.fromtop);
        Animation fromBottom   = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        linearlayout_Sign_up_by.setAnimation(fromTop);
        linearlayout_createaccount.setAnimation(fromBottom);
        linearlayout_agree_terms_of_use.startAnimation(fromBottom);


        final TextView textview_Sign_up_by = (TextView)findViewById(R.id.textview_Sign_up_by);
        final Spinner spinner = (Spinner)findViewById(R.id.spinner_wichtype);
        final EditText editText_EmailORMnumber = (EditText)findViewById(R.id.editText_EmailORMnumber);
        Button button_CreateAccount = (Button)findViewById(R.id.button_CreateAccount);
        final CheckBox checkbox_AgreeTo = (CheckBox)findViewById(R.id.checkbox_AgreeTo);
        TextView textview_Agree_explanation = (TextView)findViewById(R.id.textview_Agree_explanation);
        ///controls:@}

        //Spinner:@{
        String[] which_type =   {getString(R.string.by_EMail),getString(R.string.by_MobileNumber)};
        final ArrayAdapter arrayAdapter_spinner = new ArrayAdapter(ActivityCreateAccount.this,android.R.layout.simple_spinner_item,which_type);
        arrayAdapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item  = spinner.getSelectedItem().toString();
                 if(i == 0) { //by_EMail
                     textview_Sign_up_by.setText(getString(R.string.Sign_up_byEmail));
                     editText_EmailORMnumber.setText("");
                     editText_EmailORMnumber.setHint(R.string.hint_email);
                     editText_EmailORMnumber.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
                 }else {
                     textview_Sign_up_by.setText(getString(R.string.Sign_up_byMnumber));
                     editText_EmailORMnumber.setText("");
                     editText_EmailORMnumber.setHint(R.string.hint_MobileNumber);
                     editText_EmailORMnumber.setInputType(InputType.TYPE_CLASS_PHONE);
                 }
                //Toast.makeText(getApplicationContext(), item.toString(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //spinner:@}
        button_CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create Account:
                if(spinner.getSelectedItemId() == 0){//by EMail
                    if(!EMail.isValid(editText_EmailORMnumber.getText())){
                        Toast.makeText(getApplicationContext(),getString(R.string.msg_invalid_email), Toast.LENGTH_SHORT).show();
                        return; //stop.
                    }
                }
                if(!checkbox_AgreeTo.isChecked()){
                    Toast.makeText(getApplicationContext(), getString(R.string.msg_agree_alert), Toast.LENGTH_SHORT).show();
                    return;//stop.
                }


                /**
                 *Create account process..
                 * ....
                 * ....
                 */


                //and then go to "Code Verification " page:
                Intent intent = new Intent(getApplicationContext(),ActivityCodeVerification.class);
                startActivity(intent);

            }
        });

    }//onCreate





}
