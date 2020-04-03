
package com.example.slidebox.ui.recyclables;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.slidebox.R;

public class recyclablesFragment extends Fragment {

    private recyclablesViewModel recyclablesViewModel;
    private Spinner mySpinner;

    @Override
   public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      recyclablesViewModel = ViewModelProviders.of(this).get(recyclablesViewModel.class);
     

       final View root = inflater.inflate(R.layout.fragment_recyclables, container, false);


        final TextView textView = root.findViewById(R.id.text_recyclables);
        recyclablesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


       return root;
   }

}
