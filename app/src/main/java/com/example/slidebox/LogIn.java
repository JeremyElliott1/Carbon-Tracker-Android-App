package com.example.slidebox;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {

    private EditText userEmail;
    private EditText userPassword;

    private Button login;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        userEmail=findViewById(R.id.user_email);
        userPassword=findViewById(R.id.user_password);
        login = findViewById(R.id.login);

        firebaseAuth = FirebaseAuth.getInstance();

        initialLogin();
        notRegistered();
    }

    private void initialLogin() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userEmail.getText().toString().isEmpty() && !userPassword.getText().toString().isEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(userEmail.getText().toString(),
                            userPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent();
                                        intent.setClass(getApplicationContext(), SlideBox.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(LogIn.this, task.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
            }
                else{
                    Toast.makeText(LogIn.this, "please enter email and password",
                            Toast.LENGTH_LONG).show();
                }
            }        });
    }

    private void notRegistered(){
        TextView not_registered = findViewById(R.id.not_registered);
        not_registered.setPaintFlags(not_registered.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        not_registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }        });
    }
}