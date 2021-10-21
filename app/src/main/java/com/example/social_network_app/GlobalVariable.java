package com.example.social_network_app;

import android.app.Application;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.example.social_network_app.Basic_classes.MusicDao.MusicDao;
import com.example.social_network_app.Basic_classes.PostDao.Post;
import com.example.social_network_app.Basic_classes.PostDao.PostDao;
import com.example.social_network_app.Basic_classes.UserDao.CurrentUser;
import com.example.social_network_app.Basic_classes.UserDao.User;
import com.example.social_network_app.Basic_classes.UserDao.UserDao;

import java.util.ArrayList;
import java.util.List;

public class GlobalVariable extends Application {
    private List<Post> postList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
    private List<Music> musicList = new ArrayList<>();
    private CurrentUser user;

    @Override
    public void onCreate(){
        super.onCreate();
        postList = getPostListFromFile();
        userList = getUserListFromFile();
        musicList = getMusicListFromFile();
    }

    public CurrentUser getUser() {
        return user;
    }

    public void setUser(CurrentUser user) {
        this.user = user;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public List<Music> getMusicList() {
        return musicList;
    }

    private List<Post> getPostListFromFile(){
        PostDao postDao = new PostDao();
        return postDao.findAllPosts(this);
    }
    private List<User> getUserListFromFile(){
        UserDao userDao = new UserDao();
        return userDao.findAllUsers(this);
    }
    private List<Music> getMusicListFromFile(){
        MusicDao musicDao = new MusicDao();
        return musicDao.findAllMusics(this);
    }
}
