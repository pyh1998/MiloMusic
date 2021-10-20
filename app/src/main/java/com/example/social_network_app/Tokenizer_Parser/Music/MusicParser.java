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
    private MusicTokenizer tokenizer;

    public MusicParser(String searchText){
        tokenizer = new MusicTokenizer(searchText);
        parseExp();
    }

    /**
     * Determines whether the current conditional string is valid
     * @return return true if the string is valid
     */
    public boolean isValid(){
        for(MusicToken token :tokenList){
            if(token.getType()== MusicToken.Type.INVALID) return false;
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

    /**
     * Adheres to the grammar rule:
     * <exp>    ::= <term> | <term> ; <exp>
     *
     */
    public void parseExp(){
        parseTerm();
        if(tokenizer.hasNext() &&  tokenizer.current().getType() == MusicToken.Type.SEMICOLON){
            tokenizer.next();
            parseExp();
        }
    }

    /**
     * Adheres to the grammar rule:
     * <term>    ::= <TAG> | <ARTIST> | <STAR> | <NAME>
     *
     */
    public void parseTerm(){
        if(tokenizer.current().getType() != MusicToken.Type.INVALID){
            tokenList.add(tokenizer.current());
        }
        tokenizer.next();
    }

    public List<MusicToken> getTokenList() {
        return tokenList;
    }

    public List<MusicToken> getValidList(){
        List<MusicToken> validList = new ArrayList<>();
        int tag = 0;
        int artist = 0;
        int rate = 0;
        int name = 0;
        for(MusicToken token : tokenList){
            switch (token.getType()){
                case TAG:
                    if(tag<3){
                        validList.add(token);
                        tag++;
                    }
                    break;
                case ARTIST:
                    if(artist<1){
                        validList.add(token);
                        artist++;
                    }
                    break;
                case STAR:
                    if(rate<1){
                        validList.add(token);
                        rate++;
                    }
                    break;
                case NAME:
                    if(name<1){
                        validList.add(token);
                        name++;
                    }
                    break;
            }
        }
        return validList;
    }

    public boolean isMatched(Music music){
        List<Boolean> condition = new ArrayList<>();

        for(MusicToken token : tokenList){
            switch (token.getType()){
                case TAG:
                    MusicTag[] tags= music.getTag();
                    condition.add(false);
                    for(MusicTag tag : tags){
                        if(tag.toString().equals(token.getToken())) {
                            condition.set(condition.size()-1,true);
                            break;
                        }
                    }
                    break;
                case ARTIST:
                    condition.add(false);
                    String artist = music.getArtist();
                    if(artist.toLowerCase(Locale.ROOT).contains(token.getToken().toLowerCase(Locale.ROOT))) condition.set(condition.size()-1,true);
                    break;
                case STAR:
                    condition.add(false);
                    double rate = music.getRate();
                    double rate_search = Double.parseDouble(token.getToken());
                    switch(token.getOperator()){
                        case ">=":
                            condition.set(condition.size()-1,rate >= rate_search);
                            break;
                        case ">":
                            condition.set(condition.size()-1,rate > rate_search);
                            break;
                        case "<=":
                            condition.set(condition.size()-1,rate <= rate_search);;
                            break;
                        case "<":
                            condition.set(condition.size()-1,rate < rate_search);
                            break;
                        case "=":
                            condition.set(condition.size()-1,rate == rate_search);
                            break;
                    }
                    break;
                case NAME:
                    condition.add(false);
                    String name = music.getName();
                    if(name.toLowerCase(Locale.ROOT).contains(token.getToken().toLowerCase(Locale.ROOT))) condition.set(condition.size()-1,true);
                    break;
            }
        }
        boolean total = true;
        for(boolean b : condition){
            total = total && b;
        }
        return total;
    };

    public static void main(String[] args) {
        String s = "#abc;@Mike;*>4.3;asd";
        MusicParser parser = new MusicParser(s);
        System.out.println(parser.isValid());
        System.out.println(parser.getTokenList().toString());
    }

}
