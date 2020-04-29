package com.example.slidebox.ui.reusables;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.slidebox.R;
import com.example.slidebox.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReusablesUseAdd extends AppCompatActivity {

    //FireStore DB
    private FirebaseFirestore db = FirebaseFirestore.getInstance(); //firestore database
    private String userId;

    //Collection References
    private CollectionReference reusableItemsCollRef;

    //ArrayList ItemNames
    private List<ReusableItem> reusableItems = new ArrayList();
    private Date date;
    String selectedItemName;

    //Spinner & Buttons
    private Spinner itemNameSpinner;
    private ArrayAdapter<ReusableItem> adapter;
    private Button saveUseButton;
    private Button cancelUseAddButton;
    private TextView dateTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reusable_use_add);

        date = new Date();

        //get user ID & firestore db reference.
        User user = User.getInstance();
        userId = user.getUserId();
        reusableItemsCollRef = db.collection("users").document(userId).
                collection("ReusableItems"); //ReusableItems Collection reference


        saveUseButton = findViewById(R.id.reusable_addButton);
        cancelUseAddButton = findViewById(R.id.reusable_cancelUseAddButton);
        dateTextView = findViewById(R.id.reusable_useAddDateDisplayTextView);

        itemNameSpinner = findViewById(R.id.useItemNameSpinner);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, reusableItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemNameSpinner.setAdapter(adapter);
        itemNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ReusableItem item = (ReusableItem) parent.getSelectedItem();
                selectedItemName = item.getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // set OnClickListener for saveButton
        saveUseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUse();
                finish();
            }
        });

        // set OnClickListener for cancelButton
        cancelUseAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dateTextView.setText(getCurrentDateStr());

        getItemNames();
    }

    private void getItemNames() {
        reusableItemsCollRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                reusableItems.clear();

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ReusableItem item = documentSnapshot.toObject(ReusableItem.class);
                    reusableItems.add(item);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private String getCurrentDateStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yy");
        String strDate = formatter.format(date);
        return strDate;
    }

    private void saveUse() {
        String name = selectedItemName;
        final String points = "2";

        //Using custom object created in ReusableItem class.
        final ReusableUse use = new ReusableUse(selectedItemName, date, points);

        //Adding points to user
        User user = User.getInstance();
        user.addPoints(Integer.parseInt(points));

        /*
        referencing the database then a collection "ReusableItems", document name is auto assigned,
        then adds the fields stored within "item" object
         @field = "name"
         @field = "points"
         */
        db.collection("users").document(userId).collection("ReusableUses").document()
                .set(use).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ReusablesUseAdd.this, selectedItemName + " use saved \n Points awarded: " + points, Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ReusablesUseAdd.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
