package com.example.trident.smart.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import java.util.Collection;
import java.util.HashMap;
import com.example.trident.smart.R;

public class CustomListAdapter_ActivityAddFamily extends BaseAdapter {

    //USED BY "ActivityAddFamily.java":

    //private final Activity activity;
    Context context;
    private String rooms[];
    LayoutInflater inflater;
    private Boolean checked[];
    boolean first_running = true;
    public CustomListAdapter_ActivityAddFamily(Context _context,String[] _rooms) {

        // this.activity = _activity;
        this.context = _context;
        this.rooms = _rooms;
        inflater = (LayoutInflater.from(_context));
        /////
        hashMap_SelectedRooms.clear();
        checked = new Boolean[_rooms.length];
        for(int i=0;i<_rooms.length;i++){
            checked[i] = true;
            hashMap_SelectedRooms.put(i,_rooms[i]);
        }

    }


    @Override
    public int getCount() {
        return rooms.length;
    }

    @Override
    public Object getItem(int i) {
        return rooms[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    private static HashMap<Integer,String> hashMap_SelectedRooms = new HashMap<Integer, String>();

    public View getView(final int _position, View view, ViewGroup parent) {
        view            = inflater.inflate(R.layout.listview_activityaddfamily, null);
        CheckedTextView checkedTextView_RoomSelect = (CheckedTextView)view.findViewById(R.id.checkedTextView_RoomSelect);
        checkedTextView_RoomSelect.setText(rooms[_position]);

         if(first_running == false)
             checkedTextView_RoomSelect.setChecked(checked[_position]);

        if(checkedTextView_RoomSelect.isChecked())//first loading.
            hashMap_SelectedRooms.put(_position,rooms[_position]);
        else
            hashMap_SelectedRooms.remove(_position);

        checkedTextView_RoomSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckedTextView) view).isChecked()){//UNCHECKED
                    ((CheckedTextView) view).setChecked(false);
                    hashMap_SelectedRooms.remove(_position);
                    checked[_position] = false;
                }
                else{
                    ((CheckedTextView) view).setChecked(true);//CHECKED
                    hashMap_SelectedRooms.put(_position,rooms[_position]);
                    checked[_position] = true;
                }
            }
        });
        first_running = false;
        return view;
    };////


    public Collection<String> getSelectedRooms(){
        return hashMap_SelectedRooms.values();
    }

}
