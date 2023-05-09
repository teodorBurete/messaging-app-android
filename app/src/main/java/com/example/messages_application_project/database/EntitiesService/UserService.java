package com.example.messages_application_project.database.EntitiesService;

import android.content.Context;
import android.os.Build;
import android.telecom.Call;


import androidx.annotation.RequiresApi;

import com.example.messages_application_project.async.AsyncTaskRunner;
import com.example.messages_application_project.async.Callback;
import com.example.messages_application_project.database.DatabaseManager;
import com.example.messages_application_project.database.dao.UserDao;
import com.example.messages_application_project.database.entities.User;

import java.util.concurrent.Callable;

@RequiresApi(api = Build.VERSION_CODES.O)
public class UserService {

    private final UserDao userDao;
    private final AsyncTaskRunner asyncTaskRunner;

    public UserService(Context context) {
        this.userDao = DatabaseManager.getInstance(context).getUserDao();
        this.asyncTaskRunner = new AsyncTaskRunner();
    }

    public void update(User user, Callback<User> updateActivityThread){
        Callable<User> updateOperation = new Callable<User>() {
            @Override
            public User call() throws Exception {
                if(user!=null){

                }
                int count = userDao.update(user);
                if(count<=0){
                    return null;
                }
                return user;
            }
        };

        asyncTaskRunner.executeAsync(updateOperation,updateActivityThread);
    }

    public void insert(User user, Callback<User> insertActivityThread) {
        Callable<User> insertOperation = new Callable<User>() {
            @Override
            public User call() {
                //se realizaeaza operatia de insert, ne aflam pe alt fir de executie

                if (user == null)
                    return null;

                try {
                    userDao.insert(user);
                    return user;
                } catch (Exception e) {
                    return null;
                }

            }
        };

        asyncTaskRunner.executeAsync(insertOperation, insertActivityThread);
    }

    public void getLogInUser(String userId, String userPass, Callback<User> getLoginUserActivityThread) {
        Callable<User> getLogInUser = new Callable<User>() {
            @Override
            public User call() {

                return userDao.getLogInUser(userId,userPass);
            }
        };

        asyncTaskRunner.executeAsync(getLogInUser, getLoginUserActivityThread);
    }
}
