package com.example.slidebox.ui.travel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TravelViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TravelViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Travel fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}