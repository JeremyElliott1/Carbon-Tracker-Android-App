package com.example.slidebox.ui.shop;


import android.app.Dialog;
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


public class ShopFragment extends Fragment {

    private GridLayout mainGrid;
    private Dialog dialog;
    private ImageView closeShopPopup;
    private Button addToShoppingCart;

    private TextView textShopPopup;

    String[] itemNames = {"Coffee","Tea","Cookie","Muffin","T-shirt","Travel mug"};
    int[] itemImages = {R.drawable.coffee,R.drawable.tea,R.drawable.cookie,R.drawable.muffin,R.drawable.shirt,R.drawable.travelmug};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shop, container, false);



        dialog = new Dialog(getContext());

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
                   dialog.setContentView(R.layout.activity_shop_popup);
                    textShopPopup = dialog.findViewById(R.id.textShopPopup);
                    textShopPopup.setText(itemNames[finalI]);
                    Drawable myDrawable = getResources().getDrawable(itemImages[finalI]);

                    textShopPopup.setCompoundDrawablesWithIntrinsicBounds(null,null,null,myDrawable);
                    addToShoppingCart= (Button) dialog.findViewById(R.id.addToShoppingCart);
                    addToShoppingCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast toast=Toast.makeText(getActivity().getApplicationContext(),"Item added to shopping cart",Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 200);
                            toast.show();
                            dialog.dismiss();
                        }
                    });

                    closeShopPopup= (ImageView) dialog.findViewById(R.id.closeShopPopup);
                    closeShopPopup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


                }
            });
        }
    }
}