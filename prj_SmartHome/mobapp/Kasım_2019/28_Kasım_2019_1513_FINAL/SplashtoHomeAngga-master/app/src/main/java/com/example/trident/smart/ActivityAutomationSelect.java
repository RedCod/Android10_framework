package com.example.trident.smart;

import android.content.ClipData;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.MyCustomObject;
import com.example.trident.common.MyLog;
import com.example.trident.smart.custom.CustomListAdapter_AutomationSelect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import static com.example.trident.common.MyCustomObject.*;

public class ActivityAutomationSelect extends AppCompatActivity {
    /**
     * called by "ActivitySmartSelectAction.java,ActivitySmartSettings.java"
     *
     * Sayfa: "Tetiklenecek Otomasyon" seçme.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automation_select);
        //
        LinearLayout linearLayout_Back = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back = (Button)findViewById(R.id.button_Back);
        Button button_Done = (Button)findViewById(R.id.button_Done);
        LinearLayout linearLayout_NoAutomationAvailable = (LinearLayout)findViewById(R.id.linearLayout_NoAutomationAvailable);
        ListView listView_AutomationList                = (ListView)findViewById(R.id.listView_AutomationList);
        ///TODO: thuse values get from the database. :@{
        String[] automation_items = new String[] { "Auto.item1", "Auto.item2", "Auto.item3",//representative data.
                "Auto.item4", "Auto.item5", "Auto.item6", "Auto.item7"};
        final CustomListAdapter_AutomationSelect customListAdapterAutomationSelect = new CustomListAdapter_AutomationSelect(getApplicationContext(),automation_items);
        listView_AutomationList.setAdapter(customListAdapterAutomationSelect);
        ///TODO: thuse values get from the database. :@}

        /*//go back:
        ActivitySmartSettings
        ActivitySmartSelectAction
         */
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
                //todo: save and go to "ActivitySmartSettings.java"
                String upper_class = getIntent().getStringExtra("upper_class");
                String where_from = getIntent().getStringExtra(BetweenActivities.where_come_from);
                String current_class ="";
                if(upper_class !=null)
                    current_class  = upper_class;
                else
                    current_class = where_from;

                //MyLog.d("kerim[ActivityAutomationSelect]","otomasyon->upper:" + upper_class +",where:"+ where_from);
                //note: whe are going to "upper_class":
                ArrayList<String> arrayList_Item = new ArrayList<String>();
                ArrayList<String> arrayList_CheckState = new ArrayList<String>();
                Iterator iterator_OK_Automation = customListAdapterAutomationSelect.getHashMap_SelectedItem().entrySet().iterator();
                Map.Entry<String, Boolean> pair;
                 while (iterator_OK_Automation.hasNext()){
                     /*Map.Entry<String, Boolean>*/ pair = (Map.Entry<String, Boolean>) iterator_OK_Automation.next();
                         arrayList_Item.add(pair.getKey());
                         if(pair.getValue())//is true
                            arrayList_CheckState.add("True");
                         else
                            arrayList_CheckState.add("False");
                 }
                Intent intent = null;
                if(current_class.equals("ActivitySmartSettings.java"))
                    intent = new Intent(getApplicationContext(),ActivitySmartSettings.class);
                else if(current_class.equals("ActivityAutomationSettings.java"))
                    intent = new Intent(getApplicationContext(),ActivityAutomationSettings.class);
                intent.putExtra(BetweenActivities.for_what,"Add_Automation");
                intent.putExtra(BetweenActivities.item_name,arrayList_Item.toArray(new String[0]));
                intent.putExtra(BetweenActivities.check_state,arrayList_CheckState.toArray(new String[0]));
                startActivity(intent);
            }
        });
        ///event:@}

    }//onCreate

    private void goToBack(){
        String where_from = getIntent().getStringExtra(BetweenActivities.where_come_from);
        MyLog.d("kerim","automationSelect:where_come_from:" + where_from);
        Intent intent= null;
        if(where_from != null){
          if(where_from.equals("ActivitySmartSelectAction.java"))
              intent = new Intent(getApplicationContext(),ActivitySmartSelectAction.class);
          else if(where_from.equals("ActivitySmartSettings.java")) {
              intent = new Intent(getApplicationContext(), ActivitySmartSettings.class);
              intent.putExtra(BetweenActivities.for_what,"Add_Automation");//Boş olarak geri dönersek de bu etiketi belirtelim.
          }else if(where_from.equals("ActivityAutomationSettings.java")) {
              intent = new Intent(getApplicationContext(), ActivityAutomationSettings.class);
              intent.putExtra(BetweenActivities.for_what,"Add_Automation");//Boş olarak geri dönersek de bu etiketi belirtelim.
          }
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
