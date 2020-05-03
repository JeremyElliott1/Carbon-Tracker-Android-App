package com.example.slidebox.ui.reusables;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slidebox.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReusablesUseAdapter extends FirestoreRecyclerAdapter<ReusableUse, ReusablesUseAdapter.ItemHolder> {

    /**
     * Create a new RecyclerView adapter that listens for a Firestore Query.
     */
    ReusablesUseAdapter(@NonNull FirestoreRecyclerOptions<ReusableUse> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemHolder holder, int position, @NonNull ReusableUse model) {
        holder.reusableUseItemNameTextView.setText(model.getItemName());
        holder.reusableUseDateTextView.setText(getDateAsString(model.getDate()));
        holder.reusableUsePointTextView.setText(model.getPoints());
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reusable_use,
                parent, false);
        return new ReusablesUseAdapter.ItemHolder(v);
    }

    ReusableUse getReusablesUse(int position) {
        return getSnapshots().getSnapshot(position).toObject(ReusableUse.class);
    }

    void deleteReusableUse(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        /* variables for each TextView within an ReusableItem */
        TextView reusableUseItemNameTextView;
        TextView reusableUseDateTextView;
        TextView reusableUsePointTextView;

        ItemHolder(@NonNull View itemView) {
            super(itemView);

            reusableUseItemNameTextView = itemView.findViewById(R.id.reusable_UseItemNameTextView);
            reusableUseDateTextView = itemView.findViewById(R.id.reusable_UseDateTextView);
            reusableUsePointTextView = itemView.findViewById(R.id.reusable_UsePointsTextView);

        }
    }

    private String getDateAsString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = formatter.format(date);
        return strDate;
    }
}
