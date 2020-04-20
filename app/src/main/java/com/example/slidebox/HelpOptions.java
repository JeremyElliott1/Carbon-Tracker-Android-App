package com.example.slidebox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelpOptions extends AppCompatActivity {
    private Button button_back;
    private Button about;
    private Button shop;
    private Button leaderboard;
    private Button recyclable;
    private Button reusable;
    private Button travel;
    private Button home;
    private Button profile;
    private Button settings;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_help);
        textView = (TextView)findViewById(R.id.textView10);

        button_back = (Button)findViewById(R.id.back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpOptions.this, SettingsOptions.class);
                startActivity(intent);
            }
        });

        about = (Button)findViewById(R.id.button_help_about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("This app is for reuseable, recycleable and environmental protection.");
            }
        });

        shop = (Button)findViewById(R.id.button_help_shop);
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("Users can use point to exchange certain items.");
            }
        });

        leaderboard = (Button)findViewById(R.id.button_help_leaderboard);
        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("Leaderboard shows users' points for you to compare.");
            }
        });

        recyclable = (Button)findViewById(R.id.button_help_recyclable);
        recyclable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("Input items you recycle to gain points.");
            }
        });

        reusable = (Button)findViewById(R.id.button_help_reusable);
        reusable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("Input reusable items you use to gain points.");
            }
        });

        travel = (Button)findViewById(R.id.button_help_travel);
        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("A journey tracker to monitor your travel, gain points by reducing the time and mode of transport.");
            }
        });

        home = (Button)findViewById(R.id.button_help_home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("Home page displays your current points, and daily goals in order to gain points.");
            }
        });

        settings = (Button)findViewById(R.id.button_help_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("In settings you can change the the theme of the app, mute the app or notifications and view our policies.");
            }
        });

        profile = (Button)findViewById(R.id.button_help_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("View your profile here and change your display picture.");
            }
        });
    }
}
