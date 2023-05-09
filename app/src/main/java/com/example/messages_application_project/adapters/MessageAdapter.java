package com.example.messages_application_project.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.messages_application_project.R;
import com.example.messages_application_project.database.entities.Message;

import java.time.LocalDateTime;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MessageAdapter extends ArrayAdapter<Message> {

    private Context context;
    private int resource;
    private List<Message> messages;
    private LayoutInflater inflater;
    private String senderName;
    private boolean isSentMessage;

    public MessageAdapter(@NonNull Context context, int resource, @NonNull List<Message> messages, boolean isSentMessage, String senderName, LayoutInflater inflater) {

        super(context, resource, messages);

        this.senderName = senderName;
        this.context = context;
        this.inflater = inflater;
        this.messages = messages;
        this.resource = resource;
        this.isSentMessage = isSentMessage;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = inflater.inflate(resource, parent, false);

        Message message = messages.get(position);
        if (message == null) {
            return view;
        }

        addUserName(view);
        addPreview(view, getTextPreview(message.getMessageContent()));
        addDate(view, message.getDate());
        return view;
    }

    private String getTextPreview(String messageContent) {
        messageContent = messageContent.trim();
        if (messageContent.length() < 30)
            return messageContent;

        return messageContent.substring(0, 30) + "...";
    }

    private void addDate(View view, LocalDateTime date) {
        TextView textView = view.findViewById(R.id.adapter_inbox_row_date);
        String time = context.getString(R.string.row_message_time_template, date.getDayOfMonth(), date.getMonthValue(), String.valueOf(date.getHour()), getMinuteString(date.getMinute()));
        textView.setText(time);
    }

    private String getMinuteString(int minute) {
        if (minute > 9)
            return String.valueOf(minute);

        return "0" + minute;
    }

    private void addPreview(View view, String textPreview) {
        TextView textView = view.findViewById(R.id.adapter_inbox_row_preview);
        if (textPreview != null) {
            textView.setText(textPreview);
        }
    }

    private void addUserName(View view) {
        TextView textView = view.findViewById(R.id.adapter_inbox_row_user);
        if (senderName != null) {
            textView.setText(getUserString());
        }
    }

    private String getUserString() {
        if (isSentMessage)
            return context.getString(R.string.row_messsage_userName_template, "TO: ", senderName);
        else
            return context.getString(R.string.row_messsage_userName_template, "FROM: ", senderName);
    }
}
