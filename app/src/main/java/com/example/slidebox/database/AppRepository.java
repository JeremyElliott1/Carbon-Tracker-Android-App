package com.example.slidebox.database;

import androidx.lifecycle.LiveData;

public class AppRepository {

    private LiveData<String> user;

    public AppRepository(LiveData<String> user) {
        this.user = user;
    }
}
