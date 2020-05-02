package com.example.slidebox.ui.leaderboard;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slidebox.R;

import org.w3c.dom.Text;

class UserPointsViewHolder extends RecyclerView.ViewHolder {


    private TextView fullName;
    private TextView points;
    private ImageView imageViewThumbup;
    private TextView numThumbup;



    public UserPointsViewHolder(@NonNull View itemView) {
        super(itemView);
        fullName = itemView.findViewById(R.id.textViewUserName);
        points = itemView.findViewById(R.id.textViewPoints);
        imageViewThumbup = itemView.findViewById(R.id.imageViewThumbup);
        numThumbup = itemView.findViewById(R.id.textViewThumbupNum);

    }

    public TextView getFullName() {
        return fullName;
    }

    public TextView getPoints() {
        return points;
    }

    public ImageView getImageViewThumbup() {
        return imageViewThumbup;
    }

    public TextView getNumThumbup() {
        return numThumbup;
    }
}
