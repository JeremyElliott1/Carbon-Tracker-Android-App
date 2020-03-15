package com.example.slidebox.ui.reusables;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.slidebox.R;

public class ReusablesFragment extends Fragment {

    private ReusablesViewModel reusablesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reusablesViewModel =
                ViewModelProviders.of(this).get(ReusablesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reusables, container, false);
        final TextView textView = root.findViewById(R.id.Reusable_mainHeader);
        reusablesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}