package com.example.social_network_app.Tokenizer_Parser.Music;

import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

public class MusicParser {
    List<MusicToken> tokenList = new ArrayList<>();

    private static final int tag_max = 3;
    private static final int artist_max = 1;
    private static final int rate_max = 1;
    private static final int name_max = 1;

    private MusicTokenizer tokenizer;

    public MusicParser(String searchText){
        this.tokenizer = new MusicTokenizer(searchText);
    }

    public boolean isValid(String searchText){
        try {
            MusicTokenizer tokenizer = new MusicTokenizer(searchText);
            while (tokenizer.hasNext()) {
                tokenList.add(tokenizer.current());
                tokenizer.next();
            }
        }
        catch (Exception e){
            return false;
        }
        int tag = 0;
        int artist = 0;
        int rate = 0;
        int name = 0;
        for(MusicToken token : tokenList){
            switch (token.getType()){
                case TAG:
                    tag++;
                    break;
                case ARTIST:
                    artist++;
                    break;
                case STAR:
                    rate++;
                    break;
                case NAME:
                    name++;
                    break;
            }
        }
        return tag <= tag_max && artist <= artist_max && rate <= rate_max && name <= name_max;
    }



}
