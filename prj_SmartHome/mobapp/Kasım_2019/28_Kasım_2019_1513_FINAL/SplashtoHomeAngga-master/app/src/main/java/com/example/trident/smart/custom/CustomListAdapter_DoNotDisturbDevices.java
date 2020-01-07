package com.example.trident.smart.custom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import java.util.List;

import com.example.trident.smart.R;


public class CustomListAdapter_DoNotDisturbDevices extends BaseAdapter {

    /**
     * used by "ActivityDoNotDisturbDevices.java"
     *
     */


    public CustomListAdapter_DoNotDisturbDevices(){

    }

    private Context context;

    //static because we call in "ActivityTimeSegment.java"
    private static List<String> list_Item;
    private static List<Boolean> list_State;
    private LayoutInflater layoutInflater;
    public CustomListAdapter_DoNotDisturbDevices(Context context, List<String> listItem,List<Boolean> listState){
        layoutInflater  = (LayoutInflater.from(context));
        this.context    = context;
        this.list_Item  = listItem;
        this.list_State = listState;
    }

    @Override
    public int getCount() {
        return list_Item.size();
    }

    @Override
    public Object getItem(int i) {
        return list_Item.get(i);
    }

    /**
     * get "list_State" content.
     * @param i
     * @return
     */
    public Boolean getItemState(int i){
        return list_State.get(i);
    }

    /**
     * we call in "ActivityTimeSegment.java"
     * get "list_Item" content.
     * @return
     */
    public List<String> getListItemContent(){
        return list_Item;
    }

    /**
     * we call in "ActivityTimeSegment.java"
     * get "list_State" content.
     * @return
     */
    public List<Boolean>getListStateContent(){
        return list_State;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.listview_donotdisturbdevices, null);
        final CheckedTextView checkedTextView_DeviceName = (CheckedTextView)view.findViewById(R.id.checkedTextView_DeviceName);
         checkedTextView_DeviceName.setText(list_Item.get(i));
         checkedTextView_DeviceName.setChecked(list_State.get(i));
         if(checkedTextView_DeviceName.isChecked()) {
             checkedTextView_DeviceName.setTextColor(context.getResources().getColor(R.color.color_Black));
             checkedTextView_DeviceName.setTypeface(checkedTextView_DeviceName.getTypeface(), Typeface.BOLD);
         }else {
             checkedTextView_DeviceName.setTextColor(context.getResources().getColor(R.color.color_Gray));
             checkedTextView_DeviceName.setTypeface(checkedTextView_DeviceName.getTypeface(), Typeface.NORMAL);
         }

         checkedTextView_DeviceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckedTextView) view).isChecked()){//if is checked,make this unchecked:
                    ((CheckedTextView) view).setChecked(false);
                    ((CheckedTextView) view).setTextColor(context.getResources().getColor(R.color.color_Gray));
                    ((CheckedTextView) view).setTypeface(null,Typeface.NORMAL);
                    //..
                    //..todo:update in database.
                    list_State.set(i,false);//update "list_State"
                }
                else{//if is unchecked,make this checked:
                    ((CheckedTextView) view).setChecked(true);//CHECKED
                    ((CheckedTextView) view).setTextColor(context.getResources().getColor(R.color.color_Black));
                    ((CheckedTextView) view).setTypeface(null, Typeface.BOLD);
                    //..
                    //..todo:update to database.
                    list_State.set(i,true);//update "list_State"
                }
            }
        });

        return view;
    }

}
