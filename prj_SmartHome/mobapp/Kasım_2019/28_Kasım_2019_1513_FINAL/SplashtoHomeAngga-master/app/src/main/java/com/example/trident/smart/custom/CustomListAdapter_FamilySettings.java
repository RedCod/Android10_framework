package com.example.trident.smart.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trident.smart.R;
/*
public class CustomListAdapter_FamilySettings  extends ArrayAdapter<String> {

     // ListView(custom) for "ActivityFamilySettings.java"

    private final Activity activity;
    private final String[] itemname;
    private final Integer[] imgid;
    private final String[] desicription;
    private final Integer[] member_authority;

    public CustomListAdapter_FamilySettings(Activity _activity, String[] _itemname, Integer[] _imgid,String[] _description,Integer[] _member_authority) {
        super(_activity, R.layout.listview_fragment_home, _itemname);

        this.activity     = _activity;
        this.itemname     = _itemname;
        this.imgid        = _imgid;
        this.desicription = _description;
        this.member_authority = _member_authority;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater             = activity.getLayoutInflater();
        View rowView                        = inflater.inflate(R.layout.listview_family_settings, null,true);
        rowView.setMinimumHeight(100);
        TextView textView_title             = (TextView)rowView.findViewById(R.id.textView_Item);
        ImageView imageView                 = (ImageView)rowView.findViewById(R.id.imageView_Icon);
        TextView textView_description       = (TextView)rowView.findViewById(R.id.textView_ItemDescribtion);
        TextView textView_AdminOrNormalUser = (TextView)rowView.findViewById(R.id.textView_AdminOrNormalUser);

        textView_title.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);
        textView_description.setText(desicription[position]);
        if(member_authority[position] == 1)
            textView_AdminOrNormalUser.setText(activity.getString(R.string.administrator));
        else
            textView_AdminOrNormalUser.setText(activity.getString(R.string.user));


        return rowView;
    };
}*/
public class  CustomListAdapter_FamilySettings extends BaseAdapter {

    /**
     * used by "ActivityFamilySettings.java".  for ListView(custom) Item.
     *
     */

    private LayoutInflater inflater;
    private Context context;
    private  String[] itemname;
    private Integer[] imgid;
    private String[] desicription;
    private Integer[] member_authority;
    public CustomListAdapter_FamilySettings(Context context,String[] itemname,Integer[] imgid,String[] description,Integer[] member_authority){
        inflater = (LayoutInflater.from(context));
        this.context = context;
        this.itemname = itemname;
        this.imgid = imgid;
        this.desicription = description;
        this.member_authority = member_authority;
    }

    @Override
    public int getCount() {
        return itemname.length;
    }

    @Override
    public Object getItem(int i) {
        return itemname[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.listview_family_settings, null);
        TextView textView_title             = (TextView)view.findViewById(R.id.textView_Item);
        ImageView imageView                 = (ImageView)view.findViewById(R.id.imageView_Icon);
        TextView textView_description       = (TextView)view.findViewById(R.id.textView_ItemDescribtion);
        TextView textView_AdminOrNormalUser = (TextView)view.findViewById(R.id.textView_AdminOrNormalUser);

        textView_title.setText(itemname[i]);
        imageView.setImageResource(imgid[i]);
        textView_description.setText(desicription[i]);
        if(member_authority[i] == 1)
            textView_AdminOrNormalUser.setText(context.getString(R.string.administrator));
        else
            textView_AdminOrNormalUser.setText(context.getString(R.string.user));
        return view;
    }
}
