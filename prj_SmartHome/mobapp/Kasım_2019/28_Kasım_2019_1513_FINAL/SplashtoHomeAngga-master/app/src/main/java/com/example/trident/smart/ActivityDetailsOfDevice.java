package com.example.trident.smart;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.Alert;
import com.example.trident.common.BetweenActivities;
import com.example.trident.common.Keyboard;
import com.example.trident.common.MyLog;

import org.w3c.dom.Text;

public class ActivityDetailsOfDevice extends AppCompatActivity {

    /** "Cihaz Ayrıntıları"
     * Call from "ActivityLampControl.java"
     *
     */
    private static String TAG = "[ActivityDetailsOfDevice]";

    private static String DEVICE_NAME ="";
    TextView textView_DeviceName;
    TextView textView_DeviceLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_device);
        /////////

        ///
        LinearLayout linearLayout_Back = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back             = (Button)findViewById(R.id.button_Back);
        //
        TextView textView_label_DeviceName   = (TextView)findViewById(R.id.textView_label_DeviceName);
        /*TextView*/ textView_DeviceName     = (TextView)findViewById(R.id.textView_DeviceName);
        ImageView imageView_DeviceName_arrow = (ImageView)findViewById(R.id.imageView_DeviceName_arrow);
        //
        TextView textView_label_DeviceLocation   = (TextView)findViewById(R.id.textView_label_DeviceLocation);
        /*TextView*/ textView_DeviceLocation         = (TextView)findViewById(R.id.textView_DeviceLocation);
        ImageView imageView_DeviceLocation_arrow = (ImageView)findViewById(R.id.imageView_DeviceLocation_arrow);
        //
        TextView textView_label_CheckDeviceNetwork   = (TextView)findViewById(R.id.textView_label_CheckDeviceNetwork);
        TextView textView_CheckDeviceNetwork         = (TextView)findViewById(R.id.textView_CheckDeviceNetwork);
        ImageView imageView_CheckDeviceNetwork_arrow = (ImageView)findViewById(R.id.imageView_CheckDeviceNetwork_arrow);
        //
        TextView textView_label_ShareDevice   = (TextView)findViewById(R.id.textView_label_ShareDevice);
        ImageView imageView_ShareDevice_arrow = (ImageView)findViewById(R.id.imageView_ShareDevice_arrow);
        //
        TextView textView_label_CreateGroup   = (TextView)findViewById(R.id.textView_label_CreateGroup);
        ImageView imageView_CreateGroup_arrow = (ImageView)findViewById(R.id.imageView_CreateGroup_arrow);
        //
        TextView textView_label_DeviceInformation   = (TextView)findViewById(R.id.textView_label_DeviceInformation);
        ImageView imageView_DeviceInformation_arrow = (ImageView)findViewById(R.id.imageView_DeviceInformation_arrow);
        //
        TextView textView_label_Feedback   = (TextView)findViewById(R.id.textView_label_Feedback);
        ImageView imageView_Feedback_arrow = (ImageView)findViewById(R.id.imageView_Feedback_arrow);
        //
        TextView textView_label_CheckForFirmwareUpgrades   = (TextView)findViewById(R.id.textView_label_CheckForFirmwareUpgrades);
        ImageView imageView_CheckForFirmwareUpgrades_arrow = (ImageView)findViewById(R.id.imageView_CheckForFirmwareUpgrades_arrow);
        //
        Button button_RemoveDevice          = (Button)findViewById(R.id.button_RemoveDevice);
        Button button_RestoreFactorySetting = (Button)findViewById(R.id.button_RestoreFactorySetting);


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
            DEVICE_NAME = device_name;
        textView_DeviceName.setText(DEVICE_NAME);

        if(device_location != null)
            textView_DeviceLocation.setText(device_location);

///Events->:@{
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
//------
        textView_label_DeviceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show edit device name dialog
                editDeviceName();
            }
        });
        textView_DeviceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show edit device name dialog
                editDeviceName();
            }
        });
        imageView_DeviceName_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show edit device name dialog
                editDeviceName();
            }
        });
//------
        textView_label_DeviceLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to "Device Location" page:
                goToDeviceLocationPage();
            }
        });
        textView_DeviceLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to "Device Location" page:
                goToDeviceLocationPage();
            }
        });
        imageView_DeviceLocation_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to "Device Location" page:
                goToDeviceLocationPage();
            }
        });
//------
        textView_label_CheckDeviceNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to "Check Device Network" page.
                goToCheckDeviceNetwork();
            }
        });
        textView_CheckDeviceNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to "Check Device Network" page.
                goToCheckDeviceNetwork();
            }
        });
        imageView_CheckDeviceNetwork_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to "Check Device Network" page.
                goToCheckDeviceNetwork();
            }
        });
//------
        textView_label_ShareDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to "Share Device" page:
                goToSharedDevice();
            }
        });
        imageView_ShareDevice_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to "Share Device" page:
                goToSharedDevice();
            }
        });
//------
        textView_label_CreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to "Create Group" page:
                goToCreateDevicesGroup();
            }
        });
        imageView_CreateGroup_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to "Create Group" page:
                goToCreateDevicesGroup();
            }
        });
//------
        textView_label_DeviceInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to "Device Information" page:
                goToDeviceInformation();
            }
        });
        imageView_DeviceInformation_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to "Device Information" page:
                goToDeviceInformation();
            }
        });
//------
        textView_label_Feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to "Feedback" page:
                goToFeedback();
            }
        });
        imageView_Feedback_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to "Feedback" page:
                goToFeedback();
            }
        });
//------
        textView_label_CheckForFirmwareUpgrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show "Firmware(software)update details" dialog:
                checkForFirmwareUpgrades();
            }
        });
        imageView_CheckForFirmwareUpgrades_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show "Firmware(software) update details" dialog:
                checkForFirmwareUpgrades();
            }
        });
//------
        button_RemoveDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show "remove device" dialog.
                removeDevice();
            }
        });
        button_RestoreFactorySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show "restore factory setting" dialog:
                restoreFactorySetting();
            }
        });
///events<-:@}

        //


    }//onCreate

    private void editDeviceName(){
        final Alert.InputDialog dialogForEdit = new Alert().new InputDialog();
        AlertDialog.Builder dialogBuilder = dialogForEdit.show(
                ActivityDetailsOfDevice.this,getString(R.string.rename),textView_DeviceName.getText().toString());
        //------------
        Keyboard.getInstance(getApplicationContext()).showKeyboard();//show keyboard
        dialogBuilder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                //MyLog.d("kerim","gelbana:" + dialogForEdit.getContentOfEditTextOnAlertDialog());
                String device_new_name = dialogForEdit.getContentOfEditTextOnAlertDialog();
                textView_DeviceName.setText(device_new_name);
                DEVICE_NAME = device_new_name;
                Keyboard.getInstance(getApplicationContext()).hideKeyboard();//hide keyboard
                ///TODO:insert into new "device name" to database.
            }
        });
        dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass. do nothing
                Keyboard.getInstance(getApplicationContext()).hideKeyboard();//hide keyboard
            }
        });
        dialogBuilder.create().show();
    }//editDeviceName()

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
        Intent intent = new Intent(getApplicationContext(),ActivityLampControl.class);
        intent.putExtra("device_name",textView_DeviceName.getText().toString());//because user may have made a name change.
        startActivity(intent);
    }
    private void goToDeviceLocationPage(){
        Intent intent = new Intent(getApplicationContext(),ActivityDeviceLocation.class);
        intent.putExtra("device_location",textView_DeviceLocation.getText());
        intent.putExtra("where_come_from","ActivityDetailsOfDevice.java");
        startActivity(intent);
    }
    private void goToCheckDeviceNetwork(){
        Intent intent = new Intent(getApplicationContext(),ActivityCheckDeviceNetwork.class);
        startActivity(intent);
    }
    private void goToSharedDevice(){
        Intent intent = new Intent(getApplicationContext(),ActivitySharedDevice.class);
        intent.putExtra("where_come_from","ActivityDetailsOfDevice.java");
        startActivity(intent);
    }

    /**
     * Create for devices group to group devices.
     */
    private void goToCreateDevicesGroup(){
        Intent intent = new Intent(getApplicationContext(),ActivityCreateDevicesGroup.class);
        intent.putExtra("where_come_from","ActivityDetailsOfDevice.java");
        startActivity(intent);
    }

    /**
     * go to device information:
     */
    private void goToDeviceInformation(){
        Intent intent = new Intent(getApplicationContext(),ActivityDeviceInformation.class);
        startActivity(intent);
    }

    /**
     * go to Feedback page.
     */
    private void goToFeedback(){
        //TODO: To be completed in version 2.
        //Toast.makeText(getApplicationContext(),getString(R.string.not_yet_completed),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),ActivityFeedback.class);
        intent.putExtra(BetweenActivities.where_come_from,"ActivityDetailsOfDevice.java");
        startActivity(intent);
    }

    /**
     * Check for Firmware Upgrades (Wi-fimodule,MCU Module)
     */
    private void checkForFirmwareUpgrades(){
        //TODO: To be completed in version 2.
        //Toast.makeText(getApplicationContext(),getString(R.string.not_yet_completed),Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),getString(R.string.no_update_available),Toast.LENGTH_SHORT).show();
    }

    private void removeDevice(){
       Alert.MessageDialog alertForRemoveDevice = new Alert().new MessageDialog();
       AlertDialog.Builder dialogBuilder = alertForRemoveDevice.show(ActivityDetailsOfDevice.this,getString(R.string.notification),getString(R.string.remove_device_msg));
        dialogBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
              //todo: remove this device.
                Toast.makeText(getApplicationContext(), "Remove Device is completed", Toast.LENGTH_SHORT).show();
            }
        });
        dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass. do nothing
            }
        });
        dialogBuilder.create().show();
    }//removeDevice()

    private void restoreFactorySetting(){
        Alert.MessageDialog alertForRestoreFactorySetting = new Alert().new MessageDialog();
        AlertDialog.Builder dialogBuilder =  alertForRestoreFactorySetting.show(ActivityDetailsOfDevice.this,getString(R.string.notification),getString(R.string.restore_factory_settin_msg));
        dialogBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //todo: Restore Factory Setting:
                Toast.makeText(getApplicationContext(), "Restore Factory Settings completed", Toast.LENGTH_SHORT).show();
            }
        });
        dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass. do nothing
            }
        });
        dialogBuilder.create().show();
    }//restoreFactorySetting()


}
