package com.example.slidebox.ui.travel;

import android.os.Bundle;
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

import com.example.slidebox.R;

public class TravelFragment extends Fragment {

    private TravelViewModel travelViewModel;
    private EditText car;
    private EditText Bike;
    private EditText total;
    private Button cal;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        travelViewModel =
                ViewModelProviders.of(this).get(TravelViewModel.class);
        View root = inflater.inflate(R.layout.fragment_travel, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        travelViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        car = (EditText)root.findViewById(R.id.editText);
        Bike = (EditText)root.findViewById(R.id.editText2);
        total = (EditText)root.findViewById(R.id.editText3);
        cal = (Button)root.findViewById(R.id.button5);

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double temp;
                temp = Double.parseDouble(car.getText().toString())*0.183 + Double.parseDouble(Bike.getText().toString())*0.0073;
                total.setText(temp.toString());
            }
        });
        return root;
    }
}