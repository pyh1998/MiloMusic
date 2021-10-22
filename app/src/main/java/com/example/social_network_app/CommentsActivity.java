package com.example.social_network_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.example.social_network_app.Basic_classes.UserDao.CurrentUser;
import com.example.social_network_app.Basic_classes.UserDao.User;
import com.example.social_network_app.DataStructure.RBTree;
import com.example.social_network_app.Tokenizer_Parser.Post.PostParser;
import com.example.social_network_app.Tokenizer_Parser.Post.PostToken;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Yuhui Pang, Hang Su
 *
 * The comments page of specific music
 */
public class CommentsActivity extends AppCompatActivity {

    CurrentUser currentUser;
    Music currentMusic;
    List<Post> postList = new LinkedList<>();
    List<Post> resultList = new LinkedList<>();
    List<Post> currentList = new LinkedList<>();
    List<Post> searchResultList = new LinkedList<>();
    List<Map<String,Object>> resultMapList = new ArrayList<>();
    List<User> userList = new ArrayList<>();
    private RBTree<Integer> PostLikeCountTree = new RBTree<>();
    private RBTree<String> PostDateTree = new RBTree<>();
    public static final int COMPLETED = 0;
    GlobalVariable global;

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
    boolean flag = true;

    /**
     * Operations performed during each time period in the timeline
     */
    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == COMPLETED){
                end++;
                currentList = resultList.subList(0,end);
                showComments(currentList);
                Comments.setSelection(ListView.FOCUS_DOWN);
                Comments.setOnItemClickListener(commentsListener);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        global = (GlobalVariable) getApplication();
        currentMusic = (Music) getIntent().getSerializableExtra("Music");
        global.filterPost();
        postList = global.getPostList();
        userList = global.getUserList();
        PostLikeCountTree = global.getPostLikeCountTree();
        PostDateTree = global.getPostDateTree();
        currentUser = global.getUser();

        initView();

        for(int i=0;i<postList.size();i++){
            if(postList.get(i).getMusic(this).equals(currentMusic)){
                resultList.add(postList.get(i));
            }
        }
        sortList();
        searchResultList = resultList;

        showUser();
        showMusic();

        //Initialization displays one-fifth of the comments
        start = resultList.size() / 5;
        end = start;
        currentList = resultList.subList(0,start);
        showComments(currentList);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(flag){
                    new WorkThread().start();
                    if (end >= resultList.size()-1){
                        timer.cancel();
                    }
                }
            }
        },3000,3000);
    }

    /**
     * Initialization interface
     */
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

    /**
     * Click the POST button to post a new comment
     * Add a new comment to the global variable
     */
    private final View.OnClickListener postNewListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String newPost = editComment.getText().toString();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate =  new Date(System.currentTimeMillis());
            String date = formatter.format(curDate);
            if(!newPost.equals("")){
                Post post = new Post(global.getTotalPostCount()+1,currentMusic.getId(),currentUser.getId(),currentUser.getName(),newPost,date,0);
                global.setTotalPostCount(global.getTotalPostCount()+1);
                postList.add(post);
                currentList.add(post);
                global.setPostList(postList);
                editComment.setText("");
                Toast.makeText(getApplicationContext(),"Post successfully!",Toast.LENGTH_LONG).show();
                showComments(currentList);
                Comments.setSelection(ListView.FOCUS_DOWN);
            }
        }
    };

    /**
     * Click the current user profile picture to display detailed
     * information about the current user
     */
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

    /**
     * Click the view list to display the detailed information of
     * the corresponding user to jump to the user interface
     */
    private AdapterView.OnItemClickListener commentsListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getApplicationContext(),UserActivity.class);
            User user = currentList.get(i).getUser(userList);
            intent.putExtra("User",user);
            startActivity(intent);
        }
    };

    /**
     * Click the search button to display the query results according to the query content
     */
    private final View.OnClickListener searchResultListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String searchText = search.getText().toString();
            if(searchText.equals("")){
                currentList = resultList.subList(0,end);
                showComments(currentList);
                flag = true;
            }
            else{
                flag = false;
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
                //currentList = searchResultList;
                showComments(searchResultList);
                Toast toast = Toast.makeText(getApplicationContext(),"Search successfully! "+searchResultList.size()+" result(s)",Toast.LENGTH_LONG);
                toast.show();
            }
        }
    };

    /**
     * Sorts the results in the specified sort manner
     */
    private void sortList(){
        Collections.sort(resultList);
    }

    /**
     * To show the user head and username of current user
     */
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

    /**
     * To show the detail of current music
     */
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

    /**
     * Show the result of comments
     * @param list the list of result to be shown
     */
    public void showComments(List<Post> list){
        CommentsCount.setText(String.valueOf(list.size()));
        resultMapList.clear();
        for(int i =0;i<list.size();i++){
            Map<String,Object> map = new HashMap<>();

            User user = list.get(i).getUser(userList);
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
            map.put("comment_user_name",list.get(i).getUser_name());
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

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                resultMapList,
                R.layout.comment_item,
                new String[]{"comment_userhead","comment_user_name","comment_date","comment","likeCount"},
                new int[]{R.id.comment_userhead,R.id.comment_user_name,R.id.comment_date,R.id.comment,R.id.comment_likenum}
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
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
                    liked.setVisibility(View.INVISIBLE);
                    like.setVisibility(View.VISIBLE);
                }
            });
                return view;
            }
        };

        Comments.setAdapter(adapter);
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

    public class WorkThread extends Thread{
        @Override
        public void run(){
            //start++;
            Message msg = new Message();
            msg.what = COMPLETED;
            handler.sendMessage(msg);
        }
    }

}