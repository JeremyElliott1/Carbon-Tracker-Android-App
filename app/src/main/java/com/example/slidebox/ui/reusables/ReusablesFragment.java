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
import com.example.slidebox.ui.reusables.adapters.ItemsRecyclerAdapter;

import java.util.ArrayList;

public class ReusablesFragment extends Fragment {
    //Buttons
    private Button addUseButton;
    private Button rmvUseButton;
    private Button addItemButton;
    private Button rmvItemButton;

    //RecyclerView
    private RecyclerView itemRecyclerView;
    private RecyclerView.Adapter itemAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //Recycler DataSet -----------------
    private ArrayList<ReusableItem> reusableItemList;

    //image array for recycler view to hold images of reusables objects.


    /*Untouched Code Starts----------------------------------------------------------------*/
    private ReusablesViewModel reusablesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        reusablesViewModel = ViewModelProviders.of(this).get(ReusablesViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_reusables, container, false);
        final TextView textView = root.findViewById(R.id.reusables_MainHeader);
        /*Untouched Code Ends---------------------------------------------------------------- */

        //Creating Recycler and initializing List Data Set:
        itemRecyclerView = root.findViewById(R.id.Reusable_ItemRecyclerView); //Can this be moved to createItemRecyclerView()?
        createItemList();
        createItemRecyclerView();

        //Buttons:
        addItemButton = root.findViewById(R.id.reusables_AddItemsButton);
        rmvItemButton = root.findViewById(R.id.reusables_RmvItemsButton);

        addUseButton = root.findViewById(R.id.reusables_AddUsesButton);
        rmvUseButton = root.findViewById(R.id.reusables_RmvUsesButton);

        //Button onClickListeners:
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Enter Item Details to add", Toast.LENGTH_SHORT).show(); //Toast widget displays a fading text when invoked.
                openReusableItemAdd();

                //Navigate to new Activity that allows User to enter Item Details.
            }
        });
        rmvItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Enter Item Details to save", Toast.LENGTH_SHORT).show(); //Toast widget displays a fading text when invoked.
                //Navigate to new Activity that allows User to enter Item Details.
            }
        });

        addUseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Enter Use Details to Add", Toast.LENGTH_SHORT).show(); //Toast widget displays a fading text when invoked.
                //Navigate to new Activity that allows User to enter Item Details.
            }
        });
        rmvUseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Enter Use Details to remove", Toast.LENGTH_SHORT).show(); //Toast widget displays a fading text when invoked.
                //Navigate to new Activity that allows User to enter Item Details.
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
    public void openReusableItemAdd() {
        Intent intent = new Intent(getActivity(), ReusablesItemAdd.class);
        startActivity(intent);
    }

    public void createItemList() {
        //adding items for recycler
        reusableItemList = new ArrayList<>();
        reusableItemList.add(new ReusableItem(R.drawable.coffee, "Coffeeee!!!"));
        reusableItemList.add(new ReusableItem(R.drawable.coffee, "Coffeeee!!!"));
        reusableItemList.add(new ReusableItem(R.drawable.coffee, "Coffeeee!!!"));
        reusableItemList.add(new ReusableItem(R.drawable.coffee, "Coffeeee!!!"));
    }

    public void createItemRecyclerView() {
        itemRecyclerView.setHasFixedSize(true);//Improves efficiency as item View size is not expected to change.
        // assigning layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        //assigning Adapter
        itemAdapter = new ItemsRecyclerAdapter(reusableItemList);

        //setting Adapter and Manager to RecyclerView
        itemRecyclerView.setLayoutManager(layoutManager);
        itemRecyclerView.setAdapter(itemAdapter);
    }
}