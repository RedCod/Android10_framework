package com.example.trident.smart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.MyLog;

import java.util.ArrayList;
import java.util.Arrays;

public class ActivitySmartScenes extends AppCompatActivity {

    /**
     * "Akıllı Görünümler"
     * called in "FragmentSmart.java" for "Görünüm" and "Otomasyon" list and sort.
     *
     */
    RecyclerView recyclerView_Items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_scenes);
        ///
        LinearLayout linearLayout_Back = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back = (Button)findViewById(R.id.button_Back);
        Button button_Done = (Button)findViewById(R.id.button_Done);
        /*RecyclerView*/ recyclerView_Items = (RecyclerView)findViewById(R.id.recyclerView_Items);
        ///
        String getintent = getIntent().getStringExtra(BetweenActivities.SCENE_OR_AUTOMATION);
        if(getintent.equals(BetweenActivities.SCENE))
            listScenes();
        else if(getintent.equals(BetweenActivities.AUTOMATION))
            listAutomations();


        ///Events:@{
        linearLayout_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBack(getIntent().getStringExtra(BetweenActivities.SCENE_OR_AUTOMATION));
            }
        });
        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBack(getIntent().getStringExtra(BetweenActivities.SCENE_OR_AUTOMATION));
            }
        });
        button_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: DONE. Update in database. yeni items sıralamalarını veritabanında güncelle.
                ///.
                ArrayList<String> arrayList = recyclerViewAdapter.getItemsAsArrayList();
                for(String as:arrayList)
                    MyLog.d("kerim[ActivitySmartScenes]","item:" + as);

                //and go back:
            }
        });
        ///events:@}

    }//onCreate

    RecyclerViewAdapter_SmartScenesAndAutomationItem recyclerViewAdapter;
    private void listScenes(){
        //todo: "Görünüm(senaryoları)" içeriklerini listele. get from database.
        String[] scenes = {"item1","item2","item3","item4","item5","item6","item7","item8","item9","item10","item11","item12","item13","item14"};
        ArrayList<String> arrayList_Scenes = new ArrayList<>(Arrays.asList(scenes));
        setAdapter(arrayList_Scenes);
    }
    private void listAutomations(){
        //todo: "Otomasyon(otomasyonları)" içeriklerini listele. get from database.
        String[] scenes = {"Auto.item1","Auto.item2","Auto.item3","Auto.item4","Auto.item5","Auto.item6","Auto.item7","Auto.item8","Auto.item9","Auto.item10",
                "Auto.item11","Auto.item12","Auto.item13","Auto.item14"};
        ArrayList<String> arrayList_Automation = new ArrayList<>(Arrays.asList(scenes));
       setAdapter(arrayList_Automation);
    }

    /**
     *
     * @param arrayList content "Scenes" or "Automation" items.
     */
    private void setAdapter(ArrayList<String> arrayList){
        /*RecyclerViewAdapter_SmartScenesAndAutomationItem*/ recyclerViewAdapter = new RecyclerViewAdapter_SmartScenesAndAutomationItem(this,arrayList);
        ItemTouchHelper.Callback callback = new ItemMoveCallback_SmartScenesAndAutomationItem(recyclerViewAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView_Items);
        recyclerView_Items.setAdapter(recyclerViewAdapter);
    }

    /**
     *
     * @param which  content:"SCENE" OR "AUTOMATION"
     */
    private void goToBack(String which){
        Intent intent = new Intent(getApplicationContext(),ActivityMainPage.class);
        intent.putExtra(BetweenActivities.where_come_from,"ActivitySmartScenes.java:SMART");
        intent.putExtra(BetweenActivities.SCENE_OR_AUTOMATION,which); //SCENE OR AUTOMATION
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            goToBack(getIntent().getStringExtra(BetweenActivities.SCENE_OR_AUTOMATION));
        }
        else
            return super.onKeyDown(keyCode, event);
        return false;
    }
}
