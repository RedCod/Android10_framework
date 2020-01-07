package com.example.trident.smart;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


public class TestActivity  extends AppCompatActivity   {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
              Button button = (Button)findViewById(R.id.button2);
        CheckedTextView checkedTextView = (CheckedTextView)findViewById(R.id.checkedTextView);
              button.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Intent intent = new Intent(getApplicationContext(),ActivityMainPage.class);
                      startActivity(intent);
                  }
              });
              checkedTextView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      if(((CheckedTextView) view).isChecked())
                          ((CheckedTextView) view).setChecked(false);
                      else
                          ((CheckedTextView) view).setChecked(true);
                  }
              });
    }


}