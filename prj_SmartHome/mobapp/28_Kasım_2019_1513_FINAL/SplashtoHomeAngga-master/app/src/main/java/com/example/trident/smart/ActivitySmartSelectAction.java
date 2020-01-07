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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.MyLog;

public class ActivitySmartSelectAction extends AppCompatActivity implements FragmentHome.OnFragmentInteractionListener,
                                                                            Fragment_FragmentHome_RoomDevices.OnFragmentInteractionListener,
                                                                            Fragment_FragmentHome_BlankRoomContent.OnFragmentInteractionListener{
    /**
     * used by "ActivitySmartSettings.java","ActivityAutomationSettings.java","ActivityAutomationSelectCondition.java"
     *
     * Sayfa: "İşlem Seçin"
     * Senaryo ve Otomasyon için aksiyon seçme sayfası.
     * //Ayrıca Condition için Cihaz seçim amaçlı kullanılır.
     */
    private static String FOR_WHAT ="";
    private static String WHERE_COME_FROM ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_select_action);
        //
        LinearLayout linearLayout_Back       = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                   = (Button)findViewById(R.id.button_Back);
        LinearLayout linearLayout_TopLayout  = (LinearLayout)findViewById(R.id.linearLayout_TopLayout);
        TextView textView_label_Automation   = (TextView)findViewById(R.id.textView_label_Automation);
        TextView textView_Automation         = (TextView)findViewById(R.id.textView_Automation);
        ImageView imageView_Automation_arrow = (ImageView)findViewById(R.id.imageView_Automation_arrow);
        TextView textView_label_TimeLapse    = (TextView)findViewById(R.id.textView_label_TimeLapse);
        ImageView imageView_TimeLapse_arrow  = (ImageView)findViewById(R.id.imageView_TimeLapse_arrow);
        TabLayout tabLayout_Rooms            = (TabLayout)findViewById(R.id.tabLayout_Rooms);
        ViewPager viewPager                  = (ViewPager)findViewById(R.id.viewPager);
        ///for test:
        String for_what = getIntent().getStringExtra(BetweenActivities.for_what);
        String where_come_from = getIntent().getStringExtra(BetweenActivities.where_come_from);
        //MyLog.d("kerim[ActivitySmartSelection]", "for_what:" + for_what);
        //MyLog.d("kerim[ActivitySmartSelection]", "where_come_from:" + where_come_from);
        if(for_what !=null)
            FOR_WHAT = for_what;

        if(FOR_WHAT.equals("Add_Condition"))
            linearLayout_TopLayout.setVisibility(View.GONE);//hide top panel if we came here for "Add_Condition"

        if(where_come_from !=null)
            WHERE_COME_FROM = where_come_from;

           // MyLog.d("kerim","what:"+ FOR_WHAT +",come:" + WHERE_COME_FROM);
        /**TODO:
         * print automation quantity information to be triggered to "textView_Automation".
         */
        //fixme: exmaple - representative value !!!!!!!!!!!!!!!
        textView_Automation.setText("1 " + getString(R.string.will_be_trigged));

        ///Event:@{
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
        textView_label_Automation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAutotomationSelectPage();
            }
        });
        textView_Automation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAutotomationSelectPage();
            }
        });
        imageView_Automation_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAutotomationSelectPage();
            }
        });

        textView_label_TimeLapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                goToTimeLapsePage();
            }
        });
        imageView_TimeLapse_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                goToTimeLapsePage();
            }
        });
        ///event:@}

        ///:@{
        /**
         * Room'lar db'den çekilir.
         * Room sayısı room_count'a atanır.
         * For içinde:eğer room içinde cihaz varsa "Fragment_FragmentHome_RoomDevices" atanır. Aksi halde "Fragment_FragmentHome_BlankRoomContent" atanır.
         *
         */
        int room_count = 10;
        Fragment[] childFragments = new Fragment[room_count];
        String[] room_names = new String[room_count];
        String[] device_names = {//TODO: get thus values from database
                "Lamba_1",
                "Lamba_2",
                "Plug Mutfak"
        };
        String[] device_location = {
          "Mutfak",
          "Mutfak",
          "Çalışma Odası"
        };

        String[] devices_type = {//TODO: get thus values from database
                "lamp",
                "lamp",
                "wall_plug"
        };
        String[] devices_state = {//TODO: get thus values from database
                "Online",
                "Online",
                "Offline",
        };

        for(int i=0;i<room_count;i++){
            /**
             * Her döngüde oda ismi ve ilgili oda ismine ait cihaz isimleri "Fragment_FragmentHome_RoomDevices"'a dizi olarak tek seferde parametre gidilir.
             * Fragment_FragmentHome_RoomDevices.class tarafında ise oda ismi tablayout:tab'a title olarak atanır Ve cihaz isimleri "device_names" dizisine atanır.
             * Böylece her odaya ait tab ve cihaz isimleri listView'de yerini alır.
             *              *
             */
            if(i%2 == 1){//TODO: #############"if" for test.####################
                //TODO:if no room content,make new "Fragment_FragmentHome_BlankRoomContent()" object.
                childFragments[i] = new Fragment_FragmentHome_BlankRoomContent();

                if(i==0)//TODO:first tab should be always "All Devices"
                    room_names[0] = getString(R.string.all_devices);
                else
                    room_names[i] = "Room" +i;
            }else{
                //TODO: if content exists,make new "Fragment_FragmentHome_RoomDevices" object.
                childFragments[i] = new Fragment_FragmentHome_RoomDevices(WHERE_COME_FROM,"ActivitySmartSelectAction.java",devices_type,device_names,devices_state,
                        device_location,FOR_WHAT);
                if(i==0)//TODO:first tab should be always "All Devices"
                    room_names[0] = getString(R.string.all_devices);
                else
                    room_names[i] = "Room" +i;
            }

            /*childFragments[i] = new Fragment_FragmentHome_RoomDevices("room name:" + i,device_names);
            room_names[i] = "Room" +i;*/
        }
         /*Fragment[] childFragments = new Fragment[] {
                new Fragment_FragmentHome_RoomDevices(),
                new Fragment_FragmentHome_BlankRoomContent()

        };*/
        tabLayout_Rooms.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),childFragments,room_names));
        tabLayout_Rooms.setupWithViewPager(viewPager);
        ///:@}

    }//onCreate

    private void goToAutotomationSelectPage(){
        //todo: complete this.!!
        Intent intent = new Intent(getApplicationContext(),ActivityAutomationSelect.class);
        intent.putExtra("upper_class",WHERE_COME_FROM);
        intent.putExtra(BetweenActivities.where_come_from,"ActivitySmartSelectAction.java");//will be needed on the way back to here.
        startActivity(intent);
    }

    private void goToTimeLapsePage(){
        Intent intent = new Intent(getApplicationContext(),ActivityTimeLapse.class);
        intent.putExtra("upper_class",WHERE_COME_FROM);
        intent.putExtra(BetweenActivities.where_come_from,"ActivitySmartSelectAction.java");
        startActivity(intent);
    }

    private void goToBack(){
        //returning without any action
        Intent intent = null;
        if(WHERE_COME_FROM.equals("ActivitySmartSettings.java"))
            intent = new Intent(getApplicationContext(), ActivitySmartSettings.class);
        else if(WHERE_COME_FROM.equals("ActivityAutomationSettings.java"))
            intent = new Intent(getApplicationContext(), ActivityAutomationSettings.class);
        else if(WHERE_COME_FROM.equals("ActivityAutomationSelectCondition.java"))
            intent = new Intent(getApplicationContext(), ActivityAutomationSelectCondition.class);

        intent.putExtra(BetweenActivities.for_what, FOR_WHAT);
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
