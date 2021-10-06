package com.example.social_network_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.social_network_app.Basic_classes.MusicDao.MusicDao;
import com.example.social_network_app.Basic_classes.PostDao.Post;
import com.example.social_network_app.Basic_classes.PostDao.PostDao;
import com.example.social_network_app.Basic_classes.PostDao.PostInterface;
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

    List<Post> postList;
    List<Map<String,Object>> resultList = new ArrayList<>();
    ListView resultView;
    ImageButton searchButton;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultView = findViewById(R.id.rv_musiclist);
        searchButton = findViewById(R.id.ib_search);

        getPostList();

        for(int i =0;i<postList.size();i++){
            Map<String,Object> map = new HashMap<>();
            Post post = postList.get(i);
            try {
                Log.e("!!!!!!!!!",post.getMusic().getPicture());
                Field field = R.drawable.class.getField(post.getMusic().getPicture());
                Log.e("!!!!!!!!!",field.getName());
                int img_id = field.getInt(field.getName());
                map.put("m_img",img_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            map.put("m_name",post.getMusic().getName());
            map.put("m_artist",post.getMusic().getArtist());
            map.put("m_album",post.getMusic().getAlbum());
            map.put("m_rate",post.getMusic().getRate());
            Log.e("!!!!!!!!!",map.toString());
            resultList.add(map);
        }
        SimpleAdapter listAdapter = new SimpleAdapter(
                this,
                resultList,
                R.layout.music_item,
                new String[]{"m_img","m_name","m_artist","m_album","m_rate"},
                new int[]{R.id.m_img,R.id.m_name,R.id.m_artist,R.id.m_album,R.id.m_rate}
        );
        resultView.setAdapter(listAdapter);
    }

    public void getPostList(){
        String myjson = getJson(MainActivity.this, "post_list.json");
        Gson gson = new Gson();
        postList = (List<Post>)gson .fromJson(myjson, new TypeToken<List<Post>>(){}.getType());
    }

    public static String getJson(Context context, String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}