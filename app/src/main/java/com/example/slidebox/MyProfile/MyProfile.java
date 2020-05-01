package com.example.slidebox.MyProfile;



import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.transition.Slide;

import android.app.Application;


import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.slidebox.MyProfile.Edit.ProfileEditActivity;
import com.example.slidebox.R;
import com.example.slidebox.User;
import com.example.slidebox.databinding.ActivityMyProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.common.base.Strings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MyProfile extends AppCompatActivity {

    private static final String TAG = "MyProfile";
    private ProfileViewModel profileViewModel;
    private Button buttonEditor;
    private ImageView profileImage;
    private TextView textViewFirstName;
    private TextView textViewLastName;
    private TextView textViewEmail;
    private StorageReference mRef = FirebaseStorage.getInstance().getReference();
    ActivityMyProfileBinding dataBinding;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_my_profile);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        buttonEditor = findViewById(R.id.button_editor);
        profileImage = findViewById(R.id.profile_image);
        textViewFirstName = findViewById(R.id.textView_FirstName);
        textViewLastName = findViewById(R.id.textView_LastName);
        textViewEmail = findViewById(R.id.textView_Email);

        //profileViewModel = new ProfileViewModel(this);
        loadProfileImage();
        ReadData readData = new ReadData();

        String s = readData.getFirstName();
        Log.d(TAG,"sdd" +s);
        //profileViewModel.getUserInfor();
       // loadProfileInfor();


        //  setup customer's toolbar with manifests setting
        Toolbar myToolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //------------------------------------------------

        //-------------------FriebaseAuth-------------------------------
        setupFirebaseAuth();

       buttonEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),ProfileEditActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loadProfileImage(){
        String s = getResources().getString(R.string.defaultImageChildPath);
        mRef.child(s).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });
    }



    private void setupFirebaseAuth(){
        Log.d(TAG,"setupFirebaseAuth: setting auth");
        mAuth = FirebaseAuth.getInstance();
        Log.d(TAG,"setupFirebaseAuth: initial finished");
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            Log.d(TAG,"onAuthStateChange:signed in" + user.getUid());
        }else {
            Log.d(TAG,"ononAuthStateChanged:signed out");
        }
    }




}
