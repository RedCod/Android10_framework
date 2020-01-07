package com.example.trident.smart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.trident.common.MyLog;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class ActivityQRCodeScanPage extends AppCompatActivity {
    /**
     * using by "ActivityAddDevices.java"
     *
     *
     * !!!!!!!!!! not used now because moved to in the "ActivityAddDevices.java" file.
     */
    private IntentIntegrator qrScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scan_page);
        //
        LinearLayout linearLayout_Back = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back = (Button)findViewById(R.id.button_Back);
        //
        //Initialize the Scan Object
        qrScan = new IntentIntegrator(this);
        qrScan.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        qrScan.setCameraId(0);
        qrScan.setOrientationLocked(true);
        qrScan.setBeepEnabled(true);
        qrScan.initiateScan();


        ///Events:{
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
        //event:}

    }//onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //Check to see if QR Code has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //QR Code contains some data

                    MyLog.d("kerim","QRCode:" + result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivityAddDevices.class);
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
