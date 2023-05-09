package com.example.messages_application_project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.messages_application_project.R;
import com.example.messages_application_project.database.entities.User;

import java.util.List;

public class BlockedAdapter extends ArrayAdapter<User> {

    private Context context;
    private int resource;
    private List<User> blockedUsers;
    private LayoutInflater inflater;
    private List<User> friendlyUsers;


    public BlockedAdapter(@NonNull Context context, int resource, @NonNull List<User> blockedUsers, List<User> friendlyUsers, LayoutInflater inflater) {
        super(context, resource, blockedUsers);

        this.context=context;
        this.resource=resource;
        this.blockedUsers = blockedUsers;
        this.inflater=inflater;
        this.friendlyUsers=friendlyUsers;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        User user = blockedUsers.get(position);

        if(user == null){
            return view;
        }

        addUserName(view,user.getName());
        addUserId(view,user.getId());
        initializeUnblockButton(view,user);

        return view;
    }

    private void initializeUnblockButton(View view, User user) {
        Button blockContactBtn = view.findViewById(R.id.adapter_blocked_row_unblock_btn);
        blockContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blockedUsers.remove(user);
                friendlyUsers.add(user);
                notifyDataSetChanged();
            }
        });
    }

    private void addUserId(View view, String id) {
        TextView tv = view.findViewById(R.id.adapter_blocked_row_user_id);
        if(id!=null){
            tv.setText(id);
        }
    }

    private void addUserName(View view, String name) {
        TextView tv = view.findViewById(R.id.adapter_blocked_row_name);
        if(name!=null){
            tv.setText(name);
        }
    }
}
