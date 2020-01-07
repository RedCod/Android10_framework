package com.example.trident.common;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.trident.smart.RecyclerViewAdapter_AutomationSettingsItem;

public class GUI {
    /**
     * using by:
     * -ActivitySmartSettings.java
     * -ActivityAutomationSettings.java
     * -ActivityMessageCenterSettings.java
     * -CustomListAdapter_DoNotDisturbDevices.java
     * -ActivityHelpCenter.java
     */

    public GUI(){

    }


    /**
     * Dinamik olarak(barındırdığı veri satır sayısı kadar) LinearLayoutlar'ı boyutlandır.
     * @param linearLayout
     * @param listView
     * @param baseAdapter
     * @param count //liste içindeki item saysısı.
     * @param fix_size //boyutlandırma birim sabit değeri.
     */
    public void dynamicallySizingLayout(LinearLayout linearLayout/*linearLayout_*/,
                                         ListView listView,
                                         BaseAdapter baseAdapter /*customListAdapter*/,
                                         int count,final int fix_size){
        /**
         * FIXME: BU FONKSİYONUN OLDUĞU TÜM LAYOUTLARI TEK YERDEN ÇAĞIR.
         */
        ///for Layout Resize:@{
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) linearLayout.getLayoutParams();
        final int dimension = count;//90;//ATTENTION:do not touch this value. default = 90
        int total_height = fix_size;//((count < 20 )? 45 : -45);//dimension;
        int item_count = count;
        View item;
        for(int i=0; i<item_count;i++){
            item = baseAdapter.getView(i,null,listView);
            item.measure(0,0);
            total_height += item.getMeasuredHeight();
        }
        layoutParams.height = total_height + (1 * (item_count));
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.requestLayout();
        layoutParams = (ConstraintLayout.LayoutParams) linearLayout.getLayoutParams();
        ///for Layout Resize:@}

        ///for Listview resize:@{
        ViewGroup.LayoutParams params2 = listView.getLayoutParams();
        params2.height = layoutParams.height;
        listView.setLayoutParams(params2);
        listView.requestLayout();
        ////for Listview resize:@}
    }


    /**
     *  Dinamik olarak(barındırdığı veri satır sayısı kadar) RecyclerView'ı boyutlandır.
     * @param linearLayout
     * @param recyclerView_ActionsList
     * @param recyclerViewAdapter
     * @param count //liste içindeki item saysısı.
     * @param fix_size //boyutlandırma birim sabit değeri.
     */
    public void dynamicallySizingLayout(LinearLayout linearLayout/*linearLayout_*/,
                                        RecyclerView recyclerView_ActionsList,
                                        RecyclerView.Adapter<RecyclerViewAdapter_AutomationSettingsItem.MyViewHolder> recyclerViewAdapter /*customListAdapter*/,
                                        int count,final int fix_size){
        ///for Layout Resize:@{
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) linearLayout.getLayoutParams();
        final int dimension = count;//90;//ATTENTION:do not touch this value. default = 90
        int total_height = fix_size;//((count < 20 )? 45 : -45);//dimension;
        int item_count = count;
        RecyclerViewAdapter_AutomationSettingsItem.MyViewHolder item;
        for(int i=0; i<item_count;i++){
            item = recyclerViewAdapter.onCreateViewHolder(recyclerView_ActionsList,0);
            item.linearLayout_MainItemBase.measure(0,0);
            total_height += item.linearLayout_MainItemBase.getMeasuredHeight();
        }
        layoutParams.height = total_height + (1 * (item_count));
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.requestLayout();
        layoutParams = (ConstraintLayout.LayoutParams) linearLayout.getLayoutParams();
        ///for Layout Resize:@}

        ///RecyclerView Resize:@{
        ViewGroup.LayoutParams params2 = recyclerView_ActionsList.getLayoutParams();
        params2.height = layoutParams.height; //same height.
        recyclerView_ActionsList.setLayoutParams(params2);
        recyclerView_ActionsList.requestLayout();
        ///RecyclerView Resize:@}
    }
}
