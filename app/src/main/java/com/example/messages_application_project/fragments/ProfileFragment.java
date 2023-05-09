package com.example.messages_application_project.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.messages_application_project.R;
import com.example.messages_application_project.async.Callback;
import com.example.messages_application_project.database.EntitiesService.UserService;
import com.example.messages_application_project.database.entities.Status;
import com.example.messages_application_project.database.entities.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


@RequiresApi(api = Build.VERSION_CODES.O)
public class ProfileFragment extends Fragment {

    public static final String LOGGED_USER = "LOGGED_USER";
    private Spinner spinner;
    private TextInputEditText tietName;
    private User loggedUser;
    private Button saveBttn;
    private UserService userService = new UserService(getContext());

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment getInstance(User loggedUser) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(LOGGED_USER, loggedUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            loggedUser = (User) getArguments().getSerializable(LOGGED_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        spinner = view.findViewById(R.id.profile_spinner);
        tietName = view.findViewById(R.id.profile_tiet_name);
        saveBttn = view.findViewById(R.id.profile_button_save);
        if (getContext() != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext().getApplicationContext()
                    , R.array.profile_status_values,
                    android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            tietName.setText(loggedUser.getName());
            saveBttn.setOnClickListener(getSaveButtonEvent());
        }
    }

    private View.OnClickListener getSaveButtonEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUserName = tietName.getText().toString().trim();

                if (isValidName(newUserName)) {
                    loggedUser.setName(newUserName);
                  //  loggedUser.setStatus(spinner.get); //TODO
                    userService.update(loggedUser, updateUserCallback());
                }
            }


            private boolean isValidName(String newUserName) {
                if (newUserName == null ||
                        newUserName.length() < 2) {
                    Toast.makeText(getContext().getApplicationContext(), "Name has to be at least 2 characters long.",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }
        };
    }

    private Callback<User> updateUserCallback() {
        return new Callback<User>() {
            @Override
            public void runResultOnUiThread(User result) {
                if(result!=null){
                    Toast.makeText(getContext().getApplicationContext(), "Changes saved!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext().getApplicationContext(), "Changes failed to save!", Toast.LENGTH_SHORT).show();

                }
            }
        };
    }
}