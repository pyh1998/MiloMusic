package com.example.social_network_app.Basic_classes.PostDao;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.example.social_network_app.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class PostDao implements PostInterface{

    @Override
    public List<Post> findAllPosts(Context context) {
        String myjson = getJson(context, "post.json");
        Gson gson = new Gson();
        return  gson .fromJson(myjson, new TypeToken<List<Post>>(){}.getType());
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
