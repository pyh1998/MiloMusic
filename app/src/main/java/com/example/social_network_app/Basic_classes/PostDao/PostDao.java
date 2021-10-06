package com.example.social_network_app.Basic_classes.PostDao;

import android.content.res.AssetManager;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class PostDao implements PostInterface{

    @Override
    public List<Post> findAllPosts() {
        Gson gson = new Gson();
        JsonReader jsonReader = null;

        final Type CUS_LIST_TYPE = new TypeToken<List<Post>>() {}.getType();
        //or TypeToken.getParameterized(ArrayList.class, PersonJSON.class).getType();

        try{
            jsonReader = new JsonReader(new FileReader("app/src/main/assets/post_list.json"));
        }catch (Exception e) {
            e.printStackTrace();
        }

        return gson.fromJson(jsonReader, CUS_LIST_TYPE);
    }

}
