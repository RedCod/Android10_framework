package com.example.trident.smart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.MyLog;
import com.example.trident.smart.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSmart.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentSmart#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressLint("ValidFragment")
public class FragmentSmart extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private int tab_index_for_tablayout_Smart = 0;
    @SuppressLint("ValidFragment")
    public FragmentSmart(int tabindex) {
        // Required empty public constructor
        tab_index_for_tablayout_Smart = tabindex;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSmart.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSmart newInstance(String param1, String param2) {
        FragmentSmart fragment = new FragmentSmart(0);
        //MyLog.d("kerim","FragmentSmart_2");
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    TabLayout tablayout_Smart;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_smart, container, false);
        ImageView imageView_GoToSortScenarioAndAutomationPage = (ImageView)view.findViewById(R.id.imageView_GoToSortScenarioAndAutomationPage);
        Button button_btnPlus_GoToAddScenarioPage             = (Button)view.findViewById(R.id.button_btnPlus_GoToAddScenarioPage);
        /*TabLayout*/ tablayout_Smart                         = (TabLayout)view.findViewById(R.id.tablayout_Smart);
        ViewPager viewPager_Smart                             = (ViewPager)view.findViewById(R.id.viewPager_Smart);

        Fragment[] childFragments = new Fragment[2];
        String[] tablayout_tab_names = {getString(R.string.scene),getString(R.string.automation)}; //TabLayout Tab1,Tab2
        /*
        childFragments[0] = new Fragment_FragmentSmart_BlankScenarioContent();
        childFragments[1] = new Fragment_FragmentSmart_ScenarioList();
        */
        boolean is_have_scenario = true;//todo: this value get from database.
        boolean is_have_automation = true;//todo: this value get from database.
        if(is_have_scenario == false)
            childFragments[0] = new Fragment_FragmentSmart_BlankScenarioContent();
        else
            childFragments[0] = new Fragment_FragmentSmart_ScenarioList();

        if(is_have_automation == false)
            childFragments[1] = new Fragment_FragmentSmart_BlankAutomationContent();
        else
            childFragments[1] = new Fragment_FragmentSmart_AutomationList();

       // tablayout_Smart.setTabGravity(TabLayout.GRAVITY_CENTER);
        tablayout_Smart.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager_Smart.setAdapter(new ViewPagerAdapter(getActivity().getSupportFragmentManager(),childFragments,tablayout_tab_names));
        tablayout_Smart.setupWithViewPager(viewPager_Smart);
        loadTabLayoutItems();

        //select tab: for "Scene
        TabLayout.Tab tab = tablayout_Smart.getTabAt(tab_index_for_tablayout_Smart);
        tab.select();
        //

        imageView_GoToSortScenarioAndAutomationPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: to go "Sort Scenario and Automation page" page.
                //SEXX

                int tab_position = tablayout_Smart.getSelectedTabPosition();
                Intent intent = new Intent(getContext().getApplicationContext(),ActivitySmartScenes.class);
                if(tab_position == 0)//if we're on the "Görünüm(Scene)" tab,:
                    intent.putExtra(BetweenActivities.SCENE_OR_AUTOMATION,BetweenActivities.SCENE);
                else//if we're on the "Otomasyon(Automation)" tab,://tab_position is "1":
                    intent.putExtra(BetweenActivities.SCENE_OR_AUTOMATION,BetweenActivities.AUTOMATION);
                startActivity(intent);
            }
        });


        button_btnPlus_GoToAddScenarioPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tab_position = tablayout_Smart.getSelectedTabPosition();
                if(tab_position == 0){//if we're on the "Görünüm(Scene)" tab,:
                    Intent intent = new Intent(getContext().getApplicationContext(),ActivitySmartSettings.class);
                    intent.putExtra("page_title",getString(R.string.smart_settings));
                    intent.putExtra("for_what","Add");
                    startActivity(intent);
                }else{//if we're on the "Otomasyon(Automation)" tab,:
                    //tab_position is "1":
                    //go to "ActivitySmartSettingsAutomation.java"
                    Intent intent = new Intent(getContext().getApplicationContext(),ActivityAutomationSettings.class);
                    intent.putExtra("page_title",getString(R.string.automation_add));
                    intent.putExtra("for_what","Add");
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void loadTabLayoutItems(){
        /**
         * load tabLayout items names.
         */
        //for test:
        tablayout_Smart.setTabMode(TabLayout.MODE_SCROLLABLE);
       // tablayout_Smart.addTab(tablayout_Smart.newTab().setText("Room1"));
       // tablayout_Smart.addTab(tablayout_Smart.newTab().setText("Room2"));
    }
}
