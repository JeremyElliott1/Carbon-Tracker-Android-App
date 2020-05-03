package com.example.slidebox.ui.reusables;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.slidebox.R;
import com.example.slidebox.User;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
public class ReusablesFragment extends Fragment {
    //Buttons
    private Button addUseButton;
    private Button rmvUseButton;
    private Button addItemButton;
    private Button rmvItemButton;
    //    Firebase database
    private FirebaseFirestore db = FirebaseFirestore.getInstance(); //firestore database
    private String userId;
    //ReusablesUses
    private CollectionReference reusableUsesCollRef;
    private ReusablesUseAdapter usesRecyclerViewAdapter;
    //ReusableItems
    private CollectionReference reusableItemsCollRef;
    private ReusablesItemAdapter itemRecyclerViewAdapter;
    /*Untouched Code Starts----------------------------------------------------------------*/
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_reusables, container, false);
        final TextView textView = root.findViewById(R.id.reusables_MainHeader);
        /*Untouched Code Ends---------------------------------------------------------------- */
        //get user ID & firestore db reference.
        User user = User.getInstance();
        userId = user.getUserId();
        reusableItemsCollRef = db.collection("users").document(userId)
                .collection("ReusableItems"); //ReusableItems Collection reference
        reusableUsesCollRef = db.collection("users").document(userId)
                .collection("ReusableUses"); //ReusableUses Collection reference
        setUpItemRecyclerView(root);
        itemRecyclerViewAdapter.startListening();
        setUpUsesRecyclerView(root);
        usesRecyclerViewAdapter.startListening();
//Buttons:
        addItemButton = root.findViewById(R.id.reusables_AddItemsButton);
        rmvItemButton = root.findViewById(R.id.reusables_RmvItemsButton);
        addUseButton = root.findViewById(R.id.reusables_AddUsesButton);
        rmvUseButton = root.findViewById(R.id.reusables_RmvUsesButton);
//Button onClickListeners:
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReusableItemAdd();
            }
        });
        rmvItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReusableItemRmv();
            }
        });
        addUseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReusableUseAdd();
            }
        });
        rmvUseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReusableUseRmv();
            }
        });
        /*Untouched Code Starts----------------------------------------------------------------*/
        return root;
    }
    /*Untouched Code Ends---------------------------------------------------------------- */
    //Button Navigation
    private void openReusableItemAdd() {
        Intent intent = new Intent(getActivity(), ReusablesItemAdd.class);
        startActivity(intent);
    }
    private void openReusableItemRmv() {
        Intent intent = new Intent(getActivity(), ReusablesItemRmv.class);
        startActivity(intent);
    }
    private void openReusableUseAdd() {
        Intent intent = new Intent(getActivity(), ReusablesUseAdd.class);
        startActivity(intent);
    }
    private void openReusableUseRmv() {
        Intent intent = new Intent(getActivity(), ReusablesUseRmv.class);
        startActivity(intent);
    }
    private void setUpItemRecyclerView(View v) {
        Query query = reusableItemsCollRef.orderBy("name", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<ReusableItem>()
                .setQuery(query, ReusableItem.class)
                .build();
        itemRecyclerViewAdapter = new ReusablesItemAdapter(options);
        RecyclerView itemRecyclerView = v.findViewById(R.id.reusable_ItemRecyclerView);
        itemRecyclerView.setHasFixedSize(true);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemRecyclerView.setAdapter(itemRecyclerViewAdapter);
    }
    private void setUpUsesRecyclerView(View v) {
        Query query = reusableUsesCollRef.orderBy("date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<ReusableUse>()
                .setQuery(query, ReusableUse.class)
                .build();
        usesRecyclerViewAdapter = new ReusablesUseAdapter(options);
        RecyclerView useRecyclerView = v.findViewById(R.id.reusables_UsesRecyclerView);
        useRecyclerView.setHasFixedSize(true);
        useRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        useRecyclerView.setAdapter(usesRecyclerViewAdapter);
    }
}

