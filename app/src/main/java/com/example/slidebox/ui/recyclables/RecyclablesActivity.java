package com.example.slidebox.ui.recyclables;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.slidebox.MainActivity;
import com.example.slidebox.R;


public class RecyclablesActivity extends AppCompatActivity{ //implements AdapterView.OnItemSelectedListener{

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclables_activity);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(RecyclablesActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Recyclable_Items));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
//       Spinner mySpinner = findViewById(R.id.spinner1);
//        ArrayAdapter<CharSequence> recyclablesAdapter2 = ArrayAdapter.createFromResource(this, R.array.Recyclable_Items, android.R.layout.simple_spinner_item);
//       recyclablesAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//       mySpinner.setAdapter(recyclablesAdapter2);
//       mySpinner.setOnItemSelectedListener(this);

/*
       ArrayAdapter<String> recyclablesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Recyclable_Items));
        recyclablesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(recyclablesAdapter);
        mySpinner.setOnItemSelectedListener(this);*/


    }

//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        String text = adapterView.getItemAtPosition(position).toString();
//        Toast.makeText(adapterView.getContext(),text, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
//    String text = parent.getItemAtPosition(position).toString();
//        Toast.makeText(parent.getContext(),text, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
}
