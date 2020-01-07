package com.example.trident.smart.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.trident.smart.R;
import java.util.List;

public class CustomListAdapter_MessageCenterSettings extends BaseAdapter {
    /**
     * using by "ActivityMessageCenterSettings.java
     *
     */
    private LayoutInflater layoutInflater;
    private Context context;
    private List listItem;
    private List listItemDesc;
    public  CustomListAdapter_MessageCenterSettings(Context context, List<String> listItem,List<String> listItemDesc){
        this.layoutInflater    = (LayoutInflater.from(context));
        this.context           = context;
        this.listItem     = listItem;
        this.listItemDesc = listItemDesc;
    }

    /**
     * get Item count
     * @return
     */
    @Override
    public int getCount() {
        return listItem.size();
    }

    /**
     * get Item content
     * @param i index
     * @return item
     */
    @Override
    public Object getItem(int i) {
        return listItem.get(i);
    }

    /**
     * Get Item desicription.(So second item...)
     * @param i index/position
     * @return
     */
    public Object getItemDesc(int i){return  listItemDesc.get(i);}

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.listview_twotextviewandarrow, null);
        TextView textView_Item     = (TextView)view.findViewById(R.id.textView_Item);
        TextView textView_ItemDesc = (TextView)view.findViewById(R.id.textView_ItemDesc);
        textView_Item.setText(listItem.get(i).toString());
        textView_ItemDesc.setText(listItemDesc.get(i).toString());

        return view;
    }
}
