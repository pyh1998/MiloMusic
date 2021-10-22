package com.example.social_network_app;

import android.app.Application;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.example.social_network_app.Basic_classes.MusicDao.MusicDao;
import com.example.social_network_app.Basic_classes.PostDao.Post;
import com.example.social_network_app.Basic_classes.PostDao.PostDao;
import com.example.social_network_app.Basic_classes.PostDao.PostDaoInterface;
import com.example.social_network_app.Basic_classes.UserDao.CurrentUser;
import com.example.social_network_app.Basic_classes.UserDao.User;
import com.example.social_network_app.Basic_classes.UserDao.UserDao;
import com.example.social_network_app.DataStructure.Node;
import com.example.social_network_app.DataStructure.RBTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * @author Yuhui Pang
 *
 * Global variables are used and updated in each activity
 */
public class GlobalVariable extends Application {
    private List<Post> postList = new LinkedList<>();
    private List<User> userList = new ArrayList<>();
    private List<Music> musicList = new ArrayList<>();
    private List<Post> likePost = new LinkedList<>();
    private RBTree<Double> MusicRateTree = new RBTree<>();  //The tree stored music rate and music
    private RBTree<String> MusicDateTree = new RBTree<>();  //The tree stored music date and music
    private RBTree<Integer> PostLikeCountTree = new RBTree<>();  //The tree stored post like count and music
    private RBTree<String> PostDateTree = new RBTree<>();  //The tree stored post date and music
    private CurrentUser user;
    private int totalPostCount;

    @Override
    public void onCreate(){
        super.onCreate();
        postList = getPostListFromFile();
        userList = getUserListFromFile();
        musicList = getMusicListFromFile();
        totalPostCount = postList.size();
        initTree();
    }

    /**
     * filter the hate language of post list
     */
    public void filterPost(){
        PostDaoInterface postDao = new PostDao();
        List<String> hate = postDao.findHateString(this);
        Iterator<Post> iterator = postList.iterator();
        while (iterator.hasNext()) {
            String comment = iterator.next().getUserReviews();
            for(String s : hate){
                if(comment.toLowerCase(Locale.ROOT).contains(s.toLowerCase(Locale.ROOT))){
                    iterator.remove();
                    break;
                }
            }
        }
    }

    /**
     * Update tree structure global variables
     */
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

    /**
     * add a like post to the liked post list
     * @param post the post need to be added
     */
    public void addLikePost(Post post){
        this.likePost.add(post);
    }

    /**
     * remove a like post to the liked post list
     * @param post the post need to be removed
     */
    public void removeLikePost(Post post){
        this.likePost.remove(post);
    }

    /**
     * check if this post be liked
     * @param post the post need be checked
     * @return return true if it is liked, return false if not
     */
   public boolean ifLike(Post post){
        return likePost.contains(post);
   }

    /**
     * The getter and setter function of these global variables
     */

    public List<Post> getLikePost() {
        return likePost;
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

    /**
     * Read data from a file
     * @return the list of post data stored in file
     */
    private List<Post> getPostListFromFile(){
        PostDao postDao = new PostDao();
        return postDao.findAllPosts(this);
    }
    /**
     * Read data from a file
     * @return the list of user data stored in file
     */
    private List<User> getUserListFromFile(){
        UserDao userDao = new UserDao();
        return userDao.findAllUsers(this);
    }
    /**
     * Read data from a file
     * @return the list of music data stored in file
     */
    private List<Music> getMusicListFromFile(){
        MusicDao musicDao = new MusicDao();
        return musicDao.findAllMusics(this);
    }
}
