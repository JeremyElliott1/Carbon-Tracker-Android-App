package com.example.slidebox.ui.reusables;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReusablesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ReusablesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}