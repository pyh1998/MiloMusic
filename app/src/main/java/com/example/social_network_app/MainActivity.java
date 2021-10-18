package com.example.social_network_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
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
import com.example.social_network_app.Basic_classes.MusicDao.MusicDao;
import com.example.social_network_app.Basic_classes.MusicDao.MusicDaoInterface;
import com.example.social_network_app.Basic_classes.PostDao.Post;
import com.example.social_network_app.Basic_classes.PostDao.PostDao;
import com.example.social_network_app.Basic_classes.PostDao.PostInterface;
import com.example.social_network_app.Basic_classes.UserDao.User;
import com.example.social_network_app.Tokenizer_Parser.Music.MusicParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    List<Music> MusicList;
    List<Music> resultList = new ArrayList<>();
    List<Map<String,Object>> resultMapList = new ArrayList<>();
    User currentUser;

    ListView resultView;
    ImageButton searchButton;
    TextView tv_userName;
    ImageView iv_userHead;
    EditText search;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        currentUser = (User) getIntent().getSerializableExtra("CurrentUser");


        initView();

    }

    private void initView() {
        resultView = findViewById(R.id.rv_musiclist);
        searchButton = findViewById(R.id.ib_search);
        tv_userName = findViewById(R.id.tv_username);
        iv_userHead = findViewById(R.id.iv_userhead);
        search = findViewById(R.id.search);

        MusicList = getMusicList();
        resultList = MusicList;  //TODO search function(parse & token)
        showMusic(resultList);
        showUser();

        resultView.setOnItemClickListener(CommentViewListener);
        searchButton.setOnClickListener(searchResultListener);
    }

    private final AdapterView.OnItemClickListener CommentViewListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getApplicationContext(),CommentsActivity.class);
            Music music = resultList.get(i);
            Bundle bundle = new Bundle();
            bundle.putSerializable("CurrentUser", currentUser);
            bundle.putSerializable("Music",music);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    private final View.OnClickListener searchResultListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String searchText = search.getText().toString();
            MusicParser parser = new MusicParser(searchText);
            try{
                if(!parser.isValid()){
                    Toast.makeText(getApplicationContext(),"Invalid Input!",Toast.LENGTH_LONG).show();
                    return;
                }

            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Invalid Input! IllegalTokenException!",Toast.LENGTH_LONG).show();
            }
            resultList = new ArrayList<>();
            for(Music music : MusicList){
                if(parser.isMatched(music)){
                    resultList.add(music);
                }
            }
            Log.e("!!!!!!!!!!!!!!",resultList.toString());
            showMusic(resultList);
        }
    };



    @SuppressLint("SetTextI18n")
    public void showUser(){
        String name = currentUser.getName();
        String head_img = currentUser.getHeed();
        Log.e("!!!!!!!!!!!!!!",String.valueOf(head_img));
        tv_userName.setText(name + " !");
        try {
            Field field = R.drawable.class.getField(head_img);
            int img_id = field.getInt(field.getName());
            iv_userHead.setImageResource(img_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showMusic(List<Music> list){
        resultMapList.clear();
        for(int i =0;i<list.size();i++){
            Map<String,Object> map = new HashMap<>();
            Music music = list.get(i);
            try {
                Field field = R.drawable.class.getField(music.getPicture());
                int img_id = field.getInt(field.getName());
                map.put("m_img",img_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            map.put("m_name",music.getName());
            map.put("m_artist",music.getArtist());
            map.put("m_date",music.getReleaseDate());
            map.put("m_rate",music.getRate());
//            Log.e("!!!!!!!!!",map.toString());
            resultMapList.add(map);
        }
        SimpleAdapter listAdapter = new SimpleAdapter(
                this,
                resultMapList,
                R.layout.music_item,
                new String[]{"m_img","m_name","m_artist","m_date","m_rate"},
                new int[]{R.id.m_img,R.id.m_name,R.id.m_artist,R.id.m_date,R.id.m_rate}
        );
        resultView.setAdapter(listAdapter);
    }

    public List<Music> getMusicList(){
        MusicDaoInterface music = new MusicDao();
        return music.findAllMusics(this);
    }

    @Override
    protected
    void onSaveInstanceState(Bundle outState) {
        Log.d(TAG," -- onSaveInstanceState");
        Bundle bundle = new Bundle();
        bundle.putSerializable("CurrentUser", currentUser);
        outState.putBundle("CurrentUser", bundle);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected
    void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG," -- onRestoreInstanceState");
        if(savedInstanceState != null) {
            Bundle bundle = savedInstanceState.getBundle("CurrentUser");
            currentUser = (User) bundle.getSerializable("CurrentUser");
        }}

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG," -- onConfigurationChanged");
//        if(newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
//
//            setContentView(R.layout.activity_main);
//        }else{
//
//            setContentView(R.layout.activity_main);
//        }
        setContentView(R.layout.activity_main);
        initView();
    }
}