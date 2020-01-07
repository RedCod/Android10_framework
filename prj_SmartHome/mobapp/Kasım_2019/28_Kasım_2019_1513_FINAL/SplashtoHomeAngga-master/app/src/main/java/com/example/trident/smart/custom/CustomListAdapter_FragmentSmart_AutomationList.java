package com.example.trident.smart.custom;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.MyLog;
import com.example.trident.smart.ActivityAutomationSettings;
import com.example.trident.smart.R;


public class CustomListAdapter_FragmentSmart_AutomationList extends ArrayAdapter<String> {
    /**
     * ListView(custom) for "FragmentSmart.java > Fragment_FragmentSmart_AutomationList.java"
     *
     * Otomasyonların listelendiği sayfa.
     */
    private final Activity activity;
    private final String[] item_name;
    private ListView listView;
    private boolean first_running = false;
    public CustomListAdapter_FragmentSmart_AutomationList(Activity _activity, String[] _itemname, ListView listView) {
        super(_activity, R.layout.listview_automationlist_fragment_smart, _itemname);
        this.activity  = _activity;
        this.item_name = _itemname;
        this.listView  = listView;
        first_running  = true;
    }
    /**
     * todo:
     * The pictures to be used for the scenario we will be kept in application. We will store just picture name or id in database.
     * we will extract the id from the database and compare to project contents with pictures.
     *
     */
    Integer[] scenario_item_pictures = {
            R.drawable.automation
            //todo:and other pictures..
    };

    private static int scrool_position = 0;//for scroll
    LayoutInflater inflater;
    public View getView(final int position, View itemView, ViewGroup parent) {
         inflater = activity.getLayoutInflater();
        itemView                                 = inflater.inflate(R.layout.listview_automationlist_fragment_smart, null,true);
        LinearLayout linearLayout_AutomationItem = (LinearLayout)itemView.findViewById(R.id.linearLayout_AutomationItem);
        final TextView textView_AutomationName   = (TextView)itemView.findViewById(R.id.textView_AutomationName);
        final Switch switchButton_ActiveInactive = (Switch) itemView.findViewById(R.id.switchButton_ActiveInactive);
        linearLayout_AutomationItem.setBackgroundResource(scenario_item_pictures[0]);
        textView_AutomationName.setText(item_name[position]);

        linearLayout_AutomationItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrool_position = position;
                MyLog.d("kerim","Automation linearlayout click:" + item_name[position]);
                Intent intent = new Intent(activity.getApplicationContext(), ActivityAutomationSettings.class);
                intent.putExtra(BetweenActivities.page_title,activity.getString(R.string.automation_edit));
                intent.putExtra(BetweenActivities.automation_name,textView_AutomationName.getText());
                intent.putExtra(BetweenActivities.for_what,"Edit");//for what.
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.getApplication().startActivity(intent);
            }
        });
        switchButton_ActiveInactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.d("kerim","Automation switch click:" + item_name[position] + ",checked:" + switchButton_ActiveInactive.isChecked());
            }
        });

        ///scroll: Scroll to where we leaved this page.
        if(first_running == true){ //don't work on every refresh.
            notifyDataSetChanged();
            listView.smoothScrollToPositionFromTop(scrool_position, 0, 0);
            first_running = false;
        }
        return itemView;
    };

     public String getItemName(int position){
         return item_name[position];
     }
     public int getCount() {return item_name.length ;}
}

