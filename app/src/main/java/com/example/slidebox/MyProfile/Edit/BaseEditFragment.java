package com.example.slidebox.MyProfile.Edit;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.slidebox.MyProfile.MyProfile;
import com.example.slidebox.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class BaseEditFragment extends Fragment {

    private BaseEditViewModel mViewModel;
    private Toolbar mToolbar;
    private ImageView imageViewProfileEdit;
    private StorageReference mRef = FirebaseStorage.getInstance().getReference();

    public static BaseEditFragment newInstance() {
        return new BaseEditFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_edit_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BaseEditViewModel.class);
        mToolbar = getView().findViewById(R.id.toolbarBaseEditor);
        imageViewProfileEdit = getView().findViewById(R.id.imageView_picture_baseedit);

        imageViewProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_baseEditFragment_to_pictureEditFragment);
            }
        });

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyProfile.class);
                startActivity(intent);
            }
        });

        loadProfileImage();
    }

    private void loadProfileImage(){
        String s = getResources().getString(R.string.defaultImageChildPath);
        mRef.child(s).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageViewProfileEdit);
            }
        });
    }

}
