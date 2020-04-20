package com.example.slidebox.MyProfile;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ReadData {
    Uri filePath;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    DatabaseReference mRef = firebaseDatabase.getReference();

    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    StorageReference storageReference = firebaseStorage.getReference();

    void chooseImage(AppCompatActivity appCompatActivity){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        appCompatActivity.startActivityForResult(Intent.createChooser(intent,"Select Picture"), 71);

    }










}
