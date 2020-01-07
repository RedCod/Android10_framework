package com.example.trident.smart;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.Database;
import com.example.trident.common.Lamp;
import com.example.trident.common.MyLog;

public class ActivityLampControl extends AppCompatActivity {
    /** "Lamba Kontrol"
     * this class calling from the "Fragment_FragmentHome_RoomDevices.java"
     *
     */

    /**TODO:
     * todo:
     */
    private static String TAG = "[ActivityLampControl]";
    private static String DEVICE_NAME = "";
    private int COLOR = 0;
    BottomNavigationView bottom_LampNavigation;

    private ColorPickerView colorPickerView;
    ImageView imageView_LampColor;
    ImageView imageView_Lamp;
///PANO:@{
    //LinearLayout linearLayout_Pano;
    LinearLayout linearLayout_White;
    LinearLayout linearLayout_Colour;
    LinearLayout linearLayout_Scene;

    //white:@{
    SeekBar seekBar_BrightOfWhite;
    TextView textView_BrightPercentOfWhite;
    SeekBar seekBar_ColourTempOfWhite;
    TextView textVie_ColourTempPercentOfWhite;
    //white:@}
    //colour:@{
    SeekBar seekBar_SaturationOfColour;
    TextView textView_SaturationPercentOfColour;
    SeekBar seekBar_BrightOfColour;
    TextView textView_BrightPercentOfColour;
    //colour:}

    ///>scene->edit panels>imageView_SceneEdit_ShowBSS -> show this layout:@{//For set their("Scene1,Scene2,Scene3,Scene4") saturation,bright,speed.
    LinearLayout linearLayout_SettingsForSceneXXXX;
    SeekBar seekBar_SaturationOfSettingsForSceneXXXX;
    TextView textView_SaturationPercentOfSettingsForSceneXXXX;
    SeekBar seekBar_BrightOfSettingsForSceneXXXX;
    TextView textView_BrightPercentOfSettingsForSceneXXXX;
    SeekBar seekBar_SpeedOfSettingsForSceneXXXX;
    TextView textView_SpeedPercentOfSettingsForSceneXXXX;
    ///>scene->edit panels>imageView_SceneEdit_ShowBSS -> show this layout:@}

 ///PANO:@}

    ///Scene:@{
    ImageView imageView_SceneEdit_ShowBSS;
    ImageView imageView_SceneEdit_ShowC;
    ///Scene:@}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp_control);
//
        Intent intent = getIntent();
        String device_name = intent.getStringExtra("device_name");
        //String this_is_a_group = intent.getStringExtra("group_of_device");
        if(device_name != null)
            DEVICE_NAME = device_name;

        //if(DEBUG)MyLog.d(TAG,"kertens:device_name:" + DEVICE_NAME);
        Database database = new Database();
        database.loadLampSettingsForLampControl(DEVICE_NAME);
        //
        LinearLayout linearLayout_Back    = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                = (Button)findViewById(R.id.button_Back);
        final TextView textView_DeviceName      = (TextView)findViewById(R.id.textView_DeviceName);
        Button button_GoToDetailsOfDevice  = (Button)findViewById(R.id.button_GoToDetailsOfDevice);


        ///BottomNavigationView:@{
        /*BottomNavigationView*/ bottom_LampNavigation = (BottomNavigationView) findViewById(R.id.bottom_LampNavigation);
        bottom_LampNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //bottom_LampNavigation.getMenu().getItem(0).setVisible(false);
        //bottom_LampNavigation.findViewById(R.id.navigation_Item_IDLE).setVisibility(View.GONE);//We will use if necessary.Because we don't want the first menu to be selected as default.
        //bottom_LampNavigation.invalidate();
        ///BottomNavigationView:@}

        //step 2:
        colorPickerView                    = (ColorPickerView) findViewById(R.id.colorPickerView);
        //resultColorView                  = findViewById(R.id.resultColorView);//todo:disabled from xml side
        /**ImageView*/ imageView_LampColor = (ImageView)findViewById(R.id.imageView_LampColor);
        /*ImageView*/ imageView_Lamp       = (ImageView)findViewById(R.id.imageView_Lamp);
        //COLOR = getResources().getColor(R.color.color_violet1);
        loadColorPickerViewListeners();


        textView_DeviceName.setText(DEVICE_NAME);

//

///PANO:@{
        //linearLayout_Pano:
        //linearLayout_Pano = (LinearLayout)findViewById(R.id.linearLayout_Pano);
        linearLayout_White                              = (LinearLayout)findViewById(R.id.linearLayout_White);
        //seekBar_BrightOfWhite:
         seekBar_BrightOfWhite                   = (SeekBar)findViewById(R.id.seekBar_BrightOfWhite);
        //textView_BrightPercentOfWhite:
        textView_BrightPercentOfWhite    = (TextView)findViewById(R.id.textView_BrightPercentOfWhite);
        //seekBar_ColourTempOfWhite:
        seekBar_ColourTempOfWhite               = (SeekBar)findViewById(R.id.seekBar_ColourTempOfWhite);
        //textVie_ColourTempPercentOfWhite:
        textVie_ColourTempPercentOfWhite = (TextView)findViewById(R.id.textVie_ColourTempPercentOfWhite);
        //
        //linearLayout_Colour:
        linearLayout_Colour                        = (LinearLayout)findViewById(R.id.linearLayout_Colour);
        //seekBar_SaturationOfColour:
        seekBar_SaturationOfColour             = (SeekBar)findViewById(R.id.seekBar_SaturationOfColour);
        //textView_SaturationPercentOfColour:
        textView_SaturationPercentOfColour    = (TextView)findViewById(R.id.textView_SaturationPercentOfColour);
        //seekBar_BrightOfColour:
        seekBar_BrightOfColour         = (SeekBar)findViewById(R.id.seekBar_BrightOfColour);
        //textView_BrightPercentOfColour:
        textView_BrightPercentOfColour = (TextView)findViewById(R.id.textView_BrightPercentOfColour);

        seekBar_BrightOfWhite.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {//SEEKBAR BRIGHT:
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView_BrightPercentOfWhite.setText(i +"%");
                setBrightnessToImageView_LampColor(i);
                Lamp.setBrightLevelOfWhite(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //do nothing.
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int last_progress_value = seekBar.getProgress();
                /**TODO: send last seekBar value to the MQTT server and database.
                 * todo: send last value to the MQTT -> client(lamp).
                 * todo: get this value from the MQTT broker side,and then update into database.
                 */

            }
        });
        seekBar_ColourTempOfWhite.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {//SEEKBAR COLOUR TEMP:
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textVie_ColourTempPercentOfWhite.setText(i+"%");
                //setBlackAndWhite(imageView_Lamp,i);//SEX
                setColourTempToImageView_LampColor(i);
                Lamp.setColourTempLevelOfWhite(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //do nothing.
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int last_progress_value = seekBar.getProgress();
                /**TODO: send last seekBar value to the MQTT server and database.
                 * todo: send last value to the MQTT -> client(lamp).
                 * todo: get this value from the MQTT broker side,and then update into database.
                 */
            }
        });


        seekBar_SaturationOfColour.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView_SaturationPercentOfColour.setText(i+"%");
                setSaturationToImageView_LampColor(i);
                Lamp.setSaturationLevelOfColour(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int last_progress_value = seekBar.getProgress();
                /**TODO: send last seekBar value to the MQTT server and database.
                 * todo: send last value to the MQTT -> client(lamp).
                 * todo: get this value from the MQTT broker side,and then update into database.
                 */
            }
        });
        seekBar_BrightOfColour.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView_BrightPercentOfColour.setText(i+"%");
                setBrightnessToImageView_LampColor(i);
                Lamp.setBrightLevelOfColour(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int last_progress_value = seekBar.getProgress();
                /**TODO: send last seekBar value to the MQTT server and database.
                 * todo: send last value to the MQTT -> client(lamp).
                 * todo: get this value from the MQTT broker side,and then update into database.
                 */
            }
        });


        //>SCENE:@{

        linearLayout_Scene = (LinearLayout)findViewById(R.id.linearLayout_Scene);

        ///>scene>edit panels:@{
            imageView_SceneEdit_ShowBSS = (ImageView)findViewById(R.id.imageView_SceneEdit_ShowBSS);
            imageView_SceneEdit_ShowC  = (ImageView)findViewById(R.id.imageView_SceneEdit_ShowC);
        ///>scene>edit panels:@}
        //////////////////////////------------------------------------------------------------------
        ///>scene->edit panels>imageView_SceneEdit_ShowBSS -> show this layout:@{
        linearLayout_SettingsForSceneXXXX = (LinearLayout)findViewById(R.id.linearLayout_SettingsForSceneXXXX);
        seekBar_SaturationOfSettingsForSceneXXXX = (SeekBar)findViewById(R.id.seekBar_SaturationOfSettingsForSceneXXXX);
        textView_SaturationPercentOfSettingsForSceneXXXX = (TextView)findViewById(R.id.textView_SaturationPercentOfSettingsForSceneXXXX);
        seekBar_BrightOfSettingsForSceneXXXX = (SeekBar)findViewById(R.id.seekBar_BrightOfSettingsForSceneXXXX);
        textView_BrightPercentOfSettingsForSceneXXXX = (TextView)findViewById(R.id.textView_BrightPercentOfSettingsForSceneXXXX);
        seekBar_SpeedOfSettingsForSceneXXXX = (SeekBar)findViewById(R.id.seekBar_SpeedOfSettingsForSceneXXXX);
        textView_SpeedPercentOfSettingsForSceneXXXX = (TextView)findViewById(R.id.textView_SpeedPercentOfSettingsForSceneXXXX);
        ///>scene->edit panels>imageView_SceneEdit_ShowBSS -> show this layout:@}


        //linearLayout_SceneNight:
        LinearLayout linearLayout_SceneNight   = (LinearLayout)findViewById(R.id.linearLayout_SceneNight);

        //linearLayout_SceneRead:
        LinearLayout linearLayout_SceneRead    = (LinearLayout)findViewById(R.id.linearLayout_SceneRead);

        //linearLayout_SceneMeeting:
        LinearLayout linearLayout_SceneMeeting = (LinearLayout)findViewById(R.id.linearLayout_SceneMeeting);

        //linearLayout_SceneLeisure:
        LinearLayout linearLayout_SceneLeisure = (LinearLayout)findViewById(R.id.linearLayout_SceneLeisure);

        //linearLayout_SceneScene1:
        LinearLayout linearLayout_SceneScene1  = (LinearLayout)findViewById(R.id.linearLayout_SceneScene1);

        //linearLayout_SceneScene2:
        LinearLayout linearLayout_SceneScene2  = (LinearLayout)findViewById(R.id.linearLayout_SceneScene2);

        //linearLayout_SceneScene3:
        LinearLayout linearLayout_SceneScene3  = (LinearLayout)findViewById(R.id.linearLayout_SceneScene3);

        //linearLayout_SceneScene4:
        LinearLayout linearLayout_SceneScene4  = (LinearLayout)findViewById(R.id.linearLayout_SceneScene4);

        //textView_SceneNight:
        final TextView textView_SceneNight   = (TextView)findViewById(R.id.textView_SceneNight);
        //textView_SceneRead:
        final TextView textView_SceneRead    = (TextView)findViewById(R.id.textView_SceneRead);
        //textView_SceneMeeting:
        final TextView textView_SceneMeeting = (TextView)findViewById(R.id.textView_SceneMeeting);
        //textView_SceneLeisure:
        final TextView textView_SceneLeisure = (TextView)findViewById(R.id.textView_SceneLeisure);
        //textView_SceneScene1:
        final TextView textView_SceneScene1  = (TextView)findViewById(R.id.textView_SceneScene1);
        //textView_SceneScene2:
        final TextView textView_SceneScene2  = (TextView)findViewById(R.id.textView_SceneScene2);
        //textView_SceneScene3:
        final TextView textView_SceneScene3  = (TextView)findViewById(R.id.textView_SceneScene3);
        //textView_SceneScene4:
        final TextView textView_SceneScene4  = (TextView)findViewById(R.id.textView_SceneScene4);


          final TextView[] textViewsOfScenes = {
                                                textView_SceneNight,
                                                textView_SceneRead,
                                                textView_SceneMeeting,
                                                textView_SceneLeisure,
                                                textView_SceneScene1,
                                                textView_SceneScene2,
                                                textView_SceneScene3,
                                                textView_SceneScene4
         };

        //>>event:@{
        imageView_SceneEdit_ShowBSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVisibility__linearLayout_SettingsForSceneXXXX_ForBSS(View.VISIBLE);
            }
        });
        imageView_SceneEdit_ShowC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: show  colorpickerview,for adjust colour of Scene1,2,..
                setVisibility__linearLayout_Scene_ForShowC(View.GONE);
               /* bottom_LampNavigation.getMenu().getItem(0).setVisible(true);//navigation_Item_PanoClosingArrow
                //hide other Items:
                bottom_LampNavigation.getMenu().getItem(1).setVisible(false);//navigation_Item_White
                bottom_LampNavigation.getMenu().getItem(2).setVisible(false);//navigation_Item_Colour
                bottom_LampNavigation.getMenu().getItem(3).setVisible(false);//navigation_Item_Scene
                bottom_LampNavigation.getMenu().getItem(4).setVisible(false);//navigation_Item_Schedule*/
            }
        });

        linearLayout_SceneNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyGrayColorToSceneTextViews(textViewsOfScenes);//apply gray to all.
                applyWhiteColorToSceneTextView(textView_SceneNight);
                setVisibilitySceneEditControls(View.GONE);
            }
        });
        linearLayout_SceneRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyGrayColorToSceneTextViews(textViewsOfScenes);//apply gray to all.
                applyWhiteColorToSceneTextView(textView_SceneRead);
                setVisibilitySceneEditControls(View.GONE);
            }
        });
        linearLayout_SceneMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyGrayColorToSceneTextViews(textViewsOfScenes);//apply gray to all.
                applyWhiteColorToSceneTextView(textView_SceneMeeting);
                setVisibilitySceneEditControls(View.GONE);
            }
        });
        linearLayout_SceneLeisure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyGrayColorToSceneTextViews(textViewsOfScenes);//apply gray to all.
                applyWhiteColorToSceneTextView(textView_SceneLeisure);
                setVisibilitySceneEditControls(View.GONE);
            }
        });
        //Scene 1,2,3,4:
        linearLayout_SceneScene1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyGrayColorToSceneTextViews(textViewsOfScenes);//apply gray to all.
                applyWhiteColorToSceneTextView(textView_SceneScene1);
                setVisibilitySceneEditControls(View.VISIBLE);
            }
        });
        linearLayout_SceneScene2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyGrayColorToSceneTextViews(textViewsOfScenes);//apply gray to all.
                applyWhiteColorToSceneTextView(textView_SceneScene2);
                setVisibilitySceneEditControls(View.VISIBLE);
            }
        });
        linearLayout_SceneScene3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyGrayColorToSceneTextViews(textViewsOfScenes);//apply gray to all.
                applyWhiteColorToSceneTextView(textView_SceneScene3);
                setVisibilitySceneEditControls(View.VISIBLE);
            }
        });
        linearLayout_SceneScene4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyGrayColorToSceneTextViews(textViewsOfScenes);//apply gray to all.
                applyWhiteColorToSceneTextView(textView_SceneScene4);
                setVisibilitySceneEditControls(View.VISIBLE);
            }
        });


        ///>scene->edit panels>imageView_SceneEdit_ShowBSS -> show this layout:@{  //linearLayout_SettingsForSceneXXXX
        seekBar_SaturationOfSettingsForSceneXXXX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView_SaturationPercentOfSettingsForSceneXXXX.setText(i+"%");
                setSaturationToImageView_LampColor(i);
                Lamp.setSaturationLevelOfColour(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int last_progress_value = seekBar.getProgress();
                /**TODO: send last seekBar value to the MQTT server and database.
                 * todo: send last value to the MQTT -> client(lamp).
                 * todo: get this value from the MQTT broker side,and then update into database.
                 */
            }
        });

        seekBar_BrightOfSettingsForSceneXXXX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView_BrightPercentOfSettingsForSceneXXXX.setText(i+"%");
                setBrightnessToImageView_LampColor(i);
                Lamp.setBrightLevelOfColour(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                /**TODO: send last seekBar value to the MQTT server and database.
                 * todo: send last value to the MQTT -> client(lamp).
                 * todo: get this value from the MQTT broker side,and then update into database.
                 */
            }
        });

        seekBar_SpeedOfSettingsForSceneXXXX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView_SpeedPercentOfSettingsForSceneXXXX.setText(i+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                /**TODO: send last seekBar value to the MQTT server and database.
                 * todo: send last value to the MQTT -> client(lamp).
                 * todo: get this value from the MQTT broker side,and then update into database.
                 */
            }
        });
        ///>scene->edit panels>imageView_SceneEdit_ShowBSS -> show this layout:@}
        //>>event:@}

        /**
         * TODO: Select the last selected scene by default.
         * -todo: retrieve the most recently selected scene by user from the database
         */
        //todo:selected by default. Exmp:
         linearLayout_SceneRead.callOnClick();

        //>SCENE:@}

///PANO:@}



///BACK:@{
        linearLayout_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to back:
                goToBack();
            }
        });
        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to back:
                goToBack();
            }
        });
///back:@}
        button_GoToDetailsOfDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean is_group_member = false;//"false" = this device is not a group member: //todo:this value get from database.
                String this_is_a_group = getIntent().getStringExtra("group_of_device");//todo: OR using this <<<<<<<<<<<<<<<
                if(!is_group_member){ //false
                    //todo:if this device is not a "group" member,go to "ActivityDetailsOfDevice.java":
                    //todo: go to "Detail Of Device" page:
                    Intent intent = new Intent(getApplicationContext(),ActivityDetailsOfDevice.class);
                    intent.putExtra("device_name",textView_DeviceName.getText());
                    startActivity(intent);
                }else{
                    //todo:else if this device is a "group" member,go to "ActivityDetailsOfDeviceGroup.java":
                    Intent intent = new Intent(getApplicationContext(),ActivityDetailsOfDeviceGroup.class);
                    intent.putExtra("device_name",textView_DeviceName.getText());
                    startActivity(intent);
                }
            }
        });

        imageView_Lamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLampONorOFF();
            }
        });

        //TODO:///////////////////////////////////////////////////:@{
        /**TODO: step
         * -Get Lamp Control page settings value from SERVER-DATABASE.
         */


        /////TODO:@{
        //+Get device state ON or OFF.
        //+if Device is OFF, InVisible "bottom_LampNavigation" items,except "Schedule" item. And InVisible "colorPickerView" and "resultColorView" color palet from the screen.
        //+if Device is ON, Visible "bottom_LampNavigation" items.And Visible "colorPickerView" and "resultColorView" color palet from the screen.
        //+

        /////todo:@}

        ///:@{//fixme:edit tis block.

        colorPickerView.setEnabled(false);
        colorPickerView.setVisibilityColorPalete(View.INVISIBLE);//hide "Colour palet".
        if(Lamp.getONOFF().equals("ON"))
            turnLampON();
        else
            turnLampOFF();

//TODO:///////////////////////////////////////////////////:@}
    }//onCreate

    private void loadColorPickerViewListeners() {
        colorPickerView.setColorListener(new ColorPickerView.ColorListener() {
            @Override
            public void onColorSelected(int color) {
                //resultColorView.setBackgroundColor(color);
                if(MyLog.DEGUB)MyLog.d(TAG,"kertens:color:" + color);
                COLOR = color;
                if(Lamp.isON())
                    changeLampColorFromPallette(color);
                else
                    changeLampColorFromPallette(getResources().getColor(R.color.color_violet1));
            }
        });
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //bottom_LampNavigation.findViewById(R.id.navigation_Item_IDLE).setVisibility(View.GONE);//We will use if necessary.Because we don't want the first menu to be selected as default.
            // bottom_LampNavigation.invalidate();

            switch (item.getItemId()) {
                case R.id.navigation_Item_PanoClosingArrow://Close "White,Colour,Scene" Pano layout.
                    if(isInVisibility__linearLayout_Scene_ForShowC())
                        setVisibility__linearLayout_Scene_ForShowC(View.VISIBLE);//linearLayout_Scene.setVisibility(View.VISIBLE);
                      else if(isVisibility__linearLayout_SettingsForSceneXXXX_ForBSS())
                         setVisibility__linearLayout_SettingsForSceneXXXX_ForBSS(View.GONE);
                     else
                         panoClosingArrowClose();

                    return true;
                case R.id.navigation_Item_White:
                    //menu.findItem(R.id.navigation_Item_White).setIcon(R.drawable.circled);//changed icon
                    //TODO: IF IS LAMP ON,Show "white" plate(Bright,Colour Temp) bar.

                    colorPickerView.setEnabled(false);
                    colorPickerView.setVisibilityColorPalete(View.INVISIBLE);//hide "Colour palet".
                    linearLayout_White.setVisibility(View.VISIBLE);
                    //White:@{
                    seekBar_BrightOfWhite.setProgress(Lamp.getBrightLevelOfWhite());
                    seekBar_ColourTempOfWhite.setProgress(Lamp.getColourTempLevelOfWhite());
                    textView_BrightPercentOfWhite.setText(Lamp.getBrightLevelOfWhite()+"%");
                    textVie_ColourTempPercentOfWhite.setText(Lamp.getColourTempLevelOfWhite() +"%");
                    //TODO: Apply color to white light.
                    //todo:---:
                    setSaturationToImageView_LampColor(100);
                    setBrightnessToImageView_LampColor(seekBar_BrightOfWhite.getProgress());
                    setColourTempToImageView_LampColor(Lamp.getColourTempLevelOfWhite());
                    //white:@}
                    bottom_LampNavigation.getMenu().getItem(0).setVisible(true);//navigation_Item_PanoClosingArrow
                    //hide other Items:
                    bottom_LampNavigation.getMenu().getItem(1).setVisible(false);//navigation_Item_White
                    bottom_LampNavigation.getMenu().getItem(2).setVisible(false);//navigation_Item_Colour
                    bottom_LampNavigation.getMenu().getItem(3).setVisible(false);//navigation_Item_Scene
                    bottom_LampNavigation.getMenu().getItem(4).setVisible(false);//navigation_Item_Schedule
                    return true;
                case R.id.navigation_Item_Colour:
                    //menu.findItem(R.id.navigation_Item_Colour).setIcon(R.drawable.color_plate3);//changed icon
                    //TODO: IF IS LAMP ON,Show "Colour palate" for RGB led lamp.

                    colorPickerView.setVisibilityColorPalete(View.VISIBLE);//show Colour palet only "Colour" ITEM.
                    colorPickerView.setEnabled(true);
                    linearLayout_Colour.setVisibility(View.VISIBLE);
                    //Colour:@{
                    seekBar_SaturationOfColour.setProgress(Lamp.getSaturationLevelOfColour());
                    seekBar_BrightOfColour.setProgress(Lamp.getBrightLevelOfColour());
                    textView_SaturationPercentOfColour.setText(Lamp.getSaturationLevelOfColour() +"");
                    textView_BrightPercentOfColour.setText(Lamp.getBrightLevelOfColour() +"");
                    setSaturationToImageView_LampColor(seekBar_SaturationOfColour.getProgress());
                    setBrightnessToImageView_LampColor(seekBar_BrightOfColour.getProgress());
                    //colour:@}
                    bottom_LampNavigation.getMenu().getItem(0).setVisible(true);//navigation_Item_PanoClosingArrow
                    //hide other Items:
                    bottom_LampNavigation.getMenu().getItem(1).setVisible(false);//navigation_Item_White
                    bottom_LampNavigation.getMenu().getItem(2).setVisible(false);//navigation_Item_Colour
                    bottom_LampNavigation.getMenu().getItem(3).setVisible(false);//navigation_Item_Scene
                    bottom_LampNavigation.getMenu().getItem(4).setVisible(false);//navigation_Item_Schedule
                    return true;
                case R.id.navigation_Item_Scene:
                    //menu.findItem(R.id.navigation_Item_Scene).setIcon(R.drawable.star);//changed icon
                    //TODO: IF IS LAMP ON,Show "Scene plate"(available colour temp).
                    colorPickerView.setEnabled(false);
                    colorPickerView.setVisibilityColorPalete(View.INVISIBLE);//hide "Colour palet".
                    linearLayout_Scene.setVisibility(View.VISIBLE);
                    bottom_LampNavigation.getMenu().getItem(0).setVisible(true);//navigation_Item_PanoClosingArrow
                    //hide other Items:
                    bottom_LampNavigation.getMenu().getItem(1).setVisible(false);//navigation_Item_White
                    bottom_LampNavigation.getMenu().getItem(2).setVisible(false);//navigation_Item_Colour
                    bottom_LampNavigation.getMenu().getItem(3).setVisible(false);//navigation_Item_Scene
                    bottom_LampNavigation.getMenu().getItem(4).setVisible(false);//navigation_Item_Schedule
                    return true;
                case R.id.navigation_Item_Schedule:
                    //menu.findItem(R.id.navigation_Item_Schedule).setIcon(R.drawable.schedule);//changed icon
                    //TODO: go to "Add Program(Program Ekle)" page. For schedule device on-off.
                    Intent intent = new Intent(getApplicationContext(),ActivityAddSchedule.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };



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
        Intent intent = new Intent(getApplicationContext(),ActivityMainPage.class);
        startActivity(intent);
    }

    private void setLampONorOFF(){
        if(Lamp.isOFF())//is off,turn on.
            turnLampON();
        else//is on,turn off.
            turnLampOFF();

    }

    private void turnLampON(){
        //if(MyLog.DEGUB)MyLog.d(TAG,"turnLampON");
        imageView_Lamp.setImageResource(R.drawable.lamp_on);
        Lamp.setON(true);
        changeLampColorFromPallette(COLOR);
        if(Lamp.isRGB()){
            setVisibilityLampNavigation(true);

        }
        else{
            setVisibilityLampNavigation(false);
            setVisibilityLampNavigation2(true);
        }
        imageView_LampColor.setVisibility(View.VISIBLE);

        setBrightnessToImageView_LampColor(Lamp.getBrightLevelOfWhite());//todo:Last used brightness level by user. get from database.
        setSaturationToImageView_LampColor(Lamp.getSaturationLevelOfColour());//todo:Last used saturation level by user. get from database.
        //TODO: send "turn on" data to mqtt server:
        //..
    }
    private void turnLampOFF(){
        imageView_Lamp.setImageResource(R.drawable.lamp_off);
        Lamp.setOFF(true);
        //changeLampColorFromPallette(getResources().getColor(R.color.color_violet1));
        colorPickerView.setEnabled(false);
        colorPickerView.setVisibilityColorPalete(View.INVISIBLE);
        imageView_LampColor.setVisibility(View.GONE);
        linearLayout_White.setVisibility(View.GONE);
        linearLayout_Colour.setVisibility(View.GONE);
        linearLayout_Scene.setVisibility(View.GONE);
        setVisibility__linearLayout_SettingsForSceneXXXX_ForBSS(View.GONE);
        setVisibilityLampNavigation(false);
        //TODO: send "turn off" data to mqtt server:
        //..
    }

    /**
     * Visibility all "bottom_LampNavigation" items.
     * @param state
     */
    private void setVisibilityLampNavigation(boolean state){
        if(state == false)//because we have to hide all of item:
            bottom_LampNavigation.getMenu().getItem(0).setVisible(state);//navigation_Item_PanoClosingArrow

        bottom_LampNavigation.getMenu().getItem(1).setVisible(state);//White
        bottom_LampNavigation.getMenu().getItem(2).setVisible(state);//Colour
        bottom_LampNavigation.getMenu().getItem(3).setVisible(state);//Scene
        bottom_LampNavigation.getMenu().getItem(4).setVisible(true);//Schedule. always show.
    }

    /**
     * only visibility "White,Schedule" items.
     * @param state
     */
    private void setVisibilityLampNavigation2(boolean state){
        bottom_LampNavigation.getMenu().getItem(1).setVisible(state);//White
        bottom_LampNavigation.getMenu().getItem(4).setVisible(true);//Schedule. always show.
    }

    /**
     * set visibility Edit Constrols on the "Scene" (Scene1,Scene2,Scene3,Scene4) items.
     * @param visibility
     */
    private void setVisibilitySceneEditControls(int visibility){
        imageView_SceneEdit_ShowBSS.setVisibility(visibility);
        imageView_SceneEdit_ShowC.setVisibility(visibility);
    }

    /**
     * if user click "PanoClosingArrow" Item,
     */
    private void panoClosingArrowClose(){
        linearLayout_White.setVisibility(View.GONE);
        linearLayout_Colour.setVisibility(View.GONE);
        linearLayout_Scene.setVisibility(View.GONE);
        //hide yourself:
        bottom_LampNavigation.getMenu().getItem(0).setVisible(false);//navigation_Item_PanoClosingArrow
        if(Lamp.isRGB()){
            setVisibilityLampNavigation(true);
        }
        else{
            setVisibilityLampNavigation(false);
            setVisibilityLampNavigation2(true);
        }
    }

    //linearLayout_SettingsForSceneXXXX
    private boolean visibility__linearLayout_SettingsForSceneXXXX_ForBSS = false;
    private void setVisibility__linearLayout_SettingsForSceneXXXX_ForBSS(int visibility){
        linearLayout_SettingsForSceneXXXX.setVisibility(visibility);
        if(visibility == View.VISIBLE)
            visibility__linearLayout_SettingsForSceneXXXX_ForBSS = true;
        else
            visibility__linearLayout_SettingsForSceneXXXX_ForBSS = false;
    }
    private boolean isVisibility__linearLayout_SettingsForSceneXXXX_ForBSS(){
        return visibility__linearLayout_SettingsForSceneXXXX_ForBSS;
    }


    private boolean invisibility_linearLayout_Scene_ForShowC = false;
    private void setVisibility__linearLayout_Scene_ForShowC(int visibility){
        linearLayout_Scene.setVisibility(visibility);
        if(visibility == View.VISIBLE) {
            colorPickerView.setEnabled(false);
            colorPickerView.setVisibilityColorPalete(View.INVISIBLE);
            //imageView_LampColor.setVisibility(View.GONE);
            invisibility_linearLayout_Scene_ForShowC = false;//is not invisible.
        }else {
            colorPickerView.setEnabled(true);
            colorPickerView.setVisibilityColorPalete(View.VISIBLE);
            imageView_LampColor.setVisibility(View.VISIBLE);
            invisibility_linearLayout_Scene_ForShowC = true;//yes is invisible.
        }
    }
    private boolean isInVisibility__linearLayout_Scene_ForShowC(){
        return  invisibility_linearLayout_Scene_ForShowC;
    }



    /**
     * Apply "Gray" color to "Scene" Textview's. For deaktif status.
     * @param textViews
     */
    private void applyGrayColorToSceneTextViews(TextView[] textViews){
        for(int i=0;i<textViews.length;i++){
            textViews[i].setTextColor(getResources().getColor(R.color.color_Gray));
        }
    }

    /**
     * Apply "White" color to "Scene" TextView,when user selected which one TextView.(User actually select LinearLayout. Exp:linearLayout_SceneNight)
     * @param textView
     */
    private void applyWhiteColorToSceneTextView(TextView textView){
        textView.setTextColor(getResources().getColor(R.color.color_White));
    }


    PorterDuffColorFilter porterDuffColorFilter_ForBrightness ;
    /**
     * Change brightness "imageView_LampColor"
     * @param progress
     */
    private void setBrightnessToImageView_LampColor(int progress){

        /**fixme:This method is applicable (maybe later)
         * >https://www.androidhive.info/2017/11/android-building-image-filters-like-instagram/
         * >https://github.com/Zomato/AndroidPhotoFilters
         */
         //MyLog.d("kertensS","bright:" + progress);
         int value =0;
         progress = ~progress; //don't touch.!

            if(progress < -30){ //-40,-50,-100
                value = (progress - 100) * 255 / 100;
                porterDuffColorFilter_ForBrightness = new PorterDuffColorFilter(Color.argb(value, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);// 255, 255, 255
            }else
            {//-29,-20,-10
                value = (progress - 100) * 63 / 100;
                porterDuffColorFilter_ForBrightness = new PorterDuffColorFilter(Color.argb(value, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);// 255, 255, 255
            }
           changeLampColorFromPallette(COLOR,porterDuffColorFilter_ForBrightness );
    }



    private ColorMatrix colorMatrix_ForSaturation;
    private ColorMatrixColorFilter colorMatrixColorFilter_ForSaturation;
    /**
     * Change saturation of "imageView_LampColor".  (bottom_LampNavigation Colour item)
     * @param progress
     */
    private void setSaturationToImageView_LampColor(int progress){
        //Initialize the ColorMatrix object
        colorMatrix_ForSaturation = new ColorMatrix();
        //Initialize the ColorMatrixColorFilter object
        colorMatrixColorFilter_ForSaturation = new ColorMatrixColorFilter(colorMatrix_ForSaturation);
        colorMatrix_ForSaturation.setSaturation(progress/(float)100);
        //Create a new ColorMatrixColorFilter with the recently altered colorMatrix
        colorMatrixColorFilter_ForSaturation = new ColorMatrixColorFilter(colorMatrix_ForSaturation);
        //Assign the ColorMatrix to the paint object again
        //imageView_LampColor.setColorFilter(cmFilter);
        changeLampColorFromPallette(COLOR,colorMatrixColorFilter_ForSaturation);
    }


    ColorMatrix ColorMatrix_ForColourTemp;
    ColorMatrixColorFilter colorMatrixColorFilter_ForColourTemp;
    /**
     * Change Colour Temp of "imageView_LampColor"
     * @param progress
     */
    private void setColourTempToImageView_LampColor(int progress){
        //TODO: Colour Temp.
        int amount = 255;
        ColorMatrix_ForColourTemp = new ColorMatrix();
        ColorMatrix_ForColourTemp.set(new float[] {
                /*(float) ((1.0000* amount)/255.0f), 0, 0, 0, 0,
                0, (float) (((1.0000+progress)* amount)/255.0f), 0, 0, 0,
                0, 0, (float) (((1.0000+progress)* amount)/255.0), 0, 0,
                0, 0, 0, 1, 0});*/
                             (float) (((1.0000+progress)* progress)/255.0f), 1, 1, 1, 0,  //r
                              0, (float) (((1.0000+progress)* progress)/255.0f), 1, 0, 0, //g
                              0, 0, (float) (((1.0000+progress)* progress)/255.0f), 0, 0, //b
                              0, 0, 0, 1, 0});
        colorMatrixColorFilter_ForColourTemp = new ColorMatrixColorFilter(ColorMatrix_ForColourTemp);
        //imageView_LampColor.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        changeLampColorFromPallette(COLOR,colorMatrixColorFilter_ForColourTemp);
    }


//############################################################################################################: changeLampColorFromPallette +@{
    //FIXME: Refectoring these all funciton,maybe decrease only one function



    /**
     * TODO: make it more performance. Not re-create each time.
     * FIXME: make it more performance and fast. Not re-create each time.
     * //currently only working ;)
     */
    Bitmap bitmap;
    Paint paint = new Paint();
    Canvas canvas;
    private void changeLampColorFromPallette(int _color){
        /*Bitmap*/ bitmap = Bitmap.createBitmap(
                250, // Width
                250, // Height
                Bitmap.Config.ARGB_8888 // Config
        );


        // Initialize a new Canvas instance
        /*Canvas*/ canvas = new Canvas(bitmap);

        // Draw a solid color to the canvas background
        canvas.drawColor(getResources().getColor(R.color.color_violet1));

        // Initialize a new Paint instance to draw the Circle
        //Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(_color);
        paint.setAntiAlias(true);

        // Calculate the available radius of canvas
        int radius = Math.min(canvas.getWidth(),canvas.getHeight()/2);

        // Set a pixels value to padding around the circle
        int padding = 1;

                /*
                    public void drawCircle (float cx, float cy, float radius, Paint paint)
                        Draw the specified circle using the specified paint. If radius is <= 0, then
                        nothing will be drawn. The circle will be filled or framed based on the
                        Style in the paint.

                    Parameters
                        cx : The x-coordinate of the center of the circle to be drawn
                        cy : The y-coordinate of the center of the circle to be drawn
                        radius : The radius of the cirle to be drawn
                        paint : The paint used to draw the circle
                */
        // Finally, draw the circle on the canvas
        canvas.drawCircle(
                canvas.getWidth() / 2, // cx
                canvas.getHeight() / 2, // cy
                radius - padding, // Radius
                paint // Paint
        );

        // Display the newly created bitmap on app interface
        imageView_LampColor.setImageBitmap(bitmap);
    }

    /**
     * change brightness of "imageView_LampColor"  by "setBrightnessToImageView_LampColor"
     * @param _color
     * @param porterDuffColorFilter
     */
    private void changeLampColorFromPallette(int _color,PorterDuffColorFilter porterDuffColorFilter){
        /*Bitmap*/ bitmap = Bitmap.createBitmap(
                250, // Width
                250, // Height
                Bitmap.Config.ARGB_8888 // Config
        );


        // Initialize a new Canvas instance
        /*Canvas*/ canvas = new Canvas(bitmap);

        // Draw a solid color to the canvas background
        canvas.drawColor(getResources().getColor(R.color.color_violet1));

        // Initialize a new Paint instance to draw the Circle
        //Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(_color);
        paint.setColorFilter(porterDuffColorFilter);
        paint.setAntiAlias(true);

        // Calculate the available radius of canvas
        int radius = Math.min(canvas.getWidth(),canvas.getHeight()/2);

        // Set a pixels value to padding around the circle
        int padding = 1;

                /*
                    public void drawCircle (float cx, float cy, float radius, Paint paint)
                        Draw the specified circle using the specified paint. If radius is <= 0, then
                        nothing will be drawn. The circle will be filled or framed based on the
                        Style in the paint.

                    Parameters
                        cx : The x-coordinate of the center of the circle to be drawn
                        cy : The y-coordinate of the center of the circle to be drawn
                        radius : The radius of the cirle to be drawn
                        paint : The paint used to draw the circle
                */
        // Finally, draw the circle on the canvas
        canvas.drawCircle(
                canvas.getWidth() / 2, // cx
                canvas.getHeight() / 2, // cy
                radius - padding, // Radius
                paint // Paint
        );

        // Display the newly created bitmap on app interface
        imageView_LampColor.setImageBitmap(bitmap);
    }

    /**
     * change brightness of "imageView_LampColor"  by "setSaturationToImageView_LampColor"
     * @param _color
     * @param colorMatrixColorFilter
     */
    private void changeLampColorFromPallette(int _color,ColorMatrixColorFilter colorMatrixColorFilter){
        /*Bitmap*/ bitmap = Bitmap.createBitmap(
                250, // Width
                250, // Height
                Bitmap.Config.ARGB_8888 // Config
        );

        // Initialize a new Canvas instance
        /*Canvas*/ canvas = new Canvas(bitmap);

        // Draw a solid color to the canvas background
        canvas.drawColor(getResources().getColor(R.color.color_violet1));

        // Initialize a new Paint instance to draw the Circle
        //Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(_color);
        paint.setColorFilter(colorMatrixColorFilter);
        paint.setAntiAlias(true);

        // Calculate the available radius of canvas
        int radius = Math.min(canvas.getWidth(),canvas.getHeight()/2);

        // Set a pixels value to padding around the circle
        int padding = 1;

                /*
                    public void drawCircle (float cx, float cy, float radius, Paint paint)
                        Draw the specified circle using the specified paint. If radius is <= 0, then
                        nothing will be drawn. The circle will be filled or framed based on the
                        Style in the paint.

                    Parameters
                        cx : The x-coordinate of the center of the circle to be drawn
                        cy : The y-coordinate of the center of the circle to be drawn
                        radius : The radius of the cirle to be drawn
                        paint : The paint used to draw the circle
                */
        // Finally, draw the circle on the canvas
        canvas.drawCircle(
                canvas.getWidth() / 2, // cx
                canvas.getHeight() / 2, // cy
                radius - padding, // Radius
                paint // Paint
        );
        // Display the newly created bitmap on app interface
        imageView_LampColor.setImageBitmap(bitmap);
    }

//############################################################################################################: changeLampColorFromPallette -@}

}


