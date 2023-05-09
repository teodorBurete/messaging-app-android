package com.example.messages_application_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.messages_application_project.async.Callback;
import com.example.messages_application_project.database.EntitiesService.UserService;
import com.example.messages_application_project.database.entities.Status;
import com.example.messages_application_project.database.entities.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CreateAccountActivity extends AppCompatActivity {

    public static final String USER_KEY = "userKey";
    //User user;
    TextInputEditText idTiet;
    TextInputEditText passTiet;
    TextInputEditText nameTiet;
    Button createAccBtn;
    UserService userService;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        userService = new UserService(getApplicationContext());
        intent = getIntent();
        initializeViews();

    }

    private void initializeViews() {

        idTiet = findViewById(R.id.create_acc_tiet_id);
        passTiet = findViewById(R.id.create_acc_tiet_pass);
        nameTiet = findViewById(R.id.create_acc_tiet_name);
        createAccBtn = findViewById(R.id.create_acc_btn_create);

        createAccBtn.setOnClickListener(getCreateAccountEvent());
    }

    private View.OnClickListener getCreateAccountEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    User user = buildUserFromComponents();
                    userService.insert(user, insertUserCallback());
                }
            }
        };
    }

    private User buildUserFromComponents() {

        String id = idTiet.getText().toString();
        String password = passTiet.getText().toString();
        String name = nameTiet.getText().toString();
        Status status = Status.AVAILABLE;

        return new User(id, password, name, status);

    }


    private Callback<User> insertUserCallback() {
        return new Callback<User>() {
            @Override
            public void runResultOnUiThread(User user) {
                if (user == null) {
                    Toast.makeText(getApplicationContext(),
                            "User ID already take. Try another!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Account created succesfully!",
                            Toast.LENGTH_SHORT).show();

                    intent.putExtra(USER_KEY, user);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        };

    }

    private boolean isValid() {

        if (idTiet.getText() == null ||
                idTiet.getText().toString().trim().length() < 4) {

            Toast.makeText(getApplicationContext(), "ID has to be at least 4 characters long.",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (passTiet.getText() == null ||
                passTiet.getText().toString().length() < 4) {

            Toast.makeText(getApplicationContext(), "Password has to be at least 4 characters long.",
                    Toast.LENGTH_SHORT).show();
        }

        if (nameTiet.getText() == null ||
                nameTiet.getText().toString().trim().length() < 2) {
            Toast.makeText(getApplicationContext(), "Name has to be at least 2 characters long.",
                    Toast.LENGTH_SHORT).show();

        }

        return true;
    }
}