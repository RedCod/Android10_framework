package com.example.trident.smart.custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.MyLog;
import com.example.trident.smart.ActivitySmartSettings;
import com.example.trident.smart.R;

public class CustomListAdapter_FragmentSmart_ScenarioList extends BaseAdapter {
    /**
     * ListView(custom) for "FragmentSmart.java > Fragment_FragmentSmart_ScenarioList.java"
     * "Smart" sekmesinde "Görünüm" içeriğinin(senaryolar) bulunduğu ve yönetildiği sayfa.
     */
    /**
     * FIXME:if the problem persists = https://www.itworld.com/article/2705632/how-to-make-smooth-scrolling-listviews-in-android.html
     */

    static  class ViewHolder{
        LinearLayout linearLayout_ScenarioItem;
        TextView textView_ScenarioName;
        Button button_PerformScenario;
    }

    private final Activity activity;
    private final String[] item_name;
    private LayoutInflater layoutInflater;
    private Context context;
    private ListView listView;
    private boolean first_running = false;
    public CustomListAdapter_FragmentSmart_ScenarioList(Activity _activity, String[] _itemname, ListView listView) {

        this.layoutInflater = (LayoutInflater.from(_activity.getApplicationContext()));
        this.activity = _activity;
        this.context = _activity.getApplicationContext();
        this.item_name = _itemname;
        this.listView = listView;
        first_running = true;
    }
    /**
     * todo:
     * The pictures to be used for the scenario we will be kept in application. We will store just picture name or id in database.
     * we will extract the id from the database and compare to project contents with pictures.
     *
     */
    Integer[] scenario_item_pictures = {
            R.drawable.night
            //todo:and other pictures..
    };

    private static int scrool_position = 0;//for scroll
    ViewHolder viewHolder;
    public View getView(final int position, View itemView, ViewGroup parent) {
        //LayoutInflater inflater = activity.getLayoutInflater();
        if(itemView == null) {
            itemView = layoutInflater.inflate(R.layout.listview_scenariolist_fragment_smart, null, true);
            viewHolder = new ViewHolder();
            viewHolder.linearLayout_ScenarioItem = (LinearLayout) itemView.findViewById(R.id.linearLayout_ScenarioItem);
            viewHolder.textView_ScenarioName     = (TextView) itemView.findViewById(R.id.textView_ScenarioName);
            viewHolder.button_PerformScenario    = (Button) itemView.findViewById(R.id.button_PerformScenario);
            itemView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)itemView.getTag();
        }


        //for test:
          listView.setOnScrollListener(new AbsListView.OnScrollListener() {
              @Override
              public void onScrollStateChanged(AbsListView absListView, int i) {
              }

              @Override
              public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                 // MyLog.d("kerim","onScroll > i:" + i + ",i1:" + i1 + ",i2:" + i2);
                  //scrool_position = i;
              }
          });


        viewHolder.linearLayout_ScenarioItem.setBackgroundResource(scenario_item_pictures[0]);
        viewHolder.textView_ScenarioName.setText(item_name[position]);

        viewHolder.linearLayout_ScenarioItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrool_position = position;
                if(MyLog.DEGUB) {
                    MyLog.d("kerim", "scrool_position1:" + scrool_position + " count1:" + (item_name.length) + ",count2:" + position);
                    MyLog.d("kerim", "linearlayout click:" + item_name[position]);
                }
                Intent intent = new Intent(activity.getApplicationContext(), ActivitySmartSettings.class);
                intent.putExtra(BetweenActivities.page_title,activity.getString(R.string.Edit));
                intent.putExtra(BetweenActivities.scenario_name,viewHolder.textView_ScenarioName.getText());
                intent.putExtra(BetweenActivities.for_what,"Edit");//for what
                activity.startActivity(intent);
            }
        });
        viewHolder.button_PerformScenario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: Perform.
                MyLog.d("kerim","Button click:" + item_name[position]);
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

   /* public String getItemName(int position){
        return itemname[position];
    }
    public String getItemNameDesicription(int position){
        return  desicription[position];
    }
    */

    public int getCount() {return item_name.length ;}

    @Override
    public Object getItem(int i) {
        return item_name[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

}

