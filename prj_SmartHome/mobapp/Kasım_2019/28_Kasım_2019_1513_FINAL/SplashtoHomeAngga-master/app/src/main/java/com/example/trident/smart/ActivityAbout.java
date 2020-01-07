package com.example.trident.smart;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.Ini;
import com.example.trident.common.MyLog;
import com.example.trident.common.Preferences;

import java.io.LineNumberReader;

public class ActivityAbout extends AppCompatActivity {
    /**
     * "Hakkında"
     * called by "ActivitySettings.java"
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //
        LinearLayout linearLayout_Back              = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                          = (Button)findViewById(R.id.button_Back);
        LinearLayout linearLayout_RateUs            = (LinearLayout)findViewById(R.id.linearLayout_RateUs);
        LinearLayout linearLayout_AboutTridentSmart = (LinearLayout)findViewById(R.id.linearLayout_AboutTridentSmart);
        LinearLayout linearLayout_PrivacyPolicy     = (LinearLayout)findViewById(R.id.linearLayout_PrivacyPolicy);
        LinearLayout linearLayout_ServiceAgreement  = (LinearLayout)findViewById(R.id.linearLayout_ServiceAgreement);
        LinearLayout linearLayout_OpenSourceLicense = (LinearLayout)findViewById(R.id.linearLayout_OpenSourceLicense);
        TextView textView_Version                   = (TextView)findViewById(R.id.textView_Version);
        LinearLayout linearLayout_CheckUpdates      = (LinearLayout)findViewById(R.id.linearLayout_CheckUpdates);
        final TextView textView_CheckUpdatesInfo    = (TextView)findViewById(R.id.textView_CheckUpdatesInfo);
        TextView textView_RightInfo                 = (TextView)findViewById(R.id.textView_RightInfo);
        //
        ///Default:@{
        textView_Version.setText(Preferences.loadAsString(getApplicationContext(),"Settings","version"));//güncel versiyonu göster.(preferences)
        textView_CheckUpdatesInfo.setText(getString(R.string.no_updates));// or "update_available"
        textView_RightInfo.setText(Ini.ALL_RIGHT_RESERVED);
        ///default:@}

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
        linearLayout_RateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to "Bizi Değerlendirin" (Google play Trident Smart page):
                //https://stackoverflow.com/questions/11753000/how-to-open-the-google-play-store-directly-from-my-android-application
                Intent intent = null;
                String packageName = "com.tuya.smart";//todo: Replace with "com.trident.smat" package name.
                try {
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("market://details?id=" + packageName)); //<<burası çalışır.
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
                }
            }
        });
        linearLayout_AboutTridentSmart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to "Trident Smart Hakkında" page:
                Intent intent = new Intent(getApplicationContext(),ActivityAboutTridentSmart.class);
                intent.putExtra("page_title",getString(R.string.about_smart));
                intent.putExtra("web_content_link","http://www.trident.com.tr/tr/index.php?option=com_content&view=article&id=67&Itemid=75");//represented link
                startActivity(intent);
            }
        });
        linearLayout_PrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to "Gizlilik Politikası" page:
                Intent intent = new Intent(getApplicationContext(),ActivityAboutTridentSmart.class);
                intent.putExtra("page_title",getString(R.string.privacy_policy));
                intent.putExtra("web_content_link","http://www.trident.com.tr/tr/index.php?option=com_content&view=article&id=75&Itemid=99");//represented link
                startActivity(intent);
            }
        });
        linearLayout_ServiceAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to "Hizmet Sözleşmesi" page:
                Intent intent = new Intent(getApplicationContext(),ActivityAboutTridentSmart.class);
                intent.putExtra("page_title",getString(R.string.service_agreement));
                intent.putExtra("web_content_link","https://www.google.com/");//represented link
                startActivity(intent);
            }
        });
        linearLayout_OpenSourceLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to "Açık kaynak bileşen lisansı" page:
                Intent intent = new Intent(getApplicationContext(),ActivityAboutTridentSmart.class);
                intent.putExtra("page_title",getString(R.string.open_source_license));
                intent.putExtra("web_content_link","https://resources.whitesourcesoftware.com/blog-whitesource/open-source-licenses-explained");//represented link
                startActivity(intent);
            }
        });
        linearLayout_CheckUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: güncelleme durumu görüntüle:
                //textView_CheckUpdatesInfo.setText(getString(R.string.no_updates));
                Toast.makeText(getApplicationContext(),getString(R.string.no_update_available),Toast.LENGTH_SHORT).show();
            }
        });
        ///events:@}

    }//onCreate


    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivitySettings.class);
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
