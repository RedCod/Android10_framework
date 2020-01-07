package com.example.trident.smart.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trident.smart.R;

import java.util.Collection;
import java.util.HashMap;

public class CustomListAdapter_CreateDevicesGroup extends BaseAdapter {//call by "ActivityCreateDevicesGroup.java"

    private LayoutInflater inflter;
    private Context context;
    private String[] device_name;
    private String[] device_description;
    private Boolean[] checked;
    boolean first_running = true;
    public CustomListAdapter_CreateDevicesGroup(Context _context,String[] _device_name,String[] _device_description){
        //
        inflter = (LayoutInflater.from(_context));
        this.context = _context;
        device_name   = _device_name;
        device_description = _device_description;

        hashMap_SelectedDevices.clear();
        checked = new Boolean[device_name.length];
        for(int i=0;i<_device_name.length;i++){
            checked[i] = true;
            hashMap_SelectedDevices.put(i,device_name[i]);
        }
    }

    @Override
    public int getCount() {
        return device_name.length;

    }

    @Override
    public Object getItem(int i) {
        return device_name[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * get
     * @return
     */
    public Collection<String> getSelectedDevices(){
        return hashMap_SelectedDevices.values();
    }

    private static HashMap<Integer,String> hashMap_SelectedDevices = new HashMap<Integer,String>();
    public View getView(final int _position, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.listview_create_devices_group, null);
        LinearLayout linearLayout_Main_CreateDevicesGroup = (LinearLayout)view.findViewById(R.id.linearLayout_Main_CreateDevicesGroup);
        TextView textView_Item                = (TextView)view.findViewById(R.id.textView_Item);
        TextView textView_ItemDescribtion     = (TextView)view.findViewById(R.id.textView_ItemDescribtion);
        final CheckedTextView checkBox_DeviceSelect = (CheckedTextView)view.findViewById(R.id.checkBox_DeviceSelect);
        textView_Item.setText(device_name[_position]);
        textView_ItemDescribtion.setText(device_description[_position]);
        if(first_running == false)
            checkBox_DeviceSelect.setChecked(checked[_position]);

        if(checkBox_DeviceSelect.isChecked())
            hashMap_SelectedDevices.put(_position,device_name[_position]);
        else
            hashMap_SelectedDevices.remove(_position);


        linearLayout_Main_CreateDevicesGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox_DeviceSelect.isChecked()){
                    checkBox_DeviceSelect.setChecked(false);
                    int index = _position;
                    hashMap_SelectedDevices.remove(_position);
                    checked[_position] = false;
                }else{
                    checkBox_DeviceSelect.setChecked(true);
                    hashMap_SelectedDevices.put(_position,device_name[_position]);
                    checked[_position] = true;
                }
            }
        });
       /*//It's not necessary
        checkBox_DeviceSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckedTextView)view).isChecked()){
                    ((CheckedTextView)view).setChecked(false);
                    checked[_position] = false;
                }else{
                    ((CheckedTextView)view).setChecked(true);
                    checked[_position] = true;
                }
            }
        });*/
        first_running = false;
        return view;
    }
}
