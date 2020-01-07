package com.example.trident.smart;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.Account;
import com.example.trident.common.Alert;
import com.example.trident.common.BetweenActivities;
import com.example.trident.common.Login;
import com.example.trident.common.MyLog;

import java.io.LineNumberReader;

public class ActivityAccountSecurity extends AppCompatActivity {
    /**
     * //"Hesap ve Güvenlik"
     * called by "ActivityPersonalCenter.java"
     *
     *
     */
    boolean spinner_is_first_action = true;//sayfa ilk açıldığında spinner event'in çalışmasını istemiyoruz.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_security);
        //
        LinearLayout linearLayout_Back                = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                            = (Button)findViewById(R.id.button_Back);
        LinearLayout linearLayout_BindMobilePhone     = (LinearLayout)findViewById(R.id.linearLayout_BindMobilePhone);
        TextView textView_BindMobilePhone             = (TextView)findViewById(R.id.textView_BindMobilePhone);
        TextView textView_Email                       = (TextView)findViewById(R.id.textView_Email);
        LinearLayout linearLayout_AccountLocation     = (LinearLayout)findViewById(R.id.linearLayout_AccountLocation);
      //  final TextView textView_AccountLocation       = (TextView)findViewById(R.id.textView_AccountLocation);
        Spinner spinner_AccountLocation               = (Spinner)findViewById(R.id.spinner_AccountLocation);
        LinearLayout linearLayout_ModifyLoginPassword = (LinearLayout)findViewById(R.id.linearLayout_ModifyLoginPassword);
        LinearLayout linearLayout_GestureUnlock       = (LinearLayout)findViewById(R.id.linearLayout_GestureUnlock);
        TextView textView_GestureUnlock               = (TextView)findViewById(R.id.textView_GestureUnlock);
        LinearLayout linearLayout_DeactivateAccount   = (LinearLayout)findViewById(R.id.linearLayout_DeactivateAccount);
        //
///Defalut:@{
        ///Cep Telefonu Bağla:@{
        String connected_phone_number = getIntent().getStringExtra("connected_phone_number");//get from "ActivityCodeVerification.java"
        if(connected_phone_number !=null)
            textView_BindMobilePhone.setText(connected_phone_number);
        else
            textView_BindMobilePhone.setText(getString(R.string.unbound));//bağlı değil
        //Cep Telefonu Bağla:@}

        //E-Posta:
        //todo: get email value from database and set:
        textView_Email.setText("tridenturetim@gmail.com");

        //Hesap Konumu:
        //String[] array = {"Türkiye","Avrupa"};
        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.spinner_account_location_item,array); //(this, android.R.layout.simple_spinner_item, array);
        ArrayAdapter<CharSequence> spinnerDataAdapter = ArrayAdapter.createFromResource(this,R.array.account_location,R.layout.spinner_account_location_item); //get resource array.
        spinner_AccountLocation.setAdapter(spinnerDataAdapter);
        //

///Default:@}

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
        linearLayout_BindMobilePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to "bind mobile phone" page:

                Intent intent = new Intent(getApplicationContext(),ActivityBindMobilePhone.class);
                startActivity(intent);
                //return back  and set state to below textView:
                //textView_BindMobilePhone.setText();
            }
        });
        linearLayout_AccountLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to "Account Location" page:
                //and set:
               // textView_AccountLocation.setText("Avrupa");
            }
        });

        spinner_AccountLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spinner_is_first_action)
                {//ilk sayfa yüklenirken bu event çalıştığı için pass geç.
                    spinner_is_first_action = false;
                    return;
                }
                String item = adapterView.getSelectedItem().toString();
                MyLog.d("kerim[ActivityAccountSecurity]","select item:" + item + ",i:" + i +",l:" + l);
                //todo: seçilen item'i veritabanına güncelle.:
                //..
                //..
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        linearLayout_ModifyLoginPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCodeVerificationPage();
            }
        });
        linearLayout_GestureUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to "GestureUnlock" page.  NOTE: to be completed in V2 version.
                Toast.makeText(getApplicationContext(),"To be completed in V2 version",Toast.LENGTH_SHORT).show();
                //textView_GestureUnlock.setText("not yet");
            }
        });
        linearLayout_DeactivateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: show alert dialog for "Deactivate Account":
                Alert.MessageDialog messageDialog = new Alert().new MessageDialog();
                AlertDialog.Builder dialogBuilder = messageDialog.show(ActivityAccountSecurity.this,
                        getString(R.string.deactive_account_title),getString(R.string.deactive_account_desc));

                dialogBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Todo: DeactiveAccount
                        //...
                        //..
                        //tüm işlemler tamamlandığında "Login Out" yap:"
                       boolean deactivate_state = new Account().deactivateAccount();
                       if(deactivate_state){
                           new Login(ActivityAccountSecurity.this).logout();
                       }

                    }
                });
                dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //pass. do nothing
                    }
                });
                dialogBuilder.create().show();


            }
        });
        ///events:@}

    }//onCreate

    private void goToCodeVerificationPage(){
        //todo: mevcut kayıtlı telefon numarasına "Onay Kodu" gönder ve onay kodu giriş sayfasına "Login şifresini değiştirmek için" git:
        Intent intent = new Intent(getApplicationContext(),ActivityCodeVerification.class);
        intent.putExtra(BetweenActivities.where_come_from,"ActivityAccountSecurity.java");
        startActivity(intent);
    }

    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivityPersonalCenter.class);
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
