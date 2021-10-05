package com.example.social_network_app.Basic_class;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class Post {
    private int id;
    private Music music;
    private User user;
    private String userReviews;

    public Post(int id, int music_id, int user_id, String userReviews) {
        this.id = id;
        this.music = getMusic(music_id);
        this.user = getUser(user_id);
        this.userReviews = userReviews;
    }

    public  Music getMusic(int music_id){
        Gson gson = new Gson();
        JsonReader jsonReader = null;

        final Type CUS_LIST_TYPE = new TypeToken<List<Music>>() {}.getType();
        //or TypeToken.getParameterized(ArrayList.class, PersonJSON.class).getType();

        try{
            jsonReader = new JsonReader(new FileReader("app/src/main/assets/music_list.json"));
        }catch (Exception e) {
            e.printStackTrace();
        }

        List<Music> musicList = gson.fromJson(jsonReader, CUS_LIST_TYPE);
        System.out.println(musicList);
        for(Music music : musicList){
            if(music.getId() == music_id){
                return music;
            }
        }
        return null;
    }

    private User getUser(int user_id){
        Gson gson = new Gson();
        JsonReader jsonReader = null;

        final Type CUS_LIST_TYPE = new TypeToken<List<User>>() {}.getType();
        //or TypeToken.getParameterized(ArrayList.class, PersonJSON.class).getType();

        try{
            jsonReader = new JsonReader(new FileReader("app/src/main/assets/user.json"));
        }catch (Exception e) {
            e.printStackTrace();
        }

        List<User> userList = gson.fromJson(jsonReader, CUS_LIST_TYPE);
        System.out.println(userList);
        for(User user : userList){
            if(user.getId() == user_id){
                return user;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", music=" + music +
                ", user=" + user +
                ", userReviews='" + userReviews + '\'' +
                '}';
    }
}
