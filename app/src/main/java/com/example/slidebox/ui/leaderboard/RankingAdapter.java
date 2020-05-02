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
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RankingAdapter extends FirestorePagingAdapter<UserPoints, UserPointsViewHolder> {


    private static final String TAG = "RankingAdapter";
    private static final int TODAY_FLAG = 1;
    private static final int WEEK_FLAG = 2;
    private static final int MONTH_FLAG = 3;
    private int flag;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RankingAdapter(@NonNull FirestorePagingOptions<UserPoints> options, int flag) {
        super(options);
        this.flag = flag;
    }

    @NonNull
    @Override
    public UserPointsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_rangkinglist,parent,false);

        ImageView imageViewThumbup = view.findViewById(R.id.imageViewThumbup);
        imageViewThumbup.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {


                                                }
                                            }
        );


        return new UserPointsViewHolder(view);
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
        holder.getFullName().setText(fullName);
        holder.getImageViewThumbup().setImageResource(R.drawable.ic_thumb_up_black_24dp);
        holder.getItemPosition().setText(String.valueOf(position +1));
        holder.getPoints().setText(point);
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
}
