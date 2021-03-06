package com.example.social_network_app.Basic_classes.UserDao;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author Yuhui Pang
 *
 * To get user data from file user.json
 */
public class UserDao implements UserDaoInterface{
    @Override
    public List<User> findAllUsers(Context context) {
        String myjson = getJson(context, "user.json");
        Gson gson = new Gson();
        return  gson.fromJson(myjson, new TypeToken<List<User>>(){}.getType());
    }

    @Override
    public User findUserById(Context context,int id) {
        List<User> userList = findAllUsers(context);
        for(User user : userList){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public static String getJson(Context context, String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        AssetManager assetManager = context.getAssets();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
