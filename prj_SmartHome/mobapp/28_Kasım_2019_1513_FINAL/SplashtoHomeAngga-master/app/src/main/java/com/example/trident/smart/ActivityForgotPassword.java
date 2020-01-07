package com.example.trident.smart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trident.common.EMail;
import com.example.trident.common.Phone;

public class ActivityForgotPassword extends AppCompatActivity {

    EditText editText_EmailORMnumber;
    Button button_get_verification_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ///Controls:@{

        /*
        linearlayout_forgot_password
        linearlayout_get_varification_code
        editText_EmailORMnumber
        button_get_verification_code
         */
        editText_EmailORMnumber      = (EditText)findViewById(R.id.editText_EmailORMnumber);
        button_get_verification_code = (Button)findViewById(R.id.button_get_verification_code);
        ///Controls:@}
        button_get_verification_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get varification code(for user):
                String email_or_mobilnumber = editText_EmailORMnumber.getText().toString().trim();


                if(EMail.isValid(email_or_mobilnumber)){
                 //if suitable e-mail format:
                    ///* is this email address already registered? YES:send new varification code to this email address. NO: so redirect to "ActivityCreateAccount.class".

                }else if(Phone.isValidPhoneNumber(email_or_mobilnumber)){
                    //* is this phone number already registered? YES:send new varification code to this email address. NO: so redirect to "ActivityCreateAccount.class".

                    Toast.makeText(getApplicationContext(),"geçerli numara",Toast.LENGTH_SHORT).show();

                }else{
                    ///*invalid email and phonenumber.
                    Toast.makeText(getApplicationContext(),getString(R.string.msg_invalid_email_or_pnumber),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }//onCreate



}
