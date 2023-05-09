package com.example.messages_application_project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.messages_application_project.R;
import com.example.messages_application_project.adapters.ContactAdapter;
import com.example.messages_application_project.database.entities.Status;
import com.example.messages_application_project.database.entities.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class ContactsFragment extends Fragment {


    public static final String FRIENDLY_KEY = "FRIENDLY_KEY";
    public static final String BLOCKED_KEY = "BLOCKED_KEY";
    public static final String LOGGED_USER_KEY = "LOGGED_USER_KEY";
    private ArrayList<User> friendlyUsers;
    private User loggedUser;
    private ListView lvUsers;
    private Button btnAddContact;
    private TextInputEditText tietUserId;
    ArrayList<User> blockedUsers;

    public ContactsFragment() {
        // Required empty public constructor
    }

    public static ContactsFragment getInstance(ArrayList<User> friendlyUsers, ArrayList<User> blockedUsers,User loggedUser) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putSerializable(FRIENDLY_KEY,friendlyUsers);
        args.putSerializable(BLOCKED_KEY,blockedUsers);
        args.putSerializable(LOGGED_USER_KEY,loggedUser);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            friendlyUsers = (ArrayList<User>) getArguments().getSerializable(FRIENDLY_KEY);
            blockedUsers = (ArrayList<User>) getArguments().getSerializable(BLOCKED_KEY);
            loggedUser= (User) getArguments().getSerializable(LOGGED_USER_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        lvUsers = view.findViewById(R.id.contacts_lv);
        btnAddContact = view.findViewById(R.id.contacts_button_add);
        tietUserId= view.findViewById(R.id.contacts_tiet_add_contact);

        if(getContext()!=null){
            ContactAdapter contactAdapter = new ContactAdapter(getContext().getApplicationContext(),
                    R.layout.lv_contacts_row_view,friendlyUsers,blockedUsers,loggedUser,getLayoutInflater());

            lvUsers.setAdapter(contactAdapter);

            setAddContactOnClickListener(btnAddContact,contactAdapter);
        }
    }

    private void setAddContactOnClickListener(Button addContactBtn,ContactAdapter contactAdapter) {
        addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User addedUser = new User("addedUser","asdd","addedUser", Status.AVAILABLE);
                //User addedUser = query with user id;
                if(addedUser!=null){
                    friendlyUsers.add(addedUser);
                    contactAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext().getApplicationContext(), "User added!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext().getApplicationContext(), "User not found!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}