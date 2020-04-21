package com.example.slidebox.ui.leaderboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.slidebox.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {
    List<UserPoints> userPointsList = new ArrayList<UserPoints>();

    public List<UserPoints> getUserPointsList() {
        return userPointsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.cell_rangkinglist,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserPoints userPoints = userPointsList.get(position);

        holder.textViewNum.setText(String.valueOf(userPoints));


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNum, textViewUserName, textViewPoints, textViewThumbupNum;
        ImageView imageViewThumb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNum = itemView.findViewById(R.id.textViewNum);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            textViewPoints = itemView.findViewById(R.id.textViewPoints);
            textViewThumbupNum = itemView.findViewById(R.id.textViewThumbupNum);
            imageViewThumb = itemView.findViewById(R.id.imageViewThumbup);
        }
    }
}
