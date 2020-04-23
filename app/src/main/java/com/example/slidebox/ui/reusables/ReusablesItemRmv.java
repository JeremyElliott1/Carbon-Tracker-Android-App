package com.example.slidebox.ui.reusables;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ReusablesItemRmv extends AppCompatActivity {
    //Cancel Button
    private Button cancelButton;
    //    Firebase RecyclerAdapter
    private FirebaseFirestore db = FirebaseFirestore.getInstance(); //firestore database
    private CollectionReference reusableItemsCollRef;
    private String userId;
    private ReusablesItemAdapter itemRecyclerViewAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reusable_item_remove);

        //get userId
        User user = new User();
        userId = user.getUserId();
        reusableItemsCollRef = db.collection("users").document(userId).collection("ReusableItems"); //ReusableItems Collection reference

        setUpItemRecyclerView();
        itemRecyclerViewAdapter.startListening();


        cancelButton = findViewById(R.id.reusabuleCancelItemRemoveButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpItemRecyclerView() {
        Query query = reusableItemsCollRef.orderBy("name", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<ReusableItem>()
                .setQuery(query, ReusableItem.class)
                .build();

        itemRecyclerViewAdapter = new ReusablesItemAdapter(options);

        RecyclerView itemRecyclerView = findViewById(R.id.reusableItemRemoveRecyclerView);
        itemRecyclerView.setHasFixedSize(true);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemRecyclerView.setAdapter(itemRecyclerViewAdapter);

        /* simpleCallback defines what directions are supported */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override /* onMove is for drag and drop functionality which is not used */
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override /* onSwiped is for swipe movements. */
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                ReusableItem item;
                int itemPoints;
                item = itemRecyclerViewAdapter.getReusablesItem(viewHolder.getAdapterPosition());
                itemPoints = Integer.parseInt(item.getPoints()); // NEEDS TO BE FIXED SO ITS SAVING JUST AN INT NOT A STRING FOR ITEM POINTS


                itemRecyclerViewAdapter.deleteReusableItem(viewHolder.getAdapterPosition());
                User user = new User();
                user.addPoints((itemPoints * -1));
            }
        }).attachToRecyclerView(itemRecyclerView);
    }


}
