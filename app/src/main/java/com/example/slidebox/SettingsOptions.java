package com.example.slidebox;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class SettingsOptions extends AppCompatActivity {
    private Button button_About;
    private Button button_Tos;
    private Button button_Privacy;
    private Button button_Help;
    private Switch switch_sounds;
    private Switch switch_dark_mode;
    private AudioManager amanager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_settings);

        //  setup customer's toolbar with manifests setting
        Toolbar myToolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //------------------------------------------------


        amanager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        button_About = (Button) findViewById(R.id.button_About);
        button_About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetting_About();
            }
        });

        button_Tos = (Button) findViewById(R.id.button_Tos);
        button_Tos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetting_Tos();
            }
        });

        button_Privacy = (Button) findViewById(R.id.button_privacy_policy);
        button_Privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetting_Privacy();
            }
        });

        button_Help = (Button) findViewById(R.id.button_Help);
        button_Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetting_Help();
            }
        });


        //Global mute settings & saving state of switch
        //https://stackoverflow.com/questions/10895882/mute-the-global-sound-in-android
        //https://www.youtube.com/watch?v=RyiTx8lWdx0
        switch_sounds = (Switch) findViewById(R.id.sounds);
        final SharedPreferences sharedPreferences = getSharedPreferences("save", 0);

        switch_sounds.setChecked(sharedPreferences.getBoolean("value", true));
        switch_sounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                if (switch_sounds.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("save", 0).edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    switch_sounds.setChecked(true);
                    AudioManager amanager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
                    amanager.setStreamMute(AudioManager.STREAM_ALARM, false);
                    amanager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    amanager.setStreamMute(AudioManager.STREAM_RING, false);
                    amanager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
                }
                else {
                    SharedPreferences.Editor editor = getSharedPreferences("save", 0).edit();
                    editor.putBoolean("value", false);
                    editor.apply();
                    switch_sounds.setChecked(false);
                    AudioManager amanager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
                    amanager.setStreamMute(AudioManager.STREAM_ALARM, true);
                    amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    amanager.setStreamMute(AudioManager.STREAM_RING, true);
                    amanager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
                }
            }
        });

        //night-mode tutorial
        //https://www.youtube.com/watch?v=QhGf8fGJM8U
        switch_dark_mode = (Switch) findViewById(R.id.dark);
        switch_dark_mode.setChecked(sharedPreferences.getBoolean("dark", true));
        switch_dark_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                if (switch_dark_mode.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("save", 0).edit();
                    editor.putBoolean("dark", true);
                    editor.apply();
                    switch_dark_mode.setChecked(true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else {
                    SharedPreferences.Editor editor = getSharedPreferences("save", 0).edit();
                    editor.putBoolean("dark", false);
                    editor.apply();
                    switch_dark_mode.setChecked(false);
                    getTheme().applyStyle(R.style.AppTheme, true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                }
            }
        });
    }


    public void openSetting_About() {
        Intent intent = new Intent(this, SettingsAbout.class);
        startActivity(intent);
    }

    public void openSetting_Tos() {
        Intent intent = new Intent(this, SettingsTos.class);
        startActivity(intent);
    }

    public void openSetting_Privacy() {
        Intent intent = new Intent(this, SettingsPrivacyPolicy.class);
        startActivity(intent);
    }

    public void openSetting_Help() {
        Intent intent = new Intent(this, HelpOptions.class);
        startActivity(intent);
    }
    public static boolean getDarkMode(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean("dark", true);
    }
}
