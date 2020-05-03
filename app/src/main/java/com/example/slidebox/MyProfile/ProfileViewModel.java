package com.example.slidebox.MyProfile;

import android.content.Context;
import android.service.autofill.AutofillService;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.slidebox.R;
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

public class ProfileViewModel extends ViewModel {
    private static final String TAG = "ProfileViewModel";
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
    private DocumentReference documentReference = dataBase.collection("users").document();
    private Map<String, Object> userInfor = new HashMap<String, Object>();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public ProfileViewModel() {
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

    public void setDefImageUrl(Context context) {
        this.defImageUrl = context.getResources().getString(R.string.defaultImageChildPath);
    }

/*    public void setUserFile(){
        String userID = String.valueOf(user.getUid());
        storageReference.child(userID);
        Log.d(TAG,"Set the userfile!");
    }*/

    public Map<String, Object> getMap() {
        return userInfor;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getEMail() {
        return eMail;
    }

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

    public String geteMail() {
        return eMail;
    }
}
