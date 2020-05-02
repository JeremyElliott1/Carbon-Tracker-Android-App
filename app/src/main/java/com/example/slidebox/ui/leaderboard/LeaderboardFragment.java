package com.example.slidebox.ui.leaderboard;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slidebox.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;

public class LeaderboardFragment extends Fragment {
    private static final String TAG = "Leaderboard";
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private RecyclerView mFirestoreList;
    private FirestoreRecyclerAdapter adapter;
    private ListView listView;
//    private FirebaseList

    private LeaderboardViewModel leaderboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        leaderboardViewModel =
               new ViewModelProvider(this).get(LeaderboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        mFirestoreList =root.findViewById(R.id.recyclerView);

        Query query = firebaseFirestore.collection("users");
        FirestoreRecyclerOptions<UserPoints> options = new FirestoreRecyclerOptions.Builder<UserPoints>()
                .setQuery(query,UserPoints.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<UserPoints, UserPointsViewHolder>(options) {
            @NonNull
            @Override
            public UserPointsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_rangkinglist,parent,false);

                return new UserPointsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull UserPointsViewHolder holder, int position, @NonNull UserPoints model) {

                String fullName = model.getFirstName() + "  " + model.getLastName();
                holder.getFullName().setText(fullName);


            }
        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFirestoreList.setAdapter(adapter);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}