package com.example.slidebox.ui.reusables.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slidebox.R;
import com.example.slidebox.ui.reusables.ReusableItem;

import java.util.ArrayList;

public class ItemsRecyclerAdapter extends RecyclerView.Adapter<ItemsRecyclerAdapter.ReusableItemViewHolder> {

    private ArrayList<ReusableItem> reusableItemList = new ArrayList<>();


    static class ReusableItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView reusableImageView;
        public TextView reusableTextView;

        public ReusableItemViewHolder(@NonNull View reusableItemView) {
            super(reusableItemView);
            reusableImageView = reusableItemView.findViewById(R.id.reusableItemImageView);
            reusableTextView = reusableItemView.findViewById(R.id.reusableItemTextView);
        }
    }

    //Constructor of ItemRecyclerAdapter
    //@param ArrayList of reusableObjects
    //Specifies what data type and data source will be referenced.
    public ItemsRecyclerAdapter(ArrayList<ReusableItem> itemList) {
        reusableItemList = itemList;
    }

    //Specifying what view is used to hold the Item Data/details
    @NonNull
    @Override
    public ItemsRecyclerAdapter.ReusableItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reusable_item, parent, false);
        ReusableItemViewHolder VH = new ReusableItemViewHolder(v);
        return VH;
    }

    //Specifies what data is added to what ViewHolder based upon
    // the position of the ViewHolder object in the RecyclerView
    // the position of the data item in the referenced ArrayList.
    @Override
    public void onBindViewHolder(@NonNull ItemsRecyclerAdapter.ReusableItemViewHolder holder, int position) {
        ReusableItem currentItem = reusableItemList.get(position);

        //setting the ViewHolder to reference the currentItem's image
        holder.reusableImageView.setImageResource(currentItem.getReusableItemImage());
        //setting the ViewHolder to reference the currentItem's String description
        holder.reusableTextView.setText(currentItem.getItemStringDescription());
    }

    @Override
    public int getItemCount() {
        return reusableItemList.size();
    }


}
