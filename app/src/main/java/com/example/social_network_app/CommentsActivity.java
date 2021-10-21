package com.example.social_network_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.example.social_network_app.Basic_classes.PostDao.Post;
import com.example.social_network_app.Basic_classes.PostDao.PostDao;
import com.example.social_network_app.Basic_classes.UserDao.User;
import com.example.social_network_app.Tokenizer_Parser.Music.MusicParser;
import com.example.social_network_app.Tokenizer_Parser.Music.MusicToken;
import com.example.social_network_app.Tokenizer_Parser.Post.PostParser;
import com.example.social_network_app.Tokenizer_Parser.Post.PostToken;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class CommentsActivity extends AppCompatActivity {

    User currentUser;
    Music currentMusic;
    List<Post> postList = new ArrayList<>();
    List<Post> resultList = new ArrayList<>();
    List<Post> currentList = new ArrayList<>();
    List<Post> searchResultList = new ArrayList<>();
    List<Map<String,Object>> resultMapList = new ArrayList<>();
    public static final int COMPLETED = 0;

    Button postComment;
    EditText editComment;
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
    ImageButton searchButton;
    EditText search;
    int start;
    int end;
    Timer timer = new Timer();

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == COMPLETED){
                showComments(currentList);
                Comments.setSelection(ListView.FOCUS_DOWN);
                CommentsCount.setText(String.valueOf(start));
                Comments.setOnItemClickListener(commentsListener);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        currentUser = (User) getIntent().getSerializableExtra("CurrentUser");
        currentMusic = (Music) getIntent().getSerializableExtra("Music");

        GlobalVariable global = (GlobalVariable) getApplication();
        postList = global.getPostList();

        initView();

        for(int i=0;i<postList.size();i++){
            if(postList.get(i).getMusic(this).equals(currentMusic)){
                resultList.add(postList.get(i));
            }
        }
        searchResultList = resultList;

        showUser();
        showMusic();

        start = resultList.size() / 5;
        end = start + 1;
        currentList = resultList.subList(0,start+1);
        showComments(currentList);
        CommentsCount.setText(String.valueOf(start+1));

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new WorkThread().start();
                if (end == resultList.size()-1){
                    timer.cancel();
                }
            }
        },5000,5000);


    }

    private void initView(){
        userHead = findViewById(R.id.iv_userhead_comments);
        MusicImage = findViewById(R.id.comment_music);
        MusicName = findViewById(R.id.music_name);
        ArtistName = findViewById(R.id.artist_name);
        AlbumName = findViewById(R.id.album_name);
        ReleaseDate = findViewById(R.id.album_releasedate);
        CommentsCount = findViewById(R.id.comments_count);
        Comments = findViewById(R.id.user_comments);
        user_name = findViewById(R.id.c_user_name);

        search = findViewById(R.id.comment_search);
        searchButton = findViewById(R.id.ib_search2);

        like_num = findViewById(R.id.comment_likenum);
        like = findViewById(R.id.comment_like);
        liked = findViewById(R.id.comment_liked);
        postComment = findViewById(R.id.comment_button);
        editComment = findViewById(R.id.user_post_comments);

        Comments.setOnItemClickListener(commentsListener);
        searchButton.setOnClickListener(searchResultListener);
        userHead.setOnClickListener(showUserDetailListener);
        postComment.setOnClickListener(postNewListener);
    }

    private final View.OnClickListener postNewListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            timer.cancel();
            String newPost = editComment.getText().toString();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate =  new Date(System.currentTimeMillis());
            String date = formatter.format(curDate);
            if(!newPost.equals("")){
                Post post = new Post(postList.size(),currentMusic.getId(),currentUser.getId(),currentUser.getName(),newPost,date,0);
                postList.add(post);
                currentList.add(post);
                GlobalVariable global = (GlobalVariable) getApplication();
                global.setPostList(postList);
                editComment.setText("");
                Toast.makeText(getApplicationContext(),"Post successfully!",Toast.LENGTH_LONG).show();
                showComments(currentList);
                Comments.setSelection(ListView.FOCUS_DOWN);
            }
        }
    };

    private final View.OnClickListener showUserDetailListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(),UserActivity.class);
            User user = currentUser;
            intent.putExtra("User",user);
            intent.putExtra("CurrentUser",currentUser);
            startActivity(intent);
        }
    };

    private AdapterView.OnItemClickListener commentsListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getApplicationContext(),UserActivity.class);
            User user = resultList.get(i).getUser(CommentsActivity.this);
            intent.putExtra("User",user);
            intent.putExtra("CurrentUser",currentUser);
            startActivity(intent);
        }
    };

    private final View.OnClickListener searchResultListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            timer.cancel();
            String searchText = search.getText().toString();
            if(searchText.equals("")){
                showComments(currentList);
            }
            else{
                PostParser parser = new PostParser(searchText);
                try{
                    if(!parser.isValid()){
                        List<PostToken> validList = parser.getValidList();
                        if(validList.size() == 0){
                            Toast.makeText(getApplicationContext(),"Invalid Input!",Toast.LENGTH_LONG).show();
                            return;
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Partial Valid Input! Valid condition: "+validList.toString(),Toast.LENGTH_LONG).show();
                        }
                    }

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Invalid Input! IllegalTokenException!",Toast.LENGTH_LONG).show();
                }
                searchResultList = new ArrayList<>();
                for(Post post : currentList){
                    if(parser.isMatched(post)){
                        searchResultList.add(post);
                    }
                }
                Log.e("!!!!!!!!!!!!!!",searchResultList.toString());
                showComments(searchResultList);
                Toast toast = Toast.makeText(getApplicationContext(),"Search successfully! "+searchResultList.size()+" result(s)",Toast.LENGTH_LONG);
                //toast.setGravity(Gravity.TOP,0,0);
                toast.show();
            }
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
        resultMapList.clear();
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
            resultMapList.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                resultMapList,
                R.layout.comment_item,
                new String[]{"comment_userhead","comment_user_name","comment_date","comment","likeCount"},
                new int[]{R.id.comment_userhead,R.id.comment_user_name,R.id.comment_date,R.id.comment,R.id.comment_likenum}
        ) {
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

        Comments.setAdapter(adapter);
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

    public class WorkThread extends Thread{
        @Override
        public void run(){
            start++;
            end++;
            currentList = resultList.subList(0,end);
            Message msg = new Message();
            msg.what = COMPLETED;
            handler.sendMessage(msg);
        }
    }

//    public List<Post> getPostList(){
//        PostDao postDao = new PostDao();
//        return postDao.findAllPosts(this);
//    }
}