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
import com.example.slidebox.databinding.ActivityMyProfileBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyProfile extends AppCompatActivity {

    private static final String TAG = "MyProfile";
    private ProfileViewModel profileViewModel;
    private Button buttonEditor;
    ActivityMyProfileBinding dataBinding;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_my_profile);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        //  profileImage = findViewById(R.id.profile_image);
        buttonEditor = findViewById(R.id.button_editor);



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
