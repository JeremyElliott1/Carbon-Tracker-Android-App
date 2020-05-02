package com.example.slidebox.MyProfile;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class ReadData {

    private static final int PICK_IMAGE = 1;
    private static final String TAG = "ReadData";
    private String weeklyPoints;
    private String monthlyPoints;
    private String totalPoints;
    private String currentPoints;
    private String lastName;
    private String firstName;
    private String eMail;
    private String imageUserID;
    private String mImageURL;
    private String defImageUrl;
    private FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
    private DocumentReference documentReference = dataBase.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
    private Map<String, Object> userInfor = new HashMap<String, Object>();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public ReadData() {
        getUserInfor(new FireBaseCallBack() {
            @Override
            public void onCallBack(HashMap<String, Object> map) {
                Log.d(TAG, "dfds" + (String) map.get("firstName"));
                readUserInforToViewModule(map);
            }
        });
    }

    private void readUserInforToViewModule(HashMap<String, Object> userInfor) {
        this.firstName = (String) userInfor.get("firstName");
        Log.d(TAG, "ddddddddd" + getFirstName());
        this.lastName = (String) userInfor.get("lastName");
        this.eMail = (String) userInfor.get("email");
        this.currentPoints = String.valueOf(userInfor.get("currentPoints"));
        this.weeklyPoints = String.valueOf(userInfor.get("weeklyPoints"));
        this.monthlyPoints = String.valueOf(userInfor.get("monthlyPoints"));
        this.totalPoints = String.valueOf(userInfor.get("totalPoints"));

    }

    public void getUserInfor(final FireBaseCallBack fireBaseCallBack) {

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

   /* public void readData (){
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                HashMap<String,Object> hashMap=documentSnapshot.toObject(HashMap.class);
                readUserInforToViewModule(hashMap);
            }
        });
    }*/

    private interface FireBaseCallBack {
        void onCallBack(HashMap<String, Object> map);
    }

//        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if (documentSnapshot.exists()) {
//                    userInfor.putAll(documentSnapshot.getData());
//                    String s =String.valueOf( userInfor.isEmpty());
//                    Log.d(TAG, "DocumentSnapshot data: " + documentSnapshot.getData());
//                } else {
//                    Log.d(TAG, "No such document");
//                }
//            }
//        });


    public String getWeeklyPoints() {
        return weeklyPoints;
    }

    public String getMonthlyPoints() {
        return monthlyPoints;
    }

    public String getTotalPoints() {
        return totalPoints;
    }

    public String getCurrentPoints() {
        return currentPoints;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String geteMail() {
        return eMail;
    }


}










