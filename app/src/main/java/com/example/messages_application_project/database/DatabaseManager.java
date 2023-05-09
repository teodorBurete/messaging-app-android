package com.example.messages_application_project.database;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.messages_application_project.database.dao.ContactDao;
import com.example.messages_application_project.database.dao.MessageDao;
import com.example.messages_application_project.database.dao.UserDao;
import com.example.messages_application_project.database.entities.Contact;
import com.example.messages_application_project.database.entities.Message;
import com.example.messages_application_project.database.entities.User;
import com.example.messages_application_project.util.DateTimeConverter;

@RequiresApi(api = Build.VERSION_CODES.O)
@Database(entities = {User.class, Message.class, Contact.class},exportSchema = false,version = 1)
@TypeConverters({DateTimeConverter.class})
public abstract class DatabaseManager extends RoomDatabase {

    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {

        if (databaseManager == null) {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = Room.databaseBuilder(context, DatabaseManager.class, "chatapp_db")
                            .fallbackToDestructiveMigration()
                            //.allowMainThreadQueries()
                            .build();
                }
            }
        }
        return databaseManager;
    }

    public abstract UserDao getUserDao();

    public abstract MessageDao getMessageDao();

    public abstract ContactDao getContactDao();
}
