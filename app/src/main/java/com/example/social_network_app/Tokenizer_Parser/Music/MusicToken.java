package com.example.social_network_app.Tokenizer_Parser.Music;

public class MusicToken {
    // The following enum defines different types of tokens.
    public enum Type {NAME,TAG,ARTIST,SEMICOLON,STAR}

    /**
     * The following exception should be thrown if a tokenizer attempts to tokenize something that is not of one
     * of the types of tokens.
     */
    public static class IllegalTokenException extends IllegalArgumentException {
        public IllegalTokenException(String errorMessage) {
            super(errorMessage);
        }
    }

    private static final String symbol[] = {"==","<=",">="};
    // Fields of the class Token.
    private final String token; // Token representation in String form.
    private final Type type;    // Type of the token.
    private final String operator;
    private final int length;

    public MusicToken(String token, Type type, String operator) {
        this.token = token;
        this.type = type;
        this.operator = operator;
        this.length = this.token.length() + 3;
    }

    public MusicToken(String token, Type type) {
        this.token = token;
        this.type = type;
        this.operator = "";
        this.length = this.token.length() + 1;
    }

    public MusicToken(String piece){
        this.length = piece.length();
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
            if(!isIn(piece.substring(1,3),symbol)) throw new IllegalTokenException("IllegalToken!");
            this.operator = piece.substring(1,3);
            this.token = piece.substring(3);
        }
        else {
            this.type = Type.NAME;
            this.token = piece;
            this.operator = "";
        }
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

    @Override
    public String toString() {
        if(!this.operator.equals(""))return type + operator + token;
        return type + token;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true; // Same hashcode.
        if (!(other instanceof MusicToken)) return false; // Null or not the same type.
        return this.type == ((MusicToken) other).getType() && this.token.equals(((MusicToken) other).getToken()); // Values are the same.
    }


    private boolean isIn(String string, String symbol[]) {
        for (String s : symbol) {
            if (string.equals(s)) {
                return true;
            }
        }
        return false;
    }

}
