package com.example.slidebox.ui.recyclables;//package com.example.slidebox.ui.recyclables;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.slidebox.R;
import com.example.slidebox.User;


public class RecyclablesFragment extends Fragment {

    public static final int DIALOG_FRAGMENT = 1;
    public static final int RESULT_OK = 101;

    private RecyclablesViewModel recyclablesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recyclablesViewModel = new ViewModelProvider(this).get(RecyclablesViewModel.class);
        final View root = inflater.inflate(R.layout.recyclables_dialog, container, false);

        TextView textView = (TextView)root.findViewById(R.id.paperDialog);
        this.paperDialog(textView, root);
        return root;
    }

    User user = User.getInstance();

    // Show single choice radio button in alert dialog.
    private void paperDialog(TextView textView, View v) {
        final TextView textViewTmp = textView;
        Button dialogPaperButton = (Button) v.findViewById(R.id.paperButton);
        dialogPaperButton.setOnClickListener(new View.OnClickListener() {

            private String chooseItem;
            int index;

            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(),R.style.AlertDialog);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setTitle("What kind of paper are you recycling?");

                final String listItemArr[] = {"Old corrugated Containers", "Old Newspaper selected", "Standard Stationary Paper selected", "High Grade De-inked Paper selected"};
                final int[] points = {15,5,10,10};
                builder.setSingleChoiceItems(listItemArr, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int itemIndex) {
                        chooseItem = listItemArr[itemIndex];
                        index = itemIndex;
                    }
                });

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "Item added: " + chooseItem + ". You have been awarded " + points[index] + " points!", Toast.LENGTH_SHORT).show();
                        user.addPoints(points[index]);
                    }
                });

                builder.show();


            }
        });
    }



}
