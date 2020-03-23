package com.example.slidebox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HelpOptions extends AppCompatActivity {
    private Button button_back;
    private Button help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_help);
        button_back = (Button)findViewById(R.id.button4);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpOptions.this, SlideBox.class);
                startActivity(intent);
            }
        });

        help = (Button)findViewById(R.id.button);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Should add content", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
