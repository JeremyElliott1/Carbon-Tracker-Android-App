package com.example.slidebox.ui.reusables;

import com.google.firebase.firestore.Exclude;

public class ReusableItem {
    private String id;
    private String name;
    private String points;

    public ReusableItem() {
        //fireStore needs an empty constructor otherwise it crashes.
    }

    public ReusableItem(String name, String points) {
        this.name = name;
        this.points = points;
    }

    @Exclude
    //this tag ensures that id is not saved as an additional field in the Reusableitems documents
    // so that it is only used within this application to retrieve each document ID.
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPoints() {
        return points;
    }

}