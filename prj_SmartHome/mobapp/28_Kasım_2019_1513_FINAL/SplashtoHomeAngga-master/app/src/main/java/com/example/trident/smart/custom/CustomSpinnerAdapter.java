package com.example.trident.smart.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trident.common.MyLog;
import com.example.trident.common.Preferences;
import com.example.trident.smart.R;

public class CustomSpinnerAdapter extends BaseAdapter {
    private static String TAG = "[CustomSpinnerAdapter]";
    Context context;
    int item_icons_tick            = R.drawable.tick_b; //tick
    int item_icons_home_management = R.drawable.settings3;

    String[] families;
    LayoutInflater inflter;
    ImageView[]  imageViews;
    boolean is_first_start = false;//default

    public CustomSpinnerAdapter(Context applicationContext,String[] _families) {
        this.context = applicationContext;
        //this.item_icons = _item_icons;
        this.families  = _families;
        inflter = (LayoutInflater.from(applicationContext));
        is_first_start = true;//yes
        //FragmentHome.selection(PREVIOUS_POSITION);
        //MyLog.d(TAG,"GELBANA:" +PREVIOUS_POSITION);
    }

    @Override
    public int getCount() {
        return families.length; //item_icons.length;
    }

    @Override
    public Object getItem(int i) {
        return families[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public static int PREVIOUS_POSITION = -1;
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        view = inflter.inflate(R.layout.spinner_fragment_home, null);
        final ImageView  imageViewIcon        = (ImageView) view.findViewById(R.id.imageView_spinner);
        final TextView textView = (TextView) view.findViewById(R.id.textView_spinner);
        textView.setText(families[position]);
        if(is_first_start== false && PREVIOUS_POSITION == position){
            imageViewIcon.setVisibility(View.VISIBLE);
        }
        is_first_start = false;
        return view;
    }

   /* @Override
    public View getDropDownView(int position, View convertView,final ViewGroup parent) {
        //MyLog.d(TAG,"is null mu:" + convertView +",position:" + position);
        if(convertView !=null){

        }
        return super.getDropDownView(position, convertView, parent);
    }*/
}
