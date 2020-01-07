package com.example.trident.smart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.Preferences;
import com.example.trident.smart.custom.CustomSpinnerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentHome.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment  {
    private static String TAG = "[FragmentHome]";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
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
    private String PREF = "Family_Spinner_Item";
    private String PREF_KEY = "LAST_POSITION";

    TabLayout tablayout_Rooms;
    LinearLayout linearlayout_ScenariosItems;
    CustomSpinnerAdapter spinnerAdapter;
    static Spinner spinner_Family;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        ///Controls:@{
        /*final Spinner*/ spinner_Family                    = (Spinner) view.findViewById(R.id.spinner_family);
        Button button_AddDevices                            = (Button)view.findViewById(R.id.button_AddDevices);
        ImageView imageView_HomeLocationSun                 = (ImageView)view.findViewById(R.id.imageView_homelocation_sun);
        TextView textView_HomeLocationWelcome               = (TextView)view.findViewById(R.id.textView_homelocation_welcome);
        TextView textView_HomeLocationWelcomeDescription    = (TextView)view.findViewById(R.id.textView_homelocation_welcome_description);

        tablayout_Rooms                                     = (TabLayout)view.findViewById(R.id.tablayout_rooms);
        TextView textView_ManagementRoom                    = (TextView)view.findViewById(R.id.textview_management_room);
        /*LinearLayout*/ linearlayout_ScenariosItems        = (LinearLayout)view.findViewById(R.id.linearlayout_scenarios_items);
        ViewPager viewPager                                 = view.findViewById(R.id.view_pager);
        ///Controls:@}
        //////////////////////////

        ///call functions:
        loadScenarioItems();
        loadTabLayoutItems();

//Spinner:@{
        final String[] familes_arr =   {"Aile1","Aile2","Aile3","Aile4",getString(R.string.home_management)};//Normaly Family data get user database and then put this array for spinner. And put "Home Management" in last item-row.
        /*CustomSpinnerAdapter*/ spinnerAdapter = new CustomSpinnerAdapter(view.getContext(),familes_arr);
        spinner_Family.setAdapter(spinnerAdapter);
        spinner_Family.setSelection(loadPreference());//set selection last position.
        spinner_Family.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //  String item  = spinner_wich_family.getSelectedItem().toString();
                if(position == familes_arr.length -1) {//if selection last("Home Management") item. Go to "Home Management" page.
                    //...
                    //..
                    //Toast.makeText(getContext(),"select spinner position(go to 'Ev Yönetimi' page :" + spin_familys[position],Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity().getApplicationContext(),ActivityHomeManagement.class);
                    intent.putExtra(BetweenActivities.where_come_from,"FragmentHome.java");
                    startActivity(intent);
                    return;//break this line. because we don't need run following codes.
                }

                if(position != familes_arr.length -1){//if position refer the "Ev Yonetimi",pass preferences save....
                    /**
                     * Save this "position" in the Preference file.
                     * Because we need to keep(save) the position of the selected family.
                     * when application reopened, to automatically select a preselected family.
                     */
                    Preferences.saveAsInt(getActivity().getApplicationContext(),PREF,PREF_KEY,position);
                    spinnerAdapter.PREVIOUS_POSITION = position;
                }
                Toast.makeText(getContext(),"select spinner:" + spinnerAdapter.getItem(position),Toast.LENGTH_SHORT).show();
                //todo:get item:@{
                    //spinner_Family.getAdapter().getItem(position)
                    //spinner_Family.getSelectedItem()
                    //spinnerAdapter.getItem(position)
                //get item:}

                //Spinner selected Item:@{set new settings to the "TextView" (when selected Spinner_Item).
                    TextView textView_SpinnerItem = view.findViewById(R.id.textView_spinner);
                    //textView_SpinnerItem.setTextColor(Color.GREEN);
                    textView_SpinnerItem.setTypeface(Typeface.DEFAULT_BOLD);
                //Spinner selected Item:@}
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



//spinner:@}
        button_AddDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to "add devices (devices list)" page:
                //Toast.makeText(getContext(),"go to add devices list page.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext().getApplicationContext(),ActivityAddDevices.class);
                startActivity(intent);
            }
        });

///HOME LOCATION CONTROLS EVENT:@{
       //imageView_homelocation_sun
       //textView_homelocation_welcome
        //textView_homelocation_welcome_description
        imageView_HomeLocationSun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeLocationAdjustment();
            }
        });
        textView_HomeLocationWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeLocationAdjustment();
            }
        });
        textView_HomeLocationWelcomeDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeLocationAdjustment();
            }
        });
///HOME LOCATION CONTROLS EVENT:@}

        tablayout_Rooms.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               //load room content from db according to selected room(room tab).
               Toast.makeText(getContext(),"selected ROOM tab: " +tab.getText() +",getPosition():" + tab.getPosition(),Toast.LENGTH_SHORT).show();

               /** TODO:remove this comment. because move on loadFragment()
                * if selected room  devices exists,load "Fragment_FragmentHome_RoomDevices".
                * But if selected room devices not exists, load "add device page fragment".
                *
                */
           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {

           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {

           }
       });

        textView_ManagementRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 *if click "textview_management_room",going to Room management page.
                 */
                //Toast.makeText(getContext(),"going to Room Management page.",Toast.LENGTH_SHORT).show();
                ActivityRoomManagement.FROM_PAGE = "FragmentHome.java";
                Intent intent = new Intent(getActivity().getApplicationContext(),ActivityRoomManagement.class);
                startActivity(intent);
            }
        });

        ///:@{
        /**
         * Room'lar db'den çekilir.
         * Room sayısı room_count'a atanır.
         * For içinde:eğer room içinde cihaz varsa "Fragment_FragmentHome_RoomDevices" atanır. Aksi halde "Fragment_FragmentHome_BlankRoomContent" atanır.
         *
         */
        int room_count = 10;
        Fragment[] childFragments = new Fragment[room_count];
        String[] room_names = new String[room_count];
        ///TODO: for test: this values get from database.:@{
        String[] device_names = {//TODO: get thus values from database
                "Lamba_1",
                "Lamba_2",
                "Plug Mutfak"

        };

        String[] devices_type = {//TODO: get thus values from database
                "lamp",
                "lamp",
                "wall_plug"
        };
        String[] devices_state = {//TODO: get thus values from database
                "Online",
                "Online",
                "Offline",
        };
        ///todo:@}
        for(int i=0;i<room_count;i++){
            /**
             * Her döngüde oda ismi ve ilgili oda ismine ait cihaz isimleri "Fragment_FragmentHome_RoomDevices"'a dizi olarak tek seferde parametre gidilir.
             * Fragment_FragmentHome_RoomDevices.class tarafında ise oda ismi tablayout:tab'a title olarak atanır Ve cihaz isimleri "device_names" dizisine atanır.
             * Böylece her odaya ait tab ve cihaz isimleri listView'de yerini alır.
             *              *
             */
            if(i%2 == 1){//TODO: #############"if" for test.####################
                //TODO:if no room content,make new "Fragment_FragmentHome_BlankRoomContent()" object.
                childFragments[i] = new Fragment_FragmentHome_BlankRoomContent();
                if(i==0)//TODO:first tab should be always "All Devices"
                    room_names[0] = getString(R.string.all_devices);
                else
                    room_names[i] = "Room" +i;
            }else{
                //TODO: if content exists,make new "Fragment_FragmentHome_RoomDevices" object.
                childFragments[i] = new Fragment_FragmentHome_RoomDevices("FragmentHome.java",devices_type,device_names,devices_state);
                if(i==0)//TODO:first tab should be always "All Devices"
                    room_names[0] = getString(R.string.all_devices);
                else
                    room_names[i] = "Room" +i;
            }

            /*childFragments[i] = new Fragment_FragmentHome_RoomDevices("room name:" + i,device_names);
            room_names[i] = "Room" +i;*/
        }
         /*Fragment[] childFragments = new Fragment[] {
                new Fragment_FragmentHome_RoomDevices(),
                new Fragment_FragmentHome_BlankRoomContent()

        };*/
        tablayout_Rooms.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager.setAdapter(new ViewPagerAdapter(getActivity().getSupportFragmentManager(),childFragments,room_names));
        tablayout_Rooms.setupWithViewPager(viewPager);
        ///:@}

        return  view;
    }//onCreateView


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

    /*#############################################START FUNCTION###################################*/

    /**
     *
     */
    int count_scenario_button_id =1;
    private void loadScenarioItems(){
        /**
         * Load scenario.
         */

        /**FIXME: todo:
         * Senaryolar DB'den çekilip,her senaryo ITEM'ı dinamik bir button olarak oluşturulup eklenecek.
         * DB'den alınan : "senaryo_id,senaryo_komut" içerikleri bir Array'a key,value (not: key=senaryo_id,value=senaryo_komut)şeklinde atılacak.
         * Aynı zamanda button'a  setId(senaryo_id) şeklinde ayarlanacak. Böylece Button Click eventinden getId() alınıp  Array içeriğinde key-value karşılığı alınarak komut çalıştırılacaktır.
         * Senaryo ITEM'a ait mqtt komutu Array'a  key-value,yani .add(senaryo_id,senaryo_komut) aktarılacaktır.
         */

        // add Button
        Button button = null;
        for(int i=0;i<7;i++){  //FOR TEST :for loop. TODO: "For" loop content consists number of scenario items received from database.
            button = new Button(getContext());
            button.setAllCaps(false);
            //button.setId(count_scenario_button_id);
            //button.setText("Senaryo" +count_scenario_button_id);
            //count_scenario_button_id++;
            LinearLayout.LayoutParams button_scenario_layout = new
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.FILL_PARENT);
            button_scenario_layout.weight = 1.0f;
            button.setBackgroundColor(getResources().getColor(R.color.color_White1));
            button.setBackgroundResource(R.drawable.buttonshape3);
            button.setId(count_scenario_button_id);
            button.setText("Senaryo" +count_scenario_button_id);
            button_scenario_layout.leftMargin = 20; //button margin left
            button_scenario_layout.width = 300;//button width
            button.setOnClickListener(scenario_Button_clickListener);
            count_scenario_button_id++;
            button.setLayoutParams(button_scenario_layout);
            //linearlayout_scenarios_items.removeAllViews();
            linearlayout_ScenariosItems.addView(button);
        }
    }
    /**
     * Scenario ITEMs button ClickEventListener.
     */
    View.OnClickListener scenario_Button_clickListener = new View.OnClickListener() {
        public void onClick(View v) {
            Button button = (Button)v;
            Toast.makeText(getContext(), "Button id:" + v.getId() + ",text:" + button.getText(), Toast.LENGTH_SHORT).show();
        }
    };
    /**
     *
     */
    private void loadTabLayoutItems(){
        /**
         * load tabLayout items(room names) from database.
         */
        //for test:
        tablayout_Rooms.setTabMode(TabLayout.MODE_SCROLLABLE);
        tablayout_Rooms.addTab(tablayout_Rooms.newTab().setText("Room1"));
        tablayout_Rooms.addTab(tablayout_Rooms.newTab().setText("Room2"));
        tablayout_Rooms.addTab(tablayout_Rooms.newTab().setText("Room3"));
        tablayout_Rooms.addTab(tablayout_Rooms.newTab().setText("Room4"));
        tablayout_Rooms.addTab(tablayout_Rooms.newTab().setText("Room5"));
        tablayout_Rooms.addTab(tablayout_Rooms.newTab().setText("Room2"));
        tablayout_Rooms.addTab(tablayout_Rooms.newTab().setText("Room3"));
        tablayout_Rooms.addTab(tablayout_Rooms.newTab().setText("Room4"));
        tablayout_Rooms.addTab(tablayout_Rooms.newTab().setText("Room5"));
    }

    private void homeLocationAdjustment(){
        Toast.makeText(getContext(), "go to the Location Adjustment Page.", Toast.LENGTH_SHORT).show();
    }

    private int loadPreference(){
        int get_last_position = Preferences.loadAsInt(getActivity().getApplicationContext(),PREF,PREF_KEY);
        return  get_last_position;
    }
    /*###########################################end FUNCTION#######################################*/
}
