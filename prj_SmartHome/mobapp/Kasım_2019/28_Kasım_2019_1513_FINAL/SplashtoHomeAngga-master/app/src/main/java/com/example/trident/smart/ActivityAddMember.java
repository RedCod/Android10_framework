package com.example.trident.smart;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.Family;
import com.example.trident.common.Server;

import org.w3c.dom.Text;

import java.io.File;

public class ActivityAddMember extends AppCompatActivity {



    /**
     * "Üye Ekle"
     *
     * TODO: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! CAOUTION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *      if the user is "Administrator",can use this page. But else,can not use this page.
     *      + CAN NOT EDIT AND REMOVE. CAN ONLY DISPLAYED!
     *
     */
    public static boolean FROM_CHILD_PAGE = false;
    //public static String  GET_COUNTRY_VALUE_FROM_CHILD_PAGE = "Turkey+90"; //default value "Turkey+90". Set value from "ActivitySelectCountryAndRegion.java" class.
    ImageView imageView_MemberProfilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        //
        LinearLayout linearLayout_Back               = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                           = (Button)findViewById(R.id.button_Back);
        final Button button_Add                      = (Button)findViewById(R.id.button_Add);
        final EditText editText_NameOfMember         = (EditText)findViewById(R.id.editText_NameOfMember);
        /*ImageView*/ imageView_MemberProfilePhoto   = (ImageView)findViewById(R.id.imageView_MemberProfilePhoto);
        ImageView imageView_MemberProfilePhoto_arrow = (ImageView)findViewById(R.id.imageView_MemberProfilePhoto_arrow);
        final TextView textView_CountryAndRegion     = (TextView)findViewById(R.id.textView_CountryAndRegion);
        final ImageView imageView_CountryAndRegion_arrow   = (ImageView)findViewById(R.id.imageView_CountryAndRegion_arrow);
        //editText_Account
        final EditText editText_Account              = (EditText)findViewById(R.id.editText_Account);
        final Switch switchButton_SetAsAdministrator = (Switch)findViewById(R.id.switchButton_SetAsAdministrator);
        ///////////
        enableRuntimePermission();
        /**
         * TODO: Eğer üye eklerken "editText_Account" boş geçilmesine izin vermezsek, bu durumda aşağıdaki sayfayı tasarlamaya gerek kalmaz.
         *  Bağlanan hesap   //"Aile ayarları" > "aile üyeleri" item > Page:"Bağlanan hesap"
         *  ///NOT: üyeye ait bir hesap yoksa,hesap eklemek için bu(Bağlanan hesap) sayfaya gidilir.
         *TODO: bu sayfanın gerekliliğini teyit et!
         * 26_TEMM  18:06
         *
         *
         *
         *
         */
        //set Country value from "ActivitySelectCountryRegion.java" class or default value.
        String country = getIntent().getStringExtra(BetweenActivities.selected_value);//get Country value from "ActivitySelectCountryRegion.java"
        if(country ==null)
            textView_CountryAndRegion.setText("Turkey+90");
        else
            textView_CountryAndRegion.setText(country);

//Back@{
        linearLayout_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBack();
            }
        });
        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//go BACK:
                goToBack();
            }
        });
//Back:@}
        //button_Add
        button_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//Add family member:
                //TODO:
                String mamber_name        = editText_NameOfMember.getText().toString().trim();
                String country_and_region = textView_CountryAndRegion.getText().toString();
                String acount             = editText_Account.getText().toString().trim();
                //...

            }
        });

        //editText_NameOfMember
        /**
         * For button_Add enabled true or false.
         */
        editText_NameOfMember.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length() > 0)
                     button_Add.setEnabled(true);
                else
                    button_Add.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //imageView_MemberProfilePhoto
        imageView_MemberProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: go to select Profile Photo.
                if(false)//TODO: select Dialog option,if get Photo from Camera or Gallery.
                    getImageFromCamera();
                else
                    getImageFromGallery();

            }
        });
        imageView_MemberProfilePhoto_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//ImageView arrow:
                if(false)//TODO: select Dialog option,if get Photo from Camera or Gallery.
                    getImageFromCamera();
                else
                    getImageFromGallery();
            }
        });

        //textView_CountryAndRegion
        textView_CountryAndRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSelectCountryAndRegionPage();
            }
        });

        imageView_CountryAndRegion_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSelectCountryAndRegionPage();
            }
        });

        //switchButton_SetAsAdministrator
        switchButton_SetAsAdministrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Family family = new Family(getApplicationContext());
                Family family = Family.getInstance(getApplicationContext());
                if(switchButton_SetAsAdministrator.isChecked() == true){
                      //TODO: set as Administrator this Member.
                    family.setFamilyAdministrator_ON();
                }else{
                     //TODO: if member as an Administrator,remove admin authority.
                    family.setFamilyAdministrator_OFF();
                }
            }
        });
    }//onCreate


    private void goToSelectCountryAndRegionPage(){
        Intent intent = new Intent(getApplicationContext(),ActivitySelectCountryAndRegion.class);
        intent.putExtra(BetweenActivities.where_come_from,"ActivityAddMember.java");
        startActivity(intent);
    }


    ///
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
                    imageView_MemberProfilePhoto.setImageBitmap(bitmap);

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

    public void enableRuntimePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(ActivityAddMember.this, Manifest.permission.CAMERA))
        {
            //Toast.makeText(getApplicationContext(),"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(ActivityAddMember.this,new String[]{Manifest.permission.CAMERA}, requestPermissionCode);
        }
    }//enableRuntimePermission

    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivityFamilySettings.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent(getApplicationContext(),ActivityFamilySettings.class);
            startActivity(intent);
        }
        else
            return super.onKeyDown(keyCode, event);

        return false;
    }
}
