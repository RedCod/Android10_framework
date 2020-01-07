package com.example.trident.smart.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.trident.common.MyLog;
import com.example.trident.smart.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomListAdapter_WithArrowed extends BaseAdapter {

    final int INVALID_ID = -1;
     String[] rooms;
     Context context;
    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
    List<String> mylist = new ArrayList<String>();
    public  CustomListAdapter_WithArrowed(Context _context, String[] _rooms){
        this.context = _context;
        this.rooms = _rooms;
        inflter = (LayoutInflater.from(_context));
        //for test
       // gel(_rooms);
    }
/*
    public void gel(String[] room){
        for (int i = 0; i < room.length; ++i) {
            mIdMap.put(room[i], i);
            mylist.add(room[i]);
        }
    }*/
    LayoutInflater inflter;
    @Override
    public int getCount() {
        return rooms.length;
    }

    @Override
    public Object getItem(int i) {
        return rooms[i];
        //return null;
    }

    @Override
    public long getItemId(int position) {
       /* if (position < 0 || position >= rooms.length) {
            return INVALID_ID;
        }
        String item = getItem(position).toString();
        return mIdMap.get(item);*/
       return position;
    }

    /*

      public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        String item = getItem(position).toString();
        return mIdMap.get(item);
    }


     */


    @Override
    public View getView(int _position, View view, ViewGroup viewGroup) {
        view                   = inflter.inflate(R.layout.listview_witharrowed, null);
        TextView textView_Item = (TextView)view.findViewById(R.id.textView_Item);
        textView_Item.setText(rooms[_position]);
        return view;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }
}
