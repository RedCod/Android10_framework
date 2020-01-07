package com.example.trident.smart;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.Alert;
import com.example.trident.common.BetweenActivities;
import com.example.trident.common.DeviceType;
import com.example.trident.common.GUI;
import com.example.trident.common.Keyboard;
import com.example.trident.common.MyCustomObject;
import com.example.trident.common.MyLog;
import com.example.trident.common.SettingCover;
import com.example.trident.common.Static;
import com.example.trident.smart.custom.SmartAndAutomationItem;
import com.example.trident.smart.custom.SmartAndAutomationSettingsListContents;

import java.util.ArrayList;
import java.util.Random;

public class ActivitySmartSettings extends AppCompatActivity  {
    /**
     * called by FragmentSmart.java,CustomListAdapter_FragmentSmart_ScenarioList.java
     *
     * Sayfa:"Akıllı Seneryo" ekle-düzenle.
     *
     * This Class will be used for both "insertion" and "editing".
     *
     *
     * TODO: Bu sayfayı da "ActivityAutomationSettings.java" gibi kayan sayfa ve listview'in içeriğine göre yeniden boyutlandırılabilir yap.
     */

    //static MyCustomObject listenerObject = new MyCustomObject();
    GUI gui;// = new GUI();//for dynamically sizing layout.
    static ScrollView scrollView_Based;
    LinearLayout linearLayout_AddActions;
    RecyclerView recyclerView_ActionsList;
    TextView textView_ScenarioName;
    TextView textView_DeleteSmart;
    LinearLayout linearLayout_ActionsBase;
    RecyclerViewAdapter_AutomationSettingsItem recyclerViewAdapter = null;
    private static String PAGE_TITLE ="";
    private static int COVER_IMAGE_ID = new Random().nextInt(10);//Generate a random ID index.
    private static int SMART_ID = -1;//this value get from database. Id value of "smart".
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_settings);
        //
        TextView textView_PageTitle                   = (TextView)findViewById(R.id.textView_PageTitle);
        LinearLayout linearLayout_Back                = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                            = (Button)findViewById(R.id.button_Back);
        Button button_Save                            = (Button)findViewById(R.id.button_Save);
        /*final TextView*/ textView_ScenarioName      = (TextView)findViewById(R.id.textView_ScenarioName);
        LinearLayout linearLayout_ScenarioItemCover   = (LinearLayout)findViewById(R.id.linearLayout_ScenarioItemCover);
        ImageView imageView_GoToSettingCover          = (ImageView)findViewById(R.id.imageView_GoToSettingCover);
        /*LinearLayout*/ linearLayout_AddActions      = (LinearLayout)findViewById(R.id.linearLayout_AddActions);
        linearLayout_ActionsBase                      = (LinearLayout)findViewById(R.id.linearLayout_ActionsBase);
        ImageView imageView_Plus_AddActions           = (ImageView)findViewById(R.id.imageView_Plus_AddActions);
        recyclerView_ActionsList                      = findViewById(R.id.recyclerView_ActionsList);
        Switch switchButton_ShowOnHomepage            = (Switch)findViewById(R.id.switchButton_ShowOnHomepage);
        textView_DeleteSmart                          = (TextView)findViewById(R.id.textView_DeleteSmart);
        scrollView_Based                              = (ScrollView)findViewById(R.id.scrollView_Based);
        //
        gui = new GUI();

        String page_title    = getIntent().getStringExtra(BetweenActivities.page_title);

        String scenario_name = getIntent().getStringExtra(BetweenActivities.scenario_name);//senaryou başlığı
        final String for_what      = getIntent().getStringExtra(BetweenActivities.for_what);//for "Add" or "Edit" ?
        if(page_title !=null)
            PAGE_TITLE = page_title;
        textView_PageTitle.setText("Akıllı Senaryo-"+PAGE_TITLE);
        if(scenario_name !=null)
            Static.SCENARIO_NAME = scenario_name;
        textView_ScenarioName.setText(Static.SCENARIO_NAME);

        ///Cover Image:@{
        int cover_image_id = getIntent().getIntExtra("cover_image_id",-1);//get from the "ActivitySmartSettingCover.java"
        if(cover_image_id != -1)
            COVER_IMAGE_ID = cover_image_id;
         SettingCover settingCover = new SettingCover();
         linearLayout_ScenarioItemCover.setBackgroundResource(settingCover.getImage(COVER_IMAGE_ID));
        ///Cover Image:@}

        //INIT apply:
        if(for_what !=null)
            INIT_Apply(for_what);//!!!COUTION:!!!!  //stay is here....>>>>>>>>>>>>>>
        else
            loadAllSmart();
        //Event:@{
        linearLayout_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to back:
                goToBack();
            }
        });
        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to back:
                goToBack();
            }
        });
        button_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save: todo: save smart settings to the database.
                /**todo:
                 *  + if "listView_Actions" items count is  "< 1",please don't save and throw message to user.
                 *  Because at least "1" transaction must be specified.
                 *  ELSE
                 *   + save and go to back
                 */
                saveAllSmartSettings();
                Toast.makeText(getApplicationContext(), "Kaydedildi", Toast.LENGTH_SHORT).show();
                goToBack();
            }
        });
        linearLayout_AddActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo:go to "select transaction(işlem seçim page)"
               // MyLog.d("kerim","LAYOUT go to(İÇ LAYOUT) (Select Trancastion-işlem seçim- page.");
                goToSelectActionPage();
            }
        });
        imageView_Plus_AddActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MyLog.d("kerim","Imageview_Plus_AddAction (Select Trancastion-işlem seçim- page.");
                goToSelectActionPage();
            }
        });

        switchButton_ShowOnHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo:show on the homepage
            }
        });
        textView_ScenarioName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Alert.InputDialog inputDialog = new Alert().new InputDialog();
                final AlertDialog.Builder alertDialog = inputDialog.show(ActivitySmartSettings.this,getString(R.string.edit_name),getString(R.string.enter_the_name_of_the_smart),
                        textView_ScenarioName.getText().toString());
                Keyboard.getInstance(getApplicationContext()).showKeyboard();//show keyboard
                alertDialog.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String content = inputDialog.getContentOfEditTextOnAlertDialog().trim();
                        if(content.length() > 0){
                            textView_ScenarioName.setText(content);
                            Static.SCENARIO_NAME = content;
                        }else
                            Toast.makeText(getApplicationContext(), getString(R.string.please_enter_a_name_for_the_scene), Toast.LENGTH_SHORT).show();
                        Keyboard.getInstance(getApplicationContext()).hideKeyboard();//hide keyboard
                    }
                });
                alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Keyboard.getInstance(getApplicationContext()).hideKeyboard();//hide keyboard
                    }
                });
                alertDialog.create().show();
            }
        });
        imageView_GoToSettingCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for scrollView position:@{
                keepPageCurrentScrollPosition();
                //for scrollView position:@}
                //go to:
                //MyLog.d("kerim[ActivitySmartSettings]",getIntent().getStringExtra(BetweenActivities.for_what));
                Intent intent = new Intent(getApplicationContext(),ActivitySmartSettingCover.class);
                intent.putExtra(BetweenActivities.for_what,getIntent().getStringExtra(BetweenActivities.for_what));//we will need when we back here this class.
                intent.putExtra(BetweenActivities.where_come_from,"ActivitySmartSettings.java");
                startActivity(intent);
            }
        });
        textView_DeleteSmart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: delete Smart
            }
        });
        //event:}

        //for scrollView position[24100943]:@{
        scrollView_Based.post(new Runnable() {
            @Override
            public void run() {
                if(getPageCurrentScrollPosition() == 0)
                    scrollView_Based.pageScroll(View.FOCUS_UP);
                else
                    scrollView_Based.scrollTo(0,getPageCurrentScrollPosition());//pageScroll(scroll_position);
            }//run
        });
        //for scrollView position[24100943]:@}

      /*  /////////////////////// for listener.
        listenerObject.setCustomObjectListener(new MyCustomObject.MyCustomObjectListener() {
            @Override
            public void requestAutomationResult(String forWhat, String[] itemNames, String[] checkStates) {

                apply_AddingAutomation(itemNames,checkStates);
            }
        });*/

    }//onCreate


    /**
     * Listenerin çağrılacağı yere-class'a object çekmek için.
     * @return
     */
  /*  public static  MyCustomObject getListenerObject(){
        return listenerObject;
    }*/
    private void INIT_Apply(String for_what){
        //Start firstly:
        MyLog.d("kerim_INIT_Apply[ActivitySmartSettings]","for_what:" + for_what);
        if(for_what.equals("Add")){//for Add
            apply_AddState();
        }else if(for_what.equals("Edit")){//Edit
            apply_EditState();
        }else if(for_what.equals("Edit_Switch")){
            apply_EditSwitchState();
        }else if(for_what.equals("Edit_TimeLapse")){
            apply_EditTimeLapseState();
        }else if(for_what.equals("Add_Action")){
            apply_AddingAction();
        }else if(for_what.equals("Add_Action_TimeLapse")){
            apply_AddingActionTimeLapse();
        }else if(for_what.equals("Add_Automation")){
            apply_AddingAutomation();
        }
    }
// #################################################################################################+start:@{//
   SmartAndAutomationSettingsListContents smartAndAutomationSettingsListContents =
                                          new SmartAndAutomationSettingsListContents();
    private void loadAllSmart(){
        smartAndAutomationSettingsListContents.fill();
        loadListViewContent();
    }

    private void loadListViewContent(){
        ArrayList<SmartAndAutomationItem> smartAndAutomationItems = smartAndAutomationSettingsListContents.getItems();
        /*RecyclerViewAdapter_AutomationSettingsItem*/ recyclerViewAdapter =
                new RecyclerViewAdapter_AutomationSettingsItem(this,smartAndAutomationItems,"ActivitySmartSettings.java");
        ItemTouchHelper.Callback callback = new ItemMoveCallback_SmartAndAutomationSettingsItem(recyclerViewAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView_ActionsList);
        recyclerView_ActionsList.setAdapter(recyclerViewAdapter);
        if(recyclerViewAdapter.getItemCount() < 1)
            apply_AddState();
        gui.dynamicallySizingLayout(linearLayout_ActionsBase,recyclerView_ActionsList,recyclerViewAdapter,smartAndAutomationSettingsListContents.count(),90);
    }
// #################################################################################################-end:@}//

// #################################################################################################+Apply:@{
    private void apply_AddState(){
        textView_DeleteSmart.setVisibility(View.GONE);
        recyclerView_ActionsList.setVisibility(View.GONE);
        linearLayout_AddActions.setVisibility(View.VISIBLE);
    }
    private void apply_EditState(){
        textView_DeleteSmart.setVisibility(View.VISIBLE);
        linearLayout_AddActions.setVisibility(View.GONE);
        recyclerView_ActionsList.setVisibility(View.VISIBLE);
        loadAllSmart();
    }
    private void apply_EditSwitchState(){
        if(SMART_ID != -1)//so had this record before. So is  erasable
            textView_DeleteSmart.setVisibility(View.VISIBLE);
        linearLayout_AddActions.setVisibility(View.GONE);
        recyclerView_ActionsList.setVisibility(View.VISIBLE);
        int position = getIntent().getIntExtra(BetweenActivities.position,-1);
        String selected_value = getIntent().getStringExtra(BetweenActivities.selected_value);
        if(selected_value != null)
            smartAndAutomationSettingsListContents.editState(position,selected_value);
        loadListViewContent();
    }
    private void apply_EditTimeLapseState(){
        if(SMART_ID != -1)//so had this record before. So is  erasable
            textView_DeleteSmart.setVisibility(View.VISIBLE);
        linearLayout_AddActions.setVisibility(View.GONE);
        recyclerView_ActionsList.setVisibility(View.VISIBLE);
        int position = getIntent().getIntExtra(BetweenActivities.position,-1);
        String selected_value = getIntent().getStringExtra(BetweenActivities.selected_value);
        if(selected_value != null)
            smartAndAutomationSettingsListContents.editState(position,selected_value);
        loadListViewContent();
    }
    private void apply_AddingAction(){
        if(SMART_ID != -1)//so had this record before. So is  erasable
            textView_DeleteSmart.setVisibility(View.VISIBLE);
        linearLayout_AddActions.setVisibility(View.GONE);
        recyclerView_ActionsList.setVisibility(View.VISIBLE);
        String device_name     = getIntent().getStringExtra(BetweenActivities.device_name);
        String selected_value  = getIntent().getStringExtra(BetweenActivities.selected_value);
        String device_type     = getIntent().getStringExtra(BetweenActivities.device_type);
        //String device_state    = getIntent().getStringExtra(BetweenActivities.device_state);
        String device_location = getIntent().getStringExtra(BetweenActivities.device_location);
        if(selected_value != null) {
            SmartAndAutomationItem smartAndAutomationItem = new SmartAndAutomationItem();
            smartAndAutomationItem.setName(device_name);
            smartAndAutomationItem.setState(selected_value);
            smartAndAutomationItem.setLocation(device_location);
            smartAndAutomationItem.setType(device_type);
            smartAndAutomationSettingsListContents.setItem(smartAndAutomationItem);
        }
        loadListViewContent();
        gui.dynamicallySizingLayout(linearLayout_ActionsBase,recyclerView_ActionsList,recyclerViewAdapter,smartAndAutomationSettingsListContents.count(),90);
    }
    private void apply_AddingActionTimeLapse(){
        if(SMART_ID != -1)//so had this record before. So is  erasable
            textView_DeleteSmart.setVisibility(View.VISIBLE);
        linearLayout_AddActions.setVisibility(View.GONE);
        recyclerView_ActionsList.setVisibility(View.VISIBLE);
        String device_name     = "time lapse";//fixme:this value is unnecessary.Because assign.Because this value is assigned in the "RecyclerViewAdapter_AutomationSettingsItem.java" side.
        String selected_value  = getIntent().getStringExtra(BetweenActivities.selected_value);
        String device_type     = DeviceType.TIME_LAPSE;//getIntent().getStringExtra("device_type");
        if(selected_value != null) {
            SmartAndAutomationItem smartAndAutomationItem = new SmartAndAutomationItem();
            smartAndAutomationItem.setName(getString(R.string.time_lapse));
            smartAndAutomationItem.setState(selected_value);
            smartAndAutomationItem.setType(device_type);
            smartAndAutomationItem.setLocation("-");//fixme: for IndexoutofException fix.
            smartAndAutomationSettingsListContents.setItem(smartAndAutomationItem);
        }
        loadListViewContent();
    }

    private void apply_AddingAutomation(){
        /**
         * TODO: !!!!!!!!!!!ATTENTION!!!!!!!!!!!!!!!!!!!!!!
         *Eğer X otonom eklenmişse tekrar ekleme,eklemeyi engelle.
         */
        if(SMART_ID != -1)//so had this record before. So is  erasable
            textView_DeleteSmart.setVisibility(View.VISIBLE);
        linearLayout_AddActions.setVisibility(View.GONE);
        recyclerView_ActionsList.setVisibility(View.VISIBLE);
        String[] item_name   = getIntent().getStringArrayExtra(BetweenActivities.item_name);//array.
        String[] check_state = getIntent().getStringArrayExtra(BetweenActivities.check_state);
        String device_type   = DeviceType.AUTOMATION;
        /**
         * fixme: daha performanslı halini geliştir. "ActivityAutomationSelect.java'dan buraya intent edilir."
         * benzer otonom itemlerini listeye tekrar tekrar eklememek için:
         */
        SmartAndAutomationItem smartAndAutomationItem;
        if(item_name != null) {
            for (int i = 0; i < item_name.length; i++) {
                if (smartAndAutomationSettingsListContents.isExists(item_name[i]) == false && check_state[i].equals("True")) {
                    smartAndAutomationItem = new SmartAndAutomationItem();
                    smartAndAutomationItem.setName(item_name[i]);
                    //MyLog.d("kerim[XX]", "itemNames:" + item_name[i]);
                    smartAndAutomationItem.setState(getString(R.string.enable));
                    smartAndAutomationItem.setType(device_type);
                    smartAndAutomationItem.setLocation(getString(R.string.automation));
                    smartAndAutomationSettingsListContents.setItem(smartAndAutomationItem);
                } else {
                    if (check_state[i].equals("False"))
                        smartAndAutomationSettingsListContents.remove(item_name[i]);
                }
            }
        }
        loadListViewContent();
        gui.dynamicallySizingLayout(linearLayout_ActionsBase,recyclerView_ActionsList,recyclerViewAdapter,smartAndAutomationSettingsListContents.count(),90);
    }


// #################################################################################################-apply:@}
    /**
     * linearLayout_ActionsBase" grows as the "recyclerView_ActionsList" content grows
     */
 /* !!!!!!-REMOVE THIS BLOCK-!!!!!!!!!!!!!!! we moved it to GUI.java
  private void resizeLayoutActionsHeight(int count){


        ///for Layout Resize:@{
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) linearLayout_ActionsBase.getLayoutParams();
        int total_height = 90;///!!!!!!!!! ATTENTION:do not touch this value. default = 90 !!!!!!!!!!!
        int item_count = count;
        RecyclerViewAdapter_AutomationSettingsItem.MyViewHolder item;
        for(int i=0; i<item_count;i++){
            item = recyclerViewAdapter.onCreateViewHolder(recyclerView_ActionsList,0);
            item.linearLayout_MainItemBase.measure(0,0);
            total_height += item.linearLayout_MainItemBase.getMeasuredHeight();
        }
        layoutParams.height = total_height + (1 * (item_count));
        linearLayout_ActionsBase.setLayoutParams(layoutParams);
        linearLayout_ActionsBase.requestLayout();
        layoutParams = (ConstraintLayout.LayoutParams) linearLayout_ActionsBase.getLayoutParams();
        ///for Layout Resize:@}
        //
        ///RecyclerView Resize:@{
        ViewGroup.LayoutParams params2 = recyclerView_ActionsList.getLayoutParams();
        params2.height = layoutParams.height; //same height.
        recyclerView_ActionsList.setLayoutParams(params2);
        recyclerView_ActionsList.requestLayout();
        ///RecyclerView Resize:@}
    }*/


    private void saveAllSmartSettings(){
        if(recyclerViewAdapter.getItemCount() < 1 ){
            MyLog.d("kerim,ActivitySmartSettings","Böyle kayıt yapaman lo");
            return;
        }
        /*
          ///TODO: save all arrayList content:
          +send all list contents to Database server. update if already registered else insert.
          If recordings were taken to update.This means that the smart scenario has an ID value.
         */
        //todo: "ID_for_SMART"  is ID of database row.  //using for update on database.
        //arrayList_Devices
        //arrayList_DevicesLocation
        //arrayList_DevicesState
        //arrayList_DevicesType
    }
    private void goToSelectActionPage(){
        //for scrollView position:@{
        keepPageCurrentScrollPosition();
        //for scrollView position:@}
        Intent intent = new Intent(getApplicationContext(),ActivitySmartSelectAction.class);//go to "İşlem Seçin"
        intent.putExtra(BetweenActivities.where_come_from,"ActivitySmartSettings.java");
        intent.putExtra(BetweenActivities.for_what,"Add_Action");//we will need when we back here this class.
        startActivity(intent);
    }

    /**
     * it is called from this class and the "RecyclerViewAdapter_AutomationSettingsItem.java"
     */
    public static void keepPageCurrentScrollPosition(){
        Static.CURRENT_SCROLL_POSITION = scrollView_Based.getScrollY();
    }
    private int getPageCurrentScrollPosition(){
        return Static.CURRENT_SCROLL_POSITION;
    }
    private void resetPageCurrentScrollPosition(){
        Static.CURRENT_SCROLL_POSITION =0;//reset. we no longer need retained value.
    }

    private void goToBack(){
        resetPageCurrentScrollPosition(); //reset page current scroll position.
        Intent intent = new Intent(getApplicationContext(),ActivityMainPage.class);
        intent.putExtra(BetweenActivities.where_come_from,"ActivitySmartSettings.java:SMART");
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
