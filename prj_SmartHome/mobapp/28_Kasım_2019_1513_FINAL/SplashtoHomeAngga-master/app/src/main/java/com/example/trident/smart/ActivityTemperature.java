package com.example.trident.smart;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.DeviceType;
import com.example.trident.common.MyLog;
import com.example.trident.common.Static;

public class ActivityTemperature extends AppCompatActivity {
    /**
     * using by "ActivityAutomationSelectCondition.java" and "ActivityAutomationSettings.java",
     *           "ActivityCities.java (go to next class and come back)
     * "Sıcaklık seç"
     */

    //for assignment:
    private static int index_compare = 0;
    private static int index_degree = 0;

  /**
   * taşındı    "Static.java"
    private static int POSITION = -1;
    private static String FOR_WHAT =""; //Otomasyon eklemek için mi yoksa var olan bir otomasyonu editlemek için mi gelindi?
    private static String WHERE_COME_FROM ="";
    private static String CURRENT_CITY =null;
    */

    Spinner spinner_Compare;
    Spinner spinner_Degree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        //
        LinearLayout linearLayout_Back            = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back                        = (Button)findViewById(R.id.button_Back);
        final Button button_Done                  = (Button)findViewById(R.id.button_Done);
        LinearLayout linearLayout_GoToCurrentCity = (LinearLayout)findViewById(R.id.linearLayout_GoToCurrentCity);
        final TextView textView_CurrentCity       = (TextView)findViewById(R.id.textView_CurrentCity);
        /*final Spinner*/ spinner_Compare         = (Spinner)findViewById(R.id.spinner_Compare);
        /*final Spinner*/ spinner_Degree          = (Spinner)findViewById(R.id.spinner_Degree);
        //
        String[] array_compare = {
                getString(R.string.smaller_than),//Daha küçük
                getString(R.string.equals),      //Eşittir
                getString(R.string.greater_than) //Daha büyük
        };

        //todo: get temperature type from database. if type=C set "Temperature_Degree_C",else if type=F set "Temperature_Degree_F":
        int array_res_id = R.array.Temperature_Degree_C;  //R.array.Temperature_Degree_F;

        ArrayAdapter<String> dataAdapter_Compare = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array_compare);
        ArrayAdapter<CharSequence> dataAdapter_Degree  =  ArrayAdapter.createFromResource(this,R.array.Temperature_Degree_C,R.layout.support_simple_spinner_dropdown_item);
        dataAdapter_Compare.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        dataAdapter_Degree.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_Compare.setAdapter(dataAdapter_Compare);
        spinner_Degree.setAdapter(dataAdapter_Degree);
        spinner_Compare.setSelection(index_compare);
        spinner_Degree.setSelection(index_degree);
        ///
        ///
        int position = getIntent().getIntExtra(BetweenActivities.position,-1);
        if(position != -1)
            Static.POSITION = position;

        String for_what = getIntent().getStringExtra(BetweenActivities.for_what);//fixme: BU YÖNTEME DAHA VERİMLİ BİR YÖNTEM GELİŞTİRİLMELİDİR.
        if(for_what !=null)
            Static.FOR_WHAT = for_what;

        String where_come_from = getIntent().getStringExtra(BetweenActivities.where_come_from);
        if(where_come_from !=null)
            Static.WHERE_COME_FROM = where_come_from;

        final String current_city = getIntent().getStringExtra(BetweenActivities.current_city);//get from  ActivityAutomationSettings.java(for edit) and ActivityCities.java
        if(current_city !=null)
            Static.CURRENT_CITY = current_city;
        textView_CurrentCity.setText(Static.CURRENT_CITY);

        //get from ActivityAutomationSettings.java
        //String get_item_type = getIntent().getStringExtra("item_type");
        String item_name = getIntent().getStringExtra(BetweenActivities.item_name);
        if(item_name !=null){
            ///WE COME HERE FOR EDIT:
           // if(get_item_type.equals(DeviceType.TEMPERATURE)){
                String[] arr = item_name.split(":"); //exp:Sıcaklık:Daha az-40c
                arr = arr[1].split(" ");//split from space.
                if(arr.length == 2){
                    //Sıcaklık:Eşittir -40c
                    String c_value = arr[0];//Compare value. Exp: "Eşittir"
                    String d_value = arr[1];//Degree value   Exp: "-40c"
                   // MyLog.d("kerim[ActivityTemperature]","if(arr.length==2) c_value:"+c_value +",d_value:" + d_value);
                    deteremineItem_SpinnerCompare(c_value);
                    determineItem_SpinnerDegree(dataAdapter_Degree,d_value);
                }else if(arr.length == 3){
                    //Sıcaklık:Daha az -90c
                    String c_value1 = arr[0];//"Daha"
                    String c_value2 = arr[1];//"az"
                    String d_value  = arr[2];//"-40c"
                    String str_cvalue = c_value1 +" " + c_value2;
                   /* MyLog.d("kerim[ActivityTemperature]","if(arr.length==3) c_value1:"+c_value1
                            +",c_value2:"+c_value2 +",d_value:" + d_value +",str_cvalue:" + str_cvalue);*/
                    deteremineItem_SpinnerCompare(str_cvalue);
                    determineItem_SpinnerDegree(dataAdapter_Degree,d_value);
                }
           // }//if Type

        }else{
            ///if "get_item_type" is null,so we come here for Add Temperature.
        }

        ///Event:@{
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
        button_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: go to "ActivityAutomationSettings.java"
                //send "for_what" and "degree,compare" values.
                String compare = spinner_Compare.getSelectedItem().toString();
                String degree  = spinner_Degree.getSelectedItem().toString();
                /**
                 * getIntent(),önceki sayfalardan alınan verileri taşıyor. Buraya gelinen önceki Class'lar yukarıda yer almaktadır.
                 */
                //String current_city = getIntent().getStringExtra("current_city");//get from ActivityCities.java
                if(Static.CURRENT_CITY == null){
                    Toast.makeText(getApplicationContext(),getString(R.string.select_city),Toast.LENGTH_SHORT).show();
                    return;
                }
                Static.CURRENT_CITY = textView_CurrentCity.getText().toString();
                Intent intent = new Intent(getApplicationContext(),ActivityAutomationSettings.class);
                intent.putExtra(BetweenActivities.position,Static.POSITION);
                intent.putExtra(BetweenActivities.for_what,Static.FOR_WHAT);
                intent.putExtra(BetweenActivities.item_name,getString(R.string.temperature));
                intent.putExtra(BetweenActivities.selected_value,compare +" " + degree);//tek parça olarak gönder.
                intent.putExtra(BetweenActivities.current_city,Static.CURRENT_CITY);
                intent.putExtra(BetweenActivities.device_type, DeviceType.TEMPERATURE);
                //clear for new:
                Static.POSITION = -1;
                Static.CURRENT_CITY = null;
                startActivity(intent);
            }
        });
        linearLayout_GoToCurrentCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo:go to City page:
                Static.PREVIOUS_CLASS = Static.WHERE_COME_FROM;//önceki sınıfı tut.
                Intent intent = new Intent(getApplicationContext(),ActivityCities.class);
                intent.putExtra(BetweenActivities.where_come_from,"ActivityTemperature.java");
                startActivity(intent);
            }
        });

        spinner_Compare.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                index_compare = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_Degree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                index_degree = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ///Event:@}

    }//onCreate

    private void deteremineItem_SpinnerCompare(String value){
            if(value.equals(getString(R.string.smaller_than))) {
                spinner_Compare.setSelection(0);
            }
            else if(value.equals(getString(R.string.equals))) {
                spinner_Compare.setSelection(1);
            }
            else if(value.equals(getString(R.string.greater_than))) {
                spinner_Compare.setSelection(2);
            }
    }
    private void determineItem_SpinnerDegree(ArrayAdapter<CharSequence> _dataAdapter_Degree,String value){
        for(int i=0;i<_dataAdapter_Degree.getCount();i++){
            if(_dataAdapter_Degree.getItem(i).equals(value)) {
                spinner_Degree.setSelection(i);
                break;
            }
        }
    }

    private void goToBack(){
        Intent intent = null;
        //String where_come_from = getIntent().getStringExtra("where_come_from");
        //MyLog.d("kerim[ActivityTemperature","where_come_from:" + WHERE_COME_FROM);
        if(Static.WHERE_COME_FROM.equals("ActivityTemperature.java"))
            Static.WHERE_COME_FROM = Static.PREVIOUS_CLASS;

        if(Static.WHERE_COME_FROM.equals("ActivityAutomationSelectCondition.java"))
            intent = new Intent(getApplicationContext(), ActivityAutomationSelectCondition.class);
        else if(Static.WHERE_COME_FROM.equals("ActivityAutomationSettings.java"))
            intent = new Intent(getApplicationContext(), ActivityAutomationSettings.class);
        intent.putExtra(BetweenActivities.for_what,Static.FOR_WHAT);
        startActivity(intent);
        Static.POSITION = -1;
        Static.CURRENT_CITY = null;

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
