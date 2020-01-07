package com.example.trident.smart;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.GUI;
import com.example.trident.common.Static;
import com.example.trident.smart.custom.CustomListAdapter_HelpCenter;

import java.util.Arrays;
import java.util.List;

public class ActivityHelpCenter extends AppCompatActivity {

    /**
     * used by "FragmentMe.java"
     * "Yardım Merkezi ("Help Center")
     *
     *
     */

    ScrollView scrollView_Based;
    LinearLayout linearLayout_BaseFaqTitles;
    ListView listView_FaqTitles;
    CustomListAdapter_HelpCenter customListAdapterHelpCenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
        //
        LinearLayout linearLayout_Back               = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                           = (Button)findViewById(R.id.button_Back);
        /*ScrollView*/ scrollView_Based                = (ScrollView)findViewById(R.id.scrollView_Based);
        LinearLayout linearLayout_GoToMyFeedbackPage = (LinearLayout)findViewById(R.id.linearLayout_GoToMyFeedbackPage);
        linearLayout_BaseFaqTitles                   = (LinearLayout)findViewById(R.id.linearLayout_BaseFaqTitles);
        listView_FaqTitles                           = (ListView)findViewById(R.id.listView_FaqTitles);


        ///Default:
        String[] FAQ_Titles = new String[] {
                "How to share my device for family use?",
                "How to add device in App for internet(Wi-Fi) connection?",
                "How do I set my device to network(Wi-Fi) connection status?",
                "Device connection failure?",
                "What if the device is offline?",
                "SSS 6",
                "SSS 7",
                "SSS 8",
                "SSS 9",
                "SSS 10",
                "SSS 11",
                "SSS 12",
                "SSS 13",
                "SSS 14",
                "SSS 15"


        };
         List<String> listFAQTitles = Arrays.asList(FAQ_Titles);
         customListAdapterHelpCenter = new CustomListAdapter_HelpCenter(this,listFAQTitles);
         listView_FaqTitles.setAdapter(customListAdapterHelpCenter);
        //resizeLayoutBaseFAQHeight(customListAdapterHelpCenter.getCount());
        GUI gui = new GUI();
        gui.dynamicallySizingLayout(linearLayout_BaseFaqTitles,listView_FaqTitles,customListAdapterHelpCenter,customListAdapterHelpCenter.getCount(),45);

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
        linearLayout_GoToMyFeedbackPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMyFeedbackPage();
            }
        });
        listView_FaqTitles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String faq_title = customListAdapterHelpCenter.getItem(i).toString();
                //todo: go to FAQLoad page:
                Static.CURRENT_SCROLL_POSITION = scrollView_Based.getScrollY();
                goToFAQViewPage();
            }
        });

        ///events:@}
        //for scrollView position[081120191655]:@{
        scrollView_Based.post(new Runnable() {
            @Override
            public void run() {
                if(Static.CURRENT_SCROLL_POSITION == 0)
                    scrollView_Based.pageScroll(View.FOCUS_UP);
                else
                    scrollView_Based.scrollTo(0,Static.CURRENT_SCROLL_POSITION);
            }//run
        });
        //for scrollView position[081120191655]:@}

    }//onCreate

    private void resizeLayoutBaseFAQHeight(int count){
        /**
         * FIXME: BU FONKSİYONUN OLDUĞU TÜM LAYOUTLARI TEK YERDEN ÇAĞIR.
         */
        ///for Layout Resize:@{
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) linearLayout_BaseFaqTitles.getLayoutParams();
        final int dimension = count;//90;//ATTENTION:do not touch this value. default = 90
        int total_height = 45;//((count < 20 )? 45 : -45);//dimension;
        int item_count = count;
        View item;
        for(int i=0; i<item_count;i++){
            item = customListAdapterHelpCenter.getView(i,null,listView_FaqTitles);
            item.measure(0,0);
            total_height += item.getMeasuredHeight();
        }
        layoutParams.height = total_height + (1 * (item_count));
        linearLayout_BaseFaqTitles.setLayoutParams(layoutParams);
        linearLayout_BaseFaqTitles.requestLayout();
        layoutParams = (ConstraintLayout.LayoutParams) linearLayout_BaseFaqTitles.getLayoutParams();
        ///for Layout Resize:@}
        //
        ///for Listview resize:@{
        ViewGroup.LayoutParams params2 = listView_FaqTitles.getLayoutParams();
        params2.height = layoutParams.height;
        listView_FaqTitles.setLayoutParams(params2);
        listView_FaqTitles.requestLayout();
        ////for Listview resize:@}
    }
    private void goToMyFeedbackPage(){
        Intent intent = new Intent(getApplicationContext(),ActivityFeedback.class);
        intent.putExtra(BetweenActivities.where_come_from,"ActivityHelpCenter.java");
        startActivity(intent);
    }
    private void goToFAQViewPage(){
        Intent intent = new Intent(getApplicationContext(),ActivityFAQView.class);
        startActivity(intent);
    }

    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivityMainPage.class);
        intent.putExtra(BetweenActivities.where_come_from,"ActivityHelpCenter.java:ME");
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
