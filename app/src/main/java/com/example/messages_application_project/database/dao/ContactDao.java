package com.example.messages_application_project.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.messages_application_project.database.entities.Contact;
@Dao
public interface ContactDao {

    @Insert
    long insert(Contact contact);
}
