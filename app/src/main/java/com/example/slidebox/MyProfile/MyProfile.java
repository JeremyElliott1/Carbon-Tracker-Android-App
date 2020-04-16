package com.example.slidebox.MyProfile;



import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Application;
import android.transition.Slide;


import android.os.Bundle;
import android.transition.Fade;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.slidebox.R;
import com.example.slidebox.databinding.ActivityMyProfileBinding;

public class MyProfile extends AppCompatActivity {

    private Slide mSlide;
    private Fade mFade;
    private ViewGroup rootView;
    private TextView textView;
    private ProfileViewModel profileViewModel;
    ActivityMyProfileBinding dataBinding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_my_profile);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        Toolbar myToolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



    }

}
