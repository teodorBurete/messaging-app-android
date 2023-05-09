package com.example.messages_application_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.messages_application_project.adapters.ContactAdapter;
import com.example.messages_application_project.database.entities.User;
import com.google.android.material.textfield.TextInputEditText;

public class SendMessageActivity extends AppCompatActivity {

    Intent intent;
    TextView tvUserName;
    Button btnSend;
    User loggedUser;
    User recipentUser;
    TextInputEditText tietMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        initComponents();
        setRecipentName();
        setClickListeners();

    }

    private void setClickListeners() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO add message in DB
                finish();
            }
        });
    }

    private void setRecipentName() {
        tvUserName.append(recipentUser.getName());

    }

    private void initComponents() {
        intent=getIntent();
        this.tvUserName = findViewById(R.id.send_message_tv_username);
        this.tietMessage = findViewById(R.id.send_message_tiet_message);
        this.btnSend = findViewById(R.id.send_message_btn_send);

        loggedUser = (User) intent.getSerializableExtra(ContactAdapter.LOGGED_USER_KEY);
        recipentUser = (User) intent.getSerializableExtra(ContactAdapter.USER_KEY);

    }
}