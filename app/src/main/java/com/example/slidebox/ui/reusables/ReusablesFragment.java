package com.example.slidebox.ui.reusables;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slidebox.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class ReusablesFragment extends Fragment {
    //Buttons
    private Button addUseButton;
    private Button rmvUseButton;
    private Button addItemButton;
    private Button rmvItemButton;

    //    Firebase RecyclerAdapter
    private FirebaseFirestore db = FirebaseFirestore.getInstance(); //firestore database
    private CollectionReference reusableItemsCollRef = db.collection("ReusableItems"); //ReusableItems Collection reference
    private ReusablesItemAdapter itemRecyclerViewAdapter;


    //RecyclerView
    private RecyclerView itemRecyclerView;

    /*Untouched Code Starts----------------------------------------------------------------*/
    private ReusablesViewModel reusablesViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        reusablesViewModel = ViewModelProviders.of(this).get(ReusablesViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_reusables, container, false);
        final TextView textView = root.findViewById(R.id.reusables_MainHeader);
        /*Untouched Code Ends---------------------------------------------------------------- */

        setUpItemRecyclerView(root);
        itemRecyclerViewAdapter.startListening();

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
//                openReusableItemRmv();
            }
        });

        addUseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openReusableUseAdd();
            }
        });
        rmvUseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openReusableUseRmv();
            }
        });

        /*Untouched Code Starts----------------------------------------------------------------*/


        reusablesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

            }
        });
        return root;
    }
    /*Untouched Code Ends---------------------------------------------------------------- */

    //Navigates to ItemAdd Activity carrying the RecyclerView List data extra with it
    private void openReusableItemAdd() {
        Intent intent = new Intent(getActivity(), ReusablesItemAdd.class);
        startActivity(intent);
    }

//    public void openReusableItemRmv() {
//        Intent intent = new Intent(getActivity(), ReusablesItemRmv.class);
//        startActivity(intent);
//    }
//
//    public void openReusableUseAdd() {
//        Intent intent = new Intent(getActivity(), ReusablesUseAdd.class);
//        startActivity(intent);
//    }
//
//    public void openReusableUseRmv() {
//        Intent intent = new Intent(getActivity(), ReusablesUseAdd.class);
//        startActivity(intent);
//    }
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

}