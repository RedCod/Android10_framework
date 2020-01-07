package com.example.trident.smart;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.example.trident.smart.R;

/**
 * This application creates a listview where the ordering of the data set
 * can be modified in response to user touch events.
 *
 * An item in the listview is selected via a long press event and is then
 * moved around by tracking and following the movement of the user's finger.
 * When the item is released, it animates to its new position within the listview.
 */
public class ListViewDraggingAnimation extends Activity {
          //TODO: !!!!!!!!!!!!!!!!!!!!! IDLE !!!!!!!!!!!!!!!!!!!!!!!!!!!  DELETE THIS FILE.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_listview);

        String[] sCheeseStrings = { "item 1", "item 2",
                "item 3", "item 4", "item 5", "item 6", "item 7", "item 8",
                "item 9", "item 10", "item 11", "item 12", "item 13", "item 14",
                "item 15" };
  /*       final Integer[] images = { R.drawable.arrow_more,
                R.drawable.arrow_more, R.drawable.arrow_more, R.drawable.arrow_more };
*/
        ArrayList<String>mCheeseList = new ArrayList<String>();
        for (int i = 0; i < sCheeseStrings.length; ++i) {
            mCheeseList.add(sCheeseStrings[i]);
        }


        DynamicListViewAdapter adapter = new DynamicListViewAdapter(this, R.layout.listview_witharrowed, mCheeseList);
        DynamicListView listView = (DynamicListView) findViewById(R.id.listView_DynamicListView);

        listView.setCheeseList(mCheeseList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
}
