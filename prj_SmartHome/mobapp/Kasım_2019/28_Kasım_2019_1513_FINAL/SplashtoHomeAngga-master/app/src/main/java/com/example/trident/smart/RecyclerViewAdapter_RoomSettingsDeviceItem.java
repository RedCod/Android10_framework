package com.example.trident.smart;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class RecyclerViewAdapter_RoomSettingsDeviceItem  extends RecyclerView.Adapter<RecyclerViewAdapter_RoomSettingsDeviceItem.MyViewHolder>
        implements ItemMoveCallback_RoomSettingsDeviceItem.ItemTouchHelperContract{
    public static boolean ROOM_ITEM_DRAGDROP = false;//

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_Title;
        private TextView textView_TextView_Describtion;
        private ImageView imageView_IconPlusOrMinus;
        private ImageView imageView_IconDevice;
        View rowView;
        public MyViewHolder(View itemView) {
            super(itemView);
            rowView                       = itemView;
            textView_Title                = itemView.findViewById(R.id.textView_Title);
            textView_TextView_Describtion = itemView.findViewById(R.id.textView_Describtion);
            imageView_IconPlusOrMinus     = itemView.findViewById(R.id.imageView_IconPlusOrMinus);
            imageView_IconDevice          = itemView.findViewById(R.id.imageView_IconDevice);
        }
    }
    Context context;
    int icon_minus = R.drawable.minus2;
    ////
    private ArrayList<String> arrayList_Data;
    private ArrayList<String> arrayList_DeviceLocation;
    private ArrayList<Integer> arrayList_DeviceIcon;

    public RecyclerViewAdapter_RoomSettingsDeviceItem(Context _context,ArrayList<String> _arrayList_Data,ArrayList<String> _arrayList_DeviceLocation,ArrayList<Integer> _arrayList_DeviceIcon) {
        //(Context context, int layout_resource_id,List<String> objects,String[] _device_location,Integer[] _icondevice)
        this.context                  = _context;
        this.arrayList_Data           = _arrayList_Data;
        this.arrayList_DeviceLocation = _arrayList_DeviceLocation;
        this.arrayList_DeviceIcon     = _arrayList_DeviceIcon;
    }

    @Override
    public  MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_roomsettings_devices_item, parent, false);
        return new  MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, final int position) {
        holder.textView_Title.setText(arrayList_Data.get(position));
        holder.textView_TextView_Describtion.setText(arrayList_DeviceLocation.get(0));//TODO: assign device location from getting database.
        holder.imageView_IconPlusOrMinus.setImageResource(icon_minus);
        holder.imageView_IconDevice.setImageResource(arrayList_DeviceIcon.get(0));//TODO: assign device location from getting database.

        holder.imageView_IconPlusOrMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: delete this device from this(edited room) room. so take the device out of the room. And then put(save) this room to the "Tüm cihazlar" section.
                //TODO: NOTE: When the device is transferred to another room, it is deleted from the other(any) room.
                //...
                ///..
                ActivityRoomSettings.IS_ROOM_SETTINGS_CHANGE = true;//settings change ok.
                //add to below ListView(list_forListDevices):
                ActivityRoomSettings.setList_forListDevices(arrayList_Data.get(position));//(getItem(position));
                ActivityRoomSettings.removeInList_fromListDevicesInRoom(position);

                ActivityRoomSettings.setButton_Save_Visible(true);//
            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayList_Data.size();
    }

    /**
     * get all items as ArrayList.
     * @return arrayList_Data
     */
    public ArrayList<String> getItemsAsArrayList(){
        return arrayList_Data;
    }

    /**
     * get item on "arrayList_Data".
     * @param index array index.
     * @return arrayList_Data,so main item.
     */
    public String getItem(int index){
        return arrayList_Data.get(index);
    }

    /**
     * get all Items as ArrayList.
     * @return arrayList_DeviceLocation as ArrayList
     */
    public ArrayList<String> getItems2AsArrayList(){
        return arrayList_DeviceLocation;
    }
    /**
     * get item on "arrayList_DeviceLocation"
     * @param index array index
     * @return arrayList_DeviceLocation,so second item.
     */
    public String getItem2(int index){
        return  arrayList_DeviceLocation.get(index);
    }


    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(arrayList_Data, i, i + 1);
                //for location moving:
                Collections.swap(arrayList_DeviceLocation,i,i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(arrayList_Data, i, i - 1);
                //for location moving:
                Collections.swap(arrayList_DeviceLocation,i,i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        setRoomItemDragDropChanges(true);
        ActivityRoomSettings.setButton_Save_Visible(true);
    }
    @Override
    public void onRowSelected(MyViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(context.getResources().getColor(R.color.color_my_theme)); //(Color.GRAY);

    }

    @Override
    public void onRowClear(MyViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.WHITE);

    }

    //add by raptiye:
    public static void setRoomItemDragDropChanges(boolean _state){
        ROOM_ITEM_DRAGDROP = _state;
    }
    public static boolean isRoomItemDragDropChanges(){
        return ROOM_ITEM_DRAGDROP;
    }


}
