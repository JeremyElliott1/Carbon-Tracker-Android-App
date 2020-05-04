package com.example.slidebox.MyProfile.Edit;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.slidebox.LogIn;
import com.example.slidebox.MyProfile.MyProfile;
import com.example.slidebox.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.zip.Inflater;

import static android.content.ContentValues.TAG;


public class PassWordChange extends Fragment {
    private EditText editTextOld, editTextNew;
    private Button buttonChangePw;
    private Toolbar mToolbar;


    public PassWordChange() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pass_word_change, container, false);
        editTextNew = view.findViewById(R.id.editTextNew);
        editTextOld = view.findViewById(R.id.editTextOld);
        buttonChangePw = view.findViewById(R.id.buttonChange);
        mToolbar = view.findViewById(R.id.toolbarChangePW);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_passWordChange2_to_baseEditFragment);
            }
        });

        buttonChangePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPW = editTextOld.getText().toString();
                final String newPW = editTextNew.getText().toString();
                if (oldPW.equals("")) {
                    Toast.makeText(getActivity(), "OldPassWord is required!", Toast.LENGTH_SHORT).show();
                } else if (newPW.equals("")) {
                    Toast.makeText(getActivity(), "NewPassWord is null!", Toast.LENGTH_SHORT).show();
                } else if (newPW.length() < 6 || oldPW.length() < 6) {
                    Toast.makeText(getActivity(), "NewPassWord is too short", Toast.LENGTH_SHORT).show();
                } else {
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPW);
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(newPW).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getActivity(), "Password changed!", Toast.LENGTH_SHORT).show();
                                            FirebaseAuth.getInstance().signOut();
                                            Intent intent = new Intent(getActivity(), LogIn.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            Log.d(TAG, "Password updated");
                                        } else {
                                            Log.d(TAG, "Error password not updated");
                                        }
                                    }
                                });
                            } else {
                                Log.d(TAG, "Error auth failed");
                            }
                        }
                    });
                }
            }
        });
        return view;
    }
}
