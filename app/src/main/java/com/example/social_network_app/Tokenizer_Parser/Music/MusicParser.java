package com.example.social_network_app.Tokenizer_Parser.Music;

import android.widget.Switch;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.example.social_network_app.Basic_classes.MusicDao.MusicTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MusicParser {

    /**
     * The following exception should be thrown if the parse is faced with series of tokens that do not
     * correlate with any possible production rule.
     */
    public static class IllegalParserException extends IllegalArgumentException {
        public IllegalParserException(String errorMessage) {
            super(errorMessage);
        }
    }

    /**
     * The search upper limit for each condition
     */
    private static final int tag_max = 3;
    private static final int artist_max = 1;
    private static final int rate_max = 1;
    private static final int name_max = 1;

    private List<MusicToken> tokenList = new ArrayList<>();

    public MusicParser(String searchText){
        MusicTokenizer tokenizer = new MusicTokenizer(searchText);
        try {
            while (tokenizer.hasNext()) {
                tokenList.add(tokenizer.current());
                tokenizer.next();
            }
        }
        catch (Exception e){
            throw new MusicToken.IllegalTokenException("IllegalTokenException");
        }
    }

    /**
     * Determines whether the current conditional string is valid
     * @return return true if the string is valid
     */
    public boolean isValid(){
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

//    public List<MusicToken> getTokenList(){
//        MusicTokenizer musicTokenizer = new MusicTokenizer(searchText);
//        List<MusicToken> tokenList = new ArrayList<>();
//        try {
//            while (musicTokenizer.hasNext()) {
//                tokenList.add(musicTokenizer.current());
//                musicTokenizer.next();
//            }
//        }
//        catch (Exception e){
//            throw new MusicToken.IllegalTokenException("IllegalTokenException");
//        }
//        return tokenList;
//
//    }


    public List<MusicToken> getPartialValidList(){
        return null;
    }

    public boolean isMatched(Music music){
        if(!isValid()) throw new IllegalParserException("IllegalParserException");
        boolean check_tag = true;
        boolean check_artist = true;
        boolean check_star = true;
        boolean check_name = true;

        for(MusicToken token : tokenList){
            switch (token.getType()){
                case TAG:
                    MusicTag[] tags= music.getTag();
                    check_tag = false;
                    for(MusicTag tag : tags){
                        if(tag.toString().equals(token.getToken())) {
                            check_tag = true;
                            break;
                        }
                    }
                    break;
                case ARTIST:
                    check_artist = false;
                    String artist = music.getArtist();
                    if(artist.toLowerCase(Locale.ROOT).contains(token.getToken().toLowerCase(Locale.ROOT))) check_artist = true;
                    break;
                case STAR:
                    check_star = false;
                    double rate = music.getRate();
                    double rate_search = Double.parseDouble(token.getToken());
                    switch(token.getOperator()){
                        case ">=":
                            check_star = rate >= rate_search;
                            break;
                        case ">":
                            check_star = rate > rate_search;
                            break;
                        case "<=":
                            check_star = rate <= rate_search;
                            break;
                        case "<":
                            check_star = rate < rate_search;
                            break;
                        case "=":
                            check_star = rate == rate_search;
                            break;
                    }
                    break;
                case NAME:
                    check_name = false;
                    String name = music.getName();
                    if(name.toLowerCase(Locale.ROOT).contains(token.getToken().toLowerCase(Locale.ROOT))) check_name = true;
                    break;
            }
        }
        return check_tag && check_artist && check_star && check_name;
    };

    public static void main(String[] args) {
        String s = "#abc;@Mike;*>4.3;asd";
        MusicParser parser = new MusicParser(s);
        System.out.println(parser.isValid());
    }

}
