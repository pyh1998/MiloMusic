package com.example.social_network_app.Basic_classes.UserDao;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class UserDao implements UserDaoInterface{
    @Override
    public List<User> findAllUsers() {
        Gson gson = new Gson();
        JsonReader jsonReader = null;

        final Type CUS_LIST_TYPE = new TypeToken<List<User>>() {}.getType();
        //or TypeToken.getParameterized(ArrayList.class, PersonJSON.class).getType();

        try{
            jsonReader = new JsonReader(new FileReader("app/src/main/assets/user.json"));
        }catch (Exception e) {
            e.printStackTrace();
        }

        return gson.fromJson(jsonReader, CUS_LIST_TYPE);
    }

    @Override
    public User findUserById(int id) {
        List<User> userList = findAllUsers();
        for(User user : userList){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }
}
