package com.example.social_network_app.Basic_classes.UserDao;

import android.content.Context;

import java.util.List;

public interface UserDaoInterface {
    public List<User> findAllUsers(Context context);
    public User findUserById(Context context, int id);

}
