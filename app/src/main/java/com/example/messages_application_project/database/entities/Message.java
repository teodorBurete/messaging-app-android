package com.example.messages_application_project.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(tableName = "messages")
public class Message implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "sender_id")
    private  String senderId;

    @ColumnInfo(name = "recipient_id")
    private  String recipientId;

    @ColumnInfo(name = "message_content")
    private String messageContent;

    @ColumnInfo(name = "date_time")
    private LocalDateTime date;

    public Message(long id, String senderId, String recipientId, String messageContent, LocalDateTime date) {
        this.id = id;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.messageContent = messageContent;
        this.date = date;
    }

    @Ignore
    public Message(String senderId, String recipientId, String messageContent, LocalDateTime date) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.messageContent = messageContent;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
