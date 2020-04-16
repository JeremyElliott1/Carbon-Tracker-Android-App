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
//            }
//
//    }
//
//        );

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
                plasticPopup.getMenuInflater().inflate(R.menu.recyclables_popup, plasticPopup.getMenu());
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
                metalPopup.getMenuInflater().inflate(R.menu.recyclables_popup, metalPopup.getMenu());
                metalPopup.inflate(R.menu.recyclables_popup_plastic);
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
                glassPopup.getMenuInflater().inflate(R.menu.recyclables_popup, glassPopup.getMenu());
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
                unsurePopup.getMenuInflater().inflate(R.menu.recyclables_popup, unsurePopup.getMenu());
                unsurePopup.inflate(R.menu.recyclables_popup_unsure);
                unsurePopup.show();
            }
        });



    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(getActivity(), "Item 1 clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(getActivity(), "Item 2 clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(getActivity(), "Item 3 clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item4:
                Toast.makeText(getActivity(), "Item 4 clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

}
