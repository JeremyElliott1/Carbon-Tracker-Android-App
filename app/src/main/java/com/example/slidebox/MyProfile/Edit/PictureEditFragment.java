package com.example.slidebox.MyProfile.Edit;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.slidebox.MyProfile.MyProfile;
import com.example.slidebox.R;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class PictureEditFragment extends Fragment {

    private static final String TAG = "Profile_Image";
    private ImageView imageViewProfile;
    private Button buttonGallery;
    private Button buttonCamera;
    private Toolbar mToolbar;
    private static final int PICK_IMAGE = 1;
    private static final int CAPTURE_IMAGE = 2;
    private static final int REQUEST_CODE = 101;
    Uri imageUri;

    public PictureEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_picture_edit, container, false);
        verifyPermissions();
        mToolbar = view.findViewById(R.id.toolbarImageEdit);
        buttonGallery = view.findViewById(R.id.buttonFromGallery);
        buttonCamera = view.findViewById(R.id.buttonFromCamera);
        imageViewProfile = view.findViewById(R.id.imageViewProfile);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_pictureEditFragment_to_baseEditFragment);
            }
        });

        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "sellect picture"), PICK_IMAGE);
            }
        });

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAPTURE_IMAGE);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == getActivity().RESULT_OK) {
                    imageUri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                        Log.d(TAG, "load image from gallery.");
                        imageViewProfile.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CAPTURE_IMAGE:
                if (resultCode == getActivity().RESULT_OK) {
                    Log.d(TAG, "Camera start");
                    imageUri = data.getData();
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imageViewProfile.setImageBitmap(bitmap);
                }
                break;
            default:
                throw new IllegalArgumentException();

        }

    }

    private void uploadProfileImage(){



    }

    private void verifyPermissions() {
        Log.d(TAG, "verifyPermissions: asking user for permissions");
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (ContextCompat.checkSelfPermission(this.getActivity(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getActivity(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getActivity(),
                permissions[2]) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG,"Permission is passed.");
        }
        else{
            ActivityCompat.requestPermissions(this.getActivity(),permissions,REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        verifyPermissions();
    }

    private void uploadImage(){

    }
}
