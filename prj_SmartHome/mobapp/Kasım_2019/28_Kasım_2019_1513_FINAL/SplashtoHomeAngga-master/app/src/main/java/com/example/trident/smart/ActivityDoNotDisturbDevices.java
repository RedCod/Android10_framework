package com.example.trident.smart;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.GUI;
import com.example.trident.common.MyLog;
import com.example.trident.smart.custom.CustomListAdapter_DoNotDisturbDevices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityDoNotDisturbDevices extends AppCompatActivity {
    /**
     * "Cihazları Rahatsız Etmeyin"
     * used by "ActivityTimeSegment.java"
     *
     */

    LinearLayout linearLayout_DeviceListBase;
    ListView listView_DeviceList;
    CustomListAdapter_DoNotDisturbDevices customListAdapterDoNotDisturbDevices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_not_disturb_devices);
        ///
        LinearLayout linearLayout_Back           = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                       = (Button)findViewById(R.id.button_Back);
        TextView textView_WhichFamilyName        = (TextView)findViewById(R.id.textView_WhichFamilyName);
        linearLayout_DeviceListBase              = (LinearLayout)findViewById(R.id.linearLayout_DeviceListBase);
        listView_DeviceList                      = (ListView)findViewById(R.id.listView_DeviceList);
        //


        ///Default:
        textView_WhichFamilyName.setText("Aile1");//todo: get from database family name and set this textView.

        //todo:these two array content get from database:
        /**
         * -Tüm cihazları listele.
         * -Rahatsız edilmeyecek cihazları CHECK=true yap.
         */
        String[] arr_deviceName =  {
                "Device1", "Device2", "Device3",
                "Device1", "Device2", "Device3",
                "Device1", "Device2", "Device3",
                "Device1", "Device2", "Device3",
                "Device1", "Device2", "Device3"
        };
        Boolean[] arr_deviceState = {
                true,false,true,
                true,false,true,
                true,false,true,
                true,false,true,
                true,false,true
        };
        List<String>  list_DeviceName  = Arrays.asList(arr_deviceName);
        List<Boolean> list_DeviceState = Arrays.asList(arr_deviceState);
        customListAdapterDoNotDisturbDevices = new CustomListAdapter_DoNotDisturbDevices(getApplicationContext(),list_DeviceName,list_DeviceState);
        listView_DeviceList.setAdapter(customListAdapterDoNotDisturbDevices);
        //resizeLayoutBaseListView(customListAdapterDoNotDisturbDevices.getCount());//resize
        //fixed:[081120191815]:
        int count = customListAdapterDoNotDisturbDevices.getCount();
        int fix_size = (count < 6 ) ? count + 20 : count + 50;
        GUI gui = new GUI();
        gui.dynamicallySizingLayout(linearLayout_DeviceListBase,listView_DeviceList,customListAdapterDoNotDisturbDevices,count,fix_size);

        ///Events:@{
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
        ///events:@}
    }//onCreate


   /*//disabled[081120191815]
    private void resizeLayoutBaseListView(int count){
        ///for Layout Resize:@{
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) linearLayout_DeviceListBase.getLayoutParams();
        final int dimension = (count < 6 ) ? count + 20 : count + 50;//90;//ATTENTION:do not touch this value.
        int total_height = dimension;
        int item_count = count;
        for(int i=0; i<item_count;i++){
            View item = customListAdapterDoNotDisturbDevices.getView(i,null,listView_DeviceList);
            item.measure(0,0);
            total_height += item.getMeasuredHeight();
        }
        params.height = total_height + (listView_DeviceList.getDividerHeight() * (item_count));
        linearLayout_DeviceListBase.setLayoutParams(params);
        linearLayout_DeviceListBase.requestLayout();
        params = (ConstraintLayout.LayoutParams) linearLayout_DeviceListBase.getLayoutParams();
        ///for Layout Resize:@}
        //
        ///for Listview resize:@{
        ViewGroup.LayoutParams params2 = listView_DeviceList.getLayoutParams();
        params2.height = params.height;
        listView_DeviceList.setLayoutParams(params2);
        listView_DeviceList.requestLayout();
        ////for Listview resize:@}


    }*/

    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivityTimeSegment.class);
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
