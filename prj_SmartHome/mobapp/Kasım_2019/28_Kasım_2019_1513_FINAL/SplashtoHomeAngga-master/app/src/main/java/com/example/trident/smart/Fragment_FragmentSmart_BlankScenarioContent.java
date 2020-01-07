package com.example.trident.smart;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_FragmentSmart_BlankScenarioContent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_FragmentSmart_BlankScenarioContent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_FragmentSmart_BlankScenarioContent extends Fragment { //used by "FragmentSmart.java"
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public Fragment_FragmentSmart_BlankScenarioContent() {
        // Required empty public constructor
    }

   /* @SuppressLint("ValidFragment")
    public Fragment_FragmentSmart_BlankScenarioContent(String _room_name,String[] _device_names){
    }*/

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_FragmentHome_BlankRoomContent.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_FragmentSmart_BlankScenarioContent newInstance(String param1, String param2) {
        Fragment_FragmentSmart_BlankScenarioContent fragment = new Fragment_FragmentSmart_BlankScenarioContent();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_fragment_smart__blank_scenario_content, container, false);
        Button button_GoToAddScenarioPage = (Button)view.findViewById(R.id.button_GoToAddScenarioPage);
        button_GoToAddScenarioPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to "add scenario" page:
                Toast.makeText(getContext(),"go to add scenario page.",Toast.LENGTH_SHORT).show();
                //todo: go to "add scenario" page:
            }
        });
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

