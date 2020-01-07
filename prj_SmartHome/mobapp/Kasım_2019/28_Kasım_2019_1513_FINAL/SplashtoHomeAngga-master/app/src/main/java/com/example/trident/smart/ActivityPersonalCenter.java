package com.example.trident.smart;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.Alert;
import com.example.trident.common.BetweenActivities;
import com.example.trident.common.Keyboard;
import com.example.trident.common.Server;

import java.io.File;
import java.io.LineNumberReader;

public class ActivityPersonalCenter extends AppCompatActivity {
    /** "Kişisel Merkez"
     * called by "FragmentMe.java"
     */


    ImageView imageView_ProfilePhoto;
    TextView textView_NickName;
    private boolean spinner_is_first_action = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        //

        LinearLayout linearLayout_Back               = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                           = (Button)findViewById(R.id.button_Back);
        LinearLayout linearLayout_ProfilePhoto       = (LinearLayout)findViewById(R.id.linearLayout_ProfilePhoto);
        /*ImageView*/ imageView_ProfilePhoto         = (ImageView)findViewById(R.id.imageView_ProfilePhoto);
        LinearLayout linearLayout_Nickname           = (LinearLayout)findViewById(R.id.linearLayout_Nickname);
        /*final TextView*/ textView_NickName         = (TextView)findViewById(R.id.textView_NickName);
        LinearLayout linearLayout_AccountAndSecurity = (LinearLayout)findViewById(R.id.linearLayout_AccountAndSecurity);
        //TextView textView_TemperatureUnit            = (TextView)findViewById(R.id.textView_TemperatureUnit);
        Spinner spinner_TemperatureUnit              = (Spinner)findViewById(R.id.spinner_TemperatureUnit);
        LinearLayout linearLayout_TimeZone           = (LinearLayout)findViewById(R.id.linearLayout_TimeZone);
        TextView textView_TimeZone                   = (TextView)findViewById(R.id.textView_TimeZone);
        //

        ///:@{
        String get_nickname = "";//todo:get this value from databse:
        //if no nickname,set "nickname_desc" to hint:
        if(get_nickname.length() < 0)
            textView_NickName.setHint(getString(R.string.nickname_desc));
        else
            textView_NickName.setText(get_nickname);
        ///:@}
        ArrayAdapter<CharSequence> spinnerDataAdapter = ArrayAdapter.createFromResource(this,R.array.temperature_unit,R.layout.spinner_account_location_item); //get resource array.
        spinner_TemperatureUnit.setAdapter(spinnerDataAdapter);

        //time zone:
        String s_time_zone = getIntent().getStringExtra(BetweenActivities.selected_value);//get from "ActivityTimeZone.java"
        if(s_time_zone !=null)
            textView_TimeZone.setText(s_time_zone);


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
        linearLayout_ProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: show dialog for select profile photo.
                //..
                if(false)//TODO: select Dialog option,if get Photo from Camera or Gallery.
                     getImageFromCamera();
                else
                    getImageFromGallery();;
                //set: imageView_ProfilePhoto:
                //imageView_ProfilePhoto
            }
        });
        linearLayout_Nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: show dialog for set nickname:
                editNickname();
               // textView_NickName.setText(""); //set nickname.
            }
        });
        linearLayout_AccountAndSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAccountSecurityPage();
            }
        });

        spinner_TemperatureUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spinner_is_first_action)
                {//ilk sayfa yüklenirken bu event çalıştığı için pass geç.
                    spinner_is_first_action = false;
                    return;
                }
                String item = adapterView.getSelectedItem().toString();
                //..set this item to database..
                ///...
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        linearLayout_TimeZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo:
                goToTimeZonePage();
            }
        });
        ///Event:@}

    }//onCreate


    private void goToAccountSecurityPage(){
        Intent intent = new Intent(getApplicationContext(),ActivityAccountSecurity.class);
        startActivity(intent);
    }
    private void goToTimeZonePage(){
        //todo:

        //set time zone:
        //textView_TimeZone.setText( set new value);
        Intent intent = new Intent(getApplicationContext(),ActivityTimeZone.class);
        startActivity(intent);

    }

    /////////////
    File filePhoto;
    Uri uriPhoto;
    Intent intentCamera, intentGallery, intentCrop ;
    static final int requestPermissionCode  = 1 ;
    DisplayMetrics displayMetrics ;
    int width, height;

    public void getImageFromCamera() {
        intentCamera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        filePhoto = new File(Environment.getExternalStorageDirectory(),"file" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        uriPhoto = Uri.fromFile(filePhoto);
        intentCamera.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uriPhoto);
        intentCamera.putExtra("return-data", true);
        startActivityForResult(intentCamera, 0);
    }
    public void getImageFromGallery(){
        //show bottom dialog for chose Media.
        intentGallery = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intentGallery, getString(R.string.title_select_image_from_gallery)), 2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            imageCropFunction();
        }
        else if (requestCode == 2) {
            if (data != null) {
                uriPhoto = data.getData();
                imageCropFunction();
            }
        }
        else if (requestCode == 1) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if(bundle !=null){
                    Bitmap bitmap = bundle.getParcelable("data");
                    imageView_ProfilePhoto.setImageBitmap(bitmap);

                    //TODO: Upload photo to the Server.:
                    Server server = new Server();
                    server.uploadPofilePhotoToServer();
                }
            }
        }
    }//onActivityResult
    public void imageCropFunction() {
        // Image Crop Code
        try {
            intentCrop = new Intent("com.android.camera.action.CROP");
            intentCrop.setDataAndType(uriPhoto, "image/*");
            intentCrop.putExtra("crop", "true");
            intentCrop.putExtra("outputX", 180);
            intentCrop.putExtra("outputY", 180);
            intentCrop.putExtra("aspectX", 3);
            intentCrop.putExtra("aspectY", 4);
            intentCrop.putExtra("scaleUpIfNeeded", true);
            intentCrop.putExtra("return-data", true);
            startActivityForResult(intentCrop, 1);
        } catch (ActivityNotFoundException e) {

        }
    }//ImageCropFunction()
    /////////////

    private void editNickname(){
        Keyboard.getInstance(getApplicationContext()).showKeyboard();//show keyboard
        final Alert.InputDialog inputDialog = new Alert().new InputDialog();
        AlertDialog.Builder dialogBuilder = inputDialog.show(ActivityPersonalCenter.this,getString(R.string.rename),textView_NickName.getText().toString());
        dialogBuilder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String nickname = inputDialog.getContentOfEditTextOnAlertDialog().trim();
                if(nickname.length() < 1)
                {
                    Toast.makeText(getApplicationContext(),getString(R.string.nickname_blank),Toast.LENGTH_SHORT).show();
                    return;
                }
                textView_NickName.setText(nickname);
                ///TODO:get Family Name from "textView_NickName" and then set into the database.
                Keyboard.getInstance(getApplicationContext()).hideKeyboard();
                Toast.makeText(getApplicationContext(),""+nickname,Toast.LENGTH_SHORT).show();
            }
        });
        dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass. do nothing
                Keyboard.getInstance(getApplicationContext()).hideKeyboard();//hide keyboard
            }
        });
        dialogBuilder.create().show();

    }//editFamilyName()



    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivityMainPage.class);
        intent.putExtra(BetweenActivities.where_come_from,"ActivityPersonalCenter.java:ME");//ME for selected ME tab.
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
