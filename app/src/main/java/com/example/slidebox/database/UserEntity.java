package com.example.slidebox.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
class UserEntity {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "user_name")
    private String user;

    @ColumnInfo(name = "password")
    private String password;

    public UserEntity(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
