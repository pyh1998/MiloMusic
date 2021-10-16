package com.example.social_network_app.Basic_classes.PostDao;

import android.content.Context;

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
    private int music_id;
    private int user_id;
    private String userReviews;
    private String datetime;
    private int likeCount;


    public Post(int id, int music_id, int user_id, String userReviews, String datetime, int likeCount) {
        this.id = id;
        this.music_id = music_id;
        this.user_id = user_id;
        this.userReviews = userReviews;
        this.datetime = datetime;
        this.likeCount = likeCount;
    }

    public Music getMusic(Context context){
        MusicDaoInterface music = new MusicDao();
        return music.findMusicById(context, music_id);
    }

    public User getUser(Context context){
        UserDaoInterface user = new UserDao();
        return user.findUserById(context,user_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMusic_id() {
        return music_id;
    }

    public void setMusic_id(int music_id) {
        this.music_id = music_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", music_id=" + music_id +
                ", user_id=" + user_id +
                ", userReviews='" + userReviews + '\'' +
                ", datetime='" + datetime + '\'' +
                '}';
    }
}
