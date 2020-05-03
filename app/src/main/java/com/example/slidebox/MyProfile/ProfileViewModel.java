package com.example.slidebox.MyProfile;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ProfileViewModel extends ViewModel {
    private static final String TAG = "ProfileViewModel";
    private String weeklyPoints;
    private String monthlyPoints;
    private String totalPoints;
    private String currentPoints;
    private String lastName;
    private String firstName;
    private String eMail;
    private FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
    private DocumentReference documentReference = dataBase.collection("users").document();

    public ProfileViewModel() {
        getUserInfo(new FireBaseCallBack() {
            @Override
            public void onCallBack(HashMap<String, Object> map) {
                Log.d(TAG, "dfds" + (String) map.get("firstName"));
                readUserInfoToViewModule(map);
            }
        });


    }

    private void readUserInfoToViewModule(HashMap<String, Object> userInfor) {
        this.firstName = (String) userInfor.get("firstName");
        this.lastName = (String) userInfor.get("lastName");
        this.eMail = (String) userInfor.get("email");
        this.currentPoints = String.valueOf(userInfor.get("currentPoints"));
        this.weeklyPoints = String.valueOf(userInfor.get("weeklyPoints"));
        this.monthlyPoints = String.valueOf(userInfor.get("monthlyPoints"));
        this.totalPoints = String.valueOf(userInfor.get("totalPoints"));

    }

    public void getUserInfo(final FireBaseCallBack fireBaseCallBack) {

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        fireBaseCallBack.onCallBack((HashMap<String, Object>) document.getData());
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }


    private interface FireBaseCallBack {
        void onCallBack(HashMap<String, Object> map);
    }

}
