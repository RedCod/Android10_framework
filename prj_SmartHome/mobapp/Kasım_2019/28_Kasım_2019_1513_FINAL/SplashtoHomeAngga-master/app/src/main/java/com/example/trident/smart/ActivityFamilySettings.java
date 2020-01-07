package com.example.trident.smart;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.Alert;
import com.example.trident.common.GUI;
import com.example.trident.common.Keyboard;
import com.example.trident.common.MyLog;
import com.example.trident.smart.custom.CustomListAdapter_FamilySettings;

public class ActivityFamilySettings extends AppCompatActivity { ///Aile Ayarları

    /**"Aile Ayarları"
     *
     * TODO: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! CAOUTION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *      if the user is "Administrator",can use this page. But else,can not use this page.
     *      + CAN NOT EDIT AND REMOVE. CAN ONLY DISPLAYED!
     *
     */

    ///TODO:these data get from database:@{
    String[] members_names ={
            "tridenturetim1",
            "tridenturetim2",
            "tridenturetim3",
            "tridenturetim4"
    };
    Integer[] imgid={
            R.drawable.group_of_member,
            R.drawable.group_of_member,
            R.drawable.group_of_member,
            R.drawable.group_of_member

    };
    String[] description = {//state of Device. (online/offline)
            "tridenturetim@gmail.com",
            "tridenturetim@gmail.com",
            "tridenturetim@gmail.com",
            "tridenturetim@gmail.com"
    };

  /*  String a =""; //getString(R.string.user);
    String b = "";//getString(R.string.user);
    String[] member_authority = {//administrator or normal user
            a,
            b
    };*/

    Integer[] member_authority = {//administrator or normal user
            1,//Administrator
            0,
            0,
            0 //normal user
    };
    ///TODO:these data get from database:@}
    static ScrollView scrollView_Based;
    LinearLayout linearLayout_MembersBased;
    TextView textView_FamilyName;
    TextView textView_RoomCountAndGoToRoomManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_settings);
        //
        LinearLayout linearLayout_Back                       = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                                   = (Button)findViewById(R.id.button_Back);
        //
        scrollView_Based                                     = (ScrollView)findViewById(R.id.scrollView_Based);
        linearLayout_MembersBased                            = (LinearLayout)findViewById(R.id.linearLayout_MembersBased);
        Button button_GoToAddMember                          = (Button)findViewById(R.id.button_GoToAddMember);
        Button button_RemoveFamily                           = (Button)findViewById(R.id.button_RemoveFamily);
        TextView textView_label_FamilyName                   = (TextView)findViewById(R.id.textView_label_FamilyName);
        /*TextView*/ textView_FamilyName                     = (TextView)findViewById(R.id.textView_FamilyName);
        TextView textView_label_RoomManagement               = (TextView)findViewById(R.id.textView_label_RoomManagement);
        /*TextView*/ textView_RoomCountAndGoToRoomManagement = (TextView)findViewById(R.id.textView_RoomCountAndGoToRoomManagement);
        TextView textView_label_FamilyLocation               = (TextView)findViewById(R.id.textView_label_FamilyLocation);
        TextView textView_FamilySetLocation                  = (TextView)findViewById(R.id.textView_SetFamilyLocationA);
        ImageView imageView_FamilyName_arrow                 = (ImageView)findViewById(R.id.imageView_FamilyName_arrow);
        ImageView imageView_RoomManagement_arrow             = (ImageView)findViewById(R.id.imageView_RoomManagement_arrow);
        ImageView imageView_SetFamilyLocation_arrow          = (ImageView)findViewById(R.id.imageView_SetFamilyLocationA_arrow);
        ListView listView_Members                            = (ListView)findViewById(R.id.listView_Members);

        //
        ///TODO:+@{
         //+ If the user is not "Administrator", do not edit and remove anything.
        ///todo:-@}

        loadFamilySettings();//TODO:look at this function content "TODO" tag.
//Back:@{
        linearLayout_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBack();
            }
        });
        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//BACK-Geri: go to "ActivityHomeManagement.java" page.
             goToBack();
            }
        });
//Back:@}
        textView_label_FamilyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show edit family name dialog.
                editFamilyName();
            }
        });
        textView_FamilyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//Family Name-Aile Adı
                //show edit family name dialog.
                editFamilyName();
            }
        });

        textView_label_RoomManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRoomManagement();
            }
        });
        textView_RoomCountAndGoToRoomManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//TODO:Room Count and go to Room Management page.Oda saysı ve Oda Yönetimi penceresine git:
                ///...
                goToRoomManagement();
            }
        });

        textView_label_FamilyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFamilyLocation();
            }
        });
        textView_FamilySetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//TODO:Family Set Location-Aile Konumu:
               // Toast.makeText(getApplicationContext(),"Set Family Location.",Toast.LENGTH_SHORT).show();
                setFamilyLocation();
            }
        });

        imageView_FamilyName_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editFamilyName();
            }
        });
        imageView_RoomManagement_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRoomManagement();
            }
        });
        imageView_SetFamilyLocation_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFamilyLocation();
            }
        });


        button_GoToAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//Go To Add Member - Üye Ekle:
                Toast.makeText(getApplicationContext(),"go to add Member",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),ActivityAddMember.class);
                startActivity(intent);
            }
        });

        button_RemoveFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//Remove Family- Aileyi Kaldır.
                Alert.MessageDialog messageDialog = new Alert().new MessageDialog();
                AlertDialog.Builder dialogBuilder =  messageDialog.show(ActivityFamilySettings.this,getString(R.string.remove_family_dialog_title),
                        getString(R.string.remove_family_dialog_info));
                //@}
                dialogBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Todo: Remove Family.!!!!!!!!!!!!!!!!!!!!
                        MyLog.d("kerim","remove family");
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

        ////disable:@{
        //String[] rooms = {"Üye","Üye","Üye","Üye","Üye","Üye","Üye","Üye"};
        //ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, rooms);
        //CustomListAdapter_ActivityAddFamily customListAdapterActivityAddFamily = new CustomListAdapter_ActivityAddFamily(getBaseContext(),rooms);
        ////disable:@}

        CustomListAdapter_FamilySettings customListAdapterFamilySettings = new CustomListAdapter_FamilySettings(this, members_names, imgid,description,member_authority); //TODO:load data from database.
        listView_Members.setAdapter(customListAdapterFamilySettings);
        GUI gui = new GUI();
        gui.dynamicallySizingLayout(linearLayout_MembersBased,listView_Members,customListAdapterFamilySettings,customListAdapterFamilySettings.getCount(),40);
        listView_Members.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"go to Family Members page",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),ActivityFamilyMembers.class);
                startActivity(intent);
            }
        });

    }//onCreate


    public static String FAMILY_NAME ="";
    private void loadFamilySettings(){
        //get "Family Name" from previous page(ActivityHomeManagement.java).
        textView_FamilyName.setText(FAMILY_NAME);
        //TODO:load family settings from database.
        textView_RoomCountAndGoToRoomManagement.setText("6 Oda");
    }
     private void editFamilyName(){
         Keyboard.getInstance(getApplicationContext()).showKeyboard();//show keyboard
         final Alert.InputDialog inputDialog = new Alert().new InputDialog();
         AlertDialog.Builder dialogBuilder = inputDialog.show(ActivityFamilySettings.this,getString(R.string.family_name),textView_FamilyName.getText().toString());
         dialogBuilder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
                 String family_new_name = inputDialog.getContentOfEditTextOnAlertDialog().trim();
                 if(family_new_name.length() < 1)
                 {
                     Toast.makeText(getApplicationContext(),getString(R.string.msg_Please_enter_family_name),Toast.LENGTH_SHORT).show();
                     return;
                 }
                 textView_FamilyName.setText(family_new_name);
                 FAMILY_NAME = family_new_name;///
                 ///TODO:get Family Name from "editText_FamilyName" and then set into the database.
                 Keyboard.getInstance(getApplicationContext()).hideKeyboard();
                 Toast.makeText(getApplicationContext(),""+family_new_name,Toast.LENGTH_SHORT).show();
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

    private void goToRoomManagement(){
        Toast.makeText(getApplicationContext(),"go to Room Management page.",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),ActivityRoomManagement.class);
        // intent.putExtra("Family_Id",44 /*todo:family_id*/); //TODO: send related family id value,for load room names related this Family...!!!
        ActivityRoomManagement.FAMILY_ID = 44;//TODO: send related family id value,for load room names related this Family...!!!
        ActivityRoomManagement.FROM_PAGE = "ActivityFamilySettings.java";
        startActivity(intent);
    }//goToRoomManagement()
    private void setFamilyLocation(){
        Toast.makeText(getApplicationContext(),"Set Family Location.",Toast.LENGTH_SHORT).show();
    }

    private void goToBack(){
        Intent get_intent = getIntent();
        String came_from = get_intent.getStringExtra("came_from");
        if(came_from !=null && came_from.equals("ActivitySharedDevice.java")){
            Intent intent = new Intent(getApplicationContext(),ActivitySharedDevice.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(getApplicationContext(), ActivityHomeManagement.class);
            startActivity(intent);
        }
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
