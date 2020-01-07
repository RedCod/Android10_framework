package com.example.trident.smart;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trident.common.Alert;

import java.util.ArrayList;
import java.util.Collections;

public class RecyclerViewAdapter_RoomManagementRoomItem extends RecyclerView.Adapter<RecyclerViewAdapter_RoomManagementRoomItem.MyViewHolder>
        implements ItemMoveCallback_RoomManagementRoomItem.ItemTouchHelperContract {
    /**
     * using by "ActivityRoomManagement.java"
     */
    public static boolean ROOM_ITEM_DRAGDROP = false;//
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_Item,textView_Item_inf;
        private LinearLayout linearLayout_witharrowed;
        View rowView;

        public MyViewHolder(View itemView) {
            super(itemView);

            rowView                  = itemView;
            textView_Item            = itemView.findViewById(R.id.textView_Item);
            textView_Item_inf        = itemView.findViewById(R.id.textView_Item_inf);
            linearLayout_witharrowed = itemView.findViewById(R.id.linearLayout_witharrowed);
        }
    }

    private Context context;
    private ArrayList<String> arrayList_Rooms;
    private ArrayList<Integer> arrayList_DeviceCount;
    public RecyclerViewAdapter_RoomManagementRoomItem(Context _context, ArrayList<String> _arrayList_Rooms,ArrayList<Integer> _arrayList_DeviceCount) {
        this.context               = _context;
        this.arrayList_Rooms       = _arrayList_Rooms;
        this.arrayList_DeviceCount = _arrayList_DeviceCount;
    }

    @Override
    public  MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout./*listview_witharrowed*/listview_witharrowedandtextview, parent, false);
        return new  MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, final int position) {
        holder.textView_Item.setText(arrayList_Rooms.get(position));
        holder.textView_Item_inf.setText(arrayList_DeviceCount.get(position) + " " + context.getString(R.string.device));
        holder.linearLayout_witharrowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context.getApplicationContext(),"data:" + arrayList_Rooms.get(position),Toast.LENGTH_SHORT).show();
                checkIfOrderHasChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList_Rooms.size();
    }

    /**
     * get all items as ArrayList.
      * @return arrayList_Rooms
     */
    public ArrayList<String> getItemsAsArrayList(){
        return  arrayList_Rooms;
    }

    /**
     * get item.
     * @param index array index.
     * @return
     */
    public String getItem(int index){
        return arrayList_Rooms.get(index);
    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(arrayList_Rooms, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(arrayList_Rooms, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        setRoomItemDragDropChanges(true);
       ActivityRoomManagement.setButton_Edit_Visible(true);
    }
    @Override
    public void onRowSelected(MyViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(context.getResources().getColor(R.color.color_my_theme)); //(Color.GRAY);
    }

    @Override
    public void onRowClear(MyViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.WHITE);
    }
    //add by raptiye:
    public static void setRoomItemDragDropChanges(boolean _state){
        ROOM_ITEM_DRAGDROP = _state;
    }
    public static boolean isRoomItemDragDropChanges(){
        return ROOM_ITEM_DRAGDROP;
    }
    private void checkIfOrderHasChanged(){
        if(isRoomItemDragDropChanges()==true){
            //TODO:show mesage dialog.  "
            /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage(context.getString(R.string.msg_SaveRoomChanges));
            */
            Alert.MessageDialog messageDialog = new Alert().new MessageDialog();
            AlertDialog.Builder alertDialogBuilder = messageDialog.show(context,context.getString(R.string.msg_saveroomchanges));
            alertDialogBuilder.setPositiveButton(context.getString(R.string.save), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Toast.makeText(context.getApplicationContext(),"RecyclerViewAdapter You clicked yes    button",Toast.LENGTH_LONG).show();
                            //YES,TODO:Save changes.
                            ActivityRoomManagement.saveChanges();
                            Intent intent = new Intent(context.getApplicationContext(),ActivityRoomSettings.class);
                            context.startActivity(intent);
                            setRoomItemDragDropChanges(false);
                        }
                    });
            alertDialogBuilder.setNegativeButton(context.getString(R.string.donotsave),new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //do nothing.
                    ///go:
                    Intent intent = new Intent(context.getApplicationContext(),ActivityRoomSettings.class);
                    context.startActivity(intent);
                    setRoomItemDragDropChanges(false);
                }
            });
            alertDialogBuilder.create().show();
        }else{
            Intent intent = new Intent(context.getApplicationContext(),ActivityRoomSettings.class);
            context.startActivity(intent);
            setRoomItemDragDropChanges(false);
        }

    }
}
