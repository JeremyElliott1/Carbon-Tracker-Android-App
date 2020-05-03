package com.example.slidebox.MyProfile.Edit;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.slidebox.MyProfile.MyProfile;
import com.example.slidebox.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class BaseEditFragment extends Fragment {
    private Toolbar mToolbar;
    private ImageView imageViewChangePW;
    private ImageView imageViewProfileEdit;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private Button buttonUpdate;

    private boolean imageFlag = false;
    private FirebaseFirestore firebaseStorage = FirebaseFirestore.getInstance();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;

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
        editTextFirstName=getView().findViewById(R.id.textViewFirstName);
        editTextLastName=getView().findViewById(R.id.textViewLastName);
        editTextEmail=getView().findViewById(R.id.textViewEmail);
        buttonUpdate = getView().findViewById(R.id.buttonUpdata);
        imageViewProfileEdit = getView().findViewById(R.id.imageView_picture_baseedit);
        imageViewChangePW = getView().findViewById(R.id.imageViewChangePword);
        user = mAuth.getCurrentUser();
        Intent data = getActivity().getIntent();
        editTextFirstName.setText(data.getStringExtra("firstName"));
        editTextLastName.setText(data.getStringExtra("lastName"));
        editTextEmail.setText(data.getStringExtra("eMail"));
        loadProfileImage();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextFirstName.getText().toString().trim().isEmpty() ||
                        editTextLastName.getText().toString().trim().isEmpty() ||
                        editTextEmail.getText().toString().trim().isEmpty()){
                    Toast.makeText(getActivity(),"One or many field are empty.", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String email = editTextEmail.getText().toString().trim();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference documentReference = firebaseStorage.collection("users").document(user.getUid());
                        Map<String, Object> editor = new HashMap<>();
                        editor.put("email",email);
                        editor.put("firstName",editTextFirstName.getText().toString().trim());
                        editor.put("lastName",editTextLastName.getText().toString().trim());
                        documentReference.update(editor);
                        Toast.makeText(getActivity(),"Profile has been updated.", Toast.LENGTH_SHORT).show();
                        Intent intent =  new Intent(getActivity(),MyProfile.class);
                        startActivity(intent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Can not update.", Toast.LENGTH_SHORT).show();
                    }
                });

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