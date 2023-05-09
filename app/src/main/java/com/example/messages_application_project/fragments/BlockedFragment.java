package com.example.messages_application_project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.messages_application_project.R;
import com.example.messages_application_project.adapters.BlockedAdapter;
import com.example.messages_application_project.database.entities.User;

import java.util.ArrayList;


public class BlockedFragment extends Fragment {

    public static final String FRIENDLY_KEY = "FRIENDLY_KEY";
    public static final String BLOCKED_KEY = "BLOCKED_KEY";
    private ArrayList<User> blockedUsers;
    private ArrayList<User> friendlyUsers;
    private ListView lvUsers;

    public BlockedFragment() {
        // Required empty public constructor
    }


    public static BlockedFragment getInstance(ArrayList<User> blockedUsers,ArrayList<User> friendlyUsers) {
        BlockedFragment fragment = new BlockedFragment();
        Bundle args = new Bundle();
        args.putSerializable(BLOCKED_KEY,blockedUsers);
        args.putSerializable(FRIENDLY_KEY,friendlyUsers);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            blockedUsers= (ArrayList<User>) getArguments().getSerializable(BLOCKED_KEY);
            friendlyUsers = (ArrayList<User>) getArguments().getSerializable(FRIENDLY_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_blocked, container, false);
       initComponents(view);
       return view;
    }

    private void initComponents(View view) {
        lvUsers = view.findViewById(R.id.blocked_lv);

        if(getContext()!=null){
            BlockedAdapter blockedAdapter = new BlockedAdapter(getContext().getApplicationContext()
                    ,R.layout.lv_blocked_row_view,blockedUsers,friendlyUsers,getLayoutInflater());

            lvUsers.setAdapter(blockedAdapter);
        }
    }
}