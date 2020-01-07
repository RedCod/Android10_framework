package com.example.trident.smart;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.trident.common.Alert;
import com.example.trident.common.Dialog;
import com.example.trident.common.Family;
import com.example.trident.common.Keyboard;
import com.example.trident.common.MyLog;
import com.example.trident.common.Server;

import org.w3c.dom.Text;

import java.io.File;

public class ActivityFamilyMembers extends AppCompatActivity { //SAYFA: "AİLE ÜYELERİ"
    private static String TAG = "[ActivityFamilyMembers]";
    TextView textView_Name;
    ImageView imageView_ProfilePhoto;

    /** TODO: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! CAOUTION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *      if the user is "Administrator",can use this page. But else,can not use this page.
     *      + CAN NOT EDIT AND REMOVE. CAN ONLY DISPLAYED!
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_members);
        ///
        LinearLayout linearLayout_Back                   = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                               = (Button)findViewById(R.id.button_Back);

        TextView textView_label_Name                     = (TextView)findViewById(R.id.textView_label_Name);
        /*TextView*/ textView_Name                       = (TextView)findViewById(R.id.textView_Name);
        ImageView imageView_Name_arrow                   = (ImageView)findViewById(R.id.imageView_Name_arrow);

        TextView textView_label_ProfilePhoto             = (TextView)findViewById(R.id.textView_label_ProfilePhoto);
        /*ImageView*/ imageView_ProfilePhoto             = (ImageView)findViewById(R.id.imageView_ProfilePhoto);
        ImageView imageView_ProfilePhoto_arrow           = (ImageView)findViewById(R.id.imageView_ProfilePhoto_arrow);

        TextView textView_LinkedAccount                  = (TextView)findViewById(R.id.textView_LinkedAccount);
        ImageView imageView_LinkedAccount_arrow          = (ImageView)findViewById(R.id.imageView_LinkedAccount_arrow);//for Administrator and other.

        //TODO:hide it from if user not "Administrator":
        LinearLayout linearLayout_SetFamilyAdministrator = (LinearLayout)findViewById(R.id.linearLayout_SetFamilyAdministrator);
        final Switch switchButton_SetFamilyAdministrator = (Switch)findViewById(R.id.switchButton_SetFamilyAdministrator);

        //TODO:hide it from if user not "Administrator":
        LinearLayout linearLayout_RemoveMember           = (LinearLayout)findViewById(R.id.linearLayout_RemoveMember);
        Button button_RemoveMember                       = (Button)findViewById(R.id.button_RemoveMember);

        ///TODO:+@{1

        //+If the family member displayed is the "Administrator" and if the user is "Administrator":
           switchButton_SetFamilyAdministrator.setChecked(true);//default Checked value is "true".
           //switchButton_SetFamilyAdministrator.setEnabled(false);//fixme:Set "setfamilyadministrator" button enabled false. Because he is already is an Administrator.
           //imageView_LinkedAccount_arrow.setVisibility(View.INVISIBLE);;//fixme: if user is an Administrator,hide this arrow image. Else,show this arrow image.
        ///+todo:@}1  show "Remove Member-Üyeyi Kaldır" button.


         //else-TODO:@{2
            //If the family member displayed is not the "Administrator":

            ///+ hide "linearLayout_SetFamilyAdministrator" layout. Because it is not shown to anyone other than administrator:
               //|_linearLayout_SetFamilyAdministrator.setVisibility(View.INVISIBLE);

            ///+ show "Remove Member-Üyeyi Kaldır" button.
        ///todo:-@}2
        ///
        ///TODO:@{3
            //if user is not Administrator:
             //|_linearLayout_RemoveMember.setVisibility(View.INVISIBLE);

        ///todo:@}3

        enableRuntimePermission();
//Back:@{
        linearLayout_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBack();
            }
        });
        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//BACK:
              goToBack();
            }
        });
//Back:@}
///Change Name:@{
        textView_label_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//show AlertDialog window for change Name.
              changeName();
            }
        });
        textView_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeName();
            }
        });
        imageView_Name_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeName();
            }
        });
///Change Name:@}
////////////////
///Profile Photo:@{
        textView_label_ProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(false)//TODO: select Dialog option,if get Photo from Camera or Gallery.
                    getImageFromCamera();
                else
                    getImageFromGallery();
            }
        });
        imageView_ProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(false)//TODO: select Dialog option,if get Photo from Camera or Gallery.
                    getImageFromCamera();
                else
                    getImageFromGallery();



            }
        });
        imageView_ProfilePhoto_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(false)//TODO: select Dialog option,if get Photo from Camera or Gallery.
                    getImageFromCamera();
                else
                    getImageFromGallery();
            }
        });
///Profile Photo:@}
///////////////////
///Linked Account:@{
        textView_LinkedAccount.setText("test@test.com");
        //TODO: if no account:
        //|_ textView_LinkedAccount.setText(getString(R.string.not_linked));


        textView_LinkedAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//go to "ActivityConnectedAcount.java" (Bağlanan Hesap sayfası).
            //todo: if displya account is not Administrator,go to:
            goToActivityLinkedAccout();
            }
        });
        imageView_LinkedAccount_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//go to "ActivityConnectedAcount.java" (Bağlanan Hesap sayfası).
             //todo: if displya account is not Administrator,go to:
             goToActivityLinkedAccout();
            }
        });
///Linked Account:@}
///////////////////
///SetFamilyAdministrator:@{
        switchButton_SetFamilyAdministrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:
                //Family family = new Family(getApplicationContext());
                Family family = Family.getInstance(getApplicationContext());
                if(switchButton_SetFamilyAdministrator.isChecked() == true){
                    //TODO: set as Administrator this Member.
                    family.setFamilyAdministrator_ON();
                }else{
                    //TODO: if member as an Administrator,remove admin authority.
                    family.setFamilyAdministrator_OFF();
                }
            }
        });
///SetFamilyAdministrator:@}
        button_RemoveMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: remove Member.
                 Alert.MessageDialog messageDialog = new Alert().new MessageDialog();
                AlertDialog.Builder dialogBuilder = messageDialog.show(ActivityFamilyMembers.this,
                        getString(R.string.remove_member_dialog_title),getString(R.string.remove_member_dialog_info));

                dialogBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                       //Todo: Remove Member.!!!!!!!!!!!!!!!!!!!!
                    }
                });
                dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //pass. do nothing
                    }
                });
                dialogBuilder.create().show();
            }
        });//onClick

    }//onCreate

    private void changeName(){
        //TODO: show alert dialog and change "Name".
        final Alert.InputDialog inputDialog = new Alert().new InputDialog();
        AlertDialog.Builder dialogBuilder = inputDialog.show(ActivityFamilyMembers.this,
                getString(R.string.modify_name),textView_Name.getText().toString());
        Keyboard.getInstance(getApplicationContext()).showKeyboard();//show keyboard
        dialogBuilder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String member_new_name = inputDialog.getContentOfEditTextOnAlertDialog();
                textView_Name.setText(member_new_name);
                //TODO: insert into database new "family members name".

                Keyboard.getInstance(getApplicationContext()).hideKeyboard();
            }
        });
        dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass. do nothing
                //Keyboard.getInstance(this).closeKeyboard();//hide keyboard
                Keyboard.getInstance(getApplicationContext()).hideKeyboard();
            }
        });
        dialogBuilder.create().show();
    }//changeName

    ///
    File filePhoto;
    Uri uriPhoto;
    Intent intentCamera, intentGallery, intentCrop ;
    static final int requestPermissionCode  = 1 ;
    DisplayMetrics displayMetrics ;
    int width, height;

    public void getImageFromCamera() {
        intentCamera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        filePhoto    = new File(Environment.getExternalStorageDirectory(),"file" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        uriPhoto     = Uri.fromFile(filePhoto);
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

    public void enableRuntimePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(ActivityFamilyMembers.this,Manifest.permission.CAMERA))
        {
            //Toast.makeText(getApplicationContext(),"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(ActivityFamilyMembers.this,new String[]{Manifest.permission.CAMERA}, requestPermissionCode);
        }
    }//enableRuntimePermission


   /**
    * //fixed: moved to Family.java class. so disabled.
    *  private void setFamilyAdministrator_ON(){
        //TODO: Set as Family Administrator.
        Toast.makeText(getApplicationContext(),"set as family Administrator_ON",Toast.LENGTH_SHORT).show();
    }
    private void setFamilyAdministrator_OFF(){
        //TODO: Set as Family Not Administrator.
        Toast.makeText(getApplicationContext(),"set as family NOT Administrator_OFF",Toast.LENGTH_SHORT).show();
    }*/

   private void goToActivityLinkedAccout(){
       Intent intent = new Intent(getApplicationContext(),ActivityLinkedAccout.class);
       startActivity(intent);
   }

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
