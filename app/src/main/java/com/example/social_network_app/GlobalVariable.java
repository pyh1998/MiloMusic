package com.example.social_network_app;

import android.app.Application;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.example.social_network_app.Basic_classes.MusicDao.MusicDao;
import com.example.social_network_app.Basic_classes.PostDao.Post;
import com.example.social_network_app.Basic_classes.PostDao.PostDao;
import com.example.social_network_app.Basic_classes.UserDao.CurrentUser;
import com.example.social_network_app.Basic_classes.UserDao.User;
import com.example.social_network_app.Basic_classes.UserDao.UserDao;
import com.example.social_network_app.DataStructure.Node;
import com.example.social_network_app.DataStructure.RBTree;

import java.util.ArrayList;
import java.util.List;

public class GlobalVariable extends Application {
    private List<Post> postList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
    private List<Music> musicList = new ArrayList<>();
    private RBTree<Double> MusicRateTree = new RBTree<>();
    private RBTree<String> MusicDateTree = new RBTree<>();
    private RBTree<Integer> PostLikeCountTree = new RBTree<>();
    private RBTree<String> PostDateTree = new RBTree<>();
    private CurrentUser user;
    private int totalPostCount;

    @Override
    public void onCreate(){
        super.onCreate();
        postList = getPostListFromFile();
        userList = getUserListFromFile();
        musicList = getMusicListFromFile();
        totalPostCount = postList.size();
    }

    private void initTree(){
        for(Post post: postList){
            PostLikeCountTree.insert(new Node<>(post.getLikeCount(),post));
            PostDateTree.insert(new Node<>(post.getDatetime(),post));
        }
        for(Music music: musicList){
            MusicRateTree.insert(new Node<>(music.getRate(),music));
            MusicDateTree.insert(new Node<>(music.getReleaseDate(),music));
        }
    }

    public RBTree<Double> getMusicRateTree() {
        return MusicRateTree;
    }

    public void setMusicRateTree(RBTree<Double> musicRateTree) {
        MusicRateTree = musicRateTree;
    }

    public RBTree<String> getMusicDateTree() {
        return MusicDateTree;
    }

    public void setMusicDateTree(RBTree<String> musicDateTree) {
        MusicDateTree = musicDateTree;
    }

    public RBTree<Integer> getPostLikeCountTree() {
        return PostLikeCountTree;
    }

    public void setPostLikeCountTree(RBTree<Integer> postLikeCountTree) {
        PostLikeCountTree = postLikeCountTree;
    }

    public RBTree<String> getPostDateTree() {
        return PostDateTree;
    }

    public void setPostDateTree(RBTree<String> postDateTree) {
        PostDateTree = postDateTree;
    }

    public int getTotalPostCount() {
        return totalPostCount;
    }

    public void setTotalPostCount(int totalPostCount) {
        this.totalPostCount = totalPostCount;
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
