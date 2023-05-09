package com.example.messages_application_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.example.messages_application_project.database.entities.Message;
import com.example.messages_application_project.fragments.InboxFragment;

public class ShowMessageActivity extends AppCompatActivity {

    Intent intent;
    TextView tvMessage;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_message);

        intent = getIntent();
        tvMessage=findViewById(R.id.show_message_tv);
        Message message =(Message) intent.getSerializableExtra(InboxFragment.MESSAGE_KEY);
        String messageString = message.getMessageContent();
        tvMessage.setText(messageString);
    }
}