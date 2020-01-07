package com.example.trident.smart.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trident.common.DeviceIcon;
import com.example.trident.common.DeviceType;
import com.example.trident.common.MyLog;
import com.example.trident.smart.R;

import java.util.ArrayList;

public class CustomListAdapter_AutomationSettings extends BaseAdapter {

    /**
     * using by "ActivityAutomationSettings.java"
     * for "listView_ConditionsList" content.
     */
    private LayoutInflater inflter;
    private Context context;
    ArrayList<SmartAndAutomationItem> arrayListAutomationConditionItems;
    SmartAndAutomationItem smartAndAutomationItem;
    public CustomListAdapter_AutomationSettings(Context _context, ArrayList arrayList){
        inflter = (LayoutInflater.from(_context));
        this.context = _context;
        arrayListAutomationConditionItems = arrayList;
    }
    @Override
    public int getCount() {
        return arrayListAutomationConditionItems.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.listview_automation_condition_item, null);
        ImageView imageView_Icon           = (ImageView)view.findViewById(R.id.imageView_Icon);
        TextView textView_Item             = (TextView)view.findViewById(R.id.textView_Item);
        TextView textView_ItemDescribtion  = (TextView)view.findViewById(R.id.textView_ItemDescribtion);
        smartAndAutomationItem = arrayListAutomationConditionItems.get(position);
        if(smartAndAutomationItem.getType().equals(DeviceType.TEMPERATURE))
            imageView_Icon.setImageResource(DeviceIcon.TEMPERATURE);
        else if(smartAndAutomationItem.getType().equals(DeviceType.HUMIDITY))
            imageView_Icon.setImageResource(DeviceIcon.HUMIDITY);
        else if(smartAndAutomationItem.getType().equals(DeviceType.WEATHER))
            imageView_Icon.setImageResource(DeviceIcon.WEATHER);
        else if(smartAndAutomationItem.getType().equals(DeviceType.AIR_QUALITY))
            imageView_Icon.setImageResource(DeviceIcon.AIR_QUALITY);
        else if(smartAndAutomationItem.getType().equals(DeviceType.SUNRISE_SUNSET))
            imageView_Icon.setImageResource(DeviceIcon.SUNRISE_SUNSET);
        else if(smartAndAutomationItem.getType().equals(DeviceType.SCHEDULE))
            imageView_Icon.setImageResource(DeviceIcon.SCHEDULE);
        else if(smartAndAutomationItem.getType().equals(DeviceType.LAMP))
            imageView_Icon.setImageResource(DeviceIcon.LAMP);
        else if(smartAndAutomationItem.getType().equals(DeviceType.WALL_PLUG))
            imageView_Icon.setImageResource(DeviceIcon.WALL_PLUG);
       /* else if(smartAndAutomationItem.getType().equals(DeviceType.SWITCH))
            imageView_Icon.setImageResource(DeviceIcon.SWITCH);*/
        textView_Item.setText(smartAndAutomationItem.getName());
        textView_ItemDescribtion.setText(smartAndAutomationItem.getLocation());
        return view;
    }

}
