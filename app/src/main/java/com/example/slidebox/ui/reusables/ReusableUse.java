package com.example.slidebox.ui.reusables;

import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class ReusableUse {

        private String id;
        private String itemName;
        private Date date;
    private String points;

    public ReusableUse() {
            //fireStore needs an empty constructor otherwise it crashes.
        }

    ReusableUse(String itemName, Date date, String points) {
            this.itemName = itemName;
            this.date = date;
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

        String getItemName() {
            return itemName;
        }

        Date getDate() {
            return date;
        }

    public String getPoints() {
        return points;
    }
}
