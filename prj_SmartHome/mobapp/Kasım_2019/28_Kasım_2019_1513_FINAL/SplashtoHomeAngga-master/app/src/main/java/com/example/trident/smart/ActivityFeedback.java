package com.example.trident.smart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.trident.common.BetweenActivities;

public class ActivityFeedback extends AppCompatActivity {

    /**
     * using by :
     *          -"ActivityHelpCenter.java"
     *          -"ActivityDetailsOfDevice.java"
     *
     * "Geri Bildirimim" (My Feedback)
     * Kullanıcının gönderdiği feedback ve cevaplarının listelendiği sayfa.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        //
        LinearLayout linearLayout_Back = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back             = (Button)findViewById(R.id.button_Back);
        ListView listView_MyFeedback   = (ListView)findViewById(R.id.listView_MyFeedback);

        ///Default:
        ///todo: get from database.
        String[] arrFeedbackContent = new String[] { "In part one of this blog post “Eight Ways Your Android App Can Leak Memory”, we went over eight different ways your code can cause your Android application to leak memory. Specifically, all eight leaks were leaking an Activity instance, which is particularly dangerous because activities have a very large.",
                "In part one of this blog post “Eight Ways Your Android App Can Leak Memory”, we went over eight different ways your code can cause your Android application to leak memory. Specifically, all eight leaks were leaking an Activity instance, which is particularly dangerous because activities have a very large.",
                "In part one of this blog post “Eight Ways Your Android App Can Leak Memory”, we went over eight different ways your code can cause your Android application to leak memory. Specifically, all eight leaks were leaking an Activity instance, which is particularly dangerous because activities have a very large.",
                "In part one of this blog post “Eight Ways Your Android App Can Leak Memory”, we went over eight different ways your code can cause your Android application to leak memory. Specifically, all eight leaks were leaking an Activity instance, which is particularly dangerous because activities have a very large."
        };
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrFeedbackContent);
        listView_MyFeedback.setAdapter(arrayAdapter);


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
        String where_come_from = getIntent().getStringExtra(BetweenActivities.where_come_from);
        Intent intent = null;
        if(where_come_from.equals("ActivityHelpCenter.java"))
            intent = new Intent(getApplicationContext(),ActivityHelpCenter.class);
        else if(where_come_from.equals("ActivityDetailsOfDevice.java"))
            intent = new Intent(getApplicationContext(),ActivityDetailsOfDevice.class);

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
