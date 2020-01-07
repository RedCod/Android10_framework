package com.example.trident.smart.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.trident.smart.R;

import java.util.List;

public class CustomListAdapter_HelpCenter extends BaseAdapter {

    /**
     * using by "ActivityHelpCenter.java"
     *
     */

    private LayoutInflater layoutInflater;
    private Context context;
    private List<String> listItem;
    public CustomListAdapter_HelpCenter(Context context, List<String> listItem){
        this.layoutInflater    = (LayoutInflater.from(context));
        this.context           = context;
        this.listItem          = listItem;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int i) {
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.listview_textviewandarrow, null);
        TextView textView_Item = (TextView)view.findViewById(R.id.textView_Item);
        textView_Item.setText(listItem.get(i));

        return view;
    }

}
