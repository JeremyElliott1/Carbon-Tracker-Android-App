package com.example.slidebox.ui.reusables;

public class ReusableItem {
    private int itemImage;
    private String itemDescription;

    ReusableItem(int imageResource, String enteredDescription) {
        itemImage = imageResource;
        itemDescription = enteredDescription;
    }

    public int getReusableItemImage() {
        return itemImage;
    }

    public String getItemStringDescription() {
        return itemDescription;
    }

}
