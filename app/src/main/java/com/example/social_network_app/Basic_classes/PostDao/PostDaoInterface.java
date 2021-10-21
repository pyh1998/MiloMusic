package com.example.social_network_app.Basic_classes.PostDao;

import android.content.Context;

import java.io.FileReader;
import java.util.List;

public interface PostDaoInterface {
    public List<Post> findAllPosts(Context context);
}
