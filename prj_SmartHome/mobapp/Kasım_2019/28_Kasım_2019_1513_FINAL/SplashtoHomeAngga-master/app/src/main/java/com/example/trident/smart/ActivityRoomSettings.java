package com.example.trident.smart;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.Alert;
import com.example.trident.common.Keyboard;
import com.example.trident.smart.custom.CustomListAdapter_RoomSettings_DeviceItem;

import java.util.ArrayList;
import java.util.Arrays;

public class ActivityRoomSettings extends AppCompatActivity {

    /** TODO: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! CAOUTION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *      if the user is "Administrator",can use this page. But else,can not use this page.
     *      + CAN NOT EDIT AND REMOVE. CAN ONLY DISPLAYED!
     *
     */

    public static boolean IS_ROOM_SETTINGS_CHANGE = false; //assign from "CustomListAdapter_RoomSettings_DeviceItem.java,RecyclerViewAdapter_RoomSettingsDeviceItem.java"
    TextView textView_RoomName;
    static TextView textView_CountOfDevices;
    //static DynamicListView listView_DevicesInRoom;
    static RecyclerView recyclerView_DevicesInRoom;
    static ListView listView_Devices;
    static Button button_Save;
    ///ListView Devices:@{
    static ArrayList<String> list_forListDevices;
    static CustomListAdapter_RoomSettings_DeviceItem customListAdapterRoomSettingsDeviceItem;
    static RecyclerViewAdapter_RoomSettingsDeviceItem recyclerViewAdapter;

    public static void setList_forListDevices(String item){
        list_forListDevices.add(item);
        customListAdapterRoomSettingsDeviceItem.notifyDataSetChanged();
        listView_Devices.smoothScrollToPosition(customListAdapterRoomSettingsDeviceItem.getCount());
    }
    public static void removeInList_forListDevices(int index){
        list_forListDevices.remove(index);
        customListAdapterRoomSettingsDeviceItem.notifyDataSetChanged();
    }
///ListView Devices:@}
///////////////////////////////////////////////
///listView_DevicesInRoom:@{
    private static String devices_not_in_room;
    private static String x1_devices_in_this_room;
    private static String x2_devices_in_this_room;
    static ArrayList<String> list_forListDevicesInRoom;
    public static void setList_forListDevicesInRoom(String item){
        list_forListDevicesInRoom.add(item);
        recyclerViewAdapter.notifyDataSetChanged();;
        updateDevicesCount__listView_DevicesInRoom();
        recyclerView_DevicesInRoom.smoothScrollToPosition(recyclerViewAdapter.getItemCount());
    }
    public static void removeInList_fromListDevicesInRoom(int index){
        list_forListDevicesInRoom.remove(index);
        recyclerViewAdapter.notifyDataSetChanged();
        updateDevicesCount__listView_DevicesInRoom();
    }

    private static void updateDevicesCount__listView_DevicesInRoom(){
        if(list_forListDevicesInRoom.size() < 1 )
            textView_CountOfDevices.setText(devices_not_in_room);
        else
            textView_CountOfDevices.setText(x1_devices_in_this_room + " "+  list_forListDevicesInRoom.size() + " " + x2_devices_in_this_room );
    }
///listView_DevicesInRoom:@}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_settings);
        ///
        devices_not_in_room     = /*getApplicationContext().getResources().*/getString(R.string.devices_not_in_room);
        x1_devices_in_this_room = /*getApplicationContext().getResources().*/getString(R.string.x1_devices_in_this_room);
        x2_devices_in_this_room = /*getApplicationContext().getResources().*/getString(R.string.x2_devices_in_this_room);
        ///

        LinearLayout linearLayout_Back         = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                     = (Button)findViewById(R.id.button_Back);
        /*Button*/ button_Save                 = (Button)findViewById(R.id.button_Save);
        TextView textView_label_RoomName       = (TextView)findViewById(R.id.textView_label_RoomName);
        /*TextView*/ textView_RoomName         = (TextView)findViewById(R.id.textView_RoomName);
        ImageView imageView_RoomName_arrow     = (ImageView)findViewById(R.id.imageView_RoomName_arrow);
        /*TextView*/ textView_CountOfDevices       = (TextView)findViewById(R.id.textView_CountOfDevices);
       // /*DynamicListView*/ listView_DevicesInRoom = (DynamicListView)findViewById(R.id.listView_DevicesInRoom);
        recyclerView_DevicesInRoom = findViewById(R.id.recyclerView);
        /*ListView*/ listView_Devices              = (ListView) findViewById(R.id.listView_Devices);

//Back:@{
        linearLayout_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessageDialog();
            }
        });
        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showMessageDialog();
            }
        });
//Back:@}
        button_Save.setVisibility(View.INVISIBLE);//hide at firstly load.

        button_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///TODO: save room name.
                //...
                saveChanges();
                setButton_Save_Visible(false);
            }
        });
///show dialog room name :@{
        textView_label_RoomName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: show dialog room name change.
                showRoomNameDialog();//for edited room name.
            }
        });
        textView_RoomName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: show dialog room name change.
                showRoomNameDialog();//for edited room name.
            }
        });
        imageView_RoomName_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: show dialog room name change.
                showRoomNameDialog();//for edited room name.
            }
        });
///show dialog room name :@}
////////////////////////////////////////
///ListView Devices:@{
        /*//listView_Devices  content: custom adapter.
          + item with image_pluse(+).
         */
        //TODO: load device names from database: get from "Tüm Cihazlar" section.
        String[]  devices_forListDevices = {"device1","device2","device3","device4","device5","device6","device7"};

        //TODO: corresponding  devices  icon: Get load from database.
        Integer[] device_icon_forListDevices = {R.drawable.smart_lamp_icon_color};

        //TODO:(the room to which the devices belongs), load from database.
        String[]  device_location_forListDevices = {"Mutfak","Yatak Odası","Oturma Odası"};

        /*ArrayList<String>*/ list_forListDevices = new ArrayList<String>(Arrays.asList(devices_forListDevices));
        /*CustomListAdapter_RoomSettings_DeviceItem*/  customListAdapterRoomSettingsDeviceItem = new CustomListAdapter_RoomSettings_DeviceItem
                (this,/*devices_forListDevices*/list_forListDevices,device_icon_forListDevices,device_location_forListDevices);
        listView_Devices.setAdapter(customListAdapterRoomSettingsDeviceItem);
///ListView Devices:@}
////////////////////////////////////////
///listView_DevicesInRoom:@{
        /*//listView_DevicesInRoom content:custom adapter.
          + item with image_minus(-).
         */
        //TODO: load device names from database: get from belongs this(edited room) room section.
        String[]  devices_forListDevicesInRoom = {"device1","device9","device10","device11","device12","device13","device14"};

        //TODO:(the room to which the devices belongs), load from database.
        String[]  device_location_forListDevicesInRoom = {"Mutfak","Yatak Odası","Oturma Odası"};

        /*ArrayList<String> mCheeseList = new ArrayList<String>();
        for (int i = 0; i < devices_forListDevicesInRoom.length; ++i) {
            mCheeseList.add(devices_forListDevicesInRoom[i]);
        }*/
/////////////////////////////////////////////////////FIX:23temm2019_1157:@{
        /*ArrayList<String>*/ list_forListDevicesInRoom = new ArrayList<String>(Arrays.asList(devices_forListDevicesInRoom));
        Integer[] device_icon_forListDevicesInRoom = {R.drawable.smart_lamp_icon_color};//TODO: corresponding  devices  icon: Get load from database.
        ArrayList<String> arrayList_forListDeviceLocation_InRoom = new ArrayList<String>(Arrays.asList(device_location_forListDevicesInRoom));
        ArrayList<Integer> arrayList_forListDeviceIcon_InRoom = new ArrayList<Integer>(Arrays.asList(device_icon_forListDevicesInRoom));

        recyclerViewAdapter = new RecyclerViewAdapter_RoomSettingsDeviceItem(this,
                                          list_forListDevicesInRoom,
                                          arrayList_forListDeviceLocation_InRoom,
                                          arrayList_forListDeviceIcon_InRoom);

        ItemTouchHelper.Callback callback = new ItemMoveCallback_RoomSettingsDeviceItem(recyclerViewAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView_DevicesInRoom);
        recyclerView_DevicesInRoom.setAdapter(recyclerViewAdapter);
        //list_forListDevicesInRoom.add("kerim fırat");
        // recyclerViewAdapter.notifyDataSetChanged();
/////////////////////////////////////////////////////FIX:23temm2019_1157:@}

///listView_DevicesInRoom:@}
        if(list_forListDevicesInRoom.size() < 1 )
            textView_CountOfDevices.setText(getString(R.string.devices_not_in_room));
        else
            textView_CountOfDevices.setText(getString(R.string.x1_devices_in_this_room) + " "+  list_forListDevicesInRoom.size() + " " + getString(R.string.x2_devices_in_this_room) );

    }//onCreate

    private void showRoomNameDialog(){
        //TODO: show dialog room name change.
        Keyboard.getInstance(getApplicationContext()).showKeyboard();
        final Alert.InputDialog inputDialog = new Alert().new InputDialog();
        AlertDialog.Builder alertDialogBuilder = inputDialog.show(ActivityRoomSettings.this,getString(R.string.Room_Name),textView_RoomName.getText().toString());
        alertDialogBuilder.setPositiveButton(getString(R.string.confirm),new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(getApplicationContext(),"oda adı kaydedildi.", Toast.LENGTH_LONG).show();
                        textView_RoomName.setText(inputDialog.getContentOfEditTextOnAlertDialog().trim());
                        //TODO: room name update into database.
                        ///..

                        Keyboard.getInstance(getApplicationContext()).hideKeyboard();
                    }
                });
        alertDialogBuilder.setNegativeButton(getString(R.string.cancel),new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ///do nothing.
                Keyboard.getInstance(getApplicationContext()).hideKeyboard();
            }
        });
         alertDialogBuilder.create().show();
    }//showRoomNameDialog()

    private boolean saveChanges(){
        //TODO: Save changes made for the room settings.
        //
        ///
        RecyclerViewAdapter_RoomSettingsDeviceItem.setRoomItemDragDropChanges(false);
        IS_ROOM_SETTINGS_CHANGE = false;

        return true;//TODO: return database insert state.
    }

    private void goBack(){
        RecyclerViewAdapter_RoomSettingsDeviceItem.setRoomItemDragDropChanges(false);
        IS_ROOM_SETTINGS_CHANGE = false;

        Intent intent = new Intent(getApplicationContext(),ActivityRoomManagement.class);
        startActivity(intent);

    }

    private void showMessageDialog(){
        if(RecyclerViewAdapter_RoomSettingsDeviceItem.isRoomItemDragDropChanges() == true || IS_ROOM_SETTINGS_CHANGE == true){
            //TODO:show mesage dialog.  "
            /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivityRoomSettings.this);
            alertDialogBuilder.setMessage(getString(R.string.msg_saveroomchanges));
            */
            Alert.MessageDialog messageDialog = new Alert().new MessageDialog();
            AlertDialog.Builder alertDialogBuilder = messageDialog.show(ActivityRoomSettings.this,getString(R.string.msg_saveroomchanges));
            alertDialogBuilder.setPositiveButton(getString(R.string.save),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Toast.makeText(getApplicationContext(),"You clicked yes    button",Toast.LENGTH_LONG).show();
                            //YES,TODO:Save changes.
                            //and finally go back:
                            saveChanges();
                            goBack();
                        }
                    });
            alertDialogBuilder.setNegativeButton(getString(R.string.donotsave),new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // finish();
                    //do nothing. go back:
                    goBack();;
                }
            });
            alertDialogBuilder.create().show();
        }else{
            goBack();
        }
    }


    public static void setButton_Save_Visible(boolean _b){//called from "RecyclerViewAdapter_RoomSettingsDeviceItem.java,CustomListAdapter_RoomSettings_DeviceItem.java"
        if(_b)
            button_Save.setVisibility(View.VISIBLE);
        else
            button_Save.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            showMessageDialog();
        }
        else
            return super.onKeyDown(keyCode, event);

        return false;
    }

}
