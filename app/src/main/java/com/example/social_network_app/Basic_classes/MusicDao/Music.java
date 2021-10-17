package com.example.social_network_app.Basic_classes.MusicDao;


import java.io.Serializable;

public class Music implements Serializable {
    private int id;
    private String name;
    private String artist;
    private String album;
    private String releaseDate;
    private double rate;
    private String picture;
    private String tag;

    public Music(int id, String name, String artist, String album, String releaseDate, double rate,String picture,String tag){
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.releaseDate = releaseDate;
        this.rate = rate;
        this.picture = picture;
        this.tag = tag;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPicture() {
        return picture;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public MusicTag[] getTag() {
        String[] tags = tag.split(",");
        MusicTag[] musicTags = new MusicTag[tags.length];
        for(int i = 0;i<musicTags.length;i++){
            musicTags[i] = MusicTag.getTagById(Integer.parseInt(tags[i]));
        }
        return musicTags;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Music{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", rate=" + rate +
                ", picture='" + picture + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }

    public boolean equals(Music music){
        return this.id == music.id;
    }


}
