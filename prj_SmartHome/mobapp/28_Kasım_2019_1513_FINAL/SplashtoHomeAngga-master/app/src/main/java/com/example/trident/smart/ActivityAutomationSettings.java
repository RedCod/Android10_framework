package com.example.trident.smart;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DebugUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.Alert;
import com.example.trident.common.BetweenActivities;
import com.example.trident.common.DeviceType;
import com.example.trident.common.GUI;
import com.example.trident.common.Keyboard;
import com.example.trident.common.MonthsAndDays;
import com.example.trident.common.MyLog;
import com.example.trident.common.SettingCover;
import com.example.trident.common.Static;
import com.example.trident.smart.custom.CustomListAdapter_AutomationSettings;
import com.example.trident.smart.custom.SmartAndAutomationItem;
import com.example.trident.smart.custom.SmartAndAutomationSettingsListContents;
import com.example.trident.smart.custom.SmartAutomationSettingsConditionListContents;
import java.util.ArrayList;
import java.util.Random;

public class ActivityAutomationSettings extends AppCompatActivity {
    /**
     * called by "FragmentSmart.java" >>> "CustomListAdapter_FragmentSmart_AutomationList.java"
     *
     */
    /**
     * FIXME:if the problem persists = https://www.itworld.com/article/2705632/how-to-make-smooth-scrolling-listviews-in-android.html
     */
    GUI gui;
    static ScrollView scrollView_Based;
    TextView textView_ValidTime,textView_DeleteSmart;
    LinearLayout linearLayout_ConditionsBase;
    LinearLayout linearLayout_AddConditions;
    LinearLayout linearLayout_ActionsBase;//Action base
    LinearLayout linearLayout_AddActions;
    ListView listView_ConditionsList;
    RecyclerView recyclerView_ActionsList;
    CustomListAdapter_AutomationSettings customListAdapterAutomationConditionSettings =null;
    RecyclerViewAdapter_AutomationSettingsItem recyclerViewAdapter = null;
    private static int SMART_ID = -1;//this value get from database. Id value of "smart".
    private static String PAGE_TITLE ="";
    private static int COVER_IMAGE_ID = new Random().nextInt(10);//Generate a random ID index.
    private static int SCROLL_RATE = 0;//TODO: Alt sayfalara giderken bu sayfanın scroll konumunu sakla,geri dönerken bu değeri scroll'a uygulayarak kullanıcının en son sayfayı terk ettiği konuma kaydır.
    private static String REPEAT_CONTENT = null;//get from database in case of editing.

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automation_settings);
        //
        LinearLayout linearLayout_Back = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back             = (Button)findViewById(R.id.button_Back);
        TextView textView_PageTitle    = (TextView)findViewById(R.id.textView_PageTitle);//using for Add and Edit titles.
        Button button_Save             = (Button)findViewById(R.id.button_Save);
        //
        final TextView textView_AutomationName     = (TextView)findViewById(R.id.textView_AutomationName);
        LinearLayout linearLayout_AutomationItemCover = (LinearLayout)findViewById(R.id.linearLayout_AutomationItemCover);
        ImageView imageView_GoToSettingCover       = (ImageView)findViewById(R.id.imageView_GoToSettingCover);
        final Spinner spinner_WhichCondition    = (Spinner)findViewById(R.id.spinner_WhichCondition);
        ImageView imageView_Plus_AddCondition   = (ImageView)findViewById(R.id.imageView_Plus_AddCondition);
        linearLayout_ConditionsBase             = (LinearLayout)findViewById(R.id.linearLayout_ConditionsBase);
        listView_ConditionsList                 = (ListView)findViewById(R.id.listView_ConditionsList);
        linearLayout_AddConditions                   = (LinearLayout)findViewById(R.id.linearLayout_AddConditions);
        //
        linearLayout_ActionsBase                     = (LinearLayout)findViewById(R.id.linearLayout_ActionsBase);
        ImageView imageView_Plus_AddActions          = (ImageView)findViewById(R.id.imageView_Plus_AddActions);
        recyclerView_ActionsList                     = findViewById(R.id.recyclerView_ActionsList);
        linearLayout_AddActions                      = (LinearLayout)findViewById(R.id.linearLayout_AddActions);
        ImageView imageView_GoTo_ValidTimePeriodPage = (ImageView)findViewById(R.id.imageView_GoTo_ValidTimePeriodPage);
        LinearLayout linearLayout_ValidTimePeriod    = (LinearLayout)findViewById(R.id.linearLayout_ValidTimePeriod);
        textView_ValidTime                           = (TextView)findViewById(R.id.textView_ValidTime);
        textView_DeleteSmart                         = (TextView)findViewById(R.id.textView_DeleteSmart);
        /*final ScrollView*/ scrollView_Based        = (ScrollView)findViewById(R.id.scrollView_Based);

        String page_title       = getIntent().getStringExtra(BetweenActivities.page_title);
        //MyLog.d("kerim[ActivitySmartSettings]","page_title:" + page_title);
        String automation_name  = getIntent().getStringExtra(BetweenActivities.automation_name);
        String for_what         = getIntent().getStringExtra(BetweenActivities.for_what);//for "Add" or "Edit" ?
        if(page_title !=null)
            PAGE_TITLE = page_title;

        MyLog.d("kerim[ActivitySmartSettings]","for_What:" + for_what);
        textView_PageTitle.setText(PAGE_TITLE);//stay here for now..
        if(automation_name !=null)
            Static.AUTOMATION_NAME = automation_name;
        textView_AutomationName.setText(Static.AUTOMATION_NAME);

        ///Cover Image:@{
        int cover_image_id = getIntent().getIntExtra("cover_image_id",-1);//get from the "ActivitySmartSettingCover.java"
        if(cover_image_id != -1)
            COVER_IMAGE_ID = cover_image_id;
        SettingCover settingCover = new SettingCover();
        linearLayout_AutomationItemCover.setBackgroundResource(settingCover.getImage(COVER_IMAGE_ID));
        ///Cover Image:@}

///DEFAULT:@{
        gui = new GUI();

///DEFAULT:@}

        //INIT apply:
        if(for_what !=null)
            INIT_Apply(for_what);//!!!COUTION:!!!!  //stay is here....>>>>>>>>>>>>>>
        else {
            loadAllSmartConditions();//for Condition list
            loadAllSmartActions(); //for Action list
        }

        //todo: if recyclerView count > 0, visible listView,or gone:
        ///recyclerView_ConditionsList.setVisibility(View.VISIBLE);
        //todo: if recyclerView count > 0, visible listView,or gone:
        ///recyclerView_ActionsList.setVisibility(View.VISIBLE);

        ///Condition Spinner:@{
        String[] array_which_condition = {getString(R.string.condition_msg1),getString(R.string.condition_msg2)};
        //ArrayAdapter<String> dataAdapter_WhichCondition = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array_which_condition);
        ArrayAdapter<String> dataAdapter_WhichCondition = new ArrayAdapter<String>(this, R.layout.spinner_which_condition_item, array_which_condition);
        spinner_WhichCondition.setAdapter(dataAdapter_WhichCondition);
        ///Condition Spinnner:@}

        ///Events:@{
        linearLayout_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //back
                goToBack();
            }
        });
        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //back
                goToBack();
            }
        });
        button_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save: todo: save smart settings to the database.
                /**todo:
                 *  + if "recyclerView_ConditionsList" items count is  "< 1",please don't save and throw message to user.
                 *  Because at least "1" transaction must be specified. Also "recyclerView_ActionList" items count must be > 0.
                 *  Because the "condition" and "transaction" must be have.
                 *  ELSE
                 *   + save and go to back
                 */

                /**
                 * todo:
                 * Seçilen condition index değeri db de tutularak buna göre işlem yapılabilir. Öte yandan item tutulursa birçok karşılaştırma yapılması gerekir. Seç birini....
                 */
                String selected_condition_item = spinner_WhichCondition.getSelectedItem().toString();
                int selected_condition_position = spinner_WhichCondition.getSelectedItemPosition();
                //MyLog.d("kerim[ActivityAutomationSettings","seleced_condition:" + selected_condition_item +",position:" + selected_condition_position);

                saveAllAutomationSettings();
                Toast.makeText(getApplicationContext(), "Kaydedildi", Toast.LENGTH_SHORT).show();
                goToBack();
            }
        });
        textView_AutomationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Alert.InputDialog inputDialog = new Alert().new InputDialog();
                final AlertDialog.Builder alertDialog = inputDialog.show(ActivityAutomationSettings.this,getString(R.string.edit_name),getString(R.string.enter_the_name_of_the_smart),
                        textView_AutomationName.getText().toString());
                Keyboard.getInstance(getApplicationContext()).showKeyboard();//show keyboard
                alertDialog.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String content = inputDialog.getContentOfEditTextOnAlertDialog().trim();
                        if(content.length() > 0){
                            textView_AutomationName.setText(content);
                            Static.AUTOMATION_NAME= content;//for automation name text.
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
                //todo: go to Cover page.
                //for scrollView position:@{
                keepPageCurrentScrollPosition();
                //for scrollView position:@}

                MyLog.d("kerim[ActivityAutomationSettings]",getIntent().getStringExtra(BetweenActivities.for_what));
                Intent intent = new Intent(getApplicationContext(),ActivitySmartSettingCover.class);
                intent.putExtra(BetweenActivities.for_what,getIntent().getStringExtra(BetweenActivities.for_what));//we will need when we back here this class.
                intent.putExtra(BetweenActivities.where_come_from,"ActivityAutomationSettings.java");
                startActivity(intent);
            }
        });
        imageView_Plus_AddCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to add condition page:
                goToSelectConditionPage();
            }
        });


        listView_ConditionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                //for scrollView position:@{
                keepPageCurrentScrollPosition();
                //for scrollView position:@}

                //TODO:
                /**
                 * item click:
                 * + go to "ActivityTemperature.java"
                 * + go to "ActivityHumidity.java"
                 * + go to "ActivityWeather.java"
                 * + go to "ActivityAirQuality.java"
                 * + go to "ActivitySunriseSunset.java"
                 * + go to "ActivitySchedule.java"
                 * + go to "ActivitySwitch.java" //for device switch
                 *
                 * +send data:
                 *  "for_what"
                 *  seçilen item'e ait içerik değeri: şehir,ve diğer değerler". Böylece seçim sayfasında eski değeri uygulamış olarak sayfayı görüntüleriz.
                 */
                Intent intent = new Intent(getApplicationContext(),ActivityTemperature.class);//DEFAULT VALUE.
                SmartAndAutomationItem smartAndAutomationItem = smartAutomationSettingsConditionListContents.getItem(position);
                String get_item_type =smartAndAutomationItem.getType();
                if(get_item_type.equals(DeviceType.TEMPERATURE)){
                    //go to ActivityTemperature.java
                    intent = new Intent(getApplicationContext(),ActivityTemperature.class);
                    //intent.putExtra("item_type",get_item_type);//not need.
                    intent.putExtra(BetweenActivities.item_name,smartAndAutomationItem.getName());
                    intent.putExtra(BetweenActivities.current_city,smartAndAutomationItem.getLocation());

                }else if(get_item_type.equals(DeviceType.HUMIDITY)){
                    //go to "ActivityHumidity.java"
                    intent = new Intent(getApplicationContext(),ActivityHumidity.class);
                    //intent.putExtra("item_type",get_item_type);//not need.
                    intent.putExtra(BetweenActivities.item_name,smartAndAutomationItem.getName());
                    intent.putExtra(BetweenActivities.current_city,smartAndAutomationItem.getLocation());

                }else if(get_item_type.equals(DeviceType.WEATHER)){
                    //go to "ActivityWeather.java"
                    intent = new Intent(getApplicationContext(),ActivityWeather.class);
                    //intent.putExtra("item_type",get_item_type);//not need.
                    intent.putExtra(BetweenActivities.item_name,smartAndAutomationItem.getName());
                    intent.putExtra(BetweenActivities.current_city,smartAndAutomationItem.getLocation());

                }else if(get_item_type.equals(DeviceType.AIR_QUALITY)){
                    //go to "ActivityAirQuality.java"
                    intent = new Intent(getApplicationContext(),ActivityAirQuality.class);
                    //intent.putExtra("item_type",get_item_type);//not need.
                    intent.putExtra(BetweenActivities.item_name,smartAndAutomationItem.getName());
                    intent.putExtra(BetweenActivities.current_city,smartAndAutomationItem.getLocation());

                }else if(get_item_type.equals(DeviceType.SUNRISE_SUNSET)){
                    //go to "ActivitySunriseSunset.java"
                    intent = new Intent(getApplicationContext(),ActivitySunriseSunset.class);
                    //intent.putExtra("item_type",get_item_type);//not need.
                    intent.putExtra(BetweenActivities.item_name,smartAndAutomationItem.getName());
                    intent.putExtra(BetweenActivities.current_city,smartAndAutomationItem.getLocation());

                }else if(get_item_type.equals(DeviceType.SCHEDULE)){
                    //go to "ActivitySchedule.java"
                    intent = new Intent(getApplicationContext(),ActivitySchedule.class);
                    //intent.putExtra("item_type",get_item_type);//not need.
                    intent.putExtra(BetweenActivities.item_name,smartAndAutomationItem.getName());
                    //intent.putExtra(BetweenActivities.current_city,smartAndAutomationItem.getLocation());//not need

                }else if(get_item_type.equals(DeviceType.LAMP) || get_item_type.equals(DeviceType.WALL_PLUG)){
                    //go to "ActivityDevice.java"
                    //todo: go to "işlem seç" page:
                    intent = new Intent(getApplicationContext(),ActivitySwitch.class);
                    intent.putExtra(BetweenActivities.where_come_from,"ActivityAutomationSettings.java");
                    intent.putExtra(BetweenActivities.for_what,getIntent().getStringExtra(BetweenActivities.for_what));//we will need when we back here this class.
                    intent.putExtra(BetweenActivities.device_name,smartAndAutomationItem.getLocation());//Actions kısmında Lamp ve Wall_plug cihaz ismi "location" kısmına yazılır.Bu yüzden device_name olarak location gönderiyoruz.
                    startActivity(intent);

                }else{
                    // i don't know what can I do.
                }
                intent.putExtra(BetweenActivities.where_come_from,"ActivityAutomationSettings.java");
                intent.putExtra(BetweenActivities.position,position);
                intent.putExtra(BetweenActivities.for_what,"Edit_Condition");
                intent.putExtra(BetweenActivities.device_type,smartAndAutomationItem.getType());
                startActivity(intent);

            }
        });
        linearLayout_AddConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to add condition page:
                goToSelectConditionPage();
            }
        });
        imageView_Plus_AddActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to Select Actions page:
                goToSelectActionPage();
            }
        });
        linearLayout_AddActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to add Actions page:
                goToSelectActionPage();
            }
        });
        linearLayout_ValidTimePeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToValidTimePeriodPage();
            }
        });
        imageView_GoTo_ValidTimePeriodPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go t o Valid Time Period page:
                goToValidTimePeriodPage();
            }
        });
        textView_DeleteSmart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: Delete Smart. if Edit mod...
                //for scrollView position:@{
                 //keepPageCurrentScrollPosition();//we do not need.
                //for scrollView position:@}
            }
        });
        ///events:@}


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


    }//onCreate


    private void INIT_Apply(String for_what){
        //Start firstly:
        if(MyLog.DEGUB)MyLog.d("kerim_INIT_Apply","for_what:" + for_what);
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
        }else if(for_what.equals("Add_Condition")){
            apply_AddingCondition();
        }else if(for_what.equals("Edit_Condition")){
            apply_EditCondition();
        }else if(for_what.equals("Add_ValidTime")){
            apply_AddValidTime();
        }
    }
// #################################################################################################-end:@}//
    SmartAutomationSettingsConditionListContents smartAutomationSettingsConditionListContents =
        new SmartAutomationSettingsConditionListContents();//for "Conditions"
    SmartAndAutomationSettingsListContents smartAndAutomationSettingsListContents =
                                       new SmartAndAutomationSettingsListContents();//for "Actions"


    /**
     * Conditions Listview içeriğini yükler.
     */
    private void loadAllSmartConditions(){
         //SEX - conditions
        smartAutomationSettingsConditionListContents.fill();
        loadListViewConditionContent();
    }

    private void loadListViewConditionContent(){
        ArrayList<SmartAndAutomationItem> automationConditionsItems = smartAutomationSettingsConditionListContents.getItems();
        /*CustomListAdapter_AutomationSettings*/ customListAdapterAutomationConditionSettings = new CustomListAdapter_AutomationSettings(this,automationConditionsItems);
        listView_ConditionsList.setAdapter(customListAdapterAutomationConditionSettings);
        //resizeLayoutConditionsHeight(customListAdapterAutomationConditionSettings.getCount());
        gui.dynamicallySizingLayout(linearLayout_ConditionsBase,listView_ConditionsList,customListAdapterAutomationConditionSettings,customListAdapterAutomationConditionSettings.getCount(),90);

    }


    /**
     * Actions listView içeriğini yükler.
     */
    private void loadAllSmartActions(){
        //for test: new V:
        smartAndAutomationSettingsListContents.fill();
        loadRecyclerViewActionListContent(0);
    }
    private void loadRecyclerViewActionListContent(int _position) {
        //for test new V:
        ArrayList<SmartAndAutomationItem> smartAndAutomationItems = smartAndAutomationSettingsListContents.getItems();
        /*RecyclerViewAdapter_AutomationSettingsItem*/ recyclerViewAdapter =
        new RecyclerViewAdapter_AutomationSettingsItem(this,smartAndAutomationItems,"ActivityAutomationSettings.java");
        ItemTouchHelper.Callback callback = new ItemMoveCallback_SmartAndAutomationSettingsItem(recyclerViewAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView_ActionsList);
        recyclerView_ActionsList.setAdapter(recyclerViewAdapter);
        //FIXME:...
        if(recyclerViewAdapter.getItemCount() < 1)
            apply_AddState();
        //resizeLayoutActionsHeight(smartAndAutomationSettingsListContents.count()); ///or "recyclerViewAdapter.getItemCount()"
        gui.dynamicallySizingLayout(linearLayout_ActionsBase,
                recyclerView_ActionsList,recyclerViewAdapter,smartAndAutomationSettingsListContents.count(),90);
    }

//###################################################################################################
// #################################################################################################+Apply:@{
    private void apply_AddState(){
        //Conditions:
        listView_ConditionsList.setVisibility(View.GONE);//Conditions
        linearLayout_AddConditions.setVisibility(View.VISIBLE);//Conditions
        //Actions:
        recyclerView_ActionsList.setVisibility(View.GONE);//Actions
        linearLayout_AddActions.setVisibility(View.VISIBLE);//Actions
        textView_DeleteSmart.setVisibility(View.GONE);
    }
    private void apply_EditState(){
        //Conditions:
        linearLayout_AddConditions.setVisibility(View.GONE);//Conditions
        listView_ConditionsList.setVisibility(View.VISIBLE);//Conditions
        //Actions:
        linearLayout_AddActions.setVisibility(View.GONE);//Actions
        recyclerView_ActionsList.setVisibility(View.VISIBLE);//Actions
        textView_DeleteSmart.setVisibility(View.VISIBLE);
        loadAllSmartConditions();//Conditions
        loadAllSmartActions();//Actions
    }
    private void apply_EditSwitchState(){
        if(SMART_ID != -1)//so had this record before. So is  erasable
            textView_DeleteSmart.setVisibility(View.VISIBLE);
        linearLayout_AddConditions.setVisibility(View.GONE);//Conditions
        listView_ConditionsList.setVisibility(View.VISIBLE);//Conditions
        linearLayout_AddActions.setVisibility(View.GONE);//Actions
        recyclerView_ActionsList.setVisibility(View.VISIBLE);//Actions
        int position = getIntent().getIntExtra(BetweenActivities.position,-1);
        String selected_value = getIntent().getStringExtra(BetweenActivities.selected_value);
        if(selected_value != null)
            smartAndAutomationSettingsListContents.editState(position,selected_value);
        loadListViewConditionContent();//Conditions
        loadRecyclerViewActionListContent(position);//Actions
    }
    private void apply_EditTimeLapseState(){
        if(SMART_ID != -1)//so had this record before. So is  erasable
            textView_DeleteSmart.setVisibility(View.VISIBLE);

        linearLayout_AddConditions.setVisibility(View.GONE);//Conditions
        listView_ConditionsList.setVisibility(View.VISIBLE);//Conditions
        linearLayout_AddActions.setVisibility(View.GONE);//Actions
        recyclerView_ActionsList.setVisibility(View.VISIBLE);//Actions
        int position = getIntent().getIntExtra(BetweenActivities.position,-1);
        String selected_value = getIntent().getStringExtra(BetweenActivities.selected_value);
        if(selected_value != null)
            smartAndAutomationSettingsListContents.editState(position,selected_value);
        loadListViewConditionContent();//Conditions
        loadRecyclerViewActionListContent(position);//Actions
    }
    private void apply_AddingAction(){
        if(SMART_ID != -1)//so had this record before. So is  erasable
            textView_DeleteSmart.setVisibility(View.VISIBLE);
        linearLayout_AddConditions.setVisibility(View.GONE);//Conditions
        listView_ConditionsList.setVisibility(View.VISIBLE);//Conditions
        linearLayout_AddActions.setVisibility(View.GONE);    //Actions
        recyclerView_ActionsList.setVisibility(View.VISIBLE);//Actions
        int position           = getIntent().getIntExtra(BetweenActivities.position,-1);
        String device_name     = getIntent().getStringExtra(BetweenActivities.device_name);
        String selected_value  = getIntent().getStringExtra(BetweenActivities.selected_value);
        String device_type     = getIntent().getStringExtra(BetweenActivities.device_type);
        String device_state    = getIntent().getStringExtra(BetweenActivities.device_state);
        String device_location = getIntent().getStringExtra(BetweenActivities.device_location);
        if(selected_value != null) {
            SmartAndAutomationItem smartAndAutomationItem = new SmartAndAutomationItem();
            smartAndAutomationItem.setName(device_name);
            smartAndAutomationItem.setState(selected_value);
            smartAndAutomationItem.setLocation(device_location);
            smartAndAutomationItem.setType(device_type);
            smartAndAutomationSettingsListContents.setItem(smartAndAutomationItem);
        }
        loadListViewConditionContent();//Conditions
        loadRecyclerViewActionListContent(position);//Actions
        //resizeLayoutConditionsHeight(customListAdapterAutomationConditionSettings.getCount());
        gui.dynamicallySizingLayout(linearLayout_ConditionsBase,listView_ConditionsList,customListAdapterAutomationConditionSettings,customListAdapterAutomationConditionSettings.getCount(),90);
        //resizeLayoutActionsHeight(smartAndAutomationSettingsListContents.count());
        gui.dynamicallySizingLayout(linearLayout_ActionsBase,
                recyclerView_ActionsList,recyclerViewAdapter,smartAndAutomationSettingsListContents.count(),90);


    }

    private void apply_AddingActionTimeLapse(){
        if(SMART_ID != -1)//so had this record before. So is  erasable
            textView_DeleteSmart.setVisibility(View.VISIBLE);
        linearLayout_AddConditions.setVisibility(View.GONE);//Conditions
        listView_ConditionsList.setVisibility(View.VISIBLE);//Conditions
        linearLayout_AddActions.setVisibility(View.GONE);//Actions
        recyclerView_ActionsList.setVisibility(View.VISIBLE);//Actions
        //int position           = getIntent().getIntExtra("position",-1);
        String device_name     = "time lapse";//fixme:this value is unnecessary.Because assign.Because this value is assigned in the "RecyclerViewAdapter_AutomationSettingsItem.java" side.
        String selected_value  = getIntent().getStringExtra(BetweenActivities.selected_value);
        String device_type     = DeviceType.TIME_LAPSE;//getIntent().getStringExtra("device_type");
        if(selected_value != null) {
            SmartAndAutomationItem smartAndAutomationItem = new SmartAndAutomationItem();
            smartAndAutomationItem.setName(device_name);
            smartAndAutomationItem.setState(selected_value);
            smartAndAutomationItem.setType(device_type);
            smartAndAutomationItem.setLocation("-");//fixme: for IndexoutofException fix.
            smartAndAutomationSettingsListContents.setItem(smartAndAutomationItem);
        }
        loadListViewConditionContent();//Conditions
        loadRecyclerViewActionListContent(0);//Actions
    }

    private void apply_AddingAutomation(){
        /**
         * TODO: !!!!!!!!!!!ATTENTION!!!!!!!!!!!!!!!!!!!!!!
         *Eğer X otonom eklenmişse tekrar ekleme,eklemeyi engelle.
         */
        if(SMART_ID != -1)//so had this record before. So is  erasable
            textView_DeleteSmart.setVisibility(View.VISIBLE);
        linearLayout_AddConditions.setVisibility(View.GONE);//Conditions
        listView_ConditionsList.setVisibility(View.VISIBLE);//Conditions
        linearLayout_AddActions.setVisibility(View.GONE);//Actions
        recyclerView_ActionsList.setVisibility(View.VISIBLE);//Actions
        int position           = getIntent().getIntExtra(BetweenActivities.position,-1);
        String[] item_name     = getIntent().getStringArrayExtra(BetweenActivities.item_name);//array.
        String[] check_state   = getIntent().getStringArrayExtra(BetweenActivities.check_state);
        String device_type     = DeviceType.AUTOMATION;
        /**
         * fixme: daha performanslı halini geliştir. "ActivityAutomationSelect.java'dan buraya intent edilir."
         * benzer otonom itemlerini listeye tekrar tekrar eklememek için:
         */
        SmartAndAutomationItem smartAndAutomationItem;
        if(item_name !=null) {
            for (int i = 0; i < item_name.length; i++) {
                //MyLog.d("kerim","item:" + item_name[i] + ",check:" + check_state[i]);
                //for test: new V:
                if (smartAndAutomationSettingsListContents.isExists(item_name[i]) == false && check_state[i].equals("True")) {
                    smartAndAutomationItem = new SmartAndAutomationItem();
                    smartAndAutomationItem.setName(item_name[i]);
                    smartAndAutomationItem.setState(getString(R.string.enable));
                    smartAndAutomationItem.setType(device_type);
                    smartAndAutomationItem.setLocation(getString(R.string.automation));//otomasyon
                    smartAndAutomationSettingsListContents.setItem(smartAndAutomationItem);
                } else {
                    if (check_state[i].equals("False"))
                        smartAndAutomationSettingsListContents.remove(item_name[i]);
                }
            }
        }

        loadListViewConditionContent();//Conditions
        loadRecyclerViewActionListContent(position);//Actions
        //resizeLayoutConditionsHeight(customListAdapterAutomationConditionSettings.getCount());
        gui.dynamicallySizingLayout(linearLayout_ConditionsBase,listView_ConditionsList,customListAdapterAutomationConditionSettings,customListAdapterAutomationConditionSettings.getCount(),90);
        //resizeLayoutActionsHeight(smartAndAutomationSettingsListContents.count());
        gui.dynamicallySizingLayout(linearLayout_ActionsBase,
                recyclerView_ActionsList,recyclerViewAdapter,smartAndAutomationSettingsListContents.count(),90);
    }


    /**
     * for adding Condition
     */
    private void apply_AddingCondition(){

        if(SMART_ID != -1)//so had this record before. So is  erasable
            textView_DeleteSmart.setVisibility(View.VISIBLE);
        linearLayout_AddConditions.setVisibility(View.GONE);//Conditions
        listView_ConditionsList.setVisibility(View.VISIBLE);//Conditions
        linearLayout_AddActions.setVisibility(View.GONE);//Actions
        recyclerView_ActionsList.setVisibility(View.VISIBLE);//Actions

        /**
         *  getIntent:
         *  -ActivityTemperature.java
         *  -
         */
        int position          = getIntent().getIntExtra(BetweenActivities.position,-1);
        String item_name      = getIntent().getStringExtra(BetweenActivities.item_name);
        String selected_value = getIntent().getStringExtra(BetweenActivities.selected_value);
        String current_city   = getIntent().getStringExtra(BetweenActivities.current_city);
        String device_type    = getIntent().getStringExtra(BetweenActivities.device_type);
         if(selected_value != null) {
            SmartAndAutomationItem smartAndAutomationItem = new SmartAndAutomationItem();

             if(device_type.equals(DeviceType.SCHEDULE)) {//for test: 17Ekm_10:14.@{
                 MonthsAndDays monthsAndDays = new MonthsAndDays(getApplicationContext());
                 smartAndAutomationItem.setName(monthsAndDays.shortenDayNames(item_name) + " " + selected_value);//Boşluk bırak.Veri parçalamada kolaylık olması için.
             }else if(device_type.equals(DeviceType.LAMP) || device_type.equals(DeviceType.WALL_PLUG))
                 smartAndAutomationItem.setName(selected_value);
             else//for test: 17Ekm_10:14:@}
                 smartAndAutomationItem.setName(item_name + ":" + selected_value);// ":" karakteri koy. örn: Hava:Kirli

            //smartAndAutomationItem.setState("selected_value");//do not use for Conditions. Because we no need.
            smartAndAutomationItem.setLocation(current_city);
            smartAndAutomationItem.setType(device_type);
            smartAutomationSettingsConditionListContents.setItem(smartAndAutomationItem);
        }
        loadListViewConditionContent();//Conditions
        loadRecyclerViewActionListContent(position);//Actions
        //resizeLayoutConditionsHeight(customListAdapterAutomationConditionSettings.getCount());
        gui.dynamicallySizingLayout(linearLayout_ConditionsBase,listView_ConditionsList,customListAdapterAutomationConditionSettings,customListAdapterAutomationConditionSettings.getCount(),90);
       // resizeLayoutActionsHeight(smartAndAutomationSettingsListContents.count());
        gui.dynamicallySizingLayout(linearLayout_ActionsBase,
                recyclerView_ActionsList,recyclerViewAdapter,smartAndAutomationSettingsListContents.count(),90);
    }
    private void apply_EditCondition(){
        if(SMART_ID != -1)//so had this record before. So is  erasable
            textView_DeleteSmart.setVisibility(View.VISIBLE);

       linearLayout_AddConditions.setVisibility(View.GONE);//Conditions
        listView_ConditionsList.setVisibility(View.VISIBLE);//Conditions
        linearLayout_AddActions.setVisibility(View.GONE);//Actions
        recyclerView_ActionsList.setVisibility(View.VISIBLE);//Actions
        int position = getIntent().getIntExtra(BetweenActivities.position,-1);
        String item_name      = getIntent().getStringExtra(BetweenActivities.item_name);
        String selected_value = getIntent().getStringExtra(BetweenActivities.selected_value);
        String current_city   = getIntent().getStringExtra(BetweenActivities.current_city);
        String device_type    = getIntent().getStringExtra(BetweenActivities.device_type);

        if(selected_value != null && position != -1) {
            SmartAndAutomationItem smartAndAutomationItem = smartAutomationSettingsConditionListContents.getItem(position);
            if(device_type.equals(DeviceType.SCHEDULE)) {//for test: 17Ekm_10:14.@{
                MonthsAndDays monthsAndDays = new MonthsAndDays(getApplicationContext());
                smartAndAutomationItem.setName(monthsAndDays.shortenDayNames(item_name) + " " + selected_value);//Boşluk bırak.Veri parçalamada kolaylık olması için.
            }else if(device_type.equals(DeviceType.LAMP) || device_type.equals(DeviceType.WALL_PLUG))
                smartAndAutomationItem.setName(selected_value);
            else//for test: 17Ekm_10:14:@}
                smartAndAutomationItem.setName(item_name + ":" + selected_value);// ":" karakteri koy. örn: Hava:Kirli

            //smartAndAutomationItem.setState("selected_value");//do not use for Conditions. Because we no need.
            smartAndAutomationItem.setLocation(current_city);
            smartAndAutomationItem.setType(device_type);
            smartAutomationSettingsConditionListContents.editItem(position, smartAndAutomationItem);
        }else{
           if(MyLog.DEGUB) MyLog.d("kerim[ActivityAutomationSettings]","Exception selected_value:"+
                    selected_value +",position:"+position +" is null maybe...");
        }
        loadListViewConditionContent();//Conditions
        loadRecyclerViewActionListContent(position);//Actions
    }

    private void apply_AddValidTime(){
        if(SMART_ID != -1)//so had this record before. So is  erasable
            textView_DeleteSmart.setVisibility(View.VISIBLE);
        /**
         * todo:
         * 1= if "listView_ConditionsList" count > 0, pleaes VISIBLE "listView_ConditionsList",else  INVISIBLE.
         * 2= if "recyclerView_ActionsList" count > 0,please VISIBLE "recyclerView_ActionsList", else  INVISIBLE.
         */
        ///@{
        linearLayout_AddConditions.setVisibility(View.GONE);//Conditions
        listView_ConditionsList.setVisibility(View.VISIBLE);//Conditions
        linearLayout_AddActions.setVisibility(View.GONE);//Actions
        recyclerView_ActionsList.setVisibility(View.VISIBLE);//Actions
        ///@}

         String valid_time = getIntent().getStringExtra("valid_time");
         if(valid_time !=null)
             textView_ValidTime.setText(valid_time);

         //todo: bu değeri Otomasyonla birlikte kaydetmek için sakla.:
         String repeatcontent = getIntent().getStringExtra(BetweenActivities.repeat_content);
         if(repeatcontent !=null)
             this.REPEAT_CONTENT = repeatcontent; //get from "ActivityValidTimePeriod.java" for save database with this Auotomation item.

        loadListViewConditionContent();//Conditions
        loadRecyclerViewActionListContent(-1);//Actions
    }
// #################################################################################################-apply:@}
    /**
     * "linearLayout_ActionsBase" grows as the "recyclerView_ActionsList" content grows
     */
   /** //!!!!!!!!!!!! REMOVE THIS BLOCK!!!!!!!!!!!!!!!!!!!

   private void resizeLayoutConditionsHeight(int count){


        ///for Layout Resize:@{
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) linearLayout_ConditionsBase.getLayoutParams();
        int total_height = 90;//ATTENTION:do not touch this value. default = 90
        int item_count = count;
        View item;
        for(int i=0; i<item_count;i++){
            item = customListAdapterAutomationConditionSettings.getView(i,null,listView_ConditionsList);
            item.measure(0,0);
            total_height += item.getMeasuredHeight();
        }
        layoutParams.height = total_height + (1 * (item_count));
        linearLayout_ConditionsBase.setLayoutParams(layoutParams);
        linearLayout_ConditionsBase.requestLayout();
        layoutParams = (ConstraintLayout.LayoutParams) linearLayout_ConditionsBase.getLayoutParams();
        ///for Layout Resize:@}
        //
        ///for Listview resize:@{
        ViewGroup.LayoutParams params2 = listView_ConditionsList.getLayoutParams();
        params2.height = layoutParams.height;
        listView_ConditionsList.setLayoutParams(params2);
        listView_ConditionsList.requestLayout();
        ////for Listview resize:@}
    }*/
    /**
     * "linearLayout_ActionsBase" grows as the "recyclerView_ActionsList" content grows
     */
   /*
   *!!!!!!!!!!!! REMOVE THIS BLOCK!!!!!!!!!!!!!!!!!!!
   private void resizeLayoutActionsHeight(int count){

        ///for Layout Resize:@{
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) linearLayout_ActionsBase.getLayoutParams();
        int total_height = 90;//ATTENTION:do not touch this value. default = 90
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

private void saveAllAutomationSettings(){
    if(recyclerViewAdapter.getItemCount() < 1 ){
        MyLog.d("kerim,ActivityAutomationSettings","Böyle kayıt yapaman lo");
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

    private void goToSelectConditionPage(){
        //todo: go to add condition page:
        //MyLog.d("kerim","go to Add Condition Page");
        //for scrollView position:@{
        keepPageCurrentScrollPosition();
        //for scrollView position:@}
        //"Koşul Seç"
        Intent intent = new Intent(getApplicationContext(),ActivityAutomationSelectCondition.class);
        intent.putExtra(BetweenActivities.for_what,"Add_Condition");
        startActivity(intent);
    }
    private void goToSelectActionPage(){
        //for scrollView position:@{
        keepPageCurrentScrollPosition();
        //for scrollView position:@}
        Intent intent = new Intent(getApplicationContext(),ActivitySmartSelectAction.class);
        intent.putExtra(BetweenActivities.where_come_from,"ActivityAutomationSettings.java");
        intent.putExtra(BetweenActivities.for_what,"Add_Action");//we will need when we back here this class.
        startActivity(intent);
    }
    private void goToValidTimePeriodPage(){
        //for scrollView position:@{
        keepPageCurrentScrollPosition();
        //for scrollView position:@}
        //todo: go t o Valid Time Period page:
        if(MyLog.DEGUB)MyLog.d("kerim","Valid Time Perioud");
        Intent intent = new Intent(getApplicationContext(),ActivityValidTimePeriod.class);
        //intent.putExtra(BetweenActivities.for_what,"Edit");//fixme: todo: uygun parametreyi belirle.
        intent.putExtra("valid_time",textView_ValidTime.getText());
        intent.putExtra(BetweenActivities.repeat_content,this.REPEAT_CONTENT);
        startActivity(intent);
    }

    /**
     * it is called from this class and the "RecyclerViewAdapter_AutomationSettingsItem.java"
     */
    public static void keepPageCurrentScrollPosition(){
        //MyLog.d("kerim[currentScrollPosition]","get:" +scrollView_Based.getScrollY());
        Static.CURRENT_SCROLL_POSITION = scrollView_Based.getScrollY();
    }
    private int getPageCurrentScrollPosition(){
        return Static.CURRENT_SCROLL_POSITION;
    }
    private void resetPageCurrentScrollPosition(){
        Static.CURRENT_SCROLL_POSITION = 0;//reset. we no longer need retained value.
    }

    private void goToBack(){
        resetPageCurrentScrollPosition(); //reset page current scroll position.
        Intent intent = new Intent(getApplicationContext(),ActivityMainPage.class);
        intent.putExtra(BetweenActivities.where_come_from,"ActivityAutomationSettings.java:SMART");
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
