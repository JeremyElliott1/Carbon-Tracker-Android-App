package com.example.slidebox;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    public static final String TAG = "TAG";
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private FirebaseFirestore db;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUp();
    }

    private void signUp() {
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.email_sign_up);
        password = findViewById(R.id.password_sign_up);
        confirmPassword=findViewById(R.id.confirm_password);
        progressBar = findViewById(R.id.progress_bar);
        findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!firstName.getText().toString().trim().isEmpty() && !lastName.getText().toString().trim().isEmpty() &&
                        !email.getText().toString().trim().isEmpty() && !password.getText().toString().isEmpty()) {
                    if (password.getText().toString().equals(confirmPassword.getText().toString())){
                        final String myFirstName = firstName.getText().toString().trim();
                    final String myLastName = lastName.getText().toString().trim();
                    final String myEmail = email.getText().toString().trim();
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(myEmail,
                            password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    userID = firebaseAuth.getCurrentUser().getUid();
                                                    DocumentReference documentReference = db.collection("users").document(userID);
                                                    Map<String, Object> user = new HashMap<>();
                                                    user.put("firstName", myFirstName);
                                                    user.put("lastName", myLastName);
                                                    user.put("totalPoints", 0);
                                                    user.put("currentPoints", 0);
                                                    user.put("dailyPoints", 0);
                                                    user.put("weeklyPoints", 0);
                                                    user.put("monthlyPoints", 0);
                                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d(TAG, "onSuccess: user profile is created for " + userID);
                                                        }
                                                    });
                                                    Intent intent = new Intent();
                                                    intent.setClass(getApplicationContext(), LogIn.class);
                                                    startActivity(intent);
                                                    Toast.makeText(SignUp.this, "Please verify your email address to sign in",
                                                            Toast.LENGTH_LONG).show();
                                                } else {
                                                    Toast.makeText(SignUp.this, task.getException().getMessage(),
                                                            Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                                    } else {
                                        Toast.makeText(SignUp.this, task.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                    else{
                        Toast.makeText(SignUp.this, "Please ensure you entered the same password ",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(SignUp.this, "Please enter your full details ",
                            Toast.LENGTH_LONG).show();
                }
            }        });

    }


}