package com.example.trident.smart;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.MovementMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.MyLog;

import org.w3c.dom.Text;

import java.lang.reflect.GenericArrayType;

public class ActivityCodeVerification extends AppCompatActivity {
    /**
     * used by  Login page  and "ActivityAccountSecurity.java"
     */

    private static String TAG = "[ActivityCodeVerification]";
    EditText editText_verification_code1,editText_verification_code2,editText_verification_code3,editText_verification_code4,editText_verification_code5,editText_verification_code6;
    TextView textView_explanation1,textView_sendagain;
    Button button_OK,button_sendagain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verification);
        ///Controls:@{
        editText_verification_code1 = (EditText)findViewById(R.id.editText_verification_code1);
        editText_verification_code2 = (EditText)findViewById(R.id.editText_verification_code2);
        editText_verification_code3 = (EditText)findViewById(R.id.editText_verification_code3);
        editText_verification_code4 = (EditText)findViewById(R.id.editText_verification_code4);
        editText_verification_code5 = (EditText)findViewById(R.id.editText_verification_code5);
        editText_verification_code6 = (EditText)findViewById(R.id.editText_verification_code6);
        textView_explanation1 = (TextView)findViewById(R.id.textView_explanation1);//print e-mail addres on this textview
        textView_sendagain    = (TextView)findViewById(R.id.textView_sendagain);//print descending timer second on this textview.
        button_OK             = (Button)findViewById(R.id.button_OK);
        button_sendagain      = (Button)findViewById(R.id.button_sendagain);
        ///controls:}

        editText_verification_code1.setCursorVisible(false);
        editText_verification_code2.setCursorVisible(false);
        editText_verification_code3.setCursorVisible(false);
        editText_verification_code4.setCursorVisible(false);
        editText_verification_code5.setCursorVisible(false);
        editText_verification_code6.setCursorVisible(false);
        button_sendagain.setVisibility(View.GONE);//hide
       /*
        editText_verification_code1.setFocusableInTouchMode(false);
        editText_verification_code2.setFocusableInTouchMode(false);
        editText_verification_code3.setFocusableInTouchMode(false);
        editText_verification_code4.setFocusableInTouchMode(false);
        editText_verification_code5.setFocusableInTouchMode(false);
        editText_verification_code6.setFocusableInTouchMode(false);*/


        editText_verification_code1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER || keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK || keyEvent.getKeyCode() == KeyEvent.KEYCODE_MENU ) //if key press "Enter,Back,Menu",do nothing.Attention! return false.
                    return false;

                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    editText_verification_code1.setText(convertKeyCodeToString(keyEvent.getKeyCode()));
                    if(keyEvent.getKeyCode() != KeyEvent.KEYCODE_DEL)
                        editText_verification_code2.requestFocus();
                    return true;
                }
                return false;
            }
        });

        editText_verification_code2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER || keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK || keyEvent.getKeyCode() == KeyEvent.KEYCODE_MENU ) //if key press "Enter,Back,Menu",do nothing.Attention! return false.
                    return false;

                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    editText_verification_code2.setText(convertKeyCodeToString(keyEvent.getKeyCode()));
                    if(keyEvent.getKeyCode() != KeyEvent.KEYCODE_DEL)
                        editText_verification_code3.requestFocus();
                    return true;
                }
                return false;
            }
        });

        editText_verification_code3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER || keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK || keyEvent.getKeyCode() == KeyEvent.KEYCODE_MENU ) //if key press "Enter,Back,Menu",do nothing.Attention! return false.
                    return false;

                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    editText_verification_code3.setText(convertKeyCodeToString(keyEvent.getKeyCode()));
                    if(keyEvent.getKeyCode() != KeyEvent.KEYCODE_DEL)
                        editText_verification_code4.requestFocus();
                    return true;
                }
                return false;
            }
        });

        editText_verification_code4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER || keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK || keyEvent.getKeyCode() == KeyEvent.KEYCODE_MENU ) //if key press "Enter,Back,Menu",do nothing.Attention! return false.
                    return false;

                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    editText_verification_code4.setText(convertKeyCodeToString(keyEvent.getKeyCode()));
                    if(keyEvent.getKeyCode() != KeyEvent.KEYCODE_DEL)
                        editText_verification_code5.requestFocus();
                    return true;
                }
                return false;
            }
        });

        editText_verification_code5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER || keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK || keyEvent.getKeyCode() == KeyEvent.KEYCODE_MENU ) //if key press "Enter,Back,Menu",do nothing.Attention! return false.
                    return false;

                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    editText_verification_code5.setText(convertKeyCodeToString(keyEvent.getKeyCode()));
                    if(keyEvent.getKeyCode() != KeyEvent.KEYCODE_DEL)
                        editText_verification_code6.requestFocus();
                    return true;
                }
                return false;
            }
        });

        editText_verification_code6.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER || keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK || keyEvent.getKeyCode() == KeyEvent.KEYCODE_MENU ) //if key press "Enter,Back,Menu",do nothing.Attention! return false.
                    return false;

                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    editText_verification_code6.setText(convertKeyCodeToString(keyEvent.getKeyCode()));
                    return true;
                }
                return false;
            }
        });

        button_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Button OK:
                 if(editText_verification_code1.getText().length() < 1 || editText_verification_code2.getText().length() < 1 || editText_verification_code3.getText().length() < 1 ||
                         editText_verification_code4.getText().length() < 1 || editText_verification_code5.getText().length() < 1 || editText_verification_code6.getText().length() < 1){
                     Toast.makeText(getApplicationContext(),getString(R.string.msg_verification_code_missing),Toast.LENGTH_SHORT).show();
                     return;
                 }
                 String verification_code="";
                 verification_code = editText_verification_code1.getText().toString();
                 verification_code += editText_verification_code2.getText().toString();
                 verification_code += editText_verification_code3.getText().toString();
                 verification_code += editText_verification_code4.getText().toString();
                 verification_code += editText_verification_code5.getText().toString();
                 verification_code += editText_verification_code6.getText().toString();
                 MyLog.d(TAG,verification_code);
                //*send verification code to control process.

                //and is OK:
                String where_come_from = getIntent().getStringExtra(BetweenActivities.where_come_from);
                if(where_come_from !=null){
                    if(where_come_from.equals("ActivityBindMobilePhone.java")){//bu sayfadan gelinmişse o sayfanın bir üst sayfasına gidilecek.
                        Intent intent = new Intent(getApplicationContext(),ActivityAccountSecurity.class);
                        intent.putExtra("connected_phone_number","054444444");
                        startActivity(intent);
                    }else if(where_come_from.equals("ActivityAccountSecurity.java")){
                        /**todo:
                         * Şifre değiştirmek için buraya gelindi.
                         * -Şifreyi değiştir
                         * -Geri dön.
                         */
                        Toast.makeText(getApplicationContext(), "Şifre değiştirildi", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),ActivityAccountSecurity.class);
                        intent.putExtra("connected_phone_number","054444444");
                        startActivity(intent);
                    }else{
                        //fixme:todo: İlk kayıt olurken de bu sayfa kullanılır.Hangi sayfaya gideceğini belirle ve oraya yönlendir.
                    }
                }
            }
        });
        button_sendagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Button Send Again:

                //*send again the verification code to user e-mail address.
            }
        });


        //CountDown second for "Send Again" Button:
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                textView_sendagain.setText(getString(R.string.send_again) +"(" + (millisUntilFinished / 1000) +")");
            }

            public void onFinish() {
                textView_sendagain.setText(getString(R.string.send_again) +":");
                button_sendagain.setVisibility(View.VISIBLE);//show
            }
        }.start();


    }//onCreate


    private static String convertKeyCodeToString(int _keyCode){
        String key = "";
        if (_keyCode == KeyEvent.KEYCODE_0)
            key = "0";
        else if (_keyCode == KeyEvent.KEYCODE_1)
            key = "1";
        else if (_keyCode == KeyEvent.KEYCODE_2)
            key = "2";
        else if (_keyCode == KeyEvent.KEYCODE_3)
            key = "3";
        else if (_keyCode == KeyEvent.KEYCODE_4)
            key = "4";
        else if (_keyCode == KeyEvent.KEYCODE_5)
            key = "5";
        else if (_keyCode == KeyEvent.KEYCODE_6)
            key = "6";
        else if (_keyCode == KeyEvent.KEYCODE_7)
            key = "7";
        else if (_keyCode == KeyEvent.KEYCODE_8)
            key = "8";
        else if (_keyCode == KeyEvent.KEYCODE_9)
            key = "9";

        return key;
    }

}
