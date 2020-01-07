package com.example.trident.smart;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trident.common.Alert;
import com.example.trident.common.MyLog;
import com.example.trident.smart.custom.CustomListAdapter_CreateDevicesGroup;

import java.util.Collection;

public class ActivityCreateDevicesGroup extends AppCompatActivity {
    /** "Aygıt Seçin"(Cihaz grubu oluşturmak)
     * call by "ActivityDetailsOfDevice.java,ActivityDetailsOfDeviceGroup.java"
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_devices_group);
        //

        LinearLayout linearLayout_Back = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back = (Button)findViewById(R.id.button_Back);
        Button button_Save = (Button)findViewById(R.id.button_Save);
        ListView listView_Devices = (ListView)findViewById(R.id.listView_Devices);

        //TODO: get "Device name" and "Device Description" from database.:@{
        String[] device_name =  { "Device1", "Device2", "Device3",
                "Device4", "Device5", "Device6", "Device7", "Device8"};
        String[] device_desription = {"Aile11 Oturma odası","Aile1 Mutfak","Aile1 Yemek Odası",
                "Aile11 Oturma odası","Aile11 Oturma odası","Aile11 Oturma odası",
                "Aile11 Oturma odası","Aile11 Oturma odası"};
        ///todo:@}
       final CustomListAdapter_CreateDevicesGroup customListAdapterCreateDevicesGroup = new CustomListAdapter_CreateDevicesGroup(getBaseContext(),device_name,device_desription);
        listView_Devices.setAdapter(customListAdapterCreateDevicesGroup);

        linearLayout_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to back:
                goToBack();
            }
        });
        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to back:
                goToBack();
            }
        });
        button_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo:save and go to back:
                //HashMap<String,Integer> hashMap = customListAdapterCreateDevicesGroup.getCheckedDevices();
                 Collection<String> collectionSelectedDevices =  customListAdapterCreateDevicesGroup.getSelectedDevices();
                 if(collectionSelectedDevices.size() == 0)
                 {
                     Toast.makeText(getApplicationContext(),getString(R.string.create_devices_group_msg),Toast.LENGTH_SHORT).show();
                     return;
                 }

                 String item_of_devices_name ="";
                 for(String item:collectionSelectedDevices){
                     if(MyLog.DEGUB)MyLog.d("kerim","item(devices):" + item);
                     item_of_devices_name = item;
                 }
                ///////////
                final Alert.InputDialog inputDialog = new Alert().new InputDialog();
                final AlertDialog.Builder dialogBuilder = inputDialog.show(ActivityCreateDevicesGroup.this,getString(R.string.create_devices_group_dialog_title),
                        item_of_devices_name +" " + getString(R.string.create_devices_group_dialog_extra));

                dialogBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                      //todo: save database.
                        //...
                        //..
                        MyLog.d("kerim_ActivityCreateDevicesGroup:" ,""+ inputDialog.getContentOfEditTextOnAlertDialog());

                        //and then go to "Main Page":
                        goToMainPage();
                    }
                });
                dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //pass. do nothing
                    }
                });
                dialogBuilder.create().show();
                ///////////

            }
        });



    }//onCreate

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

    private void goToBack(){
        String where_come_from = getIntent().getStringExtra("where_come_from");
        Intent intent = null;
        if(where_come_from.equals("ActivityDetailsOfDevice.java"))
            intent = new Intent(getApplicationContext(),ActivityDetailsOfDevice.class);
        else if(where_come_from.equals("ActivityDetailsOfDeviceGroup.java"))
            intent = new Intent(getApplicationContext(),ActivityDetailsOfDeviceGroup.class);

        startActivity(intent);
    }
    private void goToMainPage(){
        Intent intent = new Intent(getApplicationContext(),ActivityMainPage.class);
        startActivity(intent);
    }

}
