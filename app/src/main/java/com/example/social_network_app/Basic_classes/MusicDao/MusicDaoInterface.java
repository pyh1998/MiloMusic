package com.example.social_network_app.Basic_classes.MusicDao;

import android.content.Context;

import java.util.List;

/**
 * @author Yuhui Pang
 *
 * The interface of MusicDao to get data
 */
public interface MusicDaoInterface {
     List<Music> findAllMusics(Context context);
     Music findMusicById(Context context, int id);
}
