package com.example.social_network_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.example.social_network_app.Basic_classes.PostDao.Post;
import com.example.social_network_app.Basic_classes.UserDao.CurrentUser;
import com.example.social_network_app.Basic_classes.UserDao.User;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yuhui Pang, Man Jin
 */
public class UserActivity extends AppCompatActivity {

    User user;
    CurrentUser CurrentUser;
    List<Post> postList = new ArrayList<>();
    List<Post> resultList = new ArrayList<>();
    List<Map<String,Object>> resultMapList = new ArrayList<>();
    List<User> userList = new ArrayList<>();

    ImageView iv_user_userhead;
    TextView user_username;
    TextView user_userage;
    TextView user_usersex;
    TextView user_useremail;
    TextView user_commentscount;
    ListView comments;

    TextView user_fanscount;
    TextView user_likescount;

    GlobalVariable global;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        user = (User) getIntent().getSerializableExtra("User");

        global = (GlobalVariable) getApplication();
        postList = global.getPostList();
        CurrentUser = global.getUser();
        userList = global.getUserList();

        initView();

        for(int i=0;i<postList.size();i++){
            if(postList.get(i).getUser(userList).equals(user)){
                resultList.add(postList.get(i));
            }
        }
        sortList();
        showUserDetail();
        showComments(resultList);

        user_commentscount.setText(String.valueOf(resultList.size()));
        user_fanscount.setText(String.valueOf(user.getFans()));
        int total_likes = 0;
        for(int i=0;i<postList.size();i++){
            if(postList.get(i).getUser(userList).equals(user)){
                total_likes+=postList.get(i).getLikeCount();
            }
        }
        user_likescount.setText(String.valueOf(total_likes));
        findViewById(R.id.report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reportIntent = new Intent(getApplicationContext(), BarActivity.class);
                reportIntent.putExtra("commentsCount",user_commentscount.getText());
                reportIntent.putExtra("fansCount",user_fanscount.getText());
                reportIntent.putExtra("likesCount", user_likescount.getText());
                reportIntent.putExtra("user",user);
                startActivity(reportIntent);
            }
        });

        comments.setOnItemClickListener(resultViewListener);
    }

    public void initView(){
        iv_user_userhead = findViewById(R.id.iv_user_userhead);
        user_username = findViewById(R.id.user_username);
        user_userage = findViewById(R.id.user_userage);
        user_usersex = findViewById(R.id.user_usersex);
        user_useremail = findViewById(R.id.user_useremail);
        user_commentscount = findViewById(R.id.user_commentscount);
        comments = findViewById(R.id.user_usercomments);

        user_fanscount = findViewById(R.id.user_fanscount);
        user_likescount = findViewById(R.id.user_likescount);
    }

    /**
     * Initialization interface
     */
    private final AdapterView.OnItemClickListener resultViewListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getApplicationContext(),CommentsActivity.class);
            Music music = resultList.get(i).getMusic(UserActivity.this);
            Bundle bundle = new Bundle();
            bundle.putSerializable("CurrentUser", CurrentUser);
            bundle.putSerializable("Music",music);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    /**
     * Sorts the results in the specified sort manner
     */
    private void sortList(){
        Collections.sort(resultList);
    }

    /**
     * Displays user details
     */
    public void showUserDetail(){
        String head_img = user.getHead();
        try {
            Field field = R.drawable.class.getField(head_img);
            int img_id = field.getInt(field.getName());
            iv_user_userhead.setImageResource(img_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        user_username.setText(user.getName());
        user_userage.setText(String.valueOf(user.getAge()));
        user_usersex.setText(user.getSex());
        user_useremail.setText(user.getEmail());
    }

    /**
     * Show the result of comments
     * @param list the list of result to be shown
     */
    public void showComments(List<Post> list){
        for(int i =0;i<list.size();i++){
            Map<String,Object> map = new HashMap<>();
            Music music = list.get(i).getMusic(this);
            String datetime = list.get(i).getDatetime();
            String comments = list.get(i).getUserReviews();
            String likeCount = String.valueOf(list.get(i).getLikeCount());
            try {
                Field field = R.drawable.class.getField(music.getPicture());
                int img_id = field.getInt(field.getName());
                map.put("comment_userhead",img_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            map.put("comment_user_name",music.getName());
            map.put("comment_date",datetime);
            map.put("comment",comments);
            map.put("likeCount",likeCount);
            resultMapList.add(map);
        }
        /*
        Initialize a flag list that stores whether it is liked or not
         */
        List<Boolean> flags = new ArrayList<>();
        for(Post post: list){
            if(global.ifLike(post)){
                flags.add(true);
            }
            else flags.add(false);
        }

        SimpleAdapter listAdapter = new SimpleAdapter(
                this,
                resultMapList,
                R.layout.comment_item,
                new String[]{"comment_userhead","comment_user_name","comment_date","comment","likeCount"},
                new int[]{R.id.comment_userhead,R.id.comment_user_name,R.id.comment_date,R.id.comment,R.id.comment_likenum}
        ){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                final View view=super.getView(position, convertView, parent);
                ImageButton like = view.findViewById(R.id.comment_like);
                ImageButton liked = view.findViewById(R.id.comment_liked);
                TextView num = view.findViewById(R.id.comment_likenum);
                if(flags.get(position)){
                    like.setVisibility(View.INVISIBLE);
                    liked.setVisibility(View.VISIBLE);
                    num.setText(String.valueOf(list.get(position).getLikeCount()));
                }
                else{
                    liked.setVisibility(View.INVISIBLE);
                    like.setVisibility(View.VISIBLE);
                    num.setText(String.valueOf(list.get(position).getLikeCount()));
                }
                like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        flags.set(position,true);
                        int likenum = Integer.parseInt((String) num.getText());
                        likenum += 1;
                        updateLikeCount(list.get(position).getId(),likenum);
                        global.addLikePost(list.get(position));
                        num.setText(String.valueOf(likenum));
                        user_likescount.setText(String.valueOf(Integer.parseInt(user_likescount.getText().toString())+1));
                        like.setVisibility(View.INVISIBLE);
                        liked.setVisibility(View.VISIBLE);
                    }
                });
                liked.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        flags.set(position,false);
                        int likenum = Integer.parseInt((String) num.getText());
                        likenum -= 1;
                        updateLikeCount(list.get(position).getId(),likenum);
                        global.removeLikePost(list.get(position));
                        num.setText(String.valueOf(likenum));
                        user_likescount.setText(String.valueOf(Integer.parseInt(user_likescount.getText().toString())-1));
                        liked.setVisibility(View.INVISIBLE);
                        like.setVisibility(View.VISIBLE);
                    }
                });
                return view;
            }
        };
        comments.setAdapter(listAdapter);
    }
    /**
     * Click the like button to update the like count of post
     * @param id the post id of clicked post
     * @param newCount the new like count of this post
     */
    public void updateLikeCount(int id,int newCount){
        for(int i =0;i<postList.size();i++){
            if(postList.get(i).getId() == id){
                postList.get(i).setLikeCount(newCount);
                break;
            }
        }
        global.setPostList(postList);
    }
}