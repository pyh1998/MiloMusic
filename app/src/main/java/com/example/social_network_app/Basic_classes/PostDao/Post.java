package com.example.social_network_app.Basic_classes.PostDao;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.example.social_network_app.Basic_classes.MusicDao.MusicDao;
import com.example.social_network_app.Basic_classes.MusicDao.MusicDaoInterface;
import com.example.social_network_app.Basic_classes.UserDao.User;

import java.io.Serializable;
import java.util.List;

/**
 * @author Yuhui Pang
 *
 * The post class, which can be sort by datetime
 */
public class Post implements Serializable,Comparable<Post> {
    private int id;
    private final int music_id;
    private final int user_id;
    private final String user_name;
    private final String userReviews;
    private final String datetime;
    private int likeCount;


    public Post(int id, int music_id, int user_id, String user_name, String userReviews, String datetime, int likeCount) {
        this.id = id;
        this.music_id = music_id;
        this.user_id = user_id;
        this.user_name =user_name;
        this.userReviews = userReviews;
        this.datetime = datetime;
        this.likeCount = likeCount;
    }

    public String getUser_name() {
        return user_name;
    }


    public Music getMusic(Context context){
        MusicDaoInterface music = new MusicDao();
        return music.findMusicById(context, music_id);
    }

    public User getUser(List<User> list){
        for(User user : list){
            if(user.getId() == this.user_id)
                return user;
        }
        return null;
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

    public String getUserReviews() {
        return userReviews;
    }

    public String getDatetime() {
        return datetime;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    @NonNull
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

    @Override
    public int compareTo(Post post) {
        return this.getDatetime().compareTo(post.getDatetime());
    }
}
