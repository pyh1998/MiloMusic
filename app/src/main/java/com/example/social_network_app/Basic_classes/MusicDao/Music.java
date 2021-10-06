package com.example.social_network_app.Basic_classes.MusicDao;



public class Music {
    private int id;
    private String name;
    private String artist;
    private String album;
    private String releaseDate;
    private double rate;
    private String picture;

    public Music(int id, String name, String artist, String album, String releaseDate, double rate,String picture){
        System.out.println("!!!!");
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.releaseDate = releaseDate;
        this.rate = rate;
        this.picture = picture;
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

    @Override
    public String toString() {
        return "Music{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", releaseDate=" + releaseDate +
                ", picture='" + picture + '\'' +
                '}';
    }
}
