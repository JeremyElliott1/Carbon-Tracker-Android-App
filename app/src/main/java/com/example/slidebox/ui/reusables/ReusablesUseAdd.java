package com.example.slidebox.ui.reusables;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.slidebox.R;
import com.example.slidebox.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReusablesUseAdd extends AppCompatActivity {

    //FireStore DB
    private FirebaseFirestore db = FirebaseFirestore.getInstance(); //firestore database
    private String userId;


    //Collection References
    private CollectionReference reusableItemsCollRef;


    //ArrayList ItemNames
    private List itemNames = new ArrayList();

    //Spinner & Buttons
    private Spinner itemNameSpinner;
    private Button saveUseButton;
    private Button cancelUseAddButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reusable_use_add);

        //get user ID & firestore db reference.
        User user = new User();
        userId = user.getUserId();
        reusableItemsCollRef = db.collection("users").document(userId).
                collection("ReusableItems"); //ReusableItems Collection reference


        itemNameSpinner = findViewById(R.id.useItemNameSpinner);
        saveUseButton = findViewById(R.id.reusable_addButton);
        cancelUseAddButton = findViewById(R.id.reusable_cancelUseAddButton);


        // set OnClickListener for cancelButton
        cancelUseAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
