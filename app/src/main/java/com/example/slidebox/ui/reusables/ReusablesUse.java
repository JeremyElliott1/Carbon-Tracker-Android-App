package com.example.slidebox.ui.reusables;

import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class ReusablesUse {

        private String id;
        private String itemName;
        private Date date;

        public ReusablesUse() {
            //fireStore needs an empty constructor otherwise it crashes.
        }

        public ReusablesUse(String itemName, Date date) {
            this.itemName = itemName;
            this.date = date;
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

        public String getItemName() {
            return itemName;
        }

        public Date getDate() {
            return date;
        }
}
