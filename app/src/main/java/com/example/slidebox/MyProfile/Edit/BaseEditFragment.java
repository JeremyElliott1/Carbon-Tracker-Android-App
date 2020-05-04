package com.example.slidebox.MyProfile.Edit;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.slidebox.LogIn;
import com.example.slidebox.MyProfile.MyProfile;
import com.example.slidebox.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

public class BaseEditFragment extends Fragment{
    private static final String TAG = "Base";
    private Toolbar mToolbar;
    private ImageView imageViewChangePW;
    private ImageView imageViewProfileEdit;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextPasswrod;
    private Button buttonConfirm;
    private Button buttonUpdate;
    private FirebaseFirestore firebaseStorage = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;
    private String password = "";

    public static BaseEditFragment newInstance() {
        return new BaseEditFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_edit_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mToolbar = getView().findViewById(R.id.toolbarBaseEditor);
        editTextFirstName = getView().findViewById(R.id.textViewFirstName);
        editTextLastName = getView().findViewById(R.id.textViewLastName);
        editTextEmail = getView().findViewById(R.id.textViewEmail);
        editTextPasswrod = getView().findViewById(R.id.textViewPassword);
        buttonConfirm = getView().findViewById(R.id.buttonVerifyPassword);
        buttonUpdate = getView().findViewById(R.id.buttonUpdata);
        imageViewProfileEdit = getView().findViewById(R.id.imageView_picture_baseedit);
        imageViewChangePW = getView().findViewById(R.id.imageViewChangePword);
        user = mAuth.getCurrentUser();
        final Intent data = getActivity().getIntent();
        editTextFirstName.setText(data.getStringExtra("firstName"));
        editTextLastName.setText(data.getStringExtra("lastName"));
        editTextEmail.setText(data.getStringExtra("eMail"));
        loadProfileImage();

        buttonConfirm.setVisibility(View.INVISIBLE);
        editTextPasswrod.setVisibility(View.INVISIBLE);
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editTextEmail.getText().toString().equals(data.getStringExtra("eMail"))) {
                    buttonConfirm.setVisibility(View.INVISIBLE);
                    editTextPasswrod.setVisibility(View.INVISIBLE);
                } else {
                    buttonConfirm.setVisibility(View.VISIBLE);
                    editTextPasswrod.setVisibility(View.VISIBLE);
                }
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextPasswrod.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "password is" +
                            " null!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    password = editTextPasswrod.getText().toString();
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Password is" +
                                        " correct!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    return;
                }
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextFirstName.getText().toString().trim().isEmpty() ||
                        editTextLastName.getText().toString().trim().isEmpty() ||
                        editTextEmail.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "One or many field are empty.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (editTextFirstName.getText().toString().trim().equals((data.getStringExtra("firstName")).toString()) &&
                        editTextLastName.getText().toString().trim().equals((data.getStringExtra("lastName")).toString()) &&
                        editTextEmail.getText().toString().trim().equals((data.getStringExtra("eMail")).toString())) {
                    Intent intent = new Intent(getActivity(), MyProfile.class);
                    startActivity(intent);
                    return;
                } else if (editTextEmail.getText().toString().trim().equals((data.getStringExtra("eMail"))) == false) {
                    final String email = editTextEmail.getText().toString().trim();
                    if (password.isEmpty()) {
                        Toast.makeText(getActivity(), "password is" +
                                " null!", Toast.LENGTH_SHORT).show();
                    } else {
                        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);
                        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.d(TAG, "STAGE 1");
                                            if (task.isSuccessful()) {
                                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Log.d(TAG, "STAGE 2");
                                                        if (task.isSuccessful()) {
                                                            DocumentReference documentReference = firebaseStorage.collection("users").document(user.getUid());
                                                            Map<String, Object> editor = new HashMap<>();
                                                            editor.put("email", email);
                                                            editor.put("firstName", editTextFirstName.getText().toString().trim());
                                                            editor.put("lastName", editTextLastName.getText().toString().trim());
                                                            documentReference.update(editor);
                                                            SignOut();
                                                        }
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getActivity(), "Update failed!", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getActivity(), "Can not update!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
                    }
                } else {
                    final String email = editTextEmail.getText().toString().trim();
                    user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "STAGE 1");
                            if (task.isSuccessful()) {
                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.d(TAG, "STAGE 2");
                                        if (task.isSuccessful()) {
                                            DocumentReference documentReference = firebaseStorage.collection("users").document(user.getUid());
                                            Map<String, Object> editor = new HashMap<>();
                                            editor.put("email", email);
                                            editor.put("firstName", editTextFirstName.getText().toString().trim());
                                            editor.put("lastName", editTextLastName.getText().toString().trim());
                                            documentReference.update(editor);
                                            Toast.makeText(getActivity(), "Profile has been updated.Please go to the new E-mail to verify!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getActivity(), MyProfile.class);
                                            startActivity(intent);
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "Update failed!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Can not update!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        imageViewProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_baseEditFragment_to_pictureEditFragment);
            }
        });

        imageViewChangePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_baseEditFragment_to_passWordChange2);
            }
        });

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyProfile.class);
                startActivity(intent);
            }
        });
    }

    private void SignOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), LogIn.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void loadProfileImage() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(uid + ".jpeg");
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageViewProfileEdit);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                StorageReference mref = FirebaseStorage.getInstance().getReference();
                String s = getResources().getString(R.string.defaultImageChildPath);
                mref.child(s).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(imageViewProfileEdit);
                    }
                });
            }
        });
    }
}