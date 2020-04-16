package com.example.slidebox.ui.recyclables;//package com.example.slidebox.ui.recyclables;

//import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recyclablesViewModel = new ViewModelProvider(this).get(RecyclablesViewModel.class);
        View root = inflater.inflate(R.layout.recyclables_fragment, container, false);
        final TextView textView = root.findViewById(R.id.recyclables_MainHeader);
        final View paperPopUp = root.findViewById(R.id.buttonPaper);
        paperPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paperPopUp.showContextMenu();

            }

    }

        );

        recyclablesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }



    /*public void popup_window() {

            View menuItemView = getView().findViewById(R.id.Re);
            PopupMenu paperPopup = new PopupMenu(getActivity(), menuItemView);
            paperPopup.getMenuInflater().inflate(R.menu.recyclables_popup, paperPopup.getMenu());

            paperPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_ticket:
                        Intent intdn = new Intent(getActivity(),RecyclablesFragment.class);
                        intdn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intdn);
                        break;

                    case R.id.action_survey:
                        Toast.makeText(getActivity(),"Under Construction ",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.action_service_request:
                        Toast.makeText(getActivity(),"Under Construction ",Toast.LENGTH_LONG).show();
                        break;

                    default:
                        break;

                }
                return true;
            }
        });
        paperPopup.show();
    }
        */


    public void paperPopup(View v) {
        //View menuItemView = getView().findViewById(R.menu.recyclables_popup);
        PopupMenu paperPopup = new PopupMenu(getActivity(), v);
        paperPopup.getMenuInflater().inflate(R.menu.recyclables_popup, paperPopup.getMenu());

        paperPopup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) getActivity());
        paperPopup.inflate(R.menu.recyclables_popup);

        paperPopup.show();
    }

    public void plasticPopup(View v) {
        PopupMenu plasticPopup = new PopupMenu(getActivity(), v);
        plasticPopup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) getActivity());
        plasticPopup.inflate(R.menu.recyclables_popup_plastic);
        plasticPopup.show();

    }

    public void metalPopup(View v) {
        PopupMenu metalPopup = new PopupMenu(getActivity(), v);
        metalPopup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) getActivity());
        metalPopup.inflate(R.menu.recyclables_popup_metal);
        metalPopup.show();

    }

    public void glassPopup(View v) {
        PopupMenu glassPopup = new PopupMenu(getActivity(), v);
        glassPopup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) getActivity());
        glassPopup.inflate(R.menu.recyclables_popup);
        glassPopup.show();

    }

    public void unsurePopup(View v) {
        PopupMenu unsurePopup = new PopupMenu(getActivity(), v);
        unsurePopup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) getActivity());
        unsurePopup.inflate(R.menu.recyclables_popup_unsure);
        unsurePopup.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(getActivity(), "Item 1 clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(getActivity(), "Item 1 clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(getActivity(), "Item 1 clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item4:
                Toast.makeText(getActivity(), "Item 1 clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

}
