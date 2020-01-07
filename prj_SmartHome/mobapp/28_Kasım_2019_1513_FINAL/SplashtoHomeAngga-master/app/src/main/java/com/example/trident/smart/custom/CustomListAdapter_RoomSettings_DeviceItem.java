package com.example.trident.smart.custom;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.smart.ActivityRoomSettings;
import com.example.trident.smart.DynamicListAdapter_RoomSettings_DeviceItem;
import com.example.trident.smart.R;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter_RoomSettings_DeviceItem extends ArrayAdapter<String> {

    /**
     * //ListView(custom) for "ActivityRoomSettings.java"
     */
    private final Activity activity;
    private  String[] itemname;
    private final Integer[] device_icon;
    private final String[] desicription;

    //for test:
    List<String> listDevices = new ArrayList<String>();
    public CustomListAdapter_RoomSettings_DeviceItem(Activity _activity, /*String[] _devices*/ArrayList<String> _devices, Integer[] _device_icon, String[] _device_location) {
        super(_activity, R.layout.listview_roomsettings_devices_item, _devices);

        this.activity     = _activity;
        //this.itemname     = _devices
        //////////////
        itemname = new String[_devices.size()];
        for(int i=0;i<_devices.size();i++){
            itemname[i] = _devices.get(i);
        }
        listDevices = _devices;
        //////////

        this.device_icon  = _device_icon;
        this.desicription = _device_location;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView            = inflater.inflate(R.layout.listview_roomsettings_devices_item, null,true);
        TextView textView_Title        = (TextView)rowView.findViewById(R.id.textView_Title);
        ImageView imageView_IconPlus   = (ImageView)rowView.findViewById(R.id.imageView_IconPlusOrMinus);
        ImageView imageView_IconDevice = (ImageView)rowView.findViewById(R.id.imageView_IconDevice);
        TextView textView_Description  = (TextView)rowView.findViewById(R.id.textView_Describtion);

        textView_Title.setText(listDevices.get(position));//(itemname[position]);
        //imageView_IconPlus.setImageResource(R.drawable.plus_flat);//set in xml
        imageView_IconDevice.setImageResource(device_icon[0]);//TODO:set the device Image accordingly 'position'. Because each device has an icon-image.
        textView_Description.setText(desicription[0]);//TODO:set the device desicription(device location. Exp: Mutfak,Oturma Odası etc.) accordingly 'position'. Because each device has an device location info.

       //Click
        imageView_IconPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//add to ListView Devices in room".
                Toast.makeText(activity.getApplicationContext(),"Click Icon Plus. Add to TOP LİSTVİEW.", Toast.LENGTH_LONG).show();
                //TODO: delete this device from "Tüm cihazlar" section. so take the device out of the "Tüm cihazlar" section. And then put(save) this device to this(edited room) room.
                //TODO: NOTE: When the device is transferred to another room, it is deleted from the other(any) room.

                ///..
                ActivityRoomSettings.IS_ROOM_SETTINGS_CHANGE = true;//settings change ok.
                ///////////////////

                //add to TOP ListView(list_forListDevicesInRoom):
                ActivityRoomSettings.setList_forListDevicesInRoom(listDevices.get(position));
                DynamicListAdapter_RoomSettings_DeviceItem.addHashMap(listDevices.get(position));///////FOR
                //remove this item in this list:
                ActivityRoomSettings.removeInList_forListDevices(position);
                ActivityRoomSettings.setButton_Save_Visible(true);
            }
        });


        return rowView;
    };
}
