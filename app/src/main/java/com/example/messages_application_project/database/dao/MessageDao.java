package com.example.messages_application_project.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.messages_application_project.database.entities.Message;
@Dao
public interface MessageDao {

    @Insert
    long insert(Message message);
}
