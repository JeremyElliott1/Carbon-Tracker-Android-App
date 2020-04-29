///*
//package com.example.slidebox.ui.recyclables;//package com.example.slidebox.ui.recyclables;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.example.slidebox.R;
//import com.example.slidebox.User;
//
//import java.util.Objects;
//
//
//public class RecyclablesFragment2 extends Fragment {
//
//    private RecyclablesViewModel recyclablesViewModel;
//
//    String[] glassItems = {"1", "222", "trt"};
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        recyclablesViewModel = new ViewModelProvider(this).get(RecyclablesViewModel.class);
//        final View root = inflater.inflate(R.layout.recyclables_dialog, container, false);
//
//
//        TextView textView = (TextView)root.findViewById(R.id.alertDialogTextView);
//
//
//        this.showSingleChoiceRadioInAlertDialog(textView, root);
//
//
//        return root;
//    }
//
//
//    // Show single choice radio button in alert dialog.
//    private void showSingleChoiceRadioInAlertDialog(TextView textView, View v) {
//        final TextView textViewTmp = textView;
//        Button alertDialogButton = (Button) v.findViewById(R.id.paperButton);
//        alertDialogButton.setOnClickListener(new View.OnClickListener() {
//
//            private String chooseItem;
//
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                builder.setIcon(R.mipmap.ic_launcher);
//                builder.setTitle("Customized Alert Dialog");
//
//                final String listItemArr[] = {"Java", "C++", "Python", "Javascript", "Html", "Android"};
//                builder.setSingleChoiceItems(listItemArr, 0, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int itemIndex) {
//                        chooseItem = listItemArr[itemIndex];
//                    }
//                });
//
//                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        textViewTmp.setText("You select " + chooseItem);
//                    }
//                });
//
//                builder.show();
//            }
//        });
//    }
//}
//*/
