package com.example.messages_application_project;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.messages_application_project.database.entities.Contact;
import com.example.messages_application_project.database.entities.Message;
import com.example.messages_application_project.database.entities.Status;
import com.example.messages_application_project.database.entities.User;
import com.example.messages_application_project.fragments.BlockedFragment;
import com.example.messages_application_project.fragments.ContactsFragment;
import com.example.messages_application_project.fragments.InboxFragment;
import com.example.messages_application_project.fragments.ProfileFragment;
import com.example.messages_application_project.fragments.SentFragment;
import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.O)
public class InboxActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Intent intent;
    private Fragment currentFragment;
    private User loggedUser;
    private ArrayList<Message> receivedMessages;
    private ArrayList<Message> sentMessages;
    private ArrayList<User> friendlyUsers;
    private ArrayList<User> blockedUsers;
    private TextView userNameTv;
    private TextView userStatusTv; // ?? nu stiu de ce nu merge

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        retrieveUserData();
        getUserMessages();
        getContacts();

        configNavigation();
        getNavigationItemSelectedtListener();
        openDefaultFragment(savedInstanceState);

//        userNameTv = findViewById(R.id.nav_header_user_name_tv);
//        userStatusTv = findViewById(R.id.nav_header_status_tv);
//        userNameTv.setText(loggedUser.getName());
//        userStatusTv.setText(loggedUser.getStatus().toString()); ??

    }

    private void getContacts() {
        ArrayList<Contact> friendlyContacts = new ArrayList<>();
        ArrayList<Contact> blockedContacts = new ArrayList<>();

        //TODO QUERY: retrieve all contacts where this.User.userId == user1Id row of the Contacts table && blocked = false;
        friendlyContacts.add(new Contact("user1", "user2", false));
        //TODO QUERY: retrieve all contacts where this.User.userId == user1Id row of the Contacts table && blocked = true;
        blockedContacts.add(new Contact("user1", "user3", true));

        getFriendlyUsers(friendlyContacts);
        getBlockedUsers(blockedContacts);

    }

    private void getBlockedUsers(ArrayList<Contact> blockedContacts) {
        //TODO QUERY: retrieve all users with contact.user2id == userId row of the Users table
        blockedUsers=new ArrayList<>();
        blockedUsers.add(new User("user3","asdd","User3Name", Status.AVAILABLE));

    }

    private void getFriendlyUsers(ArrayList<Contact> contacts) {
        //TODO QUERY: retrieve all users with contact.user2id == userId row of the Users table
        friendlyUsers = new ArrayList<>();
        friendlyUsers.add(new User("user2","asdd","User2Name", Status.AVAILABLE));


    }

    private void getUserMessages() {
        sentMessages = new ArrayList<>();
        receivedMessages = new ArrayList<>();

        sentMessages.add(new Message(0001L, loggedUser.getId(), "user2", "messageee", LocalDateTime.now()));
        receivedMessages.add(new Message(0002L, "user3", loggedUser.getId(), "message", LocalDateTime.now()));


    }

    private void retrieveUserData() {
        intent = getIntent();
        loggedUser = (User) intent.getSerializableExtra(MainActivity.USER_KEY);
    }

    private void openDefaultFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            currentFragment = InboxFragment.getInstance(receivedMessages);
            openFragment();
            navigationView.setCheckedItem(R.id.drawer_menu_profile);
        }
    }


    private void configNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void getNavigationItemSelectedtListener() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.drawer_menu_inbox) {
                    currentFragment = InboxFragment.getInstance(receivedMessages);
                }
                if (item.getItemId() == R.id.drawer_menu_contacts) {
                    currentFragment = ContactsFragment.getInstance(friendlyUsers,blockedUsers,loggedUser);
                }
                if (item.getItemId() == R.id.drawer_menu_profile) {
                    currentFragment = ProfileFragment.getInstance(loggedUser);
                }
                if (item.getItemId() == R.id.drawer_menu_blocked) {
                    currentFragment = BlockedFragment.getInstance(blockedUsers,friendlyUsers);
                }
                if (item.getItemId() == R.id.drawer_menu_sent_messages) {
                    currentFragment = SentFragment.getInstance(sentMessages);
                }
                openFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void openFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame_container, currentFragment)
                .commit();
    }
}