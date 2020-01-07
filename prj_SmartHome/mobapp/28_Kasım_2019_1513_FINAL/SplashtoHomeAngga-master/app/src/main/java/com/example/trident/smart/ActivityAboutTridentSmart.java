package com.example.trident.smart;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityAboutTridentSmart extends AppCompatActivity {
    /**
     * called by "ActivityAbout.java"
     * Bu sayfa parametrelerini "ActivityAbout.java"'dan alarak:
     *          -"Trident Smart Hakkında(About Smart Trident)"
     *          -"Gizlilik Politikası(Privacy Policy)"
     *          -"Hizmet Sözleşmesi(Service Agreement)"
     *          -"Açık kaynak bileşen lisansı(Open Source License)"
     *          için kullanılmaktadır.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_trident_smart);
        //
        LinearLayout linearLayout_Back = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back             = (Button)findViewById(R.id.button_Back);
        TextView textView_PageTitle    = (TextView)findViewById(R.id.textView_PageTitle);
        WebView webView                = (WebView)findViewById(R.id.webView);
        ///Default:
        //webView.loadUrl("http://www.trident.com.tr/tr/index.php?option=com_content&view=article&id=67&Itemid=75");//todo: View "Hakkımızda" page.
        String page_title = getIntent().getStringExtra("page_title");
        String web_content_link = getIntent().getStringExtra("web_content_link");
        textView_PageTitle.setText(page_title);
        if(web_content_link !=null)
            webView.loadUrl(web_content_link);
        else
            webView.loadUrl("https://www.google.com/");

       /* Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.trident.com.tr/tr/index.php?option=com_content&view=article&id=67&Itemid=75"));
        startActivity(intent);*/

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
        Intent intent = new Intent(getApplicationContext(),ActivityAbout.class);
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
