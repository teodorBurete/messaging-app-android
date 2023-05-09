package com.example.messages_application_project;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.example.messages_application_project.database.entities.User;
import com.google.android.material.textfield.TextInputEditText;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    public static final String USER_KEY = "userKey";
    private Button logInBtn;
    private Button createAccBtn;
    private TextInputEditText idTiet;
    private TextInputEditText passwordTiet;
    private UserService userService;

    private ActivityResultLauncher<Intent> createAccLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userService = new UserService(getApplicationContext());
        initializeViews();
        setOnClickListeners();
        createAccLauncher = registerCreateAccountLauncher();


    }

    private View.OnClickListener getLogInEventListener() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (idTiet.getText() == null || passwordTiet.getText() == null) {
                    Toast.makeText(MainActivity.this, "Type in ID and Password.", Toast.LENGTH_SHORT).show();
                } else {
                    String userId = idTiet.getText().toString().trim();
                    String userPass = passwordTiet.getText().toString();

                    getUserFromDB(userId, userPass);

                }

            }

        };
    }


    private void getUserFromDB(String userId, String userPass) {
        userService.getLogInUser(userId, userPass, getUserIdCallback());
    }

    private Callback<User> getUserIdCallback() {
        return new Callback<User>() {
            @Override
            public void runResultOnUiThread(User result) {
                if (result == null) {
                    Toast.makeText(getApplicationContext(),
                            "User ID and password combination not found!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), InboxActivity.class);
                    intent.putExtra(USER_KEY, result);
                    startActivity(intent);

                }
            }
        };
    }

    private ActivityResultLauncher<Intent> registerCreateAccountLauncher() {

        ActivityResultCallback<ActivityResult> callback = getCreateAccountCallback();
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);
    }

    private ActivityResultCallback<ActivityResult> getCreateAccountCallback() {

        return new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK &&
                        result.getData() != null) {

                    User user = (User) result.getData()
                            .getSerializableExtra(CreateAccountActivity.USER_KEY);

                    idTiet.setText(user.getId());
                    passwordTiet.setText(user.getPassword());

                }
            }
        };

    }

    private View.OnClickListener getCreateAccuntEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                createAccLauncher.launch(intent);

            }
        };
    }

    private void initializeViews() {
        logInBtn = findViewById(R.id.main_button_login);
        createAccBtn = findViewById(R.id.main_button_createAccount);
        idTiet = findViewById(R.id.main_tiet_id);
        passwordTiet = findViewById(R.id.main_tiet_pass);


    }

    private void setOnClickListeners() {
        createAccBtn.setOnClickListener(getCreateAccuntEventListener());
        logInBtn.setOnClickListener(getLogInEventListener());

    }
}