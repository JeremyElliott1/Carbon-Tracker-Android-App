package com.example.slidebox.ui.reusables;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slidebox.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
/* Adapter accesses data from firestore database and puts it in the RecyclerViewHolder
 *   FireStoreRecycler Adapter is a subclass of a normal RecyclerView Adapter */

public class ReusablesItemAdapter extends FirestoreRecyclerAdapter<ReusableItem, ReusablesItemAdapter.ItemHolder> {

    private AdapterView.OnItemClickListener listener;

    /*
     * Create a new RecyclerView adapter that listens for a Firestore Query.
     */
    ReusablesItemAdapter(@NonNull FirestoreRecyclerOptions<ReusableItem> options) {
        super(options);
    }

    /* determines what is put in each TextView holder within ReusableItem */
    @Override
    protected void onBindViewHolder(@NonNull ItemHolder holder, int position, @NonNull ReusableItem model) {
        holder.reusableItemNameTextView.setText(model.getName());
        holder.reusableItemPointTextView.setText(model.getPoints());
    }


    /* tells adapter which layout to inflate for each ItemViewHolder */
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reusable_item,
                parent, false);
        return new ItemHolder(v);
    }

    void deleteReusableItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        /* variables for each TextView within an ReusableItem */
        TextView reusableItemNameTextView;
        TextView reusableItemPointTextView;

        ItemHolder(@NonNull View itemView) {
            super(itemView);

            reusableItemNameTextView = itemView.findViewById(R.id.reusable_ItemNameTextView);
            reusableItemPointTextView = itemView.findViewById(R.id.reusable_ItemPointsTextView);

        }
    }

}
