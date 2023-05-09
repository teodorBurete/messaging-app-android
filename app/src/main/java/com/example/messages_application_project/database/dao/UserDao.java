package com.example.messages_application_project.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.messages_application_project.database.entities.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    public void insert(User user);

    @Query("SELECT * FROM users WHERE id = :id AND password= :pass")
    User getLogInUser(String id,String pass);

    @Update
    int update(User user);
}
