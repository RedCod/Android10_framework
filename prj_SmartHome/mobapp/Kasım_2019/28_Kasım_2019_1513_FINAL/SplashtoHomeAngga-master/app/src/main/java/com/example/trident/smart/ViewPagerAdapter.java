package com.example.trident.smart;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    /**
     * used by "FragmentHome.java"
     */
    private Fragment[] childFragments = new Fragment[] {
            new Fragment_FragmentHome_RoomDevices(),
        new Fragment_FragmentHome_BlankRoomContent()

    };
    String[] room_names = {""}; //for tabLayout tab title. set room name.

    public ViewPagerAdapter(FragmentManager fm,Fragment[] fragments,String[] frag_names){
        super(fm);
        childFragments = fragments;
        room_names = frag_names;
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        return childFragments[position];
    }

    @Override
    public int getCount() {
        return childFragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = room_names[position];//getItem(position).getClass().getName();
        return title;//title.subSequence(title.lastIndexOf(".") + 1, title.length());
    }
}
