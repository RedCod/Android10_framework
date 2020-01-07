package com.example.trident.smart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.trident.smart.custom.CustomListAdapter;

public class ActivitySharedDevice extends AppCompatActivity {
    /**"Paylaşılan Cihazlar"
     * call by "ActivityDetailsOfDevice.java"
     *
     */

    static String WHERE_COME_FROM ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_device);
        //


        LinearLayout linearLayout_Back             = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                         = (Button)findViewById(R.id.button_Back);
        TextView textView_LinkedFamilySettings     = (TextView)findViewById(R.id.textView_LinkedFamilySettings);
        LinearLayout linearLayout_EmptyDeviceState = (LinearLayout)findViewById(R.id.linearLayout_EmptyDeviceState);
        ListView listView_SharedDevices            = (ListView)findViewById(R.id.listView_SharedDevices);
        Button button_AddSharing                   = (Button)findViewById(R.id.button_AddSharing);

        String where_come_from = getIntent().getStringExtra("where_come_from");//"ActivityDetailsOfDevice.java,ActivityDetailsOfDeviceGroup.java"
        if(where_come_from !=null)
            WHERE_COME_FROM = where_come_from;

        linearLayout_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBack();
            }
        });
        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBack();
            }
        });

        textView_LinkedFamilySettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to FamilySetting
                Intent intent = new Intent(getApplicationContext(),ActivityFamilySettings.class);
                intent.putExtra("came_from","ActivitySharedDevice.java");
                startActivity(intent);
            }
        });
        button_AddSharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add sharing:
                Intent intent = new Intent(getApplicationContext(),ActivityAddSharing.class);
                startActivity(intent);
            }
        });


        ///TODO: for test: this values get from database.:@{
        String[] device_names = {//TODO: get thus values from database
                "Lamba_1",
                "Lamba_2",
                "Plug Mutfak"

        };

        String[] devices_type = {//TODO: get thus values from database
                "lamp",
                "lamp",
                "wall_plug"
        };
        String[] devices_state = {//TODO: get thus values from database
                "Online",
                "Online",
                "Offline",
        };
        ///todo:@}

        //todo: if devices exists show listview. Else hide listview and show "empty device layout."

        boolean if_exists = false;//TODO: this value get from database.
        if(if_exists == true){
            linearLayout_EmptyDeviceState.setVisibility(View.GONE);
            listView_SharedDevices.setVisibility(View.VISIBLE);
        }else{
            listView_SharedDevices.setVisibility(View.GONE);
            linearLayout_EmptyDeviceState.setVisibility(View.VISIBLE);
        }

        final CustomListAdapter customListAdapter = new CustomListAdapter(this,devices_type, device_names, devices_state);
        listView_SharedDevices.setAdapter(customListAdapter);
        listView_SharedDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //if(MyLog.DEGUB) MyLog.d("kerim","listView->Item:" + customListAdapter.getItemName(position) +",Desc:" + customListAdapter.getItemNameDesicription(position));
            }
        });


    }//onCreate

    private void goToBack(){
        Intent intent = null;
        if(WHERE_COME_FROM.equals("ActivityDetailsOfDevice.java"))
            intent = new Intent(getApplicationContext(),ActivityDetailsOfDevice.class);
        else if (WHERE_COME_FROM.equals("ActivityDetailOfDeviceGroup.java"))
            intent = new Intent(getApplicationContext(),ActivityDetailsOfDeviceGroup.class);

        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            goToBack();
        }
        else
            return super.onKeyDown(keyCode, event);

        return false;
    }

}
