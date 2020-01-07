package com.example.trident.smart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    /**
     * * !!!!!!!!! NOT USED !!!!!!!!!!!!!!!!!!!!!!!!!!
     * * !!!!!!!!! NOT USED !!!!!!!!!!!!!!!!!!!!!!!!!!
     * * !!!!!!!!! NOT USED !!!!!!!!!!!!!!!!!!!!!!!!!!
     * * !!!!!!!!! NOT USED !!!!!!!!!!!!!!!!!!!!!!!!!!
     * * !!!!!!!!! NOT USED !!!!!!!!!!!!!!!!!!!!!!!!!!
     * * !!!!!!!!! NOT USED !!!!!!!!!!!!!!!!!!!!!!!!!!
     * * !!!!!!!!! NOT USED !!!!!!!!!!!!!!!!!!!!!!!!!!
     * * !!!!!!!!! NOT USED !!!!!!!!!!!!!!!!!!!!!!!!!!
     * * !!!!!!!!! NOT USED !!!!!!!!!!!!!!!!!!!!!!!!!!
     * * !!!!!!!!! NOT USED !!!!!!!!!!!!!!!!!!!!!!!!!!
     *
     *
     * */


    RecyclerView recyclerView;
    RecyclerViewAdapter_RoomSettingsDeviceItem mAdapter;
    ArrayList<String> stringArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = findViewById(R.id.recyclerView);

        //populateRecyclerView();
    }
    private void populateRecyclerView() {
        stringArrayList.add("Item 1");
        stringArrayList.add("Item 2");
        stringArrayList.add("Item 3");
        stringArrayList.add("Item 4");
        stringArrayList.add("Item 5");
        stringArrayList.add("Item 6");
        stringArrayList.add("Item 7");
        stringArrayList.add("Item 8");
        stringArrayList.add("Item 9");
        stringArrayList.add("Item 10");

        //mAdapter = new RecyclerViewAdapter(stringArrayList);

        ItemTouchHelper.Callback callback = new ItemMoveCallback_RoomSettingsDeviceItem(mAdapter);
        ItemTouchHelper touchHelper       = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        stringArrayList.add("kerim fırat");
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
    }

}
