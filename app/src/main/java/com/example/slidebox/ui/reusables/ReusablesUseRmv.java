package com.example.slidebox.ui.reusables;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slidebox.R;
import com.example.slidebox.User;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class ReusablesUseRmv extends AppCompatActivity {

    //    Firebase database
    private FirebaseFirestore db = FirebaseFirestore.getInstance(); //firestore database

    //ReusablesUses
    private CollectionReference reusableUsesCollRef;
    private ReusablesUseAdapter usesRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reusable_use_remove);

        //get user ID & firestore db reference.
        User user = User.getInstance();
        String userId = user.getUserId();
        reusableUsesCollRef = db.collection("users")
                .document(userId).collection("ReusableUses"); //ReusableUses Collection reference

        setUpUsesRecyclerView();
        usesRecyclerViewAdapter.startListening();

        //Cancel Button
        Button cancelButton = findViewById(R.id.reusabuleCancelUseRemoveButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpUsesRecyclerView() {
        Query query = reusableUsesCollRef.orderBy("date", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<ReusableUse>()
                .setQuery(query, ReusableUse.class)
                .build();

        usesRecyclerViewAdapter = new ReusablesUseAdapter(options);

        RecyclerView useRecyclerView = findViewById(R.id.reusableUseRemoveRecyclerView);
        useRecyclerView.setHasFixedSize(true);
        useRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        useRecyclerView.setAdapter(usesRecyclerViewAdapter);

        /* simpleCallback defines what directions are supported */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override /* onMove is for drag and drop functionality which is not used */
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override /* onSwiped is for swipe movements. */
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                ReusableUse use;
                int usePoints;
                use = usesRecyclerViewAdapter.getReusablesUse(viewHolder.getAdapterPosition());
                usePoints = Integer.parseInt(use.getPoints());


                usesRecyclerViewAdapter.deleteReusableUse(viewHolder.getAdapterPosition());
                User user = User.getInstance();
                user.addPoints((usePoints * -1));
            }
        }).attachToRecyclerView(useRecyclerView);
    }
}
