package com.example.slidebox.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class DataBaseViewModel extends AndroidViewModel {
    private BaseDao baseDao;
    private AppDataBase appDataBase;

    public DataBaseViewModel(@NonNull Application application) {
        super(application);
        appDataBase = AppDataBase.getDatabase(application);
    }
}
