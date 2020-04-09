package com.example.slidebox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsAbout extends AppCompatActivity {
    private Button button_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_about);
        button_back = (Button) findViewById(R.id.back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetting_back();
            }
        });
    }
    public void openSetting_back() {
        Intent intent = new Intent(this, SettingsOptions.class);
        startActivity(intent);
    }
}
