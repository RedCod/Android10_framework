package com.example.trident.smart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trident.common.MyLog;
import com.example.trident.smart.custom.CustomListAdapter;


/**
 * //called by "FragmentHome.java","ActivitySmartSelectAction.java"
 *Anasayfada Odalara ait cihazların listelenme işleminin gerçekleştiği Fragment.
 *
 *
 */

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_FragmentHome_RoomDevices.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_FragmentHome_RoomDevices#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_FragmentHome_RoomDevices extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static String TAG = "[Fragment_FragmentHome_RoomDevices]";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Context context;
    private String UPPER_CLASS ="";
    private String WHO_USES ="";

    private OnFragmentInteractionListener mListener;

    public Fragment_FragmentHome_RoomDevices() {
        // Required empty public constructor
    }


    /*@SuppressLint("ValidFragment")
    public Fragment_FragmentHome_RoomDevices(String _room_name,String[] _device_names){
        //MyLog.d(TAG,"ROOM NAME:" +_room_name);
        device_names = _device_names;
      // this.context = _context;
    }*/



    @SuppressLint("ValidFragment")
    public Fragment_FragmentHome_RoomDevices( String who_uses, String[] devices_type,String[] _device_names,String[] devices_state){
        //MyLog.d(TAG,"ROOM NAME:" +_room_name);
        this.WHO_USES = who_uses;
        this.device_names = _device_names;
        this.devices_type = devices_type;
        this.devices_state = devices_state;
        // this.context = _context;
    }
    @SuppressLint("ValidFragment")
    public Fragment_FragmentHome_RoomDevices(String where_come_from, String who_uses, String[] devices_type,String[] device_names,String[] devices_state,String[] devices_location,String for_what){
        //MyLog.d(TAG,"ROOM NAME:" +_room_name);
        this.UPPER_CLASS      = where_come_from;
        this.WHO_USES         = who_uses;
        this.device_names     = device_names;
        this.devices_type     = devices_type;
        this.devices_state    = devices_state;
        this.devices_location = devices_location;
        this.FOR_WHAT         = for_what;
        //MyLog.d("kerim[ROOMd]","FORWHAT:" + FOR_WHAT);
        // this.context = _context;

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
    public static Fragment_FragmentHome_RoomDevices newInstance(String param1, String param2) {
        Fragment_FragmentHome_RoomDevices fragment = new Fragment_FragmentHome_RoomDevices();
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
    ListView listView_DevicesInRoom;
    private String FOR_WHAT ="";
    String[] device_names = {""};
    String[] devices_type = {""};
    String[] devices_state = {""};//state of Device. (online/offline)
    String[] devices_location = {""};

    private static int scroll_position = 0;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_home_roomdevices, container, false);
        CustomListAdapter adapter=new CustomListAdapter(getActivity(), devices_type,device_names,devices_state);//FIXME TODO
        listView_DevicesInRoom = (ListView)view.findViewById(R.id.listView_DevicesInRoom);
        listView_DevicesInRoom.setAdapter(adapter);
        //listView_DevicesInRoom.setDividerHeight(20);
        listView_DevicesInRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String selected_device = device_names[+position];
                Toast.makeText(getContext(), selected_device, Toast.LENGTH_SHORT).show();
                MyLog.d("kerim","Upper:" + UPPER_CLASS +",WHO:" + WHO_USES +",device_type:" +devices_type[position]);
                if(WHO_USES.equals("ActivitySmartSelectAction.java")){
                    Intent intent = new Intent(getContext(), ActivitySwitch.class);
                    intent.putExtra("where_come_from","ActivitySmartSelectAction.java");//the page we go to uses multiple classes,so we need to specify.
                    intent.putExtra("upper_class",UPPER_CLASS);
                    intent.putExtra("for_what",FOR_WHAT);
                    intent.putExtra("device_name", selected_device);
                    intent.putExtra("device_type",devices_type[position]);
                    intent.putExtra("device_state",devices_state[position]);
                    intent.putExtra("device_location",devices_location[position]);
                    startActivity(intent);
                }else {//"FragmentHome.java",
                    //TODO: if click Device is Lamp,go to "ActivityLampControl.java":
                    String this_is_a_group = "NO"; //"YES"; //TODO: get this value from database.
                    Intent intent = new Intent(getContext(), ActivityLampControl.class);
                    intent.putExtra("device_name", selected_device);
                    intent.putExtra("group_of_device", this_is_a_group);//if this is a group "YES",if this is not a group "NO"
                    startActivity(intent);
                }
                //TODO: else if click Device is Lamp,go to "ActivityLampControl.java":
               /* Intent intent = new Intent(getContext(),ActivityOtherControl.class);
                startActivity(intent);*/
               scroll_position = position;
            }
        });
        adapter.notifyDataSetChanged();
        listView_DevicesInRoom.smoothScrollToPosition(scroll_position);

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
