package com.example.trident.smart;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.trident.common.MyLog;
public class ActivityCheckDeviceNetwork extends AppCompatActivity {

    /** "Ağ Durumunu Kontrol Et"
     * call by "ActivityDetailsOfDevice.java"
     *
     */

    LinearLayout linearLayout_Pano1_NetworkSearching_Green;
    LinearLayout linearLayout_Pano2_NetworkInfo;

    LinearLayout linearLayout_Pano1_NetworkSearching_Orange;
    LinearLayout linearLayout_Pano2_NetworkPrecautionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_device_network);
        //
        LinearLayout linearLayout_Back = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back             = (Button)findViewById(R.id.button_Back);

        linearLayout_Pano1_NetworkSearching_Green  = (LinearLayout)findViewById(R.id.linearLayout_Pano1_NetworkSearching_Green);
        linearLayout_Pano1_NetworkSearching_Orange = (LinearLayout)findViewById(R.id.linearLayout_Pano1_NetworkSearching_Orange);
        Button button_ReTest                       = (Button)findViewById(R.id.button_ReTest);

        linearLayout_Pano2_NetworkInfo          = (LinearLayout)findViewById(R.id.linearLayout_Pano2_NetworkInfo);
        linearLayout_Pano2_NetworkPrecautionary = (LinearLayout)findViewById(R.id.linearLayout_Pano2_NetworkPrecautionary);

        //call detectingnewtork:
        new AsyncDetectingNetwork().execute("loading");


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

        button_ReTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: RE-TEST.:
                //for re-test
                //call detectingnewtork:
                setVisibilityPanoOrange(View.GONE);
                setVisibilityPanoGreen(View.VISIBLE);
                new AsyncDetectingNetwork().execute("loading");
            }
        });


    }//onCrate


    private class AsyncDetectingNetwork extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(2000);
                //if(MyLog.DEGUB) MyLog.d("kerim","sleeep:" + params[0]);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
            return "Executed";
        }
        @Override
        protected void onPostExecute(String result) {
            //call
            detectingNetwork();
        }
        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    /**
     * search,for detecting network..
     */
    private void detectingNetwork(){
        setVisibilityPanoOrange(View.GONE);
        setVisibilityPanoGreen(View.VISIBLE);

        //todo: Detecting Network:
        //..
        //..

        //todo: if network is not detected,show Orange Pano:
        boolean is_detected_network = false; //false .not detected //todo:this information is "network detected" returned value.
        if(is_detected_network == false){
            //not detected:
            //show Orange "Pano1 Orange" and "Precautionary" layout.
            setVisibilityPanoGreen(View.GONE);
            setVisibilityPanoOrange(View.VISIBLE);

        }else{
            //green "Pano1 Green" will remain shown.
        }
    }//detectingNetwork()


    private void setVisibilityPanoGreen(int visibility){
        linearLayout_Pano1_NetworkSearching_Green.setVisibility(visibility);
        linearLayout_Pano2_NetworkInfo.setVisibility(visibility);
    }
    private void setVisibilityPanoOrange(int visibility){
        linearLayout_Pano1_NetworkSearching_Orange.setVisibility(visibility);
        linearLayout_Pano2_NetworkPrecautionary.setVisibility(visibility);
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
    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivityDetailsOfDevice.class);
        startActivity(intent);
    }//goToBack()

}
