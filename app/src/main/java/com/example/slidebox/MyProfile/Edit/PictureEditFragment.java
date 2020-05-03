package com.example.slidebox.MyProfile.Edit;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.slidebox.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class PictureEditFragment extends Fragment {

    private static final String TAG = "Profile_Image";
    private ImageView imageViewProfile;
    private Button buttonGallery;
    private Button buttonCamera;
    private Button buttonConfirm;
    private Toolbar mToolbar;
    private static final int PICK_IMAGE = 1;
    private static final int CAPTURE_IMAGE = 2;
    private static final int REQUEST_CODE = 101;
    private Uri imageUri;


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
        buttonConfirm = view.findViewById(R.id.buttonComfirm);
        buttonGallery = view.findViewById(R.id.buttonFromGallery);
        buttonCamera = view.findViewById(R.id.buttonFromCamera);
        imageViewProfile = view.findViewById(R.id.imageViewProfile);

        loadProfileImage();

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

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewProfile.setDrawingCacheEnabled(true);
                imageViewProfile.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable)imageViewProfile.getDrawable()).getBitmap();
                uploadImage(bitmap);
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
                        Bitmap bitmap =(Bitmap) MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                        Log.d(TAG, "load image from gallery.");
                        imageViewProfile.setImageBitmap(bitmap);
                       // uploadImage(bitmap);
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
                    //uploadImage(bitmap);
                }
                break;
            default:
                throw new IllegalArgumentException();

        }

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

    private void uploadImage(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80,baos);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(uid+".jpeg");

        reference.putBytes(baos.toByteArray()).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(getActivity(),"Failed update ProfileImage!",Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                Toast.makeText(getActivity(),"Successfully update ProfileImage!",Toast.LENGTH_LONG).show();
                NavController navController = Navigation.findNavController(getView());
                navController.navigate(R.id.action_pictureEditFragment_to_baseEditFragment);

            }
        });

    }

    private void loadProfileImage() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(uid + ".jpeg");
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageViewProfile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                StorageReference mref = FirebaseStorage.getInstance().getReference();
                String s = getResources().getString(R.string.defaultImageChildPath);
                mref.child(s).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(imageViewProfile);
                    }
                });
            }
        });
    }

}
