package com.example.slidebox.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference quotes = db.collection("quotes");

    private TextView quoteText;
    private TextView quoteAuthor;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private Button sign_out;

    private static final String TAG = "DocSnippets";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        quoteText = root.findViewById(R.id.quoteText);
        quoteAuthor = root.findViewById(R.id.quoteAuthor);

        Calendar calender = Calendar.getInstance();
        int currentDay = calender.get(Calendar.DAY_OF_MONTH);
        int currentWeek = calender.get(Calendar.WEEK_OF_MONTH);
        int currentMonth = calender.get(Calendar.MONTH);
        SharedPreferences settings = getActivity().getSharedPreferences("PREFS",0);
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

        sign_out=root.findViewById(R.id.sign_out);
        signOut();

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
}