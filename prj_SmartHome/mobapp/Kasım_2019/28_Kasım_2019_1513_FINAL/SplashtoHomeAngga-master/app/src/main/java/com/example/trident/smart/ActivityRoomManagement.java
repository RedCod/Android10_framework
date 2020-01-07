package com.example.trident.smart;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.trident.common.Alert;
import com.example.trident.common.MyLog;
import com.example.trident.smart.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class ActivityRoomManagement extends AppCompatActivity implements FragmentHome.OnFragmentInteractionListener,
        Fragment_FragmentHome_RoomDevices.OnFragmentInteractionListener,Fragment_FragmentHome_BlankRoomContent.OnFragmentInteractionListener{

    /**
     * "Oda Yönetimi"
     *
     * TODO: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! CAOUTION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *      if the user is "Administrator",can use this page. But else,can not use this page.
     *      + CAN NOT EDIT AND REMOVE. CAN ONLY DISPLAYED!
     *
     */

    private static String TAG = "[ActivityRoomManagement]";
    public static int FAMILY_ID= 0;//FAMILY_ID set from "ActivityFamilySettings.java" page.
    public static String FROM_PAGE = "";//where did we come here from. We come "FragmentHome.java" OR "ActivityFamilySettings.java".
    private static String msg_save_room_changes_stringId;
    private static String pst_btn_save;
    private static String ngtv_btn_donotsave;
    static Button button_Save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_management);
        ///
      /*  msg_save_room_changes_stringId = getString(R.string.msg_SaveRoomChanges);
        pst_btn_save                   = getString(R.string.save);
        ngtv_btn_donotsave             = getString(R.string.donotsave);*/
        //
        LinearLayout linearLayout_Back  = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back              = (Button)findViewById(R.id.button_Back);
        /*Button*/ button_Save          = (Button)findViewById(R.id.button_Save);
        RecyclerView   recyclerView     = findViewById(R.id.recyclerView_Rooms);
        Button button_GoToAddRoomP      = (Button)findViewById(R.id.button_GoToAddRoomP);
//Back:@{
        linearLayout_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessageDailog("BACKWARD");
            }
        });
        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//BACK:
              showMessageDailog("BACKWARD");
            }
        });
//Back:@}
        button_Save.setVisibility(View.INVISIBLE);//hide at firstly load.
        button_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//EDİT:
                ///TODO:save changes.
                saveChanges();
                setButton_Edit_Visible(false);
                //goBack();
            }
        });
        button_GoToAddRoomP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//Go to add Room Page:
                ///TODO: go to "add Room" Page.
                ActivityAddRoom.FROM_WICH_PAGE = "ActivityRoomManagement.java";
                ActivityAddRoom.WICH_FAMILY_ID = FAMILY_ID; //for to add the ROOM to the related Family.
                /*Intent intent = new Intent(getApplicationContext(),ActivityAddRoom.class);
                startActivity(intent);*/
                showMessageDailog("FORWARD");
            }
        });

        final String[]  rooms = loadRoom_RelatedFamilyId();//{"Oda1","Oda2","Oda3","Oda4","Oda5","Oda6","Oda7"};
        ArrayList<String> arrayList_Rooms = new ArrayList<String>(Arrays.asList(rooms));
        ArrayList<Integer> arrayList_HowManyDevicesInRoom = getDeviceCount_RelatedRoom();
        RecyclerViewAdapter_RoomManagementRoomItem recyclerViewAdapter = new RecyclerViewAdapter_RoomManagementRoomItem(this,arrayList_Rooms,arrayList_HowManyDevicesInRoom);
        ItemTouchHelper.Callback callback = new ItemMoveCallback_RoomManagementRoomItem(recyclerViewAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(recyclerViewAdapter);
/////////////////////////

    }//onCreate

    /*private int getFamilyId_from_ActivityFamilySettingsPage(){
        try{
            Bundle bundle = new Bundle();
            bundle = getIntent().getExtras();
            FAMILY_ID = bundle.getInt("Family_Id");
            MyLog.d(TAG,"GET Family ID:" + FAMILY_ID);
            return FAMILY_ID;
        }catch (NullPointerException npex){
            return  FAMILY_ID;
        }
    }*/
    private String[] loadRoom_RelatedFamilyId(){
        /**TODO:#######################
         * 1.Firstly,get FamilyID from "ActivityFamilySettings" page.
         * 2.Secondly,load "Room Name" from database related  X family. So using "Family ID" value.
         * 3."Room Name"'s assign rooms array,and then return.
         */
        //int family_id = getFamilyId_from_ActivityFamilySettingsPage();
        int family_id = FAMILY_ID;
        String[] rooms = {"Oda1","Oda2","Oda3","Oda4","Oda5","Oda6","Oda7"};//todo: load from database.
        //THİS SİMULATES ROOM ID:@{ Fixme:
        for(int i=0;i<rooms.length;i++)
            loadDeviceCount_RelatedRoom(i);//send room id. Representative value
        //THİS SİMULATES ROOM ID:@}
        return rooms;
    }

////ROOM:@{
    ArrayList<Integer> arrayList_DeviceCount_RelatedRoom = new ArrayList<Integer>();
    /**
     * Prepare device count related available Room.
     * @param _roomId: Room Id value. For get decive count from database,related avaliable room.
     */
    private void loadDeviceCount_RelatedRoom(int _roomId)
    {
        /**TODO:######################
         * select count(room_id) from tbl_Devices WHERE  room_id = _roomId
         */

        //fixme: right now for TEST: these values get from database.
        arrayList_DeviceCount_RelatedRoom.add(1); //todo: add "count(room_id)" value you received from database.
    }
    private ArrayList<Integer> getDeviceCount_RelatedRoom(){

        /**TODO:######################
         *
         */
       return arrayList_DeviceCount_RelatedRoom;
    }
////ROOM:}

    public static boolean saveChanges(){
        //TODO: Save changes made for the room.
        //+save new room order...
        ///
        RecyclerViewAdapter_RoomManagementRoomItem.setRoomItemDragDropChanges(false);
        ///+save room new order...
        //...
        return true; //TODO: return database insert state.
    }
    private void goBack(){
        if(FROM_PAGE.equals("FragmentHome.java")){
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_for_FragmentHome,new FragmentHome()).commit();
        }else if(FROM_PAGE.equals("ActivityFamilySettings.java")){
            Intent intent = new Intent(getApplicationContext(),ActivityFamilySettings.class);
            startActivity(intent);
        }else{
            ///...
        }
        //ROOM_ITEM_DRAGDROP = false;
        RecyclerViewAdapter_RoomManagementRoomItem.setRoomItemDragDropChanges(false);
    }

    private void showMessageDailog(final String _state){
        if(RecyclerViewAdapter_RoomManagementRoomItem.isRoomItemDragDropChanges()==true){
            //TODO:show mesage dialog.  "

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivityRoomManagement.this);
            alertDialogBuilder.setMessage(getString(R.string.msg_saveroomchanges));
            /*Alert.MessageDialog messageDialog = new Alert().new MessageDialog();
            AlertDialog.Builder alertDialogBuilder = messageDialog.show(ActivityRoomManagement.this,"",msg_save_room_changes_stringId);
            */
            alertDialogBuilder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Toast.makeText(getApplicationContext(),"You clicked yes  d   button",Toast.LENGTH_LONG).show();
                            //YES,TODO:Save changes.
                            saveChanges();
                            //and finally go back:
                            if(_state.equals("BACKWARD"))
                                  goBack();
                            else if(_state.equals("FORWARD"))
                            {
                                Intent intent = new Intent(getApplicationContext(),ActivityAddRoom.class);
                                startActivity(intent);
                            }
                        }
                    });
            alertDialogBuilder.setNegativeButton(getString(R.string.donotsave),new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // finish();
                    //do nothing. go back:
                    if(_state.equals("BACKWARD"))
                        goBack();
                    else if(_state.equals("FORWARD"))
                    {
                        Intent intent = new Intent(getApplicationContext(),ActivityAddRoom.class);
                        startActivity(intent);
                    }
                }
            });
            alertDialogBuilder.create().show();
        }else{
            if(_state.equals("BACKWARD"))
                goBack();
            else if(_state.equals("FORWARD"))
            {
                Intent intent = new Intent(getApplicationContext(),ActivityAddRoom.class);
                startActivity(intent);
            }
        }
        RecyclerViewAdapter_RoomManagementRoomItem.setRoomItemDragDropChanges(false);
    }

   @Override
    public void onFragmentInteraction(Uri uri) {

    }


     public static void setButton_Edit_Visible(boolean _b){//called from "RecyclerViewAdapter_RoomManagementRoomItem.java"
        if(_b)
            button_Save.setVisibility(View.VISIBLE);
        else
            button_Save.setVisibility(View.INVISIBLE);
     }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            showMessageDailog("BACKWARD");
        }
        else
            return super.onKeyDown(keyCode, event);

        return false;
    }
}
