package com.example.trident.smart;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.trident.common.Alert;
import com.example.trident.common.BetweenActivities;
import com.example.trident.common.MyLog;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ActivityAddDevices extends AppCompatActivity {
    /**
     * "Cihaz Ekle, -Manuel Olarak Ekle-Cihaz Ara-Barkod ile ekle"
     * used by "FragmentHome.java"
     *
     */
    private String TAG ="[ActivityAddDevices]";

    //TabLayout tabLayout;
    //ViewPager viewPager;
    FrameLayout frameLayout_ProductBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_devices);
        //

        LinearLayout linearLayout_Back   = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back               = (Button)findViewById(R.id.button_Back);
        Button button_SearchDevice       = (Button)findViewById(R.id.button_SearchDevice);
        Button button_GoToQRCodeScanPage = (Button)findViewById(R.id.button_GoToQRCodeScanPage);
        frameLayout_ProductBase          = (FrameLayout)findViewById(R.id.frameLayout_ProductBase);

        ///Default:
        //button_SearchDevice.setVisibility(View.GONE);//fixme:V2'ye kadar gizli kalsın.!!!!!!!!!!!!!!!!!!!(xml tarafından visibility=gone yapıldı)
        tabCategoriOfProductsButtonClickHandler((Button)findViewById(R.id.button_ElectricalSwitches));//select default this product page.

        ///Events:@{
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
        button_SearchDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to "Search Device" page. !!!!!NOTE:will be completed in V2.!!!!!
            }
        });
        button_GoToQRCodeScanPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(getApplicationContext(),ActivityQRCodeScanPage.class);
                startActivity(intent);*/
                //
               scanQRCode();
            }
        });


    }//onCreate

        private void scanQRCode(){
            IntentIntegrator intentIntegrator = new IntentIntegrator(ActivityAddDevices.this);
            intentIntegrator.initiateScan();//start
        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * For Scan QR Code: get result from QR Code scan page.
         */
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result == null)
            super.onActivityResult(requestCode, resultCode, data);
        else{
            if(result.getContents() != null){
                String str_result = result.getContents();
                if(str_result.contains("id=com."))//himmmm bu google play linkidir. exp:"https://play.google.com/store/apps/details?id=com.tuya.smart"
                {
                    String[] arrsplit = str_result.split("="); //split like this link "https://play.google.com/store/apps/details?id=com.tuya.smart"
                    str_result = arrsplit[1];//assing "com.tuya.smart" to str_result variable.
                }
                if(str_result.length() > 3 && str_result.substring(0,4).equals("com.")){
                    //todo git:
                        final String package_name = str_result;//"com.tuya.smart";//todo: Replace with "com.trident.smat" package name.
                        Alert.MessageDialog messageDialog = new Alert().new MessageDialog();
                        AlertDialog.Builder dialogBuilder = messageDialog.show(ActivityAddDevices.this,getString(R.string.notification),getString(R.string.qrcodepage_notification) +"\n" + package_name);
                        dialogBuilder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setData(Uri.parse("market://details?id=" + package_name)); //<<burası çalışır.
                                startActivity(intent);
                            }
                        });
                        dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //pass. do nothing
                            }
                        });
                        dialogBuilder.create().show();
                }else{
                    if(str_result.contains("itunes.apple"))//IOS'a ait QR kod taratıldı...
                        Toast.makeText(getApplicationContext(),getString(R.string.qrcodepage_alertforios),Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(),getString(R.string.qrcodepage_alert),Toast.LENGTH_SHORT).show();
                   scanQRCode();
                }
            }else{
                Toast.makeText(this, getString(R.string.result_not_found), Toast.LENGTH_LONG).show();
                return;
            }
        }
    }//onActivityResult(..)


    /**
     * left side of the screen(activity_add_device.xml)
     * In the "activity_add_devices.xml" click tab(left side) buttons click.
     * @param view  click button View object.
     */
    public void tabCategoriOfProductsButtonClickHandler(View view){

        //button_ElectricalSwitches
        //button_Lighting
        //button_HomeAppliance
        //button_SmallHomeAppliance
        //button_KitchenAppliance
        //button_SecuritySensor
        //button_WearableHealth
        //button_Other
     switch (view.getId()){
         case R.id.button_ElectricalSwitches:///<% Electrical Switches (Anahtarlar). include "product_switches.xml" %>
             loadProductPage(R.layout.product_switches);
             productSwitchesPageHandler();
             break;
         case R.id.button_Lighting:///<% Lighting(Aydınlatma) %>
             loadProductPage(R.layout.product_lighting);
             productLightingPageHandler();
             break;
         case R.id.button_LargeHomeAppliance:///<% LargeHomeAppliance(Beyaz Eşya%>
             loadProductPage(R.layout.product_largehomeappliance);
             productLargeHomeAppliancePageHandler();
             break;
         case R.id.button_SmallHomeAppliance:///<% SmallHomeAppliance(Küçük Ev Aletleri %>
             loadProductPage(R.layout.product_smallhomeappliance);
             productSmallHomeAppliancePageHandler();
             break;
         case R.id.button_KitchenAppliance:///<% KitchenAppliance(Mutfak Aletleri) %>
             loadProductPage(R.layout.product_kitchenappliance);
             productKitchenAppliancePageHandler();
             break;
         case R.id.button_SecuritySensor:///<% SecuritySensor(Güvenlik & Kuruma) %>
             loadProductPage(R.layout.product_securitysensor);
             productSecurityAndSensorPageHandler();
             break;
         case R.id.button_WearableHealth:///<% WearableHealth(Giyilebilir & Sağlık) %>
             loadProductPage(R.layout.product_wearableandhealth);
             productWearableAndHealthPageHandler();
             break;
         case R.id.button_Other:///<% Other(Diğer) %>
             loadProductPage(R.layout.product_other);
             productOtherPageHandler();
             break;
     }
        makeCategoriOfProductsButtonDeactivate();//firstly make all button deactivate.
        makeCategoriOfProductsButtonActivate(view);//and then make activate clicked button.
    }


    private void loadProductPage(int r_layout){
        frameLayout_ProductBase.removeAllViews();//clear
        ConstraintLayout constraintLayout_Product = (ConstraintLayout) View.inflate(getApplicationContext(), r_layout, null);
        frameLayout_ProductBase.addView(constraintLayout_Product);
    }//loadProduct(...)

    /**
     * left side of the screen(activity_add_device.xml)
     * Ürün kategorisi için tıklanan buttonun aktif olma durumunu belirle.
     * @param view Button ref.
     */
    private void makeCategoriOfProductsButtonActivate(View view){
        view.setBackgroundColor(getResources().getColor(R.color.color_White));
        ((Button)view).setTextColor(getResources().getColor(R.color.color_orange1));
    }

    Button button_ElectricalSwitches;
    Button button_Lighting;
    Button button_LargeHomeAppliance;
    Button button_SmallHomeAppliance;
    Button button_KitchenAppliance;
    Button button_SecuritySensor;
    Button button_WearableHealth;
    Button button_Other;
    Button[] categoriOfProductsTabButtons;
    /**
     * left side of the screen(activity_add_device.xml)
     * Ürün kategorisi için tıklanan button dışındaki olanların durumunu normale çevir.
     * Fixme: Button nesne erişimlerini daha performanslı hale getir.
     */
    private void makeCategoriOfProductsButtonDeactivate(){
        //we make all button deactivate:
      if(button_ElectricalSwitches == null) {
          button_ElectricalSwitches = (Button) findViewById(R.id.button_ElectricalSwitches);
          button_Lighting           = (Button) findViewById(R.id.button_Lighting);
          button_LargeHomeAppliance      = (Button) findViewById(R.id.button_LargeHomeAppliance);
          button_SmallHomeAppliance = (Button) findViewById(R.id.button_SmallHomeAppliance);
          button_KitchenAppliance   = (Button) findViewById(R.id.button_KitchenAppliance);
          button_SecuritySensor     = (Button) findViewById(R.id.button_SecuritySensor);
          button_WearableHealth     = (Button) findViewById(R.id.button_WearableHealth);
          button_Other              = (Button) findViewById(R.id.button_Other);
          categoriOfProductsTabButtons =new Button[]{
                  button_ElectricalSwitches,
                  button_Lighting,
                  button_LargeHomeAppliance,
                  button_SmallHomeAppliance,
                  button_KitchenAppliance,
                  button_SecuritySensor,
                  button_WearableHealth,
                  button_Other};
      }//if
     for(int i=0;i<categoriOfProductsTabButtons.length;i++){
         categoriOfProductsTabButtons[i].setBackgroundColor(getResources().getColor(R.color.color_White1));
         categoriOfProductsTabButtons[i].setTextColor(getResources().getColor(R.color.color_Gray));
     }//for

    }//makeCategoriOfProductsButtonDeactivate()


    /**
     * TODO: Bu ürünlere ait nesneler,ürünlerin son hali olarak yeniden düzenlenmelidir.
     *       todo:Buradaki ürünler tamamen temsilidir
     */

    //row1:
    LinearLayout linearLayout_SocketWifi;
    LinearLayout linearLayout_SocketZigbee;
    LinearLayout linearLayout_SocketBluetooth;
    //row2:
    LinearLayout linearLayout_Socket;
    LinearLayout linearLayout_SwitchWifi;
    LinearLayout linearLayout_SwitchZigbee;
    //row3:
    LinearLayout linearLayout_SwitchBluetooth;
    LinearLayout linearLayout_PowerStripWifi;
    LinearLayout linearLayout_ScenarioSwitchWifi;
    //row4:
    LinearLayout linearLayout_ScenarioSwitchZigbee;
    LinearLayout linearLayout_CurtainSwitchWifi;
    LinearLayout linearLayout_CurtainSwitchZigbee;
    //row5:
    LinearLayout linearLayout_WrilessSwitchZigbee;
    LinearLayout linearLayout_ScenarioLightWifi;
    LinearLayout linearLayout_BreakerWifi;

    /**
     *We have included file "product_switches.xml"
     * In this xml file,we will click product layout then run this function.
     * -Anahtarlar
     */
    private void productSwitchesPageHandler(){
        /**
         * TODO:
         * FIXME: Daha etkin bir çözüm geliştirilmelidir(kod kalabalığı vb.). !!!!!!!!!!!!!!!!!!!!!!!!
         *
         */
        if(linearLayout_SocketWifi == null){
            //row1:
            linearLayout_SocketWifi      = (LinearLayout)findViewById(R.id.linearLayout_SocketWifi);
            linearLayout_SocketZigbee    = (LinearLayout)findViewById(R.id.linearLayout_SocketZigbee);
            linearLayout_SocketBluetooth = (LinearLayout)findViewById(R.id.linearLayout_SocketBluetooth);
            //row2:
            linearLayout_Socket       = (LinearLayout)findViewById(R.id.linearLayout_Socket);
            linearLayout_SwitchWifi   = (LinearLayout)findViewById(R.id.linearLayout_SwitchWifi);
            linearLayout_SwitchZigbee = (LinearLayout)findViewById(R.id.linearLayout_SwitchZigbee);
            //row3:
            linearLayout_SwitchBluetooth    = (LinearLayout)findViewById(R.id.linearLayout_SwitchBluetooth);
            linearLayout_PowerStripWifi     = (LinearLayout)findViewById(R.id.linearLayout_PowerStripWifi);
            linearLayout_ScenarioSwitchWifi = (LinearLayout)findViewById(R.id.linearLayout_ScenarioSwitchWifi);
            //row4:
            linearLayout_ScenarioSwitchZigbee = (LinearLayout)findViewById(R.id.linearLayout_ScenarioSwitchZigbee);
            linearLayout_CurtainSwitchWifi    = (LinearLayout)findViewById(R.id.linearLayout_CurtainSwitchWifi);
            linearLayout_CurtainSwitchZigbee  = (LinearLayout)findViewById(R.id.linearLayout_CurtainSwitchZigbee);
            //row5:
            linearLayout_WrilessSwitchZigbee = (LinearLayout)findViewById(R.id.linearLayout_WrilessSwitchZigbee);
            linearLayout_ScenarioLightWifi   = (LinearLayout)findViewById(R.id.linearLayout_ScenarioLightWifi);
            linearLayout_BreakerWifi         = (LinearLayout)findViewById(R.id.linearLayout_BreakerWifi);
        }//if

        linearLayout_SocketWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.d("kerim"+TAG,"linearLayout_SocketWifi");
            }
        });
        linearLayout_SocketZigbee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.d("kerim"+TAG,"linearLayout_SocketZigbee");
            }
        });
        linearLayout_SocketBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.d("kerim"+TAG,"linearLayout_SocketBluetooth");
            }
        });
        linearLayout_Socket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.d("kerim"+TAG,"linearLayout_Socket");
            }
        });
        linearLayout_SwitchWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.d("kerim"+TAG,"linearLayout_SwitchWifi");
            }
        });
        linearLayout_SwitchZigbee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.d("kerim"+TAG,"linearLayout_SwitchZigbee");
            }
        });
        linearLayout_SwitchBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.d("kerim"+TAG,"linearLayout_SwitchBluetooth");
            }
        });
        linearLayout_PowerStripWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.d("kerim"+TAG,"linearLayout_PowerStripWifi");
            }
        });
        linearLayout_ScenarioSwitchWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.d("kerim"+TAG,"linearLayout_ScenarioSwitchWifi");
            }
        });
        linearLayout_ScenarioSwitchZigbee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.d("kerim"+TAG,"linearLayout_ScenarioSwitchZigbee");
            }
        });
        linearLayout_CurtainSwitchWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.d("kerim"+TAG,"linearLayout_CurtainSwitchWifi");
            }
        });
        linearLayout_CurtainSwitchZigbee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.d("kerim"+TAG,"linearLayout_CurtainSwitchZigbee");
            }
        });
        linearLayout_WrilessSwitchZigbee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.d("kerim"+TAG,"linearLayout_WrilessSwitchZigbee");
            }
        });
        linearLayout_ScenarioLightWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.d("kerim"+TAG,"linearLayout_ScenarioLightWifi");
            }
        });
        linearLayout_BreakerWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.d("kerim"+TAG,"linearLayout_BreakerWifi");
            }
        });
    }//productSwitchesPageHandler()




    //row1:
    LinearLayout linearLayout_LightingWifi;
    LinearLayout linearLayout_LightingBluetooth;
    LinearLayout linearLayout_LightingZigbee;
    //row2:
    LinearLayout linearLayout_LightingBleWifi;
    LinearLayout linearLayout_LightingRemoteControlBluetooth;
    /**
     *We have included file "product_lighting.xml"
     * In this xml file,we will click product layout then run this function.
     * -Aydınlatma
     */
    private void productLightingPageHandler(){
        if(linearLayout_LightingWifi == null){
            linearLayout_LightingWifi                   = (LinearLayout)findViewById(R.id.linearLayout_LightingWifi);
            linearLayout_LightingBluetooth              = (LinearLayout)findViewById(R.id.linearLayout_LightingBluetooth);
            linearLayout_LightingZigbee                 = (LinearLayout)findViewById(R.id.linearLayout_LightingZigbee);
            linearLayout_LightingBleWifi                = (LinearLayout)findViewById(R.id.linearLayout_LightingBleWifi);
            linearLayout_LightingRemoteControlBluetooth = (LinearLayout)findViewById(R.id.linearLayout_LightingRemoteControlBluetooth);
        }
        ///Events:@{
         //todo:
        ///events:@}
    }//productLightingPageHandler()



    LinearLayout linearLayout_Ventilation;
    LinearLayout linearLayout_AirConditioner;
    LinearLayout linearLayout_Refrigerator;
    LinearLayout linearLayout_WashingMachine;
    LinearLayout linearLayout_AirConditionerZigbee;
    LinearLayout linearLayout_WaterHeater;
    /**
     *We have included file "product_largehomeappliance.xml"
     * In this xml file,we will click product layout then run this function.
     * -Beyaz Eşya
     */
    private void productLargeHomeAppliancePageHandler(){
        if(linearLayout_Ventilation == null){
            linearLayout_Ventilation          = (LinearLayout)findViewById(R.id.linearLayout_Ventilation);
            linearLayout_AirConditioner       = (LinearLayout)findViewById(R.id.linearLayout_AirConditioner);
            linearLayout_Refrigerator         = (LinearLayout)findViewById(R.id.linearLayout_Refrigerator);
            linearLayout_WashingMachine       = (LinearLayout)findViewById(R.id.linearLayout_WashingMachine);
            linearLayout_AirConditionerZigbee = (LinearLayout)findViewById(R.id.linearLayout_AirConditionerZigbee);
            linearLayout_WaterHeater          = (LinearLayout)findViewById(R.id.linearLayout_WaterHeater);
        }
        ///Events:@{
        //todo:
        ///events:@}
    }//productLargeHomeAppliancePageHandler()



    LinearLayout linearLayout_WarmingTable;
    LinearLayout linearLayout_TeaMachine;
    LinearLayout linearLayout_AirPurifier;
    LinearLayout linearLayout_PetFeeder;
    LinearLayout linearLayout_Heater;
    LinearLayout linearLayout_HeaterZigbee;
    LinearLayout linearLayout_HeaterBluetooth;
    LinearLayout linearLayout_Thermostat;
    LinearLayout linearLayout_ThermostatZigbee;
    LinearLayout linearLayout_ThermostatBluetooth;
    LinearLayout linearLayout_DoorandWindowWifi;
    LinearLayout linearLayout_Curtain;
    LinearLayout linearLayout_CurtainZigbee;
    LinearLayout linearLayout_CurtainMotorZigbee;
    LinearLayout linearLayout_ElectricBlankets;
    LinearLayout linearLayout_RobotVacuum;
    LinearLayout linearLayout_RobotVacuumZigbee;
    /**
     *We have included file "product_smallhomeappliance.xml"
     * In this xml file,we will click product layout then run this function.
     * -Küçük Ev Aletleri.
     */
    private void productSmallHomeAppliancePageHandler(){
        if(linearLayout_WarmingTable == null){
            linearLayout_WarmingTable        = (LinearLayout)findViewById(R.id.linearLayout_WarmingTable);
            linearLayout_TeaMachine          = (LinearLayout)findViewById(R.id.linearLayout_TeaMachine);
            linearLayout_AirPurifier         = (LinearLayout)findViewById(R.id.linearLayout_AirPurifier);
            linearLayout_PetFeeder           = (LinearLayout)findViewById(R.id.linearLayout_PetFeeder);
            linearLayout_Heater              = (LinearLayout)findViewById(R.id.linearLayout_Heater);
            linearLayout_HeaterZigbee        = (LinearLayout)findViewById(R.id.linearLayout_HeaterZigbee);
            linearLayout_HeaterBluetooth     = (LinearLayout)findViewById(R.id.linearLayout_HeaterBluetooth);
            linearLayout_Thermostat          = (LinearLayout)findViewById(R.id.linearLayout_Thermostat);
            linearLayout_ThermostatZigbee    = (LinearLayout)findViewById(R.id.linearLayout_ThermostatZigbee);
            linearLayout_ThermostatBluetooth = (LinearLayout)findViewById(R.id.linearLayout_ThermostatBluetooth);
            linearLayout_DoorandWindowWifi   = (LinearLayout)findViewById(R.id.linearLayout_DoorandWindowWifi);
            linearLayout_Curtain             = (LinearLayout)findViewById(R.id.linearLayout_Curtain);
            linearLayout_CurtainZigbee       = (LinearLayout)findViewById(R.id.linearLayout_CurtainZigbee);
            linearLayout_CurtainMotorZigbee  = (LinearLayout)findViewById(R.id.linearLayout_CurtainMotorZigbee);
            linearLayout_ElectricBlankets    = (LinearLayout)findViewById(R.id.linearLayout_ElectricBlankets);
            linearLayout_RobotVacuum         = (LinearLayout)findViewById(R.id.linearLayout_RobotVacuum);
            linearLayout_RobotVacuumZigbee   = (LinearLayout)findViewById(R.id.linearLayout_RobotVacuumZigbee);
        }
        ///Events:@{
        //todo:
        ///events:@}
    }//productSmallHomeAppliancePageHandler()



    LinearLayout linearLayout_CoffeeMachine;
    LinearLayout linearLayout_FoodProcessor;
    LinearLayout linearLayout_DishWasher;
    LinearLayout linearLayout_InductionCooker;
    LinearLayout linearLayout_Microwave;
    LinearLayout linearLayout_Oven;
    LinearLayout linearLayout_Blender;
    /**
     *We have included file "product_kitchenappliance.xml"
     * In this xml file,we will click product layout then run this function.
     * -Mutfak Aletleri.
     */
    private void productKitchenAppliancePageHandler(){
         if(linearLayout_CoffeeMachine == null){
             linearLayout_CoffeeMachine   = (LinearLayout)findViewById(R.id.linearLayout_CoffeeMachine);
             linearLayout_FoodProcessor   = (LinearLayout)findViewById(R.id.linearLayout_FoodProcessor);
             linearLayout_DishWasher      = (LinearLayout)findViewById(R.id.linearLayout_DishWasher);
             linearLayout_InductionCooker = (LinearLayout)findViewById(R.id.linearLayout_InductionCooker);
             linearLayout_Microwave       = (LinearLayout)findViewById(R.id.linearLayout_Microwave);
             linearLayout_Oven            = (LinearLayout)findViewById(R.id.linearLayout_Oven);
             linearLayout_Blender         = (LinearLayout)findViewById(R.id.linearLayout_Blender);
         }
        ///Events:@{
        //todo:
        ///events:@}
    }//productKitchenAppliancePageHandler()



    LinearLayout linearLayout_SmartCamera;
    LinearLayout linearLayout_DoorLock;
    LinearLayout linearLayout_DoorLockWifi;
    LinearLayout linearLayout_DoorLockZigbee;
    LinearLayout linearLayout_DoorLockBluetooth;
    LinearLayout linearLayout_Sensor;
    LinearLayout linearLayout_SensorWifi;
    LinearLayout linearLayout_SensorZigbee;
    LinearLayout linearLayout_SensorBluetooth;
    LinearLayout linearLayout_Alarm;
    LinearLayout linearLayout_AlarmWifi;
    LinearLayout linearLayout_AlarmZigbee;
    LinearLayout linearLayout_AlarmBluetooth;
    LinearLayout linearLayout_ContactSensor;
    LinearLayout linearLayout_ContactSensorWifi;
    LinearLayout linearLayout_ContactSensorZigbee;
    LinearLayout linearLayout_ContactSensorBluetooth;
    LinearLayout linearLayout_TemperatureAndHumidityWifi;
    LinearLayout linearLayout_TemperatureAndHumidityZigbee;
    LinearLayout linearLayout_TemperatureAndHumidityBluetooth;
    LinearLayout linearLayout_FloodDetectorWifi;
    LinearLayout linearLayout_FloodDetectorZigbee;
    LinearLayout linearLayout_SmokeDetector;
    LinearLayout linearLayout_GazDetector;
    LinearLayout linearLayout_EmergencyButton;
    LinearLayout linearLayout_EmergencyButtonWifi;
    LinearLayout linearLayout_EmergencyButtonZigbee;
    /**
     *We have included file "product_securitysensor.xml"
     * In this xml file,we will click product layout then run this function.
     * -Güvenlik&Koruma
     */
    private void productSecurityAndSensorPageHandler(){
        if(linearLayout_SmartCamera == null){
            linearLayout_SmartCamera                     = (LinearLayout)findViewById(R.id.linearLayout_SmartCamera);
            linearLayout_DoorLock                        = (LinearLayout)findViewById(R.id.linearLayout_DoorLock);
            linearLayout_DoorLockWifi                    = (LinearLayout)findViewById(R.id.linearLayout_DoorLockWifi);
            linearLayout_DoorLockZigbee                  = (LinearLayout)findViewById(R.id.linearLayout_DoorLockZigbee);
            linearLayout_DoorLockBluetooth               = (LinearLayout)findViewById(R.id.linearLayout_DoorLockBluetooth);
            linearLayout_Sensor                          = (LinearLayout)findViewById(R.id.linearLayout_Sensor);
            linearLayout_SensorWifi                      = (LinearLayout)findViewById(R.id.linearLayout_SensorWifi);
            linearLayout_SensorZigbee                    = (LinearLayout)findViewById(R.id.linearLayout_SensorZigbee);
            linearLayout_SensorBluetooth                 = (LinearLayout)findViewById(R.id.linearLayout_SensorBluetooth);
            linearLayout_Alarm                           = (LinearLayout)findViewById(R.id.linearLayout_Alarm);
            linearLayout_AlarmWifi                       = (LinearLayout)findViewById(R.id.linearLayout_AlarmWifi);
            linearLayout_AlarmZigbee                     = (LinearLayout)findViewById(R.id.linearLayout_AlarmZigbee);
            linearLayout_AlarmBluetooth                  = (LinearLayout)findViewById(R.id.linearLayout_AlarmBluetooth);
            linearLayout_ContactSensor                   = (LinearLayout)findViewById(R.id.linearLayout_ContactSensor);
            linearLayout_ContactSensorWifi               = (LinearLayout)findViewById(R.id.linearLayout_ContactSensorWifi);
            linearLayout_ContactSensorZigbee             = (LinearLayout)findViewById(R.id.linearLayout_ContactSensorZigbee);
            linearLayout_ContactSensorBluetooth          = (LinearLayout)findViewById(R.id.linearLayout_ContactSensorBluetooth);
            linearLayout_TemperatureAndHumidityWifi      = (LinearLayout)findViewById(R.id.linearLayout_TemperatureAndHumidityWifi);
            linearLayout_TemperatureAndHumidityZigbee    = (LinearLayout)findViewById(R.id.linearLayout_TemperatureAndHumidityZigbee);
            linearLayout_TemperatureAndHumidityBluetooth = (LinearLayout)findViewById(R.id.linearLayout_TemperatureAndHumidityBluetooth);
            linearLayout_FloodDetectorWifi               = (LinearLayout)findViewById(R.id.linearLayout_FloodDetectorWifi);
            linearLayout_FloodDetectorZigbee             = (LinearLayout)findViewById(R.id.linearLayout_FloodDetectorZigbee);
            linearLayout_SmokeDetector                   = (LinearLayout)findViewById(R.id.linearLayout_SmokeDetector);
            linearLayout_GazDetector                     = (LinearLayout)findViewById(R.id.linearLayout_GazDetector);
            linearLayout_EmergencyButton                 = (LinearLayout)findViewById(R.id.linearLayout_EmergencyButton);
            linearLayout_EmergencyButtonWifi             = (LinearLayout)findViewById(R.id.linearLayout_EmergencyButtonWifi);
            linearLayout_EmergencyButtonZigbee           = (LinearLayout)findViewById(R.id.linearLayout_EmergencyButtonZigbee);
        }
        ///Events:@{
        //todo:
        ///events:@}
    }//productSecurityAndSensorPageHandler()


    LinearLayout linearLayout_BodyWeightMeter;
    LinearLayout linearLayout_WatchORWristBand;
    LinearLayout linearLayout_SmartBed;
    /**
     *We have included file "product_wearableandhealth.xml"
     * In this xml file,we will click product layout then run this function.
     * -Giyilebilir Ve Sağlık.
     */
    private void productWearableAndHealthPageHandler(){

        if(linearLayout_BodyWeightMeter == null) {
            linearLayout_BodyWeightMeter  = (LinearLayout) findViewById(R.id.linearLayout_BodyWeightMeter);
            linearLayout_WatchORWristBand = (LinearLayout) findViewById(R.id.linearLayout_WatchORWristBand);
            linearLayout_SmartBed         = (LinearLayout) findViewById(R.id.linearLayout_SmartBed);
        }
        ///Events:@{
        //todo:
        ///events:@}
    }//productWearableAndHealthPageHandler()

    LinearLayout linearLayout_GetewayZigbee;
    LinearLayout linearLayout_SmartSpeaker;
    private void productOtherPageHandler(){
        if(linearLayout_GetewayZigbee == null){
            linearLayout_GetewayZigbee = (LinearLayout)findViewById(R.id.linearLayout_GetewayZigbee);
            linearLayout_SmartSpeaker  = (LinearLayout)findViewById(R.id.linearLayout_SmartSpeaker);
        }
        ///Events:@{
        //todo:
        ///events:@}
    }//productOtherPageHandler()

    private void goToBack(){
        Intent intent = new Intent(getApplicationContext(),ActivityMainPage.class);
        intent.putExtra(BetweenActivities.where_come_from,"ActivityAddDevices.java:HOME");
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
