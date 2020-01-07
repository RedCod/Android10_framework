package com.example.trident.smart;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityDeviceInformation extends AppCompatActivity {//call by "ActivityDetailsOfDevice.java"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_information);
        //

        LinearLayout linearLayout_Back = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back = (Button)findViewById(R.id.button_Back);


        LinearLayout linearLayout_DeviceVirtaulID          = (LinearLayout)findViewById(R.id.linearLayout_DeviceVirtaulID);
        final TextView textView_DeviceVirtualID            = (TextView)findViewById(R.id.textView_DeviceVirtualID);

        LinearLayout linearLayout_DeviceIPAddress          = (LinearLayout)findViewById(R.id.linearLayout_DeviceIPAddress);
        final TextView textView_DeviceIPAddress            = (TextView)findViewById(R.id.textView_DeviceIPAddress);

        LinearLayout linearLayout_DeviceMacAddress         = (LinearLayout)findViewById(R.id.linearLayout_DeviceMacAddress);
        final TextView textView_DeviceMacAddress           = (TextView)findViewById(R.id.textView_DeviceMacAddress);

        LinearLayout linearLayout_DeviceTimeZone           = (LinearLayout)findViewById(R.id.linearLayout_DeviceTimeZone);
        final TextView textView_DeviceTimeZone             = (TextView)findViewById(R.id.textView_DeviceTimeZone);

        LinearLayout linearLayout_DeviceWifiSignalStrength = (LinearLayout)findViewById(R.id.linearLayout_DeviceWifiSignalStrength);
        final TextView textView_DeviceWifiSignalStrength   = (TextView)findViewById(R.id.textView_DeviceWifiSignalStrength);

        ///Event:>
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

        linearLayout_DeviceVirtaulID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClipboard(textView_DeviceVirtualID.getText().toString());
            }
        });
        linearLayout_DeviceIPAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              setClipboard(textView_DeviceIPAddress.getText().toString());
            }
        });
        linearLayout_DeviceMacAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setClipboard(textView_DeviceMacAddress.getText().toString());
            }
        });
        linearLayout_DeviceTimeZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClipboard(textView_DeviceTimeZone.getText().toString());
            }
        });
        linearLayout_DeviceWifiSignalStrength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClipboard(textView_DeviceWifiSignalStrength.getText().toString());
            }
        });
        //event:<


    }//onCreate

    private void setClipboard(String _text){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setText(_text);
        Toast.makeText(getApplicationContext(),getString(R.string.copied),Toast.LENGTH_SHORT).show();
    }

    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivityDetailsOfDevice.class);
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
