package com.example.messages_application_project.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.messages_application_project.R;
import com.example.messages_application_project.ShowMessageActivity;
import com.example.messages_application_project.adapters.MessageAdapter;
import com.example.messages_application_project.database.entities.Message;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SentFragment extends Fragment {

    public static final String MESSAGE_KEY = "MESSAGE_KEY";
    private ArrayList<Message> sentMessages;
    private ListView lvMessages;
    private String senderName = "senderName";

    public SentFragment() {
        // Required empty public constructor
    }

    public static SentFragment getInstance(ArrayList<Message> sentMessages) {
        SentFragment fragment = new SentFragment();
        Bundle args = new Bundle();
        args.putSerializable("key", sentMessages);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sentMessages = (ArrayList<Message>) getArguments().getSerializable("key");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sent, container, false);
        initComponents(view);
        setOnItemClickListener();
        return view;
    }

    private void setOnItemClickListener() {
        lvMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext().getApplicationContext(), ShowMessageActivity.class);
                intent.putExtra(MESSAGE_KEY,sentMessages.get(i));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().getApplicationContext().startActivity(intent);
            }
        });
    }

    private void initComponents(View view) {
        lvMessages = view.findViewById(R.id.sent_lv);
        if (getContext() != null) {
            MessageAdapter messageAdapter = new MessageAdapter(getContext().getApplicationContext(),
                    R.layout.lv_inbox_row_view,sentMessages,true,senderName,getLayoutInflater());
            lvMessages.setAdapter(messageAdapter);
        }
    }
}