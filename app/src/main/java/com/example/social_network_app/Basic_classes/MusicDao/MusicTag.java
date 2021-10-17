package com.example.social_network_app.Basic_classes.MusicDao;


public enum MusicTag {
    alternative(1),blues(2),country(3),dance(4),electronica(5),
    hiphop(6),pop(7),rap(8),rock(9),R_B(10);

    int id;

    MusicTag(int id) {
        this.id = id;
    }
    public static MusicTag getTagById(int id){
        for(MusicTag m : MusicTag.values()){
            if(m.id == id){
                return m;
            }
        }
        return null;
    }
}
