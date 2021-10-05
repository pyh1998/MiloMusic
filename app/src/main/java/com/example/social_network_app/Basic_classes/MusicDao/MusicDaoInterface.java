package com.example.social_network_app.Basic_classes.MusicDao;

import java.util.List;

public interface MusicDaoInterface {
    public List<Music> findAllMusics();
    public Music findMusicById(int id);
}
