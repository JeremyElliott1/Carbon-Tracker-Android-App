package com.example.slidebox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsOptions extends AppCompatActivity {
    private Button button_About;
    private Button button_Tos;
    private Button button_Security;
    private Button button_Privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_settings);

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

        button_Security = (Button) findViewById(R.id.button_Security);
        button_Security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetting_Security();
            }
        });

        button_Privacy = (Button) findViewById(R.id.button_privacy_policy);
        button_Privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetting_Privacy();
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

    public void openSetting_Security() {
        Intent intent = new Intent(this, SettingsSecurity.class);
        startActivity(intent);
    }

    public void openSetting_Privacy() {
        Intent intent = new Intent(this, SettingsPrivacyPolicy.class);
        startActivity(intent);
    }


}
