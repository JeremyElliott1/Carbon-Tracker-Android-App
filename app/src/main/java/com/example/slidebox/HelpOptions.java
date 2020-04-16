package com.example.slidebox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HelpOptions extends AppCompatActivity {
    private Button button_back;
    private Button about;
    private Button shop;
    private Button leaderboard;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_help);
        textView = (TextView)findViewById(R.id.textView10);

        button_back = (Button)findViewById(R.id.button4);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpOptions.this, SlideBox.class);
                startActivity(intent);
            }
        });

        about = (Button)findViewById(R.id.button);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("This app is for reuseable, recycleable and environmental protection");
            }
        });

        shop = (Button)findViewById(R.id.button6);
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("Users can use point to exchange certain items");
            }
        });

        leaderboard = (Button)findViewById(R.id.button7);
        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("Leaderboard shows users' points for you to compare");
            }
        });
    }
}
