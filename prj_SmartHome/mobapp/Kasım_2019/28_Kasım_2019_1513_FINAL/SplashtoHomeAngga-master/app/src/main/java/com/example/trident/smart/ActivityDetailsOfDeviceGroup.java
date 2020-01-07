package com.example.trident.smart;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.Alert;
import com.example.trident.common.Keyboard;
import com.example.trident.common.MyLog;

public class ActivityDetailsOfDeviceGroup extends AppCompatActivity {
    /** "Grup Ayrıntıları"
     *call by "ActivityLampControl.java"
     *
     */

    private static String GROUP_NAME = "";
    TextView textView_GroupName;
    TextView textView_GroupPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_device_group);
        //

        LinearLayout linearLayout_Back               = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                           = (Button)findViewById(R.id.button_Back);
        Button button_DismissGroup                   = (Button)findViewById(R.id.button_DismissGroup);
        LinearLayout linearLayout_GroupName          = (LinearLayout)findViewById(R.id.linearLayout_GroupName);
        /*TextView*/ textView_GroupName                  = (TextView)findViewById(R.id.textView_GroupName);
        LinearLayout linearLayout_GroupPosition      = (LinearLayout)findViewById(R.id.linearLayout_GroupPosition);
        /*TextView*/ textView_GroupPosition              = (TextView)findViewById(R.id.textView_GroupPosition);
        LinearLayout linearLayout_ManageGroupDevices = (LinearLayout)findViewById(R.id.linearLayout_ManageGroupDevices);
        LinearLayout linearLayout_SharingGroup       = (LinearLayout)findViewById(R.id.linearLayout_SharingGroup);
        LinearLayout linearLayout_FeedBack           = (LinearLayout)findViewById(R.id.linearLayout_FeedBack);

        /**
         * TODO: query device detail information from database.
         * todo: or the following information bring from previous class(ActivityLampControl.java) to here.
         *
         * FIXME:
         */
        Intent intent = getIntent();
        String device_name = intent.getStringExtra("device_name");
        String device_location = intent.getStringExtra("device_location");//"Mutfak";//todo: get previous class OR get from database.
        if(device_name != null)
            GROUP_NAME = device_name;
        textView_GroupName.setText(GROUP_NAME);

        if(device_location !=null)
           textView_GroupPosition.setText(device_location);

        ///Event:@{
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
        linearLayout_GroupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo:group name:
               editGroupName();
            }
        });
        linearLayout_GroupPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: group position
                goToDeviceLocation();
            }
        });
        linearLayout_ManageGroupDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo:Manage Group Devices:
                goToCreateDevicesGroup();//for manage the devices.
            }
        });
        linearLayout_SharingGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: sharing group:
                goToSharedDevice();
            }
        });

        linearLayout_FeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: To be completed in version 2.
                goToFeedback();
            }
        });
        button_DismissGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: dismiss group:
            Alert.MessageDialog messageDialog = new Alert().new MessageDialog();
            AlertDialog.Builder dialogBuilder = messageDialog.show(ActivityDetailsOfDeviceGroup.this,getString(R.string.notification),getString(R.string.dismiss_group_msg));
                dialogBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //todo: dismiss this group:
                       Toast.makeText(getApplicationContext(),getString(R.string.group_removed_toast),Toast.LENGTH_SHORT).show();
                       //and going to Main page:
                        Intent intent = new Intent(getApplicationContext(),ActivityMainPage.class);
                        startActivity(intent);
                    }
                });
                dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //pass. do nothing
                    }
                });
                dialogBuilder.create().show();
            }
        });
        ///event:}


    }//onCreate

    private void editGroupName(){
        final Alert.InputDialog inputDialog = new Alert().new InputDialog();
        AlertDialog.Builder dialogBuilder = inputDialog.show(ActivityDetailsOfDeviceGroup.this,getString(R.string.rename),textView_GroupName.getText().toString());
        Keyboard.getInstance(getApplicationContext()).showKeyboard();
        dialogBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String group_new_name = inputDialog.getContentOfEditTextOnAlertDialog();
                textView_GroupName.setText(group_new_name);
                //todo:save group name.
                //..
                Keyboard.getInstance(getApplicationContext()).hideKeyboard();
            }
        });
        dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass. do nothing
                Keyboard.getInstance(getApplicationContext()).hideKeyboard();
            }
        });
        dialogBuilder.create().show();
    }

    /**
     * go to Feedback page.
     */
    private void goToFeedback(){
        //TODO: To be completed in version 2.
        Toast.makeText(getApplicationContext(),getString(R.string.not_yet_completed),Toast.LENGTH_SHORT).show();
    }

    private void goToSharedDevice(){
        Intent intent = new Intent(getApplicationContext(),ActivitySharedDevice.class);
        intent.putExtra("where_come_from","ActivityDetailOfDeviceGroup.java");
        startActivity(intent);
    }
    private void goToCreateDevicesGroup(){
        //for manage the devices
        Intent intent = new Intent(getApplicationContext(),ActivityCreateDevicesGroup.class);
        intent.putExtra("where_come_from","ActivityDetailsOfDeviceGroup.java");
        startActivity(intent);
    }
    private void goToDeviceLocation(){
        Intent intent = new Intent(getApplicationContext(),ActivityDeviceLocation.class);
        intent.putExtra("device_location",textView_GroupPosition.getText());
        intent.putExtra("where_come_from","ActivityDetailsOfDeviceGroup.java");
        startActivity(intent);
    }
    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivityLampControl.class);
        startActivity(intent);
    }//goToBack()


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
