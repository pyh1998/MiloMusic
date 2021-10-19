package com.example.social_network_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.social_network_app.Basic_classes.PostDao.PostDao;
import com.example.social_network_app.Basic_classes.UserDao.User;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

    User user;
    User CurrentUser;
    List<Post> postList = new ArrayList<>();
    List<Post> resultList = new ArrayList<>();
    List<Map<String,Object>> resultMapList = new ArrayList<>();

    ImageView iv_user_userhead;
    TextView user_username;
    TextView user_userage;
    TextView user_usersex;
    TextView user_useremail;
    TextView user_commentscount;
    ListView comments;

    TextView user_fanscount;
    TextView user_likescount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        iv_user_userhead = findViewById(R.id.iv_user_userhead);
        user_username = findViewById(R.id.user_username);
        user_userage = findViewById(R.id.user_userage);
        user_usersex = findViewById(R.id.user_usersex);
        user_useremail = findViewById(R.id.user_useremail);
        user_commentscount = findViewById(R.id.user_commentscount);
        comments = findViewById(R.id.user_usercomments);

        user_fanscount = findViewById(R.id.user_fanscount);
        user_likescount = findViewById(R.id.user_likescount);


        Intent intent = getIntent();
        user = (User) getIntent().getSerializableExtra("User");
        CurrentUser = (User) getIntent().getSerializableExtra("CurrentUser");

        GlobalVariable globalVariable = (GlobalVariable) getApplication();
        postList = globalVariable.getPostList();
        for(int i=0;i<postList.size();i++){
            if(postList.get(i).getUser(this).equals(user)){
                resultList.add(postList.get(i));
            }
        }
        showUserDetail();
        showComments(resultList);

        user_commentscount.setText(String.valueOf(resultList.size()));
        user_fanscount.setText(String.valueOf(user.getFans()));
        int total_likes = 0;
        for(int i=0;i<postList.size();i++){
            if(postList.get(i).getUser(this).equals(user)){
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
                reportIntent.putExtra("username",user_username.getText());
                startActivity(reportIntent);
            }
        });

        comments.setOnItemClickListener(resultViewListener);
    }

    private AdapterView.OnItemClickListener resultViewListener = new AdapterView.OnItemClickListener() {
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
                like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int likenum = Integer.parseInt((String) num.getText());
                        likenum += 1;
                        updateLikeCount(list.get(position).getId(),likenum);
                        num.setText(String.valueOf(likenum));
                        like.setVisibility(View.INVISIBLE);
                        liked.setVisibility(View.VISIBLE);
                    }
                });
                liked.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int likenum = Integer.parseInt((String) num.getText());
                        likenum -= 1;
                        updateLikeCount(list.get(position).getId(),likenum);
                        num.setText(String.valueOf(likenum));
                        liked.setVisibility(View.INVISIBLE);
                        like.setVisibility(View.VISIBLE);
                    }
                });
                return view;
            }
        };
        comments.setAdapter(listAdapter);
    }

    public void updateLikeCount(int id,int newCount){
        for(int i =0;i<postList.size();i++){
            if(postList.get(i).getId() == id){
                postList.get(i).setLikeCount(newCount);
                break;
            }
        }
        GlobalVariable globalVariable = (GlobalVariable) getApplication();
        globalVariable.setPostList(postList);
    }

    public List<Post> getPostList(){
        PostDao postDao = new PostDao();
        return postDao.findAllPosts(this);
    }
}