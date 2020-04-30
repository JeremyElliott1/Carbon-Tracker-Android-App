package com.example.slidebox.ui.reusables;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.slidebox.Home;
import com.example.slidebox.R;
import com.example.slidebox.SlideBox;
import com.example.slidebox.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ReusablesItemAdd extends AppCompatActivity {

    //boolean value to indicate uniqueness of item name input
    private boolean duplicatedName = true;

    //widgets
    private EditText editTextName;
    private Button saveItemButton;
    private Button cancelItemAddButton;

    //Database instance
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    //User info
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reusable_item_add);

        //assigning widgets to respective xml widget
        editTextName = findViewById(R.id.editTextItemNameAdd);
        saveItemButton = findViewById(R.id.addItemButton);
        cancelItemAddButton = findViewById(R.id.reusable_cancelAddItemButton);

        //get userId
        User user = User.getInstance();
        userId = user.getUserId();

        // set OnClickListener for saveButton
        saveItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameIsUnique();
                saveItem(v);
                finish();
            }
        });

        // set OnClickListener for cancelButton
        cancelItemAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void saveItem(View v) {

        String name = editTextName.getText().toString();
        if (name.length() < 1) {
            Toast.makeText(this, "Error! \n Invalid name input \n please try again", Toast.LENGTH_SHORT).show();
        } else {

            if (duplicatedName = true) {
                Toast.makeText(this, "Error! \n Duplicated name input \n please try again", Toast.LENGTH_SHORT).show();
            } else {

                String points = "10";

                //Using custom object created in ReusableItem class.
                final ReusableItem item = new ReusableItem(name, points);

                //Adding points to user
                User user = User.getInstance();
                user.addPoints(Integer.parseInt(points));

        /*
        referencing the database then a collection "ReusableItems", document name is auto assigned,
        then adds the fields stored within "item" object
         @field = "name"
         @field = "points"
         */
                db.collection("users").document(userId).collection("ReusableItems").document()
                        .set(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ReusablesItemAdd.this, item.getName() + " saved", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ReusablesItemAdd.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    //Check to ensure item name input is unique
    private boolean nameIsUnique() {
        final String enteredName = editTextName.getText().toString();

        db.collection("users").document(userId).collection("ReusableItems").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            ReusableItem item = documentSnapshot.toObject(ReusableItem.class);
                            String name = item.getName();

                            duplicatedName = name.equals(enteredName);

                        }

                    }
                });
        return duplicatedName;
    }
}
