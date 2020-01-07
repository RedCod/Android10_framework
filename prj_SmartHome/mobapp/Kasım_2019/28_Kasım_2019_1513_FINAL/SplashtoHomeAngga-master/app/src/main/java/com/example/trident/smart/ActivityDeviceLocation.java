package com.example.trident.smart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.trident.common.MyLog;
import com.example.trident.smart.custom.CustomListAdapter_ActivityDeviceLocation;

import java.util.HashMap;
import java.util.Iterator;

public class ActivityDeviceLocation extends AppCompatActivity {
    /**
     * call from "ActivityDetailsOfDevice.java,ActivityDetailsOfDeviceGroup.java"
     *
     */

    String where_come_from="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_location);
        //

        LinearLayout linearLayout_Back = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back = (Button)findViewById(R.id.button_Back);

        Button button_Save               = (Button)findViewById(R.id.button_Save);
        ListView listView_DeviceLocation = (ListView)findViewById(R.id.listView_DeviceLocation);

        Intent intent = getIntent();
        where_come_from = intent.getStringExtra("where_come_from");//where(which class) we come from.Because it is used by multiple classes.
        ///TODO: "Locations" array for test. this array content,get from database.
        String current_location = intent.getStringExtra("device_location"); //"Mutfak";//todo:get this from previous activity. Or get database.
        String[] locations = {"Mutfak","Yemek odası","Oturma odası","Balkon","Kiler","Yatak odası","Sundurma","Çalışma odası","İkinci oda","Bebek odası"};
        CustomListAdapter_ActivityDeviceLocation customListAdapterActivityDeviceLocation = new CustomListAdapter_ActivityDeviceLocation(getBaseContext(),locations,current_location);
        listView_DeviceLocation.setAdapter(customListAdapterActivityDeviceLocation);

        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBack(null,where_come_from);
            }
        });
        button_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///TODO: get selected Location,and then insert into database associating with the Device:@{
                HashMap locationsHashMap =  CustomListAdapter_ActivityDeviceLocation.getSelectedRooms();
                Iterator<String> keySetIterator = locationsHashMap.keySet().iterator();
                while(keySetIterator.hasNext()){
                    String loc_K = keySetIterator.next();
                    if(MyLog.DEGUB)MyLog.d("kerim","key(location): " + loc_K /* + " value: " + roomsHashMap.get(roomK)*/);
                    //TODO: insert into new location value to database.
                    //....insert into as "general devices" if no location is selected.
                    //...
                    //and then,go back:
                    goToBack(loc_K,where_come_from);
                }
                ///TODO:@}
            }
        });

    }//onCreate



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            goToBack(null,where_come_from);
        }
        else
            return super.onKeyDown(keyCode, event);

        return false;
    }
    private void goToBack(String _loc,String _where_come_from){
        if(MyLog.DEGUB)MyLog.d("kerim","where_come_from:"+_where_come_from);
        Intent intent = null;
        if(_where_come_from.equals("ActivityDetailsOfDevice.java"))
             intent = new Intent(getApplicationContext(),ActivityDetailsOfDevice.class);
        else if(_where_come_from.equals("ActivityDetailsOfDeviceGroup.java"))
            intent = new Intent(getApplicationContext(),ActivityDetailsOfDeviceGroup.class);
        
        if(_loc !=null)
          intent.putExtra("device_location",_loc);//because user may have made of location change.

        startActivity(intent);
    }
}
