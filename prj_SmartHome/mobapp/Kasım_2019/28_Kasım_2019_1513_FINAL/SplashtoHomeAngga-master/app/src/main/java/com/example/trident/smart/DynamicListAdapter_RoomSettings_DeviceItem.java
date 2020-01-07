package com.example.trident.smart;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.MyLog;

import java.util.ArrayList;
import java.util.List;

public class DynamicListAdapter_RoomSettings_DeviceItem   extends ArrayAdapter<String> {

    final int INVALID_ID = -1;
    //FIX:to add similar device names to the list. Create ArrayList instead of HashMap list.
    //static HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
    static ArrayList<String> arrayList = new ArrayList<String>();

    Context context;
    int layout_resource_id = 0;
    String[] device_location;
    Integer[] icon_device;
    int icon_minus = R.drawable.minus2;
    public DynamicListAdapter_RoomSettings_DeviceItem(Context context, int layout_resource_id,List<String> objects,String[] _device_location,Integer[] _icondevice) {
        super(context, layout_resource_id, objects);
        this.layout_resource_id = layout_resource_id;
        this.context = context;
        MyLog.d("kertenS","objects.size():" + objects.size());
        for (int i = 0; i < objects.size(); ++i) {
            //hashMap.put(objects.get(i), i);
            arrayList.add(objects.get(i));
        }
        this.device_location    = _device_location;
        this.icon_device        = _icondevice;
    }
    /*private view holder class*/
    private class ViewHolder {
        TextView textView_Title;
        TextView textView_TextView_Describtion;
        ImageView imageView_IconPlusOrMinus;
        ImageView imageView_IconDevice;
    }
    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= arrayList.size()) {
            return INVALID_ID;
        }
        try {//FIXME: maybe.....     fix by raptiye.

            MyLog.d("kertenSs", "position:" + position + ",arrayList.size:"+arrayList.size() +",arrayList.indexOf:" + arrayList.indexOf(getItem(position)));
            //String item = getItem(position).toString();
            // return hashMap.get(item);
            return arrayList.indexOf(getItem(position));
        }catch (IndexOutOfBoundsException ioex){
            MyLog.d("kertenSs:","Exception:" + ioex.getMessage());
            //FIXME: I don't know why i set "1" for position. Please fix this.
            return  arrayList.indexOf(getItem(1));
        }
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        DynamicListAdapter_RoomSettings_DeviceItem.ViewHolder holder = null;
        //String rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(/*R.layout.listview_witharrowed*/layout_resource_id, null);
            holder = new DynamicListAdapter_RoomSettings_DeviceItem.ViewHolder();
            holder.textView_Title                = (TextView) convertView.findViewById(R.id.textView_Title);
            holder.textView_TextView_Describtion = (TextView)convertView.findViewById(R.id.textView_Describtion);
            holder.imageView_IconPlusOrMinus     = (ImageView)convertView.findViewById(R.id.imageView_IconPlusOrMinus);
            holder.imageView_IconDevice          = (ImageView)convertView.findViewById(R.id.imageView_IconDevice);
            convertView.setTag(holder);
        } else
            holder = (DynamicListAdapter_RoomSettings_DeviceItem.ViewHolder) convertView.getTag();

        holder.textView_Title.setText(getItem(position));
        holder.textView_TextView_Describtion.setText(device_location[0]);//TODO: assign device location from getting database.
        holder.imageView_IconPlusOrMinus.setImageResource(icon_minus);
        holder.imageView_IconDevice.setImageResource(icon_device[0]);//TODO: assign device location from getting database.
        //holder.imageView.setImageResource(rowItem.getImageId());

        holder.imageView_IconPlusOrMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context.getApplicationContext(),"Click İcon Minux. put in below LİSTVİEW.", Toast.LENGTH_LONG).show();
                //TODO: delete this device from this(edited room) room. so take the device out of the room. And then put(save) this room to the "Tüm cihazlar" section.
                //TODO: NOTE: When the device is transferred to another room, it is deleted from the other(any) room.
                //...
                ///..
                ActivityRoomSettings.IS_ROOM_SETTINGS_CHANGE = true;//settings change ok.

                //add to below ListView(list_forListDevices):
                ActivityRoomSettings.setList_forListDevices(getItem(position));//(getItem(position));
                //remove this item in this list:
                arrayList.remove(getItem(position));
                ActivityRoomSettings.removeInList_fromListDevicesInRoom(position);

                ///
            }
        });


        //MyLog.d("kertenS:","getCount:" + getCount() +",hashMap.size:" + arrayList.size());
        return convertView;
    }



    @Override
    public boolean hasStableIds() {
        return true;
    }

    public static void addHashMap(String item){
        //int index = arrayList.size()+1;
        //hashMap.put(item, index);
        arrayList.add(item);
    }
    public static int getListSize(){
        return arrayList.size();
    }

}
