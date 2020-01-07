package com.example.trident.smart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.trident.common.BetweenActivities;
import com.example.trident.smart.custom.CustomListAdapter_WithArrowed;

public class ActivityHomeManagement extends AppCompatActivity {
/**
 * "Ev Yönetimi"
 * called by "FragmentHome.java" and "FragmentMe.java"
 *
 */

    /** TODO: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! CAOUTION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *      if the user is "Administrator",can use this page. But else,can not use this page.
     *      + CAN NOT EDIT AND REMOVE. CAN ONLY DISPLAYED!
     *
     */
    private static int scroll_position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_management);

        LinearLayout linearLayout_Back = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back = (Button)findViewById(R.id.button_Back);
        final ListView listView_Families = (ListView)findViewById(R.id.listView_Families);
        Button button_GoToAddFamily = (Button)findViewById(R.id.button_GoToAddFamily);

        ///TODO: "families" array for test. this array content,get from database.
        final String[] families = {"Aile","Aile","Aile","Aile","Aile","Aile","Aile","Aile","Aile","Aile","Aile","Aile","Aile","Aile","Aile","Aile","Aile","Aile"};
        //ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, families);
        final CustomListAdapter_WithArrowed customListAdapterWithArrowed = new CustomListAdapter_WithArrowed(getApplicationContext(),families);
            listView_Families.setAdapter(customListAdapterWithArrowed);
        customListAdapterWithArrowed.notifyDataSetChanged();
        listView_Families.smoothScrollToPosition(scroll_position);
///Back:@{
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
///Back:@}
        listView_Families.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int _position, long l) {
                //goin to "ActivityFamilySettings.java" page.
                scroll_position = _position;
                Intent intent = new Intent(getApplicationContext(),ActivityFamilySettings.class);
                //intent.putExtra("Family_Name",listView_Families.getItemAtPosition(_position).toString().trim());
                ActivityFamilySettings.FAMILY_NAME = customListAdapterWithArrowed.getItem(_position).toString();//listView_Families.getItemAtPosition(_position).toString().trim();
                startActivity(intent);
            }
        });

        button_GoToAddFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to add family page.
                Intent intent = new Intent(getApplicationContext(),ActivityAddFamily.class);
                startActivity(intent);
            }
        });

    }//onCreate
    //Todo:block BACK_KEY in "Home Management" page.


    private  void goToBack(){
        String where_come_from = getIntent().getStringExtra(BetweenActivities.where_come_from);
        Intent intent = new Intent(getApplicationContext(),ActivityMainPage.class);
        if(where_come_from !=null){
            if(where_come_from.equals("FragmentHome.java"))
                intent.putExtra(BetweenActivities.where_come_from,"ActivityHomeManagement.java:HOME");//HOME for select HOME tab.
            else if(where_come_from != null && where_come_from.equals("FragmentMe.java"))
                intent.putExtra(BetweenActivities.where_come_from,"ActivityHomeManagement.java:ME");//ME for select ME tab.
        }

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
