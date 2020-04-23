package com.example.slidebox.ui.recyclables;//package com.example.slidebox.ui.recyclables;

//import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.slidebox.R;
import com.example.slidebox.ui.reusables.ReusablesViewModel;


public class RecyclablesFragment extends Fragment implements PopupMenu.OnMenuItemClickListener{

    private RecyclablesViewModel recyclablesViewModel;
    private Button paperButton;
    private Button metalButton;
    private Button plasticButton;
    private Button glassButton;
    private Button unsureButton;

    private int CO2value;
    private int totalCO2;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recyclablesViewModel = new ViewModelProvider(this).get(RecyclablesViewModel.class);
        View root = inflater.inflate(R.layout.recyclables_fragment, container, false);
        final TextView textView = root.findViewById(R.id.recyclables_MainHeader);
//        final View paperPopUp = root.findViewById(R.id.buttonPaper);
//        paperPopUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                paperPopUp.showContextMenu();
//

        recyclablesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        paperButton = root.findViewById(R.id.buttonPaper);
        metalButton = root.findViewById(R.id.buttonMetal);
        plasticButton = root.findViewById(R.id.buttonPlastic);
        glassButton = root.findViewById(R.id.buttonGlass);
        unsureButton = root.findViewById(R.id.buttonUnsure);
        paperPopup(root);
        plasticPopup(root);
        metalPopup(root);
        glassPopup(root);
        unsurePopup(root);

        return root;
    }


    public void paperPopup(View v) {
        final PopupMenu paperPopup = new PopupMenu(getActivity(),v);
        paperPopup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        paperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paperPopup.getMenuInflater().inflate(R.menu.recyclables_popup, paperPopup.getMenu());
                paperPopup.inflate(R.menu.recyclables_popup);
                paperPopup.show();

            }
        });

    }

    public void plasticPopup(View v) {
        final PopupMenu plasticPopup = new PopupMenu(getActivity(), v);
        plasticPopup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        plasticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plasticPopup.getMenuInflater().inflate(R.menu.recyclables_popup_plastic, plasticPopup.getMenu());
                plasticPopup.inflate(R.menu.recyclables_popup_plastic);
                plasticPopup.show();
            }
        });



    }

    public void metalPopup(View v) {
        final PopupMenu metalPopup = new PopupMenu(getActivity(), v);
        metalPopup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        metalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                metalPopup.getMenuInflater().inflate(R.menu.recyclables_popup_metal, metalPopup.getMenu());
                metalPopup.inflate(R.menu.recyclables_popup_metal);
                metalPopup.show();
            }
        });



    }


    public void glassPopup(View v) {
        final PopupMenu glassPopup = new PopupMenu(getActivity(), v);
        glassPopup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        glassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                glassPopup.getMenuInflater().inflate(R.menu.recyclables_popup_glass, glassPopup.getMenu());
                glassPopup.inflate(R.menu.recyclables_popup_glass);
                glassPopup.show();
            }
        });



    }

    public void unsurePopup(View v) {
        final PopupMenu unsurePopup = new PopupMenu(getActivity(), v);
        unsurePopup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        unsureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsurePopup.getMenuInflater().inflate(R.menu.recyclables_popup_unsure, unsurePopup.getMenu());
                unsurePopup.inflate(R.menu.recyclables_popup_unsure);
                unsurePopup.show();
            }
        });



    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(getActivity(), "Old corrugated Containers selected", Toast.LENGTH_SHORT).show();
                CO2value = CO2value + 10;
                totalCO2 += CO2value;
            return true;
            case R.id.item2:
                Toast.makeText(getActivity(), "Old Newspaper selected", Toast.LENGTH_SHORT).show();
                CO2value = CO2value + 10;
                totalCO2 += CO2value;
                return true;
            case R.id.item3:
                Toast.makeText(getActivity(), "Standard Stationary Paper selected", Toast.LENGTH_SHORT).show();
                CO2value = CO2value + 10;
                totalCO2 += CO2value;
                return true;
            case R.id.item4:
                Toast.makeText(getActivity(), "High Grade De-inked Paper selected", Toast.LENGTH_SHORT).show();
                CO2value = CO2value + 10;
                totalCO2 += CO2value;
                return true;
            case R.id.item5:
                Toast.makeText(getActivity(), "Transparent Glass selected", Toast.LENGTH_SHORT).show();
                CO2value = CO2value + 20;
                totalCO2 += CO2value;
                return true;
            case R.id.item6:
                Toast.makeText(getActivity(), "Coloured Glass selected", Toast.LENGTH_SHORT).show();
                CO2value = CO2value + 20;
                totalCO2 += CO2value;
                return true;
            case R.id.item7:
                Toast.makeText(getActivity(), "Stationary Items selected", Toast.LENGTH_SHORT).show();
                CO2value = CO2value + 30;
                totalCO2 += CO2value;
                return true;
            case R.id.item8:
                Toast.makeText(getActivity(), "PC's, Laptops, Tablets etc.. selected", Toast.LENGTH_SHORT).show();
                CO2value = CO2value + 30;
                totalCO2 += CO2value;
                return true;
            case R.id.item9:
                Toast.makeText(getActivity(), "Batteries selected", Toast.LENGTH_SHORT).show();
                CO2value = CO2value + 30;
                totalCO2 += CO2value;
                return true;
            case R.id.item10:
                Toast.makeText(getActivity(), "Generic Scrap Metal selected", Toast.LENGTH_SHORT).show();
                CO2value = CO2value + 30;
                totalCO2 += CO2value;
                return true;
            case R.id.item11:
                Toast.makeText(getActivity(), "Small Plastic Bottle (500ml) selected", Toast.LENGTH_SHORT).show();
                CO2value = CO2value + 40;
                totalCO2 += CO2value;
                return true;
            case R.id.item12:
                Toast.makeText(getActivity(), "Medium plastic bottle (1L) selected", Toast.LENGTH_SHORT).show();
                CO2value = CO2value + 40;
                totalCO2 += CO2value;
                return true;
            case R.id.item13:
                Toast.makeText(getActivity(), "Large plastic bottle (1.5L+) selected", Toast.LENGTH_SHORT).show();
                CO2value = CO2value + 40;
                totalCO2 += CO2value;
                return true;
            case R.id.item14:
                Toast.makeText(getActivity(), "Plastic Cup/Container selected", Toast.LENGTH_SHORT).show();
                CO2value = CO2value + 40;
                totalCO2 += CO2value;
                return true;
            default:
                return false;
        }
    }



    public int getCO2value(){
        return CO2value;
    }

    public int getTotalCO2(){
        return totalCO2;
    }














}
