package com.example.social_network_app.Basic_classes.MusicDao;


import android.content.res.AssetManager;

import com.example.social_network_app.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class MusicDao implements MusicDaoInterface {

    @Override
    public List<Music> findAllMusics() {


        Gson gson = new Gson();
        JsonReader jsonReader = null;

        final Type CUS_LIST_TYPE = new TypeToken<List<Music>>() {}.getType();
        //or TypeToken.getParameterized(ArrayList.class, PersonJSON.class).getType();

        try{
            jsonReader = new JsonReader(new FileReader("app/src/main/assets/music_list.json"));
        }catch (Exception e) {
            e.printStackTrace();
        }

        return gson.fromJson(jsonReader, CUS_LIST_TYPE);
    }

    @Override
    public Music findMusicById(int id) {
        List<Music> musicList = findAllMusics();
        for(Music music : musicList){
            if(music.getId() == id){
                return music;
            }
        }
        return null;
    }
}
