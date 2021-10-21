package com.example.social_network_app.Basic_classes.UserDao;

import android.content.Context;

import java.util.List;

/**
 * @author Yuhui Pang
 *
 * The interface of UserDao to get data
 */
public interface UserDaoInterface {
     List<User> findAllUsers(Context context);
     User findUserById(Context context, int id);
}
