package com.example.social_network_app;

import android.app.Application;

import com.example.social_network_app.Basic_classes.PostDao.Post;
import com.example.social_network_app.Basic_classes.PostDao.PostDao;

import java.util.ArrayList;
import java.util.List;

public class GlobalVariable extends Application {
    private List<Post> postList = new ArrayList<>();


    @Override
    public void onCreate(){
        postList = getPostListFromFile();
        super.onCreate();
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public List<Post> getPostListFromFile(){
        PostDao postDao = new PostDao();
        return postDao.findAllPosts(this);
    }
}
