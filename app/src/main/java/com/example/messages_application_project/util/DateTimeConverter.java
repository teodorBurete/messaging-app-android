package com.example.messages_application_project.util;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DateTimeConverter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @TypeConverter
    public String fromDate(LocalDateTime dateTime) {

        if (dateTime == null)
            return null;

        return formatter.format(dateTime);
    }

    @TypeConverter
    public LocalDateTime fromString(String dateTimeString) {
        try {
            return (LocalDateTime) formatter.parse(dateTimeString);
        } catch (Exception e) {
            return null;
        }
    }
}
