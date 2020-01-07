package com.example.trident.smart.custom;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trident.common.DeviceIcon;
import com.example.trident.common.DeviceType;
import com.example.trident.common.MyLog;
import com.example.trident.smart.R;

public class CustomListAdapter extends ArrayAdapter<String> {
    /**
     * ListView(custom) for "FragmentHome.java->Fragment_FragmentHome_RoomDevices.java,ActivitySharedDevice.java"
     */
    private final Activity activity;
    private final String[] device_name;
    private final String[] devices_type;
    private final String[] device_state;//online,offline


    public CustomListAdapter(Activity activity, String[] devices_type,String[] device_name,String[] devices_state) {
        super(activity, R.layout.listview_fragment_home, device_name);

        this.activity     = activity;
        this.device_name  = device_name;
        this.devices_type = devices_type;
        this.device_state = devices_state;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        view            = inflater.inflate(R.layout.listview_fragment_home, null,true);
        view.setMinimumHeight(100);
        TextView textView_title       = (TextView)view.findViewById(R.id.item);
        ImageView imageView           = (ImageView)view.findViewById(R.id.icon);
        TextView textView_description = (TextView)view.findViewById(R.id.item_describtion);

      /*  textView_title.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);
        textView_description.setText(desicription[position]);*/
        textView_title.setText(device_name[position]);
            if(devices_type[position].equals(DeviceType.LAMP))
                imageView.setImageResource(DeviceIcon.LAMP);
            else if(devices_type[position].equals(DeviceType.WALL_PLUG))
                imageView.setImageResource(DeviceIcon.WALL_PLUG);
            textView_description.setText(device_state[position]);
        return view;
    };

    public String getItemName(int position){
        return device_name[position];
    }
    public String getItemNameDesicription(int position){
        return  device_state[position];
    }
    public int getCount() {return device_name.length;}
}

