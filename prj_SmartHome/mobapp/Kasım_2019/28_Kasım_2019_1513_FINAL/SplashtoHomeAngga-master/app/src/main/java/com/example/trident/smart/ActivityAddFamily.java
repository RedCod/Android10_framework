package com.example.trident.smart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.Keyboard;
import com.example.trident.common.MyLog;
import com.example.trident.smart.custom.CustomListAdapter_ActivityAddFamily;

import java.util.Collection;

public class ActivityAddFamily extends AppCompatActivity {

    /** "Aile Ekle"
     *  TODO: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! CAOUTION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *      if the user is "Administrator",can use this page. But else,can not use this page.
     *      + CAN NOT EDIT AND REMOVE. CAN ONLY DISPLAYED!
     *
     */
    Button button_Back;
    Button button_Done;
    Button button_GoToAddRoom;
    EditText editText_FamilyName;
    TextView textView_SetFamilyLocationF;
    ListView listView_Rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family);

        LinearLayout linearLayout_Back               = (LinearLayout)findViewById(R.id.linearLayout_Back);
        button_Back                                  = (Button)findViewById(R.id.button_Back);
        button_Done                                  = (Button)findViewById(R.id.button_Done);
        editText_FamilyName                          = (EditText)findViewById(R.id.editText_FamilyName);
        textView_SetFamilyLocationF                  = (TextView)findViewById(R.id.textView_SetFamilyLocationF);
        ImageView imageView_SetFamilyLocationF_arrow = (ImageView)findViewById(R.id.imageView_SetFamilyLocationF_arrow);
        listView_Rooms                               = (ListView)findViewById(R.id.listView_Rooms);
        button_GoToAddRoom                           = (Button)findViewById(R.id.button_GoToAddRoom);

        ///TODO: "rooms" array for test. this array content,get from database.
        String[] rooms = {"oda1","oda2","oda3","oda4","oda5","oda6","oda7","oda8","oda9","oda10"/*,"oda","oda","oda","oda","oda","oda","oda"*/};
        //ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, android.R.id.text1, rooms);
        final CustomListAdapter_ActivityAddFamily customListAdapterActivityAddFamily = new CustomListAdapter_ActivityAddFamily(getBaseContext(),rooms);
        listView_Rooms.setAdapter(customListAdapterActivityAddFamily);

//Back:@{
        linearLayout_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivityHomeManagement();
            }
        });
        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {///BACK-GERİ:
              goToActivityHomeManagement();
            }
        });
//Back:@}
        button_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {///DONE-TAMAM:
                //Toast.makeText(getApplicationContext(),"Tamamlandı.",Toast.LENGTH_SHORT).show();
               String room_name = editText_FamilyName.getText().toString().trim();
               if(room_name.length() < 1){
                    Toast.makeText(getApplicationContext(), getString(R.string.msg_Please_enter_family_name), Toast.LENGTH_SHORT).show();
                    return;
                }

                ///TODO: get selected rooms,and then insert into database associating with the family:@{
                Collection<String> collectionSelectedRooms = customListAdapterActivityAddFamily.getSelectedRooms();
                for(String item : collectionSelectedRooms)
                   //if(MyLog.DEGUB) MyLog.d("kerim","item(room):" + item);
                ///TODO:@}

                ///TODO:--------------------------------@{
                ///...Get data from EditText etc.
                ///...Save data to database on Server
                ///...Show Mesaj on Dialog: "Family save successfuly",and Button_1:"Show Family",Button_2:"Done"
                ///if Button_1 click,go to "Show/View family page".
                ///if Button_2 click,go back. So call "goToActivityHomeManagement()" function.
                ///TODO:---------------------------------@}
                Keyboard.getInstance(getApplicationContext()).hideKeyboard();//hide keyboard.
                //and then go to "ActivityHomeManagement.java":
                ///goToActivityHomeManagement();
            }
        });
        textView_SetFamilyLocationF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///...goin to set location PAGE:
                //...
                //Toast.makeText(getApplicationContext(),"going to set location.",Toast.LENGTH_SHORT).show();
                goToSetFamilyLocation();
            }
        });
        imageView_SetFamilyLocationF_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"going to set location.",Toast.LENGTH_SHORT).show();
                goToSetFamilyLocation();
            }
        });
        button_GoToAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//Başka bir oda ekle:
              ///goin to "AddRoom" page.
              Toast.makeText(getApplicationContext(),"going to Add Room page.",Toast.LENGTH_SHORT).show();//remove this line.
              ActivityAddRoom.FROM_WICH_PAGE = "ActivityAddFamily.java"; //for "room_name" insert into to database or
              Intent intent = new Intent(getApplicationContext(),ActivityAddRoom.class);
              startActivity(intent);
            }
        });

    }//onCreate

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            goToActivityHomeManagement();
        }
        else
            return super.onKeyDown(keyCode, event);
        return false;
    }

    private void goToActivityHomeManagement(){
        Intent intent = new Intent(getApplicationContext(),ActivityHomeManagement.class);
        startActivity(intent);
    }
    private void goToSetFamilyLocation(){
        Toast.makeText(getApplicationContext(),"going to set location.",Toast.LENGTH_SHORT).show();
    }
}
