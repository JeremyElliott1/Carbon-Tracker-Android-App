package com.example.slidebox.ui.reusables;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.slidebox.R;

public class ReusablesFragment extends Fragment {
    //Buttons
    private Button addUseButton;
    private Button rmvUseButton;
    private Button addItemButton;
    private Button rmvItemButton;

    //RecyclerView
    RecyclerView ItemRecyclerView;
    //String Array for recycler View to hold names of reusable objects.
    private String[] s1;

    //image array for recycler view to hold images of reusables objects.
    int[] ReusableItemImages =
            {R.drawable.reusable_cup, R.drawable.reusable_grocerybag,
                    R.drawable.reusable_lunchbox, R.drawable.reusable_snackbox,
                    R.drawable.reusable_straw};


    /*Untouched Code Starts----------------------------------------------------------------*/
    private ReusablesViewModel reusablesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        reusablesViewModel = ViewModelProviders.of(this).get(ReusablesViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_reusables, container, false);
        final TextView textView = root.findViewById(R.id.Reusable_MainHeader);
        /*Untouched Code Ends---------------------------------------------------------------- */


        RecyclerView ItemRecyclerView = root.findViewById(R.id.Reusable_ItemRecyclerView);

        //Buttons:
        addItemButton = root.findViewById(R.id.Reusables_AddItemsButton);
        rmvItemButton = root.findViewById(R.id.Reusables_RmvItemsButton);

        addUseButton = root.findViewById(R.id.Reusables_AddUsesButton);
        rmvUseButton = root.findViewById(R.id.Reusables_RmvUsesButton);

        //Button onClickListeners:
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Enter Item Details", Toast.LENGTH_SHORT).show(); //Toast widget displays a fading text when invoked.
                //Navigate to new Activity that allows User to enter Item Details.
            }
        });
        rmvItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Enter Item Details", Toast.LENGTH_SHORT).show(); //Toast widget displays a fading text when invoked.
                //Navigate to new Activity that allows User to enter Item Details.
            }
        });

        addUseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Enter Item Details", Toast.LENGTH_SHORT).show(); //Toast widget displays a fading text when invoked.
                //Navigate to new Activity that allows User to enter Item Details.
            }
        });
        rmvUseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Enter Item Details", Toast.LENGTH_SHORT).show(); //Toast widget displays a fading text when invoked.
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
}