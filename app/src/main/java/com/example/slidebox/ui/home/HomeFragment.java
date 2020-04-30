package com.example.slidebox.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.slidebox.LogIn;
import com.example.slidebox.R;
import com.example.slidebox.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Random;

public class HomeFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference quotes = db.collection("quotes");

    private TextView quoteText;
    private TextView quoteAuthor;
    private TextView congrats;
    private TextView currentPoints;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private  String userID;
    private DocumentReference docRef;

    private Button sign_out;
    private Button set_target;
    private EditText edit_target;
    private float target;
    private CircleProgress circleProgress;

    private SharedPreferences settings;

    private static final String TAG = "DocSnippets";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        quoteText = root.findViewById(R.id.quoteText);
        quoteAuthor = root.findViewById(R.id.quoteAuthor);
        congrats = root.findViewById(R.id.congrats);
        currentPoints = root.findViewById(R.id.currentPoints);

        Calendar calender = Calendar.getInstance();
        int currentDay = calender.get(Calendar.DAY_OF_MONTH);
        int currentWeek = calender.get(Calendar.WEEK_OF_MONTH);
        int currentMonth = calender.get(Calendar.MONTH);
         settings = getActivity().getSharedPreferences("PREFS",0);
        int lastDay = settings.getInt("day",0);
        int lastWeek = settings.getInt("week",0);
        int lastMonth = settings.getInt("month",0);
        int random = settings.getInt("random", 1);

        if (lastDay!=currentDay) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("day" , currentDay);

            Random r = new Random();
            int low = 1;
            int high = 10;
            int result = r.nextInt(high-low) + low;
            editor.putInt("random",result);
            editor.commit();
        }
        if(lastWeek!=currentWeek){
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("week" , currentWeek);
            User.getInstance().resetWeeklyPoints();
            editor.commit();
        }
        if(lastMonth!=currentMonth){
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("month" , currentMonth);
            User.getInstance().resetMonthlyPoints();
            editor.commit();
        }
        getQuote(random);

        //firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userID = firebaseAuth.getCurrentUser().getUid();
        docRef = db.collection("users").document(userID);

        edit_target = root.findViewById(R.id.edit_target);
        circleProgress = root.findViewById(R.id.circle_progress);

        set_target = root.findViewById(R.id.setTarget);


        getSavedTarget();

         setTarget();

        sign_out=root.findViewById(R.id.sign_out);
        signOut();
        User.getInstance().getCurrentPoints(currentPoints);


        return root;
    }
    private void getQuote(int random){
        quotes.whereEqualTo("id",random)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String quote = document.get("quote").toString();
                        quoteText.setText(quote);
                        String author = document.get("author").toString();
                        quoteAuthor.setText(author);
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
    private void signOut(){
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(),LogIn.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }


    private void setTarget(){
        set_target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_target.getVisibility() == View.INVISIBLE){
                    edit_target.setVisibility(View.VISIBLE);

                } else if (edit_target.getVisibility() == View.VISIBLE){
                    try
                    {
                        Float savedTarget = settings.getFloat("savedTarget",0);
                        SharedPreferences.Editor editor = settings.edit();
                        target= Float.parseFloat(edit_target.getText().toString());
                        editor.putFloat("savedTarget" , target);
                        editor.commit();
                        getTotalPoints();

                    }
                    catch (NumberFormatException e)
                    {
                    }
                    edit_target.setVisibility(View.INVISIBLE);
                }

            }
        });


    }

    public void getTotalPoints(){
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    try {
                        Float totalPoints = Float.parseFloat(String.valueOf(snapshot.get("totalPoints")));
                       circleProgress.setValue((totalPoints/target)*100);
                        if ((totalPoints/target)*100>=100){
                            congrats.setVisibility(View.VISIBLE);
                        }
                        else {
                            congrats.setVisibility(View.INVISIBLE);
                        }
                    }
                    catch (NumberFormatException n){}
                    Log.d(TAG, "Current data: " + snapshot.getData());
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }
public void getSavedTarget(){
        assert settings != null;
        target = settings.getFloat("savedTarget",0);
        getTotalPoints();
    }

}