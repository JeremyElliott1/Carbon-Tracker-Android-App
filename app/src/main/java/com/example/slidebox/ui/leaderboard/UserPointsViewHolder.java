package com.example.slidebox.ui.leaderboard;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slidebox.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

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
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



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

    public int onClick(final String fullname) {
      final int[] i1 = {};
        imageViewThumbup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                firebaseFirestore.collection("users").
                        document("thumbup").
                        get().
                        addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete (@NonNull Task < DocumentSnapshot > task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        if (String.valueOf(document.get(fullname)).isEmpty() == false) {
//
                                        }

                                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    } else {
                                        Log.d(TAG, "No such document");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });
//
//                i1[0]++ ;
//                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                String fullname = (String) documentSnapshot.get("firstName") + (String)documentSnapshot.get("lastName");
                Log.d(TAG, "Count is " + i1[0]);
                Map<String, Object> map = new HashMap<>();
                map.put(fullname,String.valueOf(i1[0]));
                firebaseFirestore.collection("users").document(user.getUid()).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Added thumbup! " );
                    }
                });
                firebaseFirestore.collection("users").document("thumbup").update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Added thumbup! " );
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
