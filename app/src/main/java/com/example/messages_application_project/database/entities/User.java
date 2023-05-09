package com.example.messages_application_project.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "users")
public class User implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NotNull
    private String id;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "status")
    private Status status;

    public User(@NonNull String id, String password, String name, Status status){
        this.id=id;
        this.password=password;
        this.name=name;
        this.status=status;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(String Status) {
        this.status = status;
    }
}
