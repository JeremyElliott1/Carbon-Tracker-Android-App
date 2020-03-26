package com.example.slidebox.ui.reusables;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slidebox.R;
import com.example.slidebox.ui.reusables.adapters.ItemsRecyclerAdapter;

import java.util.ArrayList;

public class ReusablesItemAdd extends AppCompatActivity {

    //RecyclerView
    private RecyclerView itemRecyclerView;
    private RecyclerView.Adapter itemAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //Recycler DataSet -----------------
    private ArrayList<ReusableItem> itemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reusable_itemlist_add);
    }

    public void createItemRecyclerView() {
        itemRecyclerView = this.findViewById(R.id.Reusable_ItemRecyclerView);
        itemRecyclerView.setHasFixedSize(true);//Improves efficiency as item View size is not expected to change.

        // assigning layout manager
        layoutManager = new LinearLayoutManager(this);
        //assigning Adapter
        itemAdapter = new ItemsRecyclerAdapter(itemList);

        //setting Adapter and Manager to RecyclerView
        itemRecyclerView.setLayoutManager(layoutManager);
        itemRecyclerView.setAdapter(itemAdapter);

    }
}
