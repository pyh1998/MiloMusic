package com.example.social_network_app.Basic_classes.UserDao;

public class CurrentUser extends User{

    private static CurrentUser instance = null;
    private CurrentUser(int id, String name, String email, int age, String sex, String head, int fans) {
        super(id, name, email, age, sex, head, fans);
    }

    public static CurrentUser getInstance(User user){
        if(instance == null){
            instance = new CurrentUser(user.getId(), user.getName(), user.getEmail(), user.getAge(), user.getSex(), user.getHead(), user.getFans());
        }
        return instance;
    }
    public static void delUser(){
        instance = null;
    }
}
