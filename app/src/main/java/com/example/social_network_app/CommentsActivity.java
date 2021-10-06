package com.example.social_network_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.example.social_network_app.Basic_classes.UserDao.User;

public class CommentsActivity extends AppCompatActivity {

    User currentUser;
    Music currentMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        currentUser = (User) getIntent().getSerializableExtra("CurrentUser");
        currentMusic = (Music) getIntent().getSerializableExtra("Music");
        Log.e("!!!!!!!!!!!!!",currentUser.toString());
        Log.e("!!!!!!!!!!!!!",currentMusic.toString());
    }
}