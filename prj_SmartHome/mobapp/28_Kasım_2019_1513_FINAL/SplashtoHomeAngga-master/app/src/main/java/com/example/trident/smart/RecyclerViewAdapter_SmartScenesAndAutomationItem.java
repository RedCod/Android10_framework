package com.example.trident.smart;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trident.common.MyLog;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

public class RecyclerViewAdapter_SmartScenesAndAutomationItem extends RecyclerView.Adapter<RecyclerViewAdapter_SmartScenesAndAutomationItem.MyViewHolder>
        implements ItemMoveCallback_SmartScenesAndAutomationItem.ItemTouchHelperContract {

    public class MyViewHolder extends RecyclerView.ViewHolder {

        View rowView;
        private LinearLayout linearLayout_Item;
        private TextView textView_Item;
        public MyViewHolder(View itemView) {
            super(itemView);
           rowView = itemView;
           linearLayout_Item = itemView.findViewById(R.id.linearLayout_Item);
           textView_Item = itemView.findViewById(R.id.textView_Item);
        }//

    }//class MyViewHolder

    private Context context;
    private ArrayList<String> arrayList;
    public RecyclerViewAdapter_SmartScenesAndAutomationItem(Context context, ArrayList<String> arrayList){
        this.context = context;
        this.arrayList = arrayList; //"Scenes" or "Automations"
    }

    @Override
    public RecyclerViewAdapter_SmartScenesAndAutomationItem.MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_withtextviewandmoveicon, parent, false);
        return new RecyclerViewAdapter_SmartScenesAndAutomationItem.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, final int position) {
        holder.textView_Item.setText(arrayList.get(position));

        holder.linearLayout_Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.d("kerim[ITEM]","item:"+ arrayList.get(position));
            }
        });


    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    /**
     * get Item List.
     * @return Items as ArrayList
     */
    public ArrayList<String> getItemsAsArrayList(){
        return arrayList;
    }
    public String getItem(int index){
        return  arrayList.get(index);
    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(arrayList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(arrayList, i, i -1);
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
