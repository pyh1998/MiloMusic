package com.example.social_network_app.Tokenizer_Parser.Post;


import androidx.annotation.NonNull;

import java.util.regex.Pattern;

/**
 * @author Yuhui Pang
 *
 * The token of music, which has token, type and operator
 */
public class PostToken {
    // The following enum defines different types of tokens.
    public enum Type {USER,LIKECOUNT,CONTENT,SEMICOLON,INVALID}

    /**
     * The following exception should be thrown if a tokenizer attempts to tokenize something that is not of one
     * of the types of tokens.
     */
    public static class IllegalTokenException extends IllegalArgumentException {
        public IllegalTokenException(String errorMessage) {
            super(errorMessage);
        }
    }

    private static final String[] symbol = {"=","<=",">=",">","<"};
    // Fields of the class Token.
    private final String token; // Token representation in String form.
    private final PostToken.Type type;    // Type of the token.
    private final String operator;
    private final int length;


    public PostToken(String piece){
        this.length = piece.length();

        if(!isValid(piece)){
            this.type = Type.INVALID;
            this.token = "";
            this.operator = "";
        }
        else{
            char first = piece.charAt(0);
            if(first == ';'){
                this.type = Type.SEMICOLON;
                this.token = ";";
                this.operator = "";
            }
            else if(first == '@'){
                this.type = Type.USER;
                this.token = piece.substring(1);
                this.operator = "";
            }
            else if(first == '*'){
                this.type = Type.LIKECOUNT;
                int index;
                for(index = 1;index<piece.length();index++){
                    if(Character.isDigit(piece.charAt(index))) break;
                }
                if(!isIn(piece.substring(1,index))) throw new PostToken.IllegalTokenException("IllegalToken!");
                this.operator = piece.substring(1,index);
                this.token = piece.substring(index);
            }
            else {
                this.type = Type.CONTENT;
                this.token = piece;
                this.operator = "";
            }
        }

    }

    public static boolean isValid(String piece){
        if(piece == null || piece.length() == 0) return false;
        char first = piece.charAt(0);
        if(first == '*'){
            if(piece.length() == 1) return false;
            int index;
            for(index = 1;index<piece.length();index++){
                if(Character.isDigit(piece.charAt(index))) break;
            }
            if(index == 1 || index == piece.length()) return false;
            if(!isIn(piece.substring(1,index))) return false;
            String remain = piece.substring(index);
            String pattern = "^[0-9]*$";
            return Pattern.matches(pattern,remain);
        }
        else if (first == '@'){
            return piece.length() > 1;
        }
        else if(first == ';'){
            return piece.length() == 1;
        }
        return true;
    }



    public String getToken() {
        return token;
    }

    public PostToken.Type getType() {
        return type;
    }

    public String getOperator() {
        return operator;
    }

    public int getLength() {
        return length;
    }

    @NonNull
    @Override
    public String toString() {
        if(!this.operator.equals(""))return type + operator + token;
        return type +":" +token;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true; // Same hashcode.
        if (!(other instanceof PostToken)) return false; // Null or not the same type.
        return this.type == ((PostToken) other).getType() && this.token.equals(((PostToken) other).getToken()); // Values are the same.
    }


    private static boolean isIn(String string) {
        for (String s : PostToken.symbol) {
            if (string.equals(s)) {
                return true;
            }
        }
        return false;
    }
}
