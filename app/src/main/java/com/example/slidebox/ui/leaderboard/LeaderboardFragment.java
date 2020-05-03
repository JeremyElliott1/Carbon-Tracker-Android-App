package com.example.slidebox.ui.leaderboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slidebox.R;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class LeaderboardFragment extends Fragment implements RankingAdapter.OnThumbupClick {
    private static final String TAG = "Leaderboard";
    private static final int TODAY_REQUEST = 1;
    private static final int WEEK_REQUEST = 2;
    private static final int MONTH_REQUEST = 3;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private RecyclerView mFirestoreList;
    private RankingAdapter adapter;
    private Button buttonToday;
    private Button buttonWeek;
    private Button buttonMonth;
    private View view;
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private ListView listView;
//    private FirebaseList

    private LeaderboardViewModel leaderboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        leaderboardViewModel =
                new ViewModelProvider(this).get(LeaderboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        view = root;
        buttonToday = root.findViewById(R.id.buttonToday);
        buttonWeek = root.findViewById(R.id.buttonWeek);
        buttonMonth = root.findViewById(R.id.buttonMonth);
        buttonToday.setBackgroundColor(getResources().getColor(R.color.colorLeaderBoardLight));
        buttonWeek.setBackgroundColor(getResources().getColor(R.color.colorLeaderBoardLight));
        buttonMonth.setBackgroundColor(getResources().getColor(R.color.colorLeaderBoardLight));

        initialLoad(view);
        addThumbupNumToUserCollection();
        addThumbupCollection();

        buttonToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialLoad(view);
            }
        });

        buttonWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weekReyclerView(view);
            }
        });

        buttonMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthReyclerView(view);
            }
        });

        return root;
    }

    private void addThumbupNumToUserCollection() {
        firebaseFirestore.collection("users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (document.get("numThumbup") == null) {
                            int i = 0;
                            Map<String, Object> map = new HashMap<>();
                            map.put("numThumbup", i);
                            firebaseFirestore.collection("users").document(user.getUid()).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Added thumbup! ");
                                }
                            });
                        }
                        ;
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void addThumbupCollection() {

        firebaseFirestore.collection("users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (document.get(user.getUid()) == null) {
                            int i = 0;
                            Map<String, Object> map = new HashMap<>();
                            map.put(user.getUid(), String.valueOf(i));
                            firebaseFirestore.collection("users").document(user.getUid()).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Added thumbup! ");
                                }
                            });
                        }
                        ;
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void initialLoad(View root) {
        buttonToday.setBackgroundColor(getResources().getColor(R.color.colorLeaderBoardDark));
        buttonWeek.setBackgroundColor(getResources().getColor(R.color.colorLeaderBoardLight));
        buttonMonth.setBackgroundColor(getResources().getColor(R.color.colorLeaderBoardLight));
        mFirestoreList = root.findViewById(R.id.recyclerView);
        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(3)
                .build();
        Query query = firebaseFirestore.collection("users").orderBy("totalPoints", Query.Direction.DESCENDING);
        FirestorePagingOptions<UserPoints> options = new FirestorePagingOptions.Builder<UserPoints>()
                .setQuery(query, config, UserPoints.class)
                .build();
        adapter = new RankingAdapter(options, TODAY_REQUEST, this);
        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFirestoreList.setAdapter(adapter);
        adapter.startListening();
    }

    private void weekReyclerView(View root) {
        buttonWeek.setBackgroundColor(getResources().getColor(R.color.colorLeaderBoardDark));
        buttonToday.setBackgroundColor(getResources().getColor(R.color.colorLeaderBoardLight));
        buttonMonth.setBackgroundColor(getResources().getColor(R.color.colorLeaderBoardLight));
        mFirestoreList = root.findViewById(R.id.recyclerView);
        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(3)
                .build();
        Query query = firebaseFirestore.collection("users").orderBy("weeklyPoints", Query.Direction.DESCENDING);
        FirestorePagingOptions<UserPoints> options = new FirestorePagingOptions.Builder<UserPoints>()
                .setQuery(query, config, UserPoints.class)
                .build();
        adapter = new RankingAdapter(options, WEEK_REQUEST, this);
        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFirestoreList.setAdapter(adapter);
        adapter.startListening();
    }

    private void monthReyclerView(View root) {
        buttonMonth.setBackgroundColor(getResources().getColor(R.color.colorLeaderBoardDark));
        buttonToday.setBackgroundColor(getResources().getColor(R.color.colorLeaderBoardLight));
        buttonWeek.setBackgroundColor(getResources().getColor(R.color.colorLeaderBoardLight));
        mFirestoreList = root.findViewById(R.id.recyclerView);
        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(3)
                .build();
        Query query = firebaseFirestore.collection("users").orderBy("monthlyPoints", Query.Direction.DESCENDING);
        FirestorePagingOptions<UserPoints> options = new FirestorePagingOptions.Builder<UserPoints>()
                .setLifecycleOwner(this)
                .setQuery(query, config, UserPoints.class)
                .build();
        adapter = new RankingAdapter(options, MONTH_REQUEST, this);

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFirestoreList.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onClick(DocumentSnapshot documentSnapshot, int position) {

    }
}