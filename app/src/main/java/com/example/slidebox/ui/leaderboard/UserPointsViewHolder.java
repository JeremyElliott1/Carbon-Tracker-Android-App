package com.example.slidebox.ui.leaderboard;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slidebox.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

class UserPointsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static String TAG = "Holder";
    private TextView fullName;
    private TextView points;
    private ImageView imageViewThumbup;
    private TextView numThumbup;
    private TextView itemPosition;
    private ImageView imageViewUser;
    private RankingAdapter.OnThumbupClick onThumbupClick;


    public UserPointsViewHolder(@NonNull View itemView, RankingAdapter.OnThumbupClick onThumbupClick) {
        super(itemView);
        fullName = itemView.findViewById(R.id.textViewUserName);
        points = itemView.findViewById(R.id.textViewPoints);
        imageViewThumbup = itemView.findViewById(R.id.imageViewThumbup);
        numThumbup = itemView.findViewById(R.id.textViewThumbupNum);
        itemPosition = itemView.findViewById(R.id.textViewPosition);
        imageViewUser = itemView.findViewById(R.id.imageViewUser);
        this.onThumbupClick = onThumbupClick;
    }

    public TextView getItemPosition() {
        return itemPosition;
    }

    public ImageView getImageViewUser() {
        return imageViewUser;
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

    public int onClick(final String o, final DocumentSnapshot documentSnapshot) {
        final int[] i1 = {Integer.valueOf(o)};
        imageViewThumbup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i1[0]++;
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String fullname = user.getUid();
                Log.d(TAG, "Count is " + i1[0]);
                Map<String, Object> map = new HashMap<>();
                map.put(fullname, String.valueOf(i1[0]));
                firebaseFirestore.collection("users").document(user.getUid()).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Added thumbup! ");
                    }
                });
                firebaseFirestore.collection("users").document("thumbup").update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Added thumbup! ");
                    }
                });
            }
        });
        return i1[0];
    }

    @Override
    public void onClick(View v) {

    }
}
