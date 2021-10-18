package com.example.social_network_app.Basic_classes.MusicDao;


public enum MusicTag {
    alternative(1),blues(2),country(3),dance(4),electronica(5),
    hiphop(6),pop(7),rap(8),rock(9),RB(10);

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

    @Override
    public String toString() {
        switch (id){
            case 1:
                return "alternative";
            case 2:
                return "blues";
            case 3:
                return "country";
            case 4:
                return "dance";
            case 5:
                return "electronica";
            case 6:
                return "hiphop";
            case 7:
                return "pop";
            case 8:
                return "rap";
            case 9:
                return "rock";
            case 10:
                return "R&B";
            default:
                return null;
        }
    }
}
