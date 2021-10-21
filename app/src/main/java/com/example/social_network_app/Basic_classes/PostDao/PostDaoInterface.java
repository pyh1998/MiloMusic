package com.example.social_network_app.Basic_classes.PostDao;

import android.content.Context;

import java.util.List;

/**
 * @author Yuhui Pang
 *
 * The interface of PostDao to get data
 */
public interface PostDaoInterface {
     List<Post> findAllPosts(Context context);
}
