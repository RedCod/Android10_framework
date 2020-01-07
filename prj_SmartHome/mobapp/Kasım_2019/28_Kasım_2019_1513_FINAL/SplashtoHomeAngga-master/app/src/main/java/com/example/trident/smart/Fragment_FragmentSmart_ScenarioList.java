package com.example.trident.smart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.trident.smart.custom.CustomListAdapter_FragmentSmart_ScenarioList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_FragmentSmart_ScenarioList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_FragmentSmart_ScenarioList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_FragmentSmart_ScenarioList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static String TAG = "[Fragment_FragmentSmart_ScenarioList]";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
   /* public Fragment_FragmentSmart_ScenarioList() {
        // Required empty public constructor
    }*/


    @SuppressLint("ValidFragment")
    public Fragment_FragmentSmart_ScenarioList(){

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_FragmentHome_RoomDevices.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_FragmentSmart_ScenarioList newInstance(String param1, String param2) {
        Fragment_FragmentSmart_ScenarioList fragment = new Fragment_FragmentSmart_ScenarioList();
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


    //todo: get Scenario Name from database.
    String[] item = {
      "item1",
      "item2",
      "item3",
            "item4",
            "item5",
            "item6",
            "item7"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_smart__scenario_list, container, false);
        ListView listView_ScenarioList = (ListView)view.findViewById(R.id.listView_ScenarioList);
        CustomListAdapter_FragmentSmart_ScenarioList customListAdapterFragmentSmartScenarioList = new CustomListAdapter_FragmentSmart_ScenarioList(getActivity(),item,listView_ScenarioList/*for scrolll*/);
        listView_ScenarioList.setAdapter(customListAdapterFragmentSmartScenarioList);
        return  view;
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


    public void onFragmentInteraction(Uri uri) {

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
