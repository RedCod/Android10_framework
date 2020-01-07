package com.example.trident.smart;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.trident.common.MyLog;

import java.util.HashMap;
import java.util.List;

public class DynamicListViewAdapter extends ArrayAdapter<String> {

    final int INVALID_ID = -1;
    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
    Context context;

    public DynamicListViewAdapter(Context context, int layout_resource_id, List<String> objects) {
        super(context, layout_resource_id, objects);
        this.context = context;
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }
    /*private view holder class*/
    private class ViewHolder {
        TextView txtTitle;
    }
    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        String item = getItem(position).toString();
        return mIdMap.get(item);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        DynamicListViewAdapter.ViewHolder holder = null;
        //String rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_witharrowed, null);
            holder = new DynamicListViewAdapter.ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.textView_Item);
            convertView.setTag(holder);
        } else
            holder = (DynamicListViewAdapter.ViewHolder) convertView.getTag();

        holder.txtTitle.setText(getItem(position));
        //holder.imageView.setImageResource(rowItem.getImageId());

        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }

}
