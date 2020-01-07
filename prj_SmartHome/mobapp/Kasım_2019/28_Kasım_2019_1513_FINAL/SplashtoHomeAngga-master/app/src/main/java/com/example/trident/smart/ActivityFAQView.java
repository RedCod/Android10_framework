package com.example.trident.smart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

public class ActivityFAQView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqview);
        //
        LinearLayout linearLayout_Back = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back             = (Button)findViewById(R.id.button_Back);
        WebView webView                = (WebView)findViewById(R.id.webView);

        ///Default:
        //todo: "sss1.html" şeklinde hazırlanmış offline sayfaları yükle.
        //https://stackoverflow.com/questions/4027701/loading-existing-html-file-with-android-webview
        webView.loadUrl("https://blog.nimbledroid.com/");

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
        ///events:@}

    }//onCreate

    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivityHelpCenter.class);
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
