package com.example.trident.smart.custom;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trident.smart.R;

import java.util.HashMap;
import java.util.List;

public class CustomListViewAdapter extends ArrayAdapter<String> {
   //FOR TEST
    Context context;
    final int INVALID_ID = -1;

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
    public CustomListViewAdapter(Context context, int resourceId, List<String> items) {
        super(context, resourceId, items);
        this.context = context;

        for (int i = 0; i < items.size(); ++i) {
            mIdMap.put(items.get(i), i);
        }
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtTitle;
    }

    @Override
    public long getItemId(int position) {
       // return super.getItemId(position);
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        String item = getItem(position).toString();
        return mIdMap.get(item);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        String rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_witharrowed, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.textView_Item);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtTitle.setText(getItem(position));
        //holder.imageView.setImageResource(rowItem.getImageId());

        return convertView;
    }
}
