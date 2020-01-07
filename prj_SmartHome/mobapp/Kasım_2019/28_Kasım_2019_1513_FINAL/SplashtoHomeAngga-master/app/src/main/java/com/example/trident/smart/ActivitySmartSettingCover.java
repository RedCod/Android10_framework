package com.example.trident.smart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.MyLog;
import com.example.trident.common.SettingCover;

import java.util.Random;

public class ActivitySmartSettingCover extends AppCompatActivity {
    /**
     *called by "ActivitySmartSettings.java","ActivityAutomationSettings.java"
     *
     * Change cover Image.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_setting_cover);
        //
        LinearLayout linearLayout_Back = (LinearLayout)findViewById(R.id.linearLayout_Back);
        Button button_Back             = (Button)findViewById(R.id.button_Back);
        GridView gridView_Images       = (GridView)findViewById(R.id.gridView_Images);
        gridView_Images.setAdapter(new ImageAdapterGridView(getApplicationContext()));
        //
        linearLayout_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBack(-1);
            }
        });
        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBack(-1);
            }
        });

    }//onCreate

    public class ImageAdapterGridView extends BaseAdapter {
        private Context mContext;
        private SettingCover settingCover;

        public ImageAdapterGridView(Context c) {
            mContext = c;
            settingCover = new SettingCover();
        }

        public int getCount() {
            return settingCover.getCount();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final ImageView mImageView;
            if (convertView == null) {
                mImageView = new ImageView(mContext);
                mImageView.setLayoutParams(new GridView.LayoutParams(370, 160));
                mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mImageView.setPadding(16, 16, 16, 16);
            } else {
                mImageView = (ImageView) convertView;
            }
             mImageView.setImageResource(settingCover.getImage(position));
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animation myAnim = AnimationUtils.loadAnimation(mContext, R.anim.image_bounce);
                    mImageView.startAnimation(myAnim);
                    goToBack(position);
                }
            });
            return mImageView;
        }

    }//ImageAdapterGridView

    private void goToBack(int cover_image_id){
        Intent intent = null;
        String where_come_from = getIntent().getStringExtra(BetweenActivities.where_come_from);
        if(where_come_from !=null){
            if(where_come_from.equals("ActivitySmartSettings.java"))
            intent = new Intent(getApplicationContext(),ActivitySmartSettings.class);
            else if(where_come_from.equals("ActivityAutomationSettings.java"))
                intent = new Intent(getApplicationContext(),ActivityAutomationSettings.class);
            intent.putExtra(BetweenActivities.for_what,getIntent().getStringExtra(BetweenActivities.for_what));
            intent.putExtra("cover_image_id",cover_image_id);
            startActivity(intent);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            goToBack(-1);
        }
        else
            return super.onKeyDown(keyCode, event);
        return false;
    }
}
