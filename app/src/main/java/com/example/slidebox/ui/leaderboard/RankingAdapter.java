package com.example.slidebox.ui.leaderboard;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.slidebox.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingAdapter extends FirestorePagingAdapter<UserPoints, UserPointsViewHolder> {


    private static final String TAG = "RankingAdapter";
    private static final int TODAY_FLAG = 1;
    private static final int WEEK_FLAG = 2;
    private static final int MONTH_FLAG = 3;
    private int flag;
    private int count = 0;
    private OnThumbupClick onThumbupClick;
    private DocumentSnapshot ds;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RankingAdapter(@NonNull FirestorePagingOptions<UserPoints> options, int flag, OnThumbupClick onThumbupClick) {
        super(options);
        this.flag = flag;
        this.onThumbupClick = onThumbupClick;
        readDocSnapshot();
    }

    @NonNull
    @Override
    public UserPointsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_rangkinglist,parent,false);

        return new UserPointsViewHolder(view,onThumbupClick);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserPointsViewHolder holder, int position, @NonNull UserPoints model) {
        String fullName = model.getFirstName() + "  " + model.getLastName();
        String point = null;
        switch (flag){
            case TODAY_FLAG:
               point = String.valueOf(model.getTotalPoints());
                break;
            case WEEK_FLAG:
                point = String.valueOf(model.getWeeklyPoints());
                break;
            case MONTH_FLAG:
                point = String.valueOf(model.getMonthlyPoints());
                break;
        }
        //holder.onClick(model.getNumThumbup());

        holder.getFullName().setText(fullName);
        holder.getImageViewThumbup().setImageResource(R.drawable.ic_thumb_up_black_24dp);
        holder.getItemPosition().setText(String.valueOf(position +1));
        holder.getPoints().setText(point);

        DocumentSnapshot documentSnapshot = getItem(position);

        if(documentSnapshot.get(user.getUid()) != null){
            Log.d(TAG, "get thumb doc" + documentSnapshot.get(user.getUid()));
            String o = (String) documentSnapshot.get(user.getUid());

            // holder.getNumThumbup().setText(String .valueOf(holder.onClick(o,documentSnapshot)));
        }else {
            if( String.valueOf(model.getNumThumbup()).isEmpty() == false){
                holder.getNumThumbup().setText(String .valueOf(model.getNumThumbup()));
            }
        }


    }

    private void readDocSnapshot(){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore.collection("users").document("thumbup").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ds = document;
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        //        firebaseFirestore.collection("users").document("thumbup").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                            ds =document;
////                            int numthumbup = (int) ds.get(fullname2);
//                            Log.d(TAG, "get thumb doc" + numthumbup );
//                            holder1.getNumThumbup().setText(String .valueOf(holder1.onClick(numthumbup,documentSnapshot)));
//
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });



    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {
        super.onLoadingStateChanged(state);
        switch (state){
            case LOADED:
                Log.d(TAG,"All item loaded " + getItemCount());
                break;
        }
    }

    public OnThumbupClick getOnThumbupClick() {
        return onThumbupClick;
    }

    public interface OnThumbupClick{
        void onClick();
    }
}
