package com.example.trident.smart;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.MyLog;
import com.example.trident.smart.R;

import org.w3c.dom.Text;

import java.io.LineNumberReader;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMe.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMe extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentMe() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMe.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMe newInstance(String param1, String param2) {
        FragmentMe fragment = new FragmentMe();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_me, container, false);

        LinearLayout linearLayout_GoToPersonalCenter   = (LinearLayout)view.findViewById(R.id.linearLayout_GoToPersonalCenter);
        TextView textView_EmailOrPhone                 = (TextView)view.findViewById(R.id.textView_EmailOrPhone);
        LinearLayout linearLayout_GoToHomeManagement   = (LinearLayout)view.findViewById(R.id.linearLayout_GoToHomeManagement);
        LinearLayout linearLayout_GoToMessageCenter    = (LinearLayout)view.findViewById(R.id.linearLayout_GoToMessageCenter);
        LinearLayout linearLayout_GoToHelpCenter       = (LinearLayout)view.findViewById(R.id.linearLayout_GoToHelpCenter);
        LinearLayout linearLayout_GoToMoreServicesPage = (LinearLayout)view.findViewById(R.id.linearLayout_GoToMoreServicesPage);
        LinearLayout linearLayout_GoToSettingsPage     = (LinearLayout)view.findViewById(R.id.linearLayout_GoToSettingsPage);

        textView_EmailOrPhone.setText("tridenturetim@gmail.com");//todo: get "email" or "phone number" value from database.

        ///Events:@{
         linearLayout_GoToPersonalCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPersonalCenterPage();
            }
        });
        linearLayout_GoToHomeManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHomeManagementPage();
            }
        });
        linearLayout_GoToMessageCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMessageCenterPage();
            }
        });
        linearLayout_GoToHelpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHelpCenterPage();
            }
        });
        linearLayout_GoToMoreServicesPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMoreServicesPage();
            }
        });
        linearLayout_GoToSettingsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSettingsPage();
            }
        });
        ///Events:@}

        return view;
    }

///FUNCTIONS-START:@{
    private void goToPersonalCenterPage(){
        Intent intent = new Intent(getContext().getApplicationContext(),ActivityPersonalCenter.class);
        startActivity(intent);
    }

    private void goToHomeManagementPage(){
        Intent intent = new Intent(getContext().getApplicationContext(),ActivityHomeManagement.class);
        intent.putExtra(BetweenActivities.where_come_from,"FragmentMe.java");
        startActivity(intent);
    }
    private void goToMessageCenterPage(){
        Intent intent = new Intent(getContext().getApplicationContext(),ActivityMessageCenter.class);
        startActivity(intent);
    }

    private void goToHelpCenterPage(){
        Intent intent = new Intent(getContext().getApplicationContext(),ActivityHelpCenter.class);
        startActivity(intent);
    }
    private void goToMoreServicesPage(){
        /**
         * TODO: V2
         */
        Toast.makeText(getContext().getApplicationContext(), "it will be completed in version_2", Toast.LENGTH_SHORT).show();
    }
    private void goToSettingsPage(){
        Intent intent = new Intent(getContext().getApplicationContext(),ActivitySettings.class);
        startActivity(intent);
    }
///FUNCTIONS-END:@}



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
}
