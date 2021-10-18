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
import com.example.social_network_app.Basic_classes.PostDao.PostDao;
import com.example.social_network_app.Basic_classes.UserDao.User;

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
    TextView user_name;
    ListView Comments;
    TextView like_num;
    ImageButton like;
    ImageButton liked;


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
        user_name = findViewById(R.id.c_user_name);

        like_num = findViewById(R.id.comment_likenum);
        like = findViewById(R.id.comment_like);
        liked = findViewById(R.id.comment_liked);


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
            bundle.putSerializable("CurrentUser",currentUser);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };



    public void showUser(){
        String head_img = currentUser.getHead();
        try {
            Field field = R.drawable.class.getField(head_img);
            int img_id = field.getInt(field.getName());
            userHead.setImageResource(img_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        user_name.setText(currentUser.getName());
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
            String likeCount = String.valueOf(list.get(i).getLikeCount());
            try {
                Field field = R.drawable.class.getField(user.getHead());
                int img_id = field.getInt(field.getName());
                map.put("comment_userhead",img_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            map.put("comment_user_name",user.getName());
            map.put("comment_date",datetime);
            map.put("comment",comments);
            map.put("likeCount",likeCount);
            map.put("button1","a");
            map.put("button2","b");
            resultMapList.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(
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
                    num.setText(String.valueOf(likenum));
                    liked.setVisibility(View.INVISIBLE);
                    like.setVisibility(View.VISIBLE);
                }
            });
                return view;
            }
        };
        Comments.setAdapter(adapter);
    }

    public List<Post> getPostList(){
        PostDao postDao = new PostDao();
        return postDao.findAllPosts(this);
    }
}