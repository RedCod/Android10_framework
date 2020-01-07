package com.example.trident.smart;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.trident.common.MyLog;
import com.example.trident.smart.R;

public class ActivityAddRoom extends AppCompatActivity {

    /**  "Oda Ekle"
     *
     * TODO: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! CAOUTION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *      if the user is "Administrator",can use this page. But else,can not use this page.
     *      + CAN NOT EDIT AND REMOVE. CAN ONLY DISPLAYED!
     *
     */

    public static String FROM_WICH_PAGE = "";//"ActivityAddFamily.java" OR "ActivityRoomManagement.java"
    public static int WICH_FAMILY_ID = 0;    //WICH_FAMILY_ID set "ActivityRoomManagement.java" for to add the ROOM to the related Family.
    EditText editText_RoomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        LinearLayout linearLayout_Back = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back             = (Button)findViewById(R.id.button_Back);
        Button button_Done             = (Button)findViewById(R.id.button_Done);
        /*EditText*/ editText_RoomName = (EditText)findViewById(R.id.editText_RoomName);
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
                //ActivityAddRoom.super.onBackPressed();//return to the page you came from ;)
            }
        });
//Back:@}
       button_Done.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {//DONE:
               String room_name = editText_RoomName.getText().toString().trim();
               if(room_name.length() < 1)
               {
                   Toast.makeText(getApplicationContext(),getString(R.string.msg_Please_enter_room_name),Toast.LENGTH_SHORT).show();
                   return;
               }

               ///todo: if "WICH_FAMILY_ID" value is not '0',room_name insert into the database related "Family id".
               //if(WICH_FAMILY_ID > 0)//have family id.
               if(FROM_WICH_PAGE.equals("ActivityRoomManagement.java"))
               {
                   //todo: room_name insert into the database related "Family id(WICH_FAMILY_ID)". So assign the room to the X family.
                   //insert into database "room_name" and "WICH_FAMILY_ID".
                   ///..
                   //and When finished,go back to the "ActivityRoomManagement.java" page.
                   Intent intent = new Intent(getApplicationContext(),ActivityRoomManagement.class);
                   startActivity(intent);
               }else if(FROM_WICH_PAGE.equals("ActivityAddFamily.java"))
               {
                   //do not have family id. because the room will not be assigned to the family
                   //todo: but,if "WICH_FAMILY_ID" value is '0',room_name sending to ListView in the "ActivityAddFamily.java".
                   ///...
                   ///...
                   //and When finished,go back to the "ActivityAddFamily.java" page.
                   Intent intent = new Intent(getApplicationContext(),ActivityAddFamily.class);
                   intent.putExtra("room_name",room_name);//send "room_name" to ActivityAddFamily.java
                   startActivity(intent);
               }

               //ActivityAddRoom.super.onBackPressed();//return to the page you came from ;)
               //to do end of...
               WICH_FAMILY_ID = 0;
           }
       });

    }//onCreate


    private void goToBack(){
        Intent intent = null;
        if(FROM_WICH_PAGE.equals("ActivityRoomManagement.java"))
            intent = new Intent(getApplicationContext(),ActivityRoomManagement.class);
        else  if(FROM_WICH_PAGE.equals("ActivityAddFamily.java"))
            intent = new Intent(getApplicationContext(),ActivityAddFamily.class);

        startActivity(intent);
    }

    /**
     *For static Button(prepared for room name).
     * @param view
     */
     public void buttonsRecommended(View view){//Button:Click event in the "activity_add_room.xml" page.
         editText_RoomName.setText(((Button)view).getText());
         editText_RoomName.setSelection(editText_RoomName.getText().length());
     }
}
