package com.example.slidebox.ui.recyclables;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class recyclablesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public recyclablesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Recyclables fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}