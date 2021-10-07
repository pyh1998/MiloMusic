package com.example.social_network_app.Basic_classes.MusicDao;

import android.content.Context;

import java.util.List;

public interface MusicDaoInterface {
    public List<Music> findAllMusics(Context context);
    public Music findMusicById(Context context, int id);
}
