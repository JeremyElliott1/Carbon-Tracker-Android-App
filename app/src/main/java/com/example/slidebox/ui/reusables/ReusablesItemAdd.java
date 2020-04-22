package com.example.slidebox.ui.reusables;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.slidebox.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ReusablesItemAdd extends AppCompatActivity {

    //"Key" or "Field" in DB:
    private static final String KEY_NAME = "Name";
    private static final String KEY_POINTS = "Points";

    //widgets
    private EditText editTextName;
    private Button saveItemButton;

    //Database instance
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reusable_item_add);

        //assigning widgets to respective xml widget
//        editTextName = findViewById(R.id.editTextItemNameAdd);
//        saveItemButton = findViewById(R.id.addItemButton);

        // set OnClickListener for SaveButton
        saveItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem(v);
            }
        });


    }

    private void saveItem(View v) {
        String name = editTextName.getText().toString();
        String points = "10";
        Toast.makeText(this, "saving " + name, Toast.LENGTH_SHORT).show(); // displays user input to show item is saved

        //Using custom object created in ReusableItem class.
        final ReusableItem item = new ReusableItem(name, points);

        /*
        referencing the database then a collection "ReusableItems", document name is auto assigned,
        then adds the fields stored within "item" object
         @field = "name"
         @field = "points"
         */
        db.collection("ReusableItems").document().set(item).addOnSuccessListener(new OnSuccessListener<Void>() {
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
