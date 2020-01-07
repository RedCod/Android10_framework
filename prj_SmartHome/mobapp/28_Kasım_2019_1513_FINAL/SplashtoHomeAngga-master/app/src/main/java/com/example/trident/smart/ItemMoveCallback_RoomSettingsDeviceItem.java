package com.example.trident.smart;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class ItemMoveCallback_RoomSettingsDeviceItem extends ItemTouchHelper.Callback {
    /**
     * Usin by RecyclerViewAdapter_RoomSettingsDeviceItem.java
     */

    private final ItemTouchHelperContract mAdapter;

    public ItemMoveCallback_RoomSettingsDeviceItem(ItemTouchHelperContract adapter) {
        mAdapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }


    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        mAdapter.onRowMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder,
                                  int actionState) {


        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof RecyclerViewAdapter_RoomSettingsDeviceItem.MyViewHolder) {
                RecyclerViewAdapter_RoomSettingsDeviceItem.MyViewHolder myViewHolder =
                        (RecyclerViewAdapter_RoomSettingsDeviceItem.MyViewHolder) viewHolder;
                mAdapter.onRowSelected(myViewHolder);
            }

        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if (viewHolder instanceof RecyclerViewAdapter_RoomSettingsDeviceItem.MyViewHolder) {
            RecyclerViewAdapter_RoomSettingsDeviceItem.MyViewHolder myViewHolder =
                    (RecyclerViewAdapter_RoomSettingsDeviceItem.MyViewHolder) viewHolder;
            mAdapter.onRowClear(myViewHolder);
        }
    }

    public interface ItemTouchHelperContract {

        void onRowMoved(int fromPosition, int toPosition);

        void onRowSelected(RecyclerViewAdapter_RoomSettingsDeviceItem.MyViewHolder myViewHolder);

        void onRowClear(RecyclerViewAdapter_RoomSettingsDeviceItem.MyViewHolder myViewHolder);

    }

}
