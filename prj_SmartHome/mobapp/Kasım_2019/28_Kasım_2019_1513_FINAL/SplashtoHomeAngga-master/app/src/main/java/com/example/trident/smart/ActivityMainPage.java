package com.example.trident.smart;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.MyLog;

public class ActivityMainPage extends AppCompatActivity implements FragmentHome.OnFragmentInteractionListener, FragmentSmart.OnFragmentInteractionListener,
        FragmentMe.OnFragmentInteractionListener,Fragment_FragmentHome_RoomDevices.OnFragmentInteractionListener,Fragment_FragmentHome_BlankRoomContent.OnFragmentInteractionListener,
      Fragment_FragmentSmart_BlankScenarioContent.OnFragmentInteractionListener,Fragment_FragmentSmart_ScenarioList.OnFragmentInteractionListener,//for Scenario
        Fragment_FragmentSmart_BlankAutomationContent.OnFragmentInteractionListener,Fragment_FragmentSmart_AutomationList.OnFragmentInteractionListener{ //for Automation(Scenario)
    ///Main Page:
    private static String TAG = "[ActivityMainPage]";
    /*
    https://www.androidhive.info/2017/12/android-working-with-bottom-navigation/
     */
    private Toast exitToast;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        //login is successful,come to this page:
        /*BottomNavigationView*/ bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new FragmentHome());//first load "FragmentHome"
        exitToast = Toast.makeText(getApplicationContext(), getString(R.string.toast_msg_push_again) , Toast.LENGTH_SHORT);

        ///for RETURN BACK:@{
        /**
         * NOT: Buraya "ActivityHomeManagement.java" tarafından iki yoldan geliniyor.
         * 1. "FragmentHome.java" üzerinden Spinner ile seçilen menü ile "ActivityHomeManagement.java"'a gidiliyor ve oradan geri geliniyor.
         * 2. "FragmentMe.java" üzerinden "Ev Yönetimi" menüsi ile "ActivityHomeManagement.java"'a gidiliyor ve oradan geri geliniyor.
         * Eğer "Evim" tabı üzerinden gidilmişse,ordan dönerken null dönüyor ve default olarak "Evim" tabı seçili halde görüntüleniyor.
         * Ancak "Ben" tabı üzerinden gidilip dönüldüğünde bunu ayrıştırma yapmalıyız ve "Ben" tabını seçili hale getirmeliyiz.
         *
         * NOT2: Ayrıca
         * -"ActivitySmartSettings.java"
         * -"ActivityAutomationSettings.java"
         * -"Scan__"
         * -PersonalCenter.java
         * -MessageCenter.java
         * -HelpCenter.java
         * -MoreServices.java
         * -Settings.java
         * üzerinden de geliniyor.
         */
        String where_come_from = getIntent().getStringExtra(BetweenActivities.where_come_from);
        MyLog.d("kerim[ActivityMainPage]" , "where_come_from:" + where_come_from);
        if(where_come_from !=null) {

            if(where_come_from.equals("ActivityHomeManagement.java:HOME")
               || where_come_from.equals("ActivityAddDevices.java:HOME")){
                bottomNavigationView.setSelectedItemId(R.id.navigation_Item_Home);

            } else if(where_come_from.equals("ActivityHomeManagement.java:ME")){
                bottomNavigationView.setSelectedItemId(R.id.navigation_Item_Me);

            }else if (where_come_from.equals("ActivitySmartSettings.java:SMART")) {
                setTabIndexForFragmentSmart(0);//for "Görünüm" tab.
                bottomNavigationView.setSelectedItemId(R.id.navigation_Item_Smart);

            } else if (where_come_from.equals("ActivityAutomationSettings.java:SMART")) {
                //for Autonom
                setTabIndexForFragmentSmart(1);//for "Otomasyon" tab.
                bottomNavigationView.setSelectedItemId(R.id.navigation_Item_Smart);

            } else if (where_come_from.equals("ActivitySmartScenes.java:SMART")){
                String which_tab = getIntent().getStringExtra(BetweenActivities.SCENE_OR_AUTOMATION);
                if(which_tab.equals(BetweenActivities.SCENE)) {
                    setTabIndexForFragmentSmart(0);//for "Görünüm" tab.
                    bottomNavigationView.setSelectedItemId(R.id.navigation_Item_Smart);
                }else if(which_tab.equals(BetweenActivities.AUTOMATION)){
                    setTabIndexForFragmentSmart(1);//for "Otomasyon" tab.
                    bottomNavigationView.setSelectedItemId(R.id.navigation_Item_Smart);
                }

            }else if(where_come_from.equals("ActivityPersonalCenter.java:ME")
                    || where_come_from.equals("ActivityMessageCenter.java:ME")
                    || where_come_from.equals("ActivityHelpCenter.java:ME")
                    || where_come_from.equals("ActivitySettings.java:ME")){
                bottomNavigationView.setSelectedItemId(R.id.navigation_Item_Me);
            }//....

        }//if where_come_from !=null

        ///for RETURN BACK:@}



    }//onCreate


    private int fragmentsmart_tabindex = 0;
    private void setTabIndexForFragmentSmart(int index){
        fragmentsmart_tabindex = index;
    }
    private int getTabIndexForFragmentSmart(){
        return  fragmentsmart_tabindex;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            ///(menü item. changed title and/or icon):@{
            Menu menu = bottomNavigationView.getMenu();
            /*//disabled:because we set it as default in the "navigation.xml"
            menu.findItem(R.id.navigation_Item_Home).setIcon(R.drawable.home_icon_grey);
            menu.findItem(R.id.navigation_Item_Smart).setIcon(R.drawable.smart_icon_gray);
            menu.findItem(R.id.navigation_Item_Me).setIcon(R.drawable.me_icon_gray);
            */
            ///(menü item. changed title and/or icon):@}

            switch (item.getItemId()) {
                case R.id.navigation_Item_Home:
                    //MyLog.d(TAG,"Evim");
                    fragment = new FragmentHome();
                    loadFragment(fragment);
                    menu.findItem(R.id.navigation_Item_Home).setIcon(R.drawable.home_icon_color);//changed icon
                    return true;
                case R.id.navigation_Item_Smart:
                    //MyLog.d(TAG,"Akilli");
                    fragment = new FragmentSmart(getTabIndexForFragmentSmart());
                    loadFragment(fragment);
                    menu.findItem(R.id.navigation_Item_Smart).setIcon(R.drawable.smart_icon_gray);//changed icon
                    return true;
                case R.id.navigation_Item_Me:
                    //MyLog.d(TAG,"Ben");
                    fragment = new FragmentMe();
                    loadFragment(fragment);
                    menu.findItem(R.id.navigation_Item_Me).setIcon(R.drawable.me_icon_gray);//changed icon
                    return true;
            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            if(exitToast.getView().isShown())
               this.finishAffinity();
            else
                exitToast.show();
        }
        else
            return super.onKeyDown(keyCode, event);

        return false;
    }
}
