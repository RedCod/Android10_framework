package com.example.trident.smart;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.trident.common.BetweenActivities;

public class ActivityMessageCenter extends AppCompatActivity
implements Fragment_FragmentMe_MessageCenterContent.OnFragmentInteractionListener{

    /**
     * Using by "FragmentMe.java"
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);
        //
        LinearLayout linearLayout_Back        = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                    = (Button)findViewById(R.id.button_Back);
        Button button_GoToMessageSettingsPage = (Button)findViewById(R.id.button_GoToMessageSettingsPage);
        TabLayout tablayout_MessageCenter     = (TabLayout)findViewById(R.id.tablayout_MessageCenter);
        ViewPager viewPager_MessageCenter     = (ViewPager)findViewById(R.id.viewPager_MessageCenter);
        //

        /**
         * "Alarm","Aile","Bildirimler" tablara ait bildirim içerikleri.
         *
         */
        Fragment[] childFragments = new Fragment[3];
        String[] tabLayout_tab_names = {getString(R.string.alarm),getString(R.string.family),getString(R.string.notifications)};
        childFragments[0] = new Fragment_FragmentMe_MessageCenterContent();
        childFragments[1] = new Fragment_FragmentMe_MessageCenterContent();
        childFragments[2] = new Fragment_FragmentMe_MessageCenterContent();
        tablayout_MessageCenter.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager_MessageCenter.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),childFragments,tabLayout_tab_names));
        tablayout_MessageCenter.setupWithViewPager(viewPager_MessageCenter);
        /*//select tab: for "Alarm"
        TabLayout.Tab tab = tablayout_MessageCenter.getTabAt(0);
        tab.select();
        //*/


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
        button_GoToMessageSettingsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMessageSettingsPage();
            }
        });
        ///events:@}

    }//onCreate

    private void goToMessageSettingsPage(){
      Intent intent = new Intent(getApplicationContext(),ActivityMessageCenterSettings.class);
      startActivity(intent);
    }
    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivityMainPage.class);
        intent.putExtra(BetweenActivities.where_come_from,"ActivityMessageCenter.java:ME");
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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
