package com.example.messages_application_project.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import org.jetbrains.annotations.NotNull;

@Entity(tableName="contacts", primaryKeys = {"user1_id","user2_id"})
public class Contact {

    @ColumnInfo(name = "user1_id")
    @NotNull
    private String user1Id;

    @ColumnInfo(name = "user2_id")
    @NotNull
    private String user2Id;

    @ColumnInfo(name="blocked")
    boolean blocked;

    public Contact(String user1Id, String user2Id, boolean blocked) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.blocked = blocked;
    }

    public String getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(String user1Id) {
        this.user1Id = user1Id;
    }

    public String getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(String user2Id) {
        this.user2Id = user2Id;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
