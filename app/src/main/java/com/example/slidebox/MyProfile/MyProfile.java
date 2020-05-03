package com.example.slidebox.MyProfile;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.slidebox.LogIn;
import com.example.slidebox.MyProfile.Edit.ProfileEditActivity;
import com.example.slidebox.R;
import com.example.slidebox.databinding.ActivityMyProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class MyProfile extends AppCompatActivity {

    private static final String TAG = "MyProfile";
    private ProfileViewModel profileViewModel;
    private Button buttonEditor;
    private Button buttonSigOut;
    private ImageView profileImage;
    private ImageView imageViewToday;
    private ImageView imageViewWeek;
    private ImageView imageViewMonth;
    private ImageView imageViewTotal;
    private TextView textViewFirstName;
    private TextView textViewLastName;
    private TextView textViewEmail;
    private Map<String, Object> userInfor = new HashMap<String, Object>();
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;
    ActivityMyProfileBinding dataBinding;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        buttonEditor = findViewById(R.id.button_editor);
        buttonSigOut = findViewById(R.id.buttonSignOut);
        profileImage = findViewById(R.id.profile_image);
        textViewFirstName = findViewById(R.id.textView_FirstName);
        textViewLastName = findViewById(R.id.textView_LastName);
        textViewEmail = findViewById(R.id.textView_Email);
        imageViewToday = findViewById(R.id.imageViewToday);
        imageViewWeek = findViewById(R.id.imageViewWeek);
        imageViewMonth = findViewById(R.id.imageViewMonth);
        imageViewTotal = findViewById(R.id.imageViewTotal);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        buttonSigOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplication(),LogIn.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        buttonEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), ProfileEditActivity.class);
                intent.putExtra("firstName", textViewFirstName.getText());
                intent.putExtra("lastName", textViewLastName.getText());
                intent.putExtra("eMail", textViewEmail.getText());
                Log.d(TAG, "ddddddddddddd");
                startActivity(intent);
            }
        });
        documentReference = dataBase.collection("users").document(user.getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        userInfor.putAll((HashMap<String, Object>) document.getData());
                        textViewFirstName.setText(userInfor.get("firstName").toString());
                        textViewLastName.setText(userInfor.get("lastName").toString());
                        textViewEmail.setText(user.getEmail());
                        achievementFun();
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        loadProfileImage();
        //  setup customer's toolbar with manifests setting
        Toolbar myToolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //------------------------------------------------

    }

    private void achievementFun() {
        imageViewToday.setImageResource(R.drawable.ic_check_box_black_24dp);
        imageViewWeek.setImageResource(R.drawable.ic_view_week_black_24dp);
        imageViewMonth.setImageResource(R.drawable.ic_format_align_justify_black_24dp);
        imageViewTotal.setImageResource(R.drawable.ic_spa_black_24dp);

        imageViewToday.setVisibility(View.INVISIBLE);
        imageViewWeek.setVisibility(View.INVISIBLE);
        imageViewMonth.setVisibility(View.INVISIBLE);
        imageViewTotal.setVisibility(View.INVISIBLE);

        if (Integer.valueOf(userInfor.get("currentPoints").toString()) >= 20) {
            imageViewToday.setVisibility(View.VISIBLE);
        }

        if (Integer.valueOf(userInfor.get("weeklyPoints").toString()) >= 100) {
            imageViewWeek.setVisibility(View.VISIBLE);
        }

        if (Integer.valueOf(userInfor.get("monthlyPoints").toString()) >= 1000) {
            imageViewMonth.setVisibility(View.VISIBLE);
        }
        if (Integer.valueOf(userInfor.get("totalPoints").toString()) >= 10000) {
            imageViewTotal.setVisibility(View.VISIBLE);
        }


    }

    private void loadProfileImage() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(uid + ".jpeg");
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                StorageReference mref = FirebaseStorage.getInstance().getReference();
                String s = getResources().getString(R.string.defaultImageChildPath);
                mref.child(s).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);
                    }
                });
            }
        });
    }


    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: setting auth");
        mAuth = FirebaseAuth.getInstance();
        Log.d(TAG, "setupFirebaseAuth: initial finished");
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Log.d(TAG, "onAuthStateChange:signed in" + user.getUid());
        } else {
            Log.d(TAG, "onAuthStateChanged:signed out");
        }
    }


}
