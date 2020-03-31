package com.example.slidebox.ui.shop;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.w3c.dom.Text;


public class ShopFragment extends Fragment {

    private GridLayout mainGrid;

    private Dialog dialogShopPopup;
    private Dialog dialogShopRedeemPopup;

    private ImageView closeShopPopup;
    private ImageView closeShopRedeemPopup;
    private Button redeemItem;

    private TextView textShopPopup;
    private TextView textRedeemShopPopup;
    private ImageView ImageRedeemShopPopup;

    private ImageView qrCode;

    String[] itemNames = {"Coffee","Tea","Cookie","Muffin","T-shirt","Travel mug"};
    int[] itemImages = {R.drawable.coffee,R.drawable.tea,R.drawable.cookie,R.drawable.muffin,R.drawable.shirt,R.drawable.travelmug};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shop, container, false);



        dialogShopPopup = new Dialog(getContext());
        dialogShopRedeemPopup = new Dialog(getContext(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        mainGrid = (GridLayout) root.findViewById(R.id.mainGrid);

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

                    redeemItem(finalI);

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
    }
    private void redeemItem(int finalI){
        final int finalII = finalI;
        redeemItem = (Button) dialogShopPopup.findViewById(R.id.redeemItem);
        redeemItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
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
  MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

public void qrCode(String text) throws WriterException {
    qrCode = dialogShopRedeemPopup.findViewById(R.id.qr_code);
    BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 350,350);
    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
    qrCode.setImageBitmap(bitmap);
}
}