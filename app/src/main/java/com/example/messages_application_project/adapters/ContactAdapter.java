package com.example.messages_application_project.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.messages_application_project.R;
import com.example.messages_application_project.SendMessageActivity;
import com.example.messages_application_project.database.entities.Status;
import com.example.messages_application_project.database.entities.User;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter<User> {

    public static final String USER_KEY = "USER_KEY";
    public static final String LOGGED_USER_KEY = "LOGGED_USER_KEY";
    private Context context;
    private int resource;
    private List<User> friendlyUsers;
    private List<User> blockedUsers;
    private LayoutInflater inflater;
    private User loggedUser;

    public ContactAdapter(@NonNull Context context, int resource, @NonNull List<User> friendlyUsers, List<User> blockedUsers, User loggedUser, LayoutInflater inflater) {
        super(context, resource, friendlyUsers);

        this.inflater = inflater;
        this.context = context;
        this.resource = resource;
        this.friendlyUsers = friendlyUsers;
        this.loggedUser=loggedUser;
        this.blockedUsers =blockedUsers;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = inflater.inflate(resource, parent, false);
        User user = friendlyUsers.get(position);

        if(user == null){
            return view;
        }

        addUserName(view,user.getName());
        addStatus(view,user.getStatus());
        initializeSendMessageBtn(view,user);
        initializeBlockContactBtn(view,user);

        //todo finish the button

        return view;

    }

    private void initializeBlockContactBtn(View view, User user) {
        Button blockContactBtn = view.findViewById(R.id.adapter_contacts_row_block_btn);
        blockContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendlyUsers.remove(user);
                blockedUsers.add(user);
                notifyDataSetChanged();
            }
        });
    }

    private void initializeSendMessageBtn(View view,User user) {
        Button sendMessageBtn = view.findViewById(R.id.adapter_contacts_row_send_btn);
        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SendMessageActivity.class);
                intent.putExtra(USER_KEY,user);
                intent.putExtra(LOGGED_USER_KEY,loggedUser);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private void addStatus(View view, Status status) {
        TextView tv = view.findViewById(R.id.adapter_contacts_row_status);
        if(status!=null){
            tv.setText(status.toString());
        }

    }

    private void addUserName(View view,String userName) {

        TextView tv = view.findViewById(R.id.adapter_contacts_row_name);
        if(userName!=null){
            tv.setText(userName);
        }
    }

}
