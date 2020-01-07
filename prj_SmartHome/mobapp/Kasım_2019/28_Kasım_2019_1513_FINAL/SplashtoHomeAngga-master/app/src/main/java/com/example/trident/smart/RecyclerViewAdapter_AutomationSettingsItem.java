package com.example.trident.smart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trident.common.BetweenActivities;
import com.example.trident.common.DeviceIcon;
import com.example.trident.common.DeviceType;
import com.example.trident.common.MyLog;
import com.example.trident.smart.custom.SmartAndAutomationItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecyclerViewAdapter_AutomationSettingsItem extends RecyclerView.Adapter<RecyclerViewAdapter_AutomationSettingsItem.MyViewHolder>
        implements ItemMoveCallback_SmartAndAutomationSettingsItem.ItemTouchHelperContract {
    /**
     * using by "ActivitySmartSettings.java,ActivityAutomationSettings.java"
     */
    public static boolean ROOM_ITEM_DRAGDROP = false;//

    /**
     * //ActivitySmartSettings.java OR ActivityAutomationSettings.java
     * Hangi sayfadan geldiğimizi bilmeliyiz ki geri dönerken geldiğimiz sayfaya geri gidebilelim.
     * Aksi halde yanlış sayfaya yönlendiriliriz.
     */
    private static String WHICH_PAGE = null;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout_MainItemBase;
        ImageView imageView_Icon;
        TextView textView_Item;
        TextView textView_ItemDescribtion;
        TextView textView_ItemDescribtion2;
        View rowView;
        public MyViewHolder(View itemView) {
            super(itemView);
            rowView                   = itemView;
            linearLayout_MainItemBase = (LinearLayout)itemView.findViewById(R.id.linearLayout_MainItemBase);
            imageView_Icon            = (ImageView)itemView.findViewById(R.id.imageView_Icon);
            textView_Item             = (TextView)itemView.findViewById(R.id.textView_Item);
            textView_ItemDescribtion  = (TextView)itemView.findViewById(R.id.textView_ItemDescribtion);
            textView_ItemDescribtion2 = (TextView)itemView.findViewById(R.id.textView_ItemDescribtion2);
        }
    }
    Context context;
    List<String> arrayList_DevicesName;
    List<String> arrayList_DevicesLocation;
    List<String> arrayList_DevicesState;
    List<String> arrayList_DevicesTypes;
    public RecyclerViewAdapter_AutomationSettingsItem(Context context,ArrayList arrayList,String which_page){
        /**todo:fix
         * for the picture on the item (eg lamp);If the assigned item information belongs to a lamp, the "lamp" icon is assigned.For others, the same method applies.
         */
        this.context = context;
        ArrayList<SmartAndAutomationItem> arrayList1 = arrayList;
        WHICH_PAGE = which_page;
        SmartAndAutomationItem smartAndAutomationItem;
        arrayList_DevicesName     = new ArrayList<>();
        arrayList_DevicesLocation = new ArrayList<>();
        arrayList_DevicesState    = new ArrayList<>();
        arrayList_DevicesTypes    = new ArrayList<>();
       for(int i=0;i<arrayList1.size();i++){
           smartAndAutomationItem = arrayList1.get(i);
           arrayList_DevicesName.add(smartAndAutomationItem.getName());
           arrayList_DevicesLocation.add((smartAndAutomationItem.getLocation()));
           arrayList_DevicesState.add(smartAndAutomationItem.getState());
           arrayList_DevicesTypes.add(smartAndAutomationItem.getType());
       }
    }///

    @Override
    public  MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_activity_smart_settings, parent, false);
        return new  MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.textView_Item.setText(arrayList_DevicesName.get(position));
        holder.textView_ItemDescribtion.setText(arrayList_DevicesLocation.get(position));
        holder.textView_ItemDescribtion2.setText(arrayList_DevicesState.get(position));
        //
        holder.linearLayout_MainItemBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.d("kerim[RecyclerViewAdapter_AutomationSettingsItem]","devies_types:" + arrayList_DevicesTypes.get(position));
                //item base click event:

                /**
                 * Aşağıdaki class'lar tarafından bu class kullanılıyor.Bu nedenle ayrıştırma yapmamız gerekir.
                 */
                String returns_class = null;
                if(WHICH_PAGE.equals("ActivitySmartSettings.java")) {
                    ActivitySmartSettings.keepPageCurrentScrollPosition();//get scroll position,to focus later(when go back).
                    returns_class = "ActivitySmartSettings.java";
                }else if(WHICH_PAGE.equals("ActivityAutomationSettings.java")) {
                    ActivityAutomationSettings.keepPageCurrentScrollPosition();//get scroll position,to focus later(when go back).
                    returns_class = "ActivityAutomationSettings.java";
                }

                if(arrayList_DevicesTypes.get(position).equals(DeviceType.TIME_LAPSE)){
                    Intent intent = new Intent(context.getApplicationContext(), ActivityTimeLapse.class);
                    intent.putExtra(BetweenActivities.where_come_from, returns_class);
                    intent.putExtra(BetweenActivities.position, position);//we will get this value back when we return here.
                    intent.putExtra(BetweenActivities.selected_value,arrayList_DevicesState.get(position));
                    context.startActivity(intent);
                }else if(arrayList_DevicesTypes.get(position).equals(DeviceType.LAMP) || arrayList_DevicesTypes.get(position).equals(DeviceType.WALL_PLUG)){
                    Intent intent = new Intent(context.getApplicationContext(), ActivitySwitch.class);
                    intent.putExtra("upper_class", returns_class);
                    intent.putExtra(BetweenActivities.where_come_from, returns_class);
                    intent.putExtra(BetweenActivities.for_what,"Edit_Switch");
                    intent.putExtra(BetweenActivities.position, position);//we will get this value back when we return here.
                    context.startActivity(intent);
                }else if(arrayList_DevicesTypes.get(position).equals(DeviceType.AUTOMATION)){
                    //go to "Tetiklenecek Otomasyon"
                    Intent intent = new Intent(context.getApplicationContext(), ActivityAutomationSelect.class);
                    intent.putExtra(BetweenActivities.where_come_from, returns_class);//will be needed on the way back to here.
                    context.startActivity(intent);
                }
            }
        });
        //
        if(arrayList_DevicesTypes.get(position).equals(DeviceType.LAMP)){
            holder.imageView_Icon.setImageResource(DeviceIcon.LAMP);
        }else if(arrayList_DevicesTypes.get(position).equals(DeviceType.WALL_PLUG)){
            holder.imageView_Icon.setImageResource(DeviceIcon.WALL_PLUG);
        }else if(arrayList_DevicesTypes.get(position).equals(DeviceType.TIME_LAPSE)){
            holder.imageView_Icon.setImageResource(DeviceIcon.TIME_LAPSE);
            if(arrayList_DevicesState.get(position).contains(":")){
                String[] timevalue = arrayList_DevicesState.get(position).split(":");
                if(MyLog.DEGUB)MyLog.d("kerim", "[RecyclerViewAdapter_AutomationSettingsItem]:values[0]" + timevalue[0] + ",values[1]" + timevalue[1]);
                int mn = Integer.parseInt(timevalue[0]);
                int sc = Integer.parseInt(timevalue[1]);
                if(MyLog.DEGUB)MyLog.d("kerim", "[RecyclerViewAdapter_AutomationSettingsItem]> mn:" + mn + ",sc:" + sc);
                if (mn < 1) //no value for minutes.
                    holder.textView_ItemDescribtion2.setText(sc + context.getString(R.string.sc));//only second.
                else if(mn > 0 && sc < 1)
                    holder.textView_ItemDescribtion2.setText(mn + context.getString(R.string.mn));//only minute
                else
                    holder.textView_ItemDescribtion2.setText(mn + context.getString(R.string.mn) + sc + context.getString(R.string.sc));//minute and second.
            }else
            if(MyLog.DEGUB)MyLog.d("kerim","[RecyclerViewAdapter_AutomationSettingsItem] ERROR:("+arrayList_DevicesState.get(position) +") uygunsuz içerik olduğu için split yapılamadı.");
        }else if(arrayList_DevicesTypes.get(position).equals(DeviceType.AUTOMATION)){
            holder.imageView_Icon.setImageResource(DeviceIcon.AUTOMATION);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList_DevicesName.size();
    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(arrayList_DevicesName, i, i + 1);
                /// item taşırken diğer bilgilerin de ilgili itemle paralel taşınması için: !!!!!GEREKLİYSE!!!!
                Collections.swap(arrayList_DevicesLocation, i, i + 1);
                Collections.swap(arrayList_DevicesState, i, i + 1);
                Collections.swap(arrayList_DevicesTypes, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(arrayList_DevicesName, i, i - 1);
                /// item taşırken diğer bilgilerin de ilgili itemle paralel taşınması için: !!!!!GEREKLİYSE!!!!
                Collections.swap(arrayList_DevicesLocation, i, i - 1);
                Collections.swap(arrayList_DevicesState, i, i - 1);
                Collections.swap(arrayList_DevicesTypes, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }
    @Override
    public void onRowSelected(MyViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(context.getResources().getColor(R.color.color_my_theme)); //(Color.GRAY);
    }
    @Override
    public void onRowClear(MyViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.WHITE);
    }

}
