package com.example.slidebox.ui.travel;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.slidebox.HelpOptions;
import com.example.slidebox.R;


public class TravelFragment extends Fragment {

    private TravelViewModel travelViewModel;
    Button button;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        travelViewModel =
                ViewModelProviders.of(this).get(TravelViewModel.class);
        View root = inflater.inflate(R.layout.fragment_travel, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        travelViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        button = root.findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TravelActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }


}