package com.example.slidebox.ui.shop;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.slidebox.R;
import com.example.slidebox.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.w3c.dom.Text;


public class ShopFragment extends Fragment {

    private  final String TAG = "TAG";

    private GridLayout mainGrid;

    private Dialog dialogShopPopup;
    private Dialog dialogShopRedeemPopup;

    private ImageView closeShopPopup;
    private Button cancelRedeemItem;
    private ImageView closeShopRedeemPopup;
    private Button redeemItem;

    private TextView textShopPopup;
    private TextView textRedeemShopPopup;
    private ImageView ImageRedeemShopPopup;

    private ImageView qrCode;

    private String[] itemNames = {"Coffee","Tea","Cookie","Muffin","T-shirt","Travel mug"};
    private int[] itemImages = {R.drawable.coffee,R.drawable.tea,R.drawable.cookie,R.drawable.muffin,R.drawable.shirt,R.drawable.travelmug};
    private int[] points = {20,20,10,10,80,80};

    private  FirebaseFirestore db;
    private  FirebaseAuth firebaseAuth;
    private  String userID;
    private DocumentReference docRef;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shop, container, false);



        dialogShopPopup = new Dialog(getContext());
        dialogShopRedeemPopup = new Dialog(getContext(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        mainGrid = root.findViewById(R.id.mainGrid);

        db= FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        docRef = db.collection("users").document(userID);

        //Set Event
        setSingleEvent(mainGrid);

        return root;
    }

    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   dialogShopPopup.setContentView(R.layout.activity_shop_popup);
                    textShopPopup = dialogShopPopup.findViewById(R.id.textShopPopup);
                    textShopPopup.setText(itemNames[finalI]);

                    Drawable myDrawable = getResources().getDrawable(itemImages[finalI]);
                    textShopPopup.setCompoundDrawablesWithIntrinsicBounds(null,null,null,myDrawable);

                    removePoints(points[finalI], finalI);

                    closeShopPopup();

                dialogShopPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogShopPopup.show();


                }
            });
        }
    }
    private void closeShopPopup(){
        closeShopPopup= (ImageView) dialogShopPopup.findViewById(R.id.closeShopPopup);
        closeShopPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogShopPopup.dismiss();
            }
        });
        cancelRedeemItem = (Button) dialogShopPopup.findViewById(R.id.cancelRedeemItem);
        cancelRedeemItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogShopPopup.dismiss();
            }
        });

    }
    private void redeemItem(int finalI){
        final int finalII = finalI;
                Toast toast=Toast.makeText(getActivity().getApplicationContext(),"Item Redeemed",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 200);
                toast.show();
                dialogShopPopup.dismiss();
                dialogShopRedeemPopup.setContentView(R.layout.activity_shop_redeemed_popup);
                textRedeemShopPopup = dialogShopRedeemPopup.findViewById(R.id.textRedeemShopPopup);
                textRedeemShopPopup.setText(itemNames[finalII]);
                ImageRedeemShopPopup = dialogShopRedeemPopup.findViewById(R.id.ImageRedeemShopPopup);
                Drawable myDrawable = getResources().getDrawable(itemImages[finalII]);
                ImageRedeemShopPopup.setImageDrawable(myDrawable);
                getCode(itemNames[finalII]);

                dialogShopRedeemPopup.show();
                closeShopRedeemPopup();
    }
    private void closeShopRedeemPopup(){
        closeShopRedeemPopup= (ImageView) dialogShopRedeemPopup.findViewById(R.id.closeShopRedeemPopup);
        closeShopRedeemPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogShopRedeemPopup.dismiss();
            }
        });
    }
private void getCode(String text){
        try{
            qrCode(text);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
}

public void qrCode(String text) throws WriterException {
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    qrCode = dialogShopRedeemPopup.findViewById(R.id.qr_code);
    BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 350,350);
    Bitmap bitmap = Bitmap.createBitmap(350,350,Bitmap.Config.RGB_565);

    for(int x =0 ; x<350 ; x++){
        for(int y=0 ; y<350 ; y++){
            bitmap.setPixel(x,y,bitMatrix.get(x,y)? Color.BLACK : Color.parseColor("#224977"));
        }
    }
    qrCode.setImageBitmap(bitmap);
}

    public void removePoints(final int points, final int finalI){
        redeemItem = (Button) dialogShopPopup.findViewById(R.id.redeemItem);
        redeemItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Long currentPoints = (Long) document.get("currentPoints");
                        if (currentPoints >= points){
                            docRef.update(
                                    "currentPoints", currentPoints-points
                            ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    redeemItem(finalI);
                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error updating document", e);
                                        }
                                    });
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        }
                        else{
                            Toast.makeText(getContext(), "Sorry you don't have enough points to purchase this item",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
            }
        });
    }
}