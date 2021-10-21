package com.example.social_network_app.Tokenizer_Parser.Music;

import androidx.annotation.NonNull;

import java.util.regex.Pattern;

/**
 * @author Yuhui Pang
 *
 * The token of music, which has token, type and operator
 */
public class MusicToken {
    // The following enum defines different types of tokens.
    public enum Type {NAME,TAG,ARTIST,SEMICOLON,STAR,INVALID}

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
    private final Type type;    // Type of the token.
    private final String operator;
    private final int length;

    /**
     * get token from string
     * @param piece the input string, which need to be transferred to token
     */
    public MusicToken(String piece){
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
            else if(first == '#'){
                this.type = Type.TAG;
                this.token = piece.substring(1);
                this.operator = "";
            }
            else if(first == '@'){
                this.type = Type.ARTIST;
                this.token = piece.substring(1);
                this.operator = "";
            }
            else if(first == '*'){
                this.type = Type.STAR;
                int index;
                for(index = 1;index<piece.length();index++){
                    if(Character.isDigit(piece.charAt(index))) break;
                }
                if(!isIn(piece.substring(1,index))) throw new IllegalTokenException("IllegalToken!");
                this.operator = piece.substring(1,index);
                this.token = piece.substring(index);
            }
            else {
                this.type = Type.NAME;
                this.token = piece;
                this.operator = "";
            }
        }

    }

    /**
     * check if the input string is valid
     * @param piece the input string, which need to be checked
     * @return return true if the string is valid, return false if not
     */
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
            String pattern = "^([0-4](\\.[0-9])?|5(\\.0)?)$";
            return Pattern.matches(pattern,remain);
        }
        else if (first == '#' || first == '@'){
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

    public Type getType() {
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
        if (!(other instanceof MusicToken)) return false; // Null or not the same type.
        return this.type == ((MusicToken) other).getType() && this.token.equals(((MusicToken) other).getToken()); // Values are the same.
    }


    private static boolean isIn(String string) {
        for (String s : MusicToken.symbol) {
            if (string.equals(s)) {
                return true;
            }
        }
        return false;
    }

}
