package com.example.social_network_app.Tokenizer_Parser.Post;



import com.example.social_network_app.Basic_classes.PostDao.Post;
import com.example.social_network_app.GlobalVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PostParser {
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
    private static final int music_max = 1;
    private static final int user_max = 1;
    private static final int likecount_max = 1;
    private static final int content_max = 1;

    private List<PostToken> tokenList = new ArrayList<>();
    private PostTokenizer tokenizer;

    public PostParser(String searchText){
        tokenizer = new PostTokenizer(searchText);
        parseExp();
    }

    /**
     * Determines whether the current conditional string is valid
     * @return return true if the string is valid
     */
    public boolean isValid(){
        if(tokenList.isEmpty()) return false;
        for(PostToken token :tokenList){
            if(token.getType()== PostToken.Type.INVALID) return false;
        }
        int music = 0;
        int user = 0;
        int likecount = 0;
        int content = 0;
        for(PostToken token : tokenList){
            switch (token.getType()){
                case MUSIC:
                    music++;
                    break;
                case USER:
                    user++;
                    break;
                case LIKECOUNT:
                    likecount++;
                    break;
                case CONTENT:
                    content++;
                    break;
            }
        }
        return  music <= music_max && user <= user_max && likecount <= likecount_max && content <= content_max;
    }

    /**
     * Adheres to the grammar rule:
     * <exp>    ::= <term> | <term> ; <exp>
     *
     */
    public void parseExp(){
        parseTerm();
        if(tokenizer.hasNext() &&  tokenizer.current().getType() == PostToken.Type.SEMICOLON){
            tokenizer.next();
            parseExp();
        }
    }

    /**
     * Adheres to the grammar rule:
     * <term>    ::= <TAG> | <ARTIST> | <STAR> | <NAME> | <INVALID>
     *
     */
    public void parseTerm(){
        if(tokenizer.hasNext() && tokenizer.current().getType() != PostToken.Type.SEMICOLON){
            tokenList.add(tokenizer.current());
            tokenizer.next();
        }
    }

    public List<PostToken> getTokenList() {
        return tokenList;
    }

    public List<PostToken> getValidList(){
        List<PostToken> validList = new ArrayList<>();
        int music = 0;
        int user = 0;
        int likecount = 0;
        int content = 0;
        for(PostToken token : tokenList){
            switch (token.getType()){
                case MUSIC:
                    if(music<music_max){
                        validList.add(token);
                        music++;
                    }
                case USER:
                    if(user<user_max){
                        validList.add(token);
                        user++;
                    }
                    break;
                case LIKECOUNT:
                    if(likecount<likecount_max){
                        validList.add(token);
                        likecount++;
                    }
                    break;
                case CONTENT:
                    if(content<content_max){
                        validList.add(token);
                        content++;
                    }
                    break;
            }
        }
        return validList;
    }

    public boolean isMatched(Post post){
        List<Boolean> condition = new ArrayList<>();

        for(PostToken token : tokenList){
            switch (token.getType()){
                case MUSIC:
                    condition.add(false);
                    String music = post.getMusic_name();
                    if(music.toLowerCase(Locale.ROOT).contains(token.getToken().toLowerCase(Locale.ROOT))) condition.set(condition.size()-1,true);
                    break;
                case USER:
                    condition.add(false);
                    String user = post.getUser_name();
                    if(user.toLowerCase(Locale.ROOT).contains(token.getToken().toLowerCase(Locale.ROOT))) condition.set(condition.size()-1,true);
                    break;
                case LIKECOUNT:
                    condition.add(false);
                    double num = post.getLikeCount();
                    double num_search = Double.parseDouble(token.getToken());
                    switch(token.getOperator()){
                        case ">=":
                            condition.set(condition.size()-1,num >= num_search);
                            break;
                        case ">":
                            condition.set(condition.size()-1,num > num_search);
                            break;
                        case "<=":
                            condition.set(condition.size()-1,num <= num_search);;
                            break;
                        case "<":
                            condition.set(condition.size()-1,num < num_search);
                            break;
                        case "=":
                            condition.set(condition.size()-1,num == num_search);
                            break;
                    }
                    break;
                case CONTENT:
                    condition.add(false);
                    String content = post.getUserReviews();
                    if(content.toLowerCase(Locale.ROOT).contains(token.getToken().toLowerCase(Locale.ROOT))) condition.set(condition.size()-1,true);
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
        String s = "#music;@abc;*<489;asdd";
        PostParser parser = new PostParser(s);
        System.out.println(parser.isValid());
        System.out.println(parser.getTokenList().toString());
    }
}
