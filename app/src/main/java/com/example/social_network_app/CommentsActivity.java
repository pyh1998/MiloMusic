package com.example.social_network_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.example.social_network_app.Basic_classes.PostDao.Post;
import com.example.social_network_app.Basic_classes.PostDao.PostDao;
import com.example.social_network_app.Basic_classes.UserDao.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentsActivity extends AppCompatActivity {

    User currentUser;
    Music currentMusic;
    List<Post> postList = new ArrayList<>();
    List<Post> resultList = new ArrayList<>();
    List<Map<String,Object>> resultMapList = new ArrayList<>();

    ImageView userHead;
    ImageView MusicImage;
    TextView MusicName;
    TextView ArtistName;
    TextView AlbumName;
    TextView ReleaseDate;
    TextView CommentsCount;
    ListView Comments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        currentUser = (User) getIntent().getSerializableExtra("CurrentUser");
        currentMusic = (Music) getIntent().getSerializableExtra("Music");

        userHead = findViewById(R.id.iv_userhead_comments);
        MusicImage = findViewById(R.id.comment_music);
        MusicName = findViewById(R.id.music_name);
        ArtistName = findViewById(R.id.artist_name);
        AlbumName = findViewById(R.id.album_name);
        ReleaseDate = findViewById(R.id.album_releasedate);
        CommentsCount = findViewById(R.id.comments_count);
        Comments = findViewById(R.id.user_comments);

        postList = getPostList();

        for(int i=0;i<postList.size();i++){
            if(postList.get(i).getMusic(this).equals(currentMusic)){
                resultList.add(postList.get(i));
            }
        }
        showUser();
        showMusic();
        showComments(resultList);
        CommentsCount.setText(String.valueOf(resultList.size()));

        Comments.setOnItemClickListener(commentsListener);
    }

    private AdapterView.OnItemClickListener commentsListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getApplicationContext(),UserActivity.class);
            User user = resultList.get(i).getUser(CommentsActivity.this);
            Bundle bundle = new Bundle();
            bundle.putSerializable("User",user);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    public void showUser(){
        String head_img = currentUser.getHeed();
        try {
            Field field = R.drawable.class.getField(head_img);
            int img_id = field.getInt(field.getName());
            userHead.setImageResource(img_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showMusic(){
        String img = currentMusic.getPicture();
        try {
            Field field = R.drawable.class.getField(img);
            int img_id = field.getInt(field.getName());
            MusicImage.setImageResource(img_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MusicName.setText(currentMusic.getName());
        ArtistName.setText(currentMusic.getArtist());
        AlbumName.setText(currentMusic.getAlbum());
        ReleaseDate.setText(currentMusic.getReleaseDate());
    }

    public void showComments(List<Post> list){
        for(int i =0;i<list.size();i++){
            Map<String,Object> map = new HashMap<>();
            User user = list.get(i).getUser(this);
            String datetime = list.get(i).getDatetime();
            String comments = list.get(i).getUserReviews();
            try {
                Field field = R.drawable.class.getField(user.getHeed());
                int img_id = field.getInt(field.getName());
                map.put("comment_userhead",img_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            map.put("comment_user_name",user.getName());
            map.put("comment_date",datetime);
            map.put("comment",comments);
            resultMapList.add(map);
        }
        SimpleAdapter listAdapter = new SimpleAdapter(
                this,
                resultMapList,
                R.layout.comment_item,
                new String[]{"comment_userhead","comment_user_name","comment_date","comment"},
                new int[]{R.id.comment_userhead,R.id.comment_user_name,R.id.comment_date,R.id.comment}
        );
        Comments.setAdapter(listAdapter);
    }

    public List<Post> getPostList(){
        PostDao postDao = new PostDao();
        return postDao.findAllPosts(this);
    }
}