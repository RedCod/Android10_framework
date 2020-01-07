package com.example.trident.smart.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.example.trident.common.MyLog;
import com.example.trident.smart.R;

import java.util.HashMap;

public class CustomListAdapter_ActivityDeviceLocation extends BaseAdapter { //used by "ActivityDeviceLocation.java"


    Context context;
    private String locations[];
    private String current_location="";
    LayoutInflater inflter;
    CheckedTextView[] checkedTextView_Array;
    public CustomListAdapter_ActivityDeviceLocation(Context _context,String[] _locations,String _current_location){
        inflter = (LayoutInflater.from(_context));
        this.context = _context;
        this.locations = _locations;
        this.current_location = _current_location;
        hashMap_SelectedLocations.clear();
        checkedTextView_Array = new CheckedTextView[locations.length];
    }


    @Override
    public int getCount() {
        return locations.length;
    }

    @Override
    public Object getItem(int i) {
        return locations[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private static HashMap<String, Integer> hashMap_SelectedLocations = new HashMap<String, Integer>();

    public View getView(final int _position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.listview_activityaddfamily, null);
        CheckedTextView checkedTextView_LocationSelect = (CheckedTextView)view.findViewById(R.id.checkedTextView_RoomSelect);
        checkedTextView_LocationSelect.setText(locations[_position]);
        if(locations[_position].equals(current_location))
            checkedTextView_LocationSelect.setChecked(true);
        else
            checkedTextView_LocationSelect.setChecked(false);

        if(checkedTextView_LocationSelect.isChecked() == true){//first loading.
            hashMap_SelectedLocations.put(locations[_position],_position);
        }/*else{
            MyLog.d("kerim","burası kaç kez çalıştı?");
            hashMap_SelectedLocations.remove(locations[_position]);
        }*/
        checkedTextView_Array[_position] = checkedTextView_LocationSelect;
        checkedTextView_LocationSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(((CheckedTextView) view).isChecked()){
                    ((CheckedTextView) view).setChecked(false);
                    hashMap_SelectedLocations.clear();
                    hashMap_SelectedLocations.put("",_position);//for control if length the other side.
                } else{
                    for(int i=0;i<checkedTextView_Array.length;i++)
                        checkedTextView_Array[i].setChecked(false);

                    ((CheckedTextView) view).setChecked(true);//CHECKED
                    hashMap_SelectedLocations.clear();
                    hashMap_SelectedLocations.put(locations[_position],_position);
                }
            }
        });
        return view;
    };////

    public static HashMap getSelectedRooms(){
        return hashMap_SelectedLocations;
    }
}
