package com.example.social_network_app.Basic_classes;

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

    public Post(int id, int music_id, int user_id, String userReviews) {
        this.id = id;
        this.music = getMusic(music_id);
        this.user = getUser(user_id);
        this.userReviews = userReviews;
    }

    public static void main(String[] args) {
        System.out.println(getMusic(1));
        System.out.println(getUser(2));
    }

    public static Music getMusic(int music_id){
        MusicDaoInterface music = new MusicDao();
        return music.findMusicById(music_id);
    }

    private static User getUser(int user_id){
        UserDaoInterface user = new UserDao();
        return user.findUserById(user_id);
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
