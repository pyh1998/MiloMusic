package com.example.social_network_app.Basic_classes.UserDao;

import java.util.List;

public interface UserDaoInterface {
    public List<User> findAllUsers();
    public User findUserById(int id);

}
