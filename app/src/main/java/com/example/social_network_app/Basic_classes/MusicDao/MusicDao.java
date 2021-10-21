package com.example.social_network_app.Basic_classes.MusicDao;


import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class MusicDao implements MusicDaoInterface  {

    @Override
    public List<Music> findAllMusics(Context context) {
        String myjson = getJson(context, "music.json");
        Gson gson = new Gson();
        return  gson.fromJson(myjson, new TypeToken<List<Music>>(){}.getType());
    }

    @Override
    public Music findMusicById(Context context, int id) {
        List<Music> musicList = findAllMusics(context);
        for(Music music : musicList){
            if(music.getId() == id){
                return music;
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
