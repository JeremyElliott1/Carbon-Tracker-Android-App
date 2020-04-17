package com.example.slidebox.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserEntity.class}, version = 1,exportSchema = false)

public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase INSTANCE;
    static synchronized AppDataBase getDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class,"app_database")
                    .build();
        }
        return INSTANCE;
    }

    public abstract BaseDao getBaseDao();
}
