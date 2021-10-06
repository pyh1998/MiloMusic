package com.example.social_network_app.Basic_classes.PostDao;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.example.social_network_app.Basic_classes.MusicDao.MusicDao;
import com.example.social_network_app.Basic_classes.MusicDao.MusicDaoInterface;
import com.example.social_network_app.Basic_classes.UserDao.User;
import com.example.social_network_app.Basic_classes.UserDao.UserDao;
import com.example.social_network_app.Basic_classes.UserDao.UserDaoInterface;
import com.google.gson.Gson;
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
    private String datetime;

    public Post(int id, int music_id, int user_id, String userReviews,String datetime) {
        this.id = id;
        this.music = getMusicByID(music_id);
        this.user = getUserById(user_id);
        this.userReviews = userReviews;
        this.datetime = datetime;
    }

    public static void main(String[] args) {
        System.out.println(getMusicByID(1));
        System.out.println(getUserById(2));
        //PostInterface post = new PostDao();
        //System.out.println(PostDao.findAllPost());
    }

    public static Music getMusicByID(int music_id){
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
        for(Music music : musicList){
            if(music.getId() == music_id){
                return music;
            }
        }
        return null;
    }

    private static User getUserById(int user_id){
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
        for(User user : userList){
            if(user.getId() == user_id){
                return user;
            }
        }
        return null;
    }

//    public Music getMusicByID(int music_id){
//        MusicDaoInterface music = new MusicDao();
//        return music.findMusicById(music_id);
//    }
//
//    private User getUserById(int user_id){
//        UserDaoInterface user = new UserDao();
//        return user.findUserById(user_id);
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(String userReviews) {
        this.userReviews = userReviews;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
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
